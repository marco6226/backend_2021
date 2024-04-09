/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.sigess.restful.ipr;

import co.sigess.entities.com.Mensaje;
import co.sigess.entities.com.TipoMensaje;
import co.sigess.entities.emp.Empresa;
import co.sigess.entities.ipr.TipoPeligro;
import co.sigess.facade.ipr.TipoPeligroFacade;
import co.sigess.restful.ServiceREST;
import co.sigess.restful.security.Secured;
import co.sigess.util.Util;
import java.util.List;
import javax.ejb.EJB;
import javax.persistence.PersistenceException;
import javax.persistence.Query;
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
@Path("tipoPeligro")
public class TipoPeligroREST extends ServiceREST {

    @EJB
    private TipoPeligroFacade tipoPeligroFacade;

    public TipoPeligroREST() {
        super(TipoPeligroFacade.class);
    }
    
//    @GET
//    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
//    public Response findAll() {
//        try {
//            List<TipoPeligro> list = tipoPeligroFacade.findAllByEmpresa(super.getEmpresaIdRequestContext());
//            return Response.ok(list).build();
//        } catch (Exception ex) {
//            return Util.manageException(ex, TipoPeligroREST.class);
//        }
//    }
   
    @GET
    @Path("empresaId")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response findEmpresa(@PathParam("empresaId") Integer empresaId) {
        try {            
            List<TipoPeligro> list = tipoPeligroFacade.findForEmp(getEmpresaIdRequestContext());
            return Response.ok(list).build();
        } catch (Exception ex) {
            return Util.manageException(ex, TipoPeligroREST.class);
        }
    }
    
    @GET
    @Path("{tipoPeligroId}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response find(@PathParam("tipoPeligroId") Long tipoPeligroId) {
        try {
            TipoPeligro tipoPeligro = tipoPeligroFacade.find(tipoPeligroId);
            return Response.ok(tipoPeligro).build();
        } catch (Exception ex) {
            return Util.manageException(ex, TipoPeligroREST.class);
        }
    }

    @POST
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response create(TipoPeligro tipoPeligro) {
        try {
            tipoPeligro.setEmpresa(new Empresa(super.getEmpresaIdRequestContext()));
            tipoPeligro = tipoPeligroFacade.create(tipoPeligro);
            return Response.ok(tipoPeligro).build();
        } catch (Exception ex) {
            return Util.manageException(ex, TipoPeligroREST.class);
        }
    }

    @PUT
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response edit(TipoPeligro tipoPeligro) {
        try {
            tipoPeligro.setEmpresa(new Empresa(super.getEmpresaIdRequestContext()));
            tipoPeligro = tipoPeligroFacade.edit(tipoPeligro);
            return Response.ok(tipoPeligro).build();
        } catch (Exception ex) {
            return Util.manageException(ex, TipoPeligroREST.class);
        }
    }

    @DELETE
    @Path("{tipoPeligroId}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response remove(@PathParam("tipoPeligroId") Integer tipoPeligroId) {
        try {
            TipoPeligro tipoPeligro = tipoPeligroFacade.find(tipoPeligroId);
            tipoPeligroFacade.remove(tipoPeligro);
            return Response.ok(tipoPeligro).build();
        } catch (Exception ex) {
            if (ex.getCause().getCause() instanceof PersistenceException) {
                Mensaje msg = new Mensaje();
                msg.setTipoMensaje(TipoMensaje.warn);
                msg.setMensaje("Eliminación no realizada");
                msg.setDetalle("No es posible eliminar el tipo peligro ya que existen peligros de este tipo");
                return Response.status(Response.Status.BAD_REQUEST).entity(msg).build();
            } else {
                return Util.manageException(ex, TipoPeligroREST.class);
            }
        }
    }

}
