/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.sigess.entities.sec;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonValue;

/**
 *
 * @author fmoreno
 */
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum EstadoTarea {
    NUEVO,
    REALIZADA,
    VERIFICADA,
    FINALIZADA;
    
    @JsonValue
    @Override
    public String toString() {
        return super.toString(); //To change body of generated methods, choose Tools | Templates.
    }
}
