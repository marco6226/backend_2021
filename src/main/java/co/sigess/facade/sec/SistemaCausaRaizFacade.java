/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.sigess.facade.sec;

import co.sigess.entities.sec.SistemaCausaRaiz;
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
public class SistemaCausaRaizFacade extends AbstractFacade<SistemaCausaRaiz> {

    @PersistenceContext(unitName = "SIGESS_PU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public SistemaCausaRaizFacade() {
        super(SistemaCausaRaiz.class);
    }

    public List<SistemaCausaRaiz> findByEmpresa(Integer empresaId) {
        Query query = this.em.createQuery("SELECT s FROM SistemaCausaRaiz s WHERE s.empresaId = ?1 order by 1");
        query.setParameter(1, empresaId);
        List<SistemaCausaRaiz> list = (List<SistemaCausaRaiz>) query.getResultList();
        return list;

    }

    public SistemaCausaRaiz findDefault(Integer empresaId) {
        Query query = this.em.createQuery("SELECT s FROM SistemaCausaRaiz s WHERE s.empresaId = ?1 order by 1");
        query.setParameter(1, empresaId);
        query.setMaxResults(1);
        SistemaCausaRaiz scr = query.getResultList().isEmpty() ? new SistemaCausaRaiz() : (SistemaCausaRaiz) query.getResultList().get(0);
        return scr;
    }

}
