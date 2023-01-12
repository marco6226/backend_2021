/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.sigess.restful.scm;

import co.sigess.entities.aus.ReporteAusentismo;
import co.sigess.entities.com.Mensaje;
import co.sigess.entities.com.TipoMensaje;
import co.sigess.entities.emp.Empresa;
import co.sigess.entities.scm.CasosMedicos;
import co.sigess.entities.scm.Recomendaciones;
import co.sigess.entities.scm.ScmLogs;
import co.sigess.entities.scm.Diagnosticos;
import co.sigess.entities.scm.Pcl;
import co.sigess.entities.scm.Reintegro;
import co.sigess.entities.scm.SeguimientoCaso;
import co.sigess.entities.scm.SistemaAfectado;
import co.sigess.entities.scm.Sve;
import co.sigess.entities.scm.Tratamientos;
import co.sigess.exceptions.UserMessageException;
import co.sigess.facade.aus.ReporteAusentismoFacade;
import co.sigess.facade.core.LoaderFacade;
import co.sigess.facade.core.SMSFacade;
import co.sigess.facade.scm.CasosMedicosFacade;
import co.sigess.facade.scm.PclFacade;
import co.sigess.facade.scm.RecomendacionesFacade;
import co.sigess.facade.scm.ReintegroFacade;
import co.sigess.facade.scm.ScmLogsFacade;
import co.sigess.facade.scm.SeguimientoCasoFacade;
import co.sigess.facade.scm.SistemaAfectadoFacade;
import co.sigess.facade.scm.SveFacade;
import co.sigess.facade.scm.diagnosticoFacade;
import co.sigess.facade.scm.tratamientosFacade;
import co.sigess.restful.Filter;
import co.sigess.restful.FilterQuery;
import co.sigess.restful.FilterResponse;
import co.sigess.restful.ServiceREST;
import co.sigess.restful.emp.EmpleadoREST;
import co.sigess.restful.rai.ReporteREST;
import co.sigess.restful.security.Secured;
import co.sigess.util.Util;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Calendar;
import java.util.List;
import java.util.Properties;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.ws.rs.BeanParam;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.PATCH;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import kong.unirest.HttpResponse;
import kong.unirest.JsonNode;
import kong.unirest.Unirest;
import kong.unirest.UnirestException;

/**
 *
 * @author leonardo
 */
@Secured
@Path("casomedico")
public class CasoMedicoREST extends ServiceREST {

    @PersistenceContext(unitName = "SIGESS_PU")
    private EntityManager em;
    
    public final static String EMAIL_AON = "email_aon";
    public final static String PASS_AON = "pass_aon";
    @EJB
    private SistemaAfectadoFacade sistemaAfectadoFacade;

    @EJB
    private LoaderFacade loaderFacade;

    @EJB
    private SveFacade sve;

    @EJB
    private PclFacade pclFacade;

    @EJB
    private CasosMedicosFacade casosmedicosFacade;

    @EJB
    private tratamientosFacade tratamientoFacade;

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

    @EJB
    private ReintegroFacade reintegroFacade;
    

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
            
            ObjectMapper mapper = new ObjectMapper();
            String json = mapper.writeValueAsString(casosmedicos);

            casosmedicos.setEmpresa(new Empresa(super.getEmpresaIdRequestContext()));
            casosmedicos.setStatusCaso("1");
            casosmedicos = this.casosmedicosFacade.create(casosmedicos);
            this.logScm("Creacion de caso", json, casosmedicos.getId().toString(), casosmedicos.getClass().toString());

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
            casosmedicos.setEmpresa(new Empresa(super.getEmpresaIdRequestContext()));

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
        Query q1 = this.em.createNativeQuery("SELECT scm.tiporetorno_cm()");
        q1.getResultList();
        try {
            boolean filtradoEmpresa = false;

            filtradoEmpresa = filterQuery.getFilterList().stream().anyMatch(find -> find.getField().equals("empresa.id"));

            for (Filter filter : filterQuery.getFilterList()) {
                if (filter.getField().equals("empresa.id")) {
                    filtradoEmpresa = true;
                }
            }

            if (!filtradoEmpresa) {
                Filter empFilt = new Filter();
                empFilt.setCriteria("eq");
                empFilt.setField("empresa.id");
                empFilt.setValue1(super.getEmpresaIdRequestContext().toString());
                filterQuery.getFilterList().add(empFilt);
            }
            
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
            recomendaciones.setEliminado(false);

            this.logScm("Creacion de recomendacion", json, recomendaciones.getPkCase().toString(), recomendaciones.getClass().toString());
            recomendaciones = this.recomendacionesFacade.crear(recomendaciones, super.getEmpresaIdRequestContext());

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
            recomendaciones.setEliminado(false);

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
    @Path("case/{id}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response buscarCaso(@PathParam("id") String id) {
        try {

            CasosMedicos list = casosmedicosFacade.find(Integer.parseInt(id));
            return Response.ok(list).build();

        } catch (Exception ex) {
            return Util.manageException(ex, EmpleadoREST.class);
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
            diagnosticos.setCreadoPor(super.getUsuarioRequestContext().getEmail());

            ObjectMapper mapper = new ObjectMapper();
            String json = mapper.writeValueAsString(diagnosticos);

            this.logScm("Creacion de Diagnostico", json, diagnosticos.getPkCase(), diagnosticos.getClass().toString());
            diagnosticos = this.diagnosticoFacade.create(diagnosticos);

            Query q1 = this.em.createNativeQuery("SELECT scm.diagnostico_cm()");
            q1.getResultList();
            return Response.ok(diagnosticos.getId()).build();
        } catch (Exception ex) {
            return Util.manageException(ex, ReporteREST.class);
        }
    }

    @PUT
    @Path("diagnosticos")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response editDiag(Diagnosticos diagnosticos) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            String json = mapper.writeValueAsString(diagnosticos);

            this.logScm("Se edito un diagnostico", json, diagnosticos.getPkCase().toString(), diagnosticos.getClass().toString());
            diagnosticos = this.diagnosticoFacade.update(diagnosticos);
            
            Query q1 = this.em.createNativeQuery("SELECT scm.diagnostico_cm()");
            q1.getResultList();
            return Response.ok(diagnosticos).build();
        } catch (Exception ex) {
            return Util.manageException(ex, ReporteREST.class);
        }
    }

    @Secured(validarPermiso = false)
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

    @Secured(validarPermiso = false)
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
            seguimientoCaso.setEliminado(false);
            seguimientoCaso = this.seguimientoFacade.create(seguimientoCaso);
            String json = mapper.writeValueAsString(seguimientoCaso);
            this.logScm("Creacion de Seguimiento", json, seguimientoCaso.getPkCase(), seguimientoCaso.getClass().toString());

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

    @PUT
    @Path("seguimiento/{id}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response deleteSeg(@PathParam("id") String id) {
        try {
            System.out.print(id);
            String pk_case = seguimientoFacade.findById(id).get(0).getPkCase();
            this.logScm("Se Elimino un seguimiento", null, pk_case, "Seguimiento");
            
            int seg = this.seguimientoFacade.eliminar(Long.parseLong(id));
            return Response.ok(seg).build();
        } catch (Exception ex) {
            return Util.manageException(ex, ReporteREST.class);
        }
    }

    @PUT
    @Path("diagnosticos/{id}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response editDiag(@PathParam("id") String id) {
        try {

            this.logScm("Se borro un diagnostico ", null, id, "Reomendacion");
            int diag = this.diagnosticoFacade.eliminar(Long.parseLong(id));
            return Response.ok(diag).build();
        } catch (Exception ex) {
            return Util.manageException(ex, ReporteREST.class);
        }
    }

    @PUT
    @Path("recomendation/{id}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response deleteReco(@PathParam("id") String id) {
        try {

            this.logScm("Se Elimino una recomendacion", null, id, "Reomendacion");

            int seg = this.recomendacionesFacade.eliminar(Long.parseLong(id));
            return Response.ok(seg).build();
        } catch (Exception ex) {
            return Util.manageException(ex, ReporteREST.class);
        }
    }

    @POST
    @Path("tratamiento")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response createTrat(Tratamientos tratamiento) {
        try {

            ObjectMapper mapper = new ObjectMapper();
            tratamiento.setEliminado(false);
            tratamiento = this.tratamientoFacade.create(tratamiento);

            String json = mapper.writeValueAsString(tratamiento);
            this.logScm("Creacion de tratamiento", json, tratamiento.getPkCase().toString(), tratamiento.getClass().toString());

            return Response.ok(tratamiento).build();
        } catch (Exception ex) {
            return Util.manageException(ex, ReporteREST.class);
        }
    }

    @PUT
    @Path("tratamiento")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response editTrat(Tratamientos tratamiento) {
        try {

            ObjectMapper mapper = new ObjectMapper();
            String json = mapper.writeValueAsString(tratamiento);

            this.logScm("Se edito un tratamiento", json, tratamiento.getPkCase().toString(), tratamiento.getClass().toString());
            tratamiento = this.tratamientoFacade.update(tratamiento);
            return Response.ok(tratamiento).build();
        } catch (Exception ex) {
            return Util.manageException(ex, ReporteREST.class);
        }
    }

    @GET
    @Path("tratamiento/{parametro}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response getTratamiento(@PathParam("parametro") String parametro) {
        try {
            List<Tratamientos> list = this.tratamientoFacade.findAllById(parametro);
            return Response.ok(list).build();
        } catch (Exception ex) {
            return Util.manageException(ex, ReporteREST.class);
        }
    }

    @Secured(validarPermiso = false)
    @GET
    @Path("aon")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response token() throws MalformedURLException, IOException {
        Properties prop = this.loaderFacade.getSmsProperties();
        try {
            HttpResponse<String> response = Unirest.post("http://localhost:3000/api/v1/aon/token")
                    .header("accept", "application/json")
                    .queryString("email", prop.getProperty(EMAIL_AON))
                    .queryString("password", prop.getProperty(PASS_AON))
                    .asString();

            return Response.ok(response.getBody()).build();
        } catch (UnirestException ex) {
            return Util.manageException(ex, CasoMedicoREST.class);
        }

    }

    @Secured(validarPermiso = false)
    @GET
    @Path("aon/registers/{token}/{cc}/{fechai}/{fechafi}")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response oanregisters(
            @PathParam("token") String token,
            @PathParam("cc") String cc,
            @PathParam("fechai") String fechai,
            @PathParam("fechafi") String fechafi
    ) throws MalformedURLException, IOException {
        Properties prop = this.loaderFacade.getSmsProperties();
        try {
            HttpResponse<String> response = Unirest.get("http://localhost:3000/api/v1/aon/registers")
                    .header("accept", "application/json")
                    .queryString("token", token)
                    .queryString("cc", cc)
                    .queryString("fechai", fechai)
                    .queryString("fechafi", fechafi)
                    .asString();

            return Response.ok(response.getBody()).build();
        } catch (UnirestException ex) {
            return Util.manageException(ex, CasoMedicoREST.class);
        }

    }

    @POST
    @Path("pcl")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response create(Pcl pcl) {
        try {
            pcl.setEliminado(false);
            pcl = this.pclFacade.create(pcl);
            String pk_case = diagnosticoFacade.findById(pcl.getDiag()).getPkCase();
            this.logScm("Creacion de pcl", null, pk_case, pcl.getClass().toString());
            return Response.ok(pcl.getId()).build();
        } catch (Exception ex) {
            return Util.manageException(ex, CasoMedicoREST.class);
        }
    }

    @GET
    @Path("pcl/{id}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response listPcl(@PathParam("id") String id) {
        try {
            List list = this.pclFacade.findAllById(id);
            return Response.ok(list).build();
        } catch (Exception ex) {
            return Util.manageException(ex, CasoMedicoREST.class);
        }
    }

    @PUT
    @Path("pcl")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response editPcl(Pcl pcl) {
        try {
            pcl.setEliminado(false);

            ObjectMapper mapper = new ObjectMapper();
            String json = mapper.writeValueAsString(pcl);
            String pk_case = diagnosticoFacade.findById(pcl.getDiag()).getPkCase();

            this.logScm("Se edito un pcl", json, pk_case, pcl.getClass().toString());
            pcl = this.pclFacade.edit(pcl);

            return Response.ok(pcl).build();
        } catch (Exception ex) {
            return Util.manageException(ex, CasoMedicoREST.class);
        }
    }

    @PUT
    @Path("pcl/delete")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response deletePcl(Pcl pcl) {
        try {
            this.pclFacade.eliminar(pcl.getId());
            String pk_case = diagnosticoFacade.findById(pcl.getDiag()).getPkCase();
            this.logScm("Se Elimino una pcl", null, pk_case, "Pcl");
            return Response.ok(pcl.getId().toString()).build();
        } catch (Exception ex) {
            return Util.manageException(ex, CasoMedicoREST.class);
        }
    }

    private void logScm(String action, String json, String documento, String entity) {
        try {
            Calendar fechaActual = Calendar.getInstance();
            ScmLogs log = new ScmLogs();
            log.setAction(action);
            log.setPkCase(documento);
            log.setPkUser(super.getUsuarioRequestContext().getEmail());
            log.setFecha_creacion(fechaActual.getTime());
            log.setEntity(entity);
            log.setJson(json);
            scmLogsFacade.create(log);

        } catch (Exception e) {

        }

    }

    @POST
    @Path("reintegro")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response create(Reintegro reintegro) {
        try {
//            reintegro.setEliminado(false);
            reintegro = this.reintegroFacade.create(reintegro);
//            this.logScm("Creacion de reintegro", null, reintegro.getId().toString(), reintegro.getClass().toString());
            System.out.println(reintegro);
            return Response.ok(reintegro).build();
//            return Response.ok(reintegro.getId()).build();
        } catch (Exception ex) {
            return Util.manageException(ex, CasoMedicoREST.class);
        }
    }

    @GET
    @Path("reintegro/{id}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response listReintegro(@PathParam("id") String id) {
        try {
//            reintegro.setEliminado(false);
            List<Reintegro> list = (List<Reintegro>) this.reintegroFacade.getAllByCasoID(id);
            return Response.ok(list).build();
//            return Response.ok(reintegro.getId()).build();
        } catch (Exception ex) {
            return Util.manageException(ex, CasoMedicoREST.class);
        }
    }

    @PUT
    @Path("reintegro")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response editReintegro(Reintegro reintegro) {
        try {

            ObjectMapper mapper = new ObjectMapper();
            String json = mapper.writeValueAsString(reintegro);

            this.logScm("Se edito un reintegro", json, reintegro.getPk_case(), reintegro.getClass().toString());
            reintegro = this.reintegroFacade.update(reintegro);
            return Response.ok(reintegro).build();
        } catch (Exception ex) {
            return Util.manageException(ex, ReporteREST.class);
        }
    }
    
    @PUT
    @Path("cambiarEstado/{id}")
    @Secured(validarPermiso = false)
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response changeEstadoById(@PathParam("id") Integer id, String body){
        try {
            CasosMedicos casosMedicos = this.casosmedicosFacade.findById(id);
            if(casosMedicos.getStatusCaso().compareTo("0") == 0){
                casosMedicos.setStatusCaso("1");
                return Response.ok(casosmedicosFacade.update(casosMedicos)).build();
            }else if(casosMedicos.getStatusCaso().compareTo("1") == 0){
                casosMedicos.setStatusCaso("0");
                return Response.ok(casosmedicosFacade.update(casosMedicos)).build();
            }
            throw new UserMessageException("Error", "No se ha podido cambiar el estado del caso.", TipoMensaje.error);
        }catch(Exception e){
            return Util.manageException(e, CasoMedicoREST.class);
        }
    }
}


