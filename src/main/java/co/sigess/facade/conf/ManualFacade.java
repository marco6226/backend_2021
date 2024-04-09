/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.sigess.facade.conf;

import co.sigess.entities.ado.Documento;
import co.sigess.entities.com.TipoMensaje;
import co.sigess.entities.conf.Manual;
import co.sigess.exceptions.UserMessageException;
import co.sigess.facade.ado.DirectorioFacade;
import co.sigess.facade.com.AbstractFacade;
import co.sigess.util.FileUtil;
import java.io.OutputStream;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author fmoreno
 */
@Stateless
public class ManualFacade extends AbstractFacade<Manual> {

    @PersistenceContext(unitName = "SIGESS_PU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public ManualFacade() {
        super(Manual.class);
    }

    public List<Manual> buscarPorUsuario(Integer empresaIdRequestContext, Integer id) {
        Query q = this.em.createQuery("SELECT m FROM Manual m JOIN m.usuarioList u WHERE u.id = ?1 AND m.empresaId = ?2");
        q.setParameter(1, id);
        q.setParameter(2, empresaIdRequestContext);
        List<Manual> list = q.getResultList();
        return list;
    }
    
    public OutputStream findFile(Integer manualId) {
        Manual manual = this.find(manualId);
        if (manual == null) {
            throw new IllegalArgumentException("parámetro id no válido");
        }

        OutputStream file;
        try {
            file = FileUtil.getFromVirtualFS(manual.getRuta());
        } catch (Exception ex) {
            Logger.getLogger(DirectorioFacade.class.getName()).log(Level.SEVERE, null, ex);
            throw new UserMessageException("ERROR AL SOLICITAR DOCUMENTO", "Error de archivo grave: MAN-" + manual.getId(), TipoMensaje.error);
        }
        return file;
    }

}
