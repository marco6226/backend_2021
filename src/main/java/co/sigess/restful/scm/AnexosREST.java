/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.sigess.restful.scm;

import co.sigess.entities.scm.Anexos;
import co.sigess.facade.scm.AnexosFacade;
import co.sigess.restful.FilterQuery;
import co.sigess.restful.FilterResponse;
import co.sigess.restful.ServiceREST;
import co.sigess.restful.rai.ReporteREST;
import co.sigess.restful.security.Auditable;
import co.sigess.restful.security.Secured;
import co.sigess.util.Util;
import java.util.List;
import javax.ejb.EJB;
import javax.ws.rs.Path;
import javax.ws.rs.GET;
import javax.ws.rs.BeanParam;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
/**
 *
 * @author Usuario
 */
@Secured
@Path("anexscm")
public class AnexosREST extends ServiceREST{
    @EJB
    private AnexosFacade anexosFacade;
    
    public AnexosREST(){
        super(AnexosFacade.class);
    }
    
    @POST
    @Auditable
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response create(Anexos anexos) {
        try {
            anexos = anexosFacade.create(anexos);
            return Response.ok(anexos).build();
        } catch (Exception ex) {
            return Util.manageException(ex, AnexosREST.class);
        }
    }
    
    @GET
    @Path("anexosFilter")
    @Secured(requiereEmpresaId = false)
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response findRepWithFilter(@BeanParam FilterQuery filterQuery){
        try {
            if(filterQuery == null){
                filterQuery = new FilterQuery();
            }
            long numRows = filterQuery.isCount() ? anexosFacade.countWithFilter(filterQuery) : -1;
            List list = anexosFacade.findWithFilter(filterQuery);
            
            FilterResponse filterResponse = new FilterResponse();
            filterResponse.setData(list);
            filterResponse.setCount(numRows);
            return Response.ok(filterResponse).build();
        } catch (Exception e) {
            return Util.manageException(e, ReporteREST.class);
        }
    }
    
    @PUT
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response edit(Anexos anexos) {
        try {


            anexos = ((AnexosFacade) beanInstance).edit(anexos);
            return Response.ok(anexos).build();
        } catch (Exception ex) {
            return Util.manageException(ex, Anexos.class);
        }
    }
}
