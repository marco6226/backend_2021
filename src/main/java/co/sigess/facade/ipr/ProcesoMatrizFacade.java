/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.sigess.facade.ipr;

import co.sigess.entities.ipr.ProcesoMatriz;

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
public class ProcesoMatrizFacade extends AbstractFacade<ProcesoMatriz> {
    
    @PersistenceContext(unitName = "SIGESS_PU")
    private EntityManager em;
    
    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
    
    public ProcesoMatrizFacade() {
        super(ProcesoMatriz.class);
    }
    
    public List findForEmp(int empresaId){
            
        Query q = this.em.createNativeQuery("SELECT pm.id, pm,nombre FROM ipr.proceso_matriz pm WHERE fk_empresa_id = ?1 ");
        q.setParameter(1,empresaId);
        List<ProcesoMatriz> list = (List<ProcesoMatriz>) q.getResultList();
        return list;
    }
    
    public List<ProcesoMatriz> findForArea(Integer areaId){
        Query q = this.em.createQuery("SELECT pm.id, pm.estado FROM ProcesoMatriz pm WHERE pm.areaMatriz.id = :areaId and pm.estado = 'No evaluada' and pm.eliminado = false");
        q.setParameter("areaId", areaId);
        List<ProcesoMatriz> list = (List<ProcesoMatriz>) q.getResultList();
        return list;
    }
    
    public List<ProcesoMatriz> findForArea2(Integer areaId){
        System.out.println("Consulta para área ID: " + areaId); // Agrega una línea para depuración
        Query q = this.em.createQuery("SELECT pm FROM ProcesoMatriz pm WHERE pm.areaMatriz.id = :areaId AND pm.eliminado = FALSE");
        q.setParameter("areaId", areaId);
        List<ProcesoMatriz> list = (List<ProcesoMatriz>) q.getResultList();
        System.out.println("Procesos encontrados: " + list.size()); // Agrega una línea para depuración
        return list;
    }
     
    public void editProcesoEstado(Integer procesoId){
        int q = this.em.createQuery("UPDATE ProcesoMatriz pm SET pm.estado = 'No evaluada' WHERE pm.id = :procesoId")
        .setParameter("procesoId", procesoId)
        .executeUpdate();  
    }
    
    public void editProcesoEliminacion(Integer areaId){
        int q = this.em.createQuery("UPDATE ProcesoMatriz pm SET pm.eliminado = true WHERE pm.areaMatriz.id = :areaId")
        .setParameter("areaId", areaId)
        .executeUpdate();        
    }
    
    public List findToArea(Integer areaId, String nombre){
        Query q = this.em.createQuery("SELECT pm FROM ProcesoMatriz pm WHERE pm.eliminado = FALSE AND pm.areaMatriz.id = ?1 AND pm.nombre = ?2");
        q.setParameter(1, areaId);
        q.setParameter(2, nombre);
        List<ProcesoMatriz> list = (List<ProcesoMatriz>) q.getResultList();
        return list;        
    }
       public List<ProcesoMatriz> findForCoronaPros(){
            
        Query q = this.em.createNativeQuery("SELECT * FROM ipr.proceso_matriz  WHERE fk_empresa_id = 22 ", ProcesoMatriz.class);
        List<ProcesoMatriz> list = (List<ProcesoMatriz>) q.getResultList();
        return list;
    }
}
