/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.sigess.facade.com;

import co.sigess.entities.com.Ciudad;
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
public class CiudadFacade extends AbstractFacade<Ciudad> {

    @PersistenceContext(unitName = "SIGESS_PU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public CiudadFacade() {
        super(Ciudad.class);
    }

    public List<Ciudad> findByDepartamento(Integer departamentoId) {
        Query query = this.em.createQuery("SELECT c from Ciudad c where c.departamento.id = :departamentoId");
        query.setParameter("departamentoId", departamentoId);
        List<Ciudad> list = (List<Ciudad>) query.getResultList();
        return list;
    }
}
