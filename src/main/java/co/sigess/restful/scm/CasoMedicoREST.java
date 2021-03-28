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
import co.sigess.entities.scm.SeguimientoCaso;
import co.sigess.entities.scm.SistemaAfectado;
import co.sigess.entities.scm.Sve;

import co.sigess.facade.aus.ReporteAusentismoFacade;

import co.sigess.facade.scm.CasosMedicosFacade;
import co.sigess.facade.scm.RecomendacionesFacade;
import co.sigess.facade.scm.ScmLogsFacade;
import co.sigess.facade.scm.SeguimientoCasoFacade;
import co.sigess.facade.scm.SistemaAfectadoFacade;
import co.sigess.facade.scm.SveFacade;
import co.sigess.facade.scm.diagnosticoFacade;
import co.sigess.restful.Filter;
import co.sigess.restful.FilterQuery;
import co.sigess.restful.FilterResponse;
import co.sigess.restful.ServiceREST;
import co.sigess.restful.emp.EmpleadoREST;
import co.sigess.restful.rai.ReporteREST;
import co.sigess.util.Util;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Calendar;
import java.util.List;
import javax.ejb.EJB;
import javax.ws.rs.BeanParam;
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
 * @author leonardo
 */
@Path("casomedico")
public class CasoMedicoREST extends ServiceREST {

    @EJB
    private SistemaAfectadoFacade sistemaAfectadoFacade;
    
    @EJB
    private SveFacade sve;

    @EJB
    private CasosMedicosFacade casosmedicosFacade;

    @EJB
    private diagnosticoFacade diagnosticoFacade;

    @EJB
    private SeguimientoCasoFacade seguimientoFacade;

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

            casosmedicos = this.casosmedicosFacade.create(casosmedicos);
            this.logScm("Creacion de caso", "", casosmedicos.getId().toString(), casosmedicos.getClass().toString());

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

            this.logScm("Edicion de caso medico", json, casosmedicos.getId().toString(), casosmedicos.getClass().toString());
            casosmedicos = this.casosmedicosFacade.update(casosmedicos);
            return Response.ok(casosmedicos.getId()).build();
        } catch (Exception ex) {
            return Util.manageException(ex, ReporteREST.class);
        }
    }

    @GET
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    @Override
    public Response findWithFilter(@BeanParam FilterQuery filterQuery) {
        try {

            long numRows = filterQuery.isCount() ? casosmedicosFacade.countWithFilter(filterQuery) : -1;

            List list = casosmedicosFacade.findWithFilter(filterQuery);
            FilterResponse filterResponse = new FilterResponse();
            filterResponse.setData(list);
            filterResponse.setCount(numRows);
            return Response.ok(filterResponse).build();
        } catch (Exception ex) {
            return Util.manageException(ex, EmpleadoREST.class);
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

            this.logScm("Creacion de recomendacion", json, recomendaciones.getPkCase().toString(), recomendaciones.getClass().toString());
            recomendaciones = this.recomendacionesFacade.create(recomendaciones);

            return Response.ok(recomendaciones.getId()).build();
        } catch (Exception ex) {
            return Util.manageException(ex, ReporteREST.class);
        }
    }

    @PUT
    @Path("recomendation")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response editSeg(Recomendaciones recomendaciones) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            String json = mapper.writeValueAsString(recomendaciones);

            this.logScm("Se edito una recomendacion", json, recomendaciones.getPkCase().toString(), recomendaciones.getClass().toString());
            recomendaciones = this.recomendacionesFacade.update(recomendaciones);
            return Response.ok(recomendaciones).build();
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

            this.logScm("Creacion de Diagnostico", json, diagnosticos.getPkCase(), diagnosticos.getClass().toString());
            diagnosticos = this.diagnosticoFacade.create(diagnosticos);

            return Response.ok(diagnosticos.getId()).build();
        } catch (Exception ex) {
            return Util.manageException(ex, ReporteREST.class);
        }
    }

    @GET
    @Path("sistemaafectado")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response getSistemasAfectados() {
        try {

            List<SistemaAfectado> list = this.sistemaAfectadoFacade.findAll();
            return Response.ok(list).build();
        } catch (Exception ex) {
            return Util.manageException(ex, ReporteREST.class);
        }
    }
    
    @GET
    @Path("svelist")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response getSve() {
        try {

            List<Sve> list = this.sve.findAll();
            return Response.ok(list).build();
        } catch (Exception ex) {
            return Util.manageException(ex, ReporteREST.class);
        }
    }
    
    @POST
    @Path("seguimiento")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response createSeg(SeguimientoCaso seguimientoCaso) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            String json = mapper.writeValueAsString(seguimientoCaso);

            this.logScm("Creacion de Seguimiento", json, seguimientoCaso.getPkCase(), seguimientoCaso.getClass().toString());
            seguimientoCaso = this.seguimientoFacade.create(seguimientoCaso);

            return Response.ok(seguimientoCaso).build();
        } catch (Exception ex) {
            return Util.manageException(ex, ReporteREST.class);
        }
    }

    @GET
    @Path("seguimiento/{parametro}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response getSeguimiento(@PathParam("parametro") String parametro) {
        try {

            List<SeguimientoCaso> list = this.seguimientoFacade.buscar(parametro);
            return Response.ok(list).build();
        } catch (Exception ex) {
            return Util.manageException(ex, ReporteREST.class);
        }
    }

    @PUT
    @Path("seguimiento")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response editSeg(SeguimientoCaso seguimientoCaso) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            String json = mapper.writeValueAsString(seguimientoCaso);

            this.logScm("Se edito un seguimiento", json, seguimientoCaso.getPkCase(), seguimientoCaso.getClass().toString());
            seguimientoCaso = this.seguimientoFacade.update(seguimientoCaso);
            return Response.ok(seguimientoCaso).build();
        } catch (Exception ex) {
            return Util.manageException(ex, ReporteREST.class);
        }
    }

    private void logScm(String action, String json, String documento, String entity) {
        try {
            Calendar fechaActual = Calendar.getInstance();
            System.out.println(fechaActual.getTime());
            ScmLogs log = new ScmLogs();
            log.setAction(action);
            log.setPkCase(documento);
            log.setFecha_creacion(fechaActual.getTime());
            log.setEntity(entity);
            log.setJson(json);
            scmLogsFacade.create(log);

        } catch (Exception e) {

        }

    }
}
