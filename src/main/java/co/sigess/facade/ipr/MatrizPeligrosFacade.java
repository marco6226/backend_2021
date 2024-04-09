/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.sigess.facade.ipr;

import co.sigess.entities.ado.Documento;
import co.sigess.entities.ipr.MatrizPeligros;
import co.sigess.entities.sec.AnalisisDesviacion;
import co.sigess.facade.com.AbstractFacade;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author Usuario
 */
@Stateless
public class MatrizPeligrosFacade extends AbstractFacade<MatrizPeligros>{
    
    @PersistenceContext(unitName = "SIGESS_PU")
    private EntityManager em;
    
    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
    
    public MatrizPeligrosFacade() {
        super(MatrizPeligros.class);
    }
    
    public List findForEmp(int empresaId){
        //Query q = this.em.createNativeQuery("SELECT mp.* FROM ipr.matriz_peligros mp WHERE fk_empresa_id = ?1 ");
        Query q = this.em.createQuery("SELECT mp FROM MatrizPeligros mp WHERE mp.empresa.id = ?1");
        q.setParameter(1,empresaId);
        List<MatrizPeligros> list = (List<MatrizPeligros>) q.getResultList();
        return list;
    }
    
    public void relacionarDocumentoEvidencia(Documento documento, Integer matrizId) throws Exception {
        MatrizPeligros mp = this.find(matrizId);
        List<Documento> list = mp.getDocumentosList();
        if (list == null) {
            list = new ArrayList<>();
        }
        list.add(documento);
        super.edit(mp);
    }
    
    public void retirarDocumentoEvidencia(Documento documento) throws Exception {
        Query q = this.em.createQuery("SELECT c FROM MatrizPeligros c JOIN c.documentosList d WHERE d.id = ?1 ");
        q.setParameter(1, documento.getId());
        MatrizPeligros matrizP = (MatrizPeligros) q.getSingleResult();
        matrizP.getDocumentosList().remove(documento);
        super.edit(matrizP);
    }
}
