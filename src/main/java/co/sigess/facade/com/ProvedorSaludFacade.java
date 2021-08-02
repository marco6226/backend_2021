/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.sigess.facade.com;

import co.sigess.entities.com.Prepagadas;
import co.sigess.entities.com.Provsalud;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author leonardo
 */
@Stateless
public class ProvedorSaludFacade extends AbstractFacade<Provsalud>{
    
     @PersistenceContext(unitName = "SIGESS_PU")
    private EntityManager em;
     
    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public ProvedorSaludFacade() {
        super(Provsalud.class);
    }
}
