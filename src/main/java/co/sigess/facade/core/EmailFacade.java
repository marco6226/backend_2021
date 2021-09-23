/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.sigess.facade.core;

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
    public static final String PARAM_RESPONSABLE = "P{responsables}";
    @EJB
    private LoaderFacade loaderFacade;

    @Resource(name = "SIGESS_MAIL")
    private Session mailSession;

    public void sendEmail(Map<String, String> parametros, TipoMail tipoMail, String asunto, String destinatario) {
        try {
            Message message = new MimeMessage(mailSession);
            message.setSubject("SIGESS - " + asunto + " de " + destinatario);            	
            message.addRecipients(Message.RecipientType.CC, InternetAddress.parse("sistema@sigess.app"));
            message.setRecipient(Message.RecipientType.TO, new InternetAddress(destinatario));
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
