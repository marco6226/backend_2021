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
    PLANTILLA_MAIL_RIESGOS_CRITICOS("/mail/templates/PlantillaNocumplecriticos.html"),
    PLANTILLA_MAIL_TAREA_SEMANAL("/mail/templates/PlantillaMailTareaSemanal.html"),
    PLANTILLA_MAIL_ALIADO_NUEVO("/mail/templates/PlantillaAliadoNuevo.html"),
    PLANTILLA_MAIL_ALIADO_ACTUALIZADO("/mail/templates/PlantillaAliadoActualizado.html"),
    PLANTILLA_REPORTE_AT_ALIADO("/mail/templates/PlantillaReporteATAliado.html"),
    PLANTILLA_REPORTE_ALIADO_APROBADO("/mail/templates/PlantillaReporteAliadoAprobado.html"),
    PLANTILLA_REPORTE_ALIADO_RECHAZADO("/mail/templates/PlantillaReporteAliadoRechazado.html"),
    PLANTILLA_REPORTE_ALIADO_MODIFICADO("/mail/templates/PlantillaReporteAliadoModificado.html"),
    PLANTILLA_MAIL_ALIADO_CICLOCORTO("/mail/templates/PlantillaAliadoCicloCorto.html"),
    PLANTILLA_DOCUMENTOS_SALUD_LABORAL("/mail/templates/PlantillaDocumentacionSaludLaboral.html"),
    SMS_INTEGRACION_PROPERTIES("/sms/integracion.properties"),
    
    EXCEL_CONSOLIDADO_CORONA("/Excel/historicoexcelcorona.xlsx");
    
    private final String ruta;

    private Recursos(String ruta){
        this.ruta = ruta;
    }

    public String getRuta() {
        return ruta;
    }    
}
