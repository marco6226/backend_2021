/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.sigess.restful.emp;

import co.sigess.entities.com.Mensaje;
import co.sigess.entities.com.TipoMensaje;
import co.sigess.entities.emp.EventoLog;
import co.sigess.entities.emp.Usuario;
import co.sigess.entities.emp.Empresa;
import co.sigess.entities.inp.ViewInspeccionesCtr;
import co.sigess.exceptions.UserMessageException;
import co.sigess.facade.core.EmailFacade;
import co.sigess.facade.core.TipoMail;
import co.sigess.facade.emp.EventoLogFacade;
import co.sigess.facade.emp.UsuarioFacade;
import co.sigess.restful.CriteriaFilter;
import co.sigess.restful.Filter;
import co.sigess.restful.FilterQuery;
import co.sigess.restful.FilterResponse;
import co.sigess.restful.ServiceREST;
import co.sigess.restful.security.Auditable;
import co.sigess.restful.security.Secured;
import co.sigess.util.Util;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ws.rs.BeanParam;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import java.io.ByteArrayOutputStream;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;
import java.util.concurrent.TimeUnit;
import co.sigess.restful.emp.AuthenticationREST;

/**
 *
 * @author fmoreno
 */
@Secured
@Path("usuario")
public class UsuarioREST extends ServiceREST {
    
    @PersistenceContext(unitName = "SIGESS_PU")
    private EntityManager em;

    @EJB
    private UsuarioFacade usuarioFacade;

    @EJB
    private EmailFacade emailFacade;
    
    @EJB
    private EventoLogFacade eventoLogFacade;

    public UsuarioREST() {
        super(UsuarioFacade.class);
    }

    @Override
    @GET
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response findWithFilter(@BeanParam FilterQuery filterQuery) {
        try {
            for (String field : filterQuery.getFieldList()) {
                if (field.equals("password")) {
                    Mensaje msg = new Mensaje(
                            "INTENTO ACCESO A PASSWORD",
                            "[Usuario: " + super.getUsuarioRequestContext().getEmail()
                            + "] [fecha:" + Util.SIMPLE_DATE_FORMAT_ISO.format(new Date()),
                            TipoMensaje.error
                    );
                    Logger.getLogger(UsuarioREST.class.getName()).log(Level.SEVERE, msg.toString());
                    this.usuarioFacade.bloquearUsuario(super.getUsuarioRequestContext());
                    return Response.status(Response.Status.FORBIDDEN).entity(new Mensaje()).build();
                }
            }

            boolean filtradoEmpresa = false;
            for (Filter filter : filterQuery.getFilterList()) {
                if (filter.getField().equals("usuarioEmpresaList.empresa.id")) {
                    filtradoEmpresa = true;
                }
            }

            if (!filtradoEmpresa) {
                Filter empFilt = new Filter();
                empFilt.setCriteriaEnum(CriteriaFilter.EQUALS);
                empFilt.setField("usuarioEmpresaList.empresa.id");
                empFilt.setValue1(getEmpresaIdRequestContext().toString());
                filterQuery.getFilterList().add(empFilt);
            }

            long numRows = filterQuery.isCount() ? usuarioFacade.countWithFilter(filterQuery) : -1;
            List list = usuarioFacade.findWithFilter(filterQuery);
            List<HashMap> listHash = new ArrayList();
            int repetidos = 0;
            for (Object object : list) {
                HashMap map = (HashMap) object;
                boolean contiene = false;
                for (HashMap hashMap : listHash) {
                    if (map.get("id").equals(hashMap.get("id"))) {
                        contiene = true;
                        repetidos += 1;
                        break;
                    }
                }
                if (!contiene) {
                    listHash.add(map);
                }
            }
            FilterResponse filterResponse = new FilterResponse();
            filterResponse.setData(listHash);
            filterResponse.setCount(numRows - repetidos);
            return Response.ok(filterResponse).build();
        } catch (Exception ex) {
            return Util.manageException(ex, UsuarioREST.class);
        }
    }

    @GET
    @Path("historiaLogin")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    @Secured(requiereEmpresaId = false)
    public Response consultarHistoriaLogin() {
        try {
            List<EventoLog> list = this.eventoLogFacade.consultarHistoriaLogin(super.getUsuarioRequestContext());
            FilterResponse filterResponse = new FilterResponse();
            filterResponse.setData(list);
            filterResponse.setCount(10);
            return Response.ok(filterResponse).build();
        } catch (Exception ex) {
            return Util.manageException(ex, AuthenticationREST.class);
        }
    }

    @PUT
    @Path("cambiarPasswd")
    @Produces(MediaType.APPLICATION_JSON)
    @Secured(requiereEmpresaId = false)
    @Auditable(persistData = false)
    public Response cambiarPasswd(Map<String, String> param) {
        try {
            String oldPasswd = param.get("passwdAnterior");
            String newPasswd = param.get("passwdNuevo");
            String newPasswdConfirm = param.get("passwdNuevoConfirm");

            Usuario user = usuarioFacade.cambiarPasswd(super.getUsuarioRequestContext().getId(), newPasswd, newPasswdConfirm, oldPasswd);
            return Response.ok(user).build();
        } catch (Exception ex) {
            return Util.manageException(ex, AuthenticationREST.class);
        }
    }

    @POST
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    @Auditable
    public Response create(Usuario usuario) {
        try {
            usuarioFacade.create(usuario, super.getEmpresaIdRequestContext(),true);
            return Response.ok(usuario).build();
        } catch (Exception ex) {
            return Util.manageException(ex, UsuarioREST.class);
        }
    }

    @POST
    @Path("aliado/{idEmpresaAliada}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response createUsuarioAliado(Usuario usuario, @PathParam("idEmpresaAliada") Integer idEmpresaAliada) {
        try {
            
            String host1;
            Query q1 = em.createNativeQuery("SELECT h from com.host h where h.host ='Produccion'");
            List hostin =q1.getResultList();

            if(hostin.size()==0){
                host1="https://demo.sigess.app";
            }else{
                host1="https://sigess.app";
            }
            
            usuarioFacade.create(usuario, idEmpresaAliada,true);
            //TimeUnit.SECONDS.sleep(5);
            Query q = this.em.createNativeQuery("SELECT * FROM emp.empresa  WHERE id = ?1 ",Empresa.class);
            q.setParameter(1,Integer.parseInt(idEmpresaAliada.toString()));
            List<Empresa> list = (List<Empresa>) q.getResultList();
            
            String nit = list.get(0).getNit();
            String razonSocial = list.get(0).getRazonSocial();
            
            Map<String, String> parametros = new HashMap<>();
            
            parametros.put(EmailFacade.PARAM_ID,idEmpresaAliada.toString());
            parametros.put(EmailFacade.PARAM_NOMBRE,razonSocial);
            parametros.put(EmailFacade.PARAM_NIT,nit);
            parametros.put(EmailFacade.PARAM_HOST1,host1);
            emailFacade.sendEmail(parametros, TipoMail.ALIADO_NUEVO, "Aliado Nuevo", usuario.getEmail());
            
            return Response.ok(usuario).build();
        } catch (Exception ex) {
            return Util.manageException(ex, UsuarioREST.class);
        }
    }
    
    @PUT
    @Path("aliadoActualizar/{idEmpresaAliada}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response actualziarAliado(String email, @PathParam("idEmpresaAliada") Integer idEmpresaAliada) {
        try {     
            
            String host1;
            Query q1 = em.createNativeQuery("SELECT h from com.host h where h.host ='Produccion'");
            List hostin =q1.getResultList();

            if(hostin.size()==0){
                host1="https://demo.sigess.app";
            }else{
                host1="https://sigess.app";
            }
            //TimeUnit.SECONDS.sleep(5);
            Query q = this.em.createNativeQuery("SELECT * FROM emp.empresa  WHERE id = ?1 ",Empresa.class);
            q.setParameter(1,Integer.parseInt(idEmpresaAliada.toString()));
            List<Empresa> list = (List<Empresa>) q.getResultList();
            
            String nit = list.get(0).getNit();
            String razonSocial = list.get(0).getRazonSocial();
            
            Map<String, String> parametros = new HashMap<>();
            parametros.put(EmailFacade.PARAM_ID,idEmpresaAliada.toString());
            parametros.put(EmailFacade.PARAM_NOMBRE,razonSocial);
            parametros.put(EmailFacade.PARAM_NIT,nit);
            parametros.put(EmailFacade.PARAM_HOST1,host1);
            emailFacade.sendEmail(parametros, TipoMail.ALIADO_NUEVO, "Aliado Actualizar Información", email);
            return Response.ok().build();
        } catch (Exception ex) {
            return Util.manageException(ex, UsuarioREST.class);
        }
    }
    
    @PUT
    @Path("emailAliadoActualizado/{idEmpresaAliada}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response aliadoActualizado(String email, @PathParam("idEmpresaAliada") Integer idEmpresaAliada) {
        try {    
            String host1;
            Query q1 = em.createNativeQuery("SELECT h from com.host h where h.host ='Produccion'");
            List hostin =q1.getResultList();

            if(hostin.size()==0){
                host1="https://demo.sigess.app";
            }else{
                host1="https://sigess.app";
            }
            
            Query q = this.em.createNativeQuery("SELECT * FROM emp.empresa  WHERE id = ?1 ",Empresa.class);
            q.setParameter(1,Integer.parseInt(idEmpresaAliada.toString()));
            System.out.println(q);
            List<Empresa> list = (List<Empresa>) q.getResultList();
            
            String nit = list.get(0).getNit();
            String razonSocial = list.get(0).getRazonSocial();

            Map<String, String> parametros = new HashMap<>();
            parametros.put(EmailFacade.PARAM_ID,idEmpresaAliada.toString());
            parametros.put(EmailFacade.PARAM_NOMBRE,razonSocial);
            parametros.put(EmailFacade.PARAM_NIT,nit);
            parametros.put(EmailFacade.PARAM_HOST1,host1);
            emailFacade.sendEmail(parametros, TipoMail.ALIADO_ACTUALIZADO, "el Aliado Actualizo la Información", email);
            return Response.ok().build();
        } catch (Exception ex) {
            return Util.manageException(ex, UsuarioREST.class);
        }
    }
    
    @Secured(validarPermiso = false)
    @PUT
    @Path("emailAliadoCicloCorto/{id}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response aliadoCicloCorto(String email, @PathParam("id") String idInspeccionCicloCorto) {
        try {    
            System.out.println(email);
            
            String host1;
            Query q1 = em.createNativeQuery("SELECT h from com.host h where h.host ='Produccion'");
            List hostin =q1.getResultList();

            if(hostin.size()==0){
                host1="https://demo.sigess.app";
            }else{
                host1="https://sigess.app";
            }
            
            Query q = this.em.createNativeQuery("SELECT * FROM inp.vw_inspeccion_ctr  WHERE id = ?1 ",ViewInspeccionesCtr.class);
            q.setParameter(1,idInspeccionCicloCorto);
            //System.out.println(q);
            List<ViewInspeccionesCtr> list = (List<ViewInspeccionesCtr>) q.getResultList();
            
            String razonsocial = list.get(0).getEmpresaAliada().getRazonSocial();
            //String razonSocial = list.get(0).getRazonSocial();

            Map<String, String> parametros = new HashMap<>();
            
            parametros.put(EmailFacade.PARAM_HOST1,host1);
            parametros.put(EmailFacade.PARAM_INSP,idInspeccionCicloCorto);
            parametros.put(EmailFacade.PARAM_RAZONSOCIAL,razonsocial);
            
            emailFacade.sendEmail(parametros, TipoMail.ALIADO_ACTUALIZADO_CICLOCORTO, "Correo ciclo corto", email);
            return Response.ok().build();
        } catch (Exception ex) {
            return Util.manageException(ex, UsuarioREST.class);
        }
    }

    @PUT
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    @Auditable
    public Response edit(Usuario usuario) {
        try {
            usuario = usuarioFacade.edit(usuario, super.getEmpresaIdRequestContext());
            return Response.ok(usuario).build();
        } catch (Exception ex) {
            return Util.manageException(ex, UsuarioREST.class);
        }
    }

    @PUT
    @Path("update")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response update(Usuario usuario) {
        try {
            if (!usuario.getId().equals(super.getUsuarioRequestContext().getId())) {
                throw new UserMessageException("Operación no permitida", "No puede editar datos de otro usuario", TipoMensaje.error);
            }
            usuario = usuarioFacade.update(usuario, super.getEmpresaIdRequestContext());
            return Response.ok(usuario).build();
        } catch (Exception ex) {
            return Util.manageException(ex, UsuarioREST.class);
        }
    }

    @DELETE
    @Path("{usuarioId}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    @Auditable
    public Response delete(@PathParam("usuarioId") Integer usuarioId) {
        try {
            usuarioFacade.eliminar(usuarioId);
            return Response.ok(usuarioId).build();
        } catch (Exception ex) {
            return Util.manageException(ex, UsuarioREST.class);
        }
    }

    @PUT
    @Path("terminos/{aceptar}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    @Auditable()
    @Secured(validarPermiso = false)
    public Response aceptarTerminos(@PathParam("aceptar") Boolean aceptar) {
        try {
            usuarioFacade.aceptarTerminos(super.getUsuarioRequestContext(), aceptar);
            return Response.ok().build();
        } catch (Exception ex) {
            return Util.manageException(ex, UsuarioREST.class);
        }
    }
    
    @GET
    @Path("consusuarios")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_OCTET_STREAM})
    public Response consultarConsolidado(
            @QueryParam("empresa") int empresa
            
    ) {
        
        
        try {
            ByteArrayOutputStream out = ((UsuarioFacade) super.beanInstance).consultarConsolidado(super.getEmpresaIdRequestContext());
            return Response.ok(out.toByteArray(), MediaType.APPLICATION_OCTET_STREAM_TYPE).build();
        } catch (Exception ex) {
            return Util.manageException(ex, UsuarioREST.class);
        }
    }

}
