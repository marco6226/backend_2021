/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.sigess.facade.sge;

import co.sigess.entities.com.TipoMensaje;
import co.sigess.entities.sge.Elemento;
import co.sigess.entities.sge.EstadoEvaluacion;
import co.sigess.entities.sge.Evaluacion;
import co.sigess.entities.sge.Respuesta;
import co.sigess.exceptions.UserMessageException;
import java.util.Date;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author fmoreno
 */
@Stateless
public class RespuestaFacade extends AbstractFacade<Respuesta> {

    @EJB
    private EvaluacionFacade evaluacionFacade;
    @EJB
    private ElementoFacade elementoFacade;

    @PersistenceContext(unitName = "SIGESS_PU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public RespuestaFacade() {
        super(Respuesta.class);
    }

    public EstadoEvaluacion adicionarRespuesta(Respuesta respuesta) {
        if (respuesta.getEvaluacion() == null) {
            throw new UserMessageException("ERROR 30RESP001", "No se ha especificado la evaluación de la respuesta a actualizar", TipoMensaje.error);
        }
        if (respuesta.getOpcionRespuesta() == null) {
            throw new UserMessageException("ERROR 30RESP002", "No se ha especificado la opción seleccionada de la respuesta", TipoMensaje.error);
        }
        if (respuesta.getOpcionRespuesta().getElemento() == null) {
            throw new UserMessageException("ERROR 30RESP003", "No se ha especificado el elemento de la opción de respuesta", TipoMensaje.error);
        }
        Elemento elemento = elementoFacade.find(respuesta.getOpcionRespuesta().getElemento().getId());
        if (elemento == null) {
            throw new UserMessageException("ERROR 30RESP004", "Elemento de opcion de respuesta no válido", TipoMensaje.error);
        }
        if (!elemento.getOpcionRespuestaList().contains(respuesta.getOpcionRespuesta())) {
            throw new UserMessageException("ERROR 30RESP005", "Opcion de respuesta no válida para elemento", TipoMensaje.error);
        }

        Evaluacion evaluacion = evaluacionFacade.find(respuesta.getEvaluacion().getId());
        if (evaluacion == null) {
            throw new UserMessageException("ERROR 30RESP006", "Evaluación especificada no válida", TipoMensaje.error);
        }

        if (evaluacion.getFechaFinalizacion() != null) {
            throw new UserMessageException("Evaluación finalizada", "No es posible modificar las respuestas.", TipoMensaje.warn);
        }

        deleteByElemento(elemento, evaluacion);
        super.create(respuesta);
        this.em.flush();

        Query query = this.em.createNativeQuery("SELECT  ee.numero_respuestas \"numeroRespuestas\",\n"
                + "        ee.numero_preguntas AS \"numeroPreguntas\"\n"
                + "    FROM sge.estado_evaluacion ee\n"
                + "    WHERE ee.evaluacion_id = ?1");
        query.setParameter("1", evaluacion.getId());

        Object[] res = (Object[]) query.getSingleResult();
        EstadoEvaluacion estadoEvaluacion = new EstadoEvaluacion(((Long) res[0]).intValue(), (Integer) res[1]);
        if (estadoEvaluacion.isFinalizado()) {
            query = this.em.createNativeQuery("UPDATE sge.evaluacion SET fecha_finalizacion = NOW() WHERE id = ?1");
            query.setParameter("1", evaluacion.getId());
            query.executeUpdate();
        }
        return estadoEvaluacion;
    }

    private void deleteByElemento(Elemento elemento, Evaluacion evaluacion) {
        Query query = this.em.createQuery("DELETE FROM Respuesta r WHERE r.opcionRespuesta.elemento.id = :elementoId AND r.evaluacion.id = :evaluacionId");
        query.setParameter("elementoId", elemento.getId());
        query.setParameter("evaluacionId", evaluacion.getId());
        query.executeUpdate();
    }

    @Override
    public Respuesta edit(Respuesta resp) {
        Respuesta respDB = this.find(resp.getId());
        respDB.setActividad(resp.getActividad());
        respDB.setMeta(resp.getMeta());
        respDB.setRecursos(resp.getRecursos());
        respDB.setResponsable(resp.getResponsable());
        return super.edit(respDB); //To change body of generated methods, choose Tools | Templates.
    }

    public List<Respuesta> findAllByEvaluacion(Integer evaluacionId) {
        Query query = this.em.createQuery("SELECT r from Respuesta r WHERE r.evaluacion.id = :evaluacionId");
        query.setParameter("evaluacionId", evaluacionId);
        List<Respuesta> list = (List<Respuesta>) query.getResultList();
        return list;
    }

}
