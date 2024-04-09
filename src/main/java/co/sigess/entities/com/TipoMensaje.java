/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.sigess.entities.com;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonFormat;

/**
 *
 * @author fmoreno
 */
//@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum TipoMensaje {
    success,
    info,
    warn,
    error;

    @JsonCreator // This is the factory method and must be static
    public static TipoMensaje fromString(String string) {
        return TipoMensaje.valueOf(string);
    }

    @Override
    public String toString() {
        return name();
    }

}
