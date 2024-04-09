/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.sigess.restful.security;

import java.io.InputStream;
import java.io.OutputStream;
import javax.crypto.Cipher;
import javax.crypto.SecretKeyFactory;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.security.spec.KeySpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.IvParameterSpec;

/**
 *
 * @author fmoreno
 */
public class FileEncryption {

    private final static int BUFFER_LENGHT = 65536;
    private final static int KEY_LENGHT = 256;
    private final static int ITERATOR_COUNT = 10000;
    private final static String PASSWD = "FDCBqujbBe6HWep4vxckxAHCUXQjWmkdAMFBRPC4EjBcpA7uHUqRkWjQxZKjWU8nhDNYh2Sza5n9v5t4PGKYfTsCJ2xwMvvFYTgSLY3LFfxZaPTQnurtYGFyz3FWnLtWgZg7YFH9GshXZZ6PR97v6MPtVdpURJcXz5G9Ae97g3V8DRMmBwsxTRArUNannXbxTHx5yg3FUzqp2Mkt8nAC4ZSzqSMntzJWSRRfXvLuEZHgDDknRKQwAjGAN5M6unDb";
    private final byte[] salt = {8, 25, 100, 125, 85, 45, 63, 7};
    private final byte[] iv = {74, 85, 96, 1, 4, 85, 42, 63, 98, 7, 45, 0, 63, 23, 10, 91};
    private final SecretKeySpec skey;

    private static FileEncryption instance;

    public static FileEncryption getInstance() throws Exception {
        if (instance == null) {
            instance = new FileEncryption();
        }
        return instance;
    }

    private FileEncryption() throws Exception {
        SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
        KeySpec spec = new PBEKeySpec(PASSWD.toCharArray(), salt, ITERATOR_COUNT, KEY_LENGHT);
        SecretKey tmp = factory.generateSecret(spec);
        skey = new SecretKeySpec(tmp.getEncoded(), "AES");
    }

    public void encrypt(InputStream fis, OutputStream fos) throws Exception {
        IvParameterSpec ivspec = new IvParameterSpec(iv);
        Cipher ci = Cipher.getInstance("AES/CBC/PKCS5Padding");
        ci.init(Cipher.ENCRYPT_MODE, skey, ivspec);
        processFile(ci, fis, fos);
    }

    public void decrypt(InputStream in, OutputStream out) throws Exception {
        Cipher ci = Cipher.getInstance("AES/CBC/PKCS5Padding");
        ci.init(Cipher.DECRYPT_MODE, skey, new IvParameterSpec(iv));
        processFile(ci, in, out);
    }

    static private void processFile(Cipher ci, InputStream in, OutputStream out)
            throws javax.crypto.IllegalBlockSizeException,
            javax.crypto.BadPaddingException,
            java.io.IOException {
        byte[] ibuf = new byte[BUFFER_LENGHT];
        int len;
        while ((len = in.read(ibuf)) != -1) {
            byte[] obuf = ci.update(ibuf, 0, len);
            if (obuf != null) {
                out.write(obuf);
            }
        }
        byte[] obuf = ci.doFinal();
        if (obuf != null) {
            out.write(obuf);
            out.flush();
            out.close();
        }
    }
}
