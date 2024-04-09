/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.sigess.restful.ipr;

import co.sigess.entities.ipr.Control;
import co.sigess.facade.ipr.ControlFacade;
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
@Path("control")
public class ControlREST extends ServiceREST {

    @EJB
    private ControlFacade controlFacade;

    @GET
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response findWithFilter(@BeanParam FilterQuery filterQuery) {
        try {            
            List<Control> list = controlFacade.findWithFilter(filterQuery);
            return Response.ok(list).build();
        } catch (Exception ex) {
            return Util.manageException(ex, ControlREST.class);
        }
    }

    @GET
    @Path("{controlId}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response find(@PathParam("controlId") Integer controlId) {
        try {
            Control control = controlFacade.find(controlId);
            return Response.ok(control).build();
        } catch (Exception ex) {
            return Util.manageException(ex, ControlREST.class);
        }
    }

    @POST
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response create(Control control) {
        try {
            control = controlFacade.create(control);
            return Response.ok(control).build();
        } catch (Exception ex) {
            return Util.manageException(ex, ControlREST.class);
        }
    }

    @PUT
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response edit(Control control) {
        try {
            control = controlFacade.edit(control);
            return Response.ok(control).build();
        } catch (Exception ex) {
            return Util.manageException(ex, ControlREST.class);
        }
    }

    @DELETE
    @Path("{controlId}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response remove(@PathParam("controlId") Integer controlId) {
        try {
            Control tipoControl = controlFacade.find(controlId);
            controlFacade.remove(tipoControl);
            return Response.ok(tipoControl).build();
        } catch (Exception ex) {
            return Util.manageException(ex, ControlREST.class);
        }
    }

}
