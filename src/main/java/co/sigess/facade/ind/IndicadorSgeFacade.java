/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.sigess.facade.ind;

import co.sigess.entities.ind.IndicadorSge;
import co.sigess.facade.com.AbstractFacade;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author fmoreno
 */
@Stateless
public class IndicadorSgeFacade extends AbstractFacade<IndicadorSge> {

    @PersistenceContext(unitName = "SIGESS_PU")
    private EntityManager em;

    public IndicadorSgeFacade() {
        super(IndicadorSge.class);
    }

    @Override
    protected EntityManager getEntityManager() {
        return this.em;
    }

    @Override
    public void remove(IndicadorSge entity) {
        throw new UnsupportedOperationException("Operación no permitida en una entidad de sólo lectura");
    }

    @Override
    public IndicadorSge edit(IndicadorSge entity) throws Exception {
        throw new UnsupportedOperationException("Operación no permitida en una entidad de sólo lectura");
    }

    @Override
    public IndicadorSge create(IndicadorSge entity) throws Exception {
        throw new UnsupportedOperationException("Operación no permitida en una entidad de sólo lectura");
    }

}
