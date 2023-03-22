/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.sigess.restful.ind;

import co.sigess.entities.InfComplementariaAt;
import co.sigess.entities.ind.ReporteATView;
import co.sigess.restful.FilterQuery;
import co.sigess.restful.ServiceREST;
import co.sigess.restful.security.Secured;
import javax.ejb.EJB;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;
import co.sigess.facade.ind.ReporteATViewFacade;
import java.util.LinkedList;
import java.util.List;
import javax.ws.rs.GET;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

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
    
    @GET
    @Path("/listaAt")
    public Response listaAt(){
        try{
            List<ReporteATView> list;
            list = reporteATViewFacade.findAll();
            List<ReporteATView> listAux = new LinkedList<>();
            for(ReporteATView reporte : list){
                if(reporte.getComplementaria() != null && !reporte.getComplementaria().isEmpty()){
                    InfComplementariaAt data = new Gson().fromJson(reporte.getComplementaria(), InfComplementariaAt.class);
                    if("objetado".equalsIgnoreCase(data.getEventoARL())){
                        continue;
                    }
                    listAux.add(reporte);
                }
            }
            return Response.ok(listAux).build();
        }catch(Exception ex){
            System.out.println(ex);
            return Response.serverError().build();
        }
    }
}
