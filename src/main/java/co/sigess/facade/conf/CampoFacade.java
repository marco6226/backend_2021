/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.sigess.facade.conf;

import co.sigess.entities.conf.Campo;
import co.sigess.entities.conf.Formulario;
import co.sigess.facade.com.AbstractFacade;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author fmoreno
 */
@Stateless
public class CampoFacade extends AbstractFacade<Campo> {

    @PersistenceContext(unitName = "SIGESS_PU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public CampoFacade() {
        super(Campo.class);
    }

    public void removeByFormulario(Formulario formulario) {
        Query q = this.em.createQuery("DELETE FROM Campo c WHERE c.formulario.id = ?1");
        q.setParameter(1, formulario.getId());
        q.executeUpdate();
    }
    
}
