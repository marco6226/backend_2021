/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.sigess.restful.sec;

import co.sigess.entities.sec.Desviacion;
import co.sigess.facade.sec.DesviacionFacade;
import co.sigess.facade.sec.DesviacionInspeccionFacade;
import co.sigess.restful.Filter;
import co.sigess.restful.FilterQuery;
import co.sigess.restful.FilterResponse;
import co.sigess.restful.ServiceREST;
import co.sigess.restful.inp.InspeccionREST;
import co.sigess.restful.security.Secured;
import co.sigess.restful.security.ValidadorArea;
import co.sigess.util.Util;
import java.io.ByteArrayOutputStream;
import java.util.Date;
import java.util.List;
import javax.ejb.EJB;
import javax.ws.rs.BeanParam;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 *
 * @author fmoreno
 */
@Secured
@Path("desviacion")
public class DesviacionREST extends ServiceREST {

    @EJB
    private DesviacionInspeccionFacade desviacionInspeccionFacade;

    public DesviacionREST() {
        super(DesviacionFacade.class, "empresaId");
    }
    @GET
    @Path("findDesviacion")
     @Secured(validarPermiso = false)
    @Produces(MediaType.APPLICATION_JSON)
    public Response findWithoutAreaValidation(@BeanParam FilterQuery filterQuery) {
        try {
            // Realiza la búsqueda utilizando la fachada de desviaciones
            List<Desviacion> desviacionList = ((DesviacionFacade) super.beanInstance).findWithFilter(filterQuery);

            // Envuelve el resultado en un FilterResponse, si lo necesitas
            FilterResponse filterResponse = new FilterResponse();
            filterResponse.setData(desviacionList);
            filterResponse.setCount(desviacionList.size());

            // Retorna la respuesta con los datos
            return Response.ok(filterResponse).build();
        } catch (Exception ex) {
            return Util.manageException(ex, DesviacionREST.class);
        }
    }

    @Override
    @ValidadorArea("area.id")
    public Response findWithFilter(FilterQuery filterQuery) {
        return super.findWithFilter(filterQuery);
    }

    @GET
    @Path("inspecciones")
    @Produces(MediaType.APPLICATION_JSON)
    public Response findInpWithFilter(@BeanParam FilterQuery filterQuery) {
        try {
            if (filterQuery == null) {
                filterQuery = new FilterQuery();
            }
            boolean filtradoEmpresa = false;
            for (Filter filter : filterQuery.getFilterList()) {
                if (filter.getField().equals("empresaId")) {
                    filtradoEmpresa = true;
                    break;
                }
            }
            if (!filtradoEmpresa) {
                Filter empFilt = new Filter();
                empFilt.setCriteria("eq");
                empFilt.setField("empresaId");
                empFilt.setValue1(super.getEmpresaIdRequestContext().toString());
                filterQuery.getFilterList().add(empFilt);
            }
            long numRows = filterQuery.isCount() ? desviacionInspeccionFacade.countWithFilter(filterQuery) : -1;
            List list = desviacionInspeccionFacade.findWithFilter(filterQuery);

            FilterResponse filterResponse = new FilterResponse();
            filterResponse.setData(list);
            filterResponse.setCount(numRows);
            return Response.ok(filterResponse).build();
        } catch (Exception ex) {
            return Util.manageException(ex, DesviacionREST.class);
        }
    }

    @GET
    @Path("consinv")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_OCTET_STREAM})
    public Response consultarConsolidado(
            @QueryParam("invDesde") Date invDesde,
            @QueryParam("invHasta") Date invHasta
    ) {
        if (invDesde == null) {
            throw new IllegalArgumentException("Debe especificar la fecha de inicio de rango de consulta");
        }
        if (invHasta == null) {
            throw new IllegalArgumentException("Debe especificar la fecha de fin de rango de consulta");
        }
        try {
            ByteArrayOutputStream out = ((DesviacionFacade) super.beanInstance).consultarConsolidado(super.getEmpresaIdRequestContext(), invDesde, invHasta);
            return Response.ok(out.toByteArray(), MediaType.APPLICATION_OCTET_STREAM_TYPE).build();
        } catch (Exception ex) {
            return Util.manageException(ex, DesviacionREST.class);
        }
    }
    
    @GET
    @Path("desviacionId/{desviacionId}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response findReporteAlido(@PathParam("desviacionId") int desviacionId) {
        try {            
            List<Desviacion> list = ((DesviacionFacade) super.beanInstance).findDesviacionAlido(desviacionId);
            return Response.ok(list).build();
        } catch (Exception ex) {
            return Util.manageException(ex, DesviacionREST.class);
        }
    }


}
