/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.sigess.restful.ipr;

import co.sigess.entities.emp.Empresa;
import co.sigess.entities.ipr.MatrizPeligrosLog;
import co.sigess.facade.ipr.MatrizPeligrosLogFacade;
import co.sigess.restful.FilterQuery;
import co.sigess.restful.FilterResponse;
import co.sigess.restful.ServiceREST;
import co.sigess.restful.emp.sfirmaREST;
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
@Path("matrizPlog")
public class MatrizPeligrosLogREST extends ServiceREST{
    
    @EJB
    private  MatrizPeligrosLogFacade matrizPeligrosLogFacade;
    
    public MatrizPeligrosLogREST() {
        super(MatrizPeligrosLogFacade.class);
    }
    
    @GET
    @Path("empresaId")
    @Secured(validarPermiso = false)
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response findEmpresa() {
        try {
            List<MatrizPeligrosLog> list = matrizPeligrosLogFacade.findForEmp(getEmpresaIdRequestContext());
            return Response.ok(list).build();
        } catch (Exception ex) {
            return Util.manageException(ex, MatrizPeligrosLogREST.class);
        }
    }
    
    @POST
    @Secured(validarPermiso = false)
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response create(MatrizPeligrosLog matrizPeligrosLog) {
        try {
            matrizPeligrosLog.setEmpresa(new Empresa(super.getEmpresaIdRequestContext()));
            matrizPeligrosLog = ((MatrizPeligrosLogFacade) beanInstance).create(matrizPeligrosLog);
            return Response.ok(matrizPeligrosLog).build();
        } catch (Exception ex) {
            return Util.manageException(ex, MatrizPeligrosLog.class);
        }
    }
    
    @PUT
    @Secured(validarPermiso = false)
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response update(MatrizPeligrosLog matrizPeligrosLog) {
        try {
            matrizPeligrosLog.setEmpresa(new Empresa(super.getEmpresaIdRequestContext()));
            matrizPeligrosLog = ((MatrizPeligrosLogFacade) beanInstance).edit(matrizPeligrosLog);
            return Response.ok(matrizPeligrosLog).build();
        } catch (Exception ex) {
            return Util.manageException(ex, MatrizPeligrosLogREST.class);
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
            long numRows = filterQuery.isCount() ? matrizPeligrosLogFacade.countWithFilter(filterQuery) : -1;
            List list = matrizPeligrosLogFacade.findWithFilter(filterQuery);
            
            FilterResponse filterResponse = new FilterResponse();
            filterResponse.setData(list);
            filterResponse.setCount(numRows);
            return Response.ok(filterResponse).build();
        } catch (Exception e) {
            return Util.manageException(e, sfirmaREST.class);
        }
    }
}
