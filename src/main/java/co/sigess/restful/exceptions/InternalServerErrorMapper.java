/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.sigess.restful.exceptions;

import co.sigess.entities.com.Mensaje;
import co.sigess.entities.com.TipoMensaje;
import co.sigess.util.Util;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

/**
 *
 * @author fmoreno
 */
@Provider
public class InternalServerErrorMapper implements ExceptionMapper<Exception> {

    @Override
    public Response toResponse(Exception exception) {
        Mensaje msg = new Mensaje();
        msg.setMensaje("ERROR ID: " + Util.getFechaId());
        msg.setDetalle("La solicitud presenta inconsistencias y no puede ser resuelta");
        msg.setTipoMensaje(TipoMensaje.error);
        Logger.getLogger(InternalServerErrorMapper.class.getName()).log(Level.SEVERE, msg.toString(), exception);
        return Response.status(Response.Status.BAD_REQUEST)
                .entity(msg)
                .build();
    }
}
