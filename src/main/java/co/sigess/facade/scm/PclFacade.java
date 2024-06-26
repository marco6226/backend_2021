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
        Query query = this.em.createNativeQuery("select *  from scm.pcl as pcl\n" +
"	right join scm.diagnosticos as diag\n" +
"	on CAST (pcl.diag AS BIGINT)=diag.id	\n" +
"	where pk_case=?1 and pcl.eliminado=false order by pcl.emision_pcl_fecha desc", Pcl.class);
        query.setParameter(1, caseId);
        List<Pcl> list = (List<Pcl>) query.getResultList();
        return list; 
    }
    
    public Pcl findById(Long id){
        Query query = this.em.createQuery("SELECT DISTINCT pc FROM Pcl pc WHERE PC.id = :id ORDER BY PC.id DESC");
        query.setParameter("id", id);
        List<Pcl> list = (List<Pcl>) query.getResultList();
        return list.get(0);
    }

    public Pcl update(Pcl pcl) throws Exception {

        pcl = super.edit(pcl);
        return pcl;

    }

}
