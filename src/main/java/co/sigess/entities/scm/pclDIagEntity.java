/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package co.sigess.entities.scm;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author Luis
 */
@Entity
@Table(name = "vw_pcldiagnostico", schema = "scm")
public class pclDIagEntity implements Serializable{
    
    @Id
    private Long id;
    
    private Long pclid;

    public Long getPclid() {
        return pclid;
    }

    @Column(name = "porcentaje_pcl")
    private String porcentajePcl;

    @Column(name = "emision_pcl_fecha")
    private String emisionPclFecha;

    @Column(name = "entidad_emite_pcl")
    private String entidadEmitePcl;
    
    

    @Column(name = "pcl")
    private String pcl;

    @Column(name = "diag")
    private Long diag;

    @Column(name = "eliminado")
    private Boolean eliminado;

    @Column(name = "entidad_emitida")
    private String entidadEmitida;

    @Column(name = "status_de_calificacion")
    private String statusDeCalificacion;

    @Column(name = "fecha_calificacion")
    private String fechaCalificacion;

    @Column(name = "entidad_emite_calificacion")
    private String entidadEmiteCalificacion;

    @Column(name = "entidad_emitida_calificacion")
    private String entidadEmitidaCalificacion;

    @Column(name = "observaciones")
    private String observaciones;

    @Column(name = "origen")
    private String origen;

    @Column(name = "id_diagnostico")
    private Long idDiagnostico;

    @Column(name = "nombre_diagnostico")
    private String nombreDiagnostico;
    
    @Column(name = "pk_case")
    private String pkCase;
    
    @Column(name = "nombre_entidad")
    private String nombreEntidad;
    
    @Column(name = "nombre_entidad_calificada")
    private String nombreEntidadCalificada;

    public String getNombreEntidad() {
        return nombreEntidad;
    }

    public String getNombreEntidadCalificada() {
        return nombreEntidadCalificada;
    }
    
    

    public String getPkCase() {
        return pkCase;
    }

    public void setPkCase(String pkCase) {
        this.pkCase = pkCase;
    }

    public Long getId() {
        return id;
    }

    public String getPorcentajePcl() {
        return porcentajePcl;
    }

    public String getEmisionPclFecha() {
        return emisionPclFecha;
    }

    public String getEntidadEmitePcl() {
        return entidadEmitePcl;
    }

    public String getPcl() {
        return pcl;
    }

    public Long getDiag() {
        return diag;
    }

    public Boolean getEliminado() {
        return eliminado;
    }

    public String getEntidadEmitida() {
        return entidadEmitida;
    }

    public String getStatusDeCalificacion() {
        return statusDeCalificacion;
    }

    public String getFechaCalificacion() {
        return fechaCalificacion;
    }

    public String getEntidadEmiteCalificacion() {
        return entidadEmiteCalificacion;
    }

    public String getEntidadEmitidaCalificacion() {
        
        return entidadEmitidaCalificacion;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public String getOrigen() {
        return origen;
    }

    public Long getIdDiagnostico() {
        return idDiagnostico;
    }

    public String getNombreDiagnostico() {
        return nombreDiagnostico;
    }
    
}
