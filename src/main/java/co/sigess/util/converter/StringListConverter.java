/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.sigess.util.converter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

/**
 *
 * @author fmoreno
 */
@Converter
public class StringListConverter implements AttributeConverter<List<String>, String> {

    @Override
    public String convertToDatabaseColumn(List<String> list) {
        if (list == null) {
            return null;
        }
        return String.join(";", list);
    }

    @Override
    public List<String> convertToEntityAttribute(String joined) {
        return convertToEntity(joined);
    }

    public static List<String> convertToEntity(String joined) {
        if (joined == null) {
            return null;
        }
        return new ArrayList<>(Arrays.asList(joined.split(";")));
    }

}
