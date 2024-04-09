/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.sigess.exceptions;

import co.sigess.entities.com.Mensaje;
import co.sigess.entities.com.TipoMensaje;

/**
 *
 * @author fmoreno
 */
public class UserMessageException extends RuntimeException {

    private final Mensaje mensaje;

    
    public UserMessageException(String mensaje, String detalle, TipoMensaje tipoMensaje) {
        this.mensaje = new Mensaje(mensaje, detalle, tipoMensaje);
    }
    
    public UserMessageException(Mensaje msg) {
        this.mensaje = msg;
    }
    
    
    public Mensaje getObjetoMensaje() {
        return mensaje;
    }

    public String getMensaje() {
        return mensaje.getMensaje();
    }

    public String getDetalle() {
        return mensaje.getDetalle();
    }

    public TipoMensaje getTipoMensaje() {
        return mensaje.getTipoMensaje();
    }


    public Integer getCodigo() {
        return mensaje.getCodigo();
    }

    @Override
    public String toString() {
        return "UserMessageException{" + mensaje + '}';
    }

}
