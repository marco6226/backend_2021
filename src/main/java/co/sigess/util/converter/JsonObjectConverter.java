/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.sigess.util.converter;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import javax.persistence.Converter;
import org.postgresql.util.PGobject;

/**
 *
 * @author fmoreno
 */
@Converter(autoApply = true)
public class JsonObjectConverter implements javax.persistence.AttributeConverter<Map<String, Object>, Object> {

    private static final ObjectMapper mapper = new ObjectMapper();

    @Override
    public Object convertToDatabaseColumn(Map<String, Object> map) {
        try {
            String json = mapper.writeValueAsString(map);
            PGobject out = new PGobject();
            out.setType("jsonb");
            out.setValue(json);
            return out;
        } catch (Exception e) {
            throw new IllegalArgumentException("Unable to serialize to jsonb field ", e);
        }
    }

    @Override
    public Map<String, Object> convertToEntityAttribute(Object dataValue) {
        if (dataValue == null) {
            return null;
        }
        try {
            Map<String, Object> map = mapper.readValue(((PGobject) dataValue).getValue(), Map.class);
            return map;
        } catch (IOException ex) {
            throw new IllegalArgumentException("Unable to deserialize to List field ", ex);
        }
    }

}
