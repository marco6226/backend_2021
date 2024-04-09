/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.sigess.facade.emp;

import co.sigess.entities.emp.firma;
import co.sigess.entities.scm.Pcl;
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
public class firmaFacade extends AbstractFacade<firma>{
    @PersistenceContext(unitName = "SIGESS_PU")
    private EntityManager em;
    
        @Override
    protected EntityManager getEntityManager() {
        return em;
    }
    
    public firmaFacade(){
        super(firma.class);
    }
    
    public firma findById(Long id){
        Query query = this.em.createQuery("SELECT DISTINCT f FROM firma f WHERE f.id = :id");
        query.setParameter("id", id);
        List<firma> list = (List<firma>) query.getResultList();
        return list.get(0);
    }
}
