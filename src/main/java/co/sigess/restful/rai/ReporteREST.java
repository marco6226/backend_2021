/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.sigess.restful.rai;

import co.sigess.entities.com.Mensaje;
import co.sigess.entities.com.TipoMensaje;
import co.sigess.entities.emp.Empleado;
import co.sigess.entities.emp.Empresa;
import co.sigess.entities.ipr.TipoPeligro;
import co.sigess.entities.rai.Reporte;
import co.sigess.facade.rai.ReporteFacade;
import co.sigess.restful.FilterQuery;
import co.sigess.restful.FilterResponse;
import co.sigess.restful.ServiceREST;
import co.sigess.restful.ipr.TipoPeligroREST;
import co.sigess.restful.security.Auditable;
import co.sigess.restful.security.AuthorizationFacade;
import co.sigess.restful.security.Secured;
import co.sigess.util.Util;
import java.io.File;
import java.io.InputStream;
import java.io.FileInputStream;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.persistence.Query;
import javax.ws.rs.BeanParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
import org.glassfish.jersey.media.multipart.FormDataParam;

/**
 *
 * @author fmoreno
 */
@Secured
@Path("reporte")
public class ReporteREST extends ServiceREST {

    @EJB
    private ReporteFacade reporteFacade;

    public ReporteREST() {
        super(ReporteFacade.class);
    }

    @GET
    @Path("empresaId")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response findEmpresaTemporal() {
        try {            
            List<Reporte> reporte = reporteFacade.findForEmpTemporal(getEmpresaIdRequestContext());
            return Response.ok(reporte).build();
        } catch (Exception ex) {
            return Util.manageException(ex, ReporteREST.class);
        }
    }
    
    @GET
    @Path("reportesTemporal")
    @Secured(requiereEmpresaId = false)
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response findRepWithFilter(@BeanParam FilterQuery filterQuery){
        try {
            if(filterQuery == null){
                filterQuery = new FilterQuery();
            }
            long numRows = filterQuery.isCount() ? reporteFacade.countWithFilter(filterQuery) : -1;
            List list = reporteFacade.findWithFilter(filterQuery);
            
            FilterResponse filterResponse = new FilterResponse();
            filterResponse.setData(list);
            filterResponse.setCount(numRows);
            return Response.ok(filterResponse).build();
        } catch (Exception e) {
            return Util.manageException(e, ReporteREST.class);
        }
    }
    
    @GET
    @Path("{reporteId}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response findReporteAlido(@PathParam("reporteId") Integer reporteId) {
        try {            
            List<Reporte> reporte = reporteFacade.findReporteAlido(reporteId);
            return Response.ok(reporte).build();
        } catch (Exception ex) {
            return Util.manageException(ex, ReporteREST.class);
        }
    }
    
    @GET
    @Path("inicializarReporte/{empleadoId}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response inicializarReporte(@PathParam("empleadoId") Integer empleadoId) {
        try {
            Reporte reporte = reporteFacade.inicializarReporte(empleadoId, super.getEmpresaIdRequestContext(), super.getUsuarioRequestContext().getId());
            return Response.ok(reporte).build();
        } catch (Exception ex) {
            return Util.manageException(ex, ReporteREST.class);
        }
    }

    @POST
    @Path("cargarArchivo")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response cargarArchivo(
            @FormDataParam("file") InputStream fileInputStream,
            @FormDataParam("file") FormDataContentDisposition fileMetaData,
            @FormDataParam("tipoReporte") String tipoReporte
            
    ) {
        try {
            
                
            reporteFacade.cargarArchivo(fileInputStream, tipoReporte, super.getEmpresaIdRequestContext(), super.getUsuarioRequestContext().getId());
            return Response.ok().build();
        } catch (Exception ex) {
            return Util.manageException(ex, ReporteREST.class);
        }
    }

    @POST
    @Auditable
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response create(Reporte reporte) {
        try {
            reporte.setUsuarioReporta(super.getUsuarioRequestContext());
            reporte.setEmpresa(new Empresa(super.getEmpresaIdRequestContext()));
            reporte = reporteFacade.create(reporte);
            return Response.ok(reporte.getId()).build();
        } catch (Exception ex) {
            return Util.manageException(ex, ReporteREST.class);
        }
    }

    @PUT
    @Auditable
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response edit(Reporte reporte) {
        Mensaje msg = new Mensaje("USUARIO BLOQUEADO", "El usuario "  + " ha sido bloqueado", TipoMensaje.error, Mensaje.COD_USUARIO_BLOQUEADO);
        Logger.getLogger(AuthorizationFacade.class.getName()).log(Level.INFO, msg.toString());
        try {
            Reporte reporteAux = reporteFacade.find(reporte.getId());
            if(reporteAux.getEmpresa().getId() == super.getEmpresaIdRequestContext()){
                reporte.setUsuarioReporta(super.getUsuarioRequestContext());
                reporte.setEmpresa(new Empresa(super.getEmpresaIdRequestContext()));
            } else {
                reporte.setEmpresa(reporteAux.getEmpresa());
                reporte.setUsuarioReporta(reporteAux.getUsuarioReporta());
            }
            reporte = reporteFacade.edit(reporte);
            return Response.ok(reporte).build();
        } catch (Exception ex) {
            return Util.manageException(ex, ReporteREST.class);
        }
    }
    
    @GET
    @Path("buscar/{parametro}")
    @Secured(validarPermiso = false)
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response buscar(@PathParam("parametro") String parametro) {
        try {
            List<Reporte> list = reporteFacade.buscar(parametro, super.getEmpresaIdRequestContext());
            return Response.ok(list).build();
        } catch (Exception ex) {
            return Util.manageException(ex, ReporteREST.class);
        }
    }
    


}
