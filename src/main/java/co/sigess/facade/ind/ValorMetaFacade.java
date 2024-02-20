/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.sigess.facade.ind;

import co.sigess.entities.ind.ValorMeta;
import co.sigess.facade.com.AbstractFacade;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author LENOVO
 */
@Stateless
public class ValorMetaFacade extends AbstractFacade<ValorMeta>{
    
    @PersistenceContext(unitName = "SIGESS_PU")
    private EntityManager em;
    
    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
    
    public ValorMetaFacade() {
        super(ValorMeta.class);
    }
    
    public List<ValorMeta> create(List<ValorMeta> valorMetas) throws Exception {
        try {
            for(ValorMeta valor : valorMetas){
                valor = this.create(valor);
            }
        } catch (Exception e) {
            throw new IllegalArgumentException("Error al guardar valores de la meta");
        }
        return valorMetas;
    }
    
    public List<ValorMeta> update(List<ValorMeta> valorMetas) throws Exception {
        try {
            for(ValorMeta valor: valorMetas){
                //System.out.println("pp " + valor.getValue());
                valor = this.edit(valor);
            }
        } catch (Exception e) {
            throw new IllegalArgumentException("Error al actualizar valores de la meta");
        }
        return valorMetas;
    }
}
