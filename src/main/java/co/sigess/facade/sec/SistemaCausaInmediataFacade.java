/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.sigess.facade.sec;

import co.sigess.entities.sec.SistemaCausaInmediata;
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
public class SistemaCausaInmediataFacade extends AbstractFacade<SistemaCausaInmediata> {

    @PersistenceContext(unitName = "SIGESS_PU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public SistemaCausaInmediataFacade() {
        super(SistemaCausaInmediata.class);
    }

    public List<SistemaCausaInmediata> findByEmpresa(Integer empresaId) {
        Query query = this.em.createQuery("SELECT s FROM SistemaCausaInmediata s WHERE s.empresaId = ?1");
        query.setParameter(1, empresaId);
        List<SistemaCausaInmediata> list = (List<SistemaCausaInmediata>) query.getResultList();
        return list;

    }

    public SistemaCausaInmediata findDefault(Integer empresaId) {
        Query query = this.em.createQuery("SELECT s FROM SistemaCausaInmediata s WHERE s.empresaId = ?1");
        query.setParameter(1, empresaId);
        query.setMaxResults(1);
        SistemaCausaInmediata scr = query.getResultList().isEmpty() ? new SistemaCausaInmediata() : (SistemaCausaInmediata) query.getResultList().get(0);
        return scr;
    }
    
    public SistemaCausaInmediata findDefault2(){
        Query query = this.em.createQuery("SELECT s FROM SistemaCausaInmediata s WHERE s.empresaId=2");
        query.setMaxResults(1);
        SistemaCausaInmediata sci = query.getResultList().isEmpty() ? new SistemaCausaInmediata() : (SistemaCausaInmediata) query.getResultList().get(0);
        return sci;
    }

}
