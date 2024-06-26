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
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import javax.ejb.EJB;
import javax.ws.rs.BeanParam;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.apache.commons.codec.digest.DigestUtils;
import org.bouncycastle.crypto.BlockCipher;
import org.bouncycastle.crypto.BufferedBlockCipher;
import org.bouncycastle.crypto.engines.AESEngine;
import org.bouncycastle.crypto.modes.PaddedBlockCipher;
import org.bouncycastle.crypto.params.KeyParameter;
//import org.bouncycastle.util.encoders.Base64;
//import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.binary.Hex;
import org.bouncycastle.crypto.paddings.PKCS7Padding;
import org.bouncycastle.crypto.paddings.PaddedBufferedBlockCipher;
import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
import org.glassfish.jersey.media.multipart.FormDataParam;
//import java.util.Base64;
import org.bouncycastle.crypto.CipherParameters;
import org.bouncycastle.crypto.modes.CBCBlockCipher;
import org.bouncycastle.crypto.params.ParametersWithIV;
import javax.crypto.Cipher;
import java.security.MessageDigest;
import java.util.Arrays;
import java.util.Base64;
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
                directorioFacade.actualizarModuloDir();
                return Response.ok(json).build();
            }
        } catch (Exception ex) {
            return Util.manageException(ex, DirectorioREST.class);
        }
        return null;

    }

    
//    @POST
//    @Path("upload")
//    @Consumes({MediaType.MULTIPART_FORM_DATA})
//    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
//    public Response uploadFile(
//            @FormDataParam("file") InputStream fileInputStream,
//            @FormDataParam("file") FormDataContentDisposition fileMetaData,
//            @FormDataParam("dpId") Long directorioPadreId,
//            @FormDataParam("mod") String modulo,
//            @FormDataParam("modParam") String modParam,
//            @FormDataParam("docMetaData") String docMetaData,
//            @FormDataParam("caseId") Long caseId
//    ) throws Exception {
//        try {
//            directorioFacade.validarParametrosUpload(modulo, modParam);
//            Directorio dir = null;
//            if (fileMetaData != null) {
//                if (fileMetaData.getFileName() == null) {
//                    throw new IllegalArgumentException("No se ha especificado un nombre para el archivo a guardar");
//                }
//                String fileName = fileMetaData.getFileName();
//                Map<String, Object> map = FileUtil.saveInPathFS(fileInputStream);
//                String relativePath = (String) map.get(FileUtil.RELATIVE_PATH);
//                dir = new Directorio();
//                dir.setEsDocumento(true);
//                dir.setNombre(fileName);
//                dir.setCaseId(caseId);
//                dir.setEmpresa(new Empresa(super.getEmpresaIdRequestContext()));
//                dir.setUsuario(super.getUsuarioRequestContext());
//                dir.setDocumento(new Documento());
//                dir.getDocumento().setRuta(relativePath);
//                dir.getDocumento().setNombre(fileName);
//                dir.getDocumento().setTamanio((long) map.get(FileUtil.FILE_SIZE));
//                dir.getDocumento().setModulo(Modulo.valueOf(modulo));
//                if (docMetaData != null) {
//                    Documento docObj = Util.fromJson(docMetaData, Documento.class);
//                    dir.getDocumento().setDescripcion(docObj.getDescripcion());
//                }
//                if (directorioPadreId != null) {
//                    dir.setDirectorioPadre(new Directorio(directorioPadreId));
//                }
//                directorioFacade.create(dir, modParam);
//            } else {
//                directorioFacade.eliminarDocumentos(modulo, modParam);
//            }
//            ObjectMapper mapper = new ObjectMapper();
//            String json = mapper.writeValueAsString(dir);
//            if (caseId != null) {
//                this.logScm("Guardado de ausentismo", json, dir.getId().toString(), directorioFacade.getClass().toString());
//            }
//            return Response.ok(dir).build();
//        } catch (Exception ex) {
//            return Util.manageException(ex, DirectorioREST.class);
//        }
//    }
    
    @POST
    @Path("upload")
    @Consumes({MediaType.MULTIPART_FORM_DATA})
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response uploadFile(
            @FormDataParam("file") InputStream fileInputStream,
            @FormDataParam("file") FormDataContentDisposition fileMetaData,
            @FormDataParam("descripcion") String descripcion,
            @FormDataParam("dpId") Long directorioPadreId,
            @FormDataParam("mod") String modulo,
            @FormDataParam("modParam") String modParam,
            @FormDataParam("docMetaData") String docMetaData,
            @FormDataParam("caseId") Long caseId,
            @FormDataParam("nivelAcceso") String nivelAcceso,
            @FormDataParam("perfilId") String perfilId
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
                dir.setNivelAcceso(nivelAcceso);
                dir.setEmpresa(new Empresa(super.getEmpresaIdRequestContext()));
                dir.setUsuario(super.getUsuarioRequestContext());
                dir.setDocumento(new Documento());
                dir.setPerfilId(perfilId);
                dir.getDocumento().setRuta(relativePath);
                dir.getDocumento().setNombre(fileName);
                dir.getDocumento().setDescripcion(descripcion);
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
            directorioFacade.actualizarModuloDir();
            return Response.ok(dir).build();
        } catch (Exception ex) {
            return Util.manageException(ex, DirectorioREST.class);
        }
    }
    @POST
    @Path("uploadEvidencias")
    @Consumes({MediaType.MULTIPART_FORM_DATA})
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response uploadFile(
            @FormDataParam("file") InputStream fileInputStream,
            @FormDataParam("file") FormDataContentDisposition fileMetaData,
            @FormDataParam("descripcion") String descripcion,
            @FormDataParam("dpId") Long directorioPadreId,
            @FormDataParam("mod") String modulo,
            @FormDataParam("modParam") String modParam,
            @FormDataParam("docMetaData") String docMetaData,
            @FormDataParam("caseId") Long caseId,
            @FormDataParam("nivelAcceso") String nivelAcceso,
            @FormDataParam("tipoEvidencias") String tipoEvidencias,
            @FormDataParam("perfilId") String perfilId
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
                dir.setNivelAcceso(nivelAcceso);
                dir.setEmpresa(new Empresa(super.getEmpresaIdRequestContext()));
                dir.setUsuario(super.getUsuarioRequestContext());
                dir.setDocumento(new Documento());
                dir.getDocumento().setRuta(relativePath);
                dir.getDocumento().setNombre(fileName);
                dir.getDocumento().setDescripcion(descripcion);
                dir.getDocumento().setProceso(tipoEvidencias);
                dir.getDocumento().setTamanio((long) map.get(FileUtil.FILE_SIZE));
                dir.getDocumento().setModulo(Modulo.valueOf(modulo));
                if (docMetaData != null) {
                    Documento docObj = Util.fromJson(docMetaData, Documento.class);
                    dir.getDocumento().setDescripcion(docObj.getDescripcion());
                }
                if (directorioPadreId != null) {
                    dir.setDirectorioPadre(new Directorio(directorioPadreId));
                }
                directorioFacade.create(dir, modParam,tipoEvidencias);
            } else {
                directorioFacade.eliminarDocumentos(modulo, modParam);
            }
            ObjectMapper mapper = new ObjectMapper();
            String json = mapper.writeValueAsString(dir);
            if (caseId != null) {
                this.logScm("Guardado de ausentismo", json, dir.getId().toString(), directorioFacade.getClass().toString());
            }
            directorioFacade.actualizarModuloDir();
            return Response.ok(dir).build();
        } catch (Exception ex) {
            return Util.manageException(ex, DirectorioREST.class);
        }
    }
    
    @POST
    @Path("uploadv3")
    @Consumes({MediaType.MULTIPART_FORM_DATA})
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response uploadFile(
            @FormDataParam("documentoId") Integer documentoId,
            @FormDataParam("paramId") Integer paramId,
            @FormDataParam("modulo") String modulo
    ) throws Exception {
        try {
            
            directorioFacade.create(documentoId, paramId, modulo);
            directorioFacade.actualizarModuloDir();
            return Response.ok().build();
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
            directorioFacade.actualizarModuloDir();
            return Response.ok().build();
        } catch (Exception ex) {
            return Util.manageException(ex, DirectorioREST.class);
        }
    }
    
    @POST
    @Path("updateV2")
    @Consumes({MediaType.MULTIPART_FORM_DATA})
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response updateFile(
            @FormDataParam("documentoId") Integer documentoId,
            @FormDataParam("listaId") Integer listaId,
            @FormDataParam("version") Integer version
    ) throws Exception {
        try {
            
                directorioFacade.update(documentoId, listaId, version);
            directorioFacade.actualizarModuloDir();
            return Response.ok().build();
        } catch (Exception ex) {
            return Util.manageException(ex, DirectorioREST.class);
        }
    }
    
    @GET
    @Path("download/{id}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response downloadFile(
            @HeaderParam("Authorization") String authorizationHeader,
            @PathParam("id") String documentoId) throws Exception {
        
        try {
            

            byte[] keyBytes = authorizationHeader.getBytes(StandardCharsets.UTF_8);
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            keyBytes = digest.digest(keyBytes);
            keyBytes = Arrays.copyOf(keyBytes, 16);

            SecretKeySpec aesKey = new SecretKeySpec(keyBytes, "AES");

            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            cipher.init(Cipher.DECRYPT_MODE, aesKey);
            byte[] decryptedBytes = cipher.doFinal(Base64.getDecoder().decode(documentoId));
            String textoDesencriptado = new String(decryptedBytes, StandardCharsets.UTF_8);

            System.out.println("Texto Desencriptado: " + textoDesencriptado);

            
            
            ByteArrayOutputStream file = (ByteArrayOutputStream) directorioFacade.findFile(Long.parseLong(textoDesencriptado));
            return Response.ok(file.toByteArray(), MediaType.APPLICATION_OCTET_STREAM_TYPE).build();
        } catch (Exception ex) {
            System.out.println(ex);
            return Util.manageException(ex, DirectorioREST.class);

        }
        
    }
    
    @POST
    @Path("download")
    @Consumes({MediaType.MULTIPART_FORM_DATA})
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response downloadFilePost(
            @HeaderParam("Authorization") String authorizationHeader,
            @FormDataParam("data") String encryptedId) throws Exception {
        
        try {
            

            byte[] keyBytes = authorizationHeader.getBytes(StandardCharsets.UTF_8);
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            keyBytes = digest.digest(keyBytes);
            keyBytes = Arrays.copyOf(keyBytes, 16);

            SecretKeySpec aesKey = new SecretKeySpec(keyBytes, "AES");

            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            cipher.init(Cipher.DECRYPT_MODE, aesKey);
            byte[] decryptedBytes = cipher.doFinal(Base64.getDecoder().decode(encryptedId));
            String textoDesencriptado = new String(decryptedBytes, StandardCharsets.UTF_8);

            System.out.println("Texto Desencriptado: " + textoDesencriptado);

            
            
            ByteArrayOutputStream file = (ByteArrayOutputStream) directorioFacade.findFile(Long.parseLong(textoDesencriptado));
            return Response.ok(file.toByteArray(), MediaType.APPLICATION_OCTET_STREAM_TYPE).build();
        } catch (Exception ex) {
            System.out.println(ex);
            return Util.manageException(ex, DirectorioREST.class);

        }
        
    }
        
    private static byte[] hexStringToByteArray(String s) {
        int len = s.length();
        byte[] data = new byte[len / 2];
        for (int i = 0; i < len; i += 2) {
            data[i / 2] = (byte) ((Character.digit(s.charAt(i), 16) << 4)
                    + Character.digit(s.charAt(i + 1), 16));
        }
        return data;
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
    
    @GET
    @Path("buscarDocumentosById/{ID}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response buscarById(@PathParam("ID") int Id) {
        try {
            List<Directorio> list = directorioFacade.buscarDocumentosById(Id);
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
    public Response removeDocumento(@HeaderParam("Authorization") String authorizationHeader
            , @PathParam("documentoId") String documentoId) throws Exception {
        try {
            
            
            byte[] keyBytes = authorizationHeader.getBytes(StandardCharsets.UTF_8);
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            keyBytes = digest.digest(keyBytes);
            keyBytes = Arrays.copyOf(keyBytes, 16);

            SecretKeySpec aesKey = new SecretKeySpec(keyBytes, "AES");

            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            cipher.init(Cipher.DECRYPT_MODE, aesKey);
            byte[] decryptedBytes = cipher.doFinal(Base64.getDecoder().decode(documentoId));
            String textoDesencriptado = new String(decryptedBytes, StandardCharsets.UTF_8);

            System.out.println("Texto Desencriptado: " + textoDesencriptado);
            
            
            Documento doc = documentoFacade.find(Long.parseLong(textoDesencriptado));
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
        Response resp=this.uploadFile(fileInputStream, fileMetaData,null, null, Modulo.SEC.name(), modParam, docMetaData, null, "PUBLICO",null);
        directorioFacade.actualizarModuloDir();
        return resp;
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
        Response resp=this.uploadFile(fileInputStream, fileMetaData,null, null, Modulo.COP.name(), modParam, null, null, "PUBLICO",null);
        directorioFacade.actualizarModuloDir();
        return resp;
    }
    
    @POST
    @Path("cop/download")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_OCTET_STREAM})
    public Response descargarActaPost(
            @HeaderParam("Authorization") String authorizationHeader,
            @FormDataParam("data") String encryptedId) throws Exception {
        try {
            
            byte[] keyBytes = authorizationHeader.getBytes(StandardCharsets.UTF_8);
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            keyBytes = digest.digest(keyBytes);
            keyBytes = Arrays.copyOf(keyBytes, 16);

            SecretKeySpec aesKey = new SecretKeySpec(keyBytes, "AES");

            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            cipher.init(Cipher.DECRYPT_MODE, aesKey);
            byte[] decryptedBytes = cipher.doFinal(Base64.getDecoder().decode(encryptedId));
            String textoDesencriptado = new String(decryptedBytes, StandardCharsets.UTF_8);
            
            ByteArrayOutputStream file = (ByteArrayOutputStream) directorioFacade.findFile(Long.parseLong(textoDesencriptado));

//            ByteArrayOutputStream file = (ByteArrayOutputStream) directorioFacade.encontrarDocumentoModulo(documentoId, Modulo.COP, super.getEmpresaIdRequestContext());
            return Response.ok(file.toByteArray(), MediaType.APPLICATION_OCTET_STREAM_TYPE).build();
        } catch (Exception ex) {
            return Util.manageException(ex, DirectorioREST.class);
        }
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
