/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.sigess.restful.inp;

import co.sigess.entities.com.TipoMensaje;
import co.sigess.entities.inp.Programacion;
import co.sigess.entities.ipr.ParticipanteIpecr;
import co.sigess.exceptions.UserMessageException;
import co.sigess.facade.inp.ProgramacionFacade;
import co.sigess.restful.Filter;
import co.sigess.restful.FilterQuery;
import co.sigess.restful.FilterResponse;
import co.sigess.restful.ServiceREST;
import co.sigess.restful.ipr.ParticipanteIpecrREST;
import co.sigess.restful.security.Secured;
import co.sigess.restful.security.ValidadorArea;
import co.sigess.util.Util;
import java.time.LocalDate;
import java.util.List;
import javax.ejb.EJB;
import javax.ws.rs.BeanParam;
import javax.ws.rs.DELETE;
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
 * @author fmoreno
 */
@Secured
@Path("programacion")
public class ProgramacionREST extends ServiceREST {

    @EJB
    private ProgramacionFacade programacionFacade;

    @GET
    @Path("{programacionId}")
    @Produces({MediaType.APPLICATION_JSON})
    public Response find(@PathParam("programacionId") Long programacionId) {
        try {
            Programacion prog = programacionFacade.find(programacionId);
            return Response.ok(prog).build();
        } catch (Exception iae) {
            return Util.manageException(iae, ProgramacionREST.class);
        }
    }

    @GET
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    @ValidadorArea(value = "area.id")
    public Response findWithFilter(@BeanParam FilterQuery filterQuery) {
        try {
            boolean filtradoEmpresa = false;
            for (Filter filter : filterQuery.getFilterList()) {
                if (filter.getField().equals("listaInspeccion.empresa.id")) {
                    filtradoEmpresa = true;
                    break;
                }
            }
            if (!filtradoEmpresa) {
                Filter empFilt = new Filter();
                empFilt.setCriteria("eq");
                empFilt.setField("listaInspeccion.empresa.id");
                empFilt.setValue1(super.getEmpresaIdRequestContext().toString());
                filterQuery.getFilterList().add(empFilt);
            }
            long numRows = filterQuery.isCount() ? programacionFacade.countWithFilter(filterQuery) : -1;
            List list = programacionFacade.findWithFilter(filterQuery);
            
            FilterResponse filterResponse = new FilterResponse();
            filterResponse.setData(list);
            filterResponse.setCount(numRows);
            return Response.ok(filterResponse).build();
        } catch (Exception ex) {
            return Util.manageException(ex, ProgramacionREST.class);
        }
    }
    
    @GET
    @Path("/auditoria")
    @Secured(requiereEmpresaId = false, validarPermiso = true)
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response findAuditoriaWithFilter(@BeanParam FilterQuery filterQuery){
        try {
            boolean tieneFiltradoEmpresa = false;
            for (Filter filter : filterQuery.getFilterList()) {
                if ("listaInspeccion.empresa.id".equalsIgnoreCase(filter.getField())) {
                    tieneFiltradoEmpresa = true;
                    break;
                }
            }
                
            if (!tieneFiltradoEmpresa) {
                Filter empFilter = new Filter();
                empFilter.setCriteria("eq");
                empFilter.setField("listaInspeccion.empresa.id");
                empFilter.setValue1(super.getEmpresaIdRequestContext().toString());
                filterQuery.getFilterList().add(empFilter);
            }

            long numRows = filterQuery.isCount() ? programacionFacade.countWithFilter(filterQuery) : -1;
            List list = programacionFacade.findWithFilter(filterQuery);

            FilterResponse filterResponse = new FilterResponse();
            filterResponse.setData(list);
            filterResponse.setCount(numRows);
            return Response.ok(filterResponse).build();
        } catch (Exception e) {
            return Util.manageException(e, ProgramacionREST.class);
        }
    }

    @POST
    @Produces({MediaType.APPLICATION_JSON})
    public Response create(Programacion programacion) {
        try {
            programacion = programacionFacade.create(programacion);
            return Response.ok(programacion).build();
        } catch (Exception ex) {
            return Util.manageException(ex, ProgramacionREST.class);
        }
    }
    
    @POST
    @Path("/auditoria")
    @Secured(validarPermiso = true, requiereEmpresaId = false)
    @Produces({MediaType.APPLICATION_JSON})
    public Response createAuditoria(Programacion programacion){
        try {
            programacion = programacionFacade.createAuditoria(programacion);
            return Response.ok(programacion).build();
        } catch (Exception e) {
            return Util.manageException(e, ProgramacionREST.class);
        }
    }

    @PUT
    @Produces({MediaType.APPLICATION_JSON})
    public Response edit(Programacion programacion) {
        try {
            programacion = programacionFacade.modificar(programacion);
            return Response.ok(programacion).build();
        } catch (Exception ex) {
            return Util.manageException(ex, ProgramacionREST.class);
        }
    }
    
    @PUT
    @Path("/auditoria")
    @Secured(requiereEmpresaId = false, validarPermiso = true)
    @Produces({MediaType.APPLICATION_JSON})
    public Response editAuditoria(Programacion programacion){
        try {
            programacion = programacionFacade.modificarAuditoría(programacion);
            return Response.ok(programacion).build();
        } catch (Exception ex) {
            return Util.manageException(ex, ProgramacionREST.class);
        }
    }

    @DELETE
    @Path("{programacionId}")
    @Produces({MediaType.APPLICATION_JSON})
    public Response delete(@PathParam("programacionId") Long programacionId) {
        try {
            Programacion programacion = programacionFacade.find(programacionId);
            if (programacion == null) {
                throw new UserMessageException("Error de solicitud", "El id de la programación es inválido", TipoMensaje.error);
            }
            programacionFacade.remove(programacion);
            return Response.ok(programacion).build();
        } catch (Exception ex) {
            return Util.manageException(ex, ProgramacionREST.class);
        }
    }

}
