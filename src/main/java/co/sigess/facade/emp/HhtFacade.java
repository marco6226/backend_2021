/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.sigess.facade.emp;

import co.sigess.entities.emp.Empresa;
import co.sigess.entities.emp.Hht;
import co.sigess.facade.com.AbstractFacade;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author fmoreno
 */
//@Stateless
@javax.ejb.Stateless
public class HhtFacade extends AbstractFacade<Hht> {
    
    @PersistenceContext(unitName = "SIGESS_PU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public HhtFacade() {
        super(Hht.class);
    }

//    @Override
//    public Hht edit(Hht entity) throws Exception {
//        Hht hhtDB = this.find(entity.getId());
//        hhtDB.setValor(entity.getValor());
//        hhtDB.setAnio(entity.getAnio());
//        hhtDB.setMes(entity.getMes());
//        hhtDB.setEmpresaSelect(entity.getEmpresaSelect());
//        return super.edit(hhtDB);
//    }

    public List<Hht> findByAnioEmpresa(Short anio, Integer empresaId) {
        Query q = this.em.createQuery("SELECT h FROM Hht h WHERE h.anio = ?1 AND h.empresa.id = ?2");
        q.setParameter(1, anio);
        q.setParameter(2, empresaId);
        List<Hht> list = (List<Hht>) q.getResultList();
        return list;
    }
    
    public List<Hht> create(List<Hht> hhtList, Empresa emp) throws Exception {
        hhtList.forEach(hht -> {
            hht.setEmpresa(emp);
            try {
                hht = super.create(hht);
            } catch (Exception ex) {
                throw new RuntimeException(ex.getMessage(), ex);
            }
        });
        return hhtList;
    }
    
    public List<Hht> edit(List<Hht> hhtList, Empresa emp) throws Exception {
        hhtList.forEach(hht -> {
            hht.setEmpresa(emp);
            try {
                hht = super.edit(hht);
            } catch (Exception ex) {
                throw new RuntimeException(ex.getMessage(), ex);
            }
        });
        return hhtList;
    }

}
