/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.sigess.restful;

import co.sigess.entities.emp.Empresa;
import co.sigess.facade.com.AbstractFacade;
import co.sigess.facade.emp.EmpresaFacade;
import co.sigess.restful.rai.ReporteREST;
import co.sigess.util.Util;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.ws.rs.BeanParam;
import javax.ws.rs.GET;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 *
 * @author fmoreno
 * @param <T>
 */
public abstract class ServiceREST<T extends AbstractFacade> extends Request {

    protected AbstractFacade<T> beanInstance;
    private String empresaField = "empresa.id";
    
    @EJB
    private EmpresaFacade empresaFacade;

    public ServiceREST() {
    }

    public ServiceREST(Class<T> beanClass) {
        try {
            this.beanInstance = InitialContext.doLookup("java:module/" + beanClass.getSimpleName());
        } catch (NamingException ex) {
            Logger.getLogger(ServiceREST.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public ServiceREST(Class<T> beanClass, String empresaField) {
        this(beanClass);
        this.empresaField = empresaField;
    }

    @GET
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response findWithFilter(@BeanParam FilterQuery filterQuery) {
        try {
            int paramEmpFilt = -1;
            for (int i = 0; i < filterQuery.getFilterList().size(); i++) {
                Filter filter = filterQuery.getFilterList().get(i);
                if (filter.getField().equals(this.empresaField)) {
                    paramEmpFilt = Integer.parseInt(filter.getValue1());
                    break;
                }
            }

            if (paramEmpFilt >= 0 && paramEmpFilt != getEmpresaIdRequestContext()) {
                Empresa emp = empresaFacade.find(getEmpresaIdRequestContext());
                if(emp.getIdEmpresaAliada() == null || paramEmpFilt != emp.getIdEmpresaAliada()){
                    throw new IllegalArgumentException("Parametro de empresa no coincide");
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
            filterResponse.setData(list);
            filterResponse.setCount(numRows);
            return Response.ok(filterResponse).build();
        } catch (Exception ex) {
            return Util.manageException(ex, ReporteREST.class);
        }
    }

}
