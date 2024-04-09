/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.sigess.restful.ind;

import co.sigess.entities.ind.IndicadorAusentismo;
import co.sigess.entities.ind.ModeloIndicador;
import co.sigess.facade.ind.IndicadorAusentismoFacade;
import co.sigess.restful.FilterQuery;
import co.sigess.restful.Request;
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
@Path("indicadorAusentismo")
public class IndicadorAusentismoREST extends Request {

    @EJB
    private IndicadorAusentismoFacade indicadorAusentismoFacade;

    @GET
    @Path("{anio}/{causaId}/{empresaId}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response findAll(@PathParam("anio") Integer anio, @PathParam("causaId") Integer causaId, @PathParam("empresaId") Integer empresaId) {
        try {
            List<IndicadorAusentismo> list = indicadorAusentismoFacade.findByAnio(empresaId <= 0 ? super.getEmpresaIdRequestContext() : empresaId, anio, causaId);
            return Response.ok(list).build();
        } catch (Exception ex) {
            return Util.manageException(ex, TareaDesviacionREST.class);
        }
    }

    @GET
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response find( @QueryParam("empresa_id") Integer empresaId, @QueryParam("rango") String rango) {
        try {
            List<ModeloIndicador> list = indicadorAusentismoFacade.find(empresaId <= 0 ? super.getEmpresaIdRequestContext() : empresaId, rango);
            return Response.ok(list).build();
        } catch (Exception ex) {
            return Util.manageException(ex, TareaDesviacionREST.class);
        }
    }

    

}
