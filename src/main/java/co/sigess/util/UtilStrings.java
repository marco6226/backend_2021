/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.sigess.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author fmoreno
 */
public class UtilStrings {

    public static void replaceAll(StringBuilder sb, String src, String replacement) {
        Pattern pattern = Pattern.compile(src);
        Matcher m = pattern.matcher(sb);
        int start = 0;
        while (m.find(start)) {
            sb.replace(m.start(), m.end(), replacement);
            start = m.start() + replacement.length();
        }
    }
}
