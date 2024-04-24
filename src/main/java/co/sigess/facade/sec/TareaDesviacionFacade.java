/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.sigess.facade.sec;

import co.sigess.entities.com.TipoMensaje;
import co.sigess.entities.emp.Usuario;
import co.sigess.entities.sec.EstadoTarea;
import co.sigess.entities.sec.EvidencesFiles;
import co.sigess.entities.sec.TareaDesviacion;
import co.sigess.exceptions.UserMessageException;
import co.sigess.facade.com.AbstractFacade;
import co.sigess.util.FileUtil;
import static co.sigess.util.FileUtil.ROOT_DIR;
import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
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
        tareaDB.setEnvioCorreo(entity.getEnvioCorreo());
        return super.edit(tareaDB); //To change body of generated methods, choose Tools | Templates.
    }

    public TareaDesviacion closeTask(TareaDesviacion entity) throws Exception {
        if (entity.getId() == null) {
            throw new IllegalArgumentException("Entity update without id is not possible");
        }

        TareaDesviacion tareaDB = this.find(entity.getId());

        if (entity.getFechaVerificacion() != null) {
            tareaDB.setFechaVerificacion(entity.getFechaVerificacion());
            tareaDB.setUsuarioVerifica(entity.getUsuarioVerifica());
            tareaDB.setObservacionesVerificacion(entity.getObservacionesVerificacion());
            tareaDB.setEvidencesV(entity.getEvidencesV());
        } else {

            tareaDB.setFechaCierre(entity.getFechaCierre());
            tareaDB.setDescripcionCierre(entity.getDescripcionCierre());
            tareaDB.setUsuarioCierre(entity.getUsuarioCierre());
            tareaDB.setEvidences(entity.getEvidences());
            tareaDB.setResponsableAliado(entity.getResponsableAliado());
        }

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

    public String findById(Integer tareaId) {
        String sql = "select \n"
                + "\n"
                + "json_build_object('module',vd.modulo ,\n"
                + "				  \n"
                + "'fecha_reporte', vd.fecha_reporte,\n"
                + "'regional',  regional.nombre,\n"
                + "'area',  area.nombre,\n"
                + "'division',  area.padre_nombre,\n"
                + "'hash_id',  vd.hash_id,\n"
                + "'concepto',  vd.concepto,\n"
                + "'aspecto_causante',  vd.aspecto_causante,\n"
                + "'id',  td.id,\n"
                + "'nombre',  td.nombre,\n"
                + "'descripcion',  td.descripcion,\n"
                + "'tipo_accion',   td.tipo_accion,\n"
                + "'jerarquia',  td.jerarquia,\n"
                + "'usuario',  ee.id,\n"
                + "'empResponsable', ee,\n"
                + "'email',  eu.email,\n"
                + "'fecha_proyectada',  td.fecha_proyectada,\n"
                + "'fecha_realizacion',  td.fecha_realizacion,\n"
                + "'fecha_cierre',  td.fecha_cierre,\n"
                + "'fk_usuario_cierre',    td.fk_usuario_cierre,\n"
                + "'descripcion_cierre',    td.descripcion_cierre,\n"
                + "'observaciones_realizacion',  td.observaciones_realizacion,\n"
                + "'fk_usuario_realiza_id',    td.fk_usuario_realiza_id,\n"
                + "'estado',  td.estado,\n"
                + "'fecha_verificacion',  td.fecha_verificacion,\n"
                + "'observaciones_verificacion',  td.observaciones_verificacion,\n"
                + "'fk_usuario_verifica_id',  td.fk_usuario_verifica_id,\n"
                + "'responsableAliado', td.responsable_aliado,\n"
                + "'division', vd.division,\n"
                + "'localidad', vd.localidad\n"
                + "				 )\n"
                + "	 \n"
                + " \n"
                + "	\n"
                + "	\n"
                + "	\n"
                + "	\n"
                + "	from sec.tarea_desviacion as td\n"
                + "	left join emp.empleado as ee\n"
                + "	on td.fk_emp_responsable_id=ee.id\n"
                + "	left join emp.usuario as eu\n"
                + "	on ee.fk_usuario_id=eu.id	\n"
                + "	inner join sec.analisis_desviacion_tarea_desviacion as adtd\n"
                + "	on td.id=adtd.pk_tarea_desviacion_id\n"
                + "	inner join sec.desviacion_analisis_desviacion as dad\n"
                + "	on adtd.pk_analisis_desviacion_id=dad.fk_analisis_desviacion_id	\n"
                + "	left join sec.vw_desviacion as vd\n"
                + "	on vd.hash_id=dad.pk_desviacion_hash_id\n"
                + "               left join inp.calificacion as cali\n"
		+ "		on vd.id=cali.fk_inspeccion_id and  vd.elemento=cali.fk_elemento_inspeccion_id\n"		
                + "	left join emp.area as area\n"
                + "	on vd.fk_area_id=area.id\n"
                + "	left join emp.area as regional\n"
                + "	on area.fk_area_padre_id=regional.id\n"
                + "	where td.id= ?1";
        Query query = this.em.createNativeQuery(sql);
        query.setParameter(1, tareaId);
        sql = query.getResultList().get(0).toString();
        return sql;
    }

    public String findWithDetails(Integer idEmp,String[] areasId) {
        int size = areasId.length;
      int [] arr = new int [size];
      for(int i=0; i<size; i++) {
         arr[i] = Integer.parseInt(areasId[i]); 
      }
    
    String sql = "SELECT *  from sec.tareaporareas( "+idEmp+"::int ,ARRAY" + (Arrays.toString(arr)) + "::int[] ) ";
        Query query = this.em.createNativeQuery(sql);
                query.setParameter(1, idEmp);
                query.setParameter(2, areasId);
        sql = query.getResultList().toString();
        
           
        return sql;
    }

     public String findWithDetailsByEmpleado(Integer idEmp,Integer Id) {
        String sql = "select \n"
                + "\n"
                + " json_build_object("
                + "'module',vd.modulo ,\n"
                + "'fecha_reporte', vd.fecha_reporte,\n"
                + "'regional',  regional.nombre,\n"
                + "'area',  area.nombre,\n"
                + "'division',  area.padre_nombre,\n"
                + "'hash_id',  vd.hash_id,\n"
                + "'concepto',  vd.concepto,\n"
                + "'aspecto_causante',  vd.aspecto_causante,\n"
                + "'id',  td.id,\n"
                + "'nombre',  td.nombre,\n"
                + "'descripcion',  td.descripcion,\n"
                + "'tipo_accion',   td.tipo_accion,\n"
                + "'jerarquia',  td.jerarquia,\n"
                + "'usuario',  ee.id,\n"
                + "'email',  eu.email,\n"
                + "'empResponsable', ee,\n"
                + "'fecha_proyectada',  td.fecha_proyectada,\n"
                + "'fecha_realizacion',  td.fecha_realizacion,\n"
                + "'fecha_cierre',  td.fecha_cierre,\n"
                + "'fk_usuario_cierre',    td.fk_usuario_cierre,\n"
                + "'descripcion_cierre',    td.descripcion_cierre,\n"
                + "'observaciones_realizacion',  td.observaciones_realizacion,\n"
                + "'fk_usuario_realiza_id',    td.fk_usuario_realiza_id,\n"
                + "'estado',  td.estado,\n"
                + "'trackings', Count(st.fk_tarea_id) ,"
                + "'fecha_verificacion',  td.fecha_verificacion,\n"
                + "'observaciones_verificacion',  td.observaciones_verificacion,\n"
                + "'fk_usuario_verifica_id',  td.fk_usuario_verifica_id\n"
                + "				 )\n"
                + "	from sec.tarea_desviacion as td\n"
                + "	left join emp.empleado as ee\n"
                + "	on td.fk_emp_responsable_id=ee.id\n"
                + "     left join sec.seguimiento_tarea as st\n"
                + "     on td.id = st.fk_tarea_id"
                + "	left join emp.usuario as eu\n"
                + "	on ee.fk_usuario_id=eu.id	\n"
                + "	inner join sec.analisis_desviacion_tarea_desviacion as adtd\n"
                + "	on td.id=adtd.pk_tarea_desviacion_id\n"
                + "	inner join sec.desviacion_analisis_desviacion as dad\n"
                + "	on adtd.pk_analisis_desviacion_id=dad.fk_analisis_desviacion_id	\n"
                + "	left join sec.vw_desviacion as vd\n"
                + "	on vd.hash_id=dad.pk_desviacion_hash_id\n"
                + "               left join inp.calificacion as cali\n"
		+ "		on vd.id=cali.fk_inspeccion_id and  vd.elemento=cali.fk_elemento_inspeccion_id\n"		
                + "	left join emp.area as area\n"
                + "	on vd.fk_area_id=area.id\n"
                + "	left join emp.area as regional\n"
                + "	on area.fk_area_padre_id=regional.id\n"
                + "         where td.fk_empresa_id= ?1\n"
                +"          and eu.id= ?2\n"
                + " GROUP by vd.modulo, \n"
                + "vd.fecha_reporte, \n"
                + "regional.nombre, \n"
                + "area.nombre, \n"
                + "area.padre_nombre, \n"
                + "vd.hash_id, \n"
                + "vd.concepto, \n"
                + "                vd.aspecto_causante, \n"
                + "                td.id, \n"
                + "                td.nombre, \n"
                + "                td.descripcion, \n"
                + "                td.tipo_accion, \n"
                + "                td.jerarquia, \n"
                + "                ee.id,                \n"
                + "                eu.email, \n"
                + "                td.fecha_proyectada, \n"
                + "                td.fecha_realizacion, \n"
                + "                td.fecha_cierre, \n"
                + "                td.fk_usuario_cierre, \n"
                + "                td.descripcion_cierre, \n"
                + "                td.observaciones_realizacion, \n"
                + "                td.fk_usuario_realiza_id,\n"
                + "                td.estado, \n"
                + "                st.fk_tarea_id ,\n"
                + "                td.fecha_verificacion, \n"
                + "                td.observaciones_verificacion, \n"
                + "                td.fk_usuario_verifica_id";
        Query query = this.em.createNativeQuery(sql);
                query.setParameter(1, idEmp);
                query.setParameter(2, Id);

        sql = query.getResultList().toString();
        return sql;
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

    public HashMap<String, List<String>> getImages(int tareaId) {
        TareaDesviacion tareaDB = super.find(tareaId);
        if (tareaDB == null) {
            throw new UserMessageException("No es posible reportar la tarea", "La tarea que intenta reportar no existe", TipoMensaje.warn);
        }

        String sql = ""
                + "select  \n"
                + " ado.id as ruta \n"
                + "from sec.tarea_desviacion as td\n"
                + "inner join sec.analisis_desviacion_tarea_desviacion as adtd\n"
                + "on td.id=adtd.pk_tarea_desviacion_id\n"
                + "inner join sec.desviacion_analisis_desviacion as dad\n"
                + "on adtd.pk_analisis_desviacion_id=dad.fk_analisis_desviacion_id	\n"
                + "left join sec.vw_desviacion as vd\n"
                + "on vd.hash_id=dad.pk_desviacion_hash_id\n"
                + "left join inp.calificacion as cali\n"
                + "on vd.id=cali.fk_inspeccion_id and  vd.elemento=cali.fk_elemento_inspeccion_id\n"
                + "left join inp.documento_calificacion as dc\n"
                + "on dc.fk_calificacion_id=cali.id\n"
                + "left join ado.documento as ado\n"
                + "on dc.fk_documento_id=ado.id\n"
                + "where td.id= ?1 and ruta is not null";

        Query query = this.em.createNativeQuery(sql);
        query.setParameter(1, tareaId);
        List<String> dtoList = query.setMaxResults(3).getResultList();
        System.out.print(dtoList.size());
        HashMap<String, List<String>> files = new HashMap<String, List<String>>();
        List<String> List = null;
        List<String> file = new ArrayList<String>();
        files.put("error", List);
       for (int i = 0; i <= dtoList.size(); i++) {
             // System.out.print(dtoList.get(i));
              try {
                  file.add(dtoList.get(i));
              } catch (Exception ex) {
                  //System.out.print("Erro" + ex.getMessage());
             }
          }
           files.put("files", dtoList);
            return files;
    }

     public HashMap<String, List<String>> getImages(int tareaId, String modulo) {
        TareaDesviacion tareaDB = super.find(tareaId);
        if (tareaDB == null) {
            throw new UserMessageException("No es posible reportar la tarea", "La tarea que intenta reportar no existe", TipoMensaje.warn);
        }
        
                String sql = ""
                + "select  \n"
                + " ado.id as ruta \n"
                + "from sec.tarea_desviacion as td\n"
                + "inner join sec.analisis_desviacion_tarea_desviacion as adtd\n"
                + "on td.id=adtd.pk_tarea_desviacion_id\n"
                + "inner join sec.desviacion_analisis_desviacion as dad\n"
                + "on adtd.pk_analisis_desviacion_id=dad.fk_analisis_desviacion_id	\n"
                + "left join sec.vw_desviacion as vd\n"
                + "on vd.hash_id=dad.pk_desviacion_hash_id\n";
                        
            switch(modulo){
                
                case "RAI":
                    sql+= "left join auc.documento_observacion as dc\n"
                    + "on dc.fk_observacion_id=vd.id\n"
                    + "left join ado.documento as ado\n"
                    + "on dc.fk_documento_id=ado.id\n"
                    + "where td.id= ?1 and ruta is not null";
                     break;
                case "OBS":
                    sql+= "left join auc.documento_observacion as dc\n"
                    + "on dc.fk_observacion_id=vd.id\n"
                    + "left join ado.documento as ado\n"
                    + "on dc.fk_documento_id=ado.id\n"
                    + "where td.id= ?1 and ruta is not null";
                    break;
                case "INP":
                    sql+= "left join inp.calificacion as cali\n"
                    + "on vd.id=cali.fk_inspeccion_id and  vd.elemento=cali.fk_elemento_inspeccion_id\n"
                    + "left join inp.documento_calificacion as dc\n"
                    + "on dc.fk_calificacion_id=cali.id\n"
                    + "left join ado.documento as ado\n"
                    + "on dc.fk_documento_id=ado.id\n"
                    + "where td.id= ?1 and ruta is not null";
                    break;
                default:
                    break;
            }
                              
        System.out.print(sql);
        Query query = this.em.createNativeQuery(sql);
        query.setParameter(1, tareaId);
        List<String> dtoList = query.setMaxResults(3).getResultList();
        System.out.print(dtoList.size());
        HashMap<String, List<String>> files = new HashMap<String, List<String>>();
        List<String> List = null;
        List<String> file = new ArrayList<String>();
        files.put("error", List);
       for (int i = 0; i <= dtoList.size(); i++) {
             // System.out.print(dtoList.get(i));
              try {
                  file.add(dtoList.get(i));
              } catch (Exception ex) {
                  //System.out.print("Erro" + ex.getMessage());
             }
          }
           files.put("files", dtoList);
            return files;
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
    
    public Object obtenerSeguimientoTarea(Integer id) throws Exception {
        String q = "SELECT td.id, td.fecha_cierre, td.fecha_proyectada, count(st.fk_tarea_id)\n" +
                    "FROM sec.tarea_desviacion AS td\n" +
                    "LEFT JOIN sec.seguimiento_tarea AS st\n" +
                    "ON td.id = st.fk_tarea_id\n" +
                    "GROUP BY td.id\n" +
                    "HAVING td.id = ?1";
        Query query = this.em.createNativeQuery(q);
        query.setParameter(1, id);
        Object object = query.getSingleResult();
        return object;
    }
    
    public static boolean isValidRuta(List<EvidencesFiles> evidences) {
        
        String[] forbiddenPatterns = {"..", ":", "%2e%2e", "\\", "//"};    
        
        for (EvidencesFiles evidence : evidences) {
            if (evidence.getRuta() != null) {
                for (String pattern : forbiddenPatterns) {
                    if (evidence.getRuta().contains(pattern)) {
                        return false;
                    }
                }
            }
        }        
        return true;
    }

}
