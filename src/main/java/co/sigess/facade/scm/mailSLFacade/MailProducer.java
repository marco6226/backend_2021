/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package co.sigess.facade.scm.mailSLFacade;

import java.util.Map;

/**
 *
 * @author Luis
 */
public class MailProducer {
    private EmailQueue emailQueue;

    public MailProducer(EmailQueue emailQueue) {
        this.emailQueue = emailQueue;
    }

    public void sendEmail(String email, Map<String, String> parameters) {
        EmailTask task = new EmailTask(email, parameters);
        emailQueue.enqueue(task);
    }
}

