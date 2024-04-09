/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.sigess.restful.conf;

import co.sigess.entities.conf.SistemaNivelRiesgo;
import co.sigess.facade.conf.SistemaNivelRiesgoFacade;
import co.sigess.restful.ServiceREST;
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
@Path("sistemaNivelRiesgo")
public class SistemaNivelRiesgoREST extends ServiceREST {

    @EJB
    private SistemaNivelRiesgoFacade sistemaNivelRiesgoFacade;

    public SistemaNivelRiesgoREST() {
        super(SistemaNivelRiesgoFacade.class);
    }

    @GET
    @Path("default")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response findDefault() {
        try {
            SistemaNivelRiesgo snr = sistemaNivelRiesgoFacade.findDefault(super.getEmpresaIdRequestContext());
            return Response.ok(snr).build();
        } catch (Exception ex) {
            return Util.manageException(ex, SistemaNivelRiesgoREST.class);
        }
    }
}
