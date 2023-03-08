/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.sigess.restful.sec;

import co.sigess.entities.emp.Empresa;
import co.sigess.entities.sec.AnalisisDesviacion;
import co.sigess.facade.sec.AnalisisDesviacionFacade;
import co.sigess.restful.FilterQuery;
import co.sigess.restful.ServiceREST;
import co.sigess.restful.security.Secured;
import co.sigess.util.Util;
import java.util.Date;
import java.util.List;
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
@Path("analisisDesviacion")
public class AnalisisDesviacionREST extends ServiceREST {

    public AnalisisDesviacionREST() {
        super(AnalisisDesviacionFacade.class);
    }

    @Override
    public Response findWithFilter(FilterQuery filterQuery) {
        return super.findWithFilter(filterQuery);
    }

    @GET
    @Path("{analisisDesviacionId}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response find(@PathParam("analisisDesviacionId") Integer analisisDesviacionId) {
        try {
            AnalisisDesviacion analisisDesviacion = ((AnalisisDesviacionFacade) beanInstance).find(analisisDesviacionId);
            return Response.ok(analisisDesviacion).build();
        } catch (Exception ex) {
            return Util.manageException(ex, AnalisisDesviacionREST.class);
        }
    }

    @GET
    @Path("tarea/{tareaId}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response findByTarea(@PathParam("tareaId") Integer tareaId) {
        try {
            List<AnalisisDesviacion> list = ((AnalisisDesviacionFacade) beanInstance).findByTarea(tareaId, super.getEmpresaIdRequestContext());
            return Response.ok(list).build();
        } catch (Exception ex) {
            return Util.manageException(ex, AnalisisDesviacionREST.class);
        }
    }

    @POST
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response create(AnalisisDesviacion analisisDesviacion) {
        try {
            analisisDesviacion.setEmpresa(new Empresa(super.getEmpresaIdRequestContext()));
            analisisDesviacion.setFechaElaboracion(new Date());
            analisisDesviacion.setUsuarioElaboraId(super.getUsuarioRequestContext().getId());
            analisisDesviacion = ((AnalisisDesviacionFacade) beanInstance).create(analisisDesviacion);
            return Response.ok(analisisDesviacion).build();
        } catch (Exception ex) {
            return Util.manageException(ex, AnalisisDesviacionREST.class);
        }
    }

    @PUT
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response edit(AnalisisDesviacion analisisDesviacion) {
        try {
            analisisDesviacion.setEmpresa(new Empresa(super.getEmpresaIdRequestContext()));
            analisisDesviacion.setUsuarioModificaId(super.getUsuarioRequestContext().getId());
            analisisDesviacion = ((AnalisisDesviacionFacade) beanInstance).edit(analisisDesviacion);
            return Response.ok(analisisDesviacion).build();
        } catch (Exception ex) {
            return Util.manageException(ex, AnalisisDesviacionREST.class);
        }
    }
    
    @GET
    @Path("idanalisis/{idAnalisis}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response findReporteAlido(@PathParam("idAnalisis") Integer idAnalisis) {
        try {            
            List<AnalisisDesviacion> list = ((AnalisisDesviacionFacade) beanInstance).findReporteAlido(idAnalisis);
            return Response.ok(list).build();
        } catch (Exception ex) {
            return Util.manageException(ex, AnalisisDesviacionREST.class);
        }
    }

}
