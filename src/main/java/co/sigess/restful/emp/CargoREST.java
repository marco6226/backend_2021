/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.sigess.restful.emp;

import co.sigess.entities.emp.Cargo;
import co.sigess.facade.emp.AreaFacade;
import co.sigess.facade.emp.CargoFacade;
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
 * @author fmoreno
 */
@Secured
@Path("cargo")
public class CargoREST extends ServiceREST{

    @EJB
    private CargoFacade cargoFacade;
    
    public CargoREST() {
        super(CargoFacade.class);
    }

    @GET
    @Path("empresa")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response findByEmpresa() {
        try {
            List<Cargo> list = cargoFacade.findByEmpresa(super.getEmpresaIdRequestContext());
            return Response.ok(list).build();
        } catch (Exception ex) {
            return Util.manageException(ex, CargoREST.class);
        }
    }

    @POST
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response create(Cargo cargo) {
        try {
            cargoFacade.create(cargo);
            return Response.ok(cargo).build();
        } catch (Exception ex) {
            return Util.manageException(ex, CargoREST.class);
        }
    }

    @PUT
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response edit(Cargo cargo) {
        try {
            cargoFacade.edit(cargo);
            return Response.ok(cargo).build();
        } catch (Exception ex) {
            return Util.manageException(ex, CargoREST.class);
        }
    }

    @DELETE
    @Path("{cargoId}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response delete(@PathParam("cargoId") Integer cargoId) {
        try {
            Cargo cargo = cargoFacade.eliminar(cargoId);
            return Response.ok(cargo).build();
        } catch (Exception ex) {
            return Util.manageException(ex, CargoREST.class);
        }
    }

}
