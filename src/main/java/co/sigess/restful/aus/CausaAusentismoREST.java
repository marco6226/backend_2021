/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.sigess.restful.aus;

import co.sigess.entities.aus.CausaAusentismo;
import co.sigess.facade.aus.CausaAusentismoFacade;
import co.sigess.restful.Filter;
import co.sigess.restful.FilterQuery;
import co.sigess.restful.ServiceREST;
import co.sigess.restful.security.Secured;
import co.sigess.util.Util;
import java.util.List;
import javax.ejb.EJB;
import javax.ws.rs.BeanParam;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 *
 * @author fmoreno
 */
@Secured
@Path("causaAusentismo")
public class CausaAusentismoREST extends ServiceREST{

    @EJB
    private CausaAusentismoFacade causaAusentismoFacade;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response findWithFilter(@BeanParam FilterQuery filterQuery) {
        try {
            boolean filtradoEmpresa = false;
            for (Filter filter : filterQuery.getFilterList()) {
                if (filter.getField().equals("empresa.id")) {
                    System.out.println(filter.getField());
                    filtradoEmpresa = true;
                    break;
                }
            }
            if (!filtradoEmpresa) {
                Filter empFilt = new Filter();
                empFilt.setCriteria("eq");
                empFilt.setField("empresa.id");
                empFilt.setValue1(super.getEmpresaIdRequestContext().toString());
                System.out.println(super.getEmpresaIdRequestContext().toString());
                filterQuery.getFilterList().add(empFilt);
            }
            List<CausaAusentismo> list = causaAusentismoFacade.findWithFilter(filterQuery);
            return Response.ok(list).build();
        } catch (Exception ex) {
            return Util.manageException(ex, CausaAusentismoREST.class);
        }
    }
}
