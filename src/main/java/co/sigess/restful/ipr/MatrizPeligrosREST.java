/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.sigess.restful.ipr;

import co.sigess.entities.emp.Empresa;
import co.sigess.entities.ipr.AreaMatriz;
import co.sigess.entities.ipr.MatrizPeligros;
import co.sigess.entities.ipr.ProcesoMatriz;
import co.sigess.entities.ipr.SubprocesoMatriz;
import co.sigess.facade.ipr.AreaMatrizFacade;
import co.sigess.facade.ipr.MatrizPeligrosFacade;
import co.sigess.facade.ipr.ProcesoMatrizFacade;
import co.sigess.facade.ipr.SubprocesoMatrizFacade;
import co.sigess.restful.FilterQuery;
import co.sigess.restful.FilterResponse;
import co.sigess.restful.ServiceREST;
import co.sigess.restful.security.Secured;
import co.sigess.util.Util;
import java.util.List;
import javax.ejb.EJB;
import javax.ws.rs.BeanParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;


/**
 *
 * @author Usuario
 */
@Secured
@Path("matrizP")
public class MatrizPeligrosREST extends ServiceREST{
    
    @EJB
    private  MatrizPeligrosFacade matrizPeligrosFacade;
    
    
    @EJB
    private  AreaMatrizFacade areaMatrizFacade;
    
    @EJB
    private  ProcesoMatrizFacade procesoMatrizFacade;
    
    @EJB
    private  SubprocesoMatrizFacade subprocesoMatrizFacade;
    
    public MatrizPeligrosREST() {
        super(MatrizPeligrosFacade.class);
    }
    

    
    @GET
    @Path("empresaId")
    @Secured(validarPermiso = false)
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response findEmpresa() {
        try {
            List<MatrizPeligros> list = matrizPeligrosFacade.findForEmp(getEmpresaIdRequestContext());
            return Response.ok(list).build();
        } catch (Exception ex) {
            return Util.manageException(ex, MatrizPeligrosREST.class);
        }
    }
    
    @POST
    @Secured(validarPermiso = false)
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response create(MatrizPeligros matrizPeligros) {
        try {
            matrizPeligros.setEmpresa(new Empresa(super.getEmpresaIdRequestContext()));
            matrizPeligros = ((MatrizPeligrosFacade) beanInstance).create(matrizPeligros);
            subprocesoMatrizFacade.editSubProcesoEstado(matrizPeligros.getSubProceso().getId());

            List<SubprocesoMatriz> listSubProceso = subprocesoMatrizFacade.findForProceso(matrizPeligros.getProceso().getId());
            if(listSubProceso.isEmpty()){
                ProcesoMatriz procesoMatriz = procesoMatrizFacade.find(matrizPeligros.getProceso().getId());
                procesoMatriz.setEstado("Evaluado");
                procesoMatrizFacade.edit(procesoMatriz);
                List<ProcesoMatriz> listProceso = procesoMatrizFacade.findForArea(matrizPeligros.getArea().getId());
                
                if(listProceso.isEmpty()){
                    AreaMatriz areaMatriz = areaMatrizFacade.find(matrizPeligros.getArea().getId());
                    areaMatriz.setEstado("Evaluado");
                    areaMatrizFacade.edit(areaMatriz);
                }
            }
            return Response.ok(matrizPeligros).build();
        } catch (Exception ex) {
            return Util.manageException(ex, MatrizPeligrosREST.class);
        }
    }
    
    @PUT
    @Secured(validarPermiso = false)
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response update(MatrizPeligros matrizPeligros) {
        try {
            matrizPeligros.setEmpresa(new Empresa(super.getEmpresaIdRequestContext()));
            matrizPeligros = ((MatrizPeligrosFacade) beanInstance).edit(matrizPeligros);
            return Response.ok(matrizPeligros).build();
        } catch (Exception ex) {
            return Util.manageException(ex, MatrizPeligrosREST.class);
        }
    }
    
    @GET
    @Path("mpRegistroFilter")
    @Secured(requiereEmpresaId = false)
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response findFirmWithFilter(@BeanParam FilterQuery filterQuery){
        try {
            if(filterQuery == null){
                filterQuery = new FilterQuery();
            }
            long numRows = filterQuery.isCount() ? matrizPeligrosFacade.countWithFilter(filterQuery) : -1;
            List list = matrizPeligrosFacade.findWithFilter(filterQuery);
            
            FilterResponse filterResponse = new FilterResponse();
            filterResponse.setData(list);
            filterResponse.setCount(numRows);
            return Response.ok(filterResponse).build();
        } catch (Exception e) {
            return Util.manageException(e, MatrizPeligrosREST.class);
        }
    }

}
