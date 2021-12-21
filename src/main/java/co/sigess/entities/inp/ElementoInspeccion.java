/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.sigess.entities.inp;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
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
import javax.persistence.JoinColumns;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author fmoreno
 */
@Entity
@JsonIgnoreProperties(ignoreUnknown = true)
@Table(name = "elemento_inspeccion", schema = "inp")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ElementoInspeccion.findAll", query = "SELECT e FROM ElementoInspeccion e")})
public class ElementoInspeccion implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @SequenceGenerator(name = "elemento_inspeccion_id_seq", schema = "inp", sequenceName = "elemento_inspeccion_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "elemento_inspeccion_id_seq")
    @Basic(optional = false)
    @Column(name = "id")
    private Long id;
    
    @Size(max = 20)
    @Column(name = "codigo")
    private String codigo;
    
    @Size(max = 255)
    @Column(name = "nombre")
    private String nombre;
    
    @Size(max = 512)
    @Column(name = "descripcion")
    private String descripcion;
    
    @Size(max = 512)
    @Column(name = "criticidad")
    private String criticidad;
    
    @Column(name = "calificable")
    private boolean calificable;
    
    @OrderBy("id")
    @OneToMany(cascade = CascadeType.REMOVE, mappedBy = "elementoInspeccionPadre")
    private List<ElementoInspeccion> elementoInspeccionList;
    
    @JsonIgnore
    @JoinColumn(name = "fk_elemento_inspeccion_padre_id", referencedColumnName = "id")
    @ManyToOne
    private ElementoInspeccion elementoInspeccionPadre;
    
    @JsonIgnore
    @JoinColumns({
        @JoinColumn(name = "fk_lista_inspeccion_id", referencedColumnName = "id")
        , @JoinColumn(name = "fk_lista_inspeccion_version", referencedColumnName = "version")})
    @ManyToOne(optional = false)
    private ListaInspeccion listaInspeccion;
    
    
    @JoinTable(name = "tipo_hallazgo_elemento_inspeccion", schema = "inp", joinColumns = {
        @JoinColumn(name = "fk_elemento_inspeccion_id", referencedColumnName = "id")}, inverseJoinColumns = {
        @JoinColumn(name = "fk_tipo_hallazgo_id", referencedColumnName = "id")})
    @ManyToMany
    private List<TipoHallazgo> tipoHallazgoList;

    public ElementoInspeccion() {
    }

    public ElementoInspeccion(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
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
    
    public String getCriticidad() {
        return criticidad;
    }

    public void setCriticidad(String criticidad) {
        this.criticidad = criticidad;
    }

    public List<ElementoInspeccion> getElementoInspeccionList() {
        return elementoInspeccionList;
    }

    public void setElementoInspeccionList(List<ElementoInspeccion> elementoInspeccionList) {
        this.elementoInspeccionList = elementoInspeccionList;
    }

    @JsonIgnore
    public ElementoInspeccion getElementoInspeccionPadre() {
        return elementoInspeccionPadre;
    }

    public void setElementoInspeccionPadre(ElementoInspeccion elementoInspeccionPadre) {
        this.elementoInspeccionPadre = elementoInspeccionPadre;
    }

    @JsonIgnore
    public ListaInspeccion getListaInspeccion() {
        return listaInspeccion;
    }

    public void setListaInspeccion(ListaInspeccion listaInspeccion) {
        this.listaInspeccion = listaInspeccion;
    }

    public boolean isCalificable() {
        return calificable;
    }

    public void setCalificable(boolean calificable) {
        this.calificable = calificable;
    }

    public List<TipoHallazgo> getTipoHallazgoList() {
        return tipoHallazgoList;
    }

    public void setTipoHallazgoList(List<TipoHallazgo> tipoHallazgoList) {
        this.tipoHallazgoList = tipoHallazgoList;
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
        if (!(object instanceof ElementoInspeccion)) {
            return false;
        }
        ElementoInspeccion other = (ElementoInspeccion) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "co.sigess.entities.inp.ElementoInspeccion[ id=" + id + " ]";
    }
    
}
