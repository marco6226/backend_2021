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
import co.sigess.util.Util;
import java.util.List;
import javax.ejb.EJB;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

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
            System.out.println("Entre");
            List<caracterizacionview> list = this.caracterizacionviewFACADE.findByalll();
            System.out.println(list);
            return Response.ok(list).build();
        } catch(Exception ex){
            System.out.println("catch");
            return Util.manageException(ex, caracterizacionviewREST.class);
        }
    }
    
    
    
}
