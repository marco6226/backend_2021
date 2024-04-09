/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.sigess.facade.emp;

import co.sigess.entities.emp.ConfiguracionJornada;
import co.sigess.entities.emp.Jornada;
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
public class ConfiguracionJornadaFacade extends AbstractFacade<ConfiguracionJornada> {

    @PersistenceContext(unitName = "SIGESS_PU")
    private EntityManager em;

    @EJB
    private JornadaFacade jornadaFacade;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public ConfiguracionJornadaFacade() {
        super(ConfiguracionJornada.class);
    }

    @Override
    public ConfiguracionJornada create(ConfiguracionJornada confJornada) throws Exception {
        confJornada = super.create(confJornada);
        for (Jornada jornada : confJornada.getJornadaList()) {
            jornada.setConfiguracionJornada(confJornada);
            this.jornadaFacade.create(jornada);
        }
        return confJornada;
    }

    @Override
    public ConfiguracionJornada edit(ConfiguracionJornada confJornada) throws Exception {
        confJornada = super.edit(confJornada);
        for (Jornada jornada : confJornada.getJornadaList()) {
            jornada.setConfiguracionJornada(confJornada);
            this.jornadaFacade.edit(jornada);
        }
        return confJornada;
    }

}
