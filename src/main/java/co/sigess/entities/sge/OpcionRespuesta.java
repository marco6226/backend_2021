/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.sigess.entities.sge;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
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
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author fmoreno
 */
@Entity
@JsonIgnoreProperties(ignoreUnknown = true)
@Table(name = "opcion_respuesta", schema = "sge")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "OpcionRespuesta.findAll", query = "SELECT o FROM OpcionRespuesta o")})
public class OpcionRespuesta implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @SequenceGenerator(name = "opcion_respuesta_id_seq", schema = "sge", sequenceName = "opcion_respuesta_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "opcion_respuesta_id_seq")
    @Column(name = "id")
    private Integer id;
    
    @Size(max = 45)
    @Column(name = "nombre")
    private String nombre;
    
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "valor")
    private Double valor;
    
    @Size(max = 100)
    @Column(name = "descripcion")
    private String descripcion;
    
    @Basic(optional = false)
    @NotNull
    @Column(name = "despreciable")
    private boolean despreciable;
    
    @OneToMany(mappedBy = "opcionRespuesta")
    private List<Respuesta> respuestaList;
    
    @JoinColumn(name = "fk_elemento_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Elemento elemento;

    public OpcionRespuesta() {
    }

    public OpcionRespuesta(Integer id) {
        this.id = id;
    }

    public OpcionRespuesta(Integer id, boolean despreciable) {
        this.id = id;
        this.despreciable = despreciable;
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

    public Double getValor() {
        return valor;
    }

    public void setValor(Double valor) {
        this.valor = valor;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public boolean getDespreciable() {
        return despreciable;
    }

    public void setDespreciable(boolean despreciable) {
        this.despreciable = despreciable;
    }

    @XmlTransient
    public List<Respuesta> getRespuestaList() {
        return respuestaList;
    }

    public void setRespuestaList(List<Respuesta> respuestaList) {
        this.respuestaList = respuestaList;
    }

    
    @JsonIgnore
    public Elemento getElemento() {
        return elemento;
    }

    @JsonProperty("elemento")
    public void setElemento(Elemento elemento) {
        this.elemento = elemento;
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
        if (!(object instanceof OpcionRespuesta)) {
            return false;
        }
        OpcionRespuesta other = (OpcionRespuesta) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "co.sigess.entities.sgeOpcionRespuesta[ id=" + id + " ]";
    }
    
}
