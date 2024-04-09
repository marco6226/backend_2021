/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.sigess.entities.emp;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
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
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author fmoreno
 */
@Entity
@Table(name = "evaluacion_desempeno", schema = "emp")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "EvaluacionDesempeno.findAll", query = "SELECT e FROM EvaluacionDesempeno e")})
public class EvaluacionDesempeno implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @SequenceGenerator(name = "evaluacion_desempeno_id_seq", schema = "emp", sequenceName = "evaluacion_desempeno_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "evaluacion_desempeno_id_seq")
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    
    //@NotNull(message = "Debe especificar la fecha de elaboración de la evaluacion")
    @Column(name = "fecha_elaboracion", updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaElaboracion;
    
    @Column(name = "comentario")
    private String comentario;
    
    @OneToMany(mappedBy = "evaluacionDesempeno")
    private List<CalificacionDesempeno> calificacionDesempenoList;
        
    @NotNull(message = "Debe especificar el empleado que esta siendo evaluado")
    @JoinColumn(name = "fk_empleado_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Empleado empleado;
    
    @NotNull(message = "Debe especificar el cargo del empleado que esta siendo evaluado")
    @JoinColumn(name = "fk_cargo_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Cargo cargo;
    
    //@NotNull(message = "Debe especificar el usuario que esta registrando la evaluacion")
    @JoinColumn(name = "fk_usuario_elabora_id", referencedColumnName = "id", updatable = false)
    @ManyToOne(optional = false)
    private Usuario usuarioElabora;

    public EvaluacionDesempeno() {
    }

    public EvaluacionDesempeno(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getFechaElaboracion() {
        return fechaElaboracion;
    }

    public void setFechaElaboracion(Date fechaElaboracion) {
        this.fechaElaboracion = fechaElaboracion;
    }

    public List<CalificacionDesempeno> getCalificacionDesempenoList() {
        return calificacionDesempenoList;
    }

    public void setCalificacionDesempenoList(List<CalificacionDesempeno> calificacionDesempenoList) {
        this.calificacionDesempenoList = calificacionDesempenoList;
    }

    public Empleado getEmpleado() {
        return empleado;
    }

    public void setEmpleado(Empleado empleado) {
        this.empleado = empleado;
    }

    public Cargo getCargo() {
        return cargo;
    }

    public void setCargo(Cargo cargo) {
        this.cargo = cargo;
    }

    public Usuario getUsuarioElabora() {
        return usuarioElabora;
    }

    public void setUsuarioElabora(Usuario usuarioElabora) {
        this.usuarioElabora = usuarioElabora;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof EvaluacionDesempeno)) {
            return false;
        }
        EvaluacionDesempeno other = (EvaluacionDesempeno) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "co.sigess.entities.emp.EvaluacionDesempeno[ id=" + id + " ]";
    }
    
}
