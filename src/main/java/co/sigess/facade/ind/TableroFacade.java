/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.sigess.facade.ind;

import co.sigess.entities.ind.Tablero;
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
public class TableroFacade extends AbstractFacade<Tablero>{

    @PersistenceContext(unitName = "SIGESS_PU")
    private EntityManager em;

    public TableroFacade() {
        super(Tablero.class);
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public List<Tablero> findByUser(Integer usuarioId) {
        Query q = this.em.createQuery("SELECT t FROM Tablero t JOIN t.accesoTableroList acc WHERE acc.usuarioId = ?1 AND acc.consultar = true");
        q.setParameter(1, usuarioId);
        List<Tablero> list = q.getResultList();
        return list;
    }

}
