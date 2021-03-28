/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.sigess.util;

import co.sigess.entities.com.Mensaje;
import co.sigess.entities.com.TipoMensaje;
import co.sigess.exceptions.UserMessageException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.ws.rs.NotAuthorizedException;
import javax.ws.rs.core.Response;

/**
 *
 * @author fmoreno
 */
public class Util {

    public static final String RSRC_REPORTE_EVALUACION_SGE = "/reports/sge/PlantillaReporteSGE.docx";
    public static final String RSRC_HTML_REPORTE_EVALUACION_SGE = "/reports/sge/PlantillaReporteSGE.html";
    public static final String RSRC_PLAN_TRABAJO_SGE = "/reports/sge/PlantillaPlanTrabajoSGE.xlsx";
    public static final SimpleDateFormat SIMPLE_DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");
    public static final SimpleDateFormat SIMPLE_DATE_FORMAT_ISO = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    public static final SimpleDateFormat SIMPLE_DATE_FORMAT_ID = new SimpleDateFormat("yyyyMMddhhmmssSSS");
    public static final String TMP_DIR = "/tmp/";

    public static void zip(File zipFile, List<File> srcFiles) throws Exception {
        try {
            // create byte buffer
            byte[] buffer = new byte[1024];
            FileOutputStream fos = new FileOutputStream(zipFile);
            ZipOutputStream zos = new ZipOutputStream(fos);
            for (int i = 0; i < srcFiles.size(); i++) {
                File srcFile = srcFiles.get(i);

                FileInputStream fis = new FileInputStream(srcFile);
                // begin writing a new ZIP entry, positions the stream to the start of the entry data
                zos.putNextEntry(new ZipEntry(srcFile.getName()));
                int length;
                while ((length = fis.read(buffer)) > 0) {
                    zos.write(buffer, 0, length);
                }
                zos.closeEntry();
                // close the InputStream
                fis.close();
            }
            // close the ZipOutputStream
            zos.close();
        } catch (IOException ioe) {
            System.out.println("Error creating zip file: " + ioe);
        }
    }

    public static String generateId() {
        return (Instant.now().plusNanos(System.nanoTime())).toString();
    }

    public static Response manageException(Exception ex, Class restClass) {
        Throwable exception = getUserException(ex);
        Mensaje msg = new Mensaje();
        msg.setDetalle("ERROR ID: " + getFechaId());
        msg.setTipoMensaje(TipoMensaje.error);

        if (exception instanceof UserMessageException) {
            UserMessageException ume = (UserMessageException) exception;
            if (ume.getTipoMensaje().equals(TipoMensaje.error)) {
                Logger.getLogger(restClass.getName()).log(Level.SEVERE, msg.toString(), ume);
                 System.out.print("Aqui paso");
            }
            return Response.status(Response.Status.BAD_REQUEST).entity(ume.getObjetoMensaje()).build();
        } else if (exception instanceof ConstraintViolationException) {
            ConstraintViolationException cve = (ConstraintViolationException) exception;
            List<String> msgList = new ArrayList<>();
            for (ConstraintViolation<?> constraintViolation : cve.getConstraintViolations()) {
                msgList.add(constraintViolation.getMessage());
            }
            msg.setMensaje("Error de solicitud");
            msg.setDetalle("Parámetros incorrectos: " + msgList.toString());
            msg.setTipoMensaje(TipoMensaje.warn);
            Logger.getLogger(restClass.getName()).log(Level.SEVERE, msg.toString(), exception);
            return Response.status(Response.Status.BAD_REQUEST).entity(msg).build();
        } else if (exception instanceof IllegalArgumentException) {
            msg.setMensaje("SOLICITUD MAL FORMADA");
            Logger.getLogger(restClass.getName()).log(Level.SEVERE, msg.toString(), (IllegalArgumentException) exception);
            return Response.status(Response.Status.BAD_REQUEST).entity(msg).build();
        } else {
            msg.setMensaje("ERROR ID: " + getFechaId());
            msg.setDetalle("La solicitud presenta inconsistencias y no puede ser resuelta" + ex);
            Logger.getLogger(restClass.getName()).log(Level.SEVERE, msg.toString(), ex);
            return Response.status(Response.Status.BAD_REQUEST).entity(msg).build();
        }
    }

    private static Throwable getUserException(Throwable ex) {
        if (ex.getCause() instanceof UserMessageException) {
            return ex.getCause();
        } else {
            return (ex.getCause() == null) ? ex : getUserException(ex.getCause());
        }
    }

    public static boolean isNumber(String value) {
        try {
            Integer.parseInt(value);
            return true;
        } catch (NumberFormatException nfe) {
            return false;
        }
    }

    public static String getFechaId() {
        return SIMPLE_DATE_FORMAT_ID.format(Calendar.getInstance().getTime());
    }

    public static String objectToJson(Object obj) throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(obj);
        return json;
    }

    public static <T> T fromJson(String json, Class<T> type) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            return mapper.readValue(json, type);
        } catch (JsonProcessingException ex) {
            Logger.getLogger(type.getName()).log(Level.SEVERE, null, ex);
            throw new NotAuthorizedException("No Authorization");
        } catch (IOException ex) {
            Logger.getLogger(type.getName()).log(Level.SEVERE, null, ex);
            throw new NotAuthorizedException("No Authorization");
        }
    }
}
