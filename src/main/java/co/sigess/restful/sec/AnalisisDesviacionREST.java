/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.sigess.restful.sec;

import co.sigess.entities.emp.AliadoInformacion;
import co.sigess.entities.emp.Empresa;
import co.sigess.entities.sec.AnalisisDesviacion;
import co.sigess.entities.sec.DesviacionAliados;
import co.sigess.facade.core.EmailFacade;
import co.sigess.facade.core.TipoMail;
import co.sigess.facade.emp.AliadoInformacionFacade;
import co.sigess.facade.emp.EmpresaFacade;
import co.sigess.facade.sec.AnalisisDesviacionFacade;
import co.sigess.facade.sec.DesviacionAliadosFacade;
import co.sigess.restful.FilterQuery;
import co.sigess.restful.ServiceREST;
import co.sigess.restful.security.Secured;
import co.sigess.util.Util;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.ejb.EJB;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
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
    
    @PersistenceContext(unitName = "SIGESS_PU")
    private EntityManager em;
    
    @EJB
    private AliadoInformacionFacade aliadoInformacionFacade;
    
    @EJB
    private AnalisisDesviacionFacade analisisDesviacionFacade;
    
    @EJB
    private EmailFacade emailFacade;
    
    @EJB
    private EmpresaFacade empresaFacade;
    
    @EJB
    private DesviacionAliadosFacade desviacionAliadosFacade;

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
    
    @POST
    @Path("analisisDesviacionAliado")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    @Secured(validarPermiso = false, requiereEmpresaId = false)
    public Response createAnalisisDesviacionAliado(AnalisisDesviacion analisisDesviacion){
        try {
            Empresa empresaUsuario = empresaFacade.find(super.getEmpresaIdRequestContext());
            analisisDesviacion.setEmpresa(new Empresa(empresaUsuario.getIdEmpresaAliada()));
            analisisDesviacion.setFechaElaboracion(new Date());
            analisisDesviacion.setUsuarioElaboraId(super.getUsuarioRequestContext().getId());
            analisisDesviacion = ((AnalisisDesviacionFacade) beanInstance).create(analisisDesviacion);
            return Response.ok(analisisDesviacion).build();
        } catch (Exception e) {
            return Util.manageException(e, AnalisisDesviacionREST.class);
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
    
    @PUT
    @Path("analisisDesviacionAliado")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    @Secured(requiereEmpresaId = false, validarPermiso = false)
    public Response editAnalisisDesviacionAliado(AnalisisDesviacion analisisDesviacion){
        try {
            Empresa empresaUsuario = empresaFacade.find(super.getEmpresaIdRequestContext());
            analisisDesviacion.setEmpresa(new Empresa(empresaUsuario.getIdEmpresaAliada()));
            analisisDesviacion.setUsuarioModificaId(super.getUsuarioRequestContext().getId());
            analisisDesviacion = ((AnalisisDesviacionFacade) beanInstance).edit(analisisDesviacion);
            return Response.ok(analisisDesviacion).build();
        } catch (Exception e) {
            return Util.manageException(e, AnalisisDesviacionREST.class);
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

    @POST
    @Path("emailGestores/{idReporte}")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    @Secured(requiereEmpresaId = false)
    public Response sendEmail(@PathParam("idReporte") Integer reporteID, String requestBody){
        try {
            Gson gson = new Gson();
            JsonObject body = gson.fromJson(requestBody, JsonObject.class);
            Integer aliadoID = body.get("aliadoID").getAsInt();
            Integer analisisID = body.get("analisisID").getAsInt();
            boolean esNuevo = body.get("esNuevo").getAsBoolean();
            
            Query q = em.createNativeQuery("SELECT h from com.host h where h.host ='Produccion'");
            List hosts = q.getResultList();
            String host = "https://sigess.app";
            if(hosts.size() == 0){
                host = "https://demo.sigess.app";
            }

            AliadoInformacion aliadoInformacion = aliadoInformacionFacade.findByAliadoId(aliadoID).get(0);
            Empresa empresa = empresaFacade.findEmpresaById(aliadoInformacion.getId_empresa());
            AnalisisDesviacion analisisDesviacion = analisisDesviacionFacade.findReporteAlido(analisisID).get(0);
            
            JsonArray coliderList = gson.fromJson(aliadoInformacion.getColider(), JsonArray.class);
            JsonObject gestor = gson.fromJson(analisisDesviacion.getGestor(), JsonObject.class);
            
            List<String> correosColider = new ArrayList<String>();
            for(JsonElement colider : coliderList){
                JsonObject coliderObject = colider.getAsJsonObject();
                JsonObject gestorColider = coliderObject.get("gestor").getAsJsonObject();
                JsonObject usuarioBasic = gestorColider.get("usuarioBasic").getAsJsonObject();
                correosColider.add(usuarioBasic.get("email").getAsString());
            }
            
            JsonObject usuarioBasic = gestor.get("usuarioBasic").getAsJsonObject();
            String correoGestor = usuarioBasic.get("email").getAsString();
            correosColider.add(correoGestor);
            
            Map<String, String> parametros = new HashMap<>();
            parametros.put(EmailFacade.PARAM_NIT, empresa.getNit());
            parametros.put(EmailFacade.PARAM_NOMBRE, empresa.getRazonSocial());
            parametros.put(EmailFacade.PARAM_HOST1, host);
            parametros.put(EmailFacade.PARAM_ID, reporteID.toString());
            String listaCorreos = String.join(",", correosColider);
            System.out.println(listaCorreos);
            emailFacade.sendEmail(
                    listaCorreos,
                    esNuevo ? TipoMail.REPORTE_ALIADOS : TipoMail.REPORTE_ALIADO_MODIFICADO,
                    esNuevo ? "Se creó reporte de AT de aliado" : "Modificación de reporte AT de aliado",
                    parametros);
            
            return Response.ok(true).build();
        }catch(NullPointerException npe){
            String mensage = "No se pudo enviar correo a gestores.";
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(mensage).build();
        }catch (Exception e) {
            return Util.manageException(e, AnalisisDesviacionREST.class);
        }
    }
    
    @POST
    @Path("updateSeguimiento/{idAnalisisDesviacion}")
    @Secured(requiereEmpresaId = false)
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response updateSeguimiento(@PathParam("idAnalisisDesviacion") Integer idAnalisisDesviacion, String json){
        try {
            AnalisisDesviacion ad = analisisDesviacionFacade.find(new Integer(idAnalisisDesviacion));
            
            Gson gson = new Gson();
            JsonObject data = gson.fromJson(json, JsonObject.class);
            String seguimiento = data.get("seguimiento").getAsString();
            String observacion = data.get("observaciones").getAsString();
            ad.setSeguimiento(seguimiento);
            ad.setObservacion(observacion);
            
            DesviacionAliados da = desviacionAliadosFacade.findByIdAnalisisDesviacion(idAnalisisDesviacion);
            
            Map<String, String> parametros = new HashMap<>();
            parametros.put(EmailFacade.PARAM_ID, da.getId().toString());
            
            String emailAliado = empresaFacade.find(da.getAliadoId()).getEmail();
            TipoMail tipoEmail = null;
            JsonObject seguimientoObject = gson.fromJson(seguimiento, JsonObject.class);
            String estado = seguimientoObject.get("estado").toString();
            estado = estado.substring(1, estado.length() - 1);
            System.out.println(estado);
            if("Aprobado".equalsIgnoreCase(estado)){
                tipoEmail = TipoMail.REPORTE_ALIADO_APROBADO;
            }else if("Rechazado".equalsIgnoreCase(estado)){
                tipoEmail = TipoMail.REPORTE_ALIADO_RECHAZADO;
            }else {
                throw new IllegalArgumentException();
            }
            String asunto = estado.equalsIgnoreCase("Aprobado") ? "Reporte AT Corona Aprobado" : "Reporte AT Corona Rechazado";
            emailFacade.sendEmail(emailAliado, tipoEmail, asunto, parametros);
            ad = ((AnalisisDesviacionFacade) beanInstance).edit(ad);
            return Response.ok(ad).build();
        } catch (Exception e) {
            return Util.manageException(e, AnalisisDesviacionREST.class);
        }
    }
    
    @PUT
    @Path("analisisDesviacionMP")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    @Secured(requiereEmpresaId = false, validarPermiso = false)
    public Response editAnalisisDesviacionAliadoMP(AnalisisDesviacion analisisDesviacionNew){
        try {
            AnalisisDesviacion analisisDesviacion = analisisDesviacionFacade.find(new Integer(analisisDesviacionNew.getId()));
            analisisDesviacion.setMatrizPeligro(analisisDesviacionNew.getMatrizPeligro());
            analisisDesviacion = analisisDesviacionFacade.edit(analisisDesviacion);

            return Response.ok(analisisDesviacion).build();
        } catch (Exception e) {
            return Util.manageException(e, AnalisisDesviacionREST.class);
        }
    }
    
}
