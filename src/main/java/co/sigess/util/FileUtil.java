/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.sigess.util;

import co.sigess.restful.security.FileEncryption;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.nio.file.Files;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.spec.SecretKeySpec;

/**
 *
 * @author fmoreno
 */
public class FileUtil {

    public static final String RELATIVE_PATH = "RELATIVE_PATH";
    public static final String FILE_SIZE = "FILE_SIZE";
    private final static int BUFFER_LENGHT = 65536;

    public static final String SEF_EXT = "sef";
    public static final String ROOT_DIR = System.getProperty("user.home") + File.separator + "sigess_fs" + File.separator;

    static {
        File rootDir = new File(ROOT_DIR);
        if (!rootDir.exists()) {
            rootDir.mkdirs();
        }
        rootDir = null;
    }

    public static Map<String, Object> saveInVirtualFS(InputStream in) throws FileNotFoundException, IOException, Exception {
        byte[] ibuf = new byte[BUFFER_LENGHT];

        String pathfile = Util.getFechaId() + "." + "jpg";
        int len;

        File outFile = new File(ROOT_DIR + File.separator + pathfile);
        FileOutputStream out = new FileOutputStream(outFile);

        while ((len = in.read(ibuf)) != -1) {
            out.write(ibuf, 0, len);
        }
        // FileEncryption.getInstance().encrypt(fileInputStream, fos);

        Map<String, Object> map = new HashMap<>();
        map.put(RELATIVE_PATH, pathfile);
        map.put(FILE_SIZE, outFile.length());
        return map;
    }

    public static Map<String, Object> saveInPathFS(InputStream fileInputStream) throws FileNotFoundException, IOException, Exception {

        String pathfile = Util.getFechaId() + "." + SEF_EXT;
        File outFile = new File(ROOT_DIR + File.separator + pathfile);
        FileOutputStream fos = new FileOutputStream(outFile);
        FileEncryption.getInstance().encrypt(fileInputStream, fos);

        Map<String, Object> map = new HashMap<>();
        map.put(RELATIVE_PATH, pathfile);
        map.put(FILE_SIZE, outFile.length());
        return map;
    }

    public static boolean removeFromVirtualFS(String ruta) throws FileNotFoundException, IOException {
        File documento = new File(ROOT_DIR + ruta);
        Files.deleteIfExists(documento.toPath());
        int lastIndex = ruta.lastIndexOf(File.separator);

        // Si el indice es menor que cero, indica que el documento se encuentra en la raíz de sistema de archivos del sistema
        if (lastIndex < 0) {
            return true;
        } else {
            // En caso contrario, significa que estaba alojado en un directorio que debe ser eliminado
            ruta = ruta.substring(0, lastIndex);
            File directorio = new File(ROOT_DIR + ruta);
            String[] files = directorio.list();
            if (files == null || files.length <= 0) {
                Files.deleteIfExists(directorio.toPath());
            }
            return true;
        }
    }

    public static OutputStream getFromVirtualFS(String relativePath) throws FileNotFoundException, Exception {
        InputStream fis = new FileInputStream(ROOT_DIR + relativePath);
        OutputStream fos = new ByteArrayOutputStream();
        FileEncryption.getInstance().decrypt(fis, fos);
        return fos;
    }

    public static String getFromVirtualFS2(String relativePath) throws FileNotFoundException, Exception {
        File f = new File(ROOT_DIR + relativePath);
        FileInputStream fileInputStreamReader = new FileInputStream(f);
        byte[] bytes = new byte[(int) f.length()];
        fileInputStreamReader.read(bytes);
        return new String(Base64.getEncoder().encodeToString(bytes));
    }

    public static String getFromVirtualFS3(String relativePath) throws FileNotFoundException, Exception {
        InputStream fis = new FileInputStream(ROOT_DIR + relativePath);
        OutputStream fos = new ByteArrayOutputStream();
        FileEncryption.getInstance().decrypt(fis, fos);
        System.out.print(fos);
        return fos.toString();
    }

}
