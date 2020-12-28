/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.sigess.facade.scm;
import co.sigess.facade.com.AbstractFacade;
import co.sigess.entities.scm.CasosMedicos;
import co.sigess.facade.com.AbstractFacade;
import java.io.Serializable;
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
public class CasosMedicosFacade  extends AbstractFacade<CasosMedicos>{
    
     @PersistenceContext(unitName = "SIGESS_PU")
    private EntityManager em;

  @Override
    protected EntityManager getEntityManager() {
        return em;
    }
       public CasosMedicosFacade() {
        super(CasosMedicos.class);
    }

    
     public CasosMedicos create(CasosMedicos casosMedicos, Integer empresaId) throws Exception {
        

        super.create(casosMedicos);
        return casosMedicos;
    }
     
      public CasosMedicos update(CasosMedicos casosMedicos) throws Exception {
       
        casosMedicos = super.edit(casosMedicos);
        return casosMedicos;
    }

      public List<CasosMedicos> buscar(String parametro) {
        System.out.println(parametro);

        Query q = this.em.createNativeQuery("SELECT * FROM scm.casos_medicos  WHERE documento = ?1");
        
        q.setParameter(1,parametro);
         System.out.println(q);
        List<CasosMedicos> list = (List<CasosMedicos>) q.getResultList();
        return list;
    }
      
}
