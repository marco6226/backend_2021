/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.sigess.facade.sge;

import co.sigess.entities.sge.Evaluacion;
import co.sigess.reports.sge.ReportDataSource;
import co.sigess.reports.sge.GeneradorReportesDOCX;
import co.sigess.reports.sge.GeneradorReportesHTML;
import co.sigess.reports.sge.GeneradorPlanTrabajoXSLX;
import co.sigess.util.Util;
import java.io.File;
import java.math.BigDecimal;
import java.util.HashMap;
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
public class ReporteSGEFacade {

    @EJB
    private EvaluacionFacade evaluacionFacade;

    @PersistenceContext(unitName = "SIGESS_PU")
    private EntityManager em;

    public File generarReporteEvaluacion(Integer evaluacionId, String type) throws Exception {
        Evaluacion evaluacion = evaluacionFacade.find(evaluacionId);

        Map<String, ReportDataSource> datasources = new HashMap<>();

        switch (type) {
            case "docx":
            case "html": {
                String nombreReporte = "ReporteEvaluacion_" + Util.getFechaId();
                Query query = em.createNativeQuery("SELECT elemento_codigo, elemento_nombre, descripcion_calificacion, round((promedio * 100)::numeric, 2) FROM sge.informe_sge(?1)");
                query.setParameter(1, evaluacion.getId());
                ReportDataSource rds = new ReportDataSource(new String[]{"F{elemento_codigo}", "F{elemento_nombre}", "F{descripcion_calificacion}", "F{promedio}"}, query.getResultList().toArray());
                datasources.put("DS{tblResumen}", rds);

                query = em.createNativeQuery("SELECT elemento_codigo, elemento_nombre, descripcion_calificacion, round((promedio * 100)::numeric, 2) FROM sge.informe_sge(?1)  AS inf WHERE inf.elemento_padre_id IS NULL;");
                query.setParameter(1, evaluacion.getId());
                ReportDataSource chartDesempeño = new ReportDataSource(new String[]{"F{elemento_codigo}", "F{elemento_nombre}", "F{descripcion_calificacion}", "F{promedio}"}, query.getResultList().toArray());
                datasources.put("DS{chartDesempeño}", chartDesempeño);

                query = em.createNativeQuery("SELECT inf.elemento_nombre AS ciclo_nombre,\n"
                        + "        inf.promedio AS ciclo_promedio,\n"
                        + "        inf2.elemento_nombre AS estandar_nombre,\n"
                        + "        round((inf2.promedio * 100)::numeric, 2) AS estandar_promedio\n"
                        + "    FROM sge.informe_sge(?1) as inf\n"
                        + "    INNER JOIN sge.informe_sge(?1) as inf2 ON inf2.elemento_padre_id = inf.elemento_id\n"
                        + "    WHERE inf.elemento_padre_id IS NULL;");
                query.setParameter(1, evaluacion.getId());
                ReportDataSource chartResumen = new ReportDataSource(new String[]{"F{ciclo_nombre}", "F{ciclo_promedio}", "F{estandar_nombre}", "F{estandar_promedio}"}, query.getResultList().toArray());
                datasources.put("DS{chartResumen}", chartResumen);

                Float total = 0.0F;
                for (Object value : chartDesempeño.getValues()) {
                    total += ((BigDecimal) ((Object[]) value)[3]).floatValue();
                }
                total = total / chartDesempeño.getValues().length;

                Map<String, Object> params = new HashMap<>();
                params.put("P{nombreEmpresa}", evaluacion.getEmpresa().getNombreComercial());
                params.put("P{nitEmpresa}", evaluacion.getEmpresa().getNit());
                params.put("P{nombreResponsable}", evaluacion.getNombreResponsable());
                params.put("P{emailResponsable}", evaluacion.getEmailResponsable());
                params.put("P{fechaElaboracion}", evaluacion.getFechaFinalizacion());
                params.put("P{ciudad}", evaluacion.getCiudad());
                params.put("P{direccion}", evaluacion.getDireccion());
                params.put("P{telefono}", evaluacion.getTelefono());
                params.put("P{actividadEconomica}", evaluacion.getActividadEconomica());
                params.put("P{numeroTrabajadores}", evaluacion.getNumeroTrabajadores());
                params.put("P{nombreEvaluador}", evaluacion.getNombreEvaluador());
                params.put("P{licencia}", evaluacion.getLicenciaEvaluador());
                params.put("P{evaluacionId}", evaluacion.getId());
                params.put("P{totalCumplimiento}", total);
                if (type.equals("docx")) {
                    String template = getClass().getResource(Util.RSRC_REPORTE_EVALUACION_SGE).getPath();
                    return GeneradorReportesDOCX.generar(params, datasources, new File(template), nombreReporte);
                } else {
                    String template = getClass().getResource(Util.RSRC_HTML_REPORTE_EVALUACION_SGE).getPath();
                    return GeneradorReportesHTML.generar(params, datasources, new File(template), nombreReporte);
                }
            }
            case "xlsx": {
                String nombreReporte = "CronogramaAutoEvaluacion_" + Util.getFechaId();
                Query query = em.createNativeQuery("select "
                        + "elemento_codigo, "
                        + "elemento_nombre, "
                        + "respuesta_actividad, "
                        + "respuesta_responsable, "
                        + "respuesta_recursos, "
                        + "respuesta_meta "
                        + "from sge.informe_sge(?1) where ok = 'f';");
                query.setParameter(1, evaluacion.getId());
                ReportDataSource desvDS = new ReportDataSource(new String[]{"F{codigo}", "F{elemento}", "F{actividad}", "F{responsable}", "F{recursos}", "F{meta}"}, query.getResultList().toArray());
                datasources.put("DS{planTrabajoDS}", desvDS);
                String template = getClass().getResource(Util.RSRC_PLAN_TRABAJO_SGE).getPath();
                return GeneradorPlanTrabajoXSLX.generar(null, datasources, new File(template), nombreReporte);
            }
            default:
                throw new IllegalArgumentException("No ha especificado el tipo de reporte a generar");
        }
    }

}
