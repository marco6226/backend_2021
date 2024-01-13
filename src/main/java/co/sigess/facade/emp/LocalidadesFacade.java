/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package co.sigess.facade.emp;

import co.sigess.entities.emp.Localidades;
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
public class LocalidadesFacade extends AbstractFacade<Localidades>{
    
    @PersistenceContext(unitName = "SIGESS_PU")
    private EntityManager em;
    
    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
    
    public LocalidadesFacade() {
        super(Localidades.class);
    }
    
    public Localidades adicionarLocalidad(Localidades localidades) throws Exception {
        localidades = super.create(localidades);
        return localidades;
    }
    
    public Localidades updateLocalidad(Localidades localidades) throws Exception {
        localidades = super.edit(localidades);
        return localidades;
    }
    
    public List<Localidades> findByLocalidadId(Integer empresaId) {
        
        Query query = this.em.createQuery("SELECT al from localidades al");
//        query.setParameter("id", empresaId);
        List<Localidades> localidades = (List<Localidades>) query.getResultList();
        return localidades;        
    }
    
    public List<Localidades> findByLocalidadId2(Integer empresaId) {

        Query query = this.em.createQuery("SELECT al FROM Localidades al WHERE al.id = ?1");
        query.setParameter(1, empresaId);
        List<Localidades> localidades = (List<Localidades>) query.getResultList();
        return localidades;        
    }
    
    public List<Localidades> findByAllLocalidades() {
        
        Query query = this.em.createQuery("SELECT al from Localidades al");
        List<Localidades> localidades = (List<Localidades>) query.getResultList();
        return localidades;        
    }
    
    public void deleteLocalidad(Localidades localidades)throws Exception {
        super.remove(localidades);      
    }
}
