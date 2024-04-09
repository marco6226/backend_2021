/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.sigess.restful.sec;

import co.sigess.entities.sec.SistemaCausaRaiz;
import co.sigess.facade.sec.SistemaCausaRaizFacade;
import co.sigess.restful.ServiceREST;
import co.sigess.restful.security.Compress;
import co.sigess.restful.security.Secured;
import co.sigess.util.Util;
import java.util.List;
import javax.ejb.EJB;
import javax.ws.rs.GET;
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
@Path("sistemaCausaRaiz")
public class SistemaCausaRaizREST extends ServiceREST {

    @EJB
    private SistemaCausaRaizFacade sistemaCausaRaizFacade;

//    @GET
//    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
//    public Response findAll() {
//        try {
//            List<SistemaCausaRaiz> list = sistemaCausaRaizFacade.findByEmpresa(super.getEmpresaIdRequestContext());
//            return Response.ok(list).build();
//        } catch (Exception ex) {
//            return Util.manageException(ex, SistemaCausaRaizREST.class);
//        }
//    }

    @GET    
    @Compress
    @Path("seleccionado")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response findDefault() {
        try {
            SistemaCausaRaiz scr = sistemaCausaRaizFacade.findDefault(super.getEmpresaIdRequestContext());
            return Response.ok(scr).build();
        } catch (Exception ex) {
            return Util.manageException(ex, SistemaCausaRaizREST.class);
        }
    }
    
    @GET
    @Path("seleccionado2/{idEmpresa}")
    @Secured(requiereEmpresaId = false, validarPermiso = false)
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response findDefault2(@PathParam("idEmpresa") Integer idEmpresa){
        try {
            SistemaCausaRaiz scr = sistemaCausaRaizFacade.findDefault(idEmpresa);
            return Response.ok(scr).build();
        } catch (Exception e) {
            return Util.manageException(e, SistemaCausaRaizREST.class);
        }
    }

}
