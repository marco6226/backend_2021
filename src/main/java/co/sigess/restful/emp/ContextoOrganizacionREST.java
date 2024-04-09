/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.sigess.restful.emp;

import co.sigess.entities.emp.ContextoOrganizacion;
import co.sigess.entities.emp.Empresa;
import co.sigess.facade.emp.ContextoOrganizacionFacade;
import co.sigess.restful.FilterQuery;
import co.sigess.restful.ServiceREST;
import co.sigess.restful.security.Secured;
import co.sigess.util.Util;
import java.util.Date;
import javax.ejb.EJB;
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
@Path("contextoOrganizacion")
public class ContextoOrganizacionREST extends ServiceREST{

    @EJB
    private ContextoOrganizacionFacade contextoOrganizacionFacade;
    
    @Override
    @GET
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response findWithFilter(FilterQuery filterQuery) {
        try {
            ContextoOrganizacion ctx = contextoOrganizacionFacade.findDefault(super.getEmpresaIdRequestContext());
            return Response.ok(ctx).build();
        } catch (Exception ex) {
            return Util.manageException(ex, ContextoOrganizacionREST.class);
        }
    }    
    
    @POST
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response create(ContextoOrganizacion ctx) {
        try {
            ctx.setEmpresa(new Empresa(super.getEmpresaIdRequestContext()));
            ctx.setFechaElaboracion(new Date());
            ctx.setUsuarioElaboraId(super.getUsuarioRequestContext().getId());
            ctx.setVersion(0);
            contextoOrganizacionFacade.create(ctx);
            return Response.ok(ctx).build();
        } catch (Exception ex) {
            return Util.manageException(ex, ContextoOrganizacionREST.class);
        }
    }

    @PUT
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response edit(ContextoOrganizacion ctx) {
        try {
            ContextoOrganizacion ctxdb = contextoOrganizacionFacade.findDefault(super.getEmpresaIdRequestContext());
            
            ctx.setFechaModificacion(new Date());
            ctx.setUsuarioModificaId(super.getUsuarioRequestContext().getId());
            ctx.setVersion(ctxdb.getVersion() + 1);
            contextoOrganizacionFacade.edit(ctx);
            return Response.ok(ctx).build();
        } catch (Exception ex) {
            return Util.manageException(ex, ContextoOrganizacionREST.class);
        }
    }


}
