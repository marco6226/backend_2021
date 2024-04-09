/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.sigess.restful.com;

import co.sigess.entities.com.Ccf;
import co.sigess.facade.com.CcfFacade;
import java.util.List;
import javax.ejb.EJB;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 *
 * @author fmoreno
 */
@Path("ccf")
public class CcfREST {

    @EJB
    private CcfFacade ccfFacade;

    @GET
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response findAll() {
        List<Ccf> list = ccfFacade.findAll();
        return Response.ok(list).build();
    }
}
