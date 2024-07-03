/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package co.sigess.facade.scm;

import co.sigess.entities.scm.PartesCuerpoEntity;
import co.sigess.facade.com.AbstractFacade;
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
public class PartesCuerpoFacade extends AbstractFacade<PartesCuerpoEntity>{
              @Context
    private HttpServletRequest httpRequest;
    
    @PersistenceContext(unitName = "SIGESS_PU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
     public PartesCuerpoFacade() {
        super(PartesCuerpoEntity.class);
    }
     
         public List<PartesCuerpoEntity> buscar() {

        Query q = this.em.createNativeQuery("SELECT * FROM scm.partes_cuerpo", PartesCuerpoEntity.class);

        List<PartesCuerpoEntity> list = (List<PartesCuerpoEntity>) q.getResultList();
        return list;

    }
    
}
