/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.sigess.facade.sge;

import co.sigess.entities.sge.DataSet;
import co.sigess.entities.sge.Elemento;
import co.sigess.entities.sge.Evaluacion;
import co.sigess.entities.sge.IndicadoresData;
import co.sigess.entities.sge.IndicadoresSGE;
import co.sigess.entities.sge.SistemaGestion;
import co.sigess.entities.sge.dto.DesviacionSGEDTO;
import co.sigess.util.Util;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
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
public class EvaluacionFacade extends co.sigess.facade.com.AbstractFacade<Evaluacion> {

    @EJB
    private SistemaGestionFacade sistemaGestionFacade;

    @PersistenceContext(unitName = "SIGESS_PU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public EvaluacionFacade() {
        super(Evaluacion.class);
    }

    @Override
    public Evaluacion create(Evaluacion evaluacion) throws Exception {
        this.comprobarDatos(evaluacion);
        evaluacion.setFechaInicio(Calendar.getInstance());
        return super.create(evaluacion);
    }

    @Override
    public Evaluacion edit(Evaluacion evaluacion) throws Exception {
        this.comprobarDatos(evaluacion);
        evaluacion = super.edit(evaluacion);
        return evaluacion;
    }

    private void comprobarDatos(Evaluacion evaluacion) {
        if (evaluacion.getSistemaGestion() == null || evaluacion.getSistemaGestion().getSistemaGestionPK() == null) {
            throw new IllegalArgumentException("Debe establecerse el sistema de gestión a usar");
        }
        SistemaGestion sg = sistemaGestionFacade.find(evaluacion.getSistemaGestion().getSistemaGestionPK());        
        if (sg == null) {
            throw new IllegalArgumentException("El sistema de gestión seleccionado no es válido");
        }
        evaluacion.setSistemaGestion(sg);
    }

    public List<Evaluacion> findByEmpresa(Integer empresaId, Map<String, String> filter, String sortField, String sortOrder) {
        StringBuilder strQuery = new StringBuilder();
        strQuery.append("SELECT e FROM Evaluacion e WHERE e.empresa.id = :empresaId ORDER BY e.sistemaGestion.nombre, e.fechaInicio DESC");

        Map<String, String> filterParam = new HashMap<>();
        if (filter != null && !filter.isEmpty()) {
            int i = 0;
            for (String key : filter.keySet()) {
                strQuery.append(" AND ")
                        .append("e.")
                        .append(key)
                        .append(" = :param").append(i);
                filterParam.put(("param" + i), filter.get(key));
                i++;
            }
        }
        if (sortField != null) {
            strQuery.append(" ORDER BY e.").append(sortField).append(" ");
        }
        if (sortOrder != null) {
            strQuery.append(" ").append(sortOrder).append(" ");
        }

        Query query = this.em.createQuery(strQuery.toString());
        query.setParameter("empresaId", empresaId);

        for (String key : filterParam.keySet()) {
            query.setParameter(key, Util.isNumber(filterParam.get(key)) ? Integer.parseInt(filterParam.get(key)) : filterParam.get(key));
        }

        List<Evaluacion> list = (List<Evaluacion>) query.getResultList();
        return list;
    }

    public List<IndicadoresSGE> findIndicadoresSGE(Integer empresaId) {
        List<SistemaGestion> sgeList = sistemaGestionFacade.findAllByEmpresa(empresaId);
        List<IndicadoresSGE> isgeList = new ArrayList<>();
        for (SistemaGestion sge : sgeList) {
            IndicadoresSGE isge = new IndicadoresSGE();
            isge.setNombre(sge.getNombre());
            isge.setData(new ArrayList<IndicadoresData>());
            
            IndicadoresData indSge = new IndicadoresData();
            indSge.setLabels(new LinkedHashSet<String>());
            indSge.setDatasets(new ArrayList<DataSet>());
            
            DataSet ds = new DataSet();
            ds.setData(new ArrayList<Double>());
            for (Evaluacion evaluacion : sge.getEvaluacionList()) {
                indSge.getLabels().add(Util.SIMPLE_DATE_FORMAT.format(evaluacion.getFechaInicio().getTime()));
                ds.getData().add(Math.random());
                ds.setLabel(sge.getNombre());
            }
            indSge.getDatasets().add(ds);
            isge.getData().add(indSge);
            
            
            IndicadoresData indElement = new IndicadoresData();
            indElement.setLabels(new LinkedHashSet<String>());
            indElement.setDatasets(new ArrayList<DataSet>());
            for (Elemento elemento : sge.getElementoList()) {
                DataSet dsElem = new DataSet();
                dsElem.setData(new ArrayList<Double>());
                dsElem.setLabel(elemento.getNombre());
                for (Evaluacion evaluacion : sge.getEvaluacionList()) {
                    indElement.getLabels().add(Util.SIMPLE_DATE_FORMAT.format(evaluacion.getFechaInicio().getTime()));
                    dsElem.getData().add(Math.random());
                }
                indElement.getDatasets().add(dsElem);
            }
            isge.getData().add(indElement);
            isgeList.add(isge);
        }
        return isgeList;
    }

    public List<DesviacionSGEDTO> findDesviaciones(Integer evaluacionId) {
        Query query = this.em.createNamedQuery("DesviacionesSGENativeQuery");
        query.setParameter(1, evaluacionId);
        List<DesviacionSGEDTO> list = query.getResultList();
        return list;
    }
    

}
