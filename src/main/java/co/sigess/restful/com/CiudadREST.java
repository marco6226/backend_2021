/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.sigess.restful.com;

import co.sigess.entities.com.Ciudad;
import co.sigess.facade.com.CiudadFacade;
import co.sigess.restful.CriteriaFilter;
import co.sigess.restful.Filter;
import co.sigess.restful.FilterQuery;
import co.sigess.restful.FilterResponse;
import co.sigess.restful.ServiceREST;
import co.sigess.restful.rai.ReporteREST;
import co.sigess.util.Util;
import java.io.IOException;
import java.text.ParseException;
import java.util.List;
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
@Path("ciudad")
public class CiudadREST extends ServiceREST {

    @EJB
    private CiudadFacade ciudadFacade;
    
    @GET
    @Path("{ciudadId}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response find(@PathParam("ciudadId") String ciudadId) {
        Ciudad ciudad = ciudadFacade.find(ciudadId);
        return Response.ok(ciudad).build();
    }

    @GET
    @Path("departamento/{departamentoId}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response findByDepartamento(@PathParam("departamentoId") Integer departamentoId) {
        List<Ciudad> list = ciudadFacade.findByDepartamento(departamentoId);
        return Response.ok(list).build();
    }

    @GET
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    @Override
    public Response findWithFilter(@BeanParam FilterQuery filterQuery) {
        try {            
            long numRows = filterQuery.isCount() ? ciudadFacade.countWithFilter(filterQuery) : -1;
            List list = ciudadFacade.findWithFilter(filterQuery);
            
            FilterResponse filterResponse = new FilterResponse();
            filterResponse.setData(list);
            filterResponse.setCount(numRows);
            return Response.ok(filterResponse).build();
        } catch (IOException | NoSuchFieldException | ParseException ex) {
            return Util.manageException(ex, CiudadREST.class);
        }
    }
}
