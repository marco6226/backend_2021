/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package co.sigess.facade.emp;

import co.sigess.entities.emp.ActividadesContratadas;
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
public class ActividadesContratadasFacade extends AbstractFacade<ActividadesContratadas> {
    
    @PersistenceContext(unitName = "SIGESS_PU")
    private EntityManager em;
    
    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
    
    public ActividadesContratadasFacade() {
        super(ActividadesContratadas.class);
    }
    
    public ActividadesContratadas adicionarActividadContratada(ActividadesContratadas actividadesContratadas) throws Exception {
        actividadesContratadas = super.create(actividadesContratadas);
        return actividadesContratadas;
    }
    
    public ActividadesContratadas updateActividadContratada(ActividadesContratadas actividadesContratadas) throws Exception {
        actividadesContratadas = super.edit(actividadesContratadas);
        return actividadesContratadas;
    }
    
    public List<ActividadesContratadas> findByActividadContratadaId(Integer empresaId) {
        
        Query query = this.em.createQuery("SELECT al from ActividadesContratadas al");
//        query.setParameter("id", empresaId);
        List<ActividadesContratadas> actividadesContratadas = (List<ActividadesContratadas>) query.getResultList();
        return actividadesContratadas;        
    }
    
    public void deleteActividadContratada(ActividadesContratadas actividadesContratadas)throws Exception {
        super.remove(actividadesContratadas);      
    }
}
