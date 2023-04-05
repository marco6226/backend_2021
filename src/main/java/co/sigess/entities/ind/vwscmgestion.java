/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.sigess.entities.ind;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Usuario
 */
@Entity
@JsonIgnoreProperties(ignoreUnknown = true)
@Table(name = "vw_scm_gestion", schema = "ind")
@XmlRootElement
public class vwscmgestion implements Serializable{
    
    @Column(name = "caso_medico")
    @Id
    private Integer id;

    @Column(name = "divisionunidad")
    private String divisionUnidad;    
    
    @Column(name = "ubicacion")
    private String ubicacion;
    
    @Column(name = "fecha_creacion")
    @Temporal(TemporalType.DATE)
    private Date fechaCreacion;
    
    @Column(name = "fecha_nacimiento")
    @Temporal(TemporalType.DATE)
    private Date fechaNacimiento;

    @Column(name = "fecha_ingreso")
    @Temporal(TemporalType.DATE)
    private Date fechaIngreso;
    
    @Column(name = "fecha_final")
    private String fechaFinal;
    
    @Column(name = "fecha_diagnostico")
    @Temporal(TemporalType.DATE)
    private Date fechaDiagnostico;
        
    @Column(name = "pcl")
    private String pcl;
           
    @Column(name = "entidad_emite_pcl")
    private String entidadEmitePcl;
               
    @Column(name = "emision_pcl_fecha")
    private String emisionPclFecha;
    
    @Column(name = "origen")
    private String origen;
    
    @Column(name = "entidad_emite_calificacion")
    private String entidadEmiteOrigen;
        
    @Column(name = "fecha_origen")
    private String fechaOrigen;
    
    @Column(name = "concept_rehabilitacion")
    private String conceptRehabilitacion;
    
    @Column(name = "entidad_emite_concepto")
    private String entidadEmiteConceptoRehabilitacion;
    
    @Column(name = "concept_rehabilitaciontwo")
    private String conceptRehabilitacion2;
    
    @Column(name = "entidad_emite_conceptotwo")
    private String entidadEmiteConceptoRehabilitacion2;
    
    @Column(name = "fk_empresa_id")
    private Integer empresaId;
    
    @Column(name = "recomendacion")
    private Integer recomendacion;
        
    @Column(name = "seguimiento")
    private Integer seguimiento;
            
    @Column(name = "documento")
    private Integer documento;
                
    @Column(name = "plan_accion")
    private Integer planAccion;

    public Integer getRecomendacion() {
        return recomendacion;
    }

    public void setRecomendacion(Integer recomendacion) {
        this.recomendacion = recomendacion;
    }

    public Integer getSeguimiento() {
        return seguimiento;
    }

    public void setSeguimiento(Integer seguimiento) {
        this.seguimiento = seguimiento;
    }

    public Integer getDocumento() {
        return documento;
    }

    public void setDocumento(Integer documento) {
        this.documento = documento;
    }

    public Integer getPlanAccion() {
        return planAccion;
    }

    public void setPlanAccion(Integer planAccion) {
        this.planAccion = planAccion;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDivisionUnidad() {
        return divisionUnidad;
    }

    public void setDivisionUnidad(String divisionUnidad) {
        this.divisionUnidad = divisionUnidad;
    }

    public String getUbicacion() {
        return ubicacion;
    }

    public void setUbicacion(String ubicacion) {
        this.ubicacion = ubicacion;
    }

    public Date getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(Date fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public Date getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(Date fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public Date getFechaIngreso() {
        return fechaIngreso;
    }

    public void setFechaIngreso(Date fechaIngreso) {
        this.fechaIngreso = fechaIngreso;
    }

    public String getFechaFinal() {
        return fechaFinal;
    }

    public void setFechaFinal(String fechaFinal) {
        this.fechaFinal = fechaFinal;
    }

    public Date getFechaDiagnostico() {
        return fechaDiagnostico;
    }

    public void setFechaDiagnostico(Date fechaDiagnostico) {
        this.fechaDiagnostico = fechaDiagnostico;
    }
    
    public String getPcl() {
        return pcl;
    }

    public void setPcl(String pcl) {
        this.pcl = pcl;
    }

    public String getEntidadEmitePcl() {
        return entidadEmitePcl;
    }

    public void setEntidadEmitePcl(String entidadEmitePcl) {
        this.entidadEmitePcl = entidadEmitePcl;
    }

    public String getEmisionPclFecha() {
        return emisionPclFecha;
    }

    public void setEmisionPclFecha(String emisionPclFecha) {
        this.emisionPclFecha = emisionPclFecha;
    }

    public String getOrigen() {
        return origen;
    }

    public void setOrigen(String origen) {
        this.origen = origen;
    }

    public String getEntidadEmiteOrigen() {
        return entidadEmiteOrigen;
    }

    public void setEntidadEmiteOrigen(String entidadEmiteOrigen) {
        this.entidadEmiteOrigen = entidadEmiteOrigen;
    }

    public String getFechaOrigen() {
        return fechaOrigen;
    }

    public void setFechaOrigen(String fechaOrigen) {
        this.fechaOrigen = fechaOrigen;
    }

    public String getConceptRehabilitacion() {
        return conceptRehabilitacion;
    }

    public void setConceptRehabilitacion(String conceptRehabilitacion) {
        this.conceptRehabilitacion = conceptRehabilitacion;
    }

    public String getEntidadEmiteConceptoRehabilitacion() {
        return entidadEmiteConceptoRehabilitacion;
    }

    public void setEntidadEmiteConceptoRehabilitacion(String entidadEmiteConceptoRehabilitacion) {
        this.entidadEmiteConceptoRehabilitacion = entidadEmiteConceptoRehabilitacion;
    }

    public String getConceptRehabilitacion2() {
        return conceptRehabilitacion2;
    }

    public void setConceptRehabilitacion2(String conceptRehabilitacion2) {
        this.conceptRehabilitacion2 = conceptRehabilitacion2;
    }

    public String getEntidadEmiteConceptoRehabilitacion2() {
        return entidadEmiteConceptoRehabilitacion2;
    }

    public void setEntidadEmiteConceptoRehabilitacion2(String entidadEmiteConceptoRehabilitacion2) {
        this.entidadEmiteConceptoRehabilitacion2 = entidadEmiteConceptoRehabilitacion2;
    }

    public Integer getEmpresaId() {
        return empresaId;
    }

    public void setEmpresaId(Integer empresaId) {
        this.empresaId = empresaId;
    }
    
}
