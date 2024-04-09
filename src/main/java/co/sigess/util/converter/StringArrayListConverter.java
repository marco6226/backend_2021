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
import org.postgresql.util.PGobject;

/**
 *
 * @author fmoreno
 */
@Converter
public class StringArrayListConverter implements AttributeConverter<List<String>, Object> {

    @Override
    public Object convertToDatabaseColumn(List<String> list) {
        try {
            if (list == null) {
                return null;
            }
            PGobject out = new PGobject();
            out.setType("inet[]");
            String valor = "{" + String.join(",", list) + "}";
            out.setValue(valor);
            return out;
        } catch (Exception e) {
            throw new IllegalArgumentException("Unable to serialize to jsonb field ", e);
        }
    }

    @Override
    public List<String> convertToEntityAttribute(Object joined) {
        return convertToEntity(joined);
    }

    public static List<String> convertToEntity(Object joined) {
        if (joined == null) {
            return null;
        }
        Object[] obj = (Object[]) joined;
        List<String> list = new ArrayList<>();
        for (Object ip : obj) {
            list.add(((PGobject)ip).getValue());
        }
        return list;
    }

}
