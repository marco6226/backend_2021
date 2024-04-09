/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.sigess.facade.emp;

import co.sigess.entities.emp.EventoLog;
import co.sigess.entities.emp.Usuario;
import co.sigess.facade.com.AbstractFacade;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author fmoreno
 */
@Stateless
public class EventoLogFacade extends AbstractFacade<EventoLog> {

    @PersistenceContext(unitName = "SIGESS_PU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public EventoLogFacade() {
        super(EventoLog.class);
    }

    public List<EventoLog> consultarHistoriaLogin(Usuario usuario) {
        Query q = this.em.createQuery("SELECT evt FROM EventoLog evt WHERE evt.usuarioEmail = ?1 AND evt.metodo = 'AuthenticationREST.authenticateUser' ORDER BY evt.id DESC");
        q.setParameter(1, usuario.getEmail());
        q.setMaxResults(10);
        List<EventoLog> list = q.getResultList();
        return list;
    }

}
