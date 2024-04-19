/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.sigess.restful.sec;

import co.sigess.entities.emp.Empresa;
import co.sigess.entities.sec.SeguimientoTarea;
import co.sigess.entities.sec.TareaDesviacion;
import co.sigess.facade.sec.SeguimientoTareaFacade;
import co.sigess.facade.sec.TareaDesviacionFacade;
import co.sigess.restful.CriteriaFilter;
import co.sigess.restful.Filter;
import co.sigess.restful.FilterQuery;
import co.sigess.restful.ServiceREST;
import co.sigess.restful.ado.DirectorioREST;
import co.sigess.restful.security.Secured;
import co.sigess.util.Util;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.Consumes;
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
 * @author Leo
 */
@Secured
@Path("follow")
public class SeguimientoTareaREST extends ServiceREST {

    @EJB
    private SeguimientoTareaFacade seguimientoTareaFacade;
    
   @Secured(validarPermiso = false)
   @POST
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_JSON})
    public Response create(SeguimientoTarea tarea) {
        try {
           // tarea.setEmpresa(new Empresa(super.getEmpresaIdRequestContext()));
            tarea = seguimientoTareaFacade.create(tarea);
            return Response.ok(tarea).build();
        } catch (Exception ex) {
            return Util.manageException(ex, SeguimientoTareaREST.class);
        }
    }
    
    
//    @GET
//    @Secured(validarPermiso = false)
//    @Path("{tareaId}")
//    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_JSON})
//    public Response find(@PathParam("tareaId") Integer tareaId) {
//        try {
//        FilterQuery fq = new FilterQuery();
//        List<Filter> fl = new ArrayList<>();
//        Filter filter = new Filter("tareaId", Integer.toString(tareaId), null, CriteriaFilter.EQUALS);
//        fl.add(filter);
//        fq.setFilterList(fl);
//            List<SeguimientoTarea> seguimientoTarea = seguimientoTareaFacade.findWithFilter(fq);
//            return Response.ok(seguimientoTarea).build();
//        } catch (Exception ex) {
//            return Util.manageException(ex, AnalisisDesviacionREST.class);
//        }
//    }
    
    @POST
    @Secured(validarPermiso = false)
//    @Path("{tareaId}")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_JSON})
//    public Response find(@PathParam("tareaId") Integer tareaId) {
    public Response find(@HeaderParam("Authorization") String authorizationHeader,
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
            
            
            FilterQuery fq = new FilterQuery();
            List<Filter> fl = new ArrayList<>();
            Filter filter = new Filter("tareaId", textoDesencriptado, null, CriteriaFilter.EQUALS);
            fl.add(filter);
            fq.setFilterList(fl);
            List<SeguimientoTarea> seguimientoTarea = seguimientoTareaFacade.findWithFilter(fq);
            
            ObjectMapper objectMapper = new ObjectMapper();
            String seguimientoTareaJson = objectMapper.writeValueAsString(seguimientoTarea);

            // Cifrar la respuesta en JSON
            cipher.init(Cipher.ENCRYPT_MODE, aesKey);
            byte[] encryptedBytes = cipher.doFinal(seguimientoTareaJson.getBytes(StandardCharsets.UTF_8));
            String encryptedResponse = Base64.getEncoder().encodeToString(encryptedBytes);
            
//            return Response.ok(seguimientoTarea).build();
            return Response.ok(encryptedResponse).build();

        } catch (Exception ex) {
            return Util.manageException(ex, AnalisisDesviacionREST.class);
        }
    }
    

      
    @GET
    @Secured(validarPermiso = false)
    @Path("download/{id}/{type}")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_OCTET_STREAM})
    public Response downloadFileV2(@PathParam("id") Integer documentoId,@PathParam("type") String type) throws Exception {
        try {
                
           
            HashMap<String, List<String>> file = seguimientoTareaFacade.findFileV2(documentoId,type);
     
            return Response.ok(file , MediaType.APPLICATION_JSON).build();
        
        } catch (Exception ex) {
            return Util.manageException(ex, DirectorioREST.class);
        }
    }
    
}
