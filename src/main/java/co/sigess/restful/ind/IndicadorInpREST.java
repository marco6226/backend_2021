/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.sigess.restful.ind;

import co.sigess.entities.ind.ModeloGrafica;
import co.sigess.facade.ind.IndicadorInpFacade;
import co.sigess.restful.ServiceREST;
import co.sigess.restful.sec.TareaDesviacionREST;
import co.sigess.restful.security.Secured;
import co.sigess.util.Util;
import java.util.List;
import javax.ejb.EJB;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 *
 * @author fmoreno
 */
@Secured
@Path("indicadorInp")
public class IndicadorInpREST extends ServiceREST {

    @EJB
    private IndicadorInpFacade indicadorInpFacade;

    @GET
    @Path("{areaId}/{rango}/{empresaId}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response findAll(@PathParam("areaId") Long areaId, @PathParam("rango") String rango, @PathParam("empresaId") Integer empresaId) {
        try {
            List<ModeloGrafica> list = indicadorInpFacade.find(empresaId <= 0 ? super.getEmpresaIdRequestContext() : empresaId, areaId < 0 ? null : areaId, rango);
            return Response.ok(list).build();
        } catch (Exception ex) {
            return Util.manageException(ex, TareaDesviacionREST.class);
        }
    }
      
     @GET
    @Path("test/{aresaId}/{rango}/{empresaId}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response getInspInd(@PathParam("areasId") String areasId, @PathParam("rango") String rango, @PathParam("empresaId") Integer empresaId) {
        try {
              
           return Response.ok().build();
        } catch (Exception ex) {
            return Util.manageException(ex, TareaDesviacionREST.class);
        }
    }

}
