/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.sigess.restful.emp;

import co.sigess.entities.emp.ConfiguracionJornada;
import co.sigess.facade.emp.ConfiguracionJornadaFacade;
import co.sigess.restful.ServiceREST;
import co.sigess.restful.security.Secured;
import co.sigess.util.Util;
import javax.ejb.EJB;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 *
 * @author fmoreno
 */
@Secured
@Path("configuracionJornada")
public class ConfiguracionJornadaREST extends ServiceREST{

    @EJB
    private ConfiguracionJornadaFacade configuracionJornadaFacade;

    @POST
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response create(ConfiguracionJornada configuracionJornada) {
        try {
            configuracionJornada = this.configuracionJornadaFacade.create(configuracionJornada);
            return Response.ok(configuracionJornada).build();
        } catch (Exception ex) {
            return Util.manageException(ex, ConfiguracionJornadaREST.class);
        }
    }

    @PUT
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response edit(ConfiguracionJornada configuracionJornada) {
        try {
            configuracionJornada = this.configuracionJornadaFacade.edit(configuracionJornada);
            return Response.ok(configuracionJornada).build();
        } catch (Exception ex) {
            return Util.manageException(ex, ConfiguracionJornadaREST.class);
        }
    }


}
