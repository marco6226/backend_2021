/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.sigess.restful.ipr;

import co.sigess.entities.emp.Empresa;
import co.sigess.entities.ipr.AreaMatriz;
import co.sigess.entities.ipr.ProcesoMatriz;
import co.sigess.entities.ipr.SubprocesoMatriz;
import co.sigess.facade.ipr.AreaMatrizFacade;
import co.sigess.facade.ipr.ProcesoMatrizFacade;
import co.sigess.facade.ipr.SubprocesoMatrizFacade;
import co.sigess.restful.FilterQuery;
import co.sigess.restful.FilterResponse;
import co.sigess.restful.ServiceREST;
import co.sigess.restful.emp.sfirmaREST;
import co.sigess.restful.security.Secured;
import co.sigess.util.Util;
import java.util.List;
import javax.ejb.EJB;
import javax.ws.rs.BeanParam;
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
@Path("subProcesoMatriz")
public class SubprocesoMatrizREST extends ServiceREST {
    
    @EJB
    private SubprocesoMatrizFacade subprocesoMatrizFacade;
    
    @EJB
    private ProcesoMatrizFacade procesoMatrizFacade;
    
    @EJB
    private AreaMatrizFacade areaMatrizFacade;
    
    public SubprocesoMatrizREST(){
        super(SubprocesoMatrizFacade.class);
    }
    
    @GET
    @Path("subprocesoId")
    @Secured(validarPermiso = false)
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response findEmpresa(@PathParam("subprocesoId") Integer subprocesoId) {
        try {
            List<SubprocesoMatriz> list = subprocesoMatrizFacade.findForEmp(subprocesoId);
            return Response.ok(list).build();
        } catch (Exception ex) {
            return Util.manageException(ex, SubprocesoMatrizREST.class);
        }
    }
    
    @GET
    @Path("subfilterFilter")
    @Secured(requiereEmpresaId = false)
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response findFirmWithFilter(@BeanParam FilterQuery filterQuery){
        try {
            if(filterQuery == null){
                filterQuery = new FilterQuery();
            }
            long numRows = filterQuery.isCount() ? subprocesoMatrizFacade.countWithFilter(filterQuery) : -1;
            List list = subprocesoMatrizFacade.findWithFilter(filterQuery);
            
            FilterResponse filterResponse = new FilterResponse();
            filterResponse.setData(list);
            filterResponse.setCount(numRows);
            return Response.ok(filterResponse).build();
        } catch (Exception e) {
            return Util.manageException(e, SubprocesoMatrizREST.class);
        }
    }
    
    @POST
    @Secured(validarPermiso = false)
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response create(SubprocesoMatriz subprocesoMatriz) {
        try {
            List<SubprocesoMatriz> list= (List<SubprocesoMatriz>) subprocesoMatrizFacade.findToProceso(subprocesoMatriz.getProcesoMatriz().getId() ,subprocesoMatriz.getNombre());
            if(!list.isEmpty()){
                return Response.ok(null).build();
            }
            
            subprocesoMatriz = ((SubprocesoMatrizFacade) beanInstance).create(subprocesoMatriz);
            
            procesoMatrizFacade.editProcesoEstado(subprocesoMatriz.getProcesoMatriz().getId());
            
            ProcesoMatriz procesoMatriz = procesoMatrizFacade.find(subprocesoMatriz.getProcesoMatriz().getId());
            areaMatrizFacade.editAreaEstado(procesoMatriz.getAreaMatriz().getId());

                        
            return Response.ok(subprocesoMatriz).build();
        } catch (Exception ex) {
            return Util.manageException(ex, SubprocesoMatrizREST.class);
        }
    }
    
    @PUT
    @Secured(validarPermiso = false)
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response edit(SubprocesoMatriz subprocesoMatrizIn) throws Exception {
        try {
            SubprocesoMatriz subprocesoMatriz = subprocesoMatrizFacade.find(subprocesoMatrizIn.getId());
            subprocesoMatriz.setNombre(subprocesoMatrizIn.getNombre());
            subprocesoMatriz.setEliminado(subprocesoMatrizIn.getEliminado());
            subprocesoMatriz = ((SubprocesoMatrizFacade) beanInstance).edit(subprocesoMatriz);
            
            if(subprocesoMatrizIn.getEliminado()){
            List<SubprocesoMatriz> listSubProceso = subprocesoMatrizFacade.findForProceso(subprocesoMatriz.getProcesoMatriz().getId());
                if(listSubProceso.isEmpty()){
                    ProcesoMatriz procesoMatriz = procesoMatrizFacade.find(subprocesoMatriz.getProcesoMatriz().getId());
                    procesoMatriz.setEstado("Evaluado");
                    procesoMatrizFacade.edit(procesoMatriz);
                    
                    List<ProcesoMatriz> listProceso = procesoMatrizFacade.findForArea(subprocesoMatriz.getProcesoMatriz().getAreaMatriz().getId());
                    if(listProceso.isEmpty()){
                        AreaMatriz areaMatriz = areaMatrizFacade.find(subprocesoMatriz.getProcesoMatriz().getAreaMatriz().getId());
                        areaMatriz.setEstado("Evaluado");
                        areaMatrizFacade.edit(areaMatriz);
                    }
                }
            }
            return Response.ok(subprocesoMatriz).build();
        } catch (Exception ex) {
            return Util.manageException(ex, SubprocesoMatrizREST.class);
        }
    }
    
    @DELETE
    @Secured(validarPermiso = false)
    @Path("{id}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response delete(@PathParam("id") Integer idsubprocesoMatriz) throws Exception {
        try {
            SubprocesoMatriz subprocesoMatriz = ((SubprocesoMatrizFacade) beanInstance).find(idsubprocesoMatriz);
            ((SubprocesoMatrizFacade) beanInstance).remove(subprocesoMatriz);
            
            return Response.ok(subprocesoMatriz).build();
        } catch (Exception ex) {
            return Util.manageException(ex, SubprocesoMatrizREST.class);
        }
    }
}
