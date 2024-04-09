/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.sigess.facade.emp;

import co.sigess.entities.emp.Usuario;
import co.sigess.facade.com.AbstractFacade;
import co.sigess.entities.emp.UsuarioEmpresa;
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
public class UsuarioEmpresaFacade extends AbstractFacade<UsuarioEmpresa> {

    @PersistenceContext(unitName = "SIGESS_PU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public UsuarioEmpresaFacade() {
        super(UsuarioEmpresa.class);
    }
    
    public List<UsuarioEmpresa> findUserId(int id){
    
    Query query = this.em.createQuery("SELECT u FROM UsuarioEmpresa u WHERE u.usuario.id = ?1");
    query.setParameter("1", id);
    try {
        List<UsuarioEmpresa> list = (List<UsuarioEmpresa>) query.getResultList();
        return list;
    } catch (Exception ejbExc) {
        return null;
    }
    
    }
    
}
