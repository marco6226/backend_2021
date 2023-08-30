/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.sigess.restful.ipr;

import co.sigess.entities.emp.Empresa;
import co.sigess.entities.ipr.AreaMatriz;
import co.sigess.facade.ipr.AreaMatrizFacade;
import co.sigess.restful.ServiceREST;
import co.sigess.restful.security.Secured;
import co.sigess.util.Util;
import java.util.List;
import javax.ejb.EJB;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.DELETE;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 *
 * @author Usuario
 */
@Secured
@Path("areaMatriz")
public class AreaMatrizREST extends ServiceREST{
    
    @EJB
    private  AreaMatrizFacade areaMatrizFacade;
    
    public AreaMatrizREST() {
        super(AreaMatrizFacade.class);
    }
    
    
    @GET
    @Path("empresaId")
    @Secured(validarPermiso = false)
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response findEmpresa(@PathParam("empresaId") Integer empresaId) {
        try {
            List<AreaMatriz> list = areaMatrizFacade.findForEmp(getEmpresaIdRequestContext());
            return Response.ok(list).build();
        } catch (Exception ex) {
            return Util.manageException(ex, AreaMatrizREST.class);
        }
    }
    
    @POST
    @Secured(validarPermiso = false)
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response create(AreaMatriz areaMatriz) {
        try {
            areaMatriz.setEmpresa(new Empresa(super.getEmpresaIdRequestContext()));
            areaMatriz = ((AreaMatrizFacade) beanInstance).create(areaMatriz);
            return Response.ok(areaMatriz).build();
        } catch (Exception ex) {
            return Util.manageException(ex, AreaMatrizREST.class);
        }
    }
    
    @PUT
    @Secured(validarPermiso = false)
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response edit(AreaMatriz areaMatriz) throws Exception {
        try {
            areaMatriz.setEmpresa(new Empresa(super.getEmpresaIdRequestContext()));
            areaMatriz = ((AreaMatrizFacade) beanInstance).edit(areaMatriz);
            return Response.ok(areaMatriz).build();
        } catch (Exception ex) {
            return Util.manageException(ex, AreaMatrizREST.class);
        }
    }
    
    @DELETE
    @Secured(validarPermiso = false)
    @Path("{id}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response delete(@PathParam("id") Integer idareaMatriz) throws Exception {
        try {
            AreaMatriz areaMatriz = ((AreaMatrizFacade) beanInstance).find(idareaMatriz);
            ((AreaMatrizFacade) beanInstance).remove(areaMatriz);

            return Response.ok(areaMatriz).build();
        } catch (Exception ex) {
            return Util.manageException(ex, AreaMatrizREST.class);
        }
    }
    
    
}
