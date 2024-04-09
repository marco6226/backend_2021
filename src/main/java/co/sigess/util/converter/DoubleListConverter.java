/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.sigess.util.converter;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

/**
 *
 * @author fmoreno
 */
@Converter
public class DoubleListConverter implements AttributeConverter<List<Double>, String> {

    @Override
    public String convertToDatabaseColumn(List<Double> list) {
        if (list == null) {
            return null;
        }
        String cadena = "";
        for (Double valor : list) {
            cadena += valor + ";";
        }                
        return cadena.substring(0, cadena.length() - 2);
    }

    @Override
    public List<Double> convertToEntityAttribute(String joined) {
        if (joined == null) {
            return null;
        }
        List<Double> list = new ArrayList<>();
        for (String strVal : joined.split(";")) {
            Double valor = Double.valueOf(strVal);
            list.add(valor);
        }
        return list;
    }

}
