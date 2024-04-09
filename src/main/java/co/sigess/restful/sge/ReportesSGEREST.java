/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.sigess.restful.sge;

import co.sigess.facade.sge.ReporteSGEFacade;
import co.sigess.restful.ServiceREST;
import co.sigess.restful.security.Secured;
import co.sigess.util.Util;
import java.io.File;
import javax.ejb.EJB;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 *
 * @author fmoreno
 */
@Secured
@Path("reportesSGE")
public class ReportesSGEREST extends ServiceREST{

    @EJB
    private ReporteSGEFacade reporteSGEFacade;

    @GET
    @Path("evaluacion/{evaluacionId}")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_OCTET_STREAM})
    public Response find(@PathParam("evaluacionId") Integer evaluacionId, @QueryParam("type") String type) {
        try {
            long ini = System.currentTimeMillis();
            File reporte = reporteSGEFacade.generarReporteEvaluacion(evaluacionId, type);

            String nombreReporte = reporte.getName();
            String extension = nombreReporte.substring(nombreReporte.lastIndexOf(".") + 1, nombreReporte.length());

            System.out.println("generado en: " + (System.currentTimeMillis() - ini) + "mS");
            Response.ResponseBuilder resp = Response.ok(reporte, MediaType.APPLICATION_OCTET_STREAM);
            resp.type("application/" + extension + ";");
            resp.header("Content-Disposition", "atachment;filename=\"" + reporte.getName() + "\"");
            return resp.build();

        } catch (Exception ex) {
            return Util.manageException(ex, ReportesSGEREST.class);
        }
    }

}
