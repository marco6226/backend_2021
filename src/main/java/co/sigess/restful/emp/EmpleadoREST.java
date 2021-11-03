/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.sigess.restful.emp;
import co.sigess.entities.com.Mensaje;
import co.sigess.entities.emp.Empleado;
import co.sigess.facade.emp.EmpleadoFacade;
import co.sigess.facade.emp.UsuarioFacade;
import co.sigess.restful.Filter;
import co.sigess.restful.FilterQuery;
import co.sigess.restful.FilterResponse;
import co.sigess.restful.ServiceREST;
import co.sigess.restful.inp.ListaInspeccionREST;
import co.sigess.restful.security.Auditable;
import co.sigess.restful.security.Secured;
import co.sigess.util.Util;
import java.util.HashMap;
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
 * @author fmoreno
 */
@Secured
@Path("empleado")
public class EmpleadoREST extends ServiceREST {

    @EJB
    private EmpleadoFacade empleadoFacade;

    @EJB
    private UsuarioFacade usuarioFacade;

    @GET
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    @Override
    public Response findWithFilter(@BeanParam FilterQuery filterQuery) {
        try {
            boolean filtradoEmpresa = false;
            for (Filter filter : filterQuery.getFilterList()) {
                if (filter.getField().equals("cargo.empresa.id")) {
                    filtradoEmpresa = true;
                    break;
                }
            }
            if (!filtradoEmpresa) {
                Filter empFilt = new Filter();
                empFilt.setCriteria("eq");
                empFilt.setField("cargo.empresa.id");
                empFilt.setValue1(super.getEmpresaIdRequestContext().toString());
                filterQuery.getFilterList().add(empFilt);
            }
            long numRows = filterQuery.isCount() ? empleadoFacade.countWithFilter(filterQuery) : -1;
            List list = empleadoFacade.findWithFilter(filterQuery);

            FilterResponse filterResponse = new FilterResponse();
            filterResponse.setData(list);
            filterResponse.setCount(numRows);
            return Response.ok(filterResponse).build();
        } catch (Exception ex) {
            return Util.manageException(ex, EmpleadoREST.class);
        }
    }

    @GET
    @Path("buscar/{parametro}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response buscar(@PathParam("parametro") String parametro) {
        try {
            List<Empleado> list = empleadoFacade.buscar(parametro, super.getEmpresaIdRequestContext());
            return Response.ok(list).build();
        } catch (Exception ex) {
            return Util.manageException(ex, EmpleadoREST.class);
        }
    }

    @POST
    @Auditable
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response create(Empleado empleado) {
        try {
            empleadoFacade.create(empleado, super.getEmpresaIdRequestContext());
            return Response.ok(empleado).build();
        } catch (Exception ex) {
            return Util.manageException(ex, EmpleadoREST.class);
        }
    }

    @PUT
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response edit(Empleado empleado) {
        try {
            empleado = empleadoFacade.edit(empleado);
            usuarioFacade.filtrarUsuarioEmpresa(empleado.getUsuario(), super.getEmpresaIdRequestContext());
            return Response.ok(empleado).build();
        } catch (Exception ex) {
            return Util.manageException(ex, EmpleadoREST.class);
        }
    }

    @PUT
    @Path("update")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response update(Empleado empleado) {
        try {
            empleado = empleadoFacade.update(empleado, super.getUsuarioRequestContext());
            return Response.ok(empleado).build();
        } catch (Exception ex) {
            return Util.manageException(ex, EmpleadoREST.class);
        }
    }

    @PUT
    @Path("loadAll")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response loadAll(List<Empleado> list) {
        try {
            List<Mensaje> listErrors = empleadoFacade.createList(list, super.getEmpresaIdRequestContext());
            return Response.ok(listErrors).build();
        } catch (Exception ex) {
            return Util.manageException(ex, EmpleadoREST.class);
        }
    }

    @DELETE
    @Path("{empleadoId}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response delete(@PathParam("empleadoId") Integer empleadoId) {
        try {
            empleadoFacade.remove(empleadoId);
            return Response.ok().build();
        } catch (Exception ex) {
            return Util.manageException(ex, EmpleadoREST.class);
        }
    }
    
    @GET
    @Secured(validarPermiso = false)
    @Path("images/{empleado_id}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response findImages(@PathParam("empleado_id") Integer empleado_id) {
        try {
             HashMap<String, List<String>> file = empleadoFacade.getImages(empleado_id);
            return Response.ok(file).build();
        } catch (Exception ex) {
            return Util.manageException(ex, ListaInspeccionREST.class);
        }
    }

}
