/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test.co.sigess.restful.security;

import co.sigess.restful.security.FileEncryption;
import co.sigess.util.FileUtil;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

/**
 *
 * @author fmoreno
 */
public class TestFileEncryption {

    @Rule
    public TemporaryFolder folder = new TemporaryFolder();

    public TestFileEncryption() {
    }

    @Test
    public void test() throws Exception {
        //--------------------------------------- Test streams existentes y validos ------------------------------------------------
        File plainFile = folder.newFile("input_dummy_file.txt");
        File encFile = folder.newFile("output_dummy_file." + FileUtil.SEF_EXT);
        FileInputStream inpEncFile = new FileInputStream(plainFile);
        FileOutputStream outEncFile = new FileOutputStream(encFile);
        FileEncryption.getInstance().encrypt(inpEncFile, outEncFile);

        FileInputStream inpDecFile = new FileInputStream(encFile);
        FileOutputStream outDecFile = new FileOutputStream(plainFile);
        FileEncryption.getInstance().decrypt(inpDecFile, outDecFile);

    }

}
