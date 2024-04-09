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
 * @author leonardo
 */
@Entity
@Table(name = "sistema_afectado",schema="scm")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "SistemaAfectado.findAll", query = "SELECT s FROM SistemaAfectado s"),
    @NamedQuery(name = "SistemaAfectado.findById", query = "SELECT s FROM SistemaAfectado s WHERE s.id = :id"),
    @NamedQuery(name = "SistemaAfectado.findByName", query = "SELECT s FROM SistemaAfectado s WHERE s.name = :name")})
public class SistemaAfectado implements Serializable {

    private static final long serialVersionUID = 1L;
     @Id
    @Basic(optional = false)
    @NotNull
    @SequenceGenerator(name = "sistema_afectado_id_seq", schema = "scm", sequenceName = "sistema_afectado_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sistema_afectado_id_seq")
    @Column(name = "id")
    private Long id;
    @Size(max = 2147483647)
    @Column(name = "name")
    private String name;

    public SistemaAfectado() {
    }

    public SistemaAfectado(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
        if (!(object instanceof SistemaAfectado)) {
            return false;
        }
        SistemaAfectado other = (SistemaAfectado) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "co.sigess.entities.scm.SistemaAfectado[ id=" + id + " ]";
    }
    
}
