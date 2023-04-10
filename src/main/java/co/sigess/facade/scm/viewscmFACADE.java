/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.sigess.facade.scm;

import co.sigess.entities.scm.viewscm;
import co.sigess.facade.com.AbstractFacade;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author Usuario
 */
@Stateless
public class viewscmFACADE extends AbstractFacade<viewscm>{
    @PersistenceContext(unitName = "SIGESS_PU")
    private EntityManager em;
    
    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
    
    public viewscmFACADE(){
        super(viewscm.class);
    }
    
    public List<viewscm>findByEmpresaId(Integer empresaId){
        Query q = this.em.createQuery("SELECT vwscm FROM viewscm vwscm WHERE vwscm.empresaId = ?1 ");
        q.setParameter(1, empresaId);
        List<viewscm> list = (List<viewscm>) q.getResultList();
        return list;
    }
}
