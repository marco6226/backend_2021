/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.sigess.restful.aus;

import co.sigess.entities.aus.CausaAusentismo;
import co.sigess.entities.aus.ReporteAusentismo;
import co.sigess.entities.scm.Recomendaciones;
import co.sigess.facade.aus.ReporteAusentismoFacade;
import co.sigess.restful.Filter;
import co.sigess.restful.FilterQuery;
import co.sigess.restful.FilterResponse;
import co.sigess.restful.ServiceREST;
import co.sigess.restful.rai.ReporteREST;
import co.sigess.restful.security.Secured;
import co.sigess.util.Util;
import java.io.IOException;
import java.text.ParseException;
import java.util.List;
import javax.ejb.EJB;
import javax.ws.rs.BeanParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
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
@Path("reporteAusentismo")
public class ReporteAusentismoREST extends ServiceREST {

    @EJB
    private ReporteAusentismoFacade reporteAusentismoFacade;
 
    @Override
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response findWithFilter(@BeanParam FilterQuery filterQuery) {
        try {
            boolean filtradoEmpresa = false;
            for (Filter filter : filterQuery.getFilterList()) {
                if (filter.getField().equals("causaAusentismo.empresa.id")) {
                    filtradoEmpresa = true;
                    break;
                }
            }
            if (!filtradoEmpresa) {
                Filter empFilt = new Filter();
                empFilt.setCriteria("eq");
                empFilt.setField("causaAusentismo.empresa.id");
                empFilt.setValue1(super.getEmpresaIdRequestContext().toString());
                filterQuery.getFilterList().add(empFilt);
            }            
            long numRows = filterQuery.isCount() ? reporteAusentismoFacade.countWithFilter(filterQuery) : -1;
            List list = reporteAusentismoFacade.findWithFilter(filterQuery);
            
            FilterResponse filterResponse = new FilterResponse();
            filterResponse.setData(list);
            filterResponse.setCount(numRows);
            return Response.ok(filterResponse).build();
        } catch (IOException | NoSuchFieldException | ParseException ex) {
            return Util.manageException(ex, ReporteAusentismoREST.class);
        }
    }

    @POST
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response create(ReporteAusentismo reporteAusentismo) {
        try {
            reporteAusentismo = reporteAusentismoFacade.create(reporteAusentismo);
            return Response.ok(reporteAusentismo).build();
        } catch (Exception e) {
            return Util.manageException(e, ReporteAusentismoREST.class);
        }
    }

    @GET
    @Path("scmausentismo/{parametro}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response listReco(@PathParam("parametro") String documento) {
        try {
            System.out.println("Prueba");
            List<ReporteAusentismo> list = this.reporteAusentismoFacade.buscar(documento);
            return Response.ok(list).build();
        } catch (Exception ex) {
            return Util.manageException(ex, ReporteREST.class);
        }
    }
     
    
    @PUT
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response edit(ReporteAusentismo reporteAusentismo) {
        try {
            reporteAusentismo = reporteAusentismoFacade.edit(reporteAusentismo);
            return Response.ok(reporteAusentismo).build();
        } catch (Exception e) {
            return Util.manageException(e, ReporteAusentismoREST.class);
        }
    }
}
