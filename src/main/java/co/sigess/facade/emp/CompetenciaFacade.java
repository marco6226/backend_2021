/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.sigess.facade.emp;

import co.sigess.entities.com.TipoMensaje;
import co.sigess.entities.emp.Competencia;
import co.sigess.exceptions.UserMessageException;
import co.sigess.facade.com.AbstractFacade;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author fmoreno
 */
@Stateless
public class CompetenciaFacade extends AbstractFacade<Competencia> {

    @PersistenceContext(unitName = "SIGESS_PU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public CompetenciaFacade() {
        super(Competencia.class);
    }

    public Competencia eliminar(Long competenciaId) throws Exception {
        Competencia comp = this.find(competenciaId);
        if (comp != null && comp.getCompetenciaList().isEmpty()) {
            this.remove(comp);
        } else {
            throw new UserMessageException("No es posible eliminar la competencia", "Existen referencias a ésta competencia", TipoMensaje.warn);
        }
        return comp;
    }

}
