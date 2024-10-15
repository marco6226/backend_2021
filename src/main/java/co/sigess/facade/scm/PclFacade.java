/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.sigess.facade.scm;

import co.sigess.entities.scm.CreatePclDTO;
import co.sigess.entities.scm.Pcl;
import co.sigess.entities.scm.Pcl_Diagnostico;
import co.sigess.entities.scm.pclDIagEntity;
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
public class PclFacade extends AbstractFacade<Pcl> {

    @PersistenceContext(unitName = "SIGESS_PU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public PclFacade() {
        super(Pcl.class);
    }

    public Pcl create(Pcl pcl, Integer empresaId) throws Exception {

        return super.create(pcl);
    }

    public int eliminar(Long id) {

        //3117537464
        Query q = this.em.createNativeQuery("UPDATE scm.pcl  SET eliminado = true WHERE id = ?1");
        q.setParameter(1, id);
        int deleted = q.executeUpdate();
        return deleted;
    }

public List<Object> findAllById(String caseId) {
    Query query = this.em.createNativeQuery("SELECT pcl.id,\n" +
        "    pcl.porcentaje_pcl,\n" +
        "    pcl.emision_pcl_fecha,\n" +
        "    pcl.entidad_emite_pcl,\n" +
        "    pcl.pcl,\n" +
        "    diag.id AS diag,\n" +
        "    pcl.eliminado,\n" +
        "    pcl.entidad_emitida,\n" +
        "    pcl.status_de_calificacion,\n" +
        "    pcl.fecha_calificacion,\n" +
        "    pcl.entidad_emite_calificacion,\n" +
        "    pcl.entidad_emitida_calificacion,\n" +
        "    pcl.observaciones,\n" +
        "    pcl.origen,\n" +
        "    diag.id AS id_diagnostico,\n" +
        "    diag.diagnostico AS nombre_diagnostico,\n" +
        "    pcl.origen_pcl,\n" +
        "    pcl.observaciones_pcl\n" +
        "   FROM scm.pcl pcl\n" +
        "     JOIN scm.pcl_diagnostico pcl_diag ON pcl.id = pcl_diag.id_pcl\n" +
        "     JOIN scm.diagnosticos diag ON pcl_diag.id_diagnostico = diag.id\n" +
        "  WHERE diag.pk_case= ?1 AND pcl.eliminado = false\n", Pcl.class);

    query.setParameter(1, caseId);
    List<Object> list = query.getResultList();
    System.out.println(query);
    System.out.println(list + "lsta");
    return list;
}

public List<Object> findAllByIdSaludLaboral(String caseId) {
    Query query = this.em.createNativeQuery("SELECT pcl.id,\n" +
        "    pcl.porcentaje_pcl,\n" +
        "    pcl.emision_pcl_fecha,\n" +
        "    pcl.entidad_emite_pcl,\n" +
        "    pcl.pcl,\n" +
        "    diag.id AS diag,\n" +
        "    pcl.eliminado,\n" +
        "    pcl.entidad_emitida,\n" +
        "    pcl.status_de_calificacion,\n" +
        "    pcl.fecha_calificacion,\n" +
        "    pcl.entidad_emite_calificacion,\n" +
        "    pcl.entidad_emitida_calificacion,\n" +
        "    pcl.observaciones,\n" +
        "    pcl.origen,\n" +
        "    pcl.saludlaboral,\n" +    
        "    diag.id AS id_diagnostico,\n" +
        "    diag.diagnostico AS nombre_diagnostico,\n" +
        "    pcl.origen_pcl,\n" +
        "    pcl.observaciones_pcl\n" +
        "   FROM scm.pcl pcl\n" +
        "     JOIN scm.pcl_diagnostico pcl_diag ON pcl.id = pcl_diag.id_pcl\n" +
        "     JOIN scm.diagnosticos diag ON pcl_diag.id_diagnostico = diag.id\n" +
        "  WHERE diag.pk_case= ?1 AND pcl.eliminado = false AND pcl.saludlaboral = true\n" +
        "  ORDER BY pcl.emision_pcl_fecha;", Pcl.class);

    query.setParameter(1, caseId);
    List<Object> list = query.getResultList();
    System.out.println(query);
    System.out.println(list + "lsta");
    return list;
}
       
      

    public Pcl findById(Long id) {
        Query query = this.em.createQuery("SELECT DISTINCT pc FROM Pcl pc WHERE PC.id = :id ORDER BY PC.id DESC");
        query.setParameter("id", id);
        List<Pcl> list = (List<Pcl>) query.getResultList();
        return list.get(0);
    }

    public Pcl update(Pcl pcl) throws Exception {

        pcl = super.edit(pcl);
        return pcl;

    }

}
