/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.sigess.restful.ind;

import co.sigess.entities.ind.AccesoTablero;
import co.sigess.entities.ind.Tablero;
import co.sigess.facade.ind.AccesoTableroFacade;
import co.sigess.facade.ind.TableroFacade;
import co.sigess.restful.FilterResponse;
import co.sigess.restful.Request;
import co.sigess.restful.sec.TareaDesviacionREST;
import co.sigess.restful.security.Secured;
import co.sigess.util.Util;
import java.util.List;
import javax.ejb.EJB;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 *
 * @author fmoreno
 */
@Secured
@Path("tablero")
public class TableroREST extends Request {

    @EJB
    private TableroFacade tableroFacade;

    @EJB
    private AccesoTableroFacade accFacade;

    @GET
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response find() {
        try {
            List<Tablero> list = tableroFacade.findByUser(super.getUsuarioRequestContext().getId());
            FilterResponse filterResponse = new FilterResponse();
            filterResponse.setData(list);
            filterResponse.setCount(list.size());
            return Response.ok(filterResponse).build();
        } catch (Exception ex) {
            return Util.manageException(ex, TareaDesviacionREST.class);
        }
    }

    @POST
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response create(Tablero tablero) {
        try {
            tablero.setEmpresaId(super.getEmpresaIdRequestContext());
            tablero = tableroFacade.create(tablero);

            AccesoTablero at = new AccesoTablero();
            at.setConsultar(Boolean.TRUE);
            at.setModificar(Boolean.TRUE);
            at.setPropietario(Boolean.TRUE);
            at.setUsuarioId(super.getUsuarioRequestContext().getId());
            at.setTablero(tablero);
            accFacade.create(at);

            return Response.ok(tablero).build();
        } catch (Exception ex) {
            return Util.manageException(ex, TareaDesviacionREST.class);
        }
    }
}
