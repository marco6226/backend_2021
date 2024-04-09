/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.sigess.entities.auc;

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
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
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
@Table(name = "implicacion", schema = "auc")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Implicacion.findAll", query = "SELECT i FROM Implicacion i")})
public class Implicacion implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @SequenceGenerator(name = "implicacion_id_seq", schema = "auc", sequenceName = "implicacion_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "implicacion_id_seq")
    @Basic(optional = false)
    @Column(name = "id")    
    private Integer id;    
    
    @Size(max = 128)    
    @Column(name = "nombre")    
    private String nombre;    
    
    @Size(max = 255)
    @Column(name = "descripcion")
    private String descripcion;    
    
    @OneToMany(mappedBy = "implicacionPadre")
    private List<Implicacion> implicacionList;
    
    @JoinColumn(name = "fk_implicacion_padre_id", referencedColumnName = "id")
    @ManyToOne
    private Implicacion implicacionPadre;
    
    @JoinColumn(name = "fk_tarjeta_id", referencedColumnName = "id")
    @ManyToOne
    private Tarjeta tarjeta;

    public Implicacion() {
    }

    public Implicacion(Integer id) {
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

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public List<Implicacion> getImplicacionList() {
        return implicacionList;
    }

    public void setImplicacionList(List<Implicacion> implicacionList) {
        this.implicacionList = implicacionList;
    }

    @XmlTransient
    @JsonIgnore
    public Implicacion getImplicacionPadre() {
        return implicacionPadre;
    }

    @JsonProperty("implicacionPadre")
    public void setImplicacionPadre(Implicacion implicacionPadre) {
        this.implicacionPadre = implicacionPadre;
    }

    @XmlTransient
    @JsonIgnore
    public Tarjeta getTarjeta() {
        return tarjeta;
    }

    
    @JsonProperty("tarjeta")
    public void setTarjeta(Tarjeta tarjeta) {
        this.tarjeta = tarjeta;
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
        if (!(object instanceof Implicacion)) {
            return false;
        }
        Implicacion other = (Implicacion) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "co.sigess.entities.auc.Implicacion[ id=" + id + " ]";
    }
    
}
