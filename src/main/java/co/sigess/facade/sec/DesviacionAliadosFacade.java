/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.sigess.facade.sec;

import co.sigess.entities.sec.DesviacionAliados;
import co.sigess.facade.com.AbstractFacade;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author JULIO
 */
@Stateless
public class DesviacionAliadosFacade extends AbstractFacade<DesviacionAliados>{
    @PersistenceContext(unitName = "SIGESS_PU")
    private EntityManager em;
    
    @Override
    protected EntityManager getEntityManager(){
        return em;
    }

    public DesviacionAliadosFacade() {
        super(DesviacionAliados.class);
    }
    
    public DesviacionAliados findByIdAnalisisDesviacion(Integer id) throws Exception{
        Query q = this.em.createQuery("SELECT da FROM DesviacionAliados da WHERE da.analisisDesviacionId = ?1");
        q.setParameter(1, id);
        DesviacionAliados da = (DesviacionAliados) q.getSingleResult();
        return da;
    }
    
}
