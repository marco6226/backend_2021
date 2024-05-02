/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package co.sigess.facade.scm;

import co.sigess.entities.com.Mensaje;
import co.sigess.entities.com.TipoMensaje;
import co.sigess.entities.emp.Usuario;
import co.sigess.exceptions.UserMessageException;
import co.sigess.facade.com.AbstractFacade;
import co.sigess.facade.core.EmailFacade;
import co.sigess.facade.core.SMSFacade;
import co.sigess.facade.core.TipoMail;
import co.sigess.facade.emp.TokenFacade;
import co.sigess.facade.emp.UsuarioEmpresaFacade;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
    
public Usuario enviarCorreoCasosMedicos2(String[] emails) throws Exception {
    Usuario user = null;
    for (String email : emails) {
        user = this.findByEmail(email);
        if (user == null) {
            throw new UserMessageException(
                    new Mensaje(
                            "CORREO ELECTRÓNICO NO ENCONTRADO",
                            "El correo electrónico proporcionado no está registrado en nuestra base de datos. Por favor verifique e intente nuevamente.",
                            TipoMensaje.error,
                            Mensaje.COD_USUARIO_NO_VALIDO
                    )
            );
        }

        Query q = this.em.createNativeQuery("SELECT ?1::inet <<= ANY(ip_permitida) AS ip_valida FROM emp.usuario WHERE id = ?2");
        q.setParameter(1, httpRequest.getRemoteAddr());
        q.setParameter(2, user.getId());
        boolean ipValida = (boolean) q.getSingleResult();
        ipValida = true; // This line seems to be redundant, consider removing it

        if (!ipValida) {
            throw new UserMessageException(
                    new Mensaje(
                            "ACCESO NO PERMITIDO",
                            "Su dirección IP no se encuentra autorizada para realizar peticiones. "
                                    + "Por favor pongase en contacto con el administrador.",
                            TipoMensaje.warn,
                            Mensaje.COD_IP_NO_PERMITIDA
                    )
            );
        }

        Map<String, String> parametros = new HashMap<>();
        emailFacade.sendEmail(parametros, TipoMail.SOLICITUD_DOCUMENTOS_SL, "Solicitud de documentos", email);
    }
    return user; // Return the last user processed, or handle return value differently if needed
}

public List<Usuario> enviarCorreoCasosMedicos(List<String> emails) throws Exception {
    List<Usuario> users = new ArrayList<>();
    for (String email : emails) {
        Usuario user = this.findByEmail(email);
        if (user != null) {
            Map<String, String> parametros = new HashMap<>();
            //parametros.put(EmailFacade.PARAM_COD_RECUP, nuevoPasswd);
            //parametros.put(EmailFacade.PARAM_ENVIROMENT, host1);
            emailFacade.sendEmail(parametros, TipoMail.SOLICITUD_DOCUMENTOS_SL, "Solicitud de documentos ", email);
            users.add(user);
        }
    }
    return users;
}



}
