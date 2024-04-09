/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.sigess.restful.ipr;

import co.sigess.entities.com.Mensaje;
import co.sigess.entities.com.TipoMensaje;
import co.sigess.entities.emp.Empresa;
import co.sigess.entities.ipr.Ipecr;
import co.sigess.facade.ipr.IpecrFacade;
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
@Path("ipecr")
public class IpecrREST extends ServiceREST {

    @EJB
    private IpecrFacade ipecrFacade;

    public IpecrREST() {
        super(IpecrFacade.class);
    }
    
//    @GET
//    @Path("filter")
//    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
//    public Response findWithFilter(@BeanParam FilterQuery filterQuery) {
//        try {
//            List<Ipecr> list = ipecrFacade.findWithFilter(filterQuery);
//            return Response.ok(list).build();
//        } catch (Exception ex) {
//            return Util.manageException(ex, IpecrREST.class);
//        }
//    }

//    @GET
//    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
//    public Response findAll() {
//        try {
//            List<Ipecr> list = ipecrFacade.findAllByEmpresa(super.getEmpresaIdRequestContext());
//            return Response.ok(list).build();
//        } catch (Exception ex) {
//            return Util.manageException(ex, IpecrREST.class);
//        }
//    }

    @POST
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response create(Ipecr ipecr) {
        try {
            ipecr.setEmpresa(new Empresa(super.getEmpresaIdRequestContext()));
            ipecr.setUsuarioId(super.getUsuarioRequestContext().getId());
            ipecr = ipecrFacade.create(ipecr);
            return Response.ok(ipecr).build();
        } catch (Exception ex) {
            return Util.manageException(ex, IpecrREST.class);
        }
    }

    @PUT
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response edit(Ipecr ipecr) {
        try {
            if (ipecr.getId() == null) {
                throw new IllegalArgumentException("Debe establecer el campo id para realizar la actualización");
            }
            ipecr = ipecrFacade.edit(ipecr);
            return Response.ok(ipecr).build();
        } catch (Exception ex) {
            return Util.manageException(ex, IpecrREST.class);
        }
    }

    @DELETE
    @Path("{id}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response remove(@PathParam("id") Integer id) {
        try {
            Ipecr ipecr = ipecrFacade.find(id);
            ipecrFacade.remove(ipecr);
            return Response.ok(ipecr).build();
        } catch (Exception ex) {
            if (ex.getCause().getCause() instanceof PersistenceException) {
                Mensaje msg = new Mensaje();
                msg.setTipoMensaje(TipoMensaje.warn);
                msg.setMensaje("Eliminación no realizada");
                msg.setDetalle("No es posible eliminar el peligro ya que existen fuentes, efectos o controles enlazados a este peligro");
                return Response.status(Response.Status.BAD_REQUEST).entity(msg).build();
            } else {
                return Util.manageException(ex, IpecrREST.class);
            }
        }
    }

}
