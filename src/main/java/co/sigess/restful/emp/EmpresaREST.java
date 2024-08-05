/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.sigess.restful.emp;

import co.sigess.entities.com.TipoMensaje;
import co.sigess.entities.emp.ActividadesContratadas;
import co.sigess.entities.emp.AliadoInformacion;
import co.sigess.entities.emp.Localidades;
import co.sigess.entities.emp.Empresa;
import co.sigess.entities.emp.Sst;
import co.sigess.entities.emp.AliadoInformacion;
import co.sigess.entities.emp.AliadosDivisiones;
import co.sigess.entities.emp.Area;
import co.sigess.entities.emp.Subcontratista;
import co.sigess.exceptions.UserMessageException;
import co.sigess.facade.emp.ActividadesContratadasFacade;
import co.sigess.facade.emp.EmpresaFacade;
import co.sigess.facade.emp.SstFacade;
import co.sigess.facade.emp.AliadoInformacionFacade;
import co.sigess.facade.emp.LocalidadesFacade;
import co.sigess.facade.emp.AreaFacade;
import co.sigess.facade.emp.SubcontratistaFacade;
import co.sigess.facade.emp.UsuarioFacade;
import co.sigess.restful.CriteriaFilter;
import co.sigess.restful.Filter;
import co.sigess.restful.FilterQuery;
import co.sigess.restful.FilterResponse;
import co.sigess.restful.ServiceREST;
import co.sigess.restful.rai.ReporteREST;
import co.sigess.restful.security.Secured;
import co.sigess.util.Util;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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

    @EJB
    private ActividadesContratadasFacade ActividadesContratadasFacade;
    
    @EJB
    private LocalidadesFacade LocalidadesFacade;
    
    @EJB
    private SubcontratistaFacade subcontratistaFacade;
    
    @EJB
    private UsuarioFacade usuarioFacade;
    
    @EJB
    private AreaFacade areaFacade;
    
    public EmpresaREST() {

    }

    @GET
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    @Override
    public Response findWithFilter(@BeanParam FilterQuery filterQuery) {
        try {
            boolean filtradoEmpresa = false;
            boolean isFindAliado = false;
            boolean isFindEmpresa = false;
            for (Filter filter : filterQuery.getFilterList()) {
                if (filter.getField().equals("usuarioEmpresaList.usuario.id")) {
                    filtradoEmpresa = true;
                }else if(filter.getField().compareToIgnoreCase("idempresaaliada") == 0){
                    isFindAliado = true;
                }else if(filter.getField().compareToIgnoreCase("id") == 0){
                    isFindEmpresa = true;
                }
            }

            if(isFindAliado){
                // System.out.println("isfindaliado");
                long numRows = filterQuery.isCount() ? empresaFacade.countWithFilter(filterQuery) : -1;
                List list = empresaFacade.findWithFilter(filterQuery);

                FilterResponse filterResponse = new FilterResponse();
                filterResponse.setData(list);
                filterResponse.setCount(numRows);
                // System.out.println("return >>>");
                return Response.ok(filterResponse).build();
            }
            
            if (!filtradoEmpresa && !isFindEmpresa) {
                // System.out.println("add filtradoempresa >>>");
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
            // System.out.println("return default: "+isFindAliado+", "+filtradoEmpresa);
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
    
    
    
    @POST
    @Secured(validarPermiso = false)
    @Path("createEmpresaAliada")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response createEmpresaAliada(Empresa empresa){
        try{
            if(usuarioFacade.findByEmail(empresa.getEmail()) != null){
                throw new UserMessageException("Error", "El correo del contratista se encuentra asociado a un usuario del sistema.", TipoMensaje.error);
            }
            
            List<Empresa> empresas = empresaFacade.findEmpresaByNit(empresa.getNit());
            if(empresas != null && empresas.size() > 0) {
                throw new UserMessageException("Error", "El NIT digitado se encuentra asociado a un contratista en el sistema.", TipoMensaje.error);
            }
            
            empresa = empresaFacade.adicionar(empresa, super.getUsuarioRequestContext().getId());
            return Response.ok(empresa).build();
        }catch(Exception ex){
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
    
    @DELETE
    @Secured(validarPermiso = false)
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    @Path("deleteMiembroSst/{miembroId}")
    public Response deleteMiembroSst(@PathParam("miembroId") Integer miembroId){
        try{
            return Response.ok(SstFacade.deleteById(miembroId)).build();
        }catch(Exception ex){
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
    
    @GET
    @Secured(requiereEmpresaId = false)
    @Path("getAliadoDivision/{empresaId}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response getAliadosDivision(@PathParam("empresaId") Integer empresaId){
        List<AliadosDivisiones> aliadosDivisionesList = AliadoInformacionFacade.getAliadosDivision(empresaId);
        return Response.ok(aliadosDivisionesList).build();
    }
    
    @GET
    @Secured(requiereEmpresaId = false)
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    @Path("getActividadesContratadas/{EmpresaId}")
    public Response findByActividadesContratadasId(@PathParam("EmpresaId") Integer empresaId) {
        List<ActividadesContratadas> act = ActividadesContratadasFacade.findByActividadContratadaId(empresaId);
        return Response.ok(act).build();
    }
    
    @GET
    @Secured(requiereEmpresaId = false)
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    @Path("getActividadesContratadas")
    public Response findByLocalidadId(@PathParam("EmpresaId") Integer empresaId) {
        List<Localidades> act = LocalidadesFacade.findByAllLocalidades();
        return Response.ok(act).build();
    }
    
    @GET
    @Secured(validarPermiso = false)
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    @Path("getAreas")
    public Response findByDivisionId() {
        try{
              List<Area> act = areaFacade.findByAreaId();
              return Response.ok(act).build();
        }catch (Exception e) {
            return Util.manageException(e, EmpresaREST.class);
        }
        
    }
    
    @GET
    @Secured(requiereEmpresaId = false)
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    @Path("getLocalidades/{DivisionId}")
    public Response findLocByDivisonId(@PathParam("DivisionId") Integer divisionId) {
        List<Localidades> loc = LocalidadesFacade.findByDivision(divisionId);
        return Response.ok(loc).build();
    }

    
    @GET
    @Secured(validarPermiso = false)
    @Path("findSLoc")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getLocalidadById() {
        List<Localidades> localidad = LocalidadesFacade.findByAllLocalidades();
        if (localidad.isEmpty()) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.ok(localidad).build();
    }
    
    @GET
    @Path("filterLocalidades")
    @Secured(requiereEmpresaId = false)
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response localidadesFirmWithFilter(@BeanParam FilterQuery filterQuery){
        try {
            if(filterQuery == null){
                filterQuery = new FilterQuery();
            }
            long numRows = filterQuery.isCount() ? LocalidadesFacade.countWithFilter(filterQuery) : -1;
            List list = LocalidadesFacade.findWithFilter(filterQuery);
            
            FilterResponse filterResponse = new FilterResponse();
            filterResponse.setData(list);
            filterResponse.setCount(numRows);
            return Response.ok(filterResponse).build();
        } catch (Exception e) {
            return Util.manageException(e, EmpresaREST.class);
        }
    }
    
    @PUT
    @Path("updateLocalidades")
    @Secured(validarPermiso = false)
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response updateLocalidad(Localidades localidad) {
        try {
            Localidades localidadbd = LocalidadesFacade.find(localidad.getId());
            if(!localidad.getIdDocConsolidado().equals("null")){
                localidadbd.setIdDocConsolidado(localidad.getIdDocConsolidado());
                localidadbd.setUsuarioConsolidado(localidad.getUsuarioConsolidado());
                localidadbd.setDescargaConsolidado(localidad.getDescargaConsolidado());
                localidadbd.setFechaConsolidadoStart(localidad.getFechaConsolidadoStart());
                localidadbd.setFechaConsolidado(localidad.getFechaConsolidado());
            }
            //plantabd.setIdDocConsolidado(plantas.getIdDocConsolidado());
            if(!localidad.getIdDocHistorico().equals("null")){
                localidadbd.setIdDocHistorico(localidad.getIdDocHistorico());
                localidadbd.setUsuarioHistorico(localidad.getUsuarioHistorico());
                localidadbd.setDescargaHistorico(localidad.getDescargaHistorico());
                localidadbd.setFechaHistoricoStart(localidad.getFechaHistoricoStart());
                localidadbd.setFechaHistorico(localidad.getFechaHistorico());
            }
            //plantabd.setIdDocHistorico(plantas.getIdDocHistorico());
            //if(!plantas.getIdDocHistorico().isEmpty()){plantabd.setIdDocHistorico(plantas.getIdDocHistorico());}
            
            localidadbd = LocalidadesFacade.edit(localidadbd);

            return Response.ok(localidadbd).build();
        } catch (Exception ex) {
            return Util.manageException(ex, PlantasREST.class);
        }
    }
    
    @POST
    @Secured(requiereEmpresaId = false)
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    @Path("saveSubcontratista")
    public Response postSubcontratista(Subcontratista subcontratista){
        try {
            subcontratista = subcontratistaFacade.crearSubcontratista(subcontratista);
            return Response.ok(subcontratista).build();
        } catch (Exception ex) {
            return Util.manageException(ex, EmpresaREST.class);
        }
    }
    
    @PUT
    @Secured(requiereEmpresaId = false)
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    @Path("updateSubcontratista")
    public Response updateSubcontratista(Subcontratista subcontratista){
        try {
            subcontratista = subcontratistaFacade.actualizarSubcontratista(subcontratista);
            return Response.ok(subcontratista).build();
        } catch (Exception ex) {
            return Util.manageException(ex, EmpresaREST.class);
        }
    }
    
    
    @GET
    @Secured(requiereEmpresaId = false)
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    @Path("getSubcontratistas/{aliadoId}")
    public Response getSubcontratistas(@PathParam("aliadoId") Integer aliadoId) {
        List<Subcontratista> listaSubcontratista = subcontratistaFacade.findByAliadoCreador(aliadoId);
        return Response.ok(listaSubcontratista).build();
    }

    @GET
    @Secured(requiereEmpresaId = false)
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    @Path("temporales/{empresaId}")
    public Response getTemporales(@PathParam("empresaId") Integer empresaId){
        try{
            List<AliadoInformacion> listaTemporales = AliadoInformacionFacade.findTemporalesByEmpresaId(empresaId);
            // System.out.println(listaTemporales.size());
            // Response re = Response.ok(listaTemporales).build();
            // System.out.println(re.toString() + re.getMetadata().toString());
            return Response.ok(listaTemporales).build();
        } catch(Exception e){
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    @GET
    @Secured(validarPermiso = false)
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    @Path("empresaId/{id}")
    public Response findBySelected(@PathParam("id") Integer empresaId) {
        try{
            Empresa  empresa = empresaFacade.findEmpresaById(empresaId);
            return Response.ok(empresa).build();
        } catch(Exception e){
         return Response.status(Response.Status.NOT_FOUND).build();
        }
        
    }
    
}
