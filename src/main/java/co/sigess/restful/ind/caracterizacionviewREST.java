/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.sigess.restful.ind;

import co.sigess.entities.ind.caracterizacionview;
import co.sigess.facade.ind.caracterizacionviewFACADE;
import co.sigess.restful.ServiceREST;
import co.sigess.restful.security.Secured;
import co.sigess.restful.security.TokenInterceptor;
import co.sigess.util.Util;
import com.google.gson.Gson;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.Arrays;
import java.util.Base64;
import java.util.List;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import javax.ejb.EJB;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
/**
 *
 * @author Usuario
 */
@Path("indcar")
public class caracterizacionviewREST extends ServiceREST{
    
    @EJB
    private caracterizacionviewFACADE caracterizacionviewFACADE;
    
    public caracterizacionviewREST(){
        super(caracterizacionviewFACADE.class);
    }
    
    @GET
    @Path("all")
    public Response findByAll(){
        try{
            List<caracterizacionview> list = this.caracterizacionviewFACADE.findByalll();
            Gson gson = new Gson();
            String json = gson.toJson(list);

            // Generar clave usando el token de autorización interceptada al login
            String authorizationHeader = TokenInterceptor.getToken(); 
            
            byte[] keyBytes = authorizationHeader.getBytes(StandardCharsets.UTF_8);
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            keyBytes = digest.digest(keyBytes);
            keyBytes = Arrays.copyOf(keyBytes, 16);
            
            SecretKeySpec aesKey = new SecretKeySpec(keyBytes, "AES");

            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, aesKey);
            byte[] encryptedBytes = cipher.doFinal(json.getBytes(StandardCharsets.UTF_8));
            String encryptedJson = Base64.getEncoder().encodeToString(encryptedBytes);
                        
            return Response.ok(encryptedJson).build();
        } catch(Exception ex){
            return Util.manageException(ex, caracterizacionviewREST.class);
        }
    }
    
    @GET
    @Path("all2")
    public Response findByAll2(){
        try{
            List<caracterizacionview> list = this.caracterizacionviewFACADE.findByalll();
            return Response.ok(list).build();
        } catch(Exception ex){
            return Util.manageException(ex, caracterizacionviewREST.class);
        }
    }
    
    
    
}
