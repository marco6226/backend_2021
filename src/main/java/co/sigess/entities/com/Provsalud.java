/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.sigess.entities.com;

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
@Table(name = "provsalud",schema = "com")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Provsalud.findAll", query = "SELECT p FROM Provsalud p"),
    @NamedQuery(name = "Provsalud.findById", query = "SELECT p FROM Provsalud p WHERE p.id = :id"),
    @NamedQuery(name = "Provsalud.findByCodigo", query = "SELECT p FROM Provsalud p WHERE p.codigo = :codigo"),
    @NamedQuery(name = "Provsalud.findByNit", query = "SELECT p FROM Provsalud p WHERE p.nit = :nit"),
    @NamedQuery(name = "Provsalud.findByNombre", query = "SELECT p FROM Provsalud p WHERE p.nombre = :nombre")})
public class Provsalud implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "id")
    private Short id;
    @Size(max = 20)
    @Column(name = "codigo")
    private String codigo;
    @Size(max = 20)
    @Column(name = "nit")
    private String nit;
    @Size(max = 2147483647)
    @Column(name = "nombre")
    private String nombre;

    public Provsalud() {
    }

    public Provsalud(Short id) {
        this.id = id;
    }

    public Short getId() {
        return id;
    }

    public void setId(Short id) {
        this.id = id;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getNit() {
        return nit;
    }

    public void setNit(String nit) {
        this.nit = nit;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
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
        if (!(object instanceof Provsalud)) {
            return false;
        }
        Provsalud other = (Provsalud) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "co.sigess.entities.com.Provsalud[ id=" + id + " ]";
    }
    
}
