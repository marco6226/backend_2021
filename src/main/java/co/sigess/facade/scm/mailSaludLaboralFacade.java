/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package co.sigess.facade.scm;

import co.sigess.entities.scm.MailSaludLaboralEntity;
import co.sigess.facade.com.AbstractFacade;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author Luis
 */
@Stateless
public class mailSaludLaboralFacade  extends AbstractFacade<MailSaludLaboralEntity> {
       @PersistenceContext(unitName = "SIGESS_PU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public mailSaludLaboralFacade() {
        super(MailSaludLaboralEntity.class);
    }

    public MailSaludLaboralEntity createMailCaseSL(MailSaludLaboralEntity sl) throws Exception {

        super.create(sl);
        return sl;
    }
    
     public List<MailSaludLaboralEntity> findAllByIdMail(Integer caseId) {
        //String pkUser = "12845";
        Query query = this.em.createNativeQuery("SELECT * FROM scm.mail_saludlaboral WHERE pk_case = ?1 order by estado_correo desc", MailSaludLaboralEntity.class);
        
        query.setParameter(1, caseId);
        //query.setParameter(2, pkUser);
        List<MailSaludLaboralEntity> list = (List<MailSaludLaboralEntity>) query.getResultList();
        return list;
    }
         public MailSaludLaboralEntity findById(Integer id) {
        Query q = this.em.createNativeQuery("SELECT * FROM scm.mail_saludlaboral WHERE id = ?1", MailSaludLaboralEntity.class);

        q.setParameter(1, id);
        MailSaludLaboralEntity saludLaboral = (MailSaludLaboralEntity) q.getSingleResult();
        return saludLaboral;
    }
     
     public List<MailSaludLaboralEntity> findAllByIdMailUser(Integer caseId, Integer pkUser) {
        //String pkUser = "12845";
        Query query = this.em.createNativeQuery("SELECT * FROM scm.mail_saludlaboral WHERE pk_case = ?1", MailSaludLaboralEntity.class);
        
        
        query.setParameter(1, caseId);
        query.setParameter(2, pkUser);
        List<MailSaludLaboralEntity> list = (List<MailSaludLaboralEntity>) query.getResultList();
        return list;
    }
     
          public List<MailSaludLaboralEntity> findAllByIdMailUserOnlyUser(Integer pkUser) {
        //String pkUser = "12845";
        Query query = this.em.createNativeQuery("SELECT * FROM scm.mail_saludlaboral WHERE  pk_user = ?2", MailSaludLaboralEntity.class);
        
        query.setParameter(2, pkUser);
        List<MailSaludLaboralEntity> list = (List<MailSaludLaboralEntity>) query.getResultList();
        return list;
    }
          public List<MailSaludLaboralEntity> findAllByIdMailUserOnlySolicitadoByMail(String usuarioSolicitante) {
        //String pkUser = "12845";
        Query query = this.em.createNativeQuery("SELECT * FROM scm.mail_saludlaboral WHERE  usuario_solicitante = ?2", MailSaludLaboralEntity.class);
        
        query.setParameter(2, usuarioSolicitante);
        List<MailSaludLaboralEntity> list = (List<MailSaludLaboralEntity>) query.getResultList();
        return list;
    }
     
         public MailSaludLaboralEntity update(MailSaludLaboralEntity dt) throws Exception {

        dt = super.edit(dt);
        return dt;
    }
         
public void deleteDocumentFromMail(Integer id, String docID) throws Exception {
    try {
        // Suponiendo que el campo documentos es una cadena que contiene los IDs de los documentos separados por comas.
        MailSaludLaboralEntity mailEntity = this.findById(id);
        if (mailEntity != null) {
            String documentos = mailEntity.getDocumentos();
            if (documentos != null && !documentos.isEmpty()) {
                List<String> documentosList = new ArrayList<>(Arrays.asList(documentos.split(",")));
                if (documentosList.contains(docID)) {
                    documentosList.remove(docID);
                    mailEntity.setDocumentos(String.join(",", documentosList));
                    this.update(mailEntity);
                } else {
                    throw new Exception("Documento no encontrado en la lista.");
                }
            } else {
                throw new Exception("No hay documentos para eliminar.");
            }
        } else {
            throw new Exception("MailSaludLaboralEntity no encontrado.");
        }
    } catch (Exception e) {
        // Manejar la excepción y re-lanzarla para que se registre adecuadamente
        e.printStackTrace();
        throw e;
    }
}

}
