/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.sigess.restful.scm;

import co.sigess.entities.aus.ReporteAusentismo;
import co.sigess.entities.com.Mensaje;
import co.sigess.entities.com.TipoMensaje;
import co.sigess.entities.emp.Area;
import co.sigess.entities.emp.Empleado;
import co.sigess.entities.emp.Empresa;
import co.sigess.entities.emp.Usuario;
import co.sigess.entities.scm.CasosMedicos;
import co.sigess.entities.scm.CreatePclDTO;
import co.sigess.entities.scm.DatosTrabajadorEntity;
import co.sigess.entities.scm.DiagnosticoPartesDTO;
import co.sigess.entities.scm.DiagnosticoPartesEntity;
import co.sigess.facade.scm.pclDiagnosticosFacade;
import co.sigess.entities.scm.Recomendaciones;
import co.sigess.entities.scm.ScmLogs;
import co.sigess.entities.scm.Diagnosticos;
import co.sigess.entities.scm.MailSaludLaboralEntity;
import co.sigess.entities.scm.PartesCuerpoEntity;
import co.sigess.entities.scm.Pcl;
import co.sigess.entities.scm.Pcl_Diagnostico;
import co.sigess.entities.scm.Reintegro;
import co.sigess.entities.scm.SeguimientoCaso;
import co.sigess.entities.scm.SistemaAfectado;
import co.sigess.entities.scm.Sve;
import co.sigess.entities.scm.Tratamientos;
import co.sigess.exceptions.UserMessageException;
import co.sigess.facade.aus.ReporteAusentismoFacade;
import co.sigess.facade.core.EmailFacade;
import co.sigess.facade.core.LoaderFacade;
import co.sigess.facade.core.SMSFacade;
import co.sigess.facade.emp.EmpleadoFacade;
import co.sigess.facade.emp.UsuarioFacade;
import co.sigess.facade.scm.CasosMedicosFacade;
import co.sigess.facade.scm.DatosTrabajadorFacade;
import co.sigess.facade.scm.PartesCuerpoFacade;
import co.sigess.facade.scm.PartesDiagnosticoFacade;
import co.sigess.facade.scm.PclFacade;
import co.sigess.facade.scm.RecomendacionesFacade;
import co.sigess.facade.scm.ReintegroFacade;
import co.sigess.facade.scm.SaludLaboralFacade;
import co.sigess.facade.scm.ScmLogsFacade;
import co.sigess.facade.scm.SeguimientoCasoFacade;
import co.sigess.facade.scm.SistemaAfectadoFacade;
import co.sigess.facade.scm.SveFacade;
import co.sigess.facade.scm.diagnosticoFacade;
import co.sigess.facade.scm.mailSaludLaboralFacade;
import co.sigess.facade.scm.pclDiagFacade;
import co.sigess.facade.scm.pclDiagnosticosFacade;
import co.sigess.facade.scm.tratamientosFacade;
import co.sigess.facade.sec.dtoImageDesv;
import co.sigess.restful.Filter;
import co.sigess.restful.FilterQuery;
import co.sigess.restful.FilterResponse;
import co.sigess.restful.ServiceREST;
import co.sigess.restful.emp.AuthenticationREST;
import co.sigess.restful.emp.EmpleadoREST;
import co.sigess.restful.rai.ReporteREST;
import co.sigess.restful.security.Auditable;
import co.sigess.restful.security.Secured;
import co.sigess.util.Util;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.math.BigInteger;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;
import javax.ws.rs.BeanParam;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.PATCH;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
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
    private pclDiagnosticosFacade PclDIag;

    @EJB
    private CasosMedicosFacade casosmedicosFacade;

    @EJB
    private DatosTrabajadorFacade DatosTrabajadorFacade;

    @EJB
    private tratamientosFacade tratamientoFacade;

    @EJB
    private SaludLaboralFacade SaludLaboralFacade;

    @EJB
    private pclDiagFacade pclDiagFacade;

    @EJB
    private diagnosticoFacade diagnosticoFacade;

    @EJB
    private PartesCuerpoFacade partesCuerpoFacade;
    @EJB
    private PartesDiagnosticoFacade partesDiagnosticoFacade;

    @EJB
    private mailSaludLaboralFacade mailSaludLaboralFacade;

    @EJB
    private EmpleadoFacade EmpleadoFacade;

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
            casosmedicos.setStatusCaso("1");
            this.logScm("Edicion de caso medico", json, casosmedicos.getId().toString(),
                    casosmedicos.getClass().toString());
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

            filtradoEmpresa = filterQuery.getFilterList().stream()
                    .anyMatch(find -> find.getField().equals("empresa.id"));

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

            this.logScm("Creacion de recomendacion", json, recomendaciones.getPkCase().toString(),
                    recomendaciones.getClass().toString());
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

            this.logScm("Se edito una recomendacion", json, recomendaciones.getPkCase().toString(),
                    recomendaciones.getClass().toString());
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
            if ("undefined".compareToIgnoreCase(id) == 0) {
                // return Util.manageException(new IllegalArgumentException("El valor del id es
                // undefined"), EmpleadoREST.class);
                return Response.status(Response.Status.BAD_REQUEST).build();
            }

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
            for (CasosMedicos casoMedico : list) {
                casoMedico.getPkUser().getArea().setAreaList(null);
                casoMedico.getPkUser().setUsuario(null);
                try {
                    casoMedico.getEmpresa().setLogo(null);
                } catch (Exception e) {
                }
            }
            return Response.ok(list).build();

        } catch (Exception ex) {
            return Util.manageException(ex, EmpleadoREST.class);
        }
    }

    @GET
    @Path("validateSalud/{parametro}")
    @Secured(validarPermiso = false)
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response buscarSL(@PathParam("parametro") String parametro) {
        try {

            List<DatosTrabajadorEntity> list = DatosTrabajadorFacade.buscar(parametro);
            for (DatosTrabajadorEntity casoMedico : list) {

                try {

                } catch (Exception e) {
                }
            }
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

    @GET
    @Path("diagnosticosSl/{param}")
    @Secured(validarPermiso = false)
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response getDiagnosticosSl(@PathParam("param") String param) {
        try {

            List<Diagnosticos> list = this.diagnosticoFacade.findAllByIdSl(param);
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
            Query q1 = this.em.createNativeQuery("SELECT currval('scm.diagnosticos_id_seq')");
            Long idGenerado = ((Number) q1.getSingleResult()).longValue();

            diagnosticos.setId(idGenerado);
            q1.getResultList();
            return Response.ok(diagnosticos).build();
        } catch (Exception ex) {
            return Util.manageException(ex, ReporteREST.class);
        }

    }

    @POST
    @Path("diagnosticosSl")
    @Secured(validarPermiso = false)
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response createDiagSl(DiagnosticoPartesDTO diagnosticos) {
        try {
            List<Long> value = diagnosticos.getIdPartes();
            for (Long long1 : value) {
                DiagnosticoPartesEntity pivot = new DiagnosticoPartesEntity();
                pivot.setIdDiagnostico(diagnosticos.getIdDiagnostico());
                pivot.setIdPartes(long1);

                partesDiagnosticoFacade.create(pivot);
            }

            return Response.ok(diagnosticos).build();
        } catch (Exception e) {
            return Util.manageException(e, ReporteREST.class);
        }
    }

    @PUT
    @Path("diagnosticos")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response editDiag(Diagnosticos diagnosticos) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            String json = mapper.writeValueAsString(diagnosticos);

            this.logScm("Se edito un diagnostico", json, diagnosticos.getPkCase().toString(),
                    diagnosticos.getClass().toString());
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
            this.logScm("Creacion de Seguimiento", json, seguimientoCaso.getPkCase(),
                    seguimientoCaso.getClass().toString());

            Query q1 = this.em.createNativeQuery("SELECT scm.seguimiento_cm()");
            q1.getResultList();

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

    @GET
    @Path("seguimiento/generico/{parametro}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response getSeguimientogenerico(@PathParam("parametro") String parametro) {
        try {

            List<SeguimientoCaso> list = this.seguimientoFacade.buscargenerico(parametro);
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

            Query q1 = this.em.createNativeQuery("SELECT scm.seguimiento_cm()");
            q1.getResultList();

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

            String pk_case = diagnosticoFacade.findById(id).getPkCase();
            this.logScm("Se borro un diagnostico ", null, pk_case, "diagnostico");
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

            String pk_case = String.valueOf(recomendacionesFacade.findById(id).getPkCase());
            this.logScm("Se Elimino una recomendacion", null, pk_case, "Recomendacion");

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
            @PathParam("fechafi") String fechafi) throws MalformedURLException, IOException {
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
    public Response create(CreatePclDTO createvalue) {
        try {
            // Crear una nueva instancia de PCL y establecer eliminado en false
            Pcl newPcl = createvalue.getPcl();
            newPcl.setEliminado(false);

            // Crear la nueva PCL en la base de datos y obtener su ID
            Long idPcl = this.pclFacade.create(newPcl).getId();

            // Asignar el ID generado a la nueva PCL
            newPcl.setId(idPcl);

            // Crear los registros de Pcl_Diagnostico
            for (Long diagId : createvalue.getDiags()) {
                Diagnosticos diag = new Diagnosticos();
                diag.setId(diagId);

                // Guardar el diagnóstico en la tabla diag de pcl
                Pcl_Diagnostico pclDiag = new Pcl_Diagnostico();
                pclDiag.setDiagnosticos(diag);
                pclDiag.setPcl(newPcl);
                this.PclDIag.create(pclDiag);
            }

            // Log de la creación de PCL
            String pk_case = "";
            this.logScm("Creacion de pcl", null, pk_case, newPcl.getClass().toString());

            // Construir la respuesta
            Map<String, Object> response = new HashMap<>();
            response.put("pcl", newPcl);
            response.put("diags", createvalue.getDiags());

            // Devolver la respuesta
            return Response.ok(response).build();
        } catch (Exception ex) {
            return Util.manageException(ex, CasoMedicoREST.class);
        }
    }

    @GET
    @Path("pcl/{id}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response listPcl(@PathParam("id") String id) {
        try {
            //List list = this.pclFacade.findAllById(id);
            List list = this.pclFacade.findAllById(id);
            return Response.ok(list).build();
        } catch (Exception ex) {
            return Util.manageException(ex, CasoMedicoREST.class);

        }
    }

    @GET
    @Path("pclSalud/{id}")
    @Secured(validarPermiso = false)
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response listPclSL(@PathParam("id") String id) {
        try {

            List list = this.pclFacade.findAllByIdSaludLaboral(id);
            return Response.ok(list).build();
        } catch (Exception ex) {
            return Util.manageException(ex, CasoMedicoREST.class);

        }
    }

    @GET
    @Path("pclAllDiags/{id}/{pcl}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response listPclAllDiags(@PathParam("id") String id, @PathParam("pcl") Long pcl) {
        try {
            //List list = this.pclFacade.findAllById(id);
            List list = this.pclDiagFacade.findPCLbyID(id, pcl);
            return Response.ok(list).build();
        } catch (Exception ex) {
            return Util.manageException(ex, CasoMedicoREST.class);

        }
    }

    @GET
    @Path("pclDiags/{id}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response listPclAndDiags(@PathParam("id") String id) {
        try {
            List list = this.PclDIag.findAllByIdPCL(id);
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

            this.logScm("Se edito un pcl", json, "", pcl.getClass().toString());
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
            // reintegro.setEliminado(false);
            reintegro = this.reintegroFacade.create(reintegro);
            // this.logScm("Creacion de reintegro", null, reintegro.getId().toString(),
            // reintegro.getClass().toString());
            System.out.println(reintegro);
            return Response.ok(reintegro).build();
            // return Response.ok(reintegro.getId()).build();
        } catch (Exception ex) {
            return Util.manageException(ex, CasoMedicoREST.class);
        }
    }

    @GET
    @Path("reintegro/{id}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response listReintegro(@PathParam("id") String id) {
        try {
            // reintegro.setEliminado(false);
            List<Reintegro> list = (List<Reintegro>) this.reintegroFacade.getAllByCasoID(id);
            return Response.ok(list).build();
            // return Response.ok(reintegro.getId()).build();
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
    public Response changeEstadoById(@PathParam("id") Integer id, String body) {
        try {
            CasosMedicos casosMedicos = this.casosmedicosFacade.findById(id);
            if (casosMedicos.getStatusCaso().compareTo("0") == 0) {
                casosMedicos.setStatusCaso("1");
                casosMedicos.setFechaFinal(null);
                // casosMedicos.setObservaciones(null);
                return Response.ok(casosmedicosFacade.update(casosMedicos)).build();
            } else if (casosMedicos.getStatusCaso().compareTo("1") == 0) {
                casosMedicos.setStatusCaso("0");
                return Response.ok(casosmedicosFacade.update(casosMedicos)).build();
            }
            throw new UserMessageException("Error", "No se ha podido cambiar el estado del caso.", TipoMensaje.error);
        } catch (Exception e) {
            return Util.manageException(e, CasoMedicoREST.class);
        }
    }
    
    @PUT
    @Path("cambiarEstadoSL/{id}")
    @Secured(validarPermiso = false)
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response actualizarEstadoSL(@PathParam("id") int id, DatosTrabajadorEntity updatedData) {
        try {
            DatosTrabajadorEntity dt = DatosTrabajadorFacade.findById(id);
            if (dt == null) {
                return Response.status(Response.Status.NOT_FOUND).build();
            }

            // Actualiza los campos del objeto existente con los valores del objeto actualizado
            dt.setStatusCaso(true);
            // ... actualiza otros campos necesarios ...

            return Response.ok(DatosTrabajadorFacade.update(dt)).build();
        } catch (Exception e) {
            return Util.manageException(e, CasoMedicoREST.class);
        }
    }

    @GET
    @Path("sendmail/{emails}")
    @Secured(validarPermiso = false)
    @Produces(MediaType.APPLICATION_JSON)
    public Response enviarCorreoCasosMedicos(@PathParam("emails") String emails, Map<String, String> parametros) {
        try {
            String[] correosArray = emails.split(",");
            List<String> correos = Arrays.asList(correosArray);

            if (correos != null && !correos.isEmpty()) {
                List<Usuario> usuarios = SaludLaboralFacade.enviarCorreoCasosMedicos(correos, parametros);
            }
            return Response.ok(new Mensaje("Envío realizado", "Se ha enviado un correo electrónico con los documentos a solicitar", TipoMensaje.success)).build();
        } catch (Exception ex) {
            return Util.manageException(ex, AuthenticationREST.class);
        }
    }

    @GET
    @Path("sendmailReject/{emails}")
    @Secured(validarPermiso = false)
    @Produces(MediaType.APPLICATION_JSON)
    public Response enviarCorreoRechazo(@PathParam("emails") String emails, Map<String, String> parametros) {
        try {
            String[] correosArray = emails.split(",");
            List<String> correos = Arrays.asList(correosArray);

            if (correos != null && !correos.isEmpty()) {
                List<Usuario> usuarios = SaludLaboralFacade.enviarCorreoRechazo(correos, parametros);
            }
            return Response.ok(new Mensaje("Envío realizado", "Se ha enviado un correo electrónico con los documentos a solicitar", TipoMensaje.success)).build();
        } catch (Exception ex) {
            return Util.manageException(ex, AuthenticationREST.class);
        }
    }

    @GET
    @Path("sendmaiDocsEnv/{emails}")
    @Secured(validarPermiso = false)
    @Produces(MediaType.APPLICATION_JSON)
    public Response enviarCorreoDocumentosEnviados(@PathParam("emails") String emails, Map<String, String> parametros) {
        try {
            String[] correosArray = emails.split(",");
            List<String> correos = Arrays.asList(correosArray);

            if (correos != null && !correos.isEmpty()) {
                List<Usuario> usuarios = SaludLaboralFacade.enviarCorreoDocumentosEnviadosFacade(correos, parametros);
            }
            return Response.ok(new Mensaje("Envío realizado", "Se ha enviado un correo electrónico con los documentos a solicitar", TipoMensaje.success)).build();
        } catch (Exception ex) {
            return Util.manageException(ex, AuthenticationREST.class);
        }
    }

    @GET
    @Path("sendmailRejectSolicitante/{emails}")
    @Secured(validarPermiso = false)
    @Produces(MediaType.APPLICATION_JSON)
    public Response enviarCorreoRechazoSolicitante(@PathParam("emails") String emails, Map<String, String> parametros) {
        try {
            String[] correosArray = emails.split(",");
            List<String> correos = Arrays.asList(correosArray);

            if (correos != null && !correos.isEmpty()) {
                List<Usuario> usuarios = SaludLaboralFacade.enviarCorreoRechazoSolicitante(correos, parametros);
            }
            return Response.ok(new Mensaje("Envío realizado", "Se ha enviado un correo electrónico con los documentos a solicitar", TipoMensaje.success)).build();
        } catch (Exception ex) {
            return Util.manageException(ex, AuthenticationREST.class);
        }
    }

    @POST
    @Secured(validarPermiso = false)
    @Path("createDT")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response createDT(DatosTrabajadorEntity datosTrabajador) {
        try {

            ObjectMapper mapper = new ObjectMapper();
            String json = mapper.writeValueAsString(datosTrabajador);
            Date fechaActual = new Date();
            datosTrabajador.setFechaCreacion(fechaActual);
            datosTrabajador.setUsuarioCreador(super.getUsuarioRequestContext().getEmail());
            //datosTrabajador.setPkUser(super.getUsuarioRequestContext().getId());
            //datosTrabajador.setId(null);

            datosTrabajador = this.DatosTrabajadorFacade.createDT(datosTrabajador);
            this.logScm("Creacion de datos del empledado", json, datosTrabajador.getIdSl().toString(), datosTrabajador.getClass().toString());

            return Response.ok(datosTrabajador.getIdSl()).build();
        } catch (Exception ex) {
            return Util.manageException(ex, ReporteREST.class);
        }
    }

    @GET
    @Path("salud")
    @Secured(validarPermiso = false)
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response findWithFilterSL(@BeanParam FilterQuery filterQuery) {

        try {

            long numRows = filterQuery.isCount() ? DatosTrabajadorFacade.countWithFilter(filterQuery) : -1;

            List list = DatosTrabajadorFacade.findWithFilter(filterQuery);
            FilterResponse filterResponse = new FilterResponse();
            filterResponse.setData(list);
            filterResponse.setCount(numRows);
            return Response.ok(filterResponse).build();
        } catch (Exception ex) {
            return Util.manageException(ex, EmpleadoREST.class);
        }
    }
        @GET
    @Path("saludMail")
    @Secured(validarPermiso = false)
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response findWithFilterMails(@BeanParam FilterQuery filterQuery) {

        try {

            long numRows = filterQuery.isCount() ? mailSaludLaboralFacade.countWithFilter(filterQuery) : -1;

            List list = mailSaludLaboralFacade.findWithFilter(filterQuery);
            FilterResponse filterResponse = new FilterResponse();
            filterResponse.setData(list);
            filterResponse.setCount(numRows);
            return Response.ok(filterResponse).build();
        } catch (Exception ex) {
            return Util.manageException(ex, EmpleadoREST.class);
        }
    }

    @GET
    @Secured(validarPermiso = false)
    @Path("caseSL/{idSl}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response buscarCasoSL(@PathParam("idSl") String idSl) {

        try {
            if ("undefined".compareToIgnoreCase(idSl) == 0) {
                // return Util.manageException(new IllegalArgumentException("El valor del id es
                // undefined"), EmpleadoREST.class);
                return Response.status(Response.Status.BAD_REQUEST).build();
            }

            DatosTrabajadorEntity list = DatosTrabajadorFacade.findById(Integer.parseInt(idSl));
            return Response.ok(list).build();

        } catch (Exception ex) {
            return Util.manageException(ex, EmpleadoREST.class);
        }
    }

    @PUT
    @Path("caseESL/{idSl}")
    @Secured(validarPermiso = false)
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response actualizarCasoSL(@PathParam("idSl") int idSl, DatosTrabajadorEntity updatedData) {
        try {
            DatosTrabajadorEntity dt = DatosTrabajadorFacade.findById(idSl);
            if (dt == null) {
                return Response.status(Response.Status.NOT_FOUND).build();
            }

            // Actualiza los campos del objeto existente con los valores del objeto actualizado
            dt.setFechaEdicion(new Date());
            dt.setCargoActual(updatedData.getCargoActual());
            dt.setCargoOriginal(updatedData.getCargoOriginal());
            dt.setDivisionActual(updatedData.getDivisionActual());
            dt.setDivisionOrigen(updatedData.getDivisionOrigen());
            dt.setLocalidadActual(updatedData.getLocalidadActual());
            dt.setLocalidadOrigen(updatedData.getLocalidadOrigen());
            dt.setAreaActual(updatedData.getAreaActual());
            dt.setAreaOrigen(updatedData.getAreaOrigen());
            dt.setProcesoActual(updatedData.getProcesoActual());
            dt.setProcesoOrigen(updatedData.getProcesoOrigen());
            dt.setFechaRecepcionDocs(updatedData.getFechaRecepcionDocs());
            dt.setEntidadEmiteCalificacion(updatedData.getEntidadEmiteCalificacion());
            dt.setOtroDetalle(updatedData.getOtroDetalle());
            dt.setDetalleCalificacion(updatedData.getDetalleCalificacion());
            dt.setFechaMaximaEnvDocs(updatedData.getFechaMaximaEnvDocs());
            dt.setFechaCierreCaso(updatedData.getFechaCierreCaso());
            dt.setFechaNotificacionEmp(updatedData.getFechaNotificacionEmp());
            dt.setFechaNotificacionMin(updatedData.getFechaNotificacionMin());
            dt.setEpsDictamen(updatedData.getEpsDictamen());
            dt.setFechaDictamenArl(updatedData.getFechaDictamenArl());
            dt.setArlDictamen(updatedData.getArlDictamen());
            //dt.setDocumentosArl(updatedData.getDocumentosArl());
            dt.setFechaDictamenJr(updatedData.getFechaDictamenJr());
            dt.setJrDictamen(updatedData.getJrDictamen());
            dt.setFechaDictamenJn(updatedData.getFechaDictamenJn());
            //dt.setDocumentosJn(updatedData.getDocumentosJn());
            //dt.setDocumentosJr(updatedData.getJrDictamen());
            // ... actualiza otros campos necesarios ...

            return Response.ok(DatosTrabajadorFacade.update(dt)).build();
        } catch (Exception e) {
            return Util.manageException(e, CasoMedicoREST.class);
        }
    }

    @PUT
    @Path("userUpdate/{pkUser}")
    @Secured(validarPermiso = false)
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response actualizarEmpleadoSL(@PathParam("pkUser") Integer pkUser, Empleado updatedData) {
        try {
            Empleado dt = EmpleadoFacade.findByIdEmpleado(pkUser);
            if (dt == null) {
                return Response.status(Response.Status.NOT_FOUND).build();
            }

            // Actualizar solo los campos que están presentes en updatedData
            if (updatedData.getFechaNacimiento() != null) {
                dt.setFechaNacimiento(updatedData.getFechaNacimiento());
            }
            if (updatedData.getGenero() != null) {
                dt.setGenero(updatedData.getGenero());
            }
            if (updatedData.getFechaIngreso() != null) {
                dt.setFechaIngreso(updatedData.getFechaIngreso());
            }
            if (updatedData.getTelefono1() != null) {
                dt.setTelefono1(updatedData.getTelefono1());
            }
            if (updatedData.getTelefono2() != null) {
                dt.setTelefono2(updatedData.getTelefono2());
            }
            if (updatedData.getCorporativePhone() != null) {
                dt.setCorporativePhone(updatedData.getCorporativePhone());
            }
            if (updatedData.getCiudad() != null) {
                dt.setCiudad(updatedData.getCiudad());
            }
            if (updatedData.getDireccion() != null) {
                dt.setDireccion(updatedData.getDireccion());
            }
            if (updatedData.getZonaResidencia() != null) {
                dt.setZonaResidencia(updatedData.getZonaResidencia());
            }
            if (updatedData.getCorreoPersonal() != null) {
                dt.setCorreoPersonal(updatedData.getCorreoPersonal());
            }
            if (updatedData.getEps() != null) {
                dt.setEps(updatedData.getEps());
            }
            if (updatedData.getAfp() != null) {
                dt.setAfp(updatedData.getAfp());
            }
            if (updatedData.getEmergencyContact() != null) {
                dt.setEmergencyContact(updatedData.getEmergencyContact());
            }
            if (updatedData.getPhoneEmergencyContact() != null) {
                dt.setPhoneEmergencyContact(updatedData.getPhoneEmergencyContact());
            }
            if (updatedData.getEmailEmergencyContact() != null) {
                dt.setEmailEmergencyContact(updatedData.getEmailEmergencyContact());
            }
            if (updatedData.getJefeInmediato() != null) {
                dt.setJefeInmediato(updatedData.getJefeInmediato());
            }
            if (updatedData.getEmpresa() != null) {
                dt.setEmpresa(updatedData.getEmpresa());
            }
            if (updatedData.getNit() != null) {
                dt.setNit(updatedData.getNit());
            }

            // Actualizar otros campos necesarios...
            return Response.ok(EmpleadoFacade.updateEmpleadoSL(dt)).build();
        } catch (Exception e) {
            return Util.manageException(e, CasoMedicoREST.class);
        }
    }

    @PUT
    @Path("stateAprobed/{id}")
    @Secured(validarPermiso = false)
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response actualizarEstadoAprobado(@PathParam("id") int id, MailSaludLaboralEntity updatedData) {
        try {
            MailSaludLaboralEntity dt = mailSaludLaboralFacade.findById(id);
            if (dt == null) {
                return Response.status(Response.Status.NOT_FOUND).build();
            }

            // Actualiza los campos del objeto existente con los valores del objeto actualizado
            dt.setEstadoCorreo(4);
            // ... actualiza otros campos necesarios ...

            return Response.ok(mailSaludLaboralFacade.update(dt)).build();
        } catch (Exception e) {
            return Util.manageException(e, CasoMedicoREST.class);
        }
    }

    @GET
    @Path("partesCuerpo")
    @Secured(validarPermiso = false)
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response getPartesCuerpo() {
        try {

            List<PartesCuerpoEntity> list = this.partesCuerpoFacade.buscar();
            return Response.ok(list).build();
        } catch (Exception ex) {
            return Util.manageException(ex, ReporteREST.class);
        }
    }

    @GET
    @Path("partDiag/{param}")
    @Secured(validarPermiso = false)
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response findAllByIdDiagPartes(@PathParam("param") String param) {
        try {

            List<DiagnosticoPartesEntity> list = this.partesDiagnosticoFacade.findAllByIdDiagPartes(new BigInteger(param));
            return Response.ok(list).build();
        } catch (Exception ex) {
            return Util.manageException(ex, ReporteREST.class);
        }
    }

    @DELETE
    @Path("deletePartDiag/{diag}/{part}")
    @Secured(validarPermiso = false)
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response deleteIdDiagPartes(@PathParam("diag") BigInteger diag, @PathParam("part") BigInteger part) {
        try {

            this.partesDiagnosticoFacade.deleteIdDiagPartes(diag, part);
            return Response.ok().build();
        } catch (Exception ex) {
            return Util.manageException(ex, ReporteREST.class);
        }
    }

    @DELETE
    @Path("deleteDocument/{id}/{docID}")
    @Secured(validarPermiso = false)
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response deleteDocumentFromMail(@PathParam("id") Integer id, @PathParam("docID") String docID) {
        try {
            mailSaludLaboralFacade.deleteDocumentFromMail(id, docID);
            return Response.ok(new Mensaje("Documento eliminado correctamente")).build();
        } catch (Exception e) {
            return Util.manageException(e, CasoMedicoREST.class);
        }
    }
        @DELETE
    @Path("deleteDocumentDT/{id}/{docID}")
    @Secured(validarPermiso = false)
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response deleteDocumentCaseDT(@PathParam("id") Integer id, @PathParam("docID") String docID) {
        try {
            DatosTrabajadorFacade.deleteDocumentFromMail(id, docID);
            return Response.ok(new Mensaje("Documento eliminado correctamente")).build();
        } catch (Exception e) {
            return Util.manageException(e, CasoMedicoREST.class);
        }
    }
    @DELETE
    @Path("deleteDocumentEmp/{id}/{docID}")
    @Secured(validarPermiso = false)
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response deleteDocumentEmp(@PathParam("id") Integer id, @PathParam("docID") String docID) {
        try {
            DatosTrabajadorFacade.deleteDocumentEmp(id, docID);
            return Response.ok(new Mensaje("Documento eliminado correctamente")).build();
        } catch (Exception e) {
            return Util.manageException(e, CasoMedicoREST.class);
        }
    }
        @DELETE
    @Path("deleteDocumentArl/{id}/{docID}")
    @Secured(validarPermiso = false)
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response deleteDocumentArl(@PathParam("id") Integer id, @PathParam("docID") String docID) {
        try {
            DatosTrabajadorFacade.deleteDocumentArl(id, docID);
            return Response.ok(new Mensaje("Documento eliminado correctamente")).build();
        } catch (Exception e) {
            return Util.manageException(e, CasoMedicoREST.class);
        }
    }
          @DELETE
    @Path("deleteDocumentJr/{id}/{docID}")
    @Secured(validarPermiso = false)
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response deleteDocumentJr(@PathParam("id") Integer id, @PathParam("docID") String docID) {
        try {
            DatosTrabajadorFacade.deleteDocumentJr(id, docID);
            return Response.ok(new Mensaje("Documento eliminado correctamente")).build();
        } catch (Exception e) {
            return Util.manageException(e, CasoMedicoREST.class);
        }
    }
             @DELETE
    @Path("deleteDocumentJn/{id}/{docID}")
    @Secured(validarPermiso = false)
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response deleteDocumentJn(@PathParam("id") Integer id, @PathParam("docID") String docID) {
        try {
            DatosTrabajadorFacade.deleteDocumentJn(id, docID);
            return Response.ok(new Mensaje("Documento eliminado correctamente")).build();
        } catch (Exception e) {
            return Util.manageException(e, CasoMedicoREST.class);
        }
    }
        @DELETE
    @Path("deleteDocumentMin/{id}/{docID}")
    @Secured(validarPermiso = false)
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response deleteDocumentMin(@PathParam("id") Integer id, @PathParam("docID") String docID) {
        try {
            DatosTrabajadorFacade.deleteDocumentMin(id, docID);
            return Response.ok(new Mensaje("Documento eliminado correctamente")).build();
        } catch (Exception e) {
            return Util.manageException(e, CasoMedicoREST.class);
        }
    }

//    @POST
//    @Secured(validarPermiso = false)
//    @Path("createMail")
//    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
//    public Response createMailCaseSL(MailSaludLaboralEntity datosTrabajador) {
//        try {
//
//            ObjectMapper mapper = new ObjectMapper();
//            String json = mapper.writeValueAsString(datosTrabajador);
//            //datosTrabajador.setFechaCreacion(fechaActual);
//            datosTrabajador.setUsuarioSolicitante(super.getUsuarioRequestContext().getEmail());
//            
//
//
//            datosTrabajador = this.mailSaludLaboralFacade.createMailCaseSL(datosTrabajador);
//            this.logScm("Creacion de datos del mail para empledado", json, datosTrabajador.getId().toString(), datosTrabajador.getClass().toString());
//
//            return Response.ok(datosTrabajador.getId()).build();
//        } catch (Exception ex) {
//            return Util.manageException(ex, ReporteREST.class);
//        }
//    }
    @POST
    @Secured(validarPermiso = false)
    @Path("createMail")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response createMailCaseSL(MailSaludLaboralEntity datosTrabajador) {
         String host1 = "https://demo.sigess.app"; // Valor por defecto para demostración

        Query q1 = em.createNativeQuery("SELECT COUNT(h.id) FROM com.host h WHERE h.host ='Produccion'");
        int countProduction = ((Number) q1.getSingleResult()).intValue();

        q1 = em.createNativeQuery("SELECT h.id FROM com.host h WHERE h.host ='Localhost'");
        int countLocalhost = -1;
        try {
            countLocalhost = ((Number) q1.getSingleResult()).intValue();
        } catch (Exception e) {
            host1 = "http://localhost:4200";
        }
        if (countProduction > 0) {
            host1 = "https://sigess.app"; // Cambiar a producción si hay al menos un registro de producción
        }
        if (countProduction == 0) {
            host1 = "https://demo.sigess.app";
        } if(countProduction !=0 && countProduction != 1) {
            host1 = "http://localhost:4200";
        }
        try {
            ObjectMapper mapper = new ObjectMapper();
            datosTrabajador.setDocumentos(null);            
            String json = mapper.writeValueAsString(datosTrabajador);
            Date fechaActual = new Date();
            SimpleDateFormat formatoFecha = new SimpleDateFormat("dd/MM/yyyy");
            String fechaFormateada = formatoFecha.format(fechaActual);
            datosTrabajador.setUsuarioSolicitante(super.getUsuarioRequestContext().getEmail());
            datosTrabajador.setFechaSolicitud(fechaActual);
            datosTrabajador.setEstadoCorreo(1);
            datosTrabajador.setCorreoEnviado(Boolean.FALSE);
            Date nuevaFechaLimite = datosTrabajador.getFechaLimite();
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            
            datosTrabajador.setFechaLimite(nuevaFechaLimite);
            String fechaFormateadaLimit = formatoFecha.format(nuevaFechaLimite);
            datosTrabajador = this.mailSaludLaboralFacade.createMailCaseSL(datosTrabajador);
            this.logScm("Creacion de datos del mail para empledado", json, datosTrabajador.getId().toString(), datosTrabajador.getClass().toString());

            // Enviar correo electrónico
            String emails = datosTrabajador.getUsuarioSolicitado(); // Cambiar aquí para usar el correo del usuario solicitado
            Map<String, String> parametros = new HashMap<>();
            parametros.put("{name}", datosTrabajador.getUsuarioSolicitado());
            parametros.put("{document}", datosTrabajador.getUsuarioSolicitado());
            parametros.put("{presudiosmaximomeridio}", datosTrabajador.getDocSolicitado());
            parametros.put("{fechaActual}", fechaFormateada);
            parametros.put("{nameSolicitante}", datosTrabajador.getSoliictanteNombres());
            parametros.put("{cedulaSolicitante}", datosTrabajador.getSolicitanteCedula());
            parametros.put("{nameSolicitado}", datosTrabajador.getSolicitadoNombres());
            parametros.put("{cedulaSolicitado}", datosTrabajador.getSolicitadoCedula());
            parametros.put("{fechaLimite}", fechaFormateadaLimit);
            parametros.put("{pkCase}", datosTrabajador.getPkCase().toString());
            parametros.put("{solicitadoNombresMail}", datosTrabajador.getSolicitadoNombresMail());
            parametros.put(EmailFacade.PARAM_ENVIROMENT, host1);

            Response correoResponse = enviarCorreoCasosMedicos(emails, parametros);
            if (correoResponse.getStatus() != Response.Status.OK.getStatusCode()) {
                return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                        .entity(new Mensaje("Error al enviar el correo electrónico", "No se pudo enviar el correo electrónico con los documentos a solicitar", TipoMensaje.error))
                        .build();
            }

            return Response.ok(datosTrabajador.getId()).build();
        } catch (Exception ex) {
            return Util.manageException(ex, ReporteREST.class);
        }
    }

    @PUT
    @Path("email/{id}")
    @Secured(validarPermiso = false)
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response actualizarMail(@PathParam("id") int id, MailSaludLaboralEntity updatedData) {
        try {
            MailSaludLaboralEntity dt = mailSaludLaboralFacade.findById(id);
            if (dt == null) {
                return Response.status(Response.Status.NOT_FOUND).build();
            }

            // Actualiza los campos del objeto existente con los valores del objeto actualizado
            dt.setRazonRechazoSolicitado(updatedData.getRazonRechazoSolicitado());
            dt.setEstadoCorreo(2);
            // ... actualiza otros campos necesarios ...
            String emails = dt.getUsuarioSolicitante();
            Map<String, String> parametros = new HashMap<>();
            parametros.put("{motivoRechazo}", dt.getRazonRechazoSolicitado());
            parametros.put("{docSolicitado}", dt.getDocSolicitado());
            parametros.put("{nameSolicitado}", dt.getSolicitadoNombres());
            parametros.put("{cedulaSolicitado}", dt.getSolicitadoCedula());

            Response correoResponse = enviarCorreoRechazo(emails, parametros);

            if (correoResponse.getStatus() != Response.Status.OK.getStatusCode()) {
                return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                        .entity(new Mensaje("Error al enviar el correo electrónico", "No se pudo enviar el correo electrónico con los documentos a solicitar", TipoMensaje.error))
                        .build();
            }

            return Response.ok(mailSaludLaboralFacade.update(dt)).build();
        } catch (Exception e) {
            return Util.manageException(e, CasoMedicoREST.class);
        }
    }

    @PUT
    @Path("emailEnviado/{id}")
    @Secured(validarPermiso = false)
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response enviarCorreoDocumentacionEnviada(@PathParam("id") int id, MailSaludLaboralEntity updatedData) {
        try {
            MailSaludLaboralEntity dt = mailSaludLaboralFacade.findById(id);
            if (dt == null) {
                return Response.status(Response.Status.NOT_FOUND).build();
            }

            // Verifica si el correo ya ha sido enviado
            if (dt.getCorreoEnviado()) {
                return Response.status(Response.Status.CONFLICT)
                        .entity(new Mensaje("Correo ya enviado", "El correo ya ha sido enviado para este caso", TipoMensaje.warn))
                        .build();
            }

            // Actualiza los campos del objeto existente con los valores del objeto actualizado
            Date fecha = new Date();
            dt.setFechaEnvio(fecha);
            dt.setEstadoCorreo(3);
            // ... actualiza otros campos necesarios ...
            String emails = dt.getUsuarioSolicitante();
            Map<String, String> parametros = new HashMap<>();
            parametros.put("{docSolicitado}", dt.getDocSolicitado());
            parametros.put("{nameSolicitado}", dt.getSolicitadoNombres());
            parametros.put("{cedulaSolicitado}", dt.getSolicitadoCedula());

            Response correoResponse = enviarCorreoDocumentosEnviados(emails, parametros);

            if (correoResponse.getStatus() != Response.Status.OK.getStatusCode()) {
                return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                        .entity(new Mensaje("Error al enviar el correo electrónico", "No se pudo enviar el correo electrónico con los documentos a solicitar", TipoMensaje.error))
                        .build();
            }

            // Marca el correo como enviado
            dt.setCorreoEnviado(true);

            return Response.ok(mailSaludLaboralFacade.update(dt)).build();
        } catch (Exception e) {
            return Util.manageException(e, CasoMedicoREST.class);
        }
    }

    @PUT
    @Path("emailSolicitante/{id}")
    @Secured(validarPermiso = false)
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response actualizarMailRechazoSoliictante(@PathParam("id") int id, MailSaludLaboralEntity updatedData) {
             String host1 = "https://demo.sigess.app"; // Valor por defecto para demostración

        Query q1 = em.createNativeQuery("SELECT COUNT(h.id) FROM com.host h WHERE h.host ='Produccion'");
        int countProduction = ((Number) q1.getSingleResult()).intValue();

        q1 = em.createNativeQuery("SELECT h.id FROM com.host h WHERE h.host ='Localhost'");
        int countLocalhost = -1;
        try {
            countLocalhost = ((Number) q1.getSingleResult()).intValue();
        } catch (Exception e) {
            host1 = "http://localhost:4200";
        }
        if (countProduction > 0) {
            host1 = "https://sigess.app"; // Cambiar a producción si hay al menos un registro de producción
        }
        if (countProduction == 0) {
            host1 = "https://demo.sigess.app";
        } if(countProduction !=0 && countProduction != 1) {
            host1 = "http://localhost:4200";
        }
        try {
            MailSaludLaboralEntity dt = mailSaludLaboralFacade.findById(id);
            if (dt == null) {
                return Response.status(Response.Status.NOT_FOUND).build();
            }

            // Actualiza los campos del objeto existente con los valores del objeto actualizado
            dt.setRazonRechazoSolicitante(updatedData.getRazonRechazoSolicitante());
            dt.setEstadoCorreo(1);
            dt.setFechaLimite(updatedData.getFechaLimite());
            Date nuevaFechaLimite = updatedData.getFechaLimite();
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            String fechaFormateada = sdf.format(nuevaFechaLimite);
            dt.setFechaLimite(nuevaFechaLimite);
            dt.setDocumentos(null);
            dt.setCorreoEnviado(false);
            dt.setFechaEnvio(null);
            // ... actualiza otros campos necesarios ...
            String emails = dt.getUsuarioSolicitado();
            Map<String, String> parametros = new HashMap<>();
            parametros.put("{motivoRechazoSoli}", dt.getRazonRechazoSolicitante());
            parametros.put("{docSolicitadoSoli}", dt.getDocSolicitado());
            parametros.put("{nameSolicitadoSoli}", dt.getSoliictanteNombres());
            parametros.put("{cedulaSolicitadoSoli}", dt.getSolicitanteCedula());
            parametros.put("{nuevaFechaLimiteSoli}", fechaFormateada);
            parametros.put("{pkCase}", dt.getPkCase().toString());
            parametros.put(EmailFacade.PARAM_ENVIROMENT, host1);

            Response correoResponse = enviarCorreoRechazoSolicitante(emails, parametros);

            if (correoResponse.getStatus() != Response.Status.OK.getStatusCode()) {
                return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                        .entity(new Mensaje("Error al enviar el correo electrónico", "No se pudo enviar el correo electrónico con los documentos a solicitar", TipoMensaje.error))
                        .build();
            }

            return Response.ok(mailSaludLaboralFacade.update(dt)).build();
        } catch (Exception e) {
            return Util.manageException(e, CasoMedicoREST.class);
        }
    }

    public static String arrayListToString(ArrayList<String> arrayList) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("[");
        for (int i = 0; i < arrayList.size(); i++) {
            stringBuilder.append(arrayList.get(i));
            if (i != arrayList.size() - 1) {
                stringBuilder.append(", ");
            }
        }
        stringBuilder.append("]");
        return stringBuilder.toString();
    }

    @PUT
    @Path("documents/{id}")
    @Secured(validarPermiso = false)
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response actualizarDocumentos(@PathParam("id") int id, MailSaludLaboralEntity updatedData) {
        try {
            MailSaludLaboralEntity dt = mailSaludLaboralFacade.findById(id);
//            String docs = dt.getDocumentos();
//            ArrayList<String> doc = new ArrayList<>();
//            doc.add(docs);
//            doc.add(updatedData.getDocumentos());
//            String documentosString = arrayListToString(doc);

            if (dt == null) {
                return Response.status(Response.Status.NOT_FOUND).build();
            }
            dt.setDocumentos(updatedData.getDocumentos());
            dt.setEstadoCorreo(3);
            dt.setFechaEnvio(new Date());
            //dt.setDocumentos(documentosString);
            return Response.ok(mailSaludLaboralFacade.update(dt)).build();
        } catch (Exception e) {
            return Util.manageException(e, CasoMedicoREST.class);
        }
    }
    
        @PUT
    @Path("documentsDT/{id}")
    @Secured(validarPermiso = false)
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response actualizarDocumentosCaso(@PathParam("id") int id, DatosTrabajadorEntity updatedData) {
        try {
            DatosTrabajadorEntity dt = DatosTrabajadorFacade.findById(id);
            if (dt == null) {
                return Response.status(Response.Status.NOT_FOUND).build();
            }
            dt.setDocumentos(updatedData.getDocumentos());
            //dt.setDocumentos(documentosString);
            return Response.ok(DatosTrabajadorFacade.update(dt)).build();
        } catch (Exception e) {
            return Util.manageException(e, CasoMedicoREST.class);
        }
    }
    @PUT
    @Path("documentsEmp/{id}")
    @Secured(validarPermiso = false)
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response actualizarDocumentosEmpresa(@PathParam("id") int id, DatosTrabajadorEntity updatedData) {
        try {
            DatosTrabajadorEntity dt = DatosTrabajadorFacade.findById(id);
            if (dt == null) {
                return Response.status(Response.Status.NOT_FOUND).build();
            }
            dt.setDocumentosEmpresa(updatedData.getDocumentosEmpresa());
            //dt.setDocumentos(documentosString);
            return Response.ok(DatosTrabajadorFacade.update(dt)).build();
        } catch (Exception e) {
            return Util.manageException(e, CasoMedicoREST.class);
        }
    }
        @PUT
    @Path("documentsArl/{id}")
    @Secured(validarPermiso = false)
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response actualizarDocumentosArl(@PathParam("id") int id, DatosTrabajadorEntity updatedData) {
        try {
            DatosTrabajadorEntity dt = DatosTrabajadorFacade.findById(id);
            if (dt == null) {
                return Response.status(Response.Status.NOT_FOUND).build();
            }
            dt.setDocumentosArl(updatedData.getDocumentosArl());
            //dt.setDocumentos(documentosString);
            return Response.ok(DatosTrabajadorFacade.update(dt)).build();
        } catch (Exception e) {
            return Util.manageException(e, CasoMedicoREST.class);
        }
    }
            @PUT
    @Path("documentsJr/{id}")
    @Secured(validarPermiso = false)
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response actualizarDocumentosJr(@PathParam("id") int id, DatosTrabajadorEntity updatedData) {
        try {
            DatosTrabajadorEntity dt = DatosTrabajadorFacade.findById(id);
            if (dt == null) {
                return Response.status(Response.Status.NOT_FOUND).build();
            }
            dt.setDocumentosJr(updatedData.getDocumentosJr());
            //dt.setDocumentos(documentosString);
            return Response.ok(DatosTrabajadorFacade.update(dt)).build();
        } catch (Exception e) {
            return Util.manageException(e, CasoMedicoREST.class);
        }
    }
                @PUT
    @Path("documentsJn/{id}")
    @Secured(validarPermiso = false)
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response actualizarDocumentosJn(@PathParam("id") int id, DatosTrabajadorEntity updatedData) {
        try {
            DatosTrabajadorEntity dt = DatosTrabajadorFacade.findById(id);
            if (dt == null) {
                return Response.status(Response.Status.NOT_FOUND).build();
            }
            dt.setDocumentosJn(updatedData.getDocumentosJn());
            //dt.setDocumentos(documentosString);
            return Response.ok(DatosTrabajadorFacade.update(dt)).build();
        } catch (Exception e) {
            return Util.manageException(e, CasoMedicoREST.class);
        }
    }
    @PUT
    @Path("documentsMin/{id}")
    @Secured(validarPermiso = false)
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response actualizarDocumentosMinisterio(@PathParam("id") int id, DatosTrabajadorEntity updatedData) {
        try {
            DatosTrabajadorEntity dt = DatosTrabajadorFacade.findById(id);
            if (dt == null) {
                return Response.status(Response.Status.NOT_FOUND).build();
            }
            dt.setDocumentosMinisterio(updatedData.getDocumentosMinisterio());
            //dt.setDocumentos(documentosString);
            return Response.ok(DatosTrabajadorFacade.update(dt)).build();
        } catch (Exception e) {
            return Util.manageException(e, CasoMedicoREST.class);
        }
    }

    @GET
    @Path("mailsRecept/{param}")
    @Secured(validarPermiso = false)
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response findAllByIdMail(@PathParam("param") Integer param) {
        try {

            List<MailSaludLaboralEntity> list = this.mailSaludLaboralFacade.findAllByIdMail(param);
            return Response.ok(list).build();
        } catch (Exception ex) {
            return Util.manageException(ex, ReporteREST.class);
        }
    }

    @GET
    @Path("mailsRecept/{param}/{pkUser}")
    @Secured(validarPermiso = false)
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})

    public Response findAllByIdMailUser(@PathParam("param") Integer param, @PathParam("pkUser") Integer PkUser) {
        try {

            List<MailSaludLaboralEntity> list = this.mailSaludLaboralFacade.findAllByIdMailUser(param, PkUser);
            return Response.ok(list).build();
        } catch (Exception ex) {
            return Util.manageException(ex, ReporteREST.class);
        }
    }

    @GET
    @Path("mailsReceptByUser/{pkUser}")
    @Secured(validarPermiso = false)
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})

    public Response findAllByIdMailUserOnlyUser(@PathParam("pkUser") Integer PkUser) {
        try {

            List<MailSaludLaboralEntity> list = this.mailSaludLaboralFacade.findAllByIdMailUserOnlyUser(PkUser);
            return Response.ok(list).build();
        } catch (Exception ex) {
            return Util.manageException(ex, ReporteREST.class);
        }
    }

    @GET
    @Path("mailsReceptBySolicitado/{usuarioSolicitante}")
    @Secured(validarPermiso = false)
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})

    public Response findAllByIdMailUserOnlySolicitado(@PathParam("usuarioSolicitante") String usuarioSolicitante) {
        try {

            List<MailSaludLaboralEntity> list = this.mailSaludLaboralFacade.findAllByIdMailUserOnlySolicitadoByMail(usuarioSolicitante);
            return Response.ok(list).build();
        } catch (Exception ex) {
            return Util.manageException(ex, ReporteREST.class);
        }
    }

}
