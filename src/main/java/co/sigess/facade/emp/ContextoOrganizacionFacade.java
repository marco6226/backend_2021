/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.sigess.facade.emp;

import co.sigess.entities.com.TipoMensaje;
import co.sigess.entities.emp.Competencia;
import co.sigess.entities.emp.ContextoOrganizacion;
import co.sigess.exceptions.UserMessageException;
import co.sigess.facade.com.AbstractFacade;
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
public class ContextoOrganizacionFacade extends AbstractFacade<ContextoOrganizacion> {

    @PersistenceContext(unitName = "SIGESS_PU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public ContextoOrganizacionFacade() {
        super(ContextoOrganizacion.class);
    }

    public ContextoOrganizacion findDefault(Integer empresaIdRequestContext) {
        Query q = this.em.createQuery("select ctx from ContextoOrganizacion ctx where ctx.empresa.id = ?1 ORDER BY ctx.version DESC");
        q.setParameter(1, empresaIdRequestContext);
        q.setMaxResults(1);
        try {
            ContextoOrganizacion ctx = (ContextoOrganizacion) q.getSingleResult();
            return ctx;
        } catch (Exception e) {
            return null;
        }
    }

}
