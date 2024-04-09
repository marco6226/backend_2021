/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.sigess.util.converter;

import java.util.Date;
import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import org.postgresql.util.PGInterval;

/**
 *
 * @author fmoreno
 */
@Converter
public class DurationConverter implements AttributeConverter<Date, PGInterval> {

    @Override
    public PGInterval convertToDatabaseColumn(Date duration) {
        if (duration == null) {
            return null;
        }
        PGInterval pGInterval = new PGInterval(0, 0, 0, (int) duration.getHours(), (int) duration.getMinutes(), Double.valueOf("0"));
        return pGInterval;
    }

    @Override
    public Date convertToEntityAttribute(PGInterval pgInterval) {
        if (pgInterval == null) {
            return null;
        }
        Date d = new Date();
        d.setHours(pgInterval.getHours());
        d.setMinutes(pgInterval.getMinutes());
        return d;
    }

}
