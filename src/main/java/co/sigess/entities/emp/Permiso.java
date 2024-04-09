/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.sigess.entities.emp;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
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
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author fmoreno
 */
@Entity
@Table(name = "permiso", schema = "emp")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Permiso.findAll", query = "SELECT p FROM Permiso p")})
public class Permiso implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @SequenceGenerator(name = "permiso_id_seq", schema = "emp", sequenceName = "permiso_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "permiso_id_seq")
    @Basic(optional = false)
    @Column(name = "id")
    private Long id;

    @Basic(optional = false)
    @NotNull
    @Column(name = "valido")
    private boolean valido;

    @JoinColumn(name = "fk_perfil_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Perfil perfil;

    @JoinColumn(name = "fk_recurso_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Recurso recurso;

    @JoinColumn(name = "fk_empresa_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Empresa empresa;
    
    @Column(name = "areas")
    private String areas;

    public Permiso() {
    }

    public Permiso(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public boolean isValido() {
        return valido;
    }

    public void setValido(boolean valido) {
        this.valido = valido;
    }

    @JsonIgnore
    public Perfil getPerfil() {
        return perfil;
    }

    @JsonProperty(value = "perfil")
    public void setPerfil(Perfil perfil) {
        this.perfil = perfil;
    }

    public Recurso getRecurso() {
        return recurso;
    }

    public void setRecurso(Recurso recurso) {
        this.recurso = recurso;
    }

    public String getAreas() {
        return areas;
    }

    public void setAreas(String areas) {
        this.areas = areas;
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
    public String toString() {
        return "co.sigess.entities.emp.Permiso[ id=" + id + " ]";
    }
    
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (empresa != null ? empresa.hashCode() : 0);
        hash += (recurso != null ? recurso.hashCode() : 0);
        return hash;
    }
    
    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Permiso)) {
            return false;
        }
        Permiso other = (Permiso) object;
        if(this.empresa.equals(other.getEmpresa()) && this.recurso.equals(other.getRecurso())){
            return true;
        }
        return false;
    }

}
