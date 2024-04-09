/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.sigess.restful.ipr;

import co.sigess.entities.com.Mensaje;
import co.sigess.entities.com.TipoMensaje;
import co.sigess.entities.ipr.ParticipanteIpecr;
import co.sigess.facade.ipr.ParticipanteIpecrFacade;
import co.sigess.restful.Filter;
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
@Path("participanteIpecr")
public class ParticipanteIpecrREST extends ServiceREST {

    @EJB
    private ParticipanteIpecrFacade participanteIpecrFacade;

    @GET
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response findWithFilter(@BeanParam FilterQuery filterQuery) {
        try {
            boolean filtradoEmpresa = false;
            for (Filter filter : filterQuery.getFilterList()) {
                if (filter.getField().equals("ipecr.empresa.id")) {
                    filtradoEmpresa = true;
                    break;
                }
            }
            if (!filtradoEmpresa) {
                Filter empFilt = new Filter();
                empFilt.setCriteria("eq");
                empFilt.setField("ipecr.empresa.id");
                empFilt.setValue1(super.getEmpresaIdRequestContext().toString());
                filterQuery.getFilterList().add(empFilt);
            }
            List<ParticipanteIpecr> list = participanteIpecrFacade.findWithFilter(filterQuery);
            return Response.ok(list).build();
        } catch (Exception ex) {
            return Util.manageException(ex, ParticipanteIpecrREST.class);
        }
    }

    @POST
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response create(ParticipanteIpecr partipecr) {
        try {
            partipecr = participanteIpecrFacade.create(partipecr);
            return Response.ok(partipecr).build();
        } catch (Exception ex) {
            return Util.manageException(ex, ParticipanteIpecrREST.class);
        }
    }

    @PUT
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response edit(ParticipanteIpecr partIpecr) {
        try {
            if (partIpecr.getId() == null) {
                throw new IllegalArgumentException("Debe establecer el campo id para realizar la actualización");
            }
            partIpecr = participanteIpecrFacade.edit(partIpecr);
            return Response.ok(partIpecr).build();
        } catch (Exception ex) {
            return Util.manageException(ex, ParticipanteIpecrREST.class);
        }
    }

    @DELETE
    @Path("{participanteIpecrId}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response remove(@PathParam("participanteIpecrId") Integer participanteIpecrId) {
        try {
            ParticipanteIpecr partIpecr = participanteIpecrFacade.find(participanteIpecrId);
            participanteIpecrFacade.remove(partIpecr);
            return Response.ok(partIpecr).build();
        } catch (Exception ex) {
            if (ex.getCause().getCause() instanceof PersistenceException) {
                Mensaje msg = new Mensaje();
                msg.setTipoMensaje(TipoMensaje.warn);
                msg.setMensaje("Eliminación no realizada");
                msg.setDetalle("No es posible eliminar el peligro ya que existen fuentes, efectos o controles enlazados a este peligro");
                return Response.status(Response.Status.BAD_REQUEST).entity(msg).build();
            } else {
                return Util.manageException(ex, ParticipanteIpecrREST.class);
            }
        }
    }

}
