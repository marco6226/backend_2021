/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.sigess.restful.ipr;

import co.sigess.entities.emp.Empresa;
import co.sigess.entities.ipr.AreaMatriz;
import co.sigess.entities.ipr.ProcesoMatriz;
import co.sigess.facade.ipr.AreaMatrizFacade;
import co.sigess.facade.ipr.ProcesoMatrizFacade;
import co.sigess.facade.ipr.SubprocesoMatrizFacade;
import co.sigess.restful.ServiceREST;
import co.sigess.restful.security.Secured;
import co.sigess.util.Util;
import java.util.List;
import javax.ejb.EJB;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
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
@Path("procesoMatriz")
public class ProcesoMatrizREST extends ServiceREST{
    
    @EJB
    private ProcesoMatrizFacade procesoMatrizFacade;
    
    @EJB
    private AreaMatrizFacade areaMatrizFacade;
    
    @EJB
    private SubprocesoMatrizFacade subprocesoMatrizFacade;
    
    public ProcesoMatrizREST(){
        super(ProcesoMatrizFacade.class);
    }
    
    @GET
    @Path("empresaId")
    @Secured(validarPermiso = false)
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response findEmpresa(@PathParam("empresaId") Integer empresaId) {
        try {
            List<ProcesoMatriz> list = procesoMatrizFacade.findForEmp(getEmpresaIdRequestContext());
            return Response.ok(list).build();
        } catch (Exception ex) {
            return Util.manageException(ex, ProcesoMatrizREST.class);
        }
    }
    
    @POST
    @Secured(validarPermiso = false)
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response create(ProcesoMatriz procesoMatriz) {
        try {
            procesoMatriz.setEmpresa(new Empresa(super.getEmpresaIdRequestContext()));
            procesoMatriz = ((ProcesoMatrizFacade) beanInstance).create(procesoMatriz);
            
            areaMatrizFacade.editAreaEstado(procesoMatriz.getAreaMatriz().getId());

            return Response.ok(procesoMatriz).build();
        } catch (Exception ex) {
            return Util.manageException(ex, ProcesoMatrizREST.class);
        }
    }
    
    @PUT
    @Secured(validarPermiso = false)
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response edit(ProcesoMatriz procesoMatrizIn) throws Exception {
        try {
            ProcesoMatriz procesoMatriz = procesoMatrizFacade.find(procesoMatrizIn.getId());
            procesoMatriz.setNombre(procesoMatrizIn.getNombre());
            procesoMatriz.setEliminado(procesoMatrizIn.getEliminado());
            procesoMatriz = ((ProcesoMatrizFacade) beanInstance).edit(procesoMatriz);
            
            if(procesoMatrizIn.getEliminado()){
                subprocesoMatrizFacade.editSubProcesoEliminacion(procesoMatriz.getId());
                List<ProcesoMatriz> listProceso = procesoMatrizFacade.findForArea(procesoMatriz.getAreaMatriz().getId());
                if(listProceso.isEmpty()){
                    AreaMatriz areaMatriz = areaMatrizFacade.find(procesoMatriz.getAreaMatriz().getId());
                    areaMatriz.setEstado("Evaluado");
                    areaMatrizFacade.edit(areaMatriz);
                }
            }
            
            return Response.ok(procesoMatriz).build();
        } catch (Exception ex) {
            return Util.manageException(ex, ProcesoMatrizREST.class);
        }
    }
    
    @DELETE
    @Secured(validarPermiso = false)
    @Path("{id}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response delete(@PathParam("id") Integer idprocesoMatriz) throws Exception {
        try {
            ProcesoMatriz procesoMatriz = ((ProcesoMatrizFacade) beanInstance).find(idprocesoMatriz);
            ((ProcesoMatrizFacade) beanInstance).remove(procesoMatriz);
            
            return Response.ok(procesoMatriz).build();
        } catch (Exception ex) {
            return Util.manageException(ex, ProcesoMatrizREST.class);
        }
    }
    
}
