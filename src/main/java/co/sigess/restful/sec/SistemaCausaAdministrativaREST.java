/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.sigess.restful.sec;

import co.sigess.entities.sec.SistemaCausaAdministrativa;
import co.sigess.facade.sec.SistemaCausaAdministrativaFacade;
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
@Path("sistemaCausaAdministrativa")
public class SistemaCausaAdministrativaREST extends ServiceREST {

    @EJB
    private SistemaCausaAdministrativaFacade administrativaFacade;

    @GET
    @Path("seleccionado")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response findDefault() {
        try {
            SistemaCausaAdministrativa sca = administrativaFacade.getDefault(super.getEmpresaIdRequestContext());
            return Response.ok(sca).build();
        } catch (Exception ex) {
            return Util.manageException(ex, SistemaCausaAdministrativaREST.class);
        }
    }

}
