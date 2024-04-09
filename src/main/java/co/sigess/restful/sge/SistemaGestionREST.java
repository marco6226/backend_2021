/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.sigess.restful.sge;

import co.sigess.entities.com.TipoMensaje;
import co.sigess.entities.emp.Empresa;
import co.sigess.entities.sge.SistemaGestion;
import co.sigess.entities.sge.SistemaGestionPK;
import co.sigess.exceptions.UserMessageException;
import co.sigess.facade.sge.SistemaGestionFacade;
import co.sigess.restful.Filter;
import co.sigess.restful.FilterQuery;
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
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.PathSegment;
import javax.ws.rs.core.Response;

/**
 *
 * @author fmoreno
 */
@Secured
@Path("sistemaGestion")
public class SistemaGestionREST extends ServiceREST {

    @EJB
    private SistemaGestionFacade sistemaGestionFacade;

    private SistemaGestionPK getPrimaryKey(PathSegment pathSegment) {
        /*
         * pathSemgent represents a URI path segment and any associated matrix parameters.
         * URI path part is supposed to be in form of 'somePath;id=idValue;version=versionValue'.
         * Here 'somePath' is a result of getPath() method invocation and
         * it is ignored in the following code.
         * Matrix parameters are used as field names to build a primary key instance.
         */
        SistemaGestionPK key = new SistemaGestionPK();
        javax.ws.rs.core.MultivaluedMap<String, String> map = pathSegment.getMatrixParameters();
        java.util.List<String> id = map.get("id");
        if (id != null && !id.isEmpty()) {
            key.setId(Integer.parseInt(id.get(0)));
        }
        java.util.List<String> version = map.get("version");
        if (version != null && !version.isEmpty()) {
            key.setVersion(Short.parseShort(version.get(0)));
        }
        return key;
    }

//    @GET
//    @Path("{id}")
//    @Produces(MediaType.APPLICATION_JSON)
//    public Response find(@PathParam("id") PathSegment id) {
//        try {
//            SistemaGestionPK key = getPrimaryKey(id);
//            SistemaGestion sg = sistemaGestionFacade.find(key);
//            return Response.ok().entity(sg).build();
//        } catch (Exception ex) {
//            return Util.manageException(ex, SistemaGestionREST.class);
//        }
//    }
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response create(SistemaGestion entity) {
        try {
            entity.setEmpresa(new Empresa(super.getEmpresaIdRequestContext()));
            entity = sistemaGestionFacade.create(entity);
            return Response.ok(entity).build();
        } catch (Exception ex) {
            return Util.manageException(ex, SistemaGestionREST.class);
        }
    }

    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    public Response edit(SistemaGestion entity) {
        try {
            entity.setEmpresa(new Empresa(super.getEmpresaIdRequestContext()));
            SistemaGestion sge = sistemaGestionFacade.edit(entity);
            if (entity != sge) {
                throw new UserMessageException("Se ha actualizado parcialmente el sistema de gestión.", "No es posible actualizar los elementos, existen evaluaciones iniciadas con el sistema de gestión que intenta modificar", TipoMensaje.info);
            }
            return Response.ok(entity).build();
        } catch (Exception ex) {
            return Util.manageException(ex, SistemaGestionREST.class);
        }
    }

    @GET
    @Path("evaluacion/{evaluacionId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response findByEvaluacion(@PathParam("evaluacionId") Integer evaluacionId) {
        try {
            SistemaGestion sg = sistemaGestionFacade.findByEvaluacion(evaluacionId);
            return Response.ok(sg).build();
        } catch (Exception ex) {
            return Util.manageException(ex, EvaluacionREST.class);
        }
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response findWithFilter(@BeanParam FilterQuery filterQuery) {
        try {
            boolean filtradoEmpresa = false;
            Filter filtroConsultarElementos = null;
            for (Filter filter : filterQuery.getFilterList()) {
                if (filter.getField().equals("empresa.id")) {
                    filtradoEmpresa = true;
                }
                if (filter.getField().equals("consultarElementos")) {
                    filtroConsultarElementos = filter;
                }
            }
            filterQuery.getFilterList().remove(filtroConsultarElementos);

            if (!filtradoEmpresa) {
                Filter empFilt = new Filter();
                empFilt.setCriteria("eq");
                empFilt.setField("empresa.id");
                empFilt.setValue1(super.getEmpresaIdRequestContext().toString());
                filterQuery.getFilterList().add(empFilt);
            }
            List<SistemaGestion> list = sistemaGestionFacade.findWithFilter(filterQuery);
            if (filtroConsultarElementos != null && Boolean.getBoolean(filtroConsultarElementos.getValue1())) {
                list.forEach((sg) -> {
                    sg.setElementoList(null);
                });
            }
            return Response.ok(list).build();
        } catch (Exception ex) {
            return Util.manageException(ex, SistemaGestionREST.class);
        }
    }

}
