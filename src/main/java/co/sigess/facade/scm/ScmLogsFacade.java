/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.sigess.facade.scm;

import co.sigess.entities.scm.Recomendaciones;
import co.sigess.entities.scm.ScmLogs;
import co.sigess.facade.com.AbstractFacade;
import java.util.Date;
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
public class ScmLogsFacade extends AbstractFacade<ScmLogs>{
     @PersistenceContext(unitName = "SIGESS_PU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
    
    public ScmLogsFacade(){
        super(ScmLogs.class);
    }
    
    
     public ScmLogs create(ScmLogs logs, Integer empresaId) throws Exception {
      
        super.create(logs);
        return logs;
    }
       
         public List<ScmLogs> findAllById(String userDocument) {
        Query query = this.em.createNativeQuery("SELECT * FROM scm.scm_logs WHERE pk_case = ?1 and salud_laboral = false order by fecha_creacion desc",ScmLogs.class);
        query.setParameter(1, userDocument);
        List<ScmLogs> list = (List<ScmLogs>) query.getResultList();
        return list;
    }  
         
      
     
}
