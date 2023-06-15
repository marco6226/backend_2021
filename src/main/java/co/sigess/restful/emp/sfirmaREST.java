/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.sigess.restful.emp;

import co.sigess.entities.emp.firma;
import co.sigess.facade.emp.firmaFacade;
import co.sigess.restful.security.Auditable;
import co.sigess.restful.security.Secured;
import co.sigess.util.Util;
import java.util.List;
import javax.ejb.EJB;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 *
 * @author Usuario
 */
@Secured
@Path("firm")
public class sfirmaREST {
    @EJB
    private firmaFacade firmaFacade;
    
    @POST
    @Auditable
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response create(firma firma) {
        try {
            firma = firmaFacade.create(firma);
            return Response.ok(firma).build();
        } catch (Exception ex) {
            return Util.manageException(ex, firmaREST.class);
        }
    }
    
    @GET
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response findAll() {
        List<firma> list = firmaFacade.findAll();
        return Response.ok(list).build();
    }
}
