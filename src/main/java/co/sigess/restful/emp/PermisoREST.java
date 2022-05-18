/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.sigess.restful.emp;

import co.sigess.entities.com.TipoMensaje;
import co.sigess.entities.emp.Empresa;
import co.sigess.entities.emp.Permiso;
import co.sigess.exceptions.UserMessageException;
import co.sigess.facade.emp.PermisoFacade;
import co.sigess.restful.FilterQuery;
import co.sigess.restful.ServiceREST;
import co.sigess.restful.security.Auditable;
import co.sigess.restful.security.Secured;
import co.sigess.util.Util;
import java.util.List;
import javax.ejb.EJB;
import javax.ws.rs.GET;
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
@Path("permiso")
public class PermisoREST extends ServiceREST {

    @EJB
    private PermisoFacade permisoFacade;

    @Override
    public Response findWithFilter(FilterQuery filterQuery) {
        try {
            List<Permiso> list = permisoFacade.findAllByEmpresa(super.getEmpresaIdRequestContext(), super.getUsuarioRequestContext().getId());
//            Set<Permiso> set = new HashSet<>();
//            list.stream().forEach(permiso -> set.add(permiso));
            return Response.ok(list).build();
        } catch (Exception ex) {
            return Util.manageException(ex, PermisoREST.class);
        }
    }
    
    

    @GET
    @Path("perfil/{perfilId}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response findAllByPerfil(@PathParam("perfilId") Integer perfilId) {
        try {
            List<Permiso> list = permisoFacade.findAllByPerfil(super.getEmpresaIdRequestContext(), perfilId);
            return Response.ok(list).build();
        } catch (Exception ex) {
            return Util.manageException(ex, PermisoREST.class);
        }
    }

    @PUT
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    @Auditable
    public Response actualizarPermiso(Permiso permiso) {
        try {
            permiso.setEmpresa(new Empresa(super.getEmpresaIdRequestContext()));
            permisoFacade.edit(permiso);
            return Response.ok(permiso).build();
        } catch (Exception ex) {
           // throw new UserMessageException("No es posible verificar la tarea", "La tarea que intenta verificar no existe", TipoMensaje.warn);
            return Util.manageException(ex, PermisoREST.class);
        }
    }

}
