/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.sigess.facade.com;

import co.sigess.entities.com.Cie;
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
public class CieFacade extends AbstractFacade<Cie> {

    @PersistenceContext(unitName = "SIGESS_PU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public CieFacade() {
        super(Cie.class);
    }

    public List<Cie> buscar(String parametro) {
        Query q = this.em.createQuery("SELECT c FROM Cie c WHERE c.codigo LIKE ?1 OR c.nombre LIKE ?1");
        q.setParameter(1, "%" + parametro.toUpperCase() + "%");
        q.setMaxResults(10);
        List<Cie> list = q.getResultList();
        return list;
    }

}
