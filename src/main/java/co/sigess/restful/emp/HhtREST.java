/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.sigess.restful.emp;

import co.sigess.entities.emp.Empresa;
import co.sigess.entities.emp.Hht;
import co.sigess.entities.emp.HhtIli;
import co.sigess.facade.emp.HhtFacade;
import co.sigess.facade.emp.HhtIliFacade;
import co.sigess.restful.ServiceREST;
import co.sigess.restful.security.Secured;
import co.sigess.util.Util;
import java.util.List;
import javax.ejb.EJB;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import co.sigess.restful.FilterQuery;
import co.sigess.restful.FilterResponse;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author fmoreno
 */
@Secured
@Path("hht")
public class HhtREST extends ServiceREST{

    @EJB
    private HhtFacade hhtFacade;
    
    @EJB
    private HhtIliFacade hhtIliFacade;
    
    public HhtREST() {
        super(HhtFacade.class);
    }

    @Override
    @Secured(requiereEmpresaId = false)
    public Response findWithFilter(FilterQuery filterQuery) {
        //return super.findWithFilter(filterQuery);
        try {
            long numRows = filterQuery.isCount() ? beanInstance.countWithFilter(filterQuery) : -1;
            List list = beanInstance.findWithFilter(filterQuery);
            FilterResponse filterResponse = new FilterResponse();
            filterResponse.setData(list);
            filterResponse.setCount(numRows);
            return Response.ok(filterResponse).build();
        } catch (Exception e){
            return Util.manageException(e, HhtREST.class);
        }
    }

    @POST
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response create(List<Hht> hhtList) {
        try {
            System.out.println("post");
            hhtList = this.hhtFacade.create(hhtList, new Empresa(super.getEmpresaIdRequestContext()));
            return Response.ok(hhtList).build();
        } catch (Exception ex) {
            return Util.manageException(ex, HhtREST.class);
        }
    }

    @PUT
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response edit(List<Hht> hhtList) {
        try {
            System.out.println("put");
            hhtList = this.hhtFacade.edit(hhtList, new Empresa(super.getEmpresaIdRequestContext()));
            return Response.ok(hhtList).build();
        } catch (Exception ex) {
            return Util.manageException(ex, HhtREST.class);
        }
    }

    @GET
    @Path("anio/{anio}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response findByAnio(@PathParam("anio") Short anio) {
        try {
             List<Hht> list = this.hhtFacade.findByAnioEmpresa(anio, super.getEmpresaIdRequestContext());
            return Response.ok(list).build();
        } catch (Exception ex) {
            return Util.manageException(ex, HhtREST.class);
        }
    }


}
