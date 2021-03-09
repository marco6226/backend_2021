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
@Table(name = "sve")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Sve.findAll", query = "SELECT s FROM Sve s"),
    @NamedQuery(name = "Sve.findById", query = "SELECT s FROM Sve s WHERE s.id = :id"),
    @NamedQuery(name = "Sve.findByNombre", query = "SELECT s FROM Sve s WHERE s.nombre = :nombre"),
    @NamedQuery(name = "Sve.findByDescripcion", query = "SELECT s FROM Sve s WHERE s.descripcion = :descripcion"),
    @NamedQuery(name = "Sve.findByFkEmpresaId", query = "SELECT s FROM Sve s WHERE s.fkEmpresaId = :fkEmpresaId"),
    @NamedQuery(name = "Sve.findByFicha", query = "SELECT s FROM Sve s WHERE s.ficha = :ficha")})
public class Sve implements Serializable {

    private static final long serialVersionUID = 1L;
   @Id
    @Basic(optional = false)
    @NotNull
    @SequenceGenerator(name = "sve_id_seq", schema = "scm", sequenceName = "sve_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sve_id_seq")
    @Column(name = "id")
    private Long id;
    @Size(max = 45)
    @Column(name = "nombre")
    private String nombre;
    @Size(max = 255)
    @Column(name = "descripcion")
    private String descripcion;
    @Basic(optional = false)
    @NotNull
    @Column(name = "fk_empresa_id")
    private int fkEmpresaId;
    @Size(max = 2147483647)
    @Column(name = "ficha")
    private String ficha;

    public Sve() {
    }

    public Sve(Long id) {
        this.id = id;
    }

    public Sve(Long id, int fkEmpresaId) {
        this.id = id;
        this.fkEmpresaId = fkEmpresaId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public int getFkEmpresaId() {
        return fkEmpresaId;
    }

    public void setFkEmpresaId(int fkEmpresaId) {
        this.fkEmpresaId = fkEmpresaId;
    }

    public String getFicha() {
        return ficha;
    }

    public void setFicha(String ficha) {
        this.ficha = ficha;
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
        if (!(object instanceof Sve)) {
            return false;
        }
        Sve other = (Sve) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "co.sigess.entities.scm.Sve[ id=" + id + " ]";
    }
    
}
