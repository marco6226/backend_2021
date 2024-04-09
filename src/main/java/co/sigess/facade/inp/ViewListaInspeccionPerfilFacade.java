/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.sigess.facade.inp;

import co.sigess.entities.inp.ViewListaInspeccionPerfil;
import co.sigess.facade.com.AbstractFacade;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Usuario
 */
@Stateless
public class ViewListaInspeccionPerfilFacade extends AbstractFacade<ViewListaInspeccionPerfil>{
    @PersistenceContext(unitName = "SIGESS_PU")
    private EntityManager em;
    
    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
    
    public ViewListaInspeccionPerfilFacade() {
        super(ViewListaInspeccionPerfil.class);
    }
}
