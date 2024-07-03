/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.sigess.facade.core;

import co.sigess.entities.com.ApiVersion;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Map;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author fmoreno
 */
@Singleton
@Startup
public class LoaderFacade {

    @PersistenceContext(unitName = "SIGESS_PU")
    private EntityManager em;

    private String plantillaMail;
    private String plantillaMailRecPasswd;
    private String plantillaMailCambioPasswd;
    private String plantillaMailCreacionUsuario;
    private String PlantillaMailNotificacionNueva;
    private String PlantillaMailObservacionDenegada;
    private String PlantillaMailRiesgosCriticos;
    private String PlantillaMailTareaSemanal;
    private String PlantillaAliadoNuevo;
    private String PlantillaAliadoActualizado;
    private String PlantillaReporteATAliado;
    private String PlantillaReporteAliadoAprobado;
    private String PlantillaReporteAliadoRechazado;
    private String PlantillaReporteAliadoModificado;
    private String PlantillaReporteAliadoCicloCorto;
    private String PlantillaDocumentosSaludLaboral;
    private String PlantillaRechazoDocumentosUsuario;
    private String PlantillaRechazoDocumentosUsuarioSOlicitado;
    private String PlantillaDocumentacionEnviada;

    private ApiVersion apiVersion;
    private Properties smsProp;

    @PostConstruct
    public void init() {
        getPlantillaMail();
        getPlantillaMailRecPasswd();
        getPlantillaMailCambioPasswd();
        getPlantillaMailCreacionUsuario();
        getPlantillaMailNotificacionNueva();
        getApiVersion();
        getSmsProperties();

    }

    public String getPlantillaDocumentosSaludLaborales() {
        if (this.PlantillaDocumentosSaludLaboral == null) {
            try {
                String ruta = getClass().getResource(Recursos.PLANTILLA_DOCUMENTOS_SALUD_LABORAL.getRuta()).getPath();
                int y = ruta.length();
                String x = isWindows(ruta, y);

                this.PlantillaDocumentosSaludLaboral = new String(Files.readAllBytes(Paths.get(x)));
            } catch (IOException ex) {
                Logger.getLogger(LoaderFacade.class.getName()).log(Level.SEVERE, null, ex);
                throw new IllegalArgumentException("No se ha podido inicializar correctamente la plantilla PLANTILLA_DOCUMENTOS_SALUD_LABORAL");
            }
        }
        return PlantillaDocumentosSaludLaboral;
    }
     public String getPlantillaDocumentosRechazoSolicitante() {
        if (this.PlantillaRechazoDocumentosUsuarioSOlicitado == null) {
            try {
                String ruta = getClass().getResource(Recursos.PLANTILLA_RECHAZO_DOCUMENTOS_SOLICITADO.getRuta()).getPath();
                int y = ruta.length();
                String x = isWindows(ruta, y);

                this.PlantillaRechazoDocumentosUsuarioSOlicitado = new String(Files.readAllBytes(Paths.get(x)));
            } catch (IOException ex) {
                Logger.getLogger(LoaderFacade.class.getName()).log(Level.SEVERE, null, ex);
                throw new IllegalArgumentException("No se ha podido inicializar correctamente la plantilla PLANTILLA_DOCUMENTOS_SALUD_LABORAL");
            }
        }
        return PlantillaRechazoDocumentosUsuarioSOlicitado;
    }
     
     public String getPlantillaDocumentosUsuario(){
          if (this.PlantillaRechazoDocumentosUsuario == null) {
            try {
                String ruta = getClass().getResource(Recursos.PLANTILLA_RECHAZO_DOCUMENTOS_USUARIO.getRuta()).getPath();
                int y = ruta.length();
                String x = isWindows(ruta, y);

                this.PlantillaRechazoDocumentosUsuario = new String(Files.readAllBytes(Paths.get(x)));
            } catch (IOException ex) {
                Logger.getLogger(LoaderFacade.class.getName()).log(Level.SEVERE, null, ex);
                throw new IllegalArgumentException("No se ha podido inicializar correctamente la plantilla PLANTILLA_DOCUMENTOS_SALUD_LABORAL");
            }
        }
        return PlantillaRechazoDocumentosUsuario;
     }
     public String getPlantillaDocumentacionEnviada(){
          if (this.PlantillaDocumentacionEnviada == null) {
            try {
                String ruta = getClass().getResource(Recursos.PLANTILLA_DOCUMENTACION_ENVIADA.getRuta()).getPath();
                int y = ruta.length();
                String x = isWindows(ruta, y);

                this.PlantillaDocumentacionEnviada = new String(Files.readAllBytes(Paths.get(x)));
            } catch (IOException ex) {
                Logger.getLogger(LoaderFacade.class.getName()).log(Level.SEVERE, null, ex);
                throw new IllegalArgumentException("No se ha podido inicializar correctamente la plantilla PLANTILLA_DOCUMENTOS_SALUD_LABORAL");
            }
        }
        return PlantillaDocumentacionEnviada;
     }

    public String getPlantillaMail() {
        if (this.plantillaMail == null) {
            try {

                String ruta = getClass().getResource(Recursos.PLANTILLA_MAIL.getRuta()).getPath();
                int y = ruta.length();
                String x = isWindows(ruta, y);
                System.out.println(x + "añsdkasñldsadasd");
                this.plantillaMail = new String(Files.readAllBytes(Paths.get(x)));
            } catch (IOException ex) {
                Logger.getLogger(LoaderFacade.class.getName()).log(Level.SEVERE, null, ex);
                throw new IllegalArgumentException("No se ha podido inicializar correctamente la plantilla PLANTILLA_MAIL");
            }
        }
        return plantillaMail;
    }

    public String getPlantillaMailRecPasswd() {
        if (this.plantillaMailRecPasswd == null) {
            try {
                String ruta = getClass().getResource(Recursos.PLANTILLA_MAIL_REC_PASSW.getRuta()).getPath();
                int y = ruta.length();
                String x = isWindows(ruta, y);
                this.plantillaMailRecPasswd = new String(Files.readAllBytes(Paths.get(x)));
            } catch (IOException ex) {
                Logger.getLogger(LoaderFacade.class.getName()).log(Level.SEVERE, null, ex);
                throw new IllegalArgumentException("No se ha podido inicializar correctamente la plantilla PLANTILLA_MAIL_REC_PASSW");
            }
        }
        return plantillaMailRecPasswd;
    }

    public String getPlantillaMailCambioPasswd() {
        if (this.plantillaMailCambioPasswd == null) {
            try {
                String ruta = getClass().getResource(Recursos.PLANTILLA_MAIL_CAMBIO_PASSW.getRuta()).getPath();
                int y = ruta.length();
                String x = isWindows(ruta, y);
                this.plantillaMailCambioPasswd = new String(Files.readAllBytes(Paths.get(x)));
            } catch (IOException ex) {
                Logger.getLogger(LoaderFacade.class.getName()).log(Level.SEVERE, null, ex);
                throw new IllegalArgumentException("No se ha podido inicializar correctamente la plantilla PLANTILLA_MAIL_CAMBIO_PASSW");
            }
        }
        return plantillaMailCambioPasswd;
    }

    public String getPlantillaMailNotificacionNueva() {
        if (this.PlantillaMailNotificacionNueva == null) {
            try {
                String ruta = getClass().getResource(Recursos.PLANTILLA_MAIL_NOT_NEW.getRuta()).getPath();
                int y = ruta.length();
                String x = isWindows(ruta, y);
                this.PlantillaMailNotificacionNueva = new String(Files.readAllBytes(Paths.get(x)));
            } catch (IOException ex) {
                Logger.getLogger(LoaderFacade.class.getName()).log(Level.SEVERE, null, ex);
                throw new IllegalArgumentException("No se ha podido inicializar correctamente la plantilla PLANTILLA_MAIL_NOT_NEW");
            }
        }
        return PlantillaMailNotificacionNueva;
    }

    public String getPlantillaMailCreacionUsuario() {
        if (this.plantillaMailCreacionUsuario == null) {
            try {
                String ruta = getClass().getResource(Recursos.PLANTILLA_MAIL_CREACION_USUARIO.getRuta()).getPath();
                int y = ruta.length();
                String x = isWindows(ruta, y);
                this.plantillaMailCreacionUsuario = new String(Files.readAllBytes(Paths.get(x)));
            } catch (IOException ex) {
                Logger.getLogger(LoaderFacade.class.getName()).log(Level.SEVERE, null, ex);
                throw new IllegalArgumentException("No se ha podido inicializar correctamente la plantilla PLANTILLA_MAIL_CREACION_USUARIO");
            }
        }
        return plantillaMailCreacionUsuario;
    }

    public String getPlantillaMailObservacionDenegada() {
        if (this.PlantillaMailObservacionDenegada == null) {
            try {
                String ruta = getClass().getResource(Recursos.PLANTILLA_MAIL_OBSERVACION_DENEGADA.getRuta()).getPath();
                int y = ruta.length();
                String x = isWindows(ruta, y);
                System.out.println(x + "observacion");
                this.PlantillaMailObservacionDenegada = new String(Files.readAllBytes(Paths.get(x)));
            } catch (IOException ex) {
                Logger.getLogger(LoaderFacade.class.getName()).log(Level.SEVERE, null, ex);
                throw new IllegalArgumentException("No se ha podido inicializar correctamente la plantilla PLANTILLA_MAIL_OBSERVACION_DENEGADA");
            }
        }
        return PlantillaMailObservacionDenegada;
    }

    public String getPlantillaMailRiesgosCriticos() {
        if (this.PlantillaMailRiesgosCriticos == null) {
            try {
                String ruta = getClass().getResource(Recursos.PLANTILLA_MAIL_RIESGOS_CRITICOS.getRuta()).getPath();
                int y = ruta.length();
                String x = isWindows(ruta, y);
                System.out.println(x + "riesgos");
                this.PlantillaMailRiesgosCriticos = new String(Files.readAllBytes(Paths.get(x)));
            } catch (IOException ex) {
                Logger.getLogger(LoaderFacade.class.getName()).log(Level.SEVERE, null, ex);
                throw new IllegalArgumentException("No se ha podido inicializar correctamente la plantilla PLANTILLA_MAIL_RIESGOS_CRITICOS");
            }
        }
        return PlantillaMailRiesgosCriticos;
    }

    public String getPlantillaAliadoNuevo() {
        if (this.PlantillaAliadoNuevo == null) {
            try {
                String ruta = getClass().getResource(Recursos.PLANTILLA_MAIL_ALIADO_NUEVO.getRuta()).getPath();
                int y = ruta.length();
                String x = isWindows(ruta, y);
                System.out.println(x + "riesgos");
                this.PlantillaAliadoNuevo = new String(Files.readAllBytes(Paths.get(x)));
            } catch (IOException ex) {
                Logger.getLogger(LoaderFacade.class.getName()).log(Level.SEVERE, null, ex);
                throw new IllegalArgumentException("No se ha podido inicializar correctamente la plantilla PLANTILLA_MAIL_ALIADO_NUEVO");
            }
        }
        return PlantillaAliadoNuevo;
    }

    public String getPlantillaAliadoActualizado() {
        if (this.PlantillaAliadoActualizado == null) {
            try {
                String ruta = getClass().getResource(Recursos.PLANTILLA_MAIL_ALIADO_ACTUALIZADO.getRuta()).getPath();
                int y = ruta.length();
                String x = isWindows(ruta, y);
                System.out.println(x + "riesgos");
                this.PlantillaAliadoActualizado = new String(Files.readAllBytes(Paths.get(x)));
            } catch (IOException ex) {
                Logger.getLogger(LoaderFacade.class.getName()).log(Level.SEVERE, null, ex);
                throw new IllegalArgumentException("No se ha podido inicializar correctamente la plantilla PLANTILLA_MAIL_ALIADO_ACTUALIZADO");
            }
        }
        return PlantillaAliadoActualizado;
    }

    public String getPlantillaAliadoCicloCorto() {
        if (this.PlantillaReporteAliadoCicloCorto == null) {
            try {
                String ruta = getClass().getResource(Recursos.PLANTILLA_MAIL_ALIADO_CICLOCORTO.getRuta()).getPath();
                int y = ruta.length();
                String x = isWindows(ruta, y);
                this.PlantillaReporteAliadoCicloCorto = new String(Files.readAllBytes(Paths.get(x)));
            } catch (IOException ex) {
                Logger.getLogger(LoaderFacade.class.getName()).log(Level.SEVERE, null, ex);
                throw new IllegalArgumentException("No se ha podido inicializar correctamente la plantilla PLANTILLA_MAIL_ALIADO_CICLOCORTO");
            }
        }
        return PlantillaReporteAliadoCicloCorto;
    }

    public String getPlantillaMailTareaSemanal() {
        if (this.PlantillaMailTareaSemanal == null) {
            try {
                String ruta = getClass().getResource(Recursos.PLANTILLA_MAIL_TAREA_SEMANAL.getRuta()).getPath();
                int y = ruta.length();
                String x = isWindows(ruta, y);
                System.out.println(x + "SEMANATAREA");
                this.PlantillaMailTareaSemanal = new String(Files.readAllBytes(Paths.get(x)));
            } catch (IOException ex) {
                Logger.getLogger(LoaderFacade.class.getName()).log(Level.SEVERE, null, ex);
                throw new IllegalArgumentException("No se ha podido inicializar correctamente la plantilla PLANTILLA_MAIL_TAREA_SEMANAL");
            }
        }
        return PlantillaMailTareaSemanal;
    }

    public String getPlantillaReporteAliado() {
        if (this.PlantillaReporteATAliado == null) {
            try {
                String ruta = getClass().getResource(Recursos.PLANTILLA_REPORTE_AT_ALIADO.getRuta()).getPath();
                int y = ruta.length();
                String x = isWindows(ruta, y);
                System.out.println(x + "REPORTE ALIADO");
                this.PlantillaReporteATAliado = new String(Files.readAllBytes(Paths.get(x)));
            } catch (Exception e) {
                Logger.getLogger(LoaderFacade.class.getName()).log(Level.SEVERE, null, e);
                throw new IllegalArgumentException("No se ha podido inicializar correctamente la plantilla PLANTILLA_REPORTE_AT_ALIADO");
            }
        }
        return PlantillaReporteATAliado;
    }

    public String getPlantillaReporteAliadoAprobado() {
        if (this.PlantillaReporteAliadoAprobado == null) {
            try {
                String ruta = getClass().getResource(Recursos.PLANTILLA_REPORTE_ALIADO_APROBADO.getRuta()).getPath();
                int y = ruta.length();
                String x = isWindows(ruta, y);
                System.out.println(x + "REPORTE ALIADO APROBADO");
                this.PlantillaReporteAliadoAprobado = new String(Files.readAllBytes(Paths.get(x)));
            } catch (Exception e) {
                Logger.getLogger(LoaderFacade.class.getName()).log(Level.SEVERE, null, e);
                throw new IllegalArgumentException("No se ha podido inicializar correctamente la plantilla PLANTILLA_REPORTE_ALIADO_APROBADO");
            }
        }
        return PlantillaReporteAliadoAprobado;
    }

    public String getPlantillaReporteAliadoRechazado() {
        if (this.PlantillaReporteAliadoRechazado == null) {
            try {
                String ruta = getClass().getResource(Recursos.PLANTILLA_REPORTE_ALIADO_RECHAZADO.getRuta()).getPath();
                int y = ruta.length();
                String x = isWindows(ruta, y);
                System.out.println(x + "REPORTE ALIADO RECHAZADO");
                this.PlantillaReporteAliadoRechazado = new String(Files.readAllBytes(Paths.get(x)));
            } catch (Exception e) {
                Logger.getLogger(LoaderFacade.class.getName()).log(Level.SEVERE, null, e);
                throw new IllegalArgumentException("No se ha podido inicializar correctamente la plantilla PLANTILLA_REPORTE_ALIADO_RECHAZADO");
            }
        }
        return PlantillaReporteAliadoRechazado;
    }

    public String getPlantillaReporteAliadoModificado() {
        if (this.PlantillaReporteAliadoModificado == null) {
            try {
                String ruta = getClass().getResource(Recursos.PLANTILLA_REPORTE_ALIADO_MODIFICADO.getRuta()).getPath();
                int y = ruta.length();
                String x = isWindows(ruta, y);
                System.out.println(x + "REPORTE ALIADO MODIFICADO");
                this.PlantillaReporteAliadoModificado = new String(Files.readAllBytes(Paths.get(x)));
            } catch (Exception e) {
                Logger.getLogger(LoaderFacade.class.getName()).log(Level.SEVERE, null, e);
                throw new IllegalArgumentException("No se ha podido inicializar correctamente la plantilla PLANTILLA_REPORTE_ALIADO_MODIFICADO");
            }
        }
        return PlantillaReporteAliadoModificado;
    }

    public ApiVersion getApiVersion() {
        if (this.apiVersion == null) {
            Query q = this.em.createQuery("SELECT av FROM ApiVersion av ORDER BY av.id DESC");
            q.setMaxResults(1);
            this.apiVersion = (ApiVersion) q.getSingleResult();
            System.out.println("########################## Desplegando api version: " + this.apiVersion.getVersionActual() + " ##########################");
        }
        return this.apiVersion;
    }

    public ApiVersion refreshApiVersion() {
        Query q = this.em.createQuery("SELECT av FROM ApiVersion av ORDER BY av.id DESC");
        q.setMaxResults(1);
        this.apiVersion = (ApiVersion) q.getSingleResult();
        System.out.println("########################## refrescando api version: " + this.apiVersion.getVersionActual() + " ##########################");

        return this.apiVersion;
    }

    public Properties getSmsProperties() {
        if (this.smsProp == null) {
            try {
                InputStream inputStream = getClass().getResourceAsStream(Recursos.SMS_INTEGRACION_PROPERTIES.getRuta());
                this.smsProp = new Properties();
                this.smsProp.load(inputStream);
            } catch (IOException ex) {
                Logger.getLogger(LoaderFacade.class.getName()).log(Level.SEVERE, null, ex);
                throw new IllegalArgumentException("No se ha podido inicializar el archivo de propiedades de integracion SMS");
            }
        }
        return this.smsProp;
    }

    private String isWindows(String route, int y) {
        System.out.println(System.getProperty("os.name").toLowerCase().contains("windows"));
        route = System.getProperty("os.name").toLowerCase().contains("windows") ? route.substring(1, y) : route;
        return route;
    }

}
