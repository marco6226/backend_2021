/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.sigess.facade.emp;

import co.sigess.entities.emp.TipoArea;
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
public class TipoAreaFacade extends AbstractFacade<TipoArea> {

    @PersistenceContext(unitName = "SIGESS_PU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public TipoAreaFacade() {
        super(TipoArea.class);
    }

    public List<TipoArea> findAllByEmpresa(Integer empresaId) {
        Query query = this.em.createQuery("SELECT ta FROM TipoArea ta WHERE ta.empresa.id = ?1");
        query.setParameter(1, empresaId);
        List<TipoArea> list = query.getResultList();
        return list;
    }
    
    
    
}
