/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.sigess.entities.com;

import com.fasterxml.jackson.annotation.JsonFormat;

/**
 *
 * @author fmoreno
 */
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum TipoIdentificacion {
    CEDULA_CIUDADANIA("Cédula de ciudadanía"),
    CEDULA_EXTRANJERIA("Cédula de extranjería"),
    TARJETA_IDENTIDAD("Tarjeta de identidad");

    private final String descripcion;

    TipoIdentificacion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getId() {
        return this.name();
    }
    
    public String getNombre() {
        return descripcion;
    }
}
