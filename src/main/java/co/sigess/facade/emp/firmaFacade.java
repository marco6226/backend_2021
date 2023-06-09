/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.sigess.facade.emp;

import co.sigess.entities.emp.firma;
import co.sigess.facade.com.AbstractFacade;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

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
}
