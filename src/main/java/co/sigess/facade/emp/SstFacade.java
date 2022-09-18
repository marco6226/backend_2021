/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.sigess.facade.emp;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import co.sigess.entities.emp.Sst;
import co.sigess.facade.com.AbstractFacade;
import java.util.List;
import javax.persistence.Query;

/**
 *
 * @author John Rueda
 */
@Stateless
public class SstFacade extends AbstractFacade<Sst> {
    
    @PersistenceContext(unitName = "SIGESS_PU")
    private EntityManager em;
    
    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
    
    public SstFacade() {
        super(Sst.class);
    }
    
    public Sst adicionarSST(Sst sst) throws Exception {
        sst = super.create(sst);
        return sst;
    }
    
    public Sst updateSST(Sst sst) throws Exception {
        sst = super.edit(sst);
        return sst;
    }
    
    public List<Sst> findByAliadoId(Integer aliadoId) {
        
        Query query = this.em.createQuery("SELECT st from Sst st where st.id_empresa = :aliadoId");
        query.setParameter("aliadoId", aliadoId);
        List<Sst> sst = (List<Sst>) query.getResultList();
        return sst;        
    }
    
    public void deleteByAliadoId(Sst sst)throws Exception {
        super.remove(sst);      
    }
    
}
