/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.sigess.entities.scm;

import java.io.Serializable;
import java.math.BigInteger;
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
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import org.jvnet.hk2.annotations.Optional;

/**
 *
 * @author leonardo
 */
@Entity
@Table(name = "tratamientos",schema="scm")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Tratamientos.findAll", query = "SELECT t FROM Tratamientos t"),
    @NamedQuery(name = "Tratamientos.findByTipoIntervencion", query = "SELECT t FROM Tratamientos t WHERE t.tipoIntervencion = :tipoIntervencion"),
    @NamedQuery(name = "Tratamientos.findByDescripcion", query = "SELECT t FROM Tratamientos t WHERE t.descripcion = :descripcion"),
    @NamedQuery(name = "Tratamientos.findByFecha", query = "SELECT t FROM Tratamientos t WHERE t.fecha = :fecha"),
    @NamedQuery(name = "Tratamientos.findById", query = "SELECT t FROM Tratamientos t WHERE t.id = :id"),
    @NamedQuery(name = "Tratamientos.findByPkCase", query = "SELECT t FROM Tratamientos t WHERE t.pkCase = :pkCase")})
public class Tratamientos implements Serializable {

    private static final long serialVersionUID = 1L;
    @Size(max = 2147483647)
    @Column(name = "tipo_intervencion")
    private String tipoIntervencion;
    @Size(max = 2147483647)
    @Column(name = "descripcion")
    private String descripcion;
    @Optional
    @Column(name = "tipo")
    private Integer tipo;
    @Column(name = "fecha")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fecha;
    @Id
    @Basic(optional = false)
    @NotNull
    @SequenceGenerator(name = "tratamiento_id_seq", schema = "scm", sequenceName = "tratamiento_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "tratamiento_id_seq")
    @Column(name = "id")
    private Long id;
    @Column(name = "pk_case")
    private BigInteger pkCase;

    public Tratamientos() {
    }

    public Tratamientos(Long id) {
        this.id = id;
    }

    public String getTipoIntervencion() {
        return tipoIntervencion;
    }

    public void setTipoIntervencion(String tipoIntervencion) {
        this.tipoIntervencion = tipoIntervencion;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigInteger getPkCase() {
        return pkCase;
    }

    public void setPkCase(BigInteger pkCase) {
        this.pkCase = pkCase;
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
        if (!(object instanceof Tratamientos)) {
            return false;
        }
        Tratamientos other = (Tratamientos) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "co.sigess.entities.scm.Tratamientos[ id=" + id + " ]";
    }

    /**
     * @return the tipo
     */
    public Integer getTipo() {
        return tipo;
    }

    /**
     * @param tipo the tipo to set
     */
    public void setTipo(Integer tipo) {
        this.tipo = tipo;
    }
    
}
