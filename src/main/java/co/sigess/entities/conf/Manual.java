/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.sigess.entities.conf;

import co.sigess.entities.ado.Documento;
import co.sigess.entities.emp.Usuario;
import com.fasterxml.jackson.annotation.JsonIgnore;
import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author fmoreno
 */
@Entity
@Table(name = "manual", schema = "conf")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Manual.findAll", query = "SELECT m FROM Manual m")})
public class Manual implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "id")
    private Integer id;

    @Size(max = 20)
    @Column(name = "codigo")
    private String codigo;

    @Size(max = 50)
    @Column(name = "nombre")
    private String nombre;

    @Size(max = 128)
    @Column(name = "descripcion")
    private String descripcion;
    
    @Size(max = 10)
    @Column(name = "tipo")
    private String tipo;

    @JsonIgnore
    @Size(max = 255)
    @Column(name = "ruta")
    private String ruta;

    @JsonIgnore
    @Column(name = "fk_empresa_id")
    private Integer empresaId;

    @JsonIgnore
    @JoinTable(name = "usuario_manual", schema = "conf", joinColumns = {
        @JoinColumn(name = "fk_manual_id", referencedColumnName = "id")}, inverseJoinColumns = {
        @JoinColumn(name = "fk_usuario_id", referencedColumnName = "id")})
    @ManyToMany
    private List<Usuario> usuarioList;

    public Manual() {
    }

    public Manual(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
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

    public String getRuta() {
        return ruta;
    }

    public void setRuta(String ruta) {
        this.ruta = ruta;
    }

    public Integer getEmpresaId() {
        return empresaId;
    }

    public void setEmpresaId(Integer empresaId) {
        this.empresaId = empresaId;
    }

    public List<Usuario> getUsuarioList() {
        return usuarioList;
    }

    public void setUsuarioList(List<Usuario> usuarioList) {
        this.usuarioList = usuarioList;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
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
        if (!(object instanceof Manual)) {
            return false;
        }
        Manual other = (Manual) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "co.sigess.entities.conf.Manual[ id=" + id + " ]";
    }

}
