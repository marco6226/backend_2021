/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.sigess.facade.scm;

import co.sigess.entities.scm.SeguimientoCaso;
import co.sigess.facade.com.AbstractFacade;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author leonardo
 */
@Stateless
public class SeguimientoCasoFacade extends AbstractFacade<SeguimientoCaso> {

    @PersistenceContext(unitName = "SIGESS_PU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public SeguimientoCasoFacade() {
        super(SeguimientoCaso.class);
    }

    public SeguimientoCaso create(SeguimientoCaso seguimientocaso, Integer empresaId) throws Exception {

        super.create(seguimientocaso);
        return seguimientocaso;
    }

    public SeguimientoCaso update(SeguimientoCaso seguimientocaso) throws Exception {

        seguimientocaso = super.edit(seguimientocaso);
        return seguimientocaso;
    }

    public List<SeguimientoCaso> buscar(String parametro) {

        Query q = this.em.createNativeQuery("SELECT * FROM scm.seguimiento_caso  WHERE pk_case = ?1 and eliminado != true order by fecha_seg desc", SeguimientoCaso.class);
        q.setParameter(1, parametro);
        List<SeguimientoCaso> list = (List<SeguimientoCaso>) q.getResultList();
        return list;

    }
    
    public List<SeguimientoCaso> findById(String id){
        Query q = this.em.createNativeQuery("SELECT * FROM scm.seguimiento_caso WHERE id = ?1 and eliminado != true order by fecha_seg desc", SeguimientoCaso.class);
        Integer id_aux = new Integer(id);
        q.setParameter(1, id_aux);
        List<SeguimientoCaso> seguimientoList = (List<SeguimientoCaso>) q.getResultList();
        return seguimientoList;
    }

    public int eliminar(Long id) {

        //3117537464
        Query q = this.em.createNativeQuery("UPDATE scm.seguimiento_caso  SET eliminado = true WHERE id = ?1");
        q.setParameter(1, id);
        int deleted = q.executeUpdate();
        return deleted;
    }

}
