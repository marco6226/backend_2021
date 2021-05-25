/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.sigess.facade.scm;

import co.sigess.entities.scm.Pcl;
import co.sigess.facade.com.AbstractFacade;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author leonardo
 */
@Stateless
public class PclFacade extends AbstractFacade<Pcl> {

    @PersistenceContext(unitName = "SIGESS_PU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public PclFacade() {
        super(Pcl.class);
    }
    
    
      public Pcl create(Pcl pcl, Integer empresaId) throws Exception {
    
        return  super.create(pcl);
    }
     
    

}
