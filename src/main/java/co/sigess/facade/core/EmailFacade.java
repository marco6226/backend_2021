/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.sigess.facade.core;

import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 *
 * @author fmoreno
 */
@Stateless
public class EmailFacade {

    public static final String PARAM_PLANT_PRINCIPAL = "P{contenido_plantilla}";
    public static final String PARAM_COD_RECUP = "P{codigo_recuperacion}";
    public static final String PARAM_MENSAJE = "P{mensaje}";
    public static final String PARAM_ACTIVIDAD = "P{actividad}";
    public static final String PARAM_ID = "P{id}";
    public static final String PARAM_FECHA_PROY = "P{fechaproyectada}";
    public static final String PARAM_FECHA_ENVIO = "P{fechaenvio}";
    public static final String PARAM_RESPONSABLE = "P{responsables}";
    public static final String PARAM_FECHA_REALIZADA = "P{fecharealizada}";
    public static final String PARAM_MOTIVO = "P{motivo}";
    public static final String PARAM_RIESGO_CRITICO = "P{riesgo}";
    public static final String PARAM_NOMBRE_INSPECCION = "P{nombreinspeccion}";
    public static final String PARAM_CRITICIDAD = "P{criticidad}";
    public static final String PARAM_AREA = "P{area}";
    public static final String PARAM_IDS = "P{ids}";
    public static final String PARAM_ECONOMICO = "P{economico}";
    public static final String PARAM_UBICACION = "P{ubicacion}";
    
    public static final String PARAM_COUNT = "P{count}";
    public static final String PARAM_NOMBRE = "P{nombre}";
    public static final String PARAM_NIT = "P{nit}";
    public static final String PARAM_ABIERTO = "P{abierto}";
    public static final String PARAM_SEGUIMIENTO = "P{seguimiento}";
    public static final String PARAM_VENCIDA = "P{vencida}";
    public static final String PARAM_HOST1 = "P{host1}";
    public static final String PARAM_HOST2 = "P{host2}";
    public static final String PARAM_INSP = "P{insp}";
    public static final String PARAM_RAZONSOCIAL =  "P{razonsocial}";

    @EJB
    private LoaderFacade loaderFacade;

    @Resource(name = "SIGESS_MAIL")
    private Session mailSession;

    public void sendEmail(Map<String, String> parametros, TipoMail tipoMail, String asunto, String destinatario) {
        try {
            Message message = new MimeMessage(mailSession);            
            message.setSubject("SIGESS - " + asunto + " de " + destinatario);            	
            message.addRecipients(Message.RecipientType.BCC, InternetAddress.parse("sistema@sigess.app"));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(destinatario));
            String contenido = loaderFacade.getPlantillaMail();
            String plantilla = null;
            switch (tipoMail) {
                case RECUPERACION_PASSWD:
                    plantilla = loaderFacade.getPlantillaMailRecPasswd();
                    break;
                case CAMBIO_PASSWD:
                    plantilla = loaderFacade.getPlantillaMailCambioPasswd();
                    break;
                case CREACION_USUARIO:
                    plantilla = loaderFacade.getPlantillaMailCreacionUsuario();
                    break;
                case NOTIFICACION_NUEVA:
                    plantilla = loaderFacade.getPlantillaMailNotificacionNueva();
                    break;
                case OBSERVACION_DENEGADA:
                    plantilla = loaderFacade.getPlantillaMailObservacionDenegada();
                    break;
                case RIESGOS_CRITICOS:
                    plantilla = loaderFacade.getPlantillaMailRiesgosCriticos();
                    break;
                case TAREA_SEMANAL:
                    plantilla = loaderFacade.getPlantillaMailTareaSemanal();
                    break;
                case ALIADO_NUEVO:
                    plantilla = loaderFacade.getPlantillaAliadoNuevo();
                    break;
                case ALIADO_ACTUALIZADO:
                    plantilla = loaderFacade.getPlantillaAliadoActualizado();
                    break;
                case ALIADO_ACTUALIZADO_CICLOCORTO:
                    plantilla = loaderFacade.getPlantillaAliadoCicloCorto();
                    break;
            }
            plantilla = replaceParameters(parametros, plantilla);
            contenido = contenido.replace(PARAM_PLANT_PRINCIPAL, plantilla);
            message.setContent(contenido, "text/html; charset=utf-8");
            Transport.send(message);
        } catch (MessagingException me) {
            Logger.getLogger(EmailFacade.class.getName()).log(Level.SEVERE, "", me);
        }
    }
    public void sendEmail(String msg, String asunto, String destinatario) {
        try {
            Message message = new MimeMessage(mailSession);
            message.setSubject(asunto);
            message.setRecipient(Message.RecipientType.TO, new InternetAddress(destinatario));
            message.setContent(msg, "text/html; charset=utf-8");
            Transport.send(message);
        } catch (MessagingException me) {
            Logger.getLogger(EmailFacade.class.getName()).log(Level.SEVERE, "", me);
        }
    }
    
    public void sendEmail(String destinatarios, TipoMail tipoMail, String asunto, Map<String, String> parametros) throws MessagingException{
        try {
            String contenido = loaderFacade.getPlantillaMail();
            String plantilla = null;
            
            switch(tipoMail){
                case REPORTE_ALIADOS:
                    plantilla = loaderFacade.getPlantillaReporteAliado();
                    break;
                case REPORTE_ALIADO_APROBADO:
                    plantilla = loaderFacade.getPlantillaReporteAliadoAprobado();
                    break;
                case REPORTE_ALIADO_RECHAZADO:
                    plantilla = loaderFacade.getPlantillaReporteAliadoRechazado();
                    break;
                case REPORTE_ALIADO_MODIFICADO:
                    plantilla = loaderFacade.getPlantillaReporteAliadoModificado();
                    break;
            }
            Message message = new MimeMessage(mailSession);
            message.setSubject("SIGESS - " + asunto);
            message.addRecipients(Message.RecipientType.BCC, InternetAddress.parse("sistema@sigess.app"));
            message.addRecipients(Message.RecipientType.TO, InternetAddress.parse(destinatarios));
            plantilla = replaceParameters(parametros, plantilla);
            contenido = contenido.replace(PARAM_PLANT_PRINCIPAL, plantilla);
            message.setContent(contenido, "text/html; charset=utf-8");
            Transport.send(message);
        } catch (MessagingException e) {
            Logger.getLogger(EmailFacade.class.getName()).log(Level.SEVERE, "", e);
            throw e;
        }
    }
    
    public void sendNotication(String msg, String asunto, String destinatario) {
        try {
            Message message = new MimeMessage(mailSession);
            message.setSubject(asunto);
            message.setRecipient(Message.RecipientType.TO, new InternetAddress(destinatario));
            message.setContent(msg, "text/html; charset=utf-8");
            Transport.send(message);
        } catch (MessagingException me) {
            Logger.getLogger(EmailFacade.class.getName()).log(Level.SEVERE, "", me);
        }
    }

    private String replaceParameters(Map<String, String> map, String content) {
        for (String key : map.keySet()) {
            content = content.replace(key, map.get(key));
        }
        return content;
    }
}
