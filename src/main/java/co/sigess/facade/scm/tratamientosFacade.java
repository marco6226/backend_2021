/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.sigess.facade.scm;

import co.sigess.entities.scm.Diagnosticos;
import co.sigess.entities.scm.Tratamientos;
import co.sigess.facade.com.AbstractFacade;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author leonardo
 */
@Stateless
public class tratamientosFacade extends AbstractFacade<Tratamientos> {

    @PersistenceContext(unitName = "SIGESS_PU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public tratamientosFacade() {
        super(Tratamientos.class);
    }

    public Tratamientos create(Tratamientos trat, Integer empresaId) throws Exception {
        super.create(trat);
        return trat;

    }

    public List<Tratamientos> findAllById(String caseId) {
        Query query = this.em.createNativeQuery("SELECT * FROM scm.tratamientos WHERE pk_case = ?1 and eliminado != true order by fecha desc", Tratamientos.class);
        query.setParameter(1, Integer.parseInt(caseId));
        List<Tratamientos> list = (List<Tratamientos>) query.getResultList();
        return list;
    }

    
      public Tratamientos update(Tratamientos trat) throws Exception {
        
          trat = super.edit(trat);
        return trat;
        
    }
    
}
