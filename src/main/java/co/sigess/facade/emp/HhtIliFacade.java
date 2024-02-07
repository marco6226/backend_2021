/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.sigess.facade.emp;

import co.sigess.entities.emp.HhtIli;
import co.sigess.facade.com.AbstractFacade;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Julio
 */
@Stateless
public class HhtIliFacade extends AbstractFacade<HhtIli>{

    @PersistenceContext(unitName = "SIGESS_PU")
    private EntityManager em;
    
    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
    
    public HhtIliFacade() {
        super(HhtIli.class);
    }
    
    public List<HhtIli> create(List<HhtIli> hhtIliList, long idEmpresa) throws Exception{
        hhtIliList.forEach(hhtIli -> {
            hhtIli.setIdEmpresa(idEmpresa);
            try {
                hhtIli = super.create(hhtIli);
            } catch (Exception e) {
                throw new RuntimeException(e.getMessage(), e);
            }
        });
        return hhtIliList;
    }
    
    public List<HhtIli> update(List<HhtIli> hhtIliList, long idEmpresa) throws Exception{
        hhtIliList.forEach(hhtIli -> {
            hhtIli.setIdEmpresa(idEmpresa);
            try {
                hhtIli = super.edit(hhtIli);
            } catch (Exception e) {
                throw new RuntimeException(e.getMessage(), e);
            }
        });
        return hhtIliList;
    }
}
