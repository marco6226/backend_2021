/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.sigess.facade.ind;

import co.sigess.entities.ind.Indicador;
import co.sigess.entities.ind.ModeloGrafica;
import co.sigess.facade.com.AbstractFacade;
import co.sigess.util.Util;
import java.util.Map;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author fmoreno
 */
@Stateless
public class IndicadorFacade extends AbstractFacade<Indicador>{

    @PersistenceContext(unitName = "SIGESS_PU")
    private EntityManager em;

    public IndicadorFacade() {
        super(Indicador.class);
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public ModeloGrafica consultarIndicador(String param) {
        Query q = this.em.createNativeQuery("SELECT * FROM ind.consultar_indicador(?1::json) ");
        q.setParameter(1, param);
        Object rs = q.getResultList().get(0);
        ModeloGrafica model = Util.fromJson(rs.toString(), ModeloGrafica.class);
        return model;
    }

    public void actualizarKpi(Integer empresaId, String param) {
        Query q = this.em.createNativeQuery("SELECT * FROM ind.actualizar_kpi(?1, ?2::json) ");
        q.setParameter(1, empresaId);
        q.setParameter(2, param);
        q.getResultList();
    }

}
