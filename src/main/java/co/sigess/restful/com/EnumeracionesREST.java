/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.sigess.restful.com;

import co.sigess.entities.com.TipoIdentificacion;
import co.sigess.entities.com.TipoSede;
import co.sigess.entities.com.TipoVinculacion;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 *
 * @author fmoreno
 */
@Path("enums")
public class EnumeracionesREST {

    @GET
    @Path("tipoIdentificacion")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response findTipoIdentificacion() {        
        return Response.ok(TipoIdentificacion.values()).build();
    }
       
    @GET
    @Path("tipoVinculacion")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response findTipoVinculacion() {        
        return Response.ok(TipoVinculacion.values()).build();
    }
          
    @GET
    @Path("tipoSede")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response findTipoSede() {        
        return Response.ok(TipoSede.values()).build();
    }
}
