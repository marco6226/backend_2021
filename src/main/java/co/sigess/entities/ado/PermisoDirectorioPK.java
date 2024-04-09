/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.sigess.entities.ado;

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
public class PermisoDirectorioPK implements Serializable {

    @Basic(optional = false)
    @NotNull
    @Column(name = "pk_directorio_id")
    private long pkDirectorioId;
    @Basic(optional = false)
    @NotNull
    @Column(name = "pk_usuario_id")
    private int pkUsuarioId;

    public PermisoDirectorioPK() {
    }

    public PermisoDirectorioPK(long pkDirectorioId, int pkUsuarioId) {
        this.pkDirectorioId = pkDirectorioId;
        this.pkUsuarioId = pkUsuarioId;
    }

    public long getPkDirectorioId() {
        return pkDirectorioId;
    }

    public void setPkDirectorioId(long pkDirectorioId) {
        this.pkDirectorioId = pkDirectorioId;
    }

    public int getPkUsuarioId() {
        return pkUsuarioId;
    }

    public void setPkUsuarioId(int pkUsuarioId) {
        this.pkUsuarioId = pkUsuarioId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) pkDirectorioId;
        hash += (int) pkUsuarioId;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PermisoDirectorioPK)) {
            return false;
        }
        PermisoDirectorioPK other = (PermisoDirectorioPK) object;
        if (this.pkDirectorioId != other.pkDirectorioId) {
            return false;
        }
        if (this.pkUsuarioId != other.pkUsuarioId) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "co.sigess.entities.ado.PermisoDirectorioPK[ pkDirectorioId=" + pkDirectorioId + ", pkUsuarioId=" + pkUsuarioId + " ]";
    }
    
}
