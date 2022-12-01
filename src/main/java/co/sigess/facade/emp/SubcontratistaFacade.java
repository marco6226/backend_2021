/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.sigess.facade.emp;

import co.sigess.entities.emp.Subcontratista;
import co.sigess.facade.com.AbstractFacade;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author Julio
 */
@Stateless
public class SubcontratistaFacade extends AbstractFacade<Subcontratista>{
    
    @PersistenceContext(unitName = "SIGESS_PU")
    private EntityManager em;

    public SubcontratistaFacade() {
        super(Subcontratista.class);
    }
    
    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
    
    public Subcontratista crearSubcontratista(Subcontratista subcontratista) throws Exception {
        subcontratista = super.create(subcontratista);
        return subcontratista;
    }
    
    public Subcontratista actualizarSubcontratista(Subcontratista subcontratista) throws Exception {
        subcontratista = super.edit(subcontratista);
        return subcontratista;
    }
    
    public List<Subcontratista> findByAliadoCreador(Integer aliadoId) {
        
        Query query = this.em.createQuery("SELECT su from Subcontratista su WHERE su.id_aliado_creador=:aliadoId");
        query.setParameter("aliadoId", aliadoId);
        List<Subcontratista> listaSubcontratistas = (List<Subcontratista>) query.getResultList();
        return listaSubcontratistas;
    }
}
