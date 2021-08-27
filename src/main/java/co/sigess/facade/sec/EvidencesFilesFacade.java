/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.sigess.facade.sec;

import co.sigess.entities.sec.EvidencesFiles;
import co.sigess.entities.sec.SeguimientoTarea;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import co.sigess.facade.com.AbstractFacade;

/**
 *
 * @author Leo
 */
@Stateless
public class EvidencesFilesFacade extends AbstractFacade<EvidencesFiles> {
 
    @PersistenceContext(unitName = "SIGESS_PU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
    
    public EvidencesFilesFacade(){
        super(EvidencesFiles.class);
    }
    
    
    
}
