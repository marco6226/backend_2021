/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.sigess.restful.ind;

import co.sigess.entities.emp.Empresa;
import co.sigess.entities.ind.LogIndicadores;
import co.sigess.facade.ind.LogIndicadoresFacade;
import co.sigess.restful.ServiceREST;
import co.sigess.restful.security.Secured;
import co.sigess.util.Util;
import java.util.Date;
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
 * @author Usuario
 */
@Secured
@Path("logIndicadores")
public class LogIndicadoresRest extends ServiceREST{
    public LogIndicadoresRest(){
        super(LogIndicadoresFacade.class);
    }
    
    @GET
    @Path("{logIndicadores}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response find(@PathParam("logIndicadoresId") Integer logIndicadoresId) {
        try{
            LogIndicadores logIndicadores = ((LogIndicadoresFacade) beanInstance).find(logIndicadoresId);
            return Response.ok(logIndicadores).build();
        }catch(Exception ex){
            return Util.manageException(ex, LogIndicadoresRest.class);
        }
    }
    
    @POST
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response create(LogIndicadores logIndicadores){
        try{
            logIndicadores.setEmpresa(new Empresa(super.getEmpresaIdRequestContext()));
            logIndicadores.setFecha(new Date());
            logIndicadores=((LogIndicadoresFacade) beanInstance).create(logIndicadores);
            return Response.ok(logIndicadores).build();
        } catch(Exception ex){
            return Util.manageException(ex, LogIndicadoresRest.class);
        }
    }
    
    @PUT
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response edit(LogIndicadores logIndicadores){
        try{
            logIndicadores.setEmpresa(new Empresa(super.getEmpresaIdRequestContext()));
            logIndicadores=((LogIndicadoresFacade) beanInstance).edit(logIndicadores);
            return Response.ok(logIndicadores).build();
        }catch(Exception ex){
            return Util.manageException(ex, LogIndicadoresRest.class);
        }
    }
}
