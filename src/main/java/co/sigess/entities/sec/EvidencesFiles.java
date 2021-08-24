/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.sigess.entities.sec;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Leo
 */
@Entity
@Table(name = "evidences_files",schema = "sec")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "EvidencesFiles.findAll", query = "SELECT e FROM EvidencesFiles e"),
    @NamedQuery(name = "EvidencesFiles.findById", query = "SELECT e FROM EvidencesFiles e WHERE e.id = :id"),
    @NamedQuery(name = "EvidencesFiles.findByFkSegId", query = "SELECT e FROM EvidencesFiles e WHERE e.fkSegId = :fkSegId"),
    @NamedQuery(name = "EvidencesFiles.findByRuta", query = "SELECT e FROM EvidencesFiles e WHERE e.ruta = :ruta")})
public class EvidencesFiles implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
     @SequenceGenerator(name = "evidences_seq", schema = "sec", sequenceName = "evidences_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "evidences_seq")
    @NotNull
    @Column(name = "id")
    private Integer id;
    @Column(name = "fk_seg_id")
    private Integer fkSegId;
    @Size(max = 2147483647)
    @Column(name = "ruta")
    private String ruta;

    public EvidencesFiles() {
    }

    public EvidencesFiles(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getFkSegId() {
        return fkSegId;
    }

    public void setFkSegId(Integer fkSegId) {
        this.fkSegId = fkSegId;
    }

    public String getRuta() {
        return ruta;
    }

    public void setRuta(String ruta) {
        this.ruta = ruta;
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
        if (!(object instanceof EvidencesFiles)) {
            return false;
        }
        EvidencesFiles other = (EvidencesFiles) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "co.sigess.entities.sec.EvidencesFiles[ id=" + id + " ]";
    }
    
}
