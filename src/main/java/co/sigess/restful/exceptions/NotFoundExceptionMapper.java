/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.sigess.restful.exceptions;

import co.sigess.entities.com.Mensaje;
import co.sigess.entities.com.TipoMensaje;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

/**
 *
 * @author fmoreno
 */
@Provider
public class NotFoundExceptionMapper implements ExceptionMapper<NotFoundException> {

    @Override
    public Response toResponse(NotFoundException exception) {
        Mensaje msg = new Mensaje();
        msg.setMensaje("Recurso solicitado no encontrado");
        msg.setDetalle("El recurso solicitado no existe o no se ha encontrado");
        msg.setTipoMensaje(TipoMensaje.warn);
        return Response.status(Response.Status.NOT_FOUND)
                .entity(msg)
                .build();
    }
}
