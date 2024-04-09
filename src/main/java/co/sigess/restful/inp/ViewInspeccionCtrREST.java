/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.sigess.restful.inp;

import co.sigess.entities.inp.ViewInspeccionesCtr;
import co.sigess.facade.inp.ViewInspeccionesCtrFACADE;
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
@Path("viewInspeccionesCtr")
public class ViewInspeccionCtrREST extends ServiceREST{
    
    @EJB
    private ViewInspeccionesCtrFACADE viewInspeccionesCtrFACADE;

    @GET
    @Produces({MediaType.APPLICATION_JSON})
    @Secured(requiereEmpresaId = false)
    @Override
    public Response findWithFilter(@BeanParam FilterQuery filterQuery) {
        //return super.findWithFilter(filterQuery);
        try {
            long numRows = 0;
            if(filterQuery.isCount()){
                numRows = viewInspeccionesCtrFACADE.countWithFilter(filterQuery);
            } else {
                numRows = -1;
            }
            List <ViewInspeccionesCtr>list = viewInspeccionesCtrFACADE.findWithFilter(filterQuery);
            FilterResponse filterResponse = new FilterResponse();
            filterResponse.setCount(numRows);
            
            //Ajuste para mejorar velocidad
            for(ViewInspeccionesCtr inp : list){
                if (inp.getEmpresa() != null) {
                    inp.getEmpresa().setLogo(null);
                }
                
                if (inp.getListaInspeccion() != null) {
                    inp.getListaInspeccion().setElementoInspeccionList(null);
                }
            }
            
            filterResponse.setData(list);
            return Response.ok(filterResponse).build();
        } catch (Exception e) {
            return Util.manageException(e, ServiceREST.class);
        }
    }
}
