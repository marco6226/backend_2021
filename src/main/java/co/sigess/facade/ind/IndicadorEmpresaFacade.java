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
public class IndicadorEmpresaFacade {

    @PersistenceContext(unitName = "SIGESS_PU")
    private EntityManager em;

    public List<ModeloIndicador> obtenerEvaluacionDesempeno(Integer empresaId, String rango, Integer empleadoId) {
        Query query = this.em.createNamedQuery("EvaluacionDesempenoNativeQuery");
        query.setParameter(1, empresaId);
        query.setParameter(2, rango);
        query.setParameter(3, empleadoId == null ? java.sql.Types.NULL : empleadoId);
        List<ModeloIndicador> list = query.getResultList();
        return list;
    }

}
