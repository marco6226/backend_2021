/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.sigess.entities.com;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonFormat;
import java.util.Optional;

/**
 *
 * @author fmoreno
 */
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum TipoSede {
    FISICA("Física", "");
        
    private final String nombre;
    private final String descripcion;

    private TipoSede(String nombre, String descripcion) {
        this.nombre = nombre;
        this.descripcion = descripcion;
    }

    public String getId() {
        return this.name();
    }

    public String getNombre() {
        return nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }
    
    @JsonCreator // This is the factory method and must be static
    public static TipoSede fromString(String string) {
        return TipoSede.valueOf(string);
    }
    
    @Override
    public String toString() {
        return name();
    }

}
