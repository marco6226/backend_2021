/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.sigess.restful.conf;

import co.sigess.entities.conf.ConfiguracionGeneral;
import co.sigess.entities.conf.Manual;
import co.sigess.facade.conf.ManualFacade;
import co.sigess.restful.FilterResponse;
import co.sigess.restful.ServiceREST;
import co.sigess.restful.ado.DirectorioREST;
import co.sigess.restful.security.Secured;
import co.sigess.util.Util;
import java.io.ByteArrayOutputStream;
import java.util.List;
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
@Path("manual")
public class ManualREST extends ServiceREST {

    public ManualREST() {
        super(ManualFacade.class);
    }

    @GET
    @Path("usuario")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response findByUsuario() {
        try {
            List<Manual> list = ((ManualFacade) beanInstance).buscarPorUsuario(super.getEmpresaIdRequestContext(), super.getUsuarioRequestContext().getId());
            FilterResponse filterResponse = new FilterResponse();
            filterResponse.setData(list);
            filterResponse.setCount(list.size());
            return Response.ok(filterResponse).build();
        } catch (Exception ex) {
            return Util.manageException(ex, ManualREST.class);
        }
    }

    @GET
    @Path("descargar/{id}")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_OCTET_STREAM})
    public Response downloadFile(@PathParam("id") Integer manualId) throws Exception {
        try {
            ByteArrayOutputStream file = (ByteArrayOutputStream) ((ManualFacade) beanInstance).findFile(manualId);
            return Response.ok(file.toByteArray(), MediaType.APPLICATION_OCTET_STREAM_TYPE).build();
        } catch (Exception ex) {
            return Util.manageException(ex, DirectorioREST.class);
        }
    }
}
