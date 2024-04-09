/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.sigess.facade.ind;

import co.sigess.entities.ind.IndicadorAusentismo;
import co.sigess.entities.ind.ModeloIndicador;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.Tuple;

/**
 *
 * @author fmoreno
 */
@Stateless
public class IndicadorAusentismoFacade {

    @PersistenceContext(unitName = "SIGESS_PU")
    private EntityManager em;

    public List<IndicadorAusentismo> findByAnio(Integer empresaId, Integer anio, Integer causaAusentismoId) {
        Query query = this.em.createNamedQuery("IndicadorAusentismoNativeQuery");        
        query.setParameter(1, empresaId);
        query.setParameter(2, anio);
        query.setParameter(3, causaAusentismoId);
        List<IndicadorAusentismo> list = query.getResultList();
        return list;
    }

    public List<ModeloIndicador> find(Integer empresaId, String rango) {
        Query query = this.em.createNamedQuery("CaracterizacionAusentismoNativeQuery");        
        query.setParameter(1, empresaId);
        query.setParameter(2, rango);
        List<ModeloIndicador> list = query.getResultList();
        return list;
    }

}
