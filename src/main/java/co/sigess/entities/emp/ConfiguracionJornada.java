/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.sigess.entities.emp;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
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
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.SequenceGenerator;
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
@Table(name = "configuracion_jornada", schema = "emp")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ConfiguracionJornada.findAll", query = "SELECT c FROM ConfiguracionJornada c")})
public class ConfiguracionJornada implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @SequenceGenerator(name = "configuracion_jornada_id_seq", schema = "emp", sequenceName = "configuracion_jornada_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "configuracion_jornada_id_seq")
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    
    @NotNull(message = "El campo fechaEntradaVigencia no puede ser vacio")
    @Column(name = "fecha_entrada_vigencia")
    @Temporal(TemporalType.DATE)
    private Date fechaEntradaVigencia;
    
    @NotNull(message = "El campo empleado no puede ser vacio")
    @JoinColumn(name = "fk_empleado_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Empleado empleado;
        
    @OrderBy(value = "dia")
    @NotNull(message = "El campo jornadaList no puede ser vacio")
    @OneToMany(mappedBy = "configuracionJornada")
    private List<Jornada> jornadaList;

    public ConfiguracionJornada() {
    }

    public ConfiguracionJornada(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getFechaEntradaVigencia() {
        return fechaEntradaVigencia;
    }

    public void setFechaEntradaVigencia(Date fechaEntradaVigencia) {
        this.fechaEntradaVigencia = fechaEntradaVigencia;
    }

    @JsonIgnore
    public Empleado getEmpleado() {
        return empleado;
    }

    @JsonProperty("empleado")
    public void setEmpleado(Empleado empleado) {
        this.empleado = empleado;
    }

    public List<Jornada> getJornadaList() {
        return jornadaList;
    }

    public void setJornadaList(List<Jornada> jornadaList) {
        this.jornadaList = jornadaList;
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
        if (!(object instanceof ConfiguracionJornada)) {
            return false;
        }
        ConfiguracionJornada other = (ConfiguracionJornada) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "co.sigess.entities.emp.ConfiguracionJornada[ id=" + id + " ]";
    }
    
}
