/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.sigess.restful.inp;

import co.sigess.facade.inp.ViewResumenAliadosFACADE;
import co.sigess.restful.FilterQuery;
import co.sigess.restful.FilterResponse;
import co.sigess.restful.ServiceREST;
import co.sigess.restful.security.Secured;
import co.sigess.util.Util;
import java.util.List;
import javax.ejb.EJB;
import javax.ws.rs.BeanParam;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 *
 * @author JULIO
 */
@Secured
@Path("viewResumenAliados")
public class ViewResumenAliadosREST extends ServiceREST {
    
    @EJB
    private ViewResumenAliadosFACADE viewResumenAliadosFACADE;

    @GET
    @Secured
    @Produces({MediaType.APPLICATION_JSON})
    @Override
    public Response findWithFilter(@BeanParam FilterQuery filterQuery) {
        try {
            long numRows = filterQuery.isCount() ? viewResumenAliadosFACADE.countWithFilter(filterQuery) : -1;
            List list = viewResumenAliadosFACADE.findWithFilter(filterQuery);
            FilterResponse filterResponse = new FilterResponse();
            filterResponse.setCount(numRows);
            filterResponse.setData(list);
            return Response.ok(filterResponse).build();
        } catch (Exception e) {
            return Util.manageException(e, ViewResumenAliadosREST.class);
        }
    }
    
    
}
