/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.sigess.restful.emp;

import co.sigess.entities.emp.Area;
import co.sigess.entities.emp.Empresa;
import co.sigess.entities.emp.TipoArea;
import co.sigess.facade.emp.AreaFacade;
import co.sigess.restful.CriteriaFilter;
import co.sigess.restful.Filter;
import co.sigess.restful.FilterQuery;
import co.sigess.restful.FilterResponse;
import co.sigess.restful.ServiceREST;
import co.sigess.restful.rai.ReporteREST;
import co.sigess.restful.security.Secured;
import co.sigess.util.Util;
import java.io.IOException;
import java.text.ParseException;
import java.util.List;
import javax.ejb.EJB;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
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
@Path("area")
public class AreaREST extends ServiceREST{

    @EJB
    private AreaFacade areaFacade;

    public AreaREST() {
        super(AreaFacade.class);
    }
    
    @PersistenceContext(unitName = "SIGESS_PU")
    private EntityManager em;
//    @GET
//    @Path("empresa/{empresaId}")
//    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
//    public Response findByEmpresa(@PathParam("empresaId") Integer empresaId) {
//        try {
//            List<Area> areaList = areaFacade.findByEmpresa(empresaId);
//            return Response.ok(areaList).build();
//        } catch (Exception iae) {
//            return Util.manageException(iae, AreaREST.class);
//        }
//    }

    @Override
    public Response findWithFilter(FilterQuery filterQuery) {
        try {
            Query q1 = em.createNativeQuery("SELECT e.id_empresa_aliada from emp.empresa e where e.id = ?1");
            q1.setParameter(1, getEmpresaIdRequestContext());
            List list1 = q1.getResultList();
        
            boolean filtradoEmpresa = false;
            for (Filter filter : filterQuery.getFilterList()) {
                if (filter.getField().equals("tipoArea.empresa.id")) {
                    filtradoEmpresa = true;
                }
            }
            
            if (!filtradoEmpresa) {
                Filter empFilt = new Filter();
                empFilt.setCriteriaEnum(CriteriaFilter.EQUALS);
                empFilt.setField("tipoArea.empresa.id");
                if(list1.get(0) != null){
                    empFilt.setValue1(list1.get(0).toString());
                }else{
                    empFilt.setValue1(getEmpresaIdRequestContext().toString());
                }
                filterQuery.getFilterList().add(empFilt);
            }
            
            long numRows = filterQuery.isCount() ? beanInstance.countWithFilter(filterQuery) : -1;
            List list = beanInstance.findWithFilter(filterQuery);
            
            FilterResponse filterResponse = new FilterResponse();
            filterResponse.setData(list);
            filterResponse.setCount(numRows);
            return Response.ok(filterResponse).build();
        } catch (IOException | NoSuchFieldException | ParseException ex) {
            return Util.manageException(ex, ReporteREST.class);
        }
    }
    
    @GET
    @Path("filterArea")
    @Secured(requiereEmpresaId = false)
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response localidadesFirmWithFilter(@BeanParam FilterQuery filterQuery){
        try {
            if(filterQuery == null){
                filterQuery = new FilterQuery();
            }
            long numRows = filterQuery.isCount() ? areaFacade.countWithFilter(filterQuery) : -1;
            List list = areaFacade.findWithFilter(filterQuery);
            
            FilterResponse filterResponse = new FilterResponse();
            filterResponse.setData(list);
            filterResponse.setCount(numRows);
            return Response.ok(filterResponse).build();
        } catch (Exception e) {
            return Util.manageException(e, AreaREST.class);
        }
    }
    
    

//    @GET
//    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
//    public Response findAll() {
//        try {
//            List<Area> areaList = areaFacade.findByEmpresa(super.getEmpresaIdRequestContext());
//            return Response.ok(areaList).build();
//        } catch (Exception iae) {
//            return Util.manageException(iae, AreaREST.class);
//        }
//    }

    @GET
    @Path("areaPadre/{areaPadreId}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response findByAreaPadre(@PathParam("areaPadreId") Long areaPadreId) {
        try {
            List<Area> areaList = areaFacade.findByAreaPadre(areaPadreId);
            return Response.ok(areaList).build();
        } catch (Exception iae) {
            return Util.manageException(iae, AreaREST.class);
        }
    }

    @POST
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response create(Area area) {
        try {
            area = areaFacade.create(area);
            return Response.ok(area).build();
        } catch (Exception iae) {
            return Util.manageException(iae, AreaREST.class);
        }
    }

    @PUT
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response edit(Area area) {
        try {
            area = areaFacade.edit(area);
            return Response.ok(area).build();
        } catch (Exception iae) {
            return Util.manageException(iae, AreaREST.class);
        }

    }

    @DELETE
    @Path("{areaId}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response delete(@PathParam("areaId") Long areaId) {
        try {
            Area area = areaFacade.eliminar(areaId);
            return Response.ok(area).build();
        } catch (Exception iae) {
            return Util.manageException(iae, AreaREST.class);
        }

    }
}
