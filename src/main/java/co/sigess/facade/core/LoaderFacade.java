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
import javax.ws.rs.Path;

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

    private ApiVersion apiVersion;
    private Properties smsProp;

    @PostConstruct
    public void init() {
        getPlantillaMail();
        getPlantillaMailRecPasswd();
        getPlantillaMailCambioPasswd();
        getPlantillaMailCreacionUsuario();
        getApiVersion();
        getSmsProperties();
       
    }
   
   public String getPlantillaMail() {
        if (this.plantillaMail == null) {
            try {
                    
                String ruta = getClass().getResource(Recursos.PLANTILLA_MAIL.getRuta()).getPath();
                int y = ruta.length(); 
               String x  = isWindows(ruta,y);
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
                String x = isWindows(ruta,y);
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
                String x = isWindows(ruta,y);
                this.plantillaMailCambioPasswd = new String(Files.readAllBytes(Paths.get(x)));
            } catch (IOException ex) {
                Logger.getLogger(LoaderFacade.class.getName()).log(Level.SEVERE, null, ex);
                throw new IllegalArgumentException("No se ha podido inicializar correctamente la plantilla PLANTILLA_MAIL_CAMBIO_PASSW");
            }
        }
        return plantillaMailCambioPasswd;
    }

    public String getPlantillaMailCreacionUsuario() {
        if (this.plantillaMailCreacionUsuario == null) {
            try {
                String ruta = getClass().getResource(Recursos.PLANTILLA_MAIL_CREACION_USUARIO.getRuta()).getPath();
                 int y = ruta.length(); 
                String x = isWindows(ruta,y);
                this.plantillaMailCreacionUsuario = new String(Files.readAllBytes(Paths.get(x)));
            } catch (IOException ex) {
                Logger.getLogger(LoaderFacade.class.getName()).log(Level.SEVERE, null, ex);
                throw new IllegalArgumentException("No se ha podido inicializar correctamente la plantilla PLANTILLA_MAIL_CREACION_USUARIO");
            }
        }
        return plantillaMailCreacionUsuario;
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
    
    private String isWindows (String route,int y){
        System.out.println(System.getProperty("os.name").toLowerCase().contains("windows"));
        route  =   System.getProperty("os.name").toLowerCase().contains("windows") ?  route.substring(1,y) : route;
        return route; 
    }

}
