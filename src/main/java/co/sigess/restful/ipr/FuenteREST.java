/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.sigess.restful.ipr;

import co.sigess.entities.ipr.Fuente;
import co.sigess.facade.ipr.FuenteFacade;
import co.sigess.restful.FilterQuery;
import co.sigess.restful.ServiceREST;
import co.sigess.restful.security.Secured;
import co.sigess.util.Util;
import java.util.List;
import javax.ejb.EJB;
import javax.ws.rs.BeanParam;
import javax.ws.rs.DELETE;
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
@Path("fuente")
public class FuenteREST extends ServiceREST {

    @EJB
    private FuenteFacade fuenteFacade;

    @GET
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response findWithFilter(@BeanParam FilterQuery filterQuery) {
        try {            
            List<Fuente> list = fuenteFacade.findWithFilter(filterQuery);
            return Response.ok(list).build();
        } catch (Exception ex) {
            return Util.manageException(ex, FuenteREST.class);
        }
    }

    @GET
    @Path("{fuenteId}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response find(@PathParam("fuenteId") Integer fuenteId) {
        try {
            Fuente fuente = fuenteFacade.find(fuenteId);
            return Response.ok(fuente).build();
        } catch (Exception ex) {
            return Util.manageException(ex, FuenteREST.class);
        }
    }

    @POST
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response create(Fuente fuente) {
        try {
            fuente = fuenteFacade.create(fuente);
            return Response.ok(fuente).build();
        } catch (Exception ex) {
            return Util.manageException(ex, FuenteREST.class);
        }
    }

    @PUT
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response edit(Fuente fuente) {
        try {
            fuente = fuenteFacade.edit(fuente);
            return Response.ok(fuente).build();
        } catch (Exception ex) {
            return Util.manageException(ex, FuenteREST.class);
        }
    }

    @DELETE
    @Path("{fuenteId}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response remove(@PathParam("fuenteId") Integer fuenteId) {
        try {
            Fuente fuente = fuenteFacade.find(fuenteId);
            fuenteFacade.remove(fuente);
            return Response.ok(fuente).build();
        } catch (Exception ex) {
            return Util.manageException(ex, FuenteREST.class);
        }
    }

}
