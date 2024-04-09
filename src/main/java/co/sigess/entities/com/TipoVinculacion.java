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
public enum TipoVinculacion {
    PLANTA("Planta"),
    MISION("Misión"),
    COOPERADO("Cooperado"),
    ESTUDIANTE_APRENDIZ("Estudiante o aprendiz"),
    INDEPENDIENTE("Independiente");

    private final String descripcion;

    TipoVinculacion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getId() {
        return this.name();
    }
    
    public String getNombre() {
        return descripcion;
    }
}
