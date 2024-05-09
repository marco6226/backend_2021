/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package co.sigess.facade.scm;
import co.sigess.entities.scm.DatosTrabajadorEntity;
import co.sigess.facade.com.AbstractFacade;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.Context;
/**
 *
 * @author Luis
 */
@javax.ejb.Stateless
public class DatosTrabajadorFacade extends AbstractFacade<DatosTrabajadorEntity>{
      @Context
    private HttpServletRequest httpRequest;
    
    @PersistenceContext(unitName = "SIGESS_PU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
     public DatosTrabajadorFacade() {
        super(DatosTrabajadorEntity.class);
    }

    public DatosTrabajadorEntity createDT(DatosTrabajadorEntity data) throws Exception{
        super.create(data);
        return data;
    }
}
