/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.sigess.restful.ipr;

import co.sigess.entities.com.Mensaje;
import co.sigess.entities.com.TipoMensaje;
import co.sigess.entities.emp.Empresa;
import co.sigess.entities.ipr.TipoControl;
import co.sigess.facade.ipr.TipoControlFacade;
import co.sigess.restful.ServiceREST;
import co.sigess.restful.security.Secured;
import co.sigess.util.Util;
import java.util.List;
import javax.ejb.EJB;
import javax.persistence.PersistenceException;
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
@Path("tipoControl")
public class TipoControlREST extends ServiceREST {

    @EJB
    private TipoControlFacade tipoControlFacade;
    
    public TipoControlREST() {
        super(TipoControlFacade.class);
    }

//    @GET
//    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
//    public Response findAll() {
//        try {
//            List<TipoControl> list = tipoControlFacade.findAllByEmpresa(super.getEmpresaIdRequestContext());
//            return Response.ok(list).build();
//        } catch (Exception ex) {
//            return Util.manageException(ex, TipoControlREST.class);
//        }
//    }

    @GET
    @Path("{tipoControlId}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response find(@PathParam("tipoControlId") Integer tipoControlId) {
        try {
            TipoControl tipoControl = tipoControlFacade.find(tipoControlId);
            return Response.ok(tipoControl).build();
        } catch (Exception ex) {
            return Util.manageException(ex, TipoControlREST.class);
        }
    }

    @POST
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response create(TipoControl tipoControl) {
        try {
            tipoControl.setEmpresa(new Empresa(super.getEmpresaIdRequestContext()));
            tipoControl = tipoControlFacade.create(tipoControl);
            return Response.ok(tipoControl).build();
        } catch (Exception ex) {
            return Util.manageException(ex, TipoControlREST.class);
        }
    }

    @PUT
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response edit(TipoControl tipoControl) {
        try {
            tipoControl.setEmpresa(new Empresa(super.getEmpresaIdRequestContext()));
            tipoControl = tipoControlFacade.edit(tipoControl);
            return Response.ok(tipoControl).build();
        } catch (Exception ex) {
            return Util.manageException(ex, TipoControlREST.class);
        }
    }

    @DELETE
    @Path("{tipoControlId}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response remove(@PathParam("tipoControlId") Integer tipoControlId) {
        try {
            TipoControl tipoControl = tipoControlFacade.find(tipoControlId);
            tipoControlFacade.remove(tipoControl);
            return Response.ok(tipoControl).build();
        } catch (Exception ex) {
            if (ex.getCause().getCause() instanceof PersistenceException) {
                Mensaje msg = new Mensaje();
                msg.setTipoMensaje(TipoMensaje.warn);
                msg.setMensaje("Eliminación no realizada");
                msg.setDetalle("No es posible eliminar el tipo de control ya que existen controles de este tipo");
                return Response.status(Response.Status.BAD_REQUEST).entity(msg).build();
            } else {
                return Util.manageException(ex, TipoControlREST.class);
            }
        }
    }

}
