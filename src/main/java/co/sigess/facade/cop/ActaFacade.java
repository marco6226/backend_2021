/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.sigess.facade.cop;

import co.sigess.entities.ado.Documento;
import co.sigess.entities.auc.Observacion;
import co.sigess.entities.cop.Acta;
import co.sigess.facade.ado.DocumentoFacade;
import co.sigess.facade.com.AbstractFacade;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author fmoreno
 */
@Stateless
public class ActaFacade extends AbstractFacade<Acta> {

    @EJB
    private DocumentoFacade documentoFacade;

    @PersistenceContext(unitName = "SIGESS_PU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public ActaFacade() {
        super(Acta.class);
    }

    @Override
    public Acta create(Acta entity) throws Exception {
        if (entity.getArea() == null || entity.getArea().getId() == null) {
            throw new IllegalArgumentException("Debe especificar el área del acta");
        }
        return super.create(entity);
    }

    public void relacionarDocumento(Documento documento, Long actaId) throws Exception {
        Acta ad = this.find(actaId);
        List<Documento> list = ad.getDocumentosList();
        if (list == null) {
            list = new ArrayList<>();
        }
        list.add(documento);
        super.edit(ad);
    }

    @Override
    public void remove(Acta acta) throws Exception {
        Acta actaDB = this.find(acta.getId());
        for (Documento doc : actaDB.getDocumentosList()) {
            documentoFacade.remove(doc);
        }
        super.remove(acta);
    }

}
