/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.sigess.entities.scm;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author leonardo
 */
@Entity
@Table(name = "pcl" ,schema = "scm")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Pcl.findAll", query = "SELECT p FROM Pcl p ORDER BY p.emisionPclFecha DESC"),
    @NamedQuery(name = "Pcl.findById", query = "SELECT p FROM Pcl p WHERE p.id = :id ORDER BY p.emisionPclFecha DESC"),
    @NamedQuery(name = "Pcl.findByPorcentajePcl", query = "SELECT p FROM Pcl p WHERE p.porcentajePcl = :porcentajePcl"),
    @NamedQuery(name = "Pcl.findByEmisionPclFecha", query = "SELECT p FROM Pcl p WHERE p.emisionPclFecha = :emisionPclFecha"),
    @NamedQuery(name = "Pcl.findByEntidadEmitePcl", query = "SELECT p FROM Pcl p WHERE p.entidadEmitePcl = :entidadEmitePcl"),
    @NamedQuery(name = "Pcl.findByPcl", query = "SELECT p FROM Pcl p WHERE p.pcl = :pcl"),
    @NamedQuery(name = "Pcl.findByDiag", query = "SELECT p FROM Pcl p WHERE p.diag = :diag ORDER BY p.emisionPclFecha DESC"),
    @NamedQuery(name = "Pcl.findByEliminado", query = "SELECT p FROM Pcl p WHERE p.eliminado = :eliminado ORDER BY p.emisionPclFecha DESC")})
public class Pcl implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @SequenceGenerator(name = "pcl_id_seq", schema = "scm", sequenceName = "pcl_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "pcl_id_seq")
    @NotNull
    @Column(name = "id")
    private Long id;
    @Size(max = 2147483647)
    @Column(name = "porcentaje_pcl")
    private String porcentajePcl;
    @Size(max = 2147483647)
    @Column(name = "emision_pcl_fecha")
    private String emisionPclFecha;
    @Size(max = 2147483647)
    @Column(name = "entidad_emite_pcl")
    private String entidadEmitePcl;
    @Size(max = 2147483647)
    @Column(name = "pcl")
    private String pcl;
    @Size(max = 2147483647)
    @Column(name = "observaciones")
    private String observaciones;
    @Size(max = 2147483647)
    @Column(name = "origen")
    private String origen;
    @Size(max = 2147483647)
    @Column(name = "diag")
    private String diag;
    @Column(name = "eliminado")
    private Boolean eliminado;
    @Size(max = 2147483647)
    @Column(name = "status_de_calificacion")
    private String statusDeCalificacion;
    @Size(max = 2147483647)
    @Column(name = "fecha_calificacion")
    private String fechaCalificacion;
    @Size(max = 2147483647)
    @Column(name = "entidad_emite_calificacion")
    private String entidadEmiteCalificacion;
    @Column(name = "entidad_emitida_calificacion")
    private Integer entidadEmitidaCalificacion;
    @Size(max = 2147483647)
    @Column(name = "entidad_emitida")
    private String entidadEmitida;

    public Pcl() {
    }

    public Pcl(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPorcentajePcl() {
        return porcentajePcl;
    }

    public void setPorcentajePcl(String porcentajePcl) {
        this.porcentajePcl = porcentajePcl;
    }

    public String getEmisionPclFecha() {
        return emisionPclFecha;
    }

    public void setEmisionPclFecha(String emisionPclFecha) {
        this.emisionPclFecha = emisionPclFecha;
    }

    public String getEntidadEmitePcl() {
        return entidadEmitePcl;
    }

    public void setEntidadEmitePcl(String entidadEmitePcl) {
        this.entidadEmitePcl = entidadEmitePcl;
    }

    public String getPcl() {
        return pcl;
    }

    public void setPcl(String pcl) {
        this.pcl = pcl;
    }

    public String getDiag() {
        return diag;
    }

    public void setDiag(String diag) {
        this.diag = diag;
    }

    public Boolean getEliminado() {
        return eliminado;
    }

    public void setEliminado(Boolean eliminado) {
        this.eliminado = eliminado;
    }

    public String getStatusDeCalificacion() {
        return statusDeCalificacion;
    }

    public void setStatusDeCalificacion(String statusDeCalificacion) {
        this.statusDeCalificacion = statusDeCalificacion;
    }

    public String getFechaCalificacion() {
        return fechaCalificacion;
    }

    public void setFechaCalificacion(String fechaCalificacion) {
        this.fechaCalificacion = fechaCalificacion;
    }

    public String getEntidadEmiteCalificacion() {
        return entidadEmiteCalificacion;
    }

    public void setEntidadEmiteCalificacion(String entidadEmiteCalificacion) {
        this.entidadEmiteCalificacion = entidadEmiteCalificacion;
    }

    public Integer getEntidadEmitidaCalificacion() {
        return entidadEmitidaCalificacion;
    }

    public void setEntidadEmitidaCalificacion(Integer entidadEmitidaCalificacion) {
        this.entidadEmitidaCalificacion = entidadEmitidaCalificacion;
    }

    public String getEntidadEmitida() {
        return entidadEmitida;
    }

    public void setEntidadEmitida(String entidadEmitida) {
        this.entidadEmitida = entidadEmitida;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Pcl)) {
            return false;
        }
        Pcl other = (Pcl) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "co.sigess.entities.scm.Pcl[ id=" + id + " ]";
    }

    /**
     * @return the origen
     */
    public String getOrigen() {
        return origen;
    }

    /**
     * @param origen the origen to set
     */
    public void setOrigen(String origen) {
        this.origen = origen;
    }

    /**
     * @return the observaciones
     */
    public String getObservaciones() {
        return observaciones;
    }

    /**
     * @param observaciones the observaciones to set
     */
    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }
    
}
