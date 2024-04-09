/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.sigess.facade.ind;

import co.sigess.entities.ind.Meta;
import co.sigess.entities.ind.ValorMeta;
import co.sigess.facade.com.AbstractFacade;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author JULIO
 */
@Stateless
public class MetaFacade extends AbstractFacade<Meta>{

    @PersistenceContext(unitName = "SIGESS_PU")
    private EntityManager em;
    
    @EJB
    private ValorMetaFacade valorMetaFacade;
    
    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public MetaFacade() {
        super(Meta.class);
    }
    
    public List<Meta> create(List<Meta> metas) throws Exception {
        for(Meta meta: metas){
            List<ValorMeta> valorMetaList = meta.getValorMeta();
            List<Meta> metasList = meta.getMetas();
            meta = super.create(meta);
            for(int i = 0; i < valorMetaList.size(); i++){
                valorMetaList.get(i).setIdMeta(meta.getId());
            }
            meta.setValorMeta(valorMetaFacade.create(valorMetaList));
            //System.out.println("p" + metasList);
            if(metasList != null && !metasList.isEmpty()){
                meta.setMetas(this.create(metasList));
            }
        }
        return metas;
    }
    
    public List<Meta> update(List<Meta> metas) throws Exception {
        for(Meta meta: metas) {
            List<ValorMeta> valorMetaList = meta.getValorMeta();
            List<Meta> metasList = meta.getMetas();
            meta = super.edit(meta);
            meta.setValorMeta(valorMetaFacade.update(valorMetaList));
            if(metasList != null && !metasList.isEmpty()) {
                meta.setMetas(this.update(metasList));
            }
        }
        return metas;
    }
}
