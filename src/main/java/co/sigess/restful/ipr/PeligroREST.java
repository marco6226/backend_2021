/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.sigess.restful.ipr;

import co.sigess.entities.com.Mensaje;
import co.sigess.entities.com.TipoMensaje;
import co.sigess.entities.ipr.Peligro;
import co.sigess.facade.ipr.PeligroFacade;
import co.sigess.restful.FilterQuery;
import co.sigess.restful.ServiceREST;
import co.sigess.restful.security.Secured;
import co.sigess.util.Util;
import java.util.List;
import javax.ejb.EJB;
import javax.persistence.PersistenceException;
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
@Path("peligro")
public class PeligroREST extends ServiceREST {

    @EJB
    private PeligroFacade peligroFacade;

    @GET
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response findWithFilter(@BeanParam FilterQuery filterQuery) {
        try {            
            List<Peligro> list = peligroFacade.findWithFilter(filterQuery);
            return Response.ok(list).build();
        } catch (Exception ex) {
            return Util.manageException(ex, PeligroREST.class);
        }
    }

    @GET
    @Path("{peligroId}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response find(@PathParam("peligroId") Integer peligroId) {
        try {
            Peligro peligro = peligroFacade.find(peligroId);
            return Response.ok(peligro).build();
        } catch (Exception ex) {
            return Util.manageException(ex, PeligroREST.class);
        }
    }

    @POST
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response create(Peligro peligro) {
        try {
            peligro = peligroFacade.create(peligro);
            return Response.ok(peligro).build();
        } catch (Exception ex) {
            return Util.manageException(ex, PeligroREST.class);
        }
    }

    @PUT
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response edit(Peligro peligro) {
        try {
            peligro = peligroFacade.edit(peligro);
            return Response.ok(peligro).build();
        } catch (Exception ex) {
            return Util.manageException(ex, PeligroREST.class);
        }
    }

    @DELETE
    @Path("{peligroId}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response remove(@PathParam("peligroId") Integer peligroId) {
        try {
            Peligro peligro = peligroFacade.find(peligroId);
            peligroFacade.remove(peligro);
            return Response.ok(peligro).build();
        } catch (Exception ex) {
            if (ex.getCause().getCause() instanceof PersistenceException) {
                Mensaje msg = new Mensaje();
                msg.setTipoMensaje(TipoMensaje.warn);
                msg.setMensaje("Eliminación no realizada");
                msg.setDetalle("No es posible eliminar el peligro ya que existen fuentes, efectos o controles enlazados a este peligro");
                return Response.status(Response.Status.BAD_REQUEST).entity(msg).build();
            } else {
                return Util.manageException(ex, PeligroREST.class);
            }
        }
    }

}
