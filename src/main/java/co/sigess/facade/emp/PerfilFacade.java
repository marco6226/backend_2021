/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.sigess.facade.emp;

import co.sigess.facade.com.AbstractFacade;
import co.sigess.entities.emp.Perfil;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author fmoreno
 */
@javax.ejb.Stateless
public class PerfilFacade extends AbstractFacade<Perfil> {

    @PersistenceContext(unitName = "SIGESS_PU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public PerfilFacade() {
        super(Perfil.class);
    }

    @Override
    public Perfil create(Perfil entity) throws Exception {
        return super.create(entity); //To change body of generated methods, choose Tools | Templates.
    }

    public List<Perfil> findByEmpresa(Integer empresaId) {
        Query q = this.em.createQuery("SELECT p FROM Perfil p WHERE p.empresa.id = :empresaId ORDER BY p.nombre");
        q.setParameter("empresaId", empresaId);
        List<Perfil> list = (List<Perfil>) q.getResultList();
        return list;
    }

}
