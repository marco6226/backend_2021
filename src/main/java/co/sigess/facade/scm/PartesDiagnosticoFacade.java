/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package co.sigess.facade.scm;

import co.sigess.entities.scm.DiagnosticoPartesEntity;
import co.sigess.facade.com.AbstractFacade;
import java.math.BigInteger;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.Context;

/**
 *
 * @author Luis
 */
@Stateless
public class PartesDiagnosticoFacade extends AbstractFacade<DiagnosticoPartesEntity>{
    
          @Context
    private HttpServletRequest httpRequest;
    
    @PersistenceContext(unitName = "SIGESS_PU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
     public PartesDiagnosticoFacade() {
        super(DiagnosticoPartesEntity.class);
    }
     
         public DiagnosticoPartesEntity createPD(DiagnosticoPartesEntity data) throws Exception{
        super.create(data);
        return data;
    }
         
               public List<DiagnosticoPartesEntity> findAllByIdDiagPartes(BigInteger caseId) {
        Query query = this.em.createNativeQuery("SELECT * FROM scm.partes_diagnostico WHERE id_diagnostico = ?1 ", DiagnosticoPartesEntity.class);
        
        query.setParameter(1, caseId);
        List<DiagnosticoPartesEntity> list = (List<DiagnosticoPartesEntity>) query.getResultList();
        return list;
    }
               
     
        public void deleteIdDiagPartes(BigInteger caseId, BigInteger part) {
        Query query = this.em.createNativeQuery("DELETE FROM scm.partes_diagnostico WHERE id_diagnostico = ?1 AND id_partes= ?2;");
        
        query.setParameter(1, caseId);
        query.setParameter(2, part);
        query.executeUpdate();
       
       
    }
               
               
    
    
}
