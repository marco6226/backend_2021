/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.sigess.restful.com;

import co.sigess.entities.ado.Directorio;
import co.sigess.entities.com.Departamento;
import co.sigess.facade.com.DepartamentoFacade;
import co.sigess.restful.Filter;
import co.sigess.restful.FilterQuery;
import co.sigess.restful.ado.DirectorioREST;
import co.sigess.util.Util;
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
@Path("departamento")
public class DepartamentoREST {

    @EJB
    private DepartamentoFacade departamentoFacade;

//    @GET
//    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
//    public Response findAll() {
//        List<Departamento> deptoList = departamentoFacade.findAll();
//        return Response.ok(deptoList).build();
//    }

    @GET
    @Path("pais/{paisId}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response findByPais(@PathParam("paisId") Short paisId) {
        List<Departamento> deptoList = departamentoFacade.findByPais(paisId);
        return Response.ok(deptoList).build();
    }
    
//    @GET
//    @Produces(MediaType.APPLICATION_JSON)
//    public Response findWithFilter(@BeanParam FilterQuery filterQuery) {
//        try {            
//            List<Departamento> deptoList = departamentoFacade.findWithFilter(filterQuery);
//            return Response.ok(deptoList).build();
//        } catch (Exception ex) {
//            return Util.manageException(ex, DepartamentoREST.class);
//        }
//    }
}
