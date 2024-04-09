/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.sigess.facade.com;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author fmoreno
 */
@Stateless
public class OfflineServiceFacade {

    @PersistenceContext(unitName = "SIGESS_PU")
    private EntityManager em;

    public Object cargarDatos(Integer empresaId, Integer usuarioId) {
        Query query = this.em.createNativeQuery("SELECT * FROM emp.consultar_datos_offline(1?, 2?)");
        query.setParameter(1, empresaId);
        query.setParameter(2, usuarioId);

        Object map = query.getResultList();
        return map;
    }

}
