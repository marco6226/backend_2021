/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.sigess.reports.sge;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ws.rs.NotAuthorizedException;

/**
 *
 * @author fmoreno
 */
public class FieldModel {

    private String key;
    private String type;
    private String formatClass;
    private String formatPattern;
    private String stringModel;

    private Object formatter;

    public String format(Object rawValue) throws Exception {
        if(rawValue == null){
            return "";
        }
        if(formatClass == null){
            return rawValue.toString();
        }
        Class clazz = null;
        if (this.formatter == null || clazz == null) {
            clazz = Class.forName(formatClass);
            this.formatter = clazz.newInstance();
            clazz.getMethod("applyPattern", String.class).invoke(formatter, formatPattern);
        }
        return clazz.getMethod("format", Object.class).invoke(formatter, rawValue).toString();

//        switch (formatClass) {
//            case "java.text.DecimalFormat":
//                java.text.DecimalFormat dcFormatter = new java.text.DecimalFormat(formatPattern);
//                return dcFormatter.format(rawValue);
//            case "java.text.SimpleDateFormat":
//                java.text.SimpleDateFormat sdfFormatter = new java.text.SimpleDateFormat(formatPattern);
//                return sdfFormatter.format(rawValue);
//            default:
//                rawValue.toString();
//                break;
//        }
//
//        return null;
    }

    public static FieldModel fromJson(String json) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            return mapper.readValue(json, new TypeReference<FieldModel>() {
            });
        } catch (JsonProcessingException ex) {
            Logger.getLogger(FieldModel.class.getName()).log(Level.SEVERE, null, ex);
            throw new NotAuthorizedException("No Authorization");
        } catch (IOException ex) {
            Logger.getLogger(FieldModel.class.getName()).log(Level.SEVERE, null, ex);
            throw new NotAuthorizedException("No Authorization");
        }
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getFormatClass() {
        return formatClass;
    }

    public void setFormatClass(String formatClass) {
        this.formatClass = formatClass;
    }

    public String getFormatPattern() {
        return formatPattern;
    }

    public void setFormatPattern(String formatPattern) {
        this.formatPattern = formatPattern;
    }

    public String getStringModel() {
        return stringModel;
    }

    public void setStringModel(String stringModel) {
        this.stringModel = stringModel;
    }

}
