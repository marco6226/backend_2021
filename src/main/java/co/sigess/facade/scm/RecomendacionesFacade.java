/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.sigess.facade.scm;

import co.sigess.entities.scm.CasosMedicos;
import co.sigess.entities.scm.PlanAccion;
import co.sigess.entities.scm.Recomendaciones;
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
public class RecomendacionesFacade extends AbstractFacade<Recomendaciones> {

    @PersistenceContext(unitName = "SIGESS_PU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public RecomendacionesFacade() {
        super(Recomendaciones.class);
    }

    public Recomendaciones crear(Recomendaciones recomendaciones, Integer empresaId) throws Exception {

        super.create(recomendaciones);
        
        
        return recomendaciones;
    }

    public Recomendaciones update(Recomendaciones recomendaciones) throws Exception {

        recomendaciones = super.edit(recomendaciones);
        return recomendaciones;
    }

    public List<Recomendaciones> buscar(String parametro) {
        System.out.println(parametro);

        Query q = this.em.createNativeQuery("SELECT * FROM scm.recomendaciones  WHERE pk_case = ?1 and eliminado != true order by fecha_inicio desc", Recomendaciones.class);

        q.setParameter(1, Integer.parseInt(parametro));
        List<Recomendaciones> list = (List<Recomendaciones>) q.getResultList();
        return list;

    }

    public int eliminar(Long parametro) {
        //3117537464
        Query q = this.em.createNativeQuery("UPDATE scm.recomendaciones  SET eliminado = true WHERE id = ?1");
        q.setParameter(1, parametro);
        int deleted = q.executeUpdate();
        return deleted;
    }
    
    public Recomendaciones findById(String id){
        Query query = this.em.createQuery("SELECT DISTINCT RECO FROM Recomendaciones reco where RECO.id = :id");
        Integer id_aux = new Integer(id);
        query.setParameter("id", id_aux);
        
        List<Recomendaciones> list = (List<Recomendaciones>) query.getResultList();

        return list.get(0);
    }

}
