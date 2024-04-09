/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.sigess.restful.sge;

import co.sigess.entities.sge.Elemento;
import co.sigess.facade.sge.ElementoFacade;
import co.sigess.restful.security.Secured;
import co.sigess.util.Util;
import javax.ejb.EJB;
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
@Path("elementoSGE")
public class ElementoREST {

    @EJB
    private ElementoFacade elementoFacade;

    @PUT
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response actualizarDocumentos(Elemento elemento) {
        try {
            elemento = elementoFacade.actualizarDocumentos(elemento);
            return Response.ok(elemento).build();
        } catch (Exception ex) {
            return Util.manageException(ex, ElementoREST.class);
        }
    }

}
