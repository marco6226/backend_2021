/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.sigess.facade.auc;

import co.sigess.entities.auc.Tarjeta;
import co.sigess.facade.com.AbstractFacade;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author fmoreno
 */
@Stateless
public class TarjetaFacade extends AbstractFacade<Tarjeta> {

    @PersistenceContext(unitName = "SIGESS_PU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public TarjetaFacade() {
        super(Tarjeta.class);
    }

//    public List<Tarjeta> findByEmpresa(Integer empresaId) {
//        Query query = this.em.createQuery("SELECT t FROM Tarjeta t WHERE t.empresa.id = ?1");
//        query.setParameter(1, empresaId);
//    }
    
}
