/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.sigess.restful.ipr;

import co.sigess.entities.ipr.Efecto;
import co.sigess.facade.ipr.EfectoFacade;
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
@Path("efecto")
public class EfectoREST extends ServiceREST {

    @EJB
    private EfectoFacade efectoFacade;

    @GET
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response findWithFilter(@BeanParam FilterQuery filterQuery) {
        try {            
            List<Efecto> list = efectoFacade.findWithFilter(filterQuery);
            return Response.ok(list).build();
        } catch (Exception ex) {
            return Util.manageException(ex, EfectoREST.class);
        }
    }

    @GET
    @Path("{efectoId}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response find(@PathParam("efectoId") Integer efectoId) {
        try {
            Efecto efecto = efectoFacade.find(efectoId);
            return Response.ok(efecto).build();
        } catch (Exception ex) {
            return Util.manageException(ex, EfectoREST.class);
        }
    }

    @POST
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response create(Efecto efecto) {
        try {
            efecto = efectoFacade.create(efecto);
            return Response.ok(efecto).build();
        } catch (Exception ex) {
            return Util.manageException(ex, EfectoREST.class);
        }
    }

    @PUT
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response edit(Efecto efecto) {
        try {
            efecto = efectoFacade.edit(efecto);
            return Response.ok(efecto).build();
        } catch (Exception ex) {
            return Util.manageException(ex, EfectoREST.class);
        }
    }

    @DELETE
    @Path("{efectoId}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response remove(@PathParam("efectoId") Integer efectoId) {
        try {
            Efecto efecto = efectoFacade.find(efectoId);
            efectoFacade.remove(efecto);
            return Response.ok(efecto).build();
        } catch (Exception ex) {
            return Util.manageException(ex, EfectoREST.class);
        }
    }

}
