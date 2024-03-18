/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.sigess.facade.ipr;

import co.sigess.entities.ipr.AreaMatriz;
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
public class AreaMatrizFacade extends AbstractFacade<AreaMatriz>{
    
    @PersistenceContext(unitName = "SIGESS_PU")
    private EntityManager em;
    
    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
    
    public AreaMatrizFacade() {
        super(AreaMatriz.class);
    }
    
    public List findForEmp(int empresaId){
            
        Query q = this.em.createNativeQuery("SELECT am.id, am,nombre FROM ipr.area_matriz am WHERE fk_empresa_id = ?1 ");
        q.setParameter(1,empresaId);
        List<AreaMatriz> list = (List<AreaMatriz>) q.getResultList();
        return list;
    }
    
    public void editAreaEstado(Integer areaId){
        int q = this.em.createQuery("UPDATE AreaMatriz am SET am.estado = 'No evaluada' WHERE am.id = :areaId")
        .setParameter("areaId", areaId)
        .executeUpdate();        
    }
    
    public List findToPlanta(Integer localidadId, String nombre){
        Query q = this.em.createQuery("SELECT am FROM AreaMatriz am WHERE am.localidad.id = ?1 AND am.nombre = ?2 AND am.eliminado = FALSE");
        q.setParameter(1, localidadId);
        q.setParameter(2, nombre);
        List<AreaMatriz> list = (List<AreaMatriz>) q.getResultList();
        return list;        
    }
    
}
