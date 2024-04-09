/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.sigess.entities.emp;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.io.Serializable;
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
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author fmoreno
 */
@Entity
@Table(name = "competencia", schema = "emp")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Competencia.findAll", query = "SELECT c FROM Competencia c")})
public class Competencia implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @SequenceGenerator(name = "competencia_id_seq", schema = "emp", sequenceName = "competencia_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "competencia_id_seq")
    @Basic(optional = false)
    @Column(name = "id")
    private Long id;
    
    @Size(max = 255)
    @Column(name = "nombre")
    private String nombre;
    
    @Size(max = 2048)
    @Column(name = "descripcion")
    private String descripcion;
    
    @OneToMany(mappedBy = "competencia")
    private List<Competencia> competenciaList;
    
    @JoinColumn(name = "fk_competencia_id", referencedColumnName = "id")
    @ManyToOne
    private Competencia competencia;
        
    @JoinColumn(name = "fk_empresa_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Empresa empresa;

    public Competencia() {
    }

    public Competencia(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public List<Competencia> getCompetenciaList() {
        return competenciaList;
    }

    public void setCompetenciaList(List<Competencia> competenciaList) {
        this.competenciaList = competenciaList;
    }

    
    @JsonIgnore
    public Competencia getCompetencia() {
        return competencia;
    }

    @JsonProperty(value = "competencia")
    public void setCompetencia(Competencia competencia) {
        this.competencia = competencia;
    }

    @JsonIgnore
    public Empresa getEmpresa() {
        return empresa;
    }

    @JsonProperty(value = "empresa")
    public void setEmpresa(Empresa empresa) {
        this.empresa = empresa;
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
        if (!(object instanceof Competencia)) {
            return false;
        }
        Competencia other = (Competencia) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "co.sigess.entities.emp.Competencia[ id=" + id + " ]";
    }
    
}
