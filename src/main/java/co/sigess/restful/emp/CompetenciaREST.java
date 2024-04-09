/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.sigess.restful.emp;

import co.sigess.entities.emp.Cargo;
import co.sigess.entities.emp.Competencia;
import co.sigess.entities.emp.Empresa;
import co.sigess.facade.emp.CargoFacade;
import co.sigess.facade.emp.CompetenciaFacade;
import co.sigess.restful.ServiceREST;
import co.sigess.restful.security.Secured;
import co.sigess.util.Util;
import javax.ejb.EJB;
import javax.ws.rs.DELETE;
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
@Path("competencia")
public class CompetenciaREST extends ServiceREST{

    @EJB
    private CompetenciaFacade competenciaFacade;
    
    public CompetenciaREST() {
        super(CompetenciaFacade.class);
    }

    @POST
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response create(Competencia comp) {
        try {
            comp.setEmpresa(new Empresa(super.getEmpresaIdRequestContext()));
            competenciaFacade.create(comp);
            return Response.ok(comp).build();
        } catch (Exception ex) {
            return Util.manageException(ex, CompetenciaREST.class);
        }
    }

    @PUT
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response edit(Competencia comp) {
        try {
            competenciaFacade.edit(comp);
            return Response.ok(comp).build();
        } catch (Exception ex) {
            return Util.manageException(ex, CompetenciaREST.class);
        }
    }

    @DELETE
    @Path("{competenciaId}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response delete(@PathParam("competenciaId") Long competenciaId) {
        try {
            Competencia comp = competenciaFacade.eliminar(competenciaId);
            return Response.ok(comp).build();
        } catch (Exception ex) {
            return Util.manageException(ex, CompetenciaREST.class);
        }
    }

}
