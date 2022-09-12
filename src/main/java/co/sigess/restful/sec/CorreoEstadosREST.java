/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package co.sigess.restful.sec;

import co.sigess.entities.sec.CorreoEstados;
import co.sigess.facade.sec.CorreoEstadosFacade;
//import co.sigess.facade.sec.CorreoEstadosFacade;
import co.sigess.restful.ServiceREST;
import co.sigess.restful.security.Secured;
import javax.ejb.EJB;
import javax.ws.rs.Path;

/**
 *
 * @author LENOVO
 */
@Secured
@Path("correoEstados")
public class CorreoEstadosREST extends ServiceREST{
    
    //@EJB
    //private CorreoEstadosFacade correoEstadosFacade;

    public CorreoEstadosREST() {
        super(CorreoEstadosFacade.class);
    }

    /*@GET
    @Path("areaPadre/{areaPadreId}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response findByAreaPadre(@PathParam("areaPadreId") Long areaPadreId) {
        try {
            List<Area> areaList = areaFacade.findByAreaPadre(areaPadreId);
            return Response.ok(areaList).build();
        } catch (Exception iae) {
            return Util.manageException(iae, AreaREST.class);
        }
    }*/
}
