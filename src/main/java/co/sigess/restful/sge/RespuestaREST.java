/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.sigess.restful.sge;

import co.sigess.entities.sge.EstadoEvaluacion;
import co.sigess.entities.sge.Respuesta;
import co.sigess.facade.sge.RespuestaFacade;
import co.sigess.restful.security.Secured;
import co.sigess.util.Util;
import java.util.List;
import javax.ejb.EJB;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 *
 * @author fmoreno
 */
@Secured
@Path("respuesta")
public class RespuestaREST {

    @EJB
    private RespuestaFacade respuestaFacade;

    @POST
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response create(Respuesta respuesta) {
        try {
            EstadoEvaluacion ee = respuestaFacade.adicionarRespuesta(respuesta);
            return Response.ok(ee).build();
        } catch (Exception ex) {
            return Util.manageException(ex, RespuestaREST.class);
        }
    }

    @PUT
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response edit(Respuesta respuesta) {
        try {
            respuesta = respuestaFacade.edit(respuesta);
            return Response.ok(respuesta).build();
        } catch (Exception ex) {
            return Util.manageException(ex, RespuestaREST.class);
        }
    }

    @GET
    @Path("evaluacion/{evaluacionId}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response findAllByEvaluacion(@PathParam("evaluacionId") Integer evaluacionId) {
        try {
            List<Respuesta> list = respuestaFacade.findAllByEvaluacion(evaluacionId);
            return Response.ok(list).build();
        } catch (Exception ex) {
            return Util.manageException(ex, EvaluacionREST.class);
        }
    }

}
