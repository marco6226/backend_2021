/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.sigess.restful.scm;

import co.sigess.entities.emp.Empresa;
import co.sigess.entities.rai.Reporte;
import co.sigess.entities.scm.CasosMedicos;
import co.sigess.facade.scm.CasosMedicosFacade;
import co.sigess.restful.ServiceREST;
import co.sigess.restful.rai.ReporteREST;
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
 * @author leonardo
 */

@Path("casomedico")
public class CasoMedicoREST  extends ServiceREST {
    
    @EJB
    private CasosMedicosFacade casosmedicosFacade;
  
    public CasoMedicoREST() {
        super(CasosMedicosFacade.class);
    }

    @GET
    @Path("all")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response list() {
        try {
            List list = this.casosmedicosFacade.findAll();
            return Response.ok(list).build();
        } catch (Exception ex) {
            return Util.manageException(ex, ReporteREST.class);
        }
    }
    
    
    @POST
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response create(CasosMedicos casosmedicos) {
        try {
            casosmedicos = this.casosmedicosFacade.create(casosmedicos);
            return Response.ok(casosmedicos.getId()).build();
        } catch (Exception ex) {
            return Util.manageException(ex, ReporteREST.class);
        }
    }
    
    
   
    
}
