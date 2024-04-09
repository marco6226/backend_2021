/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.sigess.restful.conf;

import co.sigess.facade.com.OfflineServiceFacade;
import co.sigess.restful.Request;
import co.sigess.restful.security.Secured;
import co.sigess.util.Util;
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
@Secured
@Path("offlineService")
public class OfflineServiceREST extends Request {

    @EJB
    private OfflineServiceFacade offlineServiceFacade;

    @GET
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response getData() {
        try {
            Object result = offlineServiceFacade.cargarDatos(super.getEmpresaIdRequestContext(), super.getUsuarioRequestContext().getId());
            return Response.ok(result).build();
        } catch (Exception ex) {
            return Util.manageException(ex, SistemaNivelRiesgoREST.class);
        }
    }

}
