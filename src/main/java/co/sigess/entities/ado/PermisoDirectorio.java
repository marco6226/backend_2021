/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.sigess.entities.ado;

import co.sigess.entities.emp.Usuario;
import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 *
 * @author fmoreno
 */
@Entity
@Table(name = "permiso_directorio", schema = "ado")
@NamedQueries({
    @NamedQuery(name = "PermisoDirectorio.findAll", query = "SELECT p FROM PermisoDirectorio p")})
public class PermisoDirectorio implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected PermisoDirectorioPK permisoDirectorioPK;
    @Column(name = "consultar")
    private Boolean consultar;
    
    @Column(name = "modificar")
    private Boolean modificar;
    
    @Column(name = "eliminar")
    private Boolean eliminar;
    
    @Column(name = "publico")
    private Boolean publico;
    
    @JoinColumn(name = "pk_directorio_id", referencedColumnName = "id", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Directorio directorio;
    
    @JoinColumn(name = "pk_usuario_id", referencedColumnName = "id", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Usuario usuario;

    public PermisoDirectorio() {
    }

    public PermisoDirectorio(PermisoDirectorioPK permisoDirectorioPK) {
        this.permisoDirectorioPK = permisoDirectorioPK;
    }

    public PermisoDirectorio(long pkDirectorioId, int pkUsuarioId) {
        this.permisoDirectorioPK = new PermisoDirectorioPK(pkDirectorioId, pkUsuarioId);
    }

    public PermisoDirectorioPK getPermisoDirectorioPK() {
        return permisoDirectorioPK;
    }

    public void setPermisoDirectorioPK(PermisoDirectorioPK permisoDirectorioPK) {
        this.permisoDirectorioPK = permisoDirectorioPK;
    }

    public Boolean getConsultar() {
        return consultar;
    }

    public void setConsultar(Boolean consultar) {
        this.consultar = consultar;
    }

    public Boolean getModificar() {
        return modificar;
    }

    public void setModificar(Boolean modificar) {
        this.modificar = modificar;
    }

    public Boolean getEliminar() {
        return eliminar;
    }

    public void setEliminar(Boolean eliminar) {
        this.eliminar = eliminar;
    }

    public Boolean getPublico() {
        return publico;
    }

    public void setPublico(Boolean publico) {
        this.publico = publico;
    }

    public Directorio getDirectorio() {
        return directorio;
    }

    public void setDirectorio(Directorio directorio) {
        this.directorio = directorio;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (permisoDirectorioPK != null ? permisoDirectorioPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PermisoDirectorio)) {
            return false;
        }
        PermisoDirectorio other = (PermisoDirectorio) object;
        if ((this.permisoDirectorioPK == null && other.permisoDirectorioPK != null) || (this.permisoDirectorioPK != null && !this.permisoDirectorioPK.equals(other.permisoDirectorioPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "co.sigess.entities.ado.PermisoDirectorio[ permisoDirectorioPK=" + permisoDirectorioPK + " ]";
    }
    
}
