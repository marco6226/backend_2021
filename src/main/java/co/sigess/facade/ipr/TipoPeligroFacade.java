/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.sigess.facade.ipr;

import co.sigess.entities.emp.Empresa;
import co.sigess.entities.ipr.TipoPeligro;
import co.sigess.facade.com.AbstractFacade;
import co.sigess.restful.ipr.TipoPeligroREST;
import co.sigess.util.Util;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.ws.rs.core.Response;

/**
 *
 * @author fmoreno
 */
@Stateless
public class TipoPeligroFacade extends AbstractFacade<TipoPeligro> {

    @PersistenceContext(unitName = "SIGESS_PU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public TipoPeligroFacade() {
        super(TipoPeligro.class);
    }
    
    public List findForEmp(int empresaId){
        Query q1 = em.createNativeQuery("SELECT e.id_empresa_aliada from emp.empresa e where e.id = ?1");
        q1.setParameter(1, empresaId);
        List list1 = q1.getResultList();

        if(list1.get(0) != null){
            empresaId=Integer.parseInt(list1.get(0).toString());
        }
            
        Query q = this.em.createNativeQuery("SELECT tp.id, tp,nombre FROM ipr.tipo_peligro tp WHERE fk_empresa_id = ?1 ");
        q.setParameter(1,empresaId);
        List<TipoPeligro> list = (List<TipoPeligro>) q.getResultList();
        return list;
    }
    
}
