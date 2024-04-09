/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.sigess.entities.emp;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.io.Serializable;
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
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author fmoreno
 */
@Entity
@Table(name = "perfil", schema = "emp")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Perfil.findAll", query = "SELECT p FROM Perfil p")
    , @NamedQuery(name = "Perfil.findById", query = "SELECT p FROM Perfil p WHERE p.id = :id")
    , @NamedQuery(name = "Perfil.findByNombre", query = "SELECT p FROM Perfil p WHERE p.nombre = :nombre")
    , @NamedQuery(name = "Perfil.findByDescripcion", query = "SELECT p FROM Perfil p WHERE p.descripcion = :descripcion")})
public class Perfil implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @SequenceGenerator(name = "perfil_id_seq", schema = "emp", sequenceName = "perfil_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "perfil_id_seq")
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "nombre")
    private String nombre;
    
    @Size(max = 255)
    @Column(name = "descripcion")
    private String descripcion;
    
    @JsonIgnore
    @OneToMany(mappedBy = "perfil")
    private List<Permiso> permisoList;
    
    @JsonIgnore
    @OneToMany(mappedBy = "perfil")
    private List<UsuarioEmpresa> usuarioEmpresaList;
    
    @JsonIgnore
    @JoinColumn(name = "fk_empresa_id", referencedColumnName = "id")
    @NotNull
    @ManyToOne(optional = false)
    private Empresa empresa;
    
    
    public Perfil() {
    }

    public Perfil(Integer id) {
        this.id = id;
    }

    public Perfil(Integer id, String nombre) {
        this.id = id;
        this.nombre = nombre;
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
    
    public List<Permiso> getPermisoList() {
        return permisoList;
    }

    public void setPermisoList(List<Permiso> permisoList) {
        this.permisoList = permisoList;
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
        if (!(object instanceof Perfil)) {
            return false;
        }
        Perfil other = (Perfil) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "co.sigess.entities.emp.Perfil[ id=" + id + " ]";
    }

    @XmlTransient
    public List<UsuarioEmpresa> getUsuarioEmpresaList() {
        return usuarioEmpresaList;
    }

    public void setUsuarioEmpresaList(List<UsuarioEmpresa> usuarioEmpresaList) {
        this.usuarioEmpresaList = usuarioEmpresaList;
    }

    public Empresa getEmpresa() {
        return empresa;
    }

    public void setEmpresa(Empresa empresa) {
        this.empresa = empresa;
    }

}
