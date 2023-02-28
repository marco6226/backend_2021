/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.sigess.facade.ind;

import co.sigess.entities.emp.Hht;
import co.sigess.entities.ind.caracterizacionview;
import co.sigess.facade.com.AbstractFacade;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author Usuario
 */
@Stateless
public class caracterizacionviewFACADE extends AbstractFacade<caracterizacionview> {
    
        @PersistenceContext(unitName = "SIGESS_PU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
    
    public caracterizacionviewFACADE(){
        super(caracterizacionview.class);
    }
    
    public List<caracterizacionview> findByalll() {
        Query q = this.em.createQuery("SELECT carview FROM caracterizacionview carview ORDER BY carview.padrenombre");
        List<caracterizacionview> list = (List<caracterizacionview>) q.getResultList();
        return list;
    }
}
