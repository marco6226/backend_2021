/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package co.sigess.facade.scm;


import co.sigess.entities.emp.Usuario;
import co.sigess.facade.com.AbstractFacade;
import co.sigess.facade.core.EmailFacade;
import co.sigess.facade.core.TipoMail;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.ejb.Asynchronous;
import javax.ejb.EJB;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.Context;

/**
 *
 * @author Luis
 */
@javax.ejb.Stateless
public class SaludLaboralFacade extends AbstractFacade<Usuario>{
    @Context
    private HttpServletRequest httpRequest;

    @EJB
    private EmailFacade emailFacade;

    @PersistenceContext(unitName = "SIGESS_PU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public SaludLaboralFacade() {
        super(Usuario.class);
    }

    public Usuario findByEmail(String email) {
     Query query = em.createQuery("SELECT u FROM Usuario u WHERE u.email = :email");
        query.setParameter("email", email);
        try {
            Usuario user = (Usuario) query.getSingleResult();
            return user;
        } catch (Exception ejbExc) {
            return null;
        }
    }
public List<Usuario> enviarCorreoCasosMedicos(List<String> emails, Map<String, String> parametros) throws Exception {
    List<Usuario> users = new ArrayList<>();
    for (String email : emails) {
        Usuario user = this.findByEmail(email);
        if (user != null) {
            users.add(user);
            enviarCorreoAsync(parametros, user);
        }
    }
    return users;
}
    @Asynchronous
    public void enviarCorreoAsync(Map<String, String> parametros, Usuario user) {
        try {
            String email = user.getEmail();
            // Lógica para enviar el correo
            emailFacade.sendEmail(parametros, TipoMail.SOLICITUD_DOCUMENTOS_SL, "Solicitud de documentos", email);
            System.out.println("Correo enviado exitosamente a: " + email);
        } catch (Exception e) {
            System.err.println("Error al enviar correo a: " + user.getEmail());
            e.printStackTrace();
        }
    }

public List<Usuario> enviarCorreoRechazo(List<String> emails, Map<String, String> parametros) throws Exception {
    List<Usuario> users = new ArrayList<>();
    for (String email : emails) {
        Usuario user = this.findByEmail(email);
        if (user != null) {
            emailFacade.sendEmail(parametros, TipoMail.RECHAZO_DOCUMENTOS_SOLICITADO, "Solicitud de documentos ", email);
            users.add(user);
        }
    }
    return users;
}
public List<Usuario> enviarCorreoRechazoSolicitante(List<String> emails, Map<String, String> parametros) throws Exception {
    List<Usuario> users = new ArrayList<>();
    for (String email : emails) {
        Usuario user = this.findByEmail(email);
        if (user != null) {
            emailFacade.sendEmail(parametros, TipoMail.RECHAZO_DOCUMENTOS_USUARIO, "Solicitud de documentos ", email);
            users.add(user);
        }
    }
    return users;
}

public List<Usuario> enviarCorreoDocumentosEnviadosFacade(List<String> emails, Map<String, String> parametros) throws Exception {
    List<Usuario> users = new ArrayList<>();
    for (String email : emails) {
        Usuario user = this.findByEmail(email);
        if (user != null) {
            emailFacade.sendEmail(parametros, TipoMail.DOCUMENTACION_ENVIADA, "Solicitud de documentos ", email);
            users.add(user);
        }
    }
    return users;
}





}
