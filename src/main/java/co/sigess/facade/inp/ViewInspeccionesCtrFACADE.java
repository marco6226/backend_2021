/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.sigess.facade.inp;

import co.sigess.entities.inp.ViewInspeccionesCtr;
import co.sigess.facade.com.AbstractFacade;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author JULIO
 */
@Stateless
public class ViewInspeccionesCtrFACADE extends AbstractFacade<ViewInspeccionesCtr>{

    @PersistenceContext(unitName = "SIGESS_PU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
    
    public ViewInspeccionesCtrFACADE(){
        super(ViewInspeccionesCtr.class);
    }
    
    public List<ViewInspeccionesCtr> findByEmpresaAliada(long paramEmp){
        Query q = this.em.createQuery("SELECT vw FROM ViewInspeccionesCtr vw WHERE vw.empresaAliada=?1");
        q.setParameter(1, paramEmp);
        List<ViewInspeccionesCtr> res = (List<ViewInspeccionesCtr>) q.getResultList();
        return res;
    }
}
