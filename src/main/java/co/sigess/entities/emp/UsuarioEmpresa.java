/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.sigess.entities.emp;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author fmoreno
 */
@Entity
@Table(name = "usuario_empresa", schema = "emp")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "UsuarioEmpresa.findAll", query = "SELECT u FROM UsuarioEmpresa u")
    , @NamedQuery(name = "UsuarioEmpresa.findByPkUsuarioId", query = "SELECT u FROM UsuarioEmpresa u WHERE u.usuarioEmpresaPK.pkUsuarioId = :pkUsuarioId")
    , @NamedQuery(name = "UsuarioEmpresa.findByPkEmpresaId", query = "SELECT u FROM UsuarioEmpresa u WHERE u.usuarioEmpresaPK.pkEmpresaId = :pkEmpresaId")})
public class UsuarioEmpresa implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @EmbeddedId
    protected UsuarioEmpresaPK usuarioEmpresaPK;
    
    @JsonIgnore
    @JoinColumn(name = "pk_empresa_id", referencedColumnName = "id", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Empresa empresa;
    
    @JoinColumn(name = "pk_perfil_id", referencedColumnName = "id", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Perfil perfil;
    
    @JsonIgnore
    @JoinColumn(name = "pk_usuario_id", referencedColumnName = "id", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Usuario usuario;

    public UsuarioEmpresa() {
    }
    
    @JsonIgnore
    public String getAsJSON() {
        try {
            ObjectMapper mapper = new ObjectMapper();
            return mapper.writeValueAsString(this);
        } catch (JsonProcessingException ex) {
            Logger.getLogger(Usuario.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    public UsuarioEmpresa(UsuarioEmpresaPK usuarioEmpresaPK) {
        this.usuarioEmpresaPK = usuarioEmpresaPK;
    }

    public UsuarioEmpresa(int pkUsuarioId, int pkEmpresaId, int pkPerfilId) {
        this.usuarioEmpresaPK = new UsuarioEmpresaPK(pkUsuarioId, pkEmpresaId, pkPerfilId);
    }

    public UsuarioEmpresaPK getUsuarioEmpresaPK() {
        return usuarioEmpresaPK;
    }

    public void setUsuarioEmpresaPK(UsuarioEmpresaPK usuarioEmpresaPK) {
        this.usuarioEmpresaPK = usuarioEmpresaPK;
    }

    public Empresa getEmpresa() {
        return empresa;
    }

    public void setEmpresa(Empresa empresa) {
        this.empresa = empresa;
    }

    public Perfil getPerfil() {
        return perfil;
    }

    public void setPerfil(Perfil perfil) {
        this.perfil = perfil;
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
        hash += (usuarioEmpresaPK != null ? usuarioEmpresaPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof UsuarioEmpresa)) {
            return false;
        }
        UsuarioEmpresa other = (UsuarioEmpresa) object;
        if ((this.usuarioEmpresaPK == null && other.usuarioEmpresaPK != null) || (this.usuarioEmpresaPK != null && !this.usuarioEmpresaPK.equals(other.usuarioEmpresaPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "co.sigess.entities.emp.UsuarioEmpresa[ usuarioEmpresaPK=" + usuarioEmpresaPK + " ]";
    }
    
}
