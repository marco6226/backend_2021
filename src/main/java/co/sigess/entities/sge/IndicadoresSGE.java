/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.sigess.entities.sge;

import java.util.List;

/**
 *
 * @author fmoreno
 */
public class IndicadoresSGE {

    private List<IndicadoresData> data;
    private String nombre;

    public List<IndicadoresData> getData() {
        return data;
    }

    public void setData(List<IndicadoresData> data) {
        this.data = data;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    
    
}
