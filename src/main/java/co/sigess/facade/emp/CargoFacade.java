/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.sigess.facade.emp;

import co.sigess.facade.com.AbstractFacade;
import co.sigess.entities.emp.Cargo;
import co.sigess.util.Util;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author fmoreno
 */
@javax.ejb.Stateless
public class CargoFacade extends AbstractFacade<Cargo> {

    @PersistenceContext(unitName = "SIGESS_PU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public CargoFacade() {
        super(Cargo.class);
    }

    @Override
    public Cargo create(Cargo cargo) throws Exception {
//        cargo.setId(Util.generateId());
        return super.create(cargo);
    }

    @Override
    public Cargo edit(Cargo cargo) throws Exception {
        if (cargo.getId() == null) {
            throw new IllegalArgumentException("No ha establecido un id para el cargo a actualizar");
        }
        return super.edit(cargo);
    }

    public Cargo eliminar(Integer cargoId) throws Exception {
        Cargo cargoDB = this.find(cargoId);
        if (cargoDB == null) {
            return null;
        }
        if (cargoDB.getEmpleadoList() == null || cargoDB.getEmpleadoList().isEmpty()) {
            super.remove(cargoDB);
        } else {
            throw new IllegalArgumentException("No es posible eliminar el cargo debido a que existen empleados con el cargo a eliminar");
        }
        return cargoDB;
    }

    public List<Cargo> findByEmpresa(Integer empresaId) {
        Query q1 = em.createNativeQuery("SELECT e.id_empresa_aliada from emp.empresa e where e.id = ?1");
        q1.setParameter(1, empresaId);
        List list1 = q1.getResultList();

        if(list1.get(0) != null){
            empresaId=Integer.parseInt(list1.get(0).toString());
        }
      
        Query query = this.em.createQuery("SELECT c from Cargo c where c.empresa.id = :empresaId ORDER BY c.nombre ASC");
        query.setParameter("empresaId", empresaId);
        List<Cargo> list = (List<Cargo>) query.getResultList();
        return list;
    }

}
