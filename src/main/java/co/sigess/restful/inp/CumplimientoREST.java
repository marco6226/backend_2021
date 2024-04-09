/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.sigess.restful.inp;

import co.sigess.entities.inp.Cumplimiento;
import co.sigess.facade.inp.CumplimientoFacade;
import co.sigess.restful.FilterQuery;
import co.sigess.restful.FilterResponse;
import co.sigess.restful.ServiceREST;
import co.sigess.restful.security.Compress;
import co.sigess.restful.security.Secured;
import co.sigess.util.Util;
import java.util.List;
import javax.ejb.EJB;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 *
 * @author Julio
 */
@Secured
@Path("cumplimiento")
public class CumplimientoREST extends ServiceREST{
    
    @EJB
    private CumplimientoFacade cumplimientoFacade;
    
    public CumplimientoREST(){
        super(CumplimientoFacade.class);
    }
    
    @Compress
    @Override
    public Response findWithFilter(FilterQuery filterQuery) {
        try {
            long numRows = filterQuery.isCount() ? cumplimientoFacade.countWithFilter(filterQuery) : -1;
            List list = cumplimientoFacade.findWithFilter(filterQuery);
            FilterResponse filterResponse = new FilterResponse();
            filterResponse.setCount(numRows);
            filterResponse.setData(list);
            return Response.ok(filterResponse).build();
        } catch (Exception e) {
            return Util.manageException(e, CumplimientoREST.class);
        }
    }
    
    @POST
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response createCumplimiento(List<Cumplimiento> cumplimientoList){
        try {
            for(Cumplimiento cumplimiento : cumplimientoList){
                cumplimiento = cumplimientoFacade.create(cumplimiento);
            }
            return Response.ok(cumplimientoList).build();
        } catch (Exception e) {
            return Util.manageException(e, CumplimientoREST.class);
        }
    }
    
    @PUT
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response editCumplimiento(List<Cumplimiento> cumplimientoList){
        try {
            for (Cumplimiento cumplimiento : cumplimientoList) {
                cumplimiento = cumplimientoFacade.edit(cumplimiento);
            }
            return Response.ok(cumplimientoList).build();
        } catch (Exception e) {
            return Util.manageException(e, CumplimientoREST.class);
        }
    }
}
