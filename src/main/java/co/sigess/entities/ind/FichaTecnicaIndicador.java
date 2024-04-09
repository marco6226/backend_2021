/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.sigess.entities.ind;

import java.io.Serializable;

/**
 *
 * @author fmoreno
 */
public class FichaTecnicaIndicador implements Serializable{

    private String proceso;
    private String nombre;
    private String descripcion;
    private String formulacion;
    private String frecuenciaSeguimiento;
    private String meta;

    public FichaTecnicaIndicador() {
    }

    public FichaTecnicaIndicador(String proceso, String nombre, String descripcion, String formulacion, String frecuenciaSeguimiento, String meta) {
        this.proceso = proceso;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.formulacion = formulacion;
        this.frecuenciaSeguimiento = frecuenciaSeguimiento;
        this.meta = meta;
    }

    public String getProceso() {
        return proceso;
    }

    public void setProceso(String proceso) {
        this.proceso = proceso;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getFormulacion() {
        return formulacion;
    }

    public void setFormulacion(String formulacion) {
        this.formulacion = formulacion;
    }


    public String getFrecuenciaSeguimiento() {
        return frecuenciaSeguimiento;
    }

    public void setFrecuenciaSeguimiento(String frecuenciaSeguimiento) {
        this.frecuenciaSeguimiento = frecuenciaSeguimiento;
    }

    public String getMeta() {
        return meta;
    }

    public void setMeta(String meta) {
        this.meta = meta;
    }

}
