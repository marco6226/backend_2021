/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.sigess.restful.cop;

import co.sigess.entities.cop.Acta;
import co.sigess.facade.cop.ActaFacade;
import co.sigess.restful.FilterQuery;
import co.sigess.restful.ServiceREST;
import co.sigess.restful.security.Secured;
import co.sigess.restful.security.ValidadorArea;
import co.sigess.util.Util;
import java.util.Date;
import javax.ws.rs.DELETE;
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
@Path("acta")
public class ActaREST extends ServiceREST {

    public ActaREST() {
        super(ActaFacade.class, "empresaId");
    }

    @ValidadorArea("area.id")
    @Override
    public Response findWithFilter(FilterQuery filterQuery) {
        return super.findWithFilter(filterQuery);
    }

    @POST
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response create(Acta acta) {
        try {
            acta.setUsuarioId(super.getUsuarioRequestContext().getId());
            acta.setFechaElaboracion(new Date());
            acta.setEmpresaId(super.getEmpresaIdRequestContext());
            ((ActaFacade) beanInstance).create(acta);
            return Response.ok(acta).build();
        } catch (Exception ex) {
            return Util.manageException(ex, ActaREST.class);
        }
    }

    @PUT
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response edit(Acta cargo) {
        try {
            ((ActaFacade) beanInstance).edit(cargo);
            return Response.ok(cargo).build();
        } catch (Exception ex) {
            return Util.manageException(ex, ActaREST.class);
        }
    }

    @DELETE
    @Path("{actaId}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response delete(@PathParam("actaId") Long actaId) {
        try {
            Acta acta = new Acta(actaId);
            ((ActaFacade) beanInstance).remove(acta);
            return Response.ok(acta).build();
        } catch (Exception ex) {
            return Util.manageException(ex, ActaREST.class);
        }
    }

}
