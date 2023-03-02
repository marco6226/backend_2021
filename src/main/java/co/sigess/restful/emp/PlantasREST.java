/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package co.sigess.restful.emp;

import co.sigess.entities.emp.Plantas;
import co.sigess.facade.emp.PlantasFacade;
import co.sigess.restful.ServiceREST;
import co.sigess.restful.security.Secured;
import java.util.List;
import javax.ejb.EJB;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;

/**
 *
 * @author LENOVO
 */
@Secured
@Path("plantas")
public class PlantasREST extends ServiceREST{
    @EJB
    private PlantasFacade plantasFacade;

    public PlantasREST() {
        super(PlantasFacade.class);
    }
    
    @GET
    @Path("/{idEmpresa}")
    public Response findPlantasByEmpresaId(@PathParam("idEmpresa") long empresaId){
        List<Plantas> plantas = plantasFacade.findPlantasByEmpresa(empresaId);
        if(plantas.size()>0){
            return Response.ok(plantas).build();
        }
        return Response.status(Response.Status.NOT_FOUND).build();
    }
}