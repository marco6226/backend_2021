/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.sigess.facade.scm;

import javax.ejb.Stateless;
import co.sigess.entities.scm.SistemaAfectado;
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
public class SistemaAfectadoFacade extends AbstractFacade<SistemaAfectado>{
       @PersistenceContext(unitName = "SIGESS_PU")
    private EntityManager em;

    @Override 
    protected EntityManager getEntityManager() {
        return em;
    }
      public SistemaAfectadoFacade(){
        super(SistemaAfectado.class);
    }
        
      
          public List<SistemaAfectado> findAllById() {
            Query query = this.em.createNativeQuery("SELECT * FROM scm.diagnosticos",SistemaAfectado.class);
        List<SistemaAfectado> list = (List<SistemaAfectado>) query.getResultList();
        return list;
    }
    
}
