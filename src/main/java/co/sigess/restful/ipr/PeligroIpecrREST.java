/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.sigess.restful.ipr;

import co.sigess.entities.com.Mensaje;
import co.sigess.entities.com.TipoMensaje;
import co.sigess.entities.ipr.PeligroIpecr;
import co.sigess.facade.ipr.IpecrFacade;
import co.sigess.facade.ipr.PeligroFacade;
import co.sigess.facade.ipr.PeligroIpecrFacade;
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
@Path("peligroIpecr")
public class PeligroIpecrREST extends ServiceREST {

    @EJB
    private PeligroIpecrFacade peligroIpecrFacade;

    @GET
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response findWithFilter(@BeanParam FilterQuery filterQuery) {
        try {            
            List<PeligroIpecr> list = peligroIpecrFacade.findWithFilter(filterQuery);
            return Response.ok(list).build();
        } catch (Exception ex) {
            return Util.manageException(ex, PeligroIpecrREST.class);
        }
    }

    @POST
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response create(PeligroIpecr ipecr) {
        try {
            ipecr = peligroIpecrFacade.create(ipecr);
            return Response.ok(ipecr).build();
        } catch (Exception ex) {
            return Util.manageException(ex, PeligroIpecrREST.class);
        }
    }

    @PUT
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response edit(PeligroIpecr peligroIpecr) {
        try {
            if(peligroIpecr.getId() == null){
                throw new IllegalArgumentException("Debe especificar el identificador del peligro para actualizarlo");
            }
            peligroIpecr = peligroIpecrFacade.edit(peligroIpecr);
            return Response.ok(peligroIpecr).build();
        } catch (Exception ex) {
            return Util.manageException(ex, PeligroIpecrREST.class);
        }
    }

    @DELETE
    @Path("{peligroIpecrId}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response remove(@PathParam("peligroIpecrId") Long peligroIpecrId) {
        try {
            PeligroIpecr peligroIpecr = peligroIpecrFacade.find(peligroIpecrId);
            peligroIpecrFacade.remove(peligroIpecr);
            return Response.ok(peligroIpecr).build();
        } catch (Exception ex) {
            if (ex.getCause().getCause() instanceof PersistenceException) {
                Mensaje msg = new Mensaje();
                msg.setTipoMensaje(TipoMensaje.warn);
                msg.setMensaje("Eliminación no realizada");
                msg.setDetalle("No es posible eliminar el peligro ya que existen fuentes, efectos o controles enlazados a este peligro");
                return Response.status(Response.Status.BAD_REQUEST).entity(msg).build();
            } else {
                return Util.manageException(ex, PeligroIpecrREST.class);
            }
        }
    }

}
