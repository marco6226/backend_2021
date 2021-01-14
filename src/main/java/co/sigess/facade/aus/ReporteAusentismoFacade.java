/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.sigess.facade.aus;

import co.sigess.entities.aus.ReporteAusentismo;
import co.sigess.entities.scm.Recomendaciones;
import co.sigess.facade.com.AbstractFacade;
import java.util.Date;
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
public class ReporteAusentismoFacade extends AbstractFacade<ReporteAusentismo> {

    @PersistenceContext(unitName = "SIGESS_PU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public ReporteAusentismoFacade() {
        super(ReporteAusentismo.class);
    }

    @Override
    public ReporteAusentismo create(ReporteAusentismo entity) throws Exception {
        entity.setFechaElaboracion(new Date());
        return super.create(entity); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<ReporteAusentismo> findAllByEmpresa(Integer empresaId) {
        Query q = this.em.createQuery("SELECT ra FROM ReporteAusentismo ra WHERE ra.causaAusentismo.empresa.id = ?1 ORDER BY ra.fechaElaboracion DESC");
        q.setParameter(1, empresaId);
        List<ReporteAusentismo> list = q.getResultList();
        return list;
    }

    @Override
    public ReporteAusentismo edit(ReporteAusentismo entity) throws Exception {
        ReporteAusentismo repDB = this.find(entity.getId());
        repDB.setCausaAusentismo(entity.getCausaAusentismo());
        repDB.setCie(entity.getCie());
        repDB.setEmpleado(entity.getEmpleado());
        repDB.setEntidadOtorga(entity.getEntidadOtorga());
        repDB.setFechaDesde(entity.getFechaDesde());
        repDB.setFechaHasta(entity.getFechaHasta());
        repDB.setFechaRadicacion(entity.getFechaRadicacion());
        return super.edit(repDB); //To change body of generated methods, choose Tools | Templates.
    }
    
      public List<ReporteAusentismo> buscar(String parametro) {
        System.out.println(parametro);

        Query q = this.em.createNativeQuery("SELECT * FROM aus.reporte_ausentismo  WHERE fk_empleado_id = ?1",ReporteAusentismo.class);
        
        q.setParameter(1,Integer.parseInt(parametro));
        List<ReporteAusentismo> list = (List<ReporteAusentismo>) q.getResultList();
        return list;

      }
    
    
}
