package co.sigess.facade.emp;


import co.sigess.entities.emp.AliadoInformacion;
import co.sigess.facade.com.AbstractFacade;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author John Rueda
 */
@Stateless
public class AliadoInformacionFacade extends AbstractFacade<AliadoInformacion>  {
    
    @PersistenceContext(unitName = "SIGESS_PU")
    private EntityManager em;
    
    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
    
    public AliadoInformacionFacade() {
        super(AliadoInformacion.class);
    }
    
    public AliadoInformacion adicionarAliadoInformacion(AliadoInformacion aliadoInformacion) throws Exception {
        aliadoInformacion = super.create(aliadoInformacion);
        return aliadoInformacion;
    }
    
    public AliadoInformacion updateAliadoInformacion(AliadoInformacion aliadoInformacion) throws Exception {
        aliadoInformacion = super.edit(aliadoInformacion);
        return aliadoInformacion;
    }
    
    public List<AliadoInformacion> findByAliadoId(Integer aliadoId) {
        
        Query query = this.em.createQuery("SELECT al from AliadoInformacion al where al.id_empresa = :aliadoId");
        query.setParameter("aliadoId", aliadoId);
        List<AliadoInformacion> aliadoInformacion = (List<AliadoInformacion>) query.getResultList();
        return aliadoInformacion;        
    }
    
    public void deleteAliadoInformacion(AliadoInformacion aliadoInformacion)throws Exception {
        super.remove(aliadoInformacion);      
    }
}
