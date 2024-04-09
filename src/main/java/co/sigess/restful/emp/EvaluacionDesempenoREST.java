/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.sigess.restful.emp;

import co.sigess.entities.emp.EvaluacionDesempeno;
import co.sigess.facade.emp.EvaluacionDesempenoFacade;
import co.sigess.restful.CriteriaFilter;
import co.sigess.restful.Filter;
import co.sigess.restful.FilterQuery;
import co.sigess.restful.FilterResponse;
import co.sigess.restful.ServiceREST;
import co.sigess.restful.rai.ReporteREST;
import co.sigess.restful.security.Secured;
import co.sigess.util.Util;
import java.util.Date;
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
 * @author fmoreno
 */
@Secured
@Path("evaluacionDesempeno")
public class EvaluacionDesempenoREST extends ServiceREST{

    @EJB
    private EvaluacionDesempenoFacade evaluacionDesempenoFacade;
    
    
    public EvaluacionDesempenoREST() {
        super(EvaluacionDesempenoFacade.class, "cargo.empresa.id");        
    }
    
    @POST
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response create(EvaluacionDesempeno evalDesemp) {
        try {
            evalDesemp.setUsuarioElabora(super.getUsuarioRequestContext());
            evalDesemp.setFechaElaboracion(new Date());
            evalDesemp = this.evaluacionDesempenoFacade.create(evalDesemp);
            return Response.ok(evalDesemp).build();
        } catch (Exception ex) {
            return Util.manageException(ex, EvaluacionDesempenoREST.class);
        }
    }

    @PUT
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response edit(EvaluacionDesempeno evalDesemp) {
        try {
            evalDesemp = this.evaluacionDesempenoFacade.edit(evalDesemp);
            return Response.ok(evalDesemp).build();
        } catch (Exception ex) {
            return Util.manageException(ex, EvaluacionDesempenoREST.class);
        }
    }



}
