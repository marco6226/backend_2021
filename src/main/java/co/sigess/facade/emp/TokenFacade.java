/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.sigess.facade.emp;

import co.sigess.entities.emp.TokenActivo;
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
public class TokenFacade extends AbstractFacade<TokenActivo> {

    @PersistenceContext(unitName = "SIGESS_PU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public TokenFacade() {
        super(TokenActivo.class);
    }

    public void eliminarTokensUsuario(Usuario user) {
        Query q = this.em.createQuery("DELETE FROM TokenActivo ta WHERE ta.usuario.id = ?1");
        q.setParameter(1, user.getId());
        q.executeUpdate();
    }

     public List getTokensByUser(Usuario user) {
        Query q = this.em.createQuery("Select ta FROM TokenActivo ta WHERE ta.usuario.id = ?1");
        q.setParameter(1, user.getId());
        return q.getResultList();
    }
}
