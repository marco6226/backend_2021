/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.sigess.restful.emp;

import co.sigess.entities.com.TipoMensaje;
import co.sigess.entities.emp.Empresa;
import co.sigess.entities.emp.TipoArea;
import co.sigess.exceptions.UserMessageException;
import co.sigess.facade.emp.TipoAreaFacade;
import co.sigess.restful.ServiceREST;
import co.sigess.restful.security.Secured;
import co.sigess.util.Util;
import java.util.logging.Level;
import javax.ws.rs.DELETE;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.logging.Logger;
import co.sigess.entities.com.Mensaje;

/**
 *
 * @author fmoreno
 */
@Secured
@Path("tipoArea")
public class TipoAreaREST extends ServiceREST {

    public TipoAreaREST() {
        super(TipoAreaFacade.class);
    }

    @POST
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response create(TipoArea tipoArea) {
        try {
            tipoArea.setEmpresa(new Empresa(getEmpresaIdRequestContext()));
            super.beanInstance.create(tipoArea);
            return Response.ok(tipoArea).build();
        } catch (Exception ex) {
            return Util.manageException(ex, TipoAreaREST.class);
        }
    }

    @PUT
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response edit(TipoArea tipoArea) {
        try {
            tipoArea.setEmpresa(new Empresa(getEmpresaIdRequestContext()));
            super.beanInstance.edit(tipoArea);
            return Response.ok(tipoArea).build();
        } catch (Exception ex) {
//            return Util.manageException(ex, TipoAreaREST.class);
            Logger.getLogger(TipoAreaREST.class.getName()).log(Level.SEVERE, "Error en la creación de TipoArea", ex);
            // Respuesta de error genérica
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                .entity(new Mensaje("Error en la creación de TipoArea", "Hubo un problema al procesar la solicitud"))
                .build();
        }
    }

    @DELETE
    @Path("{tipoAreaId}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response delete(@PathParam("tipoAreaId") Integer tipoAreaId) {
        try {
            TipoArea tipoArea = (TipoArea) super.beanInstance.find(tipoAreaId);
            if (tipoArea.getAreaList() == null || tipoArea.getAreaList().isEmpty()) {
                super.beanInstance.remove(tipoArea);
                return Response.ok(tipoArea).build();
            } else {
                throw new UserMessageException("No es posible eliminar el tipo de área", "Existen áreas del tipo que está intentado eliminar", TipoMensaje.warn);
            }
        } catch (Exception ex) {
            return Util.manageException(ex, TipoAreaREST.class);
        }
    }

}
