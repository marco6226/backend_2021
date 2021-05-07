/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.sigess.facade.scm;

import javax.ejb.Stateless;
import co.sigess.entities.scm.PlanAccion;
import co.sigess.facade.com.AbstractFacade;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author leonardo
 */
@Stateless
public class AccionPlanFacade extends AbstractFacade<PlanAccion> {
    
    @PersistenceContext(unitName = "SIGESS_PU")
    private EntityManager em;
    
    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
    public AccionPlanFacade(){
        super(PlanAccion.class);
    }
    
    
        
     public PlanAccion create(PlanAccion planaccion, Integer empresaId) throws Exception {
        
        super.create(planaccion);
        return planaccion;
    }
}
