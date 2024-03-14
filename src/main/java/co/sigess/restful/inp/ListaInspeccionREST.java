/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.sigess.restful.inp;

import co.sigess.entities.emp.Empresa;
import co.sigess.entities.emp.UsuarioEmpresa;
import co.sigess.entities.inp.ListaInspeccion;
import co.sigess.entities.inp.ListaInspeccionPK;
import co.sigess.facade.emp.UsuarioEmpresaFacade;
import co.sigess.facade.inp.ListaInspeccionFacade;
import co.sigess.restful.FilterQuery;
import co.sigess.restful.FilterResponse;
import co.sigess.restful.ServiceREST;
import co.sigess.restful.ipr.ViewMatrizPeligrosREST;
import co.sigess.restful.sec.AnalisisDesviacionREST;
import co.sigess.restful.security.Compress;
import co.sigess.restful.security.Secured;
import co.sigess.util.Util;
import java.lang.reflect.Method;
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
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.PathSegment;
import javax.ws.rs.core.Response;

/**
 *
 * @author fmoreno
 */
@Secured
@Path("listaInspeccion")
public class ListaInspeccionREST extends ServiceREST {

    @EJB
    private ListaInspeccionFacade listaInspeccionFacade;
    
    @EJB
    private UsuarioEmpresaFacade usuarioEmpresaFacade;

    public ListaInspeccionREST() {
        super(ListaInspeccionFacade.class);
    }

    @Override
    @Compress
    public Response findWithFilter(FilterQuery filterQuery) {
        return super.findWithFilter(filterQuery);
    }
    
    private ListaInspeccionPK getPrimaryKey(PathSegment pathSegment) {
        /*
         * pathSemgent represents a URI path segment and any associated matrix parameters.
         * URI path part is supposed to be in form of 'somePath;id=idValue;version=versionValue'.
         * Here 'somePath' is a result of getPath() method invocation and
         * it is ignored in the following code.
         * Matrix parameters are used as field names to build a primary key instance.
         */
        ListaInspeccionPK key = new ListaInspeccionPK();
        javax.ws.rs.core.MultivaluedMap<String, String> map = pathSegment.getMatrixParameters();
        java.util.List<String> id = map.get("id");
        if (id != null && !id.isEmpty()) {
            key.setId(Integer.parseInt(id.get(0)));
        }
        java.util.List<String> version = map.get("version");
        if (version != null && !version.isEmpty()) {
            key.setVersion(Short.parseShort(version.get(0)));
        }
        return key;
    }

//    @GET
//    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
//    public Response findWithFilter(@BeanParam FilterQuery filterQuery) {
//        try {
//            boolean filtradoEmpresa = false;
//            for (Filter filter : filterQuery.getFilterList()) {
//                if (filter.getField().equals("empresa.id")) {
//                    filtradoEmpresa = true;
//                    break;
//                }
//            }
//            if (!filtradoEmpresa) {
//                Filter empFilt = new Filter();
//                empFilt.setCriteria("eq");
//                empFilt.setField("empresa.id");
//                empFilt.setValue1(super.getEmpresaIdRequestContext().toString());
//                filterQuery.getFilterList().add(empFilt);
//            }
//            List<ListaInspeccion> list = listaInspeccionFacade.findWithFilter(filterQuery);
//            return Response.ok(list).build();
//        } catch (Exception ex) {
//            return Util.manageException(ex, ListaInspeccionREST.class);
//        }
//    }

    @GET
    @Path("{id}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response find(@PathParam("id") PathSegment id) {
        try {
            ListaInspeccionPK key = getPrimaryKey(id);
            ListaInspeccion listInp = listaInspeccionFacade.find(key);
            return Response.ok(listInp).build();
        } catch (Exception ex) {
            return Util.manageException(ex, ListaInspeccionREST.class);
        }
    }

    @POST
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response create(ListaInspeccion listaInspeccion) {
        try {
            listaInspeccion.setEmpresa(new Empresa(super.getEmpresaIdRequestContext()));
            listaInspeccion = listaInspeccionFacade.create(listaInspeccion);
            return Response.ok(listaInspeccion).build();
        } catch (Exception ex) {
            return Util.manageException(ex, ListaInspeccionREST.class);
        }
    }

    @PUT
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response edit(ListaInspeccion listaInspeccion, @QueryParam("actualizarVersion") Boolean actualizarVersion ,@QueryParam("putProfile") Boolean putProfile) {
        try {
            listaInspeccion.setEmpresa(new Empresa(super.getEmpresaIdRequestContext()));  
            int res;
            //Evitamos que PutProfile sea null
            if(putProfile == null) putProfile = false;       
            if (putProfile == true){

                res = listaInspeccionFacade.editProfile(listaInspeccion);
                return Response.ok(res).build();
            }
            else if
                    (actualizarVersion == null || actualizarVersion.equals(Boolean.FALSE)) {
                    if(listaInspeccion.getEstado().equals("inactivo")){
                        ListaInspeccion listaInspeccionDB = listaInspeccionFacade.find(listaInspeccion.getListaInspeccionPK());
                        listaInspeccion.setNumeroPreguntas(listaInspeccionDB.getNumeroPreguntas());
                        listaInspeccion = listaInspeccionFacade.editarForzado(listaInspeccion);
                    }else{
                        listaInspeccion = listaInspeccionFacade.edit(listaInspeccion);
                    }
                    
            } 
            else {
                listaInspeccion = listaInspeccionFacade.actualizarVersion(listaInspeccion);
                
            }
            return Response.ok(listaInspeccion).build();
        } catch (Exception ex) {
            return Util.manageException(ex, ListaInspeccionREST.class);
        }
    }
    
    @DELETE
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response delete(@QueryParam("id") Integer id, @QueryParam("version") Integer version){
        try {
            ListaInspeccion listaInspeccionDB = listaInspeccionFacade.findByIdAndVersion(id, version);
            listaInspeccionDB.setFkPerfilId(null);
            listaInspeccionFacade.edit(listaInspeccionDB);
            return Response.ok(true).build();
        } catch (Exception e) {
            return Util.manageException(e, ListaInspeccionREST.class);
        }
    }

    @POST
    @Path("/profile")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response editProfile(ListaInspeccion listaInspeccion, @QueryParam("actualizarVersion") Boolean actualizarVersion) {
        System.out.println("Aqui llego");
        try {
            listaInspeccion.setEmpresa(new Empresa(super.getEmpresaIdRequestContext()));
            if (actualizarVersion == null || actualizarVersion.equals(Boolean.FALSE)) {
              // listaInspeccion = listaInspeccionFacade.editProfile(listaInspeccion);
            } else {
                listaInspeccion = listaInspeccionFacade.actualizarVersion(listaInspeccion);
            }
            return Response.ok(listaInspeccion).build();
        } catch (Exception ex) {
            return Util.manageException(ex, ListaInspeccionREST.class);
        }
    }
   
    @GET
    @Secured(validarPermiso = false)
    @Path("images/{lista_id}/{version_id}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response findImages(@PathParam("lista_id") Integer lista_id, @PathParam("version_id") Integer version_id) {
        try {
             HashMap<String, List<String>> file = listaInspeccionFacade.getImages(lista_id, version_id);
            return Response.ok(file).build();
        } catch (Exception ex) {
            return Util.manageException(ex, ListaInspeccionREST.class);
        }
    }
    
    
    @POST
    @Path("filterListInspeccion")
    @Secured(requiereEmpresaId = false)
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response getFilterListInspeccion(FilterQuery filterQuery){
        try {
            if(filterQuery == null){
                filterQuery = new FilterQuery();
            }
            long numRows = filterQuery.isCount() ? listaInspeccionFacade.countWithFilter(filterQuery) : -1;
//            List<ListaInspeccion> listInpeccion = listaInspeccionFacade.findWithFilter(filterQuery);
            List<ListaInspeccion> listInpeccion = listaInspeccionFacade.findAll();
            List<UsuarioEmpresa> perfiles = (List<UsuarioEmpresa>) usuarioEmpresaFacade.findUserId(super.getUsuarioRequestContext().getId());
            
            List listOut = new ArrayList();
            System.out.println(listInpeccion.getClass().getSimpleName());
            Class<?> clase = ListaInspeccion.class;
            Method metodo = clase.getMethod("getFkPerfilId");

            for (Object item : listInpeccion) {
                System.out.println(item.getClass().getSimpleName());
                System.out.println(item);
                

            }
            if(!listInpeccion.isEmpty()){
                for (Object listIns : listInpeccion) {
                    if (listIns instanceof HashMap) {
                        Map mapItem = (Map) listIns;
                        Object list = mapItem.get("fkPerfilId");
                        System.out.println(list);
                        if(list != null){
                            String str = (String) list;
                            str = str.substring(1, str.length() - 1);
                            String[] stringNumbers = str.split(",");
                            if(stringNumbers.length>0){
                                for (String perfilUser : stringNumbers) {
                                    for (UsuarioEmpresa perfilList : perfiles) {
                                        if( perfilUser.equals(perfilList.getPerfil().getId().toString())){
                                            if(!listOut.stream().anyMatch(o -> o.equals(listIns))){
                                                listOut.add(listIns);
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    } 
                }
            }
//            if(!listInpeccion.isEmpty()){
//                for (ListaInspeccion listIns : listInpeccion) {
//                    System.out.println(listIns.getFkPerfilId());
//                    String str = listIns.getFkPerfilId();
//                    str = str.substring(1, str.length() - 1);
//                    String[] stringNumbers = str.split(",");
//                    if(stringNumbers.length>0){
//                        for (String perfilUser : stringNumbers) {
//                            for (UsuarioEmpresa perfilList : perfiles) {
//                                if( perfilUser.equals(perfilList.getPerfil().getId().toString())){
//                                    if(!listOut.stream().anyMatch(o -> o.equals(listIns))){
//                                        listOut.add(listIns);
//                                    }
//                                }
//                            }
//                        }
//                    }
//                } 
//            }
//            long numRows = !listOut.isEmpty()? listOut.size():-1;
            
            FilterResponse filterResponse = new FilterResponse();
            filterResponse.setData(listOut);
            filterResponse.setCount(numRows);
            return Response.ok(filterResponse).build();
        } catch (Exception e) {
            return Util.manageException(e, ListaInspeccionREST.class);
        }
    }
}
