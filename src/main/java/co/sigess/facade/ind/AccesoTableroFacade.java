/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.sigess.facade.ind;

import co.sigess.entities.ind.AccesoTablero;
import co.sigess.facade.com.AbstractFacade;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author fmoreno
 */
@Stateless
public class AccesoTableroFacade extends AbstractFacade<AccesoTablero>{

    @PersistenceContext(unitName = "SIGESS_PU")
    private EntityManager em;

    public AccesoTableroFacade() {
        super(AccesoTablero.class);
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }


}
