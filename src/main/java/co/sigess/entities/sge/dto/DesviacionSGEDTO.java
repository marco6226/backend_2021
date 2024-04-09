/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.sigess.entities.sge.dto;

import java.io.Serializable;
import javax.persistence.ColumnResult;
import javax.persistence.ConstructorResult;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedNativeQuery;
import javax.persistence.SqlResultSetMapping;

/**
 *
 * @author fmoreno
 */
@NamedNativeQuery(
        name = "DesviacionesSGENativeQuery",
        query = "select "
        + "respuesta_id AS respuesta_id, "
        + "respuesta_actividad , "
        + "respuesta_responsable , "
        + "respuesta_recursos , "
        + "respuesta_meta , "
        + "elemento_codigo, "
        + "elemento_nombre, "
        + "descripcion_calificacion, "
        + "despreciable, "
        + "nombre_calificacion, "
        + "calificacion, "
        + "maximo_posible, "
        + "ok "
        + "from sge.informe_sge(?1) where ok = 'f';",
        resultSetMapping = "DesviacionSGEmapping"
)
@SqlResultSetMapping(
        name = "DesviacionSGEmapping",
        classes = @ConstructorResult(
                targetClass = DesviacionSGEDTO.class,
                columns = {
                    @ColumnResult(name = "respuesta_id")
                    ,
            @ColumnResult(name = "respuesta_actividad", type = String.class)
                    ,
            @ColumnResult(name = "respuesta_responsable", type = String.class)
                    ,
            @ColumnResult(name = "respuesta_recursos", type = String.class)
                    ,
            @ColumnResult(name = "respuesta_meta", type = String.class)
                    ,
            @ColumnResult(name = "elemento_codigo", type = String.class)
                    ,
            @ColumnResult(name = "elemento_nombre", type = String.class)
                    ,
            @ColumnResult(name = "descripcion_calificacion", type = String.class)
                    ,
            @ColumnResult(name = "despreciable", type = Boolean.class)
                    ,
            @ColumnResult(name = "nombre_calificacion", type = String.class)
                    ,
            @ColumnResult(name = "calificacion", type = Double.class)
                    ,   
            @ColumnResult(name = "maximo_posible", type = Double.class)
                    ,
            @ColumnResult(name = "ok", type = Boolean.class)}
        )
)
@Entity
public class DesviacionSGEDTO implements Serializable {

    @Id
    private Long respuestaId;    
    private String actividad;
    private String responsable;
    private String recursos;
    private String meta;
    private String elementoCodigo;
    private String elementoNombre;
    private String descripcionCalificacion;
    private Boolean despreciable;
    private String nombreCalificacion;
    private Double calificacion;
    private Double maximoPosible;
    private Boolean ok;

    public DesviacionSGEDTO() {
    }

    public DesviacionSGEDTO(Long respuestaId, String actividad, String responsable, String recursos, String meta, String elementoCodigo, String elementoNombre, String descripcionCalificacion, Boolean despreciable, String nombreCalificacion, Double calificacion, Double maximoPosible, Boolean ok) {
        this.respuestaId = respuestaId;
        this.actividad = actividad;
        this.responsable = responsable;
        this.recursos = recursos;
        this.meta = meta;
        this.elementoCodigo = elementoCodigo;
        this.elementoNombre = elementoNombre;
        this.descripcionCalificacion = descripcionCalificacion;
        this.despreciable = despreciable;
        this.nombreCalificacion = nombreCalificacion;
        this.calificacion = calificacion;
        this.maximoPosible = maximoPosible;
        this.ok = ok;
    }


    public String getElementoCodigo() {
        return elementoCodigo;
    }

    public void setElementoCodigo(String elementoCodigo) {
        this.elementoCodigo = elementoCodigo;
    }

    public String getElementoNombre() {
        return elementoNombre;
    }

    public void setElementoNombre(String elementoNombre) {
        this.elementoNombre = elementoNombre;
    }

    public String getDescripcionCalificacion() {
        return descripcionCalificacion;
    }

    public void setDescripcionCalificacion(String descripcionCalificacion) {
        this.descripcionCalificacion = descripcionCalificacion;
    }

    public Boolean getDespreciable() {
        return despreciable;
    }

    public void setDespreciable(Boolean despreciable) {
        this.despreciable = despreciable;
    }

    public Long getRespuestaId() {
        return respuestaId;
    }

    public void setRespuestaId(Long respuestaId) {
        this.respuestaId = respuestaId;
    }

    public String getNombreCalificacion() {
        return nombreCalificacion;
    }

    public void setNombreCalificacion(String nombreCalificacion) {
        this.nombreCalificacion = nombreCalificacion;
    }

    public Double getCalificacion() {
        return calificacion;
    }

    public void setCalificacion(Double calificacion) {
        this.calificacion = calificacion;
    }

    public Double getMaximoPosible() {
        return maximoPosible;
    }

    public void setMaximoPosible(Double maximoPosible) {
        this.maximoPosible = maximoPosible;
    }

    public Boolean getOk() {
        return ok;
    }

    public void setOk(Boolean ok) {
        this.ok = ok;
    }

    public String getActividad() {
        return actividad;
    }

    public void setActividad(String actividad) {
        this.actividad = actividad;
    }

    public String getResponsable() {
        return responsable;
    }

    public void setResponsable(String responsable) {
        this.responsable = responsable;
    }

    public String getRecursos() {
        return recursos;
    }

    public void setRecursos(String recursos) {
        this.recursos = recursos;
    }

    public String getMeta() {
        return meta;
    }

    public void setMeta(String meta) {
        this.meta = meta;
    }

}
