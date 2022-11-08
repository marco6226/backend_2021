/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.sigess.restful.ind;

import co.sigess.entities.ind.ReporteATView;
import co.sigess.restful.FilterQuery;
import co.sigess.restful.ServiceREST;
import co.sigess.restful.security.Secured;
import javax.ejb.EJB;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;
import co.sigess.facade.ind.ReporteATViewFacade;
import java.util.List;
import javax.ws.rs.GET;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 *
 * @author Usuario
 */
//@Secured
@Path("reporteatview")
public class ReporteATViewREST extends ServiceREST{
    
    
    @EJB
    private ReporteATViewFacade reporteATViewFacade;
    
  //  public ReporteATViewREST(){
 //       super(ReporteATViewFacade.class);
 //   }
    
    @GET
    @Path("all")
    public Response list() {
        List<ReporteATView> list;
        list = reporteATViewFacade.findAll();
        return Response.ok(list).build();
    }
 //   @Override
 //   public Response findWithFilter(FilterQuery filterQuery) {
  //      return super.findWithFilter(filterQuery);
  //  }
}
