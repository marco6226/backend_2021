/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.sigess.entities.scm;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author leonardo
 */
@Entity
@Table(name = "pcl",schema = "scm")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Pcl.findAll", query = "SELECT p FROM Pcl p")})
public class Pcl implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "id")
    private Long id;
    @Size(max = 2147483647)
    @Column(name = "porcentaje_pcl")
    private String porcentajePcl;
    @Size(max = 2147483647)
    @Column(name = "emision_pcl_fecha")
    private String emisionPclFecha;
    @Size(max = 2147483647)
    @Column(name = "pcl_emit_entidad")
    private String entidadEmitePcl;
    @Size(max = 2147483647)
    @Column(name = "pcl")
    private String pcl;
    @Size(max = 2147483647)
    @Column(name = "diag")
    private String diag;

    public Pcl() {
    }

    public Pcl(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPorcentajePcl() {
        return porcentajePcl;
    }

    public void setPorcentajePcl(String porcentajePcl) {
        this.porcentajePcl = porcentajePcl;
    }

    public String getEmisionPclFecha() {
        return emisionPclFecha;
    }

    public void setEmisionPclFecha(String emisionPclFecha) {
        this.emisionPclFecha = emisionPclFecha;
    }

    public String getPclEmitEntidad() {
        return entidadEmitePcl;
    }

    public void setPclEmitEntidad(String entidadEmitePcl) {
        this.entidadEmitePcl = entidadEmitePcl;
    }

    public String getPcl() {
        return pcl;
    }

    public void setPcl(String pcl) {
        this.pcl = pcl;
    }

    public String getDiag() {
        return diag;
    }

    public void setDiag(String diag) {
        this.diag = diag;
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
        if (!(object instanceof Pcl)) {
            return false;
        }
        Pcl other = (Pcl) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "co.sigess.entities.scm.Pcl[ id=" + id + " ]";
    }
    
}
