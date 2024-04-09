/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.sigess.entities.sec;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.Date;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Size;

/**
 *
 * @author fmoreno
 */
@Entity
@Table(name = "vw_desviacion_inspeccion", schema = "sec")
@NamedQueries({
    @NamedQuery(name = "DesviacionInspeccion.findAll", query = "SELECT d FROM DesviacionInspeccion d")})
public class DesviacionInspeccion implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @Size(max = 20)
    @Column(name = "id")
    private String id;
    
    @Size(max = 20)
    @Column(name = "hash_id")
    private String hashId;
    
    @Column(name = "fk_calificacion_id")
    private BigInteger fkCalificacionId;
    
    @Column(name = "fk_empresa_id")
    private Integer empresaId;
    
    @Column(name = "fecha_realizada")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaRealizada;
    
    @Size(max = 45)
    @Column(name = "nivel_superior")
    private String nivelSuperior;
    
    @Size(max = 45)
    @Column(name = "dependencia_area")
    private String dependenciaArea;
    
    @Size(max = 45)
    @Column(name = "area_inspeccion")
    private String areaInspeccion;
    
    @Column(name = "area_inspeccion_id")
    private BigInteger areaInspeccionId;
    
    @Size(max = 255)
    @Column(name = "nombre_lista_inspeccion")
    private String nombreListaInspeccion;
    
    @Column(name = "version_lista_inspeccion")
    private Integer versionListaInspeccion;
    
    @Size(max = 255)
    @Column(name = "tipo_grupo")
    private String tipoGrupo;
    
    @Size(max = 255)
    @Column(name = "grupo_elemento")
    private String grupoElemento;
    
    @Size(max = 255)
    @Column(name = "elemento_evaluado")
    private String elementoEvaluado;
    
    @Size(max = 1024)
    @Column(name = "recomendacion")
    private String recomendacion;
    
    @Size(max = 512)
    @Column(name = "tipo_hallazgo")
    private String tipoHallazgo;
    
    @Size(max = 20)
    @Column(name = "nombre_calificacion")
    private String nombreCalificacion;
    
    @Size(max = 128)
    @Column(name = "descripcion_calificacion")
    private String descripcionCalificacion;
    
    @Column(name = "repeticion_hallazgo")
    private BigInteger repeticionHallazgo;
    
    @Size(max = 256)
    @Column(name = "tarea")
    private String tarea;
    
    @Size(max = 1024)
    @Column(name = "descripcion_tarea")
    private String descripcionTarea;
    
    @Size(max = 45)
    @Column(name = "area_responsable")
    private String areaResponsable;
    
    @Column(name = "fecha_elaboracion")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaElaboracion;
    
    @Size(max = 45)
    @Column(name = "estado_tarea")
    private String estadoTarea;
    
    @Column(name = "fecha_verificacion")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaVerificacion;

    public DesviacionInspeccion() {
    }

    public String getHashId() {
        return hashId;
    }

    public void setHashId(String hashId) {
        this.hashId = hashId;
    }

    public BigInteger getFkCalificacionId() {
        return fkCalificacionId;
    }

    public void setFkCalificacionId(BigInteger fkCalificacionId) {
        this.fkCalificacionId = fkCalificacionId;
    }

    public Integer getEmpresaId() {
        return empresaId;
    }

    public void setEmpresaId(Integer empresaId) {
        this.empresaId = empresaId;
    }

    public Date getFechaRealizada() {
        return fechaRealizada;
    }

    public void setFechaRealizada(Date fechaRealizada) {
        this.fechaRealizada = fechaRealizada;
    }

    public String getNivelSuperior() {
        return nivelSuperior;
    }

    public void setNivelSuperior(String nivelSuperior) {
        this.nivelSuperior = nivelSuperior;
    }

    public String getDependenciaArea() {
        return dependenciaArea;
    }

    public void setDependenciaArea(String dependenciaArea) {
        this.dependenciaArea = dependenciaArea;
    }

    public String getAreaInspeccion() {
        return areaInspeccion;
    }

    public void setAreaInspeccion(String areaInspeccion) {
        this.areaInspeccion = areaInspeccion;
    }
    public BigInteger getAreaInspeccionId() {
        return areaInspeccionId;
    }

    public void setAreaInspeccionId(BigInteger areaInspeccionId) {
        this.areaInspeccionId = areaInspeccionId;
    }

    public String getNombreListaInspeccion() {
        return nombreListaInspeccion;
    }

    public void setNombreListaInspeccion(String nombreListaInspeccion) {
        this.nombreListaInspeccion = nombreListaInspeccion;
    }

    public Integer getVersionListaInspeccion() {
        return versionListaInspeccion;
    }

    public void setVersionListaInspeccion(Integer versionListaInspeccion) {
        this.versionListaInspeccion = versionListaInspeccion;
    }

    public String getTipoGrupo() {
        return tipoGrupo;
    }

    public void setTipoGrupo(String tipoGrupo) {
        this.tipoGrupo = tipoGrupo;
    }

    public String getGrupoElemento() {
        return grupoElemento;
    }

    public void setGrupoElemento(String grupoElemento) {
        this.grupoElemento = grupoElemento;
    }

    public String getElementoEvaluado() {
        return elementoEvaluado;
    }

    public void setElementoEvaluado(String elementoEvaluado) {
        this.elementoEvaluado = elementoEvaluado;
    }

    public String getRecomendacion() {
        return recomendacion;
    }

    public void setRecomendacion(String recomendacion) {
        this.recomendacion = recomendacion;
    }

    public String getTipoHallazgo() {
        return tipoHallazgo;
    }

    public void setTipoHallazgo(String tipoHallazgo) {
        this.tipoHallazgo = tipoHallazgo;
    }

    public String getNombreCalificacion() {
        return nombreCalificacion;
    }

    public void setNombreCalificacion(String nombreCalificacion) {
        this.nombreCalificacion = nombreCalificacion;
    }

    public String getDescripcionCalificacion() {
        return descripcionCalificacion;
    }

    public void setDescripcionCalificacion(String descripcionCalificacion) {
        this.descripcionCalificacion = descripcionCalificacion;
    }

    public BigInteger getRepeticionHallazgo() {
        return repeticionHallazgo;
    }

    public void setRepeticionHallazgo(BigInteger repeticionHallazgo) {
        this.repeticionHallazgo = repeticionHallazgo;
    }

    public String getTarea() {
        return tarea;
    }

    public void setTarea(String tarea) {
        this.tarea = tarea;
    }

    public String getDescripcionTarea() {
        return descripcionTarea;
    }

    public void setDescripcionTarea(String descripcionTarea) {
        this.descripcionTarea = descripcionTarea;
    }

    public String getAreaResponsable() {
        return areaResponsable;
    }

    public void setAreaResponsable(String areaResponsable) {
        this.areaResponsable = areaResponsable;
    }

    public Date getFechaElaboracion() {
        return fechaElaboracion;
    }

    public void setFechaElaboracion(Date fechaElaboracion) {
        this.fechaElaboracion = fechaElaboracion;
    }

    public String getEstadoTarea() {
        return estadoTarea;
    }

    public void setEstadoTarea(String estadoTarea) {
        this.estadoTarea = estadoTarea;
    }

    public Date getFechaVerificacion() {
        return fechaVerificacion;
    }

    public void setFechaVerificacion(Date fechaVerificacion) {
        this.fechaVerificacion = fechaVerificacion;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

}
