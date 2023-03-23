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
import java.util.LinkedList;
import java.util.List;
import javax.ws.rs.GET;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

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
            JsonParser parser = new JsonParser();
            
            for(ReporteATView reporte : list){
                if(reporte.getComplementaria() != null){
                    JsonObject complementaria = parser.parse(reporte.getComplementaria()).getAsJsonObject();
                    JsonElement eventoArl = complementaria.get("EnventoARL");
                    if(eventoArl.isJsonNull()){
                        listAux.add(reporte);
                    }else{
                        String eventoArlStr = eventoArl.getAsString();
                        if("objetado".equalsIgnoreCase(eventoArlStr)) continue;
                        listAux.add(reporte);
                    }
                }else{
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
