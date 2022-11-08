/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.sigess.restful.emp;

import co.sigess.entities.emp.Empresa;
import co.sigess.entities.emp.Hht;
import co.sigess.facade.emp.HhtFacade;
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

/**
 *
 * @author fmoreno
 */
@Secured
@Path("hht")
public class HhtREST extends ServiceREST{

    @EJB
    private HhtFacade hhtFacade;
    
    public HhtREST() {
        super(HhtFacade.class);
    }

    @Override
    public Response findWithFilter(FilterQuery filterQuery) {
        return super.findWithFilter(filterQuery);
    }

    @POST
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response create(Hht hht) {
        try {
            System.out.println("post");
            hht.setEmpresa(new Empresa(super.getEmpresaIdRequestContext()));
            hht = this.hhtFacade.create(hht);
            return Response.ok(hht).build();
        } catch (Exception ex) {
            return Util.manageException(ex, HhtREST.class);
        }
    }

    @PUT
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response edit(Hht hht) {
        try {
            System.out.println("put");
            hht.setEmpresa(new Empresa(super.getEmpresaIdRequestContext()));
            hht = this.hhtFacade.edit(hht);
            return Response.ok(hht).build();
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
