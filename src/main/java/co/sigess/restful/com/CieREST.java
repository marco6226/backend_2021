/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.sigess.restful.com;

import co.sigess.entities.com.Cie;
import co.sigess.facade.com.CieFacade;
import java.util.List;
import javax.ejb.EJB;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 *
 * @author fmoreno
 */
@Path("cie")
public class CieREST {

    @EJB
    private CieFacade cieFacade;

    @GET
    @Path("buscar/{parametro}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response buscar(@PathParam("parametro") String parametro) {
        List<Cie> list = cieFacade.buscar(parametro);
        return Response.ok(list).build();
    }
}
