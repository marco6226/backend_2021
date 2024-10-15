/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package co.sigess.facade.scm.mailSLFacade;

import co.sigess.facade.core.EmailFacade;
import co.sigess.facade.core.TipoMail;
import co.sigess.facade.scm.SaludLaboralFacade;
import java.util.Map;

/**
 *
 * @author Luis
 */
public class MailConsumer implements Runnable {
    private EmailQueue emailQueue;
    private EmailFacade slFacade; // Tu clase EmailFacade

    public MailConsumer(EmailQueue emailQueue, EmailFacade emailFacade) {
        this.emailQueue = emailQueue;
        this.slFacade = emailFacade;
    }


    @Override
    public void run() {
        while (true) {
            try {
                EmailTask task = emailQueue.dequeue();
                enviarCorreo(task.getEmail(), task.getParameters());
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            }
        }
    }

    private void enviarCorreo(String email, Map<String, String> parameters) {
        try {
            // Aquí llamas a tu método de envío de correo
            slFacade.sendEmail(parameters, TipoMail.SOLICITUD_DOCUMENTOS_SL, "Solicitud de documentos", email);
            System.out.println("Correo enviado exitosamente a: " + email);
        } catch (Exception e) {
            System.err.println("Error al enviar correo a: " + email);
            e.printStackTrace();
        }
    }
}

