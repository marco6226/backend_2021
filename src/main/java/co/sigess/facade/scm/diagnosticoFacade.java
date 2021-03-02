/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.sigess.facade.scm;

import javax.ejb.Stateless;
import co.sigess.entities.scm.Diagnosticos;
import co.sigess.facade.com.AbstractFacade;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
/**
 *
 * @author leonardo
 */
@Stateless
public class diagnosticoFacade extends AbstractFacade<Diagnosticos> {
    @PersistenceContext(unitName = "SIGESS_PU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
    
    
        public diagnosticoFacade(){
        super(Diagnosticos.class);
    }
        
        public Diagnosticos create(Diagnosticos diag, Integer empresaId) throws Exception {
        

        super.create(diag);
        return diag;
    }
       
         public List<Diagnosticos> findAllById(String caseId) {
            Query query = this.em.createNativeQuery("SELECT * FROM scm.diagnosticos WHERE pk_case = ?1",Diagnosticos.class);
        query.setParameter(1, caseId);
        List<Diagnosticos> list = (List<Diagnosticos>) query.getResultList();
        return list;
    }
     
        
        
}