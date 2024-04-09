/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.sigess.facade.scm;

import co.sigess.entities.scm.Sve;
import co.sigess.facade.com.AbstractFacade;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author leonardo
 */
@Stateless
public class SveFacade extends AbstractFacade<Sve> {
   @PersistenceContext(unitName = "SIGESS_PU")
    private EntityManager em;

    @Override 
    protected EntityManager getEntityManager() {
        return em;
    }
    
    
      public SveFacade(){
        super(Sve.class);
    }
        
      
          public List<Sve> findAllById() {
            Query query = this.em.createNativeQuery("SELECT * FROM scm.sve",Sve.class);
        List<Sve> list = (List<Sve>) query.getResultList();
        return list;
    }
    
}