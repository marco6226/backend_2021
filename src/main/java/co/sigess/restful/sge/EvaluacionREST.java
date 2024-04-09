/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.sigess.restful.sge;

import co.sigess.entities.sge.Evaluacion;
import co.sigess.entities.sge.IndicadoresSGE;
import co.sigess.entities.sge.SistemaGestion;
import co.sigess.entities.sge.dto.DesviacionSGEDTO;
import co.sigess.facade.sge.EvaluacionFacade;
import co.sigess.restful.CriteriaFilter;
import co.sigess.restful.Filter;
import co.sigess.restful.FilterQuery;
import co.sigess.restful.ServiceREST;
import co.sigess.restful.security.Secured;
import co.sigess.util.Util;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.ejb.EJB;
import javax.ws.rs.BeanParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
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
@Path("evaluacion")
public class EvaluacionREST extends ServiceREST{

    @EJB
    private EvaluacionFacade evaluacionFacade;

    public EvaluacionREST() {
        super(EvaluacionFacade.class);
    }
    
    @POST
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response create(Evaluacion evaluacion) {
        try {
            evaluacion = evaluacionFacade.create(evaluacion);
            return Response.ok(evaluacion).build();
        } catch (Exception ex) {
            return Util.manageException(ex, EvaluacionREST.class);
        }
    }

    @PUT
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response update(Evaluacion evaluacion) {
        try {
            evaluacion = evaluacionFacade.edit(evaluacion);
            return Response.ok(evaluacion).build();
        } catch (Exception ex) {
            return Util.manageException(ex, EvaluacionREST.class);
        }
    }

    @GET
    @Path("desviaciones/{evaluacionId}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response findDesviaciones(@PathParam("evaluacionId") Integer evaluacionId) {
        try {
            List<DesviacionSGEDTO> list = evaluacionFacade.findDesviaciones(evaluacionId);
            return Response.ok(list).build();
        } catch (Exception ex) {
            return Util.manageException(ex, EvaluacionREST.class);
        }
    }

//    @GET
//    @Path("empresa/{empresaId}")
//    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
//    public Response findByEmpresa(
//            @PathParam("empresaId") Integer empresaId,
//            @QueryParam("sgeId") String sgeId,
//            @QueryParam("sgeVersion") String sgeVersion,
//            @QueryParam("sortField") String sortField,
//            @QueryParam("sortOrder") String sortOrder
//    ) {
//
//        Map<String, String> filter = new HashMap<>();
//        if (sgeId != null) {
//            filter.put("sistemaGestion.sistemaGestionPK.id", sgeId);
//        }
//        if (sgeVersion != null) {
//            filter.put("sistemaGestion.sistemaGestionPK.version", sgeVersion);
//        }
//
//        try {
//            List<Evaluacion> list = evaluacionFacade.findByEmpresa(empresaId, filter, sortField, sortOrder);
//            return Response.ok(list).build();
//        } catch (Exception cve) {
//            return Util.manageException(cve, EvaluacionREST.class);
//        }
//    }
    
    @GET
    @Path("indicadores/empresa/{empresaId}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response findIndicadoresByEmpresa(
            @PathParam("empresaId") Integer empresaId,
            @QueryParam("sgeId") String sgeId,
            @QueryParam("sgeVersion") String sgeVersion,
            @QueryParam("sortField") String sortField,
            @QueryParam("sortOrder") String sortOrder
    ) {

        Map<String, String> filter = new HashMap<>();
        if (sgeId != null) {
            filter.put("sistemaGestion.sistemaGestionPK.id", sgeId);
        }
        if (sgeVersion != null) {
            filter.put("sistemaGestion.sistemaGestionPK.version", sgeVersion);
        }

        try {
            List<IndicadoresSGE> list = evaluacionFacade.findIndicadoresSGE(empresaId);
            return Response.ok(list).build();
        } catch (Exception cve) {
            return Util.manageException(cve, EvaluacionREST.class);
        }
    }

}
