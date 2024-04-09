/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.sigess.entities.ipr;

import co.sigess.entities.emp.Empresa;
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
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author fmoreno
 */
@Entity
@Table(name = "tipo_peligro", schema = "ipr")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TipoPeligro.findAll", query = "SELECT t FROM TipoPeligro t")})
public class TipoPeligro implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @SequenceGenerator(name = "tipo_peligro_id_seq", schema = "ipr", sequenceName = "tipo_peligro_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "tipo_peligro_id_seq")
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    
    @NotNull(message = "El campo nombre es requerido para el tipo de peligro")
    @Size(max = 255)
    @Column(name = "nombre")
    private String nombre;
    
    @NotNull(message = "El campo empresa es requerido para el tipo de peligro")
    @JsonIgnore
    @JoinColumn(name = "fk_empresa_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Empresa empresa;
    
    @JsonIgnore
    @OneToMany(mappedBy = "tipoPeligro")
    private List<Peligro> peligroList;

    public TipoPeligro() {
    }

    public TipoPeligro(Integer id) {
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

    @JsonIgnore
    public Empresa getEmpresa() {
        return empresa;
    }

    @JsonProperty("empresa")
    public void setEmpresa(Empresa empresa) {
        this.empresa = empresa;
    }

    @XmlTransient
    public List<Peligro> getPeligroList() {
        return peligroList;
    }

    public void setPeligroList(List<Peligro> peligroList) {
        this.peligroList = peligroList;
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
        if (!(object instanceof TipoPeligro)) {
            return false;
        }
        TipoPeligro other = (TipoPeligro) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "co.sigess.entities.ipr.TipoPeligro[ id=" + id + " ]";
    }
    
}
