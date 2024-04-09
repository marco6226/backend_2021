/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.sigess.restful.com;

import co.sigess.entities.com.Eps;
import co.sigess.facade.com.EpsFacade;
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
@Path("eps")
public class EpsREST {

    @EJB
    private EpsFacade epsFacade;

    @GET
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response findAll() {
        List<Eps> list = epsFacade.findAll();
        return Response.ok(list).build();
    }
}
