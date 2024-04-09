/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.sigess.restful.ind;

import co.sigess.entities.ind.Indicador;
import co.sigess.entities.ind.ModeloGrafica;
import co.sigess.facade.ind.IndicadorFacade;
import co.sigess.restful.FilterResponse;
import co.sigess.restful.Request;
import co.sigess.restful.sec.TareaDesviacionREST;
import co.sigess.restful.security.Secured;
import co.sigess.util.Util;
import java.util.List;
import java.util.Map;
import javax.ejb.EJB;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 *
 * @author fmoreno
 */
@Secured
@Path("indicador")
public class IndicadorREST extends Request {

    @EJB
    private IndicadorFacade indicadorFacade;

    @GET
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response findAll() {
        try {
            List<Indicador> list = indicadorFacade.findAll();
            FilterResponse filterResponse = new FilterResponse();
            filterResponse.setData(list);
            filterResponse.setCount(list.size());
            return Response.ok(filterResponse).build();
        } catch (Exception ex) {
            return Util.manageException(ex, TareaDesviacionREST.class);
        }
    }

    @GET
    @Path("data")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response consultarIndicador(@QueryParam("param") String param) {
        try {
            ModeloGrafica model = indicadorFacade.consultarIndicador(param);
            return Response.ok(model).build();
        } catch (Exception ex) {
            return Util.manageException(ex, TareaDesviacionREST.class);
        }
    }
    
    
    @PUT
    @Path("kpi")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response actualizarKpi(String param) {
        try {
            indicadorFacade.actualizarKpi(super.getEmpresaIdRequestContext(), param);
            return Response.ok().build();
        } catch (Exception ex) {
            return Util.manageException(ex, TareaDesviacionREST.class);
        }
    }

}
