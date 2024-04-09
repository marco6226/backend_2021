/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.sigess.restful.emp;

import co.sigess.entities.emp.Empresa;
import co.sigess.entities.emp.Perfil;
import co.sigess.facade.emp.PerfilFacade;
import co.sigess.restful.ServiceREST;
import co.sigess.restful.security.Auditable;
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
@Path("perfil")
public class PerfilREST extends ServiceREST{

    @EJB
    private PerfilFacade perfilFacade;

    public PerfilREST() {
        super(PerfilFacade.class);
    }

//    @GET
//    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
//    public Response findAll() {
//        try {
//            List<Perfil> list = perfilFacade.findByEmpresa(super.getEmpresaIdRequestContext());
//            return Response.ok(list).build();
//        } catch (Exception ex) {
//            return Util.manageException(ex, PerfilREST.class);
//        }
//    }

    @POST
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    @Auditable
    public Response create(Perfil perfil) {
        try {
            perfil.setEmpresa(new Empresa(super.getEmpresaIdRequestContext()));
            perfilFacade.create(perfil);
            return Response.ok(perfil).build();
        } catch (Exception ex) {
            return Util.manageException(ex, PerfilREST.class);
        }
    }

    @PUT
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    @Auditable
    public Response edit(Perfil perfil) {
        try {
            perfil.setEmpresa(new Empresa(super.getEmpresaIdRequestContext()));
            perfilFacade.edit(perfil);
            return Response.ok(perfil).build();
        } catch (Exception ex) {
            return Util.manageException(ex, PerfilREST.class);
        }
    }

    @DELETE
    @Path("{perfilId}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    @Auditable
    public Response delete(@PathParam("perfilId") Integer perfilId) {
        try {
            perfilFacade.remove(new Perfil(perfilId));
            return Response.ok().build();
        } catch (Exception ex) {
            return Util.manageException(ex, PerfilREST.class);
        }
    }

}
