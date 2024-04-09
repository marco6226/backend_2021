/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.sigess.restful;

import co.sigess.facade.core.EmailFacade;
import co.sigess.facade.core.TipoMail;
import co.sigess.restful.ServiceREST;
import co.sigess.util.Util;
import java.util.Map;
import javax.ejb.EJB;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 *
 * @author fmoreno
 */
@Path("contacto")
public class ContactoREST {

    @EJB
    private EmailFacade emailFacade;

    @POST
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response create(Map<String, String> map) {
        try {
            String nombres = map.get("nombres");
            String email = map.get("email");
            String msg = nombres + " escribió: " + map.get("mensaje");
            emailFacade.sendEmail(msg, "[CONTACTO] " + email, "soporte@sigess.app");
            return Response.ok().build();
        } catch (Exception ex) {
            return Util.manageException(ex, ContactoREST.class);
        }
    }
}
