/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.sigess.restful.scm;

import co.sigess.entities.scm.CasosMedicos;
import co.sigess.entities.scm.Recomendaciones;

import co.sigess.facade.scm.CasosMedicosFacade;
import co.sigess.facade.scm.RecomendacionesFacade;
import co.sigess.restful.Filter;
import co.sigess.restful.FilterQuery;
import co.sigess.restful.ServiceREST;
import co.sigess.restful.emp.EmpleadoREST;
import co.sigess.restful.rai.ReporteREST;
import co.sigess.util.Util;
import java.util.List;
import javax.ejb.EJB;
import javax.ws.rs.BeanParam;
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
 * @author leonardo
 */
@Path("casomedico")
public class CasoMedicoREST extends ServiceREST {

    @EJB
    private CasosMedicosFacade casosmedicosFacade;

    @EJB
    private RecomendacionesFacade recomendacionesFacade;

    public CasoMedicoREST() {
        super(CasosMedicosFacade.class);

    }

    @GET
    @Path("all")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response list() {
        try {
            List list = this.casosmedicosFacade.findAll();
            return Response.ok(list).build();
        } catch (Exception ex) {
            return Util.manageException(ex, ReporteREST.class);
        }
    }

    @POST
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response create(CasosMedicos casosmedicos) {
        try {
            casosmedicos = this.casosmedicosFacade.create(casosmedicos);
            return Response.ok(casosmedicos.getId()).build();
        } catch (Exception ex) {
            return Util.manageException(ex, ReporteREST.class);
        }
    }

    @PUT
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response edit(CasosMedicos casosmedicos) {
        try {

            casosmedicos = this.casosmedicosFacade.update(casosmedicos);
            return Response.ok(casosmedicos.getId()).build();
        } catch (Exception ex) {
            return Util.manageException(ex, ReporteREST.class);
        }
    }

    @GET
    @Path("recomendation/{parametro}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response listReco(@PathParam("parametro") String parametro) {
        try {
            FilterQuery filterQuery = new FilterQuery();
            System.out.print("Aqui paso" + parametro);
            Filter empFilt = new Filter();
            empFilt.setCriteria("eq");
            empFilt.setField("pk_user");
            empFilt.setValue1(parametro);
            //filterQuery.getFilterList().add(empFilt);

            for (Filter filter : filterQuery.getFilterList()) {
                System.out.print(filter.getField());
            }
            List list = this.recomendacionesFacade.findWithFilter(filterQuery);
            return Response.ok(list).build();
        } catch (Exception ex) {
            return Util.manageException(ex, ReporteREST.class);
        }
    }

    @POST
    @Path("recomendation")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response createReco(Recomendaciones recomendaciones) {
        try {
            recomendaciones = this.recomendacionesFacade.create(recomendaciones);
            return Response.ok(recomendaciones.getId()).build();
        } catch (Exception ex) {
            return Util.manageException(ex, ReporteREST.class);
        }
    }

    @GET
    @Path("validate/{parametro}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response buscar(@PathParam("parametro") String parametro) {
        try {
            List<CasosMedicos> list = casosmedicosFacade.buscar(parametro);
            System.out.println(list.size());

            if (list.size() > 0) {

                System.out.println(list.size());

                return Response.status(400, MediaType.APPLICATION_JSON).build();
            } else {
                //   StringTokenizerst = new StringTokenizer(fields, ",");
                return Response.ok(list).build();

            }
        } catch (Exception ex) {
            return Util.manageException(ex, EmpleadoREST.class);
        }
    }

}
