/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.sigess.restful.ind;

import co.sigess.entities.ind.IndicadorSge;
import co.sigess.entities.sge.SistemaGestionPK;
import co.sigess.facade.ind.IndicadorSgeFacade;
import co.sigess.restful.Filter;
import co.sigess.restful.FilterQuery;
import co.sigess.restful.ServiceREST;
import co.sigess.restful.sec.TareaDesviacionREST;
import co.sigess.restful.security.Secured;
import co.sigess.util.Util;
import java.util.List;
import java.util.Map;
import javax.ejb.EJB;
import javax.ws.rs.BeanParam;
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
@Path("indicadorSge")
public class IndicadorSgeREST extends ServiceREST {

    @EJB
    private IndicadorSgeFacade indicadorSgeFacade;
    
//    @GET
//    @Path("{sgeId}/{version}")
//    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
//    public Response findAll(@PathParam("sgeId") Integer sgeId, @PathParam("version") Short version) {
//        try {
//            SistemaGestionPK pk = new SistemaGestionPK(sgeId, version);
//            List<IndicadorSge> list = indicadorSgeFacade.findBySge(super.getEmpresaIdRequestContext(), pk);
//            return Response.ok(list).build();
//        } catch (Exception ex) {
//            return Util.manageException(ex, TareaDesviacionREST.class);
//        }
//    }
    @Override
    @GET
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response findWithFilter(@BeanParam FilterQuery filterQuery) {
        try {
            boolean filtradoEmpresa = false;
            for (Filter filter : filterQuery.getFilterList()) {
                if (filter.getField().equals("empresaId")) {
                    filtradoEmpresa = true;
                    break;
                }
            }
            if (!filtradoEmpresa) {
                Filter empFilt = new Filter();
                empFilt.setCriteria("eq");
                empFilt.setField("empresaId");
                empFilt.setValue1(super.getEmpresaIdRequestContext().toString());
                filterQuery.getFilterList().add(empFilt);
            }
            List<IndicadorSge> list = indicadorSgeFacade.findWithFilter(filterQuery);
            return Response.ok(list).build();
        } catch (Exception ex) {
            return Util.manageException(ex, TareaDesviacionREST.class);
        }
    }

}
