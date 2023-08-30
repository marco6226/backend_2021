/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.sigess.facade.ipr;

import co.sigess.entities.ipr.SubprocesoMatriz;
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
public class SubprocesoMatrizFacade extends AbstractFacade<SubprocesoMatriz> {
    
    @PersistenceContext(unitName = "SIGESS_PU")
    private EntityManager em;
    
    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
    
    public SubprocesoMatrizFacade(){
        super(SubprocesoMatriz.class);
    }
    
    public List findForEmp(int procesoId){
            
        Query q = this.em.createNativeQuery("SELECT sm.id, sm,nombre FROM ipr.subproceso_matriz sm WHERE fk_proceso_matriz_id = ?1 ");
        q.setParameter(1,procesoId);
        List<SubprocesoMatriz> list = (List<SubprocesoMatriz>) q.getResultList();
        return list;
    }
}
