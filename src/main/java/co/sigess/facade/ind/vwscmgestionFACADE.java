/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.sigess.facade.ind;

import co.sigess.entities.ind.vwscmgestion;
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
public class vwscmgestionFACADE extends AbstractFacade<vwscmgestion>{
    @PersistenceContext(unitName = "SIGESS_PU")
    private EntityManager em;
    
        @Override
    protected EntityManager getEntityManager() {
        return em;
    }
    
    public vwscmgestionFACADE(){
        super(vwscmgestion.class);
    }

    public List<vwscmgestion>findByEmpresaId(Integer empresaId){
        Query q = this.em.createQuery("SELECT vwsgest FROM vwscmgestion vwsgest WHERE vwsgest.empresaId = ?1 ");
        q.setParameter(1, empresaId);
        List<vwscmgestion> list = (List<vwscmgestion>) q.getResultList();
        return list;
    }
}
