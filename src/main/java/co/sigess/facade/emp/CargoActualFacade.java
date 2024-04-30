/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.sigess.facade.emp;

import co.sigess.entities.emp.CargoActual;
import co.sigess.facade.com.AbstractFacade;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author Usuario
 */
@javax.ejb.Stateless
public class CargoActualFacade extends AbstractFacade<CargoActual>{
    @PersistenceContext(unitName = "SIGESS_PU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public CargoActualFacade() {
        super(CargoActual.class);
    }
    
    
    @Override
    public CargoActual create(CargoActual cargoActual) throws Exception {
//        cargo.setId(Util.generateId());
        return super.create(cargoActual);
    }
    
    public List<CargoActual> findByEmpresaId(Integer empresaId) {

        Query query = this.em.createQuery("SELECT  c from CargoActual c where c.empresa.id = :empresaId ORDER BY c.nombre ASC");
        query.setParameter("empresaId", empresaId);
        List<CargoActual> list = (List<CargoActual>) query.getResultList();
        return list;
    }
}
