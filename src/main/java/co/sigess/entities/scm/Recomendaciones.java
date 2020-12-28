/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.sigess.entities.scm;

import java.io.Serializable;
import java.util.Date;
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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author leonardo
 */
@Entity
@Table(name = "recomendaciones",schema="scm")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Recomendaciones.findAll", query = "SELECT r FROM Recomendaciones r")})
public class Recomendaciones implements Serializable {

    
    @Id
    @SequenceGenerator(name = "recomendaciones_id_seq", schema = "scm", sequenceName = "recomendaciones_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "recomendaciones_id_seq")
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    
    private static final long serialVersionUID = 1L;
    @Column(name = "generate_recomendaciones")
    private Character generateRecomendaciones;
    @Column(name = "entidad_emit_recomendaciones")
    private Character entidadEmitRecomendaciones;
    @Column(name = "tipo")
    private Character tipo;
    @Column(name = "fecha_inicio")
    @Temporal(TemporalType.DATE)
    private Date fechaInicio;
    @Column(name = "fecha_expiracion")
    @Temporal(TemporalType.DATE)
    private Date fechaExpiracion;
    @Column(name = "status")
    private Character status;
    @Column(name = "recomendaciones")
    private Character recomendaciones;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "pk_user")
    private Long pkUser;

    public Recomendaciones() {
    }

    public Recomendaciones(Long pkUser) {
        this.pkUser = pkUser;
    }

    public Character getGenerateRecomendaciones() {
        return generateRecomendaciones;
    }

    public void setGenerateRecomendaciones(Character generateRecomendaciones) {
        this.generateRecomendaciones = generateRecomendaciones;
    }

    public Character getEntidadEmitRecomendaciones() {
        return entidadEmitRecomendaciones;
    }

    public void setEntidadEmitRecomendaciones(Character entidadEmitRecomendaciones) {
        this.entidadEmitRecomendaciones = entidadEmitRecomendaciones;
    }

    public Character getTipo() {
        return tipo;
    }

    public void setTipo(Character tipo) {
        this.tipo = tipo;
    }

    public Date getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(Date fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public Date getFechaExpiracion() {
        return fechaExpiracion;
    }

    public void setFechaExpiracion(Date fechaExpiracion) {
        this.fechaExpiracion = fechaExpiracion;
    }

    public Character getStatus() {
        return status;
    }

    public void setStatus(Character status) {
        this.status = status;
    }

    public Character getRecomendaciones() {
        return recomendaciones;
    }

    public void setRecomendaciones(Character recomendaciones) {
        this.recomendaciones = recomendaciones;
    }

    public Long getPkUser() {
        return pkUser;
    }

    public void setPkUser(Long pkUser) {
        this.pkUser = pkUser;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (pkUser != null ? pkUser.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Recomendaciones)) {
            return false;
        }
        Recomendaciones other = (Recomendaciones) object;
        if ((this.pkUser == null && other.pkUser != null) || (this.pkUser != null && !this.pkUser.equals(other.pkUser))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "co.sigess.entities.scm.Recomendaciones[ pkUser=" + pkUser + " ]";
    }

    /**
     * @return the id
     */
    public Integer getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(Integer id) {
        this.id = id;
    }
    
}
