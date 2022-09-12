/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package co.sigess.facade.sec;

import co.sigess.facade.com.AbstractFacade;
import co.sigess.entities.sec.CorreoEstados;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.ejb.Stateless;
/**
 *
 * @author LENOVO
 */
@Stateless
public class CorreoEstadosFacade extends AbstractFacade<CorreoEstados> {

    @PersistenceContext(unitName = "SIGESS_PU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public CorreoEstadosFacade() {
        super(CorreoEstados.class);
    }


    
}
