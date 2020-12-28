/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.sigess.facade.scm;

import co.sigess.entities.scm.CasosMedicos;
import co.sigess.entities.scm.Recomendaciones;
import co.sigess.facade.com.AbstractFacade;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author leonardo
 */
@Stateless
public class RecomendacionesFacade extends AbstractFacade<Recomendaciones> {

    @PersistenceContext(unitName = "SIGESS_PU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
    
    public RecomendacionesFacade(){
        super(Recomendaciones.class);
    }
    
    
     public Recomendaciones create(Recomendaciones recomendaciones, Integer empresaId) throws Exception {
        

        super.create(recomendaciones);
        return recomendaciones;
    }
     
      public Recomendaciones update(Recomendaciones recomendaciones) throws Exception {
       
        recomendaciones = super.edit(recomendaciones);
        return recomendaciones;
    }

      public List<Recomendaciones> buscar(String parametro) {
        System.out.println(parametro);

        Query q = this.em.createNativeQuery("SELECT * FROM scm.casos_medicos  WHERE documento = ?1");
        
        q.setParameter(1,parametro);
        List<Recomendaciones> list = (List<Recomendaciones>) q.getResultList();
        return list;

      }
      
    
}
