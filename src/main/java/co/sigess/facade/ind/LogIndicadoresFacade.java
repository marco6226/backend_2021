/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package co.sigess.facade.ind;

import co.sigess.entities.ind.LogIndicadores;
import co.sigess.facade.com.AbstractFacade;
import java.util.Date;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Usuario
 */
@Stateless
public class LogIndicadoresFacade extends AbstractFacade<LogIndicadores>{
    
    @PersistenceContext(unitName = "SIGESS_PU")
    private EntityManager em;
    
    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
    
    public LogIndicadoresFacade(){
        super(LogIndicadores.class);
    }
    
    @Override
    public LogIndicadores create(LogIndicadores logIndicadores) throws Exception {


        super.create(logIndicadores);
        return logIndicadores;
    }

    @Override
    public LogIndicadores edit(LogIndicadores logIndicadores) throws Exception {
        
        LogIndicadores logI = this.find(logIndicadores.getId());
        logI.setModulo(logIndicadores.getModulo());
        logI.setAccion(logIndicadores.getAccion());
        logI.setFecha(new Date());
        logI.setHora(new Date());
        logI.setUsuario(logIndicadores.getUsuario());
        logI.setObservacion(logIndicadores.getObservacion());
        logI.setEmpresa(logIndicadores.getEmpresa());
        logI = super.edit(logI); //To change body of generated methods, choose Tools | Templates.

        return logIndicadores;
    }
}
