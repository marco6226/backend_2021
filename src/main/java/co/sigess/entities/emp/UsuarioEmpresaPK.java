/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.sigess.entities.emp;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;

/**
 *
 * @author fmoreno
 */
@Embeddable
public class UsuarioEmpresaPK implements Serializable {

    @Basic(optional = false)
    @NotNull
    @Column(name = "pk_usuario_id")
    private int pkUsuarioId;
    @Basic(optional = false)
    @NotNull
    @Column(name = "pk_empresa_id")
    private int pkEmpresaId;
    @Basic(optional = false)
    @NotNull
    @Column(name = "pk_perfil_id")
    private int pkPerfilId;

    public UsuarioEmpresaPK() {
    }

    public UsuarioEmpresaPK(int pkUsuarioId, int pkEmpresaId, int pkPerfilId) {
        this.pkUsuarioId = pkUsuarioId;
        this.pkEmpresaId = pkEmpresaId;
        this.pkPerfilId = pkPerfilId;
    }

    public int getPkUsuarioId() {
        return pkUsuarioId;
    }

    public void setPkUsuarioId(int pkUsuarioId) {
        this.pkUsuarioId = pkUsuarioId;
    }

    public int getPkEmpresaId() {
        return pkEmpresaId;
    }

    public void setPkEmpresaId(int pkEmpresaId) {
        this.pkEmpresaId = pkEmpresaId;
    }

    public int getPkPerfilId() {
        return pkPerfilId;
    }

    public void setPkPerfilId(int pkPerfilId) {
        this.pkPerfilId = pkPerfilId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) pkUsuarioId;
        hash += (int) pkEmpresaId;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof UsuarioEmpresaPK)) {
            return false;
        }
        UsuarioEmpresaPK other = (UsuarioEmpresaPK) object;
        if (this.pkUsuarioId != other.pkUsuarioId) {
            return false;
        }
        if (this.pkEmpresaId != other.pkEmpresaId) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "co.sigess.entities.emp.UsuarioEmpresaPK[ pkUsuarioId=" + pkUsuarioId + ", pkEmpresaId=" + pkEmpresaId + " ]";
    }
    
}
