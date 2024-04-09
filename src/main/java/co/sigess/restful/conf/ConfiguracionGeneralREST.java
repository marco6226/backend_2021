/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.sigess.restful.conf;

import co.sigess.entities.conf.ConfiguracionGeneral;
import co.sigess.entities.conf.SistemaNivelRiesgo;
import co.sigess.facade.conf.ConfiguracionGeneralFacade;
import co.sigess.facade.conf.SistemaNivelRiesgoFacade;
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
 * @author fmoreno
 */
@Secured
@Path("configuracion")
public class ConfiguracionGeneralREST extends ServiceREST {

    @EJB
    private ConfiguracionGeneralFacade confGenFacade;

    public ConfiguracionGeneralREST() {
        super(ConfiguracionGeneralFacade.class);
    }

    @GET
    @Path("empresa")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response findByEmpresa() {
        try {
            List<ConfiguracionGeneral> list = confGenFacade.buscarPorEmpresa(super.getEmpresaIdRequestContext());
            return Response.ok(list).build();
        } catch (Exception ex) {
            return Util.manageException(ex, ConfiguracionGeneralREST.class);
        }
    }
}
