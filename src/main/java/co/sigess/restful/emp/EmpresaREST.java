/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.sigess.restful.emp;

import co.sigess.entities.emp.AliadoInformacion;
import co.sigess.entities.emp.Empresa;
import co.sigess.entities.emp.Sst;
import co.sigess.entities.emp.AliadoInformacion;
import co.sigess.facade.emp.EmpresaFacade;
import co.sigess.facade.emp.SstFacade;
import co.sigess.facade.emp.AliadoInformacionFacade;
import co.sigess.restful.CriteriaFilter;
import co.sigess.restful.Filter;
import co.sigess.restful.FilterQuery;
import co.sigess.restful.FilterResponse;
import co.sigess.restful.ServiceREST;
import co.sigess.restful.rai.ReporteREST;
import co.sigess.restful.security.Secured;
import co.sigess.util.Util;
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
@Path("empresa")
public class EmpresaREST extends ServiceREST {

    @EJB
    private EmpresaFacade empresaFacade;
    
    @EJB
    private SstFacade SstFacade;
    
    @EJB
    private AliadoInformacionFacade AliadoInformacionFacade;

    public EmpresaREST() {

    }

    @GET
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    @Override
    public Response findWithFilter(@BeanParam FilterQuery filterQuery) {
        try {
            boolean filtradoEmpresa = false;
            for (Filter filter : filterQuery.getFilterList()) {
                if (filter.getField().equals("usuarioEmpresaList.usuario.id")) {
                    filtradoEmpresa = true;
                }
            }

            if (!filtradoEmpresa) {
                Filter empFilt = new Filter();
                empFilt.setCriteriaEnum(CriteriaFilter.EQUALS);
                empFilt.setField("usuarioEmpresaList.usuario.id");
                empFilt.setValue1(String.valueOf(getUsuarioRequestContext().getId()));
                filterQuery.getFilterList().add(empFilt);
            }

            long numRows = filterQuery.isCount() ? empresaFacade.countWithFilter(filterQuery) : -1;
            List list = empresaFacade.findWithFilter(filterQuery);

            FilterResponse filterResponse = new FilterResponse();
            filterResponse.setData(list);
            filterResponse.setCount(numRows);
            return Response.ok(filterResponse).build();
        } catch (Exception ex) {
            return Util.manageException(ex, ReporteREST.class);
        }
    }

    @POST
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response create(Empresa empresa) {
        try {
            empresa = empresaFacade.adicionar(empresa, super.getUsuarioRequestContext().getId());
            return Response.ok(empresa).build();
        } catch (Exception ex) {
            return Util.manageException(ex, EmpresaREST.class);
        }
    }

    @PUT
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response edit(Empresa empresa) {
        try {
            empresa = empresaFacade.edit(empresa);
            System.out.println("ok empresa");
            return Response.ok(empresa).build();
        } catch (Exception ex) {
            return Util.manageException(ex, EmpresaREST.class);
        }
    }

    @GET
    @Path("contratistas/{empresaId}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response obtenerContratistas(@PathParam("empresaId") Integer empresaId) {
        try {
            List<Empresa> empresasList = empresaFacade.find(empresaId).getEmpresasContratistasList();
            return Response.ok(empresasList).build();
        } catch (Exception ex) {
            return Util.manageException(ex, EmpresaREST.class);
        }
    }

    @PUT
    @Path("contratistas")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response vincularContratista(Empresa contratista) {
        try {
            boolean vinculado = empresaFacade.vincularContratista(super.getEmpresaIdRequestContext(), contratista);
            return Response.ok(vinculado).build();
        } catch (Exception ex) {
            return Util.manageException(ex, EmpresaREST.class);
        }
    }

    @Secured(requiereEmpresaId = false)
    @GET
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    @Path("usuario/{usuarioId}")
    public Response findByUsuario(@PathParam("usuarioId") Integer usuarioId) {
        List<Empresa> empresa = empresaFacade.findByUsuario(usuarioId);
        return Response.ok(empresa).build();
    }

    @GET
    @Secured(validarPermiso = false)
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    @Path("selected")
    public Response findBySelected() {
         
      Empresa  empresa = empresaFacade.find(super.getEmpresaIdRequestContext());
        return Response.ok(empresa).build();
    }

    @POST
    @Secured(validarPermiso = false)
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    @Path("createEquipoSST")
    public Response createEquipoSST(Sst sst) {
        try {
            sst = SstFacade.adicionarSST(sst);
            return Response.ok(sst).build();
        } catch (Exception ex) {
            return Util.manageException(ex, EmpresaREST.class);
        }
    }
    
    @PUT
    @Secured(validarPermiso = false)
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    @Path("updateEquipoSST")
    public Response updateEquipoSST(Sst sst) {
        try {
            sst = SstFacade.updateSST(sst);
            return Response.ok(sst).build();
        } catch (Exception ex) {
            return Util.manageException(ex, EmpresaREST.class);
        }
    }
  
    
    @GET
    @Secured(requiereEmpresaId = false)
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    @Path("getEquipoSST/{aliadoId}")
    public Response findByAliadoId(@PathParam("aliadoId") Integer aliadoId) {
        List<Sst> sst = SstFacade.findByAliadoId(aliadoId);
        return Response.ok(sst).build();
    }

    @DELETE
    @Secured(validarPermiso = false)
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    @Path("deleteEquipoSST")
    public Response deleteEquipoSST(Sst sst) {
        try {
            SstFacade.deleteByAliadoId(sst);
            return Response.ok().build();
        } catch (Exception ex) {
            return Util.manageException(ex, EmpresaREST.class);
        }
    }
    
    @PUT
    @Secured(validarPermiso = false)
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    @Path("saveAliadoInformacion")
    public Response saveAliadoInformacion(AliadoInformacion aliadoInformacion) {
        try {
            
            List<AliadoInformacion> data = AliadoInformacionFacade.findByAliadoId(aliadoInformacion.getId_empresa());

            if(data.size()>0){
                aliadoInformacion = AliadoInformacionFacade.updateAliadoInformacion(aliadoInformacion);
                return Response.ok(aliadoInformacion).build();
            }
            else{
                aliadoInformacion = AliadoInformacionFacade.adicionarAliadoInformacion(aliadoInformacion);
                return Response.ok(aliadoInformacion).build();
            }

        } catch (Exception ex) {
            return Util.manageException(ex, EmpresaREST.class);
        }
    }
    
    @GET
    @Secured(requiereEmpresaId = false)
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    @Path("getAliadoInformacion/{aliadoId}")
    public Response findByInformacionAliadoId(@PathParam("aliadoId") Integer aliadoId) {
        List<AliadoInformacion> aliadoInformacion = AliadoInformacionFacade.findByAliadoId(aliadoId);
        return Response.ok(aliadoInformacion).build();
    }

}
