/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.sigess.restful.emp;

import co.sigess.entities.scm.Anexos;
import co.sigess.facade.emp.firmaFacade;
import co.sigess.restful.ServiceREST;
import co.sigess.restful.scm.AnexosREST;
import co.sigess.restful.security.Auditable;
import co.sigess.restful.security.Secured;
import co.sigess.util.Util;
import javax.ejb.EJB;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import co.sigess.entities.emp.firma;

/**
 *
 * @author Usuario
 */
@Secured
@Path("firm")
public class firmaREST extends ServiceREST{
    @EJB
    private firmaFacade firmaFacade;
    
    public firmaREST(){
        super(firmaFacade.class);
    }
    
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
}
