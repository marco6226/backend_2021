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
