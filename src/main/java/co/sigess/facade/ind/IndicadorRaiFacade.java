/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.sigess.facade.ind;

import co.sigess.entities.ind.ModeloGrafica;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author fmoreno
 */
@Stateless
public class IndicadorRaiFacade {

    @PersistenceContext(unitName = "SIGESS_PU")
    private EntityManager em;

    public List<ModeloGrafica> find(Integer empresaId, String tipo, String rangos) {
        Query query = this.em.createNamedQuery("IndicadorRaiNativeQuery");        
        query.setParameter(1, empresaId);
        query.setParameter(2, tipo);
        query.setParameter(3, rangos);
        List<ModeloGrafica> list = (List<ModeloGrafica>)query.getResultList();
        return list;
    }
}
