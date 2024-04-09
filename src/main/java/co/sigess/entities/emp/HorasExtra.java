/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.sigess.entities.emp;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author fmoreno
 */
@Entity
@JsonIgnoreProperties(ignoreUnknown = true)
@Table(name = "horas_extra", schema = "emp")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "HorasExtra.findAll", query = "SELECT h FROM HorasExtra h")})
public class HorasExtra implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Long id;
    
    @Basic(optional = false)
    @NotNull(message = "El campo fecha no puede ser vacio")
    @Column(name = "fecha")
    @Temporal(TemporalType.DATE)
    private Date fecha;
    
    @Basic(optional = false)
    @NotNull(message = "El campo horas no puede ser vacio")
    @Column(name = "horas")
    private short horas;
    
    @NotNull(message = "El campo empleado no puede ser vacio")
    @JoinColumn(name = "fk_empleado_id", referencedColumnName = "id")
    @ManyToOne
    private Empleado empleado;

    public HorasExtra() {
    }

    public HorasExtra(Long id) {
        this.id = id;
    }

    public HorasExtra(Long id, Date fecha, short horas) {
        this.id = id;
        this.fecha = fecha;
        this.horas = horas;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public short getHoras() {
        return horas;
    }

    public void setHoras(short horas) {
        this.horas = horas;
    }

    @JsonIgnore
    public Empleado getEmpleado() {
        return empleado;
    }

    @JsonProperty("empleado")
    public void setEmpleado(Empleado empleado) {
        this.empleado = empleado;
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
        if (!(object instanceof HorasExtra)) {
            return false;
        }
        HorasExtra other = (HorasExtra) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "co.sigess.entities.emp.HorasExtra[ id=" + id + " ]";
    }
    
}
