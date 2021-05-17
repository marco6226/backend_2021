/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.sigess.facade.scm;

import co.sigess.facade.com.AbstractFacade;
import co.sigess.entities.scm.PlanAccion;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author leonardo
 */
@Stateless
public class planAccionFacade extends AbstractFacade<PlanAccion> {

   
    @PersistenceContext(unitName = "SIGESS_PU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
    
    public planAccionFacade(){
        super(PlanAccion.class);
    }
    
    @Override
    public PlanAccion create (PlanAccion planaccion) throws Exception{
           
        return super.create(planaccion);
    }
    

}
