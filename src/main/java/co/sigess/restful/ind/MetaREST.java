/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.sigess.restful.ind;

import co.sigess.entities.ind.Meta;
import co.sigess.facade.ind.MetaFacade;
import co.sigess.restful.CriteriaFilter;
import co.sigess.restful.Filter;
import co.sigess.restful.FilterQuery;
import co.sigess.restful.FilterResponse;
import co.sigess.restful.ServiceREST;
import co.sigess.restful.security.Compress;
import co.sigess.restful.security.Secured;
import co.sigess.util.Util;
import java.util.List;
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
 * @author LENOVO
 */
@Secured
@Path("meta")
public class MetaREST extends ServiceREST {
    
    @EJB
    private MetaFacade metaFacade;
    
    private String empresaField = "empresaId";
    
    public MetaREST() {
        super(MetaFacade.class);
    }
    
    @Compress
    @Override
    public Response findWithFilter(@BeanParam FilterQuery filterQuery) {
        try {
            int paramEmpFilt = -1;
            for(int i = 0; i < filterQuery.getFilterList().size(); i++){
                Filter filter = filterQuery.getFilterList().get(i);
                if(filter.getField().equals(this.empresaField)) {
                    paramEmpFilt = Integer.parseInt(filter.getValue1());
                    break;
                }
            }
            
            if (paramEmpFilt < 0) {
                Filter empFilt = new Filter();
                empFilt.setCriteriaEnum(CriteriaFilter.EQUALS);
                empFilt.setField(this.empresaField);
                empFilt.setValue1(getEmpresaIdRequestContext().toString());
                filterQuery.getFilterList().add(empFilt);
            }
            
            long numRows = filterQuery.isCount() ? beanInstance.countWithFilter(filterQuery) : -1;
            List list = beanInstance.findWithFilter(filterQuery);
            
            FilterResponse filterResponse = new FilterResponse();
            filterResponse.setCount(numRows);
            filterResponse.setData(list);
            return Response.ok(filterResponse).build();
        } catch (Exception e) {
            return Util.manageException(e, MetaREST.class);
        }
    }
    
    @POST
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response create(List<Meta> metas) {
        try {
            metas = metaFacade.create(metas);
            return Response.ok(metas).build();
        } catch (Exception e) {
            return Util.manageException(e, MetaREST.class);
        }
    }
    
    @PUT
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response update(List<Meta> metas) {
        try {
            metas = metaFacade.update(metas);
            return Response.ok(metas).build();
        } catch (Exception e) {
            return Util.manageException(e, MetaREST.class);
        }
    }
}
