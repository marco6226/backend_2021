/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.sigess.facade.conf;

import co.sigess.entities.com.TipoMensaje;
import co.sigess.entities.conf.SistemaNivelRiesgo;
import co.sigess.exceptions.UserMessageException;
import co.sigess.facade.com.AbstractFacade;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author fmoreno
 */
@Stateless
public class SistemaNivelRiesgoFacade extends AbstractFacade<SistemaNivelRiesgo> {

    @PersistenceContext(unitName = "SIGESS_PU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public SistemaNivelRiesgoFacade() {
        super(SistemaNivelRiesgo.class);
    }

    public SistemaNivelRiesgo findDefault(Integer empresaId) {
        Query query = this.em.createQuery("SELECT snr FROM SistemaNivelRiesgo snr WHERE snr.empresa.id = ?1 AND snr.seleccionado = true");
        query.setParameter(1, empresaId);
        if (query.getResultList().isEmpty()) {
            throw new UserMessageException("No se han establecido los niveles de riesgo de la empresa", "", TipoMensaje.warn);
        }
        SistemaNivelRiesgo snr = (SistemaNivelRiesgo) query.getResultList().get(0);
        return snr;
    }

}
