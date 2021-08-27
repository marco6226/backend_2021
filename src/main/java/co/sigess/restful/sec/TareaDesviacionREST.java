/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.sigess.restful.sec;

import co.sigess.entities.emp.Empresa;
import co.sigess.entities.sec.TareaDesviacion;
import co.sigess.facade.sec.TareaDesviacionFacade;
import co.sigess.restful.FilterQuery;
import co.sigess.restful.ServiceREST;
import co.sigess.restful.security.Secured;
import co.sigess.restful.security.ValidadorArea;
import co.sigess.util.Util;
import java.util.List;
import javax.ejb.EJB;
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
@Path("tarea")
public class TareaDesviacionREST extends ServiceREST {

    @EJB
    private TareaDesviacionFacade tareaDesviacionFacade;

    public TareaDesviacionREST() {
        super(TareaDesviacionFacade.class);
    }    

    @ValidadorArea("areaResponsable.id")
    @Override
    public Response findWithFilter(FilterQuery filterQuery) {
        return super.findWithFilter(filterQuery);
    }

    @POST
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response create(TareaDesviacion tarea) {
        try {
            tarea.setEmpresa(new Empresa(super.getEmpresaIdRequestContext()));
            tarea = tareaDesviacionFacade.create(tarea);
            return Response.ok(tarea).build();
        } catch (Exception ex) {
            return Util.manageException(ex, TareaDesviacionREST.class);
        }
    }
    
    @PUT
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response edit(TareaDesviacion tarea) {
        try {
            tarea.setEmpresa(new Empresa(super.getEmpresaIdRequestContext()));
            tarea = tareaDesviacionFacade.edit(tarea);
            return Response.ok(tarea).build();
        } catch (Exception ex) {
            return Util.manageException(ex, TareaDesviacionREST.class);
        }
    }


    @PUT
    @Secured(validarPermiso = false)
    @Path("close")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_JSON})
    public Response closeTask(TareaDesviacion tarea) {
        try {
            tarea.setEmpresa(new Empresa(super.getEmpresaIdRequestContext()));
            tarea = tareaDesviacionFacade.closeTask(tarea);
            return Response.ok(tarea).build();
        } catch (Exception ex) {
            return Util.manageException(ex, TareaDesviacionREST.class);
        }
    }    

    @GET
    @Secured(validarPermiso = false)
    @Path("{tareaId}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response find(@PathParam("tareaId") Integer tareaId) {
        try {
            TareaDesviacion tareaDesviacion = ((TareaDesviacionFacade) beanInstance).find(tareaId);
            return Response.ok(tareaDesviacion).build();
        } catch (Exception ex) {
            return Util.manageException(ex, AnalisisDesviacionREST.class);
        }
    }
    

    @PUT
    @Path("reportarCumplimiento")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response reportarCumplimiento(TareaDesviacion tarea) {
        try {
            tarea = tareaDesviacionFacade.reportarCumplimiento(tarea, super.getUsuarioRequestContext());
            return Response.ok(tarea).build();
        } catch (Exception ex) {
            return Util.manageException(ex, TareaDesviacionREST.class);
        }
    }

    @PUT
    @Path("reportarVerificacion")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response reportarVerificacion(TareaDesviacion tarea) {
        try {
            tarea = tareaDesviacionFacade.reportarVerificacion(tarea, super.getUsuarioRequestContext());
            return Response.ok(tarea).build();
        } catch (Exception ex) {
            return Util.manageException(ex, TareaDesviacionREST.class);
        }
    }

    @GET
    @Path("analisis/{analisisId}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response findByAnalisis(@PathParam("analisisId") Integer analisisId) {
        try {
            List<TareaDesviacion> list = tareaDesviacionFacade.findByAnalisisEmpresa(analisisId, super.getEmpresaIdRequestContext());
            return Response.ok(list).build();
        } catch (Exception ex) {
            return Util.manageException(ex, TareaDesviacionREST.class);
        }
    }

        

    @GET
    @Secured(validarPermiso = false)
    @Path("detail/{tareaId}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response findByDetailId(@PathParam("tareaId") Integer tareaId) {
        try {
           
            String json = tareaDesviacionFacade.findById(tareaId);
            return Response.ok(json).build();
        } catch (Exception ex) {
            return Util.manageException(ex, TareaDesviacionREST.class);
        }
    }
    
    @GET
    @Path("usuario/{usuarioId}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response findByUsuario(@PathParam("usuarioId") Integer usuarioId) {
        try {
            if (!super.getUsuarioRequestContext().getId().equals(usuarioId)) {
                // Bloquear usuario
            }
            List<TareaDesviacion> list = tareaDesviacionFacade.findByUsuarioEmpresa(usuarioId, super.getEmpresaIdRequestContext());
            return Response.ok(list).build();
        } catch (Exception ex) {
            return Util.manageException(ex, TareaDesviacionREST.class);
        }
    }

    @DELETE
    @Path("{tareaDesviacionId}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response delete(@PathParam("tareaDesviacionId") Integer tareaDesviacionId) {
        try {
            TareaDesviacion tarea = tareaDesviacionFacade.remove(tareaDesviacionId);
            return Response.ok(tarea).build();
        } catch (Exception ex) {
            return Util.manageException(ex, TareaDesviacionREST.class);
        }
    }

}
