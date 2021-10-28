/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.sigess.restful.ado;

import co.sigess.entities.ado.Directorio;
import co.sigess.entities.ado.Documento;
import co.sigess.entities.ado.Modulo;
import co.sigess.entities.emp.Empresa;
import co.sigess.entities.scm.ScmLogs;
import co.sigess.facade.ado.DirectorioFacade;
import co.sigess.facade.ado.DocumentoFacade;
import co.sigess.facade.scm.ScmLogsFacade;
import co.sigess.restful.CriteriaFilter;
import co.sigess.restful.Filter;
import co.sigess.restful.FilterQuery;
import co.sigess.restful.FilterResponse;
import co.sigess.restful.ServiceREST;
import co.sigess.restful.security.Secured;
import co.sigess.util.FileUtil;
import co.sigess.util.Util;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.ejb.EJB;
import javax.ws.rs.BeanParam;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
import org.glassfish.jersey.media.multipart.FormDataParam;

/**
 *
 * @author fmoreno
 */
@Secured
@Path("directorio")
public class DirectorioREST extends ServiceREST {

    @EJB
    private ScmLogsFacade scmLogsFacade;
    @EJB
    private DirectorioFacade directorioFacade;

    @EJB
    private DocumentoFacade documentoFacade;

    public DirectorioREST() {
        super(DirectorioFacade.class);
    }

    @POST
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response create(Directorio directorio) {
        try {
            System.out.print(directorio.getCaseId());
            directorio.setEmpresa(new Empresa(super.getEmpresaIdRequestContext()));
            directorio.setUsuario(super.getUsuarioRequestContext());
            directorio = directorioFacade.create(directorio);
            return Response.ok(directorio).build();
        } catch (Exception ex) {
            return Util.manageException(ex, DirectorioREST.class);
        }
    }

    /**
     * Actualiza el directorio recibido como parámetro
     *
     * @param directorio
     * @return
     * @throws Exception
     */
    @PUT
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response edit(Directorio directorio) throws Exception {
        try {
            directorioFacade.edit(directorio);
            return Response.ok(directorio).build();
        } catch (Exception ex) {
            return Util.manageException(ex, DirectorioREST.class);
        }
    }
    
    @Secured(validarPermiso = false)
    @POST
    @Path("uploadv2")
    @Consumes({MediaType.MULTIPART_FORM_DATA})
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response uploadFile(
            @FormDataParam("file") InputStream fileInputStream,
            @FormDataParam("file") FormDataContentDisposition fileMetaData
    ) throws Exception {
        try {

            if (fileMetaData != null) {
                if (fileMetaData.getFileName() == null) {
                    throw new IllegalArgumentException("No se ha especificado un nombre para el archivo a guardar");
                }
                String fileName = fileMetaData.getFileName();
                Map<String, Object> map = FileUtil.saveInVirtualFS(fileInputStream);
                String relativePath = (String) map.get(FileUtil.RELATIVE_PATH);

                ObjectMapper mapper = new ObjectMapper();
                String json = mapper.writeValueAsString(relativePath);

                return Response.ok(json).build();
            }
        } catch (Exception ex) {
            return Util.manageException(ex, DirectorioREST.class);
        }
        return null;

    }

    @POST
    @Path("upload")
    @Consumes({MediaType.MULTIPART_FORM_DATA})
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response uploadFile(
            @FormDataParam("file") InputStream fileInputStream,
            @FormDataParam("file") FormDataContentDisposition fileMetaData,
            @FormDataParam("dpId") Long directorioPadreId,
            @FormDataParam("mod") String modulo,
            @FormDataParam("modParam") String modParam,
            @FormDataParam("docMetaData") String docMetaData,
            @FormDataParam("caseId") Long caseId
    ) throws Exception {
        try {
            directorioFacade.validarParametrosUpload(modulo, modParam);
            Directorio dir = null;
            if (fileMetaData != null) {
                if (fileMetaData.getFileName() == null) {
                    throw new IllegalArgumentException("No se ha especificado un nombre para el archivo a guardar");
                }
                String fileName = fileMetaData.getFileName();
                Map<String, Object> map = FileUtil.saveInPathFS(fileInputStream);
                String relativePath = (String) map.get(FileUtil.RELATIVE_PATH);
                dir = new Directorio();
                dir.setEsDocumento(true);
                dir.setNombre(fileName);
                dir.setCaseId(caseId);

                dir.setEmpresa(new Empresa(super.getEmpresaIdRequestContext()));
                dir.setUsuario(super.getUsuarioRequestContext());
                dir.setDocumento(new Documento());
                dir.getDocumento().setRuta(relativePath);
                dir.getDocumento().setNombre(fileName);
                dir.getDocumento().setTamanio((long) map.get(FileUtil.FILE_SIZE));
                dir.getDocumento().setModulo(Modulo.valueOf(modulo));
                if (docMetaData != null) {
                    Documento docObj = Util.fromJson(docMetaData, Documento.class);
                    dir.getDocumento().setDescripcion(docObj.getDescripcion());
                }
                if (directorioPadreId != null) {
                    dir.setDirectorioPadre(new Directorio(directorioPadreId));
                }
                directorioFacade.create(dir, modParam);
            } else {
                directorioFacade.eliminarDocumentos(modulo, modParam);
            }
            ObjectMapper mapper = new ObjectMapper();
            String json = mapper.writeValueAsString(dir);
            if (caseId != null) {
                this.logScm("Guardado de ausentismo", json, dir.getId().toString(), directorioFacade.getClass().toString());
            }
            return Response.ok(dir).build();
        } catch (Exception ex) {
            return Util.manageException(ex, DirectorioREST.class);
        }
    }

    @POST
    @Path("uploadv4")
    @Consumes({MediaType.MULTIPART_FORM_DATA})
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response uploadFile(
            @FormDataParam("documentoId") Integer documentoId,
            @FormDataParam("listaId") Integer listaId,
            @FormDataParam("version") Integer version
    ) throws Exception {
        try {
            
                directorioFacade.create(documentoId, listaId, version);
            
            return Response.ok().build();
        } catch (Exception ex) {
            return Util.manageException(ex, DirectorioREST.class);
        }
    }

    
    @GET
    @Path("download/{id}")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_OCTET_STREAM})
    public Response downloadFile(@PathParam("id") Long documentoId) throws Exception {
        try {
            ByteArrayOutputStream file = (ByteArrayOutputStream) directorioFacade.findFile(documentoId);
            return Response.ok(file.toByteArray(), MediaType.APPLICATION_OCTET_STREAM_TYPE).build();
        } catch (Exception ex) {
            return Util.manageException(ex, DirectorioREST.class);
        }
    }
    
   

    @GET
    @Path("usuario")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response findByUsuario() {
        try {
            List<Directorio> list = directorioFacade.findAllByUsuarioEmpresa(super.getEmpresaIdRequestContext(), super.getUsuarioRequestContext().getId());
            return Response.ok(list).build();
        } catch (Exception ex) {
            return Util.manageException(ex, DirectorioREST.class);
        }
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response findWithFilter(@BeanParam FilterQuery filterQuery) {
        try {
            boolean filtradoEmpresa = false;
            boolean filtradoUsuario = false;
            for (Filter filter : filterQuery.getFilterList()) {
                System.out.print(filter.getField());
                if (filter.getField().equals("empresa.id")) {
                    filtradoEmpresa = true;
                }
                if (filter.getField().equals("usuario.id")) {
                    filtradoUsuario = true;
                }
            }

            if (!filtradoEmpresa) {
                Filter empFilt = new Filter();
                empFilt.setCriteria("eq");
                empFilt.setField("empresa.id");
                empFilt.setValue1(super.getEmpresaIdRequestContext().toString());
                filterQuery.getFilterList().add(empFilt);
            }
            long numRows = filterQuery.isCount() ? directorioFacade.countWithFilter(filterQuery) : -1;
            List<Directorio> list = directorioFacade.findWithFilter(filterQuery);

            FilterResponse filterResponse = new FilterResponse();
            filterResponse.setData(list);
            filterResponse.setCount(numRows);
            return Response.ok(filterResponse).build();
        } catch (Exception ex) {
            return Util.manageException(ex, DirectorioREST.class);
        }
    }

    @GET
    @Path("buscarDocumentos/{parametro}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response buscar(@PathParam("parametro") String parametro) {
        try {
            List<Directorio> list = directorioFacade.buscarDocumentos(super.getEmpresaIdRequestContext(), super.getUsuarioRequestContext().getId(), parametro);
            return Response.ok(list).build();
        } catch (Exception ex) {
            return Util.manageException(ex, DirectorioREST.class);
        }
    }

    @DELETE
    @Path("{directorioId}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response remove(@PathParam("directorioId") Long directorioId) {
        try {
            Directorio dir = directorioFacade.eliminar(directorioId);
            return Response.ok(dir).build();
        } catch (Exception ex) {
            return Util.manageException(ex, DirectorioREST.class);
        }
    }

    /**
     * Actualiza los atributos del documento en función de los módulos a los que
     * pertenece la entidad a la que está relacionada.
     *
     * @param documento
     * @return
     * @throws Exception
     */
    @PUT
    @Path("documento")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response edit(Documento documento) throws Exception {
        try {
            documentoFacade.edit(documento);
            return Response.ok(documento).build();
        } catch (Exception ex) {
            return Util.manageException(ex, DirectorioREST.class);
        }
    }

    /**
     * Elimina la relación que tiene el documento con otras entidades del
     * sistema y luego lo elimina lógica y físicamente del servidor
     *
     * @param documentoId
     * @return
     * @throws Exception
     */
    @DELETE
    @Path("documento/{documentoId}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response removeDocumento(@PathParam("documentoId") Long documentoId) throws Exception {
        try {
            Documento doc = documentoFacade.find(documentoId);
            documentoFacade.remove(doc);
            return Response.ok(doc).build();
        } catch (Exception ex) {
            return Util.manageException(ex, DirectorioREST.class);
        }
    }

    /* ###################################################     ANALISIS DESVIACIONES     ################################################### */
    @POST
    @Path("analisisDesviacion/upload")
    @Consumes({MediaType.MULTIPART_FORM_DATA})
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response subirArchAnalisis(
            @FormDataParam("file") InputStream fileInputStream,
            @FormDataParam("file") FormDataContentDisposition fileMetaData,
            @FormDataParam("modParam") String modParam,
            @FormDataParam("docMetaData") String docMetaData
    ) throws Exception {
        return this.uploadFile(fileInputStream, fileMetaData, null, Modulo.SEC.name(), modParam, docMetaData, null);
    }

    @GET
    @Path("analisisDesviacion/download/{id}")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_OCTET_STREAM})
    public Response descargarArchAnalisis(@PathParam("id") Long documentoId) throws Exception {
        try {
            ByteArrayOutputStream file = (ByteArrayOutputStream) directorioFacade.encontrarDocumentoModulo(documentoId, Modulo.SEC, super.getEmpresaIdRequestContext());
            return Response.ok(file.toByteArray(), MediaType.APPLICATION_OCTET_STREAM_TYPE).build();
        } catch (Exception ex) {
            return Util.manageException(ex, DirectorioREST.class);
        }
    }

    @PUT
    @Path("analisisDesviacion")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response actualizarArchAnalisis(Documento documento) throws Exception {
        try {
            documentoFacade.edit(documento);
            return Response.ok(documento).build();
        } catch (Exception ex) {
            return Util.manageException(ex, DirectorioREST.class);
        }
    }

    @DELETE
    @Path("analisisDesviacion/{id}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response eliminarArchAnalisis(@PathParam("id") Long documentoId) {
        try {
            Documento doc = documentoFacade.find(documentoId);
            if (!doc.getModulo().equals(Modulo.SEC)) {
                throw new IllegalArgumentException();
            }
            documentoFacade.remove(doc);
            return Response.ok(doc).build();
        } catch (Exception ex) {
            return Util.manageException(ex, DirectorioREST.class);
        }
    }


    /* ###################################################     COPASST     ################################################### */
    @GET
    @Path("cop")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response busquedaFiltroCop(@BeanParam FilterQuery filterQuery) {
        try {
            filterQuery.getFilterList().add(new Filter("documento.modulo", Modulo.COP.name(), null, CriteriaFilter.EQUALS));
            return super.findWithFilter(filterQuery);
        } catch (Exception ex) {
            return Util.manageException(ex, DirectorioREST.class);
        }
    }

    @POST
    @Path("cop/upload")
    @Consumes({MediaType.MULTIPART_FORM_DATA})
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response uploadFileModulo(
            @FormDataParam("file") InputStream fileInputStream,
            @FormDataParam("file") FormDataContentDisposition fileMetaData,
            @FormDataParam("modParam") String modParam
    ) throws Exception {
        return this.uploadFile(fileInputStream, fileMetaData, null, Modulo.COP.name(), modParam, null, null);
    }

    @GET
    @Path("cop/download/{id}")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_OCTET_STREAM})
    public Response descargarActa(@PathParam("id") Long documentoId) throws Exception {
        try {
            ByteArrayOutputStream file = (ByteArrayOutputStream) directorioFacade.encontrarDocumentoModulo(documentoId, Modulo.COP, super.getEmpresaIdRequestContext());
            return Response.ok(file.toByteArray(), MediaType.APPLICATION_OCTET_STREAM_TYPE).build();
        } catch (Exception ex) {
            return Util.manageException(ex, DirectorioREST.class);
        }
    }

    @DELETE
    @Path("cop/{id}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response eliminarActa(@PathParam("id") Long documentoId) {
        try {
            Documento doc = documentoFacade.find(documentoId);
            if (!doc.getModulo().equals(Modulo.COP)) {
                throw new IllegalArgumentException();
            }
            documentoFacade.remove(doc);
            return Response.ok(doc).build();
        } catch (Exception ex) {
            return Util.manageException(ex, DirectorioREST.class);
        }
    }

    private void logScm(String action, String json, String documento, String entity) {
        try {

            ScmLogs log = new ScmLogs();
            log.setAction(action);
            log.setPkUser(documento);
            log.setFecha_creacion(new Date());
            log.setEntity(entity);
            log.setJson(json);
            scmLogsFacade.create(log);
        } catch (Exception e) {

        }
    }
}
