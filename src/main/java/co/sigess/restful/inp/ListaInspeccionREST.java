/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.sigess.restful.inp;

import co.sigess.entities.emp.Empresa;
import co.sigess.entities.inp.ListaInspeccion;
import co.sigess.entities.inp.ListaInspeccionPK;
import co.sigess.facade.inp.ListaInspeccionFacade;
import co.sigess.restful.FilterQuery;
import co.sigess.restful.ServiceREST;
import co.sigess.restful.sec.AnalisisDesviacionREST;
import co.sigess.restful.security.Compress;
import co.sigess.restful.security.Secured;
import co.sigess.util.Util;
import java.util.HashMap;
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
    
}
