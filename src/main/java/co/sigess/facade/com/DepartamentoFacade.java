/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.sigess.facade.com;

import co.sigess.entities.com.Departamento;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author fmoreno
 */
@Stateless
public class DepartamentoFacade extends AbstractFacade<Departamento> {

    @PersistenceContext(unitName = "SIGESS_PU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public DepartamentoFacade() {
        super(Departamento.class);
    }

    public List<Departamento> findByPais(Short paisId) {
        Query q = this.em.createQuery("SELECT d from  Departamento d WHERE d.pais.id = :paisId ORDER BY d.nombre");
        q.setParameter("paisId", paisId);
        List<Departamento> list = (List<Departamento>) q.getResultList();
        return list;
    }

}
