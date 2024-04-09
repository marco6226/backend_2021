/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.sigess.facade.emp;

import co.sigess.facade.com.AbstractFacade;
import co.sigess.entities.emp.GrupoEmpresarial;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author fmoreno
 */
@javax.ejb.Stateless
public class GrupoEmpresarialFacade extends AbstractFacade<GrupoEmpresarial> {

    @PersistenceContext(unitName = "SIGESS_PU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public GrupoEmpresarialFacade() {
        super(GrupoEmpresarial.class);
    }
    
}
