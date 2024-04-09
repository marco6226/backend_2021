/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.sigess.entities.conf;

import co.sigess.entities.emp.Empresa;
import com.fasterxml.jackson.annotation.JsonIgnore;
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
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author fmoreno
 */
@Entity
@Table(name = "sistema_nivel_riesgo", schema = "conf")
@NamedQueries({
    @NamedQuery(name = "SistemaNivelRiesgo.findAll", query = "SELECT s FROM SistemaNivelRiesgo s")})
public class SistemaNivelRiesgo implements Serializable {


    private static final long serialVersionUID = 1L;
    @Id
    @SequenceGenerator(name = "sistema_nivel_riesgo_id_seq", schema = "conf", sequenceName = "sistema_nivel_riesgo_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sistema_nivel_riesgo_id_seq")
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    
    @Size(max = 45)
    @Column(name = "nombre")
    private String nombre;
    
    @Size(max = 255)
    @Column(name = "descripcion")
    private String descripcion;
    
    @Column(name = "seleccionado")
    private Boolean seleccionado;
        
    @OneToMany(mappedBy = "sistemaNivelRiesgo")
    private List<Probabilidad> probabilidadList;
    
    @OneToMany(mappedBy = "sistemaNivelRiesgo")
    private List<Consecuencia> consecuenciaList;
    
    @JsonIgnore
    @JoinColumn(name = "fk_empesa_id", referencedColumnName = "id")
    @ManyToOne()
    private Empresa empresa;
    
    @OneToMany(mappedBy = "sistemaNivelRiesgo")
    private List<NivelRiesgo> nivelRiesgoList;

    public SistemaNivelRiesgo() {
    }

    public SistemaNivelRiesgo(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
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


    public Empresa getEmpresa() {
        return empresa;
    }

    public void setEmpresa(Empresa empresa) {
        this.empresa = empresa;
    }

    public List<NivelRiesgo> getNivelRiesgoList() {
        return nivelRiesgoList;
    }

    public void setNivelRiesgoList(List<NivelRiesgo> nivelRiesgoList) {
        this.nivelRiesgoList = nivelRiesgoList;
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
        if (!(object instanceof SistemaNivelRiesgo)) {
            return false;
        }
        SistemaNivelRiesgo other = (SistemaNivelRiesgo) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "co.sigess.entities.conf.SistemaNivelRiesgo[ id=" + id + " ]";
    }

    public Boolean getSeleccionado() {
        return seleccionado;
    }

    public void setSeleccionado(Boolean seleccionado) {
        this.seleccionado = seleccionado;
    }

    public List<Probabilidad> getProbabilidadList() {
        return probabilidadList;
    }

    public void setProbabilidadList(List<Probabilidad> probabilidadList) {
        this.probabilidadList = probabilidadList;
    }

    public List<Consecuencia> getConsecuenciaList() {
        return consecuenciaList;
    }

    public void setConsecuenciaList(List<Consecuencia> consecuenciaList) {
        this.consecuenciaList = consecuenciaList;
    }
    
}
