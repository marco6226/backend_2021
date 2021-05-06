/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.sigess.entities.ipr;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author fmoreno
 */
@Entity
@Table(name = "peligro", schema = "ipr")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Peligro.findAll", query = "SELECT p FROM Peligro p")})
public class Peligro implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @SequenceGenerator(name = "peligro_id_seq", schema = "ipr", sequenceName = "peligro_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "peligro_id_seq")
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    
    @Size(max = 255)
    @Column(name = "nombre")
    private String nombre;
    
    @JoinColumn(name = "fk_tipo_peligro", referencedColumnName = "id", updatable = false)
    @ManyToOne(optional = false)
    private TipoPeligro tipoPeligro;
    
    @OneToMany(mappedBy = "peligro")
    private List<Fuente> fuenteList;
    
    @OneToMany(mappedBy = "peligro")
    private List<Control> controlList;
    
    @OneToMany(mappedBy = "peligro")
    private List<Efecto> efectoList;
    
    public Peligro() {
    }

    public Peligro(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public TipoPeligro getTipoPeligro() {
        return tipoPeligro;
    }

    public void setTipoPeligro(TipoPeligro tipoPeligro) {
        this.tipoPeligro = tipoPeligro;
    }

    @XmlTransient
    public List<Fuente> getFuenteList() {
        return fuenteList;
    }

    public void setFuenteList(List<Fuente> fuenteList) {
        this.fuenteList = fuenteList;
    }

    @XmlTransient
    public List<Control> getControlList() {
        return controlList;
    }

    public void setControlList(List<Control> controlList) {
        this.controlList = controlList;
    }

    @XmlTransient
    public List<Efecto> getEfectoList() {
        return efectoList;
    }

    public void setEfectoList(List<Efecto> efectoList) {
        this.efectoList = efectoList;
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
        if (!(object instanceof Peligro)) {
            return false;
        }
        Peligro other = (Peligro) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "co.sigess.entities.ipr.Peligro[ id=" + id + " ]";
    }
    
}
