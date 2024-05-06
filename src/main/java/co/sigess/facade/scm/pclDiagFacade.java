/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package co.sigess.facade.scm;

import co.sigess.entities.scm.Pcl;
import co.sigess.entities.scm.pclDIagEntity;
import co.sigess.facade.com.AbstractFacade;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author Luis
 */
@Stateless
public class pclDiagFacade extends AbstractFacade<pclDIagEntity> {
        
    @PersistenceContext(unitName = "SIGESS_PU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public pclDiagFacade() {
        super(pclDIagEntity.class);
    }
    
    public  List<Object> findPCLbyID(String id, Long pcl) {       
     //Query query = this.em.createQuery("SELECT viewpcl FROM pclDIagEntity viewpcl WHERE  viewpcl.id=:id ORDER BY pcl.id DESC");
     
     Query query = this.em.createQuery("SELECT viewpcl FROM pclDIagEntity viewpcl WHERE viewpcl.pkCase= ?1 AND viewpcl.pclid =?2 ORDER BY viewpcl.id DESC");
     //Query query = this.em.createQuery("SELECT viewpcl FROM pclDIagEntity viewpcl WHERE viewpcl.pkCase= ?1 ORDER BY viewpcl.id DESC");


        query.setParameter(1, id);
        query.setParameter(2, pcl);
        List<Object> list = query.getResultList();
        return list;
    }
    
}
