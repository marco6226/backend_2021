/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.sigess.facade.ipr;

import co.sigess.entities.ipr.SubprocesoMatriz;
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
public class SubprocesoMatrizFacade extends AbstractFacade<SubprocesoMatriz> {
    
    @PersistenceContext(unitName = "SIGESS_PU")
    private EntityManager em;
    
    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
    
    public SubprocesoMatrizFacade(){
        super(SubprocesoMatriz.class);
    }
    
    public List findForEmp(int procesoId){
            
        Query q = this.em.createNativeQuery("SELECT sm.id, sm,nombre FROM ipr.subproceso_matriz sm WHERE fk_proceso_matriz_id = ?1 ");
        q.setParameter(1,procesoId);
        List<SubprocesoMatriz> list = (List<SubprocesoMatriz>) q.getResultList();
        return list;
    }
    
    public List<SubprocesoMatriz> findForProceso(Integer procesoId){
        Query q = this.em.createQuery("SELECT sm.id, sm.estado FROM SubprocesoMatriz sm WHERE sm.procesoMatriz.id = :procesoId and sm.estado = 'No evaluada' and sm.eliminado = false");
        q.setParameter("procesoId", procesoId);
        List<SubprocesoMatriz> list = (List<SubprocesoMatriz>) q.getResultList();
        return list;
    }
    
    public void editSubProcesoEstado(Integer subprocesoId){
        int q = this.em.createQuery("UPDATE SubprocesoMatriz sm SET sm.estado = 'Evaluado' WHERE sm.id = :subprocesoId")
        .setParameter("subprocesoId", subprocesoId)
        .executeUpdate();        
    }
    
    public void editSubProcesoEliminacion(Integer procesoId){
        int q = this.em.createQuery("UPDATE SubprocesoMatriz sm SET sm.eliminado = true WHERE sm.procesoMatriz.id = :procesoId")
        .setParameter("procesoId", procesoId)
        .executeUpdate();        
    }
    
    public void editSubProcesoEliminacionArea(Integer areaId){
        int q = this.em.createQuery("UPDATE SubprocesoMatriz sm SET sm.eliminado = true WHERE sm.procesoMatriz.areaMatriz.id = :areaId")
        .setParameter("areaId", areaId)
        .executeUpdate();        
    }
    
    public List findToProceso(Integer procesoId, String nombre){
        Query q = this.em.createQuery("SELECT sm FROM SubprocesoMatriz sm WHERE sm.eliminado = FALSE AND sm.procesoMatriz.id=?1 AND sm.nombre=?2");
        q.setParameter(1, procesoId);
        q.setParameter(2, nombre);
        List<SubprocesoMatriz> list = (List<SubprocesoMatriz>) q.getResultList();
        return list;        
    }
}
