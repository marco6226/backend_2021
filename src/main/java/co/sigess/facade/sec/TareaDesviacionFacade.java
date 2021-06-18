/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.sigess.facade.sec;

import co.sigess.entities.com.TipoMensaje;
import co.sigess.entities.emp.Usuario;
import co.sigess.entities.sec.EstadoTarea;
import co.sigess.entities.sec.TareaDesviacion;
import co.sigess.exceptions.UserMessageException;
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
public class TareaDesviacionFacade extends AbstractFacade<TareaDesviacion> {

    @PersistenceContext(unitName = "SIGESS_PU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public TareaDesviacionFacade() {
        super(TareaDesviacion.class);
    }

    @Override
    public TareaDesviacion create(TareaDesviacion tareaDesviacion) throws Exception {
        tareaDesviacion.setEstado(EstadoTarea.NUEVO);
        tareaDesviacion = super.create(tareaDesviacion);
        return tareaDesviacion;
    }

    public List<TareaDesviacion> findByAnalisisEmpresa(Integer analisisId, Integer empresaId) {
        Query query = this.em.createQuery("SELECT td FROM TareaDesviacion td INNER JOIN td.analisisDesviacionList anaDesv WHERE td.empresa.id = ?1 AND anaDesv.id = ?2 ORDER BY td.id DESC");
        query.setParameter(1, empresaId);
        query.setParameter(2, analisisId);
        List<TareaDesviacion> list = (List<TareaDesviacion>) query.getResultList();
        return list;
    }

    public TareaDesviacion remove(Integer tareaId) throws Exception {
        TareaDesviacion tarea = this.find(tareaId);
        if (tarea.getEstado() == null || tarea.getEstado().equals(EstadoTarea.NUEVO)) {
            super.remove(tarea);
            return tarea;
        } else {
            throw new UserMessageException("No es posible eliminar la tarea", "La tarea se encuentra en un estado en el que no es posible eliminar", TipoMensaje.warn);
        }
    }

    @Override
    public TareaDesviacion edit(TareaDesviacion entity) throws Exception {
        if (entity.getId() == null) {
            throw new IllegalArgumentException("Entity update without id is not possible");
        }
        TareaDesviacion tareaDB = this.find(entity.getId());

        if (tareaDB.getEstado() != null && !tareaDB.getEstado().equals(EstadoTarea.NUEVO)) {
            throw new UserMessageException("No es posible actualizar la tarea", "La tarea se encuentra en un estado en el que no es posible actualizar", TipoMensaje.warn);
        }

        tareaDB.setNombre(entity.getNombre());
        tareaDB.setDescripcion(entity.getDescripcion());
        tareaDB.setTipoAccion(entity.getTipoAccion());
        tareaDB.setFechaProyectada(entity.getFechaProyectada());
           tareaDB.setEmpResponsable(entity.getEmpResponsable());
        tareaDB.setAreaResponsable(entity.getAreaResponsable());
        tareaDB.setjerarquia(entity.getjerarquia());
        return super.edit(tareaDB); //To change body of generated methods, choose Tools | Templates.
    }

    public List<TareaDesviacion> findByUsuarioEmpresa(Integer usuarioId, Integer empresaId) {
        String sql = "select t.*\n"
                + "	from sec.tarea_desviacion t\n"
                + "	inner join emp.empleado e on e.fk_area_id = t.fk_area_responsable_id\n"
                + "	where t.fk_empresa_id = ?1 and e.fk_usuario_id = ?2	\n"
                + "union\n"
                + "select t.*\n"
                + "	from sec.tarea_desviacion t\n"
                + "	inner join emp.usuario_empresa ue on ue.pk_empresa_id = ?1\n"
                + "	inner join emp.usuario u on u.id = ue.pk_usuario_id\n"
                + "	left join emp.empleado e on e.fk_usuario_id = u.id\n"
                + "	where t.fk_empresa_id = ?1 and e.id is null and u.id = ?2";
        Query query = this.em.createNativeQuery(sql, TareaDesviacion.class);
        query.setParameter(1, empresaId);
        query.setParameter(2, usuarioId);
        List<TareaDesviacion> list = (List<TareaDesviacion>) query.getResultList();
        return list;
    }

    public TareaDesviacion reportarCumplimiento(TareaDesviacion tarea, Usuario usuario) throws Exception {
        TareaDesviacion tareaDB = super.find(tarea.getId());
        if (tareaDB == null) {
            throw new UserMessageException("No es posible reportar la tarea", "La tarea que intenta reportar no existe", TipoMensaje.warn);
        }
        tareaDB.setEstado(EstadoTarea.REALIZADA);
        tareaDB.setUsuarioRealiza(usuario);
        tareaDB.setRealizada(Boolean.TRUE);
        tareaDB.setFechaRealizacion(new Date());
        tareaDB.setObservacionesRealizacion(tarea.getObservacionesRealizacion());

        tareaDB = super.edit(tareaDB);
        return tareaDB;
    }

    public TareaDesviacion reportarVerificacion(TareaDesviacion tarea, Usuario usuario) throws Exception {
        TareaDesviacion tareaDB = super.find(tarea.getId());
        if (tareaDB == null) {
            throw new UserMessageException("No es posible verificar la tarea", "La tarea que intenta verificar no existe", TipoMensaje.warn);
        }
        tareaDB.setEstado(EstadoTarea.FINALIZADA);
        tareaDB.setUsuarioVerifica(usuario);
        tareaDB.setVerificada(Boolean.TRUE);
        tareaDB.setFechaVerificacion(new Date());
        tareaDB.setObservacionesVerificacion(tarea.getObservacionesVerificacion());

        tareaDB = super.edit(tareaDB);
        return tareaDB;
    }

}
