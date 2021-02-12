/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.sigess.restful.scm;

import co.sigess.entities.aus.ReporteAusentismo;
import co.sigess.entities.scm.CasosMedicos;
import co.sigess.entities.scm.Recomendaciones;
import co.sigess.entities.scm.ScmLogs;
import co.sigess.entities.scm.Diagnosticos;

import co.sigess.facade.aus.ReporteAusentismoFacade;

import co.sigess.facade.scm.CasosMedicosFacade;
import co.sigess.facade.scm.RecomendacionesFacade;
import co.sigess.facade.scm.ScmLogsFacade;
import co.sigess.facade.scm.diagnosticoFacade;
import co.sigess.restful.Filter;
import co.sigess.restful.FilterQuery;
import co.sigess.restful.ServiceREST;
import co.sigess.restful.emp.EmpleadoREST;
import co.sigess.restful.rai.ReporteREST;
import co.sigess.util.Util;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.ejb.EJB;
import javax.persistence.Entity;
import javax.ws.rs.BeanParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.jsoup.nodes.Entities;

/**
 *
 * @author leonardo
 */
@Path("casomedico")
public class CasoMedicoREST extends ServiceREST {

    @EJB
    private CasosMedicosFacade casosmedicosFacade;
    
    @EJB
    private diagnosticoFacade diagnosticoFacade;
    
    
    @EJB
    private ReporteAusentismoFacade reporteAusentismoFacade;

    @EJB
    private ScmLogsFacade scmLogsFacade;

    @EJB
    private RecomendacionesFacade recomendacionesFacade;

    public CasoMedicoREST() {
        super(CasosMedicosFacade.class);

    }

    @GET
    @Path("all")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response list() {
        try {
            List list = this.casosmedicosFacade.findAll();
            return Response.ok(list).build();
        } catch (Exception ex) {
            return Util.manageException(ex, ReporteREST.class);
        }
    }

    @POST
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response create(CasosMedicos casosmedicos) {
        try {
            
            this.logScm("Creacion de caso", "" , casosmedicos.getPkUser(),casosmedicos.getClass().toString());
            casosmedicos = this.casosmedicosFacade.create(casosmedicos);
            return Response.ok(casosmedicos.getId()).build();
        } catch (Exception ex) {
            return Util.manageException(ex, ReporteREST.class);
        }
    }

    @PUT
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response edit(CasosMedicos casosmedicos) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            String json = mapper.writeValueAsString(casosmedicos);
            
            this.logScm("Edicion de caso medico", json , casosmedicos.getPkUser(), casosmedicos.getClass().toString());
            casosmedicos = this.casosmedicosFacade.update(casosmedicos);
            return Response.ok(casosmedicos.getId()).build();
        } catch (Exception ex) {
            return Util.manageException(ex, ReporteREST.class);
        }
    }

    @GET
    @Path("recomendation/{parametro}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response listReco(@PathParam("parametro") String parametro) {
        try {

            List<Recomendaciones> list = this.recomendacionesFacade.buscar(parametro);
            return Response.ok(list).build();
        } catch (Exception ex) {
            return Util.manageException(ex, ReporteREST.class);
        }
    }

    @POST
    @Path("recomendation")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response createReco(Recomendaciones recomendaciones) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            String json = mapper.writeValueAsString(recomendaciones);
            
            this.logScm("Creacion de recomendacion", json , recomendaciones.getPkUser().toString(),recomendaciones.getClass().toString());
            recomendaciones = this.recomendacionesFacade.create(recomendaciones);
         
            return Response.ok(recomendaciones.getId()).build();
        } catch (Exception ex) {
            return Util.manageException(ex, ReporteREST.class);
            }
    }

    
    @GET
    @Path("scmausentismo/{parametro}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response listAus(@PathParam("parametro") String documento) {
        try {
            System.out.println("Prueba");
            List<ReporteAusentismo> list = this.reporteAusentismoFacade.buscar(documento);
            return Response.ok(list).build();
        } catch (Exception ex) {
            return Util.manageException(ex, ReporteREST.class);
        }
    }
    
    
    @GET
    @Path("validate/{parametro}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response buscar(@PathParam("parametro") String parametro) {
        try {
            List<CasosMedicos> list = casosmedicosFacade.buscar(parametro);

            return Response.ok(list).build();

        } catch (Exception ex) {
            return Util.manageException(ex, EmpleadoREST.class);
        }
    }

    @GET
    @Path("logs/{parametro}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response getLogs(@PathParam("parametro") String parametro) {
          try {

            List<ScmLogs> list = this.scmLogsFacade.findAllById(parametro);
            return Response.ok(list).build();
        } catch (Exception ex) {
            return Util.manageException(ex, ReporteREST.class);
        }
    }
    
    
    private void logScm(String action , String json ,String documento,String entity){
        try {
            Calendar fechaActual = Calendar.getInstance();
            System.out.println(fechaActual.getTime());
            ScmLogs log = new ScmLogs();
            log.setAction(action);
            log.setPkUser(documento);
            log.setFecha_creacion(fechaActual.getTime());
            log.setEntity(entity);
            log.setJson(json);
            scmLogsFacade.create(log);
           
        } catch (Exception e) {

        }
          
    }
    
    @GET
    @Path("diagnosticos/{parametro}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response getDiagnosticos(@PathParam("parametro") String parametro) {
          try {

            List<Diagnosticos> list = this.diagnosticoFacade.findAllById(parametro);
            return Response.ok(list).build();
        } catch (Exception ex) {
            return Util.manageException(ex, ReporteREST.class);
        }
    }
    
    @POST
    @Path("diagnosticos")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response createDiag(Diagnosticos diagnosticos) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            String json = mapper.writeValueAsString(diagnosticos);
            
            this.logScm("Creacion de recomendacion", json , " ",diagnosticos.getClass().toString());
            diagnosticos = this.diagnosticoFacade.create(diagnosticos);
         
            return Response.ok(diagnosticos.getId()).build();
        } catch (Exception ex) {
            return Util.manageException(ex, ReporteREST.class);
            }
    }
    

   
}
