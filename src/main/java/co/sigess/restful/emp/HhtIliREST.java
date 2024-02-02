/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.sigess.restful.emp;

import co.sigess.entities.emp.HhtIli;
import co.sigess.facade.com.AbstractFacade;
import co.sigess.facade.emp.HhtIliFacade;
import co.sigess.restful.FilterQuery;
import co.sigess.restful.FilterResponse;
import co.sigess.restful.ServiceREST;
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
@Path("hhtIli")
public class HhtIliREST extends ServiceREST{
    
    @EJB
    private HhtIliFacade hhtIliFacade;
    
    public HhtIliREST() {
        super(HhtIliFacade.class);
    }
    
    @Override
    @Secured(validarPermiso = false)
    public Response findWithFilter(FilterQuery filterQuery) {
        try {
            long numRows = filterQuery.isCount() ? beanInstance.countWithFilter(filterQuery) : -1;
            List list = beanInstance.findWithFilter(filterQuery);
            FilterResponse filterResponse = new FilterResponse();
            filterResponse.setData(list);
            filterResponse.setCount(numRows);
            return Response.ok(filterResponse).build();
        } catch (Exception e) {
            return Util.manageException(e, HhtIliREST.class);
        }
    }
    
    @POST
    @Secured(validarPermiso = false)
    @Produces(MediaType.APPLICATION_JSON)
    public Response create(List<HhtIli> hhtIliList){
        try {
            List<HhtIli> hhtIliResp = hhtIliFacade.create(hhtIliList, super.getEmpresaIdRequestContext());
            return Response.ok(hhtIliResp).build();
        } catch (Exception e) {
            return Util.manageException(e, HhtIliREST.class);
        }
    }
    
    @PUT
    @Secured(validarPermiso = false)
    @Produces(MediaType.APPLICATION_JSON)
    public Response update(List<HhtIli> hhtIliList){
        try {
            List<HhtIli> hhtIliResp = hhtIliFacade.update(hhtIliList, super.getEmpresaIdRequestContext());
            return Response.ok(hhtIliResp).build();
        } catch (Exception e) {
            return Util.manageException(e, HhtIliREST.class);
        }
    }
}
