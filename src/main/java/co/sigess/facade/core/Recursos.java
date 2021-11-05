/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.sigess.facade.core;

/**
 *
 * @author fmoreno
 */
public enum Recursos {
        
    PLANTILLA_MAIL("/mail/templates/PlantillaMailPrincipal.html"),
    PLANTILLA_MAIL_REC_PASSW("/mail/templates/PlantillaRecuperacionPasswd.html"),
    PLANTILLA_MAIL_CAMBIO_PASSW("/mail/templates/PlantillaCambioPasswd.html"),
    PLANTILLA_MAIL_CREACION_USUARIO("/mail/templates/PlantillaCreacionUsuario.html"),
    PLANTILLA_MAIL_NOT_NEW("/mail/templates/PlantillaNotificacionNueva.html"),
    PLANTILLA_MAIL_OBSERVACION_DENEGADA("/mail/templates/PlantillaObservacionDenegada.html"),
    
    SMS_INTEGRACION_PROPERTIES("/sms/integracion.properties");
    
    private final String ruta;

    private Recursos(String ruta){
        this.ruta = ruta;
    }

    public String getRuta() {
        return ruta;
    }    
}
