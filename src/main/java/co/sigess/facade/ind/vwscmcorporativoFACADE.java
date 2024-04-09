/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.sigess.facade.ind;

import co.sigess.entities.ind.vwscmcorporativo;
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
public class vwscmcorporativoFACADE extends AbstractFacade<vwscmcorporativo>{
    @PersistenceContext(unitName = "SIGESS_PU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
    
    public vwscmcorporativoFACADE(){
        super(vwscmcorporativo.class);
    }
    
    public List<vwscmcorporativo> findByEmpresaId(Integer empresaId){
        System.out.println(empresaId);
        Query q = this.em.createQuery("SELECT vwscmco FROM vwscmcorporativo vwscmco WHERE vwscmco.empresaId = ?1 ");
        q.setParameter(1, empresaId);
        List<vwscmcorporativo> list = (List<vwscmcorporativo>) q.getResultList();
        return list;
    }
}
