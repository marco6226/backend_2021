/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.sigess.facade.sec;

import co.sigess.entities.sec.SistemaCausaAdministrativa;
import co.sigess.facade.com.AbstractFacade;
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
public class SistemaCausaAdministrativaFacade extends AbstractFacade<SistemaCausaAdministrativa> {

    @PersistenceContext(unitName = "SIGESS_PU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public SistemaCausaAdministrativaFacade() {
        super(SistemaCausaAdministrativa.class);
    }

    public SistemaCausaAdministrativa getDefault(Integer empresaIdRequestContext) {
        Query q = this.em.createQuery("SELECT c FROM SistemaCausaAdministrativa c WHERE c.empresaId = ?1 AND c.selected = true order by 1");
        q.setParameter(1, empresaIdRequestContext);
        List<SistemaCausaAdministrativa> list = q.getResultList();
        return list == null || list.isEmpty() ? null : list.get(0);
    }

}
