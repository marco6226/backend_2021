/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.sigess.restful.ind;

import co.sigess.entities.ind.IndicadorAusentismo;
import co.sigess.entities.ind.ModeloGrafica;
import co.sigess.entities.ind.ModeloIndicador;
import co.sigess.facade.ind.IndicadorAusentismoFacade;
import co.sigess.facade.ind.IndicadorEmpresaFacade;
import co.sigess.facade.ind.IndicadorRaiFacade;
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
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 *
 * @author fmoreno
 */
@Secured
@Path("indicadorEmpresa")
public class IndicadorEmpresaREST extends ServiceREST {

    @EJB
    private IndicadorEmpresaFacade indicadorEmpresaFacade;

    @GET
    @Path("evaluacionDesempeno")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response findAll(@QueryParam("rango") String rango, @QueryParam("empleado_id") Integer empleadoId) {
        try {
            List<ModeloIndicador> list = indicadorEmpresaFacade.obtenerEvaluacionDesempeno(super.getEmpresaIdRequestContext(), rango, empleadoId);
            return Response.ok(list).build();
        } catch (Exception ex) {
            return Util.manageException(ex, TareaDesviacionREST.class);
        }
    }

}
