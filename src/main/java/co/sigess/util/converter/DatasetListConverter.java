/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.sigess.util.converter;

import co.sigess.entities.ind.Dataset;
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
public class DatasetListConverter implements AttributeConverter<List<Dataset>, String> {

    
    @Override
    public String convertToDatabaseColumn(List<Dataset> attribute) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Dataset> convertToEntityAttribute(String joined) {
        return convertToEntity(joined);
    }

    public static List<Dataset> convertToEntity(String joined) {
        if (joined == null) {
            return null;
        }
        String[] datasetArray = joined.split("\n");
        List<Dataset> list = new ArrayList<>();
        for (String strDataset : datasetArray) {
            String[] dataSplit = strDataset.split("#");
            
            Dataset dataset = new Dataset();
            dataset.setLabel(dataSplit[0]);
            dataset.setData(new ArrayList<>());
            
            String[] data = dataSplit[1].split(";");
            for (String strValue : data) {
                dataset.getData().add(Double.parseDouble(strValue));
            }
            list.add(dataset);
        }
        
        return list;
    }


}
