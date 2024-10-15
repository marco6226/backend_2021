package co.sigess.facade.scm.mailSLFacade;

import co.sigess.facade.core.EmailFacade;
import java.util.Map;

public class EmailService {
    private EmailQueue emailQueue = new EmailQueue(); // Cola de correos
    private MailProducer producer; // Productor para agregar tareas
    private MailConsumer consumer; // Consumidor para procesar tareas
    private EmailFacade emailFacade;

    // Constructor que inicializa el productor y el consumidor
    public EmailService(EmailFacade emailFacade) {
        this.emailFacade = emailFacade;
        this.producer = new MailProducer(emailQueue); // Inicializa el productor
        this.consumer = new MailConsumer(emailQueue, emailFacade); // Inicializa el consumidor
        new Thread(consumer).start(); // Inicia el consumidor en un hilo separado
    }

    // Método para enviar correos
    public void enviarCorreo(String email, Map<String, String> parameters) {
        producer.sendEmail(email, parameters); // Encola la tarea de envío
    }
}
