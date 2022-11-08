/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.sigess.facade.ind;

import co.sigess.facade.com.AbstractFacade;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import co.sigess.entities.ind.ReporteATView;
import co.sigess.entities.sec.Desviacion;
import java.util.List;
import javax.persistence.Query;

/**
 *
 * @author Usuario
 */
@Stateless
public class ReporteATViewFacade extends AbstractFacade<ReporteATView>{
    
    @PersistenceContext(unitName = "SIGESS_PU")
    private EntityManager em;
    
    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
    
    public ReporteATViewFacade(){
        super(ReporteATView.class);
    }
    
   /* public List<Desviacion> findByEmpresa(Integer empresaIdRequestContext) {
        Query query = this.em.createQuery("SELECT r FROM ReporteATView r WHERE r.empresaId = ?1");
        query.setParameter(1, empresaIdRequestContext);
        List<Desviacion> list = (List<Desviacion>) query.getResultList();
        return list;
    }*/
}
