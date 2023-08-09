        /*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.sigess.restful.inp;

import co.sigess.entities.emp.Empresa;
import co.sigess.entities.inp.Bitacora;
import co.sigess.entities.inp.Inspeccion;
import co.sigess.entities.inp.NumeroEconomico;
import co.sigess.facade.inp.InspeccionFacade;
import co.sigess.restful.FilterQuery;
import co.sigess.restful.ServiceREST;
import co.sigess.restful.security.Compress;
import co.sigess.restful.security.Secured;
import co.sigess.util.Util;
import java.io.ByteArrayOutputStream;
import java.util.Date;
import java.util.List;
import javax.ejb.EJB;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 *
 * @author fmoreno
 */
@Secured
@Path("inspeccion")
public class InspeccionREST extends ServiceREST {

    @EJB
    private InspeccionFacade inspeccionFacade;

    public InspeccionREST() {
        super(InspeccionFacade.class);
    }

    @Compress
    @Override
    public Response findWithFilter(FilterQuery filterQuery) {
        return super.findWithFilter(filterQuery); //To change body of generated methods, choose Tools | Templates.
    }

    @GET
    @Path("{id}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response find(@PathParam("id") Long id) {
        try {
            Inspeccion insp = inspeccionFacade.find(id);
            return Response.ok(insp).build();
        } catch (Exception ex) {
            return Util.manageException(ex, InspeccionREST.class);
        }
    }

    @GET
    @Path("consolidado")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_OCTET_STREAM})
    public Response consultarConsolidado(
            @QueryParam("desde") Date desde,
            @QueryParam("hasta") Date hasta,
            @QueryParam("listaId") Integer listaId,
            @QueryParam("listaVersion") Integer listaVersion
    ) {
        if (desde == null) {
            throw new IllegalArgumentException("Debe especificar la fecha de inicio de rango de consulta");
        }
        if (hasta == null) {
            throw new IllegalArgumentException("Debe especificar la fecha de fin de rango de consulta");
        }
        if (listaId == null) {
            throw new IllegalArgumentException("Debe especificar el id de la lista de inspeccion");
        }
        if (listaVersion == null) {
            throw new IllegalArgumentException("Debe especificar la version de la lista de inspeccion");
        }
        try {
            ByteArrayOutputStream out = inspeccionFacade.consultarConsolidado(super.getEmpresaIdRequestContext(), desde, hasta, listaId, listaVersion);
            return Response.ok(out.toByteArray(), MediaType.APPLICATION_OCTET_STREAM_TYPE).build();
        } catch (Exception ex) {
            return Util.manageException(ex, InspeccionREST.class);
        }
    }

    @POST
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response create(Inspeccion inspeccion) {
        try {
            inspeccion.setEmpresa(new Empresa(super.getEmpresaIdRequestContext()));
            inspeccion.setUsuarioRegistra(super.getUsuarioRequestContext());
            inspeccion = inspeccionFacade.create(inspeccion);
            return Response.ok(inspeccion).build();
        } catch (Exception ex) {
            return Util.manageException(ex, InspeccionREST.class);
        }
    }

    @POST
    @Path("inspeccionAliado")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    @Secured(requiereEmpresaId = false)
    public Response createInspeccionAliado(Inspeccion inspeccion) {
        try {
            inspeccion.setEmpresa(new Empresa(super.getEmpresaIdRequestContext()));
            inspeccion.setUsuarioRegistra(super.getUsuarioRequestContext());
            inspeccion = inspeccionFacade.createInspeccionAliado(inspeccion);
            return Response.ok(inspeccion).build();
        } catch (Exception e) {
            return Util.manageException(e, InspeccionREST.class);
        }
    }


    @PUT
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response edit(Inspeccion inspeccion) {
        try {
            inspeccion.setUsuarioModifica(super.getUsuarioRequestContext());
            inspeccion = inspeccionFacade.edit(inspeccion);
            return Response.ok(inspeccion).build();
        } catch (Exception ex) {
            return Util.manageException(ex, InspeccionREST.class);
        }
    }
    
    @PUT
    @Path("inspeccionAliado")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    @Secured(requiereEmpresaId = false)
    public Response editInspeccionAliado(Inspeccion inspeccion){
        try {
            inspeccion.setUsuarioModifica(super.getUsuarioRequestContext());
            inspeccion = inspeccionFacade.editInspeccionAliado(inspeccion);
            return Response.ok(inspeccion).build();
        } catch (Exception e) {
            return Util.manageException(e, InspeccionREST.class);
        }
    }

    @GET
    @Path("numeroEconomico")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response findNumeroEconomico() {
            
            try {

            List<NumeroEconomico> insp = inspeccionFacade.getAllNumeroEconomico();
            return Response.ok(insp).build();
        } catch (Exception ex) {
            return Util.manageException(ex, InspeccionREST.class);
        }
    }

    @GET
    @Path("bitacora/{numeroEconomicoId}/{inspeccionId}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response findBitacora(@PathParam("numeroEconomicoId") String numeroEconomicoId,@PathParam("inspeccionId") String inspeccionId) {
            
            try {
            List<Bitacora> insp = inspeccionFacade.getBitacora(numeroEconomicoId,inspeccionId);
            return Response.ok(insp).build();
        } catch (Exception ex) {
            return Util.manageException(ex, InspeccionREST.class);
        }
    }

    @GET
    @Path("numeroEconomico{numeroEconomico}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response findNumeroEconomico2(@PathParam("numeroEconomico") String numeroEconomico) {
            
            try {
            List<NumeroEconomico> insp = inspeccionFacade.getNumeroEconomicoByNumeroEconomico(numeroEconomico);
            return Response.ok(insp).build();
        } catch (Exception ex) {
            return Util.manageException(ex, InspeccionREST.class);
        }
    }


    @POST
    @Path("bitacora")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response create2(Bitacora bitacora) {
        try {
            Bitacora bita=inspeccionFacade.crearBitacora(bitacora);
            return Response.ok(bita).build();
        } catch (Exception ex) {
            return Util.manageException(ex, InspeccionREST.class);
        }
    }
    
    
}
