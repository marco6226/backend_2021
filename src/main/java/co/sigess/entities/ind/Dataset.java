/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.sigess.entities.ind;

import co.sigess.util.converter.JsonListConverter;
import java.util.List;
import javax.persistence.Convert;

/**
 *
 * @author fmoreno
 */
public class Dataset {

    private String label;
    private List<Double> data;
    private String posicionUnidad;
    private String unidad;

    @Convert(converter = JsonListConverter.class)
    private List dataStyle;

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public List<Double> getData() {
        return data;
    }

    public void setData(List<Double> data) {
        this.data = data;
    }

    public String getPosicionUnidad() {
        return posicionUnidad;
    }

    public void setPosicionUnidad(String posicionUnidad) {
        this.posicionUnidad = posicionUnidad;
    }

    public String getUnidad() {
        return unidad;
    }

    public void setUnidad(String unidad) {
        this.unidad = unidad;
    }

    public List getDataStyle() {
        return dataStyle;
    }

    public void setDataStyle(List dataStyle) {
        this.dataStyle = dataStyle;
    }
}
