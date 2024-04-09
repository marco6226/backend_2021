/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.sigess.facade.ipr;

import co.sigess.entities.ipr.ParticipanteIpecr;
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
public class ParticipanteIpecrFacade extends AbstractFacade<ParticipanteIpecr> {

    @PersistenceContext(unitName = "SIGESS_PU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public ParticipanteIpecrFacade() {
        super(ParticipanteIpecr.class);
    }

    public List<ParticipanteIpecr> findAllByIpecr(Integer id) {
        Query q = this.em.createQuery("SELECT p FROM ParticipanteIpecr p WHERE p.ipecr.id = ?1");
        q.setParameter(1, id);
        List<ParticipanteIpecr> list = (List<ParticipanteIpecr>) q.getResultList();
        return list;
    }

}
