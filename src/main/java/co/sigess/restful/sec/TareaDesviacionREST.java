/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.sigess.restful.sec;

import co.sigess.entities.emp.Empresa;
import co.sigess.entities.sec.TareaDesviacion;
import co.sigess.facade.emp.EmpresaFacade;
import co.sigess.facade.sec.TareaDesviacionFacade;
import co.sigess.restful.FilterQuery;
import co.sigess.restful.ServiceREST;
import co.sigess.restful.security.Secured;
import co.sigess.restful.security.ValidadorArea;
import co.sigess.util.Util;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.Arrays;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import javax.ejb.EJB;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.glassfish.jersey.media.multipart.FormDataParam;

/**
 *
 * @author fmoreno
 */
@Secured
@Path("tarea")
public class TareaDesviacionREST extends ServiceREST {

    @EJB
    private TareaDesviacionFacade tareaDesviacionFacade;
    
    @EJB
    private EmpresaFacade empresaFacade;

    public TareaDesviacionREST() {
        super(TareaDesviacionFacade.class);
    }    

    //@ValidadorArea("areaResponsable.id")
    @Override
    public Response findWithFilter(FilterQuery filterQuery) {
        return super.findWithFilter(filterQuery);
    }

    @POST
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response create(TareaDesviacion tarea) {
        try {
            tarea.setEmpresa(new Empresa(super.getEmpresaIdRequestContext()));
            tarea = tareaDesviacionFacade.create(tarea);
            return Response.ok(tarea).build();
        } catch (Exception ex) {
            return Util.manageException(ex, TareaDesviacionREST.class);
        }
    }
    
    @PUT
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response edit(TareaDesviacion tarea) {
        try {
            tarea.setEmpresa(new Empresa(super.getEmpresaIdRequestContext()));
            tarea = tareaDesviacionFacade.edit(tarea);
            return Response.ok(tarea).build();
        } catch (Exception ex) {
            return Util.manageException(ex, TareaDesviacionREST.class);
        }
    }


    @PUT
    @Secured(validarPermiso = false)
    @Path("close")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_JSON})
//    public Response closeTask(TareaDesviacion tarea) {
    public Response closeTask(@HeaderParam("Authorization") String authorizationHeader,
            @FormDataParam("data") String encryptedId) {

        try {
            
            byte[] keyBytes = authorizationHeader.getBytes(StandardCharsets.UTF_8);
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            keyBytes = digest.digest(keyBytes);
            keyBytes = Arrays.copyOf(keyBytes, 16);

            SecretKeySpec aesKey = new SecretKeySpec(keyBytes, "AES");

            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            cipher.init(Cipher.DECRYPT_MODE, aesKey);
            byte[] decryptedBytes = cipher.doFinal(Base64.getDecoder().decode(encryptedId));
            String textoDesencriptado = new String(decryptedBytes, StandardCharsets.UTF_8);

            ObjectMapper objectMapper = new ObjectMapper();
            TareaDesviacion tarea = objectMapper.readValue(textoDesencriptado, TareaDesviacion.class);

            
            
            Empresa empresaUsuario = empresaFacade.find(super.getEmpresaIdRequestContext());
            if(empresaUsuario.getIdEmpresaAliada() == null){
                tarea.setEmpresa(new Empresa(empresaUsuario.getId()));
            } else {
                tarea.setEmpresa(new Empresa(empresaUsuario.getIdEmpresaAliada()));
            }
            tarea = tareaDesviacionFacade.closeTask(tarea);
            return Response.ok(tarea).build();
        } catch (Exception ex) {
            return Util.manageException(ex, TareaDesviacionREST.class);
        }
    }    

    @GET
    @Secured(validarPermiso = false)
    @Path("{tareaId}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response find(@PathParam("tareaId") Integer tareaId) {
        try {
            TareaDesviacion tareaDesviacion = ((TareaDesviacionFacade) beanInstance).find(tareaId);
            return Response.ok(tareaDesviacion).build();
        } catch (Exception ex) {
            return Util.manageException(ex, AnalisisDesviacionREST.class);
        }
    }
    
    @GET
    @Secured(validarPermiso = false)
    @Path("images/{tareaId}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response findImages(@PathParam("tareaId") Integer tareaId) {
        try {
             HashMap<String, List<String>> file = tareaDesviacionFacade.getImages(tareaId);
            return Response.ok(file).build();
        } catch (Exception ex) {
            return Util.manageException(ex, AnalisisDesviacionREST.class);
        }
    }
    
    @GET
    @Secured(validarPermiso = false)
    @Path("images/{tareaId}/{modulo}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response findImages(@PathParam("tareaId") Integer tareaId, @PathParam("modulo") String modulo) {
        try {
             HashMap<String, List<String>> file = tareaDesviacionFacade.getImages(tareaId, modulo);
            return Response.ok(file).build();
        } catch (Exception ex) {
            return Util.manageException(ex, AnalisisDesviacionREST.class);
        }
    }

    @PUT
    @Path("reportarCumplimiento")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response reportarCumplimiento(TareaDesviacion tarea) {
        try {
            tarea = tareaDesviacionFacade.reportarCumplimiento(tarea, super.getUsuarioRequestContext());
            return Response.ok(tarea).build();
        } catch (Exception ex) {
            return Util.manageException(ex, TareaDesviacionREST.class);
        }
    }

    @PUT
    @Path("reportarVerificacion")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response reportarVerificacion(TareaDesviacion tarea) {
        try {
            tarea = tareaDesviacionFacade.reportarVerificacion(tarea, super.getUsuarioRequestContext());
            return Response.ok(tarea).build();
        } catch (Exception ex) {
            return Util.manageException(ex, TareaDesviacionREST.class);
        }
    }

    @GET
    @Path("analisis/{analisisId}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response findByAnalisis(@PathParam("analisisId") Integer analisisId) {
        try {
            List<TareaDesviacion> list = tareaDesviacionFacade.findByAnalisisEmpresa(analisisId, super.getEmpresaIdRequestContext());
            return Response.ok(list).build();
        } catch (Exception ex) {
            return Util.manageException(ex, TareaDesviacionREST.class);
        }
    }

        

    @GET
    @Secured(validarPermiso = false)
    @Path("detail/{tareaId}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response findByDetailId(@PathParam("tareaId") Integer tareaId) {
        try {
           
            String json = tareaDesviacionFacade.findById(tareaId);
            return Response.ok(json).build();
        } catch (Exception ex) {
            return Util.manageException(ex, TareaDesviacionREST.class);
        }
    }
    
    @GET
    @Secured(validarPermiso = false)
    @Path("detalle/{types}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response findWithDetails(@PathParam("types")String type) {   
        try {
            String[] arrString = type.split(",");
              int[] areasId = new int[arrString.length];
              for (int i = 0; i < arrString.length; i++) {
                  areasId[i] = Integer.parseInt(arrString[i]);
                
            }
            Empresa emp;
            emp = empresaFacade.findEmpresaById(super.getEmpresaIdRequestContext());
            String json = null;
            if(emp.getIdEmpresaAliada() == null){
                json = tareaDesviacionFacade.findWithDetails(emp.getId(),arrString);
            } else {
                json = tareaDesviacionFacade.findWithDetails(emp.getIdEmpresaAliada(), arrString);
            }
            return Response.ok(json).build();
        } catch (Exception ex) {
            return Util.manageException(ex, TareaDesviacionREST.class);
        }
    }
    
    @GET
    @Secured(validarPermiso = false)
    @Path("details/{id}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response findWithDetailsByEmpleado(@PathParam("id")Integer id) {
        try {
            Empresa emp;
            emp = new Empresa (super.getEmpresaIdRequestContext());
            String json = null;
            if(emp.getIdEmpresaAliada() == null) {
                json = tareaDesviacionFacade.findWithDetailsByEmpleado(emp.getId(), id);
            } else {
                json = tareaDesviacionFacade.findWithDetailsByEmpleado(emp.getIdEmpresaAliada(), id);
            }
            return Response.ok(json).build();
        } catch (Exception ex) {
            return Util.manageException(ex, TareaDesviacionREST.class);
        }
    }
    
    
    @GET
    @Path("usuario/{usuarioId}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response findByUsuario(@PathParam("usuarioId") Integer usuarioId) {
        try {
            if (!super.getUsuarioRequestContext().getId().equals(usuarioId)) {
                // Bloquear usuario
            }
            List<TareaDesviacion> list = tareaDesviacionFacade.findByUsuarioEmpresa(usuarioId, super.getEmpresaIdRequestContext());
            return Response.ok(list).build();
        } catch (Exception ex) {
            return Util.manageException(ex, TareaDesviacionREST.class);
        }
    }

    @DELETE
    @Path("{tareaDesviacionId}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response delete(@PathParam("tareaDesviacionId") Integer tareaDesviacionId) {
        try {
            TareaDesviacion tarea = tareaDesviacionFacade.remove(tareaDesviacionId);
            return Response.ok(tarea).build();
        } catch (Exception ex) {
            return Util.manageException(ex, TareaDesviacionREST.class);
        }
    }

    @GET
    @Path("seguimiento/{id}")
    @Secured(validarPermiso = false)
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response seguimientoTarea(@PathParam("id") String idTarea) {
        try{
            Integer id = new Integer(idTarea);
            Object object = tareaDesviacionFacade.obtenerSeguimientoTarea(id);
            return Response.ok(object).build();
        }catch(Exception ex){
            System.out.println(ex.toString());
            return Util.manageException(ex, TareaDesviacionREST.class);
        }
    }
}
