/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.sigess.facade.inp;

import co.sigess.entities.ado.Documento;
import co.sigess.entities.inp.Calificacion;
import co.sigess.facade.ado.DocumentoFacade;
import co.sigess.facade.com.AbstractFacade;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author fmoreno
 */
@Stateless
public class CalificacionFacade extends AbstractFacade<Calificacion> {

    @PersistenceContext(unitName = "SIGESS_PU")
    private EntityManager em;
    
    
    @EJB
    private DocumentoFacade documentoFacade;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public CalificacionFacade() {
        super(Calificacion.class);
    }

    public void relacionarDocumento(Documento documento, Long calificacionId) throws Exception {
        Calificacion calificacion = this.find(calificacionId);
        calificacion.getDocumentosList().add(documento);
        super.edit(calificacion);
    }

    public void retirarDocumento(Documento documento) throws Exception {
        Query q = this.em.createQuery("SELECT c FROM Calificacion c JOIN c.documentosList d WHERE d.id = ?1 ");
        q.setParameter(1, documento.getId());
        Calificacion calificacion = (Calificacion) q.getSingleResult();
        calificacion.getDocumentosList().remove(documento);
        super.edit(calificacion);
    }
}
