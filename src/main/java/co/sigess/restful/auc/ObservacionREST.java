/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.sigess.restful.auc;

import co.sigess.entities.auc.Observacion;
import co.sigess.entities.emp.Usuario;
import co.sigess.facade.auc.ObservacionFacade;
import co.sigess.facade.core.EmailFacade;
import co.sigess.facade.core.TipoMail;
import co.sigess.facade.emp.UsuarioFacade;
import co.sigess.restful.Filter;
import co.sigess.restful.FilterQuery;
import co.sigess.restful.FilterResponse;
import co.sigess.restful.ServiceREST;
import co.sigess.restful.sec.TareaDesviacionREST;
import co.sigess.restful.security.Secured;
import co.sigess.util.Util;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
 * @author fmoreno
 */
@Secured
@Path("observacion")
public class ObservacionREST extends ServiceREST {

    @EJB
    private ObservacionFacade observacionFacade;
    
    @EJB
    private UsuarioFacade usuarioFacade;

    @POST
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response create(Observacion observacion) {
        try {
            observacion.setUsuarioReporta(super.getUsuarioRequestContext());
            observacion = observacionFacade.create(observacion, super.getEmpresaIdRequestContext());
            return Response.ok(observacion).build();
        } catch (Exception ex) {
            return Util.manageException(ex, TareaDesviacionREST.class);
        }
    }

//    @GET
//    @Path("usuario")
//    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
//    public Response findAllByUsuario() {
//        try {
//            List<Observacion> list = observacionFacade.findAllByUsuarioEmpresa(super.getUsuarioRequestContext().getId(), super.getEmpresaIdRequestContext());
//            return Response.ok(list).build();
//        } catch (Exception ex) {
//            return Util.manageException(ex, TarjetaREST.class);
//        }
//    }
    @GET
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    @Override
    public Response findWithFilter(@BeanParam FilterQuery filterQuery) {
        try {
            boolean filtradoEmpresa = false;
            for (Filter filter : filterQuery.getFilterList()) {
                if (filter.getField().equals("tarjeta.empresa.id")) {
                    filtradoEmpresa = true;
                }
            }

            if (!filtradoEmpresa) {
                Filter empFilt = new Filter();
                empFilt.setCriteria("eq");
                empFilt.setField("tarjeta.empresa.id");
                empFilt.setValue1(super.getEmpresaIdRequestContext().toString());
                filterQuery.getFilterList().add(empFilt);
            }
             long numRows = filterQuery.isCount() ? observacionFacade.countWithFilter(filterQuery) : -1;
            List list = observacionFacade.findWithFilter(filterQuery);
            
            FilterResponse filterResponse = new FilterResponse();
            filterResponse.setData(list);
            filterResponse.setCount(numRows);
            return Response.ok(filterResponse).build();
        } catch (Exception ex) {
            return Util.manageException(ex, TareaDesviacionREST.class);
        }
    }

    @PUT
    @Path("aceptar")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response aceptarObservacion(Observacion observacion) {
        try {
            observacion.setUsuarioRevisa(super.getUsuarioRequestContext());
            observacion = observacionFacade.aceptarObservacion(observacion);
            return Response.ok(observacion).build();
        } catch (Exception ex) {
            return Util.manageException(ex, TareaDesviacionREST.class);
        }
    }

    @PUT
    @Path("denegar")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response denegarObservacion(Observacion observacion) {
        try {
            observacion.setUsuarioRevisa(super.getUsuarioRequestContext());
            observacion = observacionFacade.denegarObservacion(observacion);
            return Response.ok(observacion).build();
        } catch (Exception ex) {
            return Util.manageException(ex, TareaDesviacionREST.class);
        }
    }

}
