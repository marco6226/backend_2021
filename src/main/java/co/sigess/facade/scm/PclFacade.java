/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.sigess.facade.scm;

import co.sigess.entities.scm.Pcl;
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
public class PclFacade extends AbstractFacade<Pcl> {

    @PersistenceContext(unitName = "SIGESS_PU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public PclFacade() {
        super(Pcl.class);
    }

    public Pcl create(Pcl pcl, Integer empresaId) throws Exception {

        return super.create(pcl);
    }
    
     public int eliminar(Long id) {

        //3117537464
        Query q = this.em.createNativeQuery("UPDATE scm.pcl  SET eliminado = true WHERE id = ?1");
        q.setParameter(1, id);
        int deleted = q.executeUpdate();
        return deleted;
    }

    public List<Pcl> findAllById(String caseId) {
        Query query = this.em.createNativeQuery("SELECT * FROM scm.pcl WHERE eliminado !=true", Pcl.class);
        //query.setParameter(1, Integer.parseInt(caseId));
        List<Pcl> list = (List<Pcl>) query.getResultList();
        return list; 
    }

    public Pcl update(Pcl pcl) throws Exception {

        pcl = super.edit(pcl);
        return pcl;

    }

}
