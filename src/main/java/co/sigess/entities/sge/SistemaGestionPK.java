/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.sigess.entities.sge;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.SequenceGenerator;
import javax.validation.constraints.NotNull;

/**
 *
 * @author fmoreno
 */
@Embeddable
public class SistemaGestionPK implements Serializable {

    @Basic(optional = false)
    @NotNull
    @SequenceGenerator(name = "sistema_gestion_id_seq", schema = "sge", sequenceName = "sistema_gestion_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sistema_gestion_id_seq")
    @Column(name = "id")
    private int id;
    @Basic(optional = false)
    @NotNull
    @Column(name = "version")
    private short version;

    public SistemaGestionPK() {
    }

    public SistemaGestionPK(int id, short version) {
        this.id = id;
        this.version = version;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public short getVersion() {
        return version;
    }

    public void setVersion(short version) {
        this.version = version;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) id;
        hash += (int) version;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SistemaGestionPK)) {
            return false;
        }
        SistemaGestionPK other = (SistemaGestionPK) object;
        if (this.id != other.id) {
            return false;
        }
        if (this.version != other.version) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "co.sigess.entities.sgeSistemaGestionPK[ id=" + id + ", version=" + version + " ]";
    }
    
}
