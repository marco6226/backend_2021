/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.sigess.restful.emp;

import co.sigess.entities.emp.HorasExtra;
import co.sigess.facade.emp.HorasExtraFacade;
import co.sigess.restful.ServiceREST;
import co.sigess.restful.security.Secured;
import co.sigess.util.Util;
import javax.ejb.EJB;
import javax.ws.rs.DELETE;
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
@Path("horasExtra")
public class HorasExtraREST extends ServiceREST{

    @EJB
    private HorasExtraFacade horasExtraFacade;

    @POST
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response create(HorasExtra horasExtra) {
        try {
            horasExtra = this.horasExtraFacade.create(horasExtra);
            return Response.ok(horasExtra).build();
        } catch (Exception ex) {
            return Util.manageException(ex, HorasExtraREST.class);
        }
    }

    @PUT
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response edit(HorasExtra horasExtra) {
        try {
            horasExtra = this.horasExtraFacade.edit(horasExtra);
            return Response.ok(horasExtra).build();
        } catch (Exception ex) {
            return Util.manageException(ex, HorasExtraREST.class);
        }
    }

    @DELETE
    @Path("{horasExtraId}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response delete(@PathParam("horasExtraId") Long horasExtraId) {
        try {
            HorasExtra entity = this.horasExtraFacade.find(horasExtraId);
            this.horasExtraFacade.remove(entity);
            return Response.ok(entity).build();
        } catch (Exception ex) {
            return Util.manageException(ex, HorasExtraREST.class);
        }
    }


}
