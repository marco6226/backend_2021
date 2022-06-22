/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.sigess.facade.sec;

import co.sigess.entities.ado.Documento;
import co.sigess.entities.com.TipoMensaje;
import co.sigess.entities.sec.AnalisisDesviacion;
import co.sigess.entities.sec.Desviacion;
import co.sigess.entities.sec.EstadoTarea;
import co.sigess.entities.sec.TareaDesviacion;
import co.sigess.exceptions.UserMessageException;
import co.sigess.facade.com.AbstractFacade;
import java.util.ArrayList;
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
public class AnalisisDesviacionFacade extends AbstractFacade<AnalisisDesviacion> {

    @PersistenceContext(unitName = "SIGESS_PU")
    private EntityManager em;

    @EJB
    private TareaDesviacionFacade tareaDesvFacade;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public AnalisisDesviacionFacade() {
        super(AnalisisDesviacion.class);
    }

    @Override
    public AnalisisDesviacion create(AnalisisDesviacion analisisDesviacion) throws Exception {
        if (analisisDesviacion.getDesviacionesList() == null || analisisDesviacion.getDesviacionesList().isEmpty()) {
            throw new UserMessageException("El análisis realizado debe contener al menos una desviación", "", TipoMensaje.warn);
        }
        if (analisisDesviacion.getAnalisisCosto() != null) {
            analisisDesviacion.getAnalisisCosto().setAnalisisDesviacion(analisisDesviacion);
        }

        // Comprueba si al menos una de las desviaciones ya fue investigada
        List<String> desvIdList = new ArrayList<>();
        analisisDesviacion.getDesviacionesList().forEach((desv) -> desvIdList.add(desv.getHashId()));
        Query q = this.em.createQuery("SELECT desv FROM Desviacion desv WHERE desv.hashId IN ?1 AND desv.analisisId IS NOT NULL");
        q.setParameter(1, desvIdList);
        if (!q.getResultList().isEmpty()) {
            throw new UserMessageException(
                    "Investigación previa existente",
                    "Ya se encuentra registrada una investigación de los hallazgos a registrar",
                    TipoMensaje.info
            );
        }

        super.create(analisisDesviacion);

        if (analisisDesviacion.getTareaDesviacionList() != null) {
            for (TareaDesviacion tarea : analisisDesviacion.getTareaDesviacionList()) {
                tarea.setAnalisisDesviacionList(new ArrayList<>());
                tarea.getAnalisisDesviacionList().add(analisisDesviacion);
                tarea.setEmpresa(analisisDesviacion.getEmpresa());
                tareaDesvFacade.create(tarea);
            }
        }

        return analisisDesviacion;
    }

    @Override
    public AnalisisDesviacion edit(AnalisisDesviacion anDesv) throws Exception {
        if (anDesv.getDesviacionesList() == null || anDesv.getDesviacionesList().isEmpty()) {
            throw new UserMessageException("El análisis realizado debe contener al menos una desviación", "", TipoMensaje.warn);
        }
        if (anDesv.getAnalisisCosto() != null) {
            anDesv.getAnalisisCosto().setAnalisisDesviacionId(anDesv.getId());
            anDesv.getAnalisisCosto().setAnalisisDesviacion(anDesv);
        }
        AnalisisDesviacion adDB = this.find(anDesv.getId());
        adDB.setAnalisisCosto(anDesv.getAnalisisCosto());
        adDB.setCausaInmediataList(anDesv.getCausaInmediataList());
        adDB.setCausaRaizList(anDesv.getCausaRaizList());
        adDB.setCausasAdminList(anDesv.getCausasAdminList());
        adDB.setFechaModificacion(new Date());
        adDB.setDesviacionesList(anDesv.getDesviacionesList());
        adDB.setObservacion(anDesv.getObservacion());
        adDB.setParticipantes(anDesv.getParticipantes());
        adDB.setFlow_chart(anDesv.getFlow_chart());
        adDB.setFactor_causal(anDesv.getFactor_causal());
        adDB.setUsuarioModificaId(anDesv.getUsuarioModificaId());
        adDB.setEmpresa(anDesv.getEmpresa());
        adDB.setIncapacidades(anDesv.getIncapacidades());
        adDB.setComplementaria(anDesv.getComplementaria());
        adDB = super.edit(adDB); //To change body of generated methods, choose Tools | Templates.

        // Si el listado de tareas es null se inicializa con un array vacio para evitar NullPointerException
        if (anDesv.getTareaDesviacionList() == null) {
            anDesv.setTareaDesviacionList(new ArrayList<>());
        }

        // Busca tareas en DB que no esten en la lista de tareas recibidas,
        // y las elimina solo si estan en estado NUEVO
        for (TareaDesviacion tareaDB : adDB.getTareaDesviacionList()) {
            if (tareaDB.getEstado().equals(EstadoTarea.NUEVO) && !anDesv.getTareaDesviacionList().contains(tareaDB)) {
                tareaDesvFacade.remove(tareaDB);
            }
        }

        for (TareaDesviacion tarea : anDesv.getTareaDesviacionList()) {
            // Si la tarea tiene id = null, se asume como una tarea nueva y se crea
            if (tarea.getId() == null) {
                tarea.setAnalisisDesviacionList(new ArrayList<>());
                tarea.getAnalisisDesviacionList().add(adDB);
                tarea.setEmpresa(adDB.getEmpresa());
                tareaDesvFacade.create(tarea);
            } else {
                // Si la tarea tiene id, se asume como ya creada y se intenta actualizar
                int idx = adDB.getTareaDesviacionList().indexOf(tarea);
                if (idx > -1) {
                    // Si la tarea existe en DB, se actualizan los datos de la version en BD
                    TareaDesviacion tareaDB = adDB.getTareaDesviacionList().get(idx);
                    if (tareaDB.getEstado().equals(EstadoTarea.NUEVO)) {
                        tareaDB.setAreaResponsable(tarea.getAreaResponsable());
                        tareaDB.setDescripcion(tarea.getDescripcion());
                        tareaDB.setFechaProyectada(tarea.getFechaProyectada());
                        tareaDB.setNombre(tarea.getNombre());
                        tareaDB.setjerarquia(tarea.getjerarquia());
                        tareaDB.setModulo(tarea.getModulo());
                        tareaDB.setEmpResponsable(tarea.getEmpResponsable());
                        tareaDB.setCodigo(tarea.getCodigo());
                        tareaDB.setTipoAccion(tarea.getTipoAccion());
                        tareaDesvFacade.edit(tareaDB);
                    }
                }
            }
        }

        return anDesv;
    }

    public List<AnalisisDesviacion> findByEmpresa(Integer empresId) {
        Query query = this.em.createQuery("SELECT ad FROM AnalisisDesviacion ad WHERE ad.empresa.id = ?1 ORDER BY ad.fechaElaboracion DESC");
        query.setParameter(1, empresId);
        List<AnalisisDesviacion> list = (List<AnalisisDesviacion>) query.getResultList();
        return list;
    }

    public List<AnalisisDesviacion> findByTarea(Integer tareaId, Integer empresaId) {
        Query query = this.em.createQuery("SELECT ad FROM AnalisisDesviacion ad JOIN ad.tareaDesviacionList tdl WHERE ad.empresa.id = ?1 AND tdl.id = ?2 ORDER BY ad.fechaElaboracion DESC");
        query.setParameter(1, empresaId);
        query.setParameter(2, tareaId);
        List<AnalisisDesviacion> list = (List<AnalisisDesviacion>) query.getResultList();
        return list;
    }

    public void relacionarDocumento(Documento documento, Integer analisisId) throws Exception {
        AnalisisDesviacion ad = this.find(analisisId);
        List<Documento> list = ad.getDocumentosList();
        if (list == null) {
            list = new ArrayList<>();
        }
        list.add(documento);
        super.edit(ad);
    }

    public void retirarDocumento(Documento documento) throws Exception {
        Query q = this.em.createQuery("SELECT c FROM AnalisisDesviacion c JOIN c.documentosList d WHERE d.id = ?1 ");
        q.setParameter(1, documento.getId());
        AnalisisDesviacion analisis = (AnalisisDesviacion) q.getSingleResult();
        analisis.getDocumentosList().remove(documento);
        super.edit(analisis);
    }

}
