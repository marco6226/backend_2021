/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.sigess.restful.rai;

import co.sigess.entities.com.Mensaje;
import co.sigess.entities.com.TipoMensaje;
import co.sigess.entities.emp.Empresa;
import co.sigess.entities.rai.Reporte;
import co.sigess.facade.rai.ReporteFacade;
import co.sigess.restful.ServiceREST;
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
        System.out.println("resporte");
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
            reporte.setUsuarioReporta(super.getUsuarioRequestContext());
            reporte.setEmpresa(new Empresa(super.getEmpresaIdRequestContext()));
            reporte = reporteFacade.edit(reporte);
            return Response.ok(reporte).build();
        } catch (Exception ex) {
            return Util.manageException(ex, ReporteREST.class);
        }
    }

}
