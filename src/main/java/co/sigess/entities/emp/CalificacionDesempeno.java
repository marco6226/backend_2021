/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.sigess.entities.emp;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.io.Serializable;
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
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author fmoreno
 */
@Entity
@Table(name = "calificacion_desempeno", schema = "emp")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "CalificacionDesempeno.findAll", query = "SELECT c FROM CalificacionDesempeno c")})
public class CalificacionDesempeno implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @SequenceGenerator(name = "calificacion_desempeno_id_seq", schema = "emp", sequenceName = "calificacion_desempeno_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "calificacion_desempeno_id_seq")
    @Basic(optional = false)
    @Column(name = "id")
    private Long id;
    
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "puntaje")
    private Double puntaje;
    
    @JsonIgnore
    @JoinColumn(name = "fk_evaluacion_desempeno_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private EvaluacionDesempeno evaluacionDesempeno;

    
    @JoinColumn(name = "fk_competencia_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Competencia competencia;
    
    public CalificacionDesempeno() {
    }

    public CalificacionDesempeno(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getPuntaje() {
        return puntaje;
    }

    public void setPuntaje(Double puntaje) {
        this.puntaje = puntaje;
    }

    public EvaluacionDesempeno getEvaluacionDesempeno() {
        return evaluacionDesempeno;
    }

    public void setEvaluacionDesempeno(EvaluacionDesempeno evaluacionDesempeno) {
        this.evaluacionDesempeno = evaluacionDesempeno;
    }

    public Competencia getCompetencia() {
        return competencia;
    }

    public void setCompetencia(Competencia competencia) {
        this.competencia = competencia;
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
        if (!(object instanceof CalificacionDesempeno)) {
            return false;
        }
        CalificacionDesempeno other = (CalificacionDesempeno) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "co.sigess.entities.emp.CalificacionDesempeno[ id=" + id + " ]";
    }
    
}
