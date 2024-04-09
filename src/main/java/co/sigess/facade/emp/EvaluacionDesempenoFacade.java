/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.sigess.facade.emp;

import co.sigess.entities.com.TipoMensaje;
import co.sigess.entities.emp.CalificacionDesempeno;
import co.sigess.entities.emp.EvaluacionDesempeno;
import co.sigess.exceptions.UserMessageException;
import co.sigess.facade.com.AbstractFacade;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author fmoreno
 */
@Stateless
public class EvaluacionDesempenoFacade extends AbstractFacade<EvaluacionDesempeno> {

    @PersistenceContext(unitName = "SIGESS_PU")
    private EntityManager em;

    @EJB
    private CalificacionDesempenoFacade calificacionDesempenoFacade;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public EvaluacionDesempenoFacade() {
        super(EvaluacionDesempeno.class);
    }

    @Override
    public EvaluacionDesempeno create(EvaluacionDesempeno entity) throws Exception {
        if (entity.getCalificacionDesempenoList() == null || entity.getCalificacionDesempenoList().isEmpty()) {
            throw new UserMessageException("Evaluación no válida", "La evaluación que intenta crear no contiene respuestas", TipoMensaje.error);
        }
        for (CalificacionDesempeno calfDesemp : entity.getCalificacionDesempenoList()) {
            if (calfDesemp.getCompetencia() == null) {
                throw new UserMessageException("Calificación no válida", "Una de las calificaciones no tiene relacionada la competencia", TipoMensaje.error);
            }
        }
        entity = super.create(entity); //To change body of generated methods, choose Tools | Templates.
        for (CalificacionDesempeno calfDesemp : entity.getCalificacionDesempenoList()) {
            calfDesemp.setEvaluacionDesempeno(entity);
            this.calificacionDesempenoFacade.create(calfDesemp);
        }
        return entity;
    }

    @Override
    public EvaluacionDesempeno edit(EvaluacionDesempeno entity) throws Exception {

        for (CalificacionDesempeno calfDesemp : entity.getCalificacionDesempenoList()) {
            if (calfDesemp.getCompetencia() == null) {
                throw new UserMessageException("Calificación no válida", "Una de las calificaciones no tiene relacionada la competencia", TipoMensaje.error);
            }
            if (calfDesemp.getId() == null) {
                throw new UserMessageException("Calificación no válida", "Una de las calificaciones no tiene relacionado el id", TipoMensaje.error);
            }
            calfDesemp.setEvaluacionDesempeno(entity);
            this.calificacionDesempenoFacade.edit(calfDesemp);
        }
        return super.edit(entity);
    }

}
