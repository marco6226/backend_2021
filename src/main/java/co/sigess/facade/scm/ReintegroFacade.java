/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package co.sigess.facade.scm;

import co.sigess.entities.scm.Pcl;
import co.sigess.entities.scm.Reintegro;
import co.sigess.facade.com.AbstractFacade;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
/**
 *
 * @author John Rueda
 */
@Stateless
public class ReintegroFacade extends AbstractFacade<Reintegro> {
     @PersistenceContext(unitName = "SIGESS_PU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public ReintegroFacade() {
        super(Reintegro.class);
    }

    public Reintegro create(Reintegro reintegro, Integer empresaId) throws Exception {

        return super.create(reintegro);
    }

    public List<Reintegro> getAllByCasoID(String parametro) {
        Query q = this.em.createNativeQuery("SELECT * FROM scm.reintegros where pk_case = ?1",Reintegro.class);
        q.setParameter(1, parametro);
        List<Reintegro> list = (List<Reintegro>) q.getResultList();
        return list;
        //return q.getResultList();
    }

}
