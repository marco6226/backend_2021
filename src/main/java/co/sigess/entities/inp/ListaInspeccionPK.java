/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.sigess.entities.inp;

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
public class ListaInspeccionPK implements Serializable {

    @Basic(optional = false)
    @Column(name = "id")
    @SequenceGenerator(name = "lista_inspeccion_id_seq", schema = "inp", sequenceName = "lista_inspeccion_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "lista_inspeccion_id_seq")
    private int id;
    
    @Basic(optional = false)
    @NotNull
    @Column(name = "version")
    private int version;

    public ListaInspeccionPK() {
    }

    public ListaInspeccionPK(int id, int version) {
        this.id = id;
        this.version = version;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
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
        if (!(object instanceof ListaInspeccionPK)) {
            return false;
        }
        ListaInspeccionPK other = (ListaInspeccionPK) object;
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
        return "co.sigess.entities.inp.ListaInspeccionPK[ id=" + id + ", version=" + version + " ]";
    }
    
}
