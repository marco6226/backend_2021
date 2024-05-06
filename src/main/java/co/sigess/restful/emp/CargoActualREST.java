/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.sigess.restful.emp;

import co.sigess.entities.emp.Cargo;
import co.sigess.entities.emp.CargoActual;
import co.sigess.entities.emp.Empresa;
import co.sigess.facade.emp.CargoActualFacade;
import co.sigess.facade.emp.CargoFacade;
import co.sigess.restful.FilterQuery;
import co.sigess.restful.FilterResponse;
import co.sigess.restful.ServiceREST;
import co.sigess.restful.ipr.MatrizPeligrosREST;
import co.sigess.restful.security.Secured;
import co.sigess.util.Util;
import java.util.List;
import javax.ejb.EJB;
import javax.ws.rs.BeanParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 *
 * @author Usuario
 */
@Secured
@Path("cargoActual")
public class CargoActualREST extends ServiceREST{
    
    @EJB
    private CargoActualFacade cargoActualFacade;
    
    public CargoActualREST() {
        super(CargoActualFacade.class);
    }

    @GET
    @Secured(validarPermiso = false)
    @Path("empresa")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response findByEmpresa() {
        try {
            List<CargoActual> list = cargoActualFacade.findByEmpresaId(super.getEmpresaIdRequestContext());
            return Response.ok(list).build();
        } catch (Exception ex) {
            return Util.manageException(ex, CargoActualREST.class);
        }
    }
    
    @GET
    @Secured(validarPermiso = false)
    @Path("cargoFilter")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response findFirmWithFilter(@BeanParam FilterQuery filterQuery){
        try {
            if(filterQuery == null){
                filterQuery = new FilterQuery();
            }
            long numRows = filterQuery.isCount() ? cargoActualFacade.countWithFilter(filterQuery) : -1;
            List list = cargoActualFacade.findWithFilter(filterQuery);
            
            FilterResponse filterResponse = new FilterResponse();
            filterResponse.setData(list);
            filterResponse.setCount(numRows);
            return Response.ok(filterResponse).build();
        } catch (Exception e) {
            return Util.manageException(e, CargoActualREST.class);
        }
    }

    @POST
    @Secured(validarPermiso = false)
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response create(CargoActual cargoActual) {
        try {
            Empresa empresa=new Empresa();
            empresa.setId(super.getEmpresaIdRequestContext());
            cargoActual.setEmpresa(empresa);
            cargoActualFacade.create(cargoActual);
            return Response.ok(cargoActual).build();
        } catch (Exception ex) {
            return Util.manageException(ex, CargoActualREST.class);
        }
    }
    
}
