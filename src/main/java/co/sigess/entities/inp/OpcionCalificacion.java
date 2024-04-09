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
@Table(name = "opcion_calificacion", schema = "inp")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "OpcionCalificacion.findAll", query = "SELECT o FROM OpcionCalificacion o")})
public class OpcionCalificacion implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @SequenceGenerator(name = "opcion_calificacion_id_seq", schema = "inp", sequenceName = "opcion_calificacion_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "opcion_calificacion_id_seq")
    @Basic(optional = false)
    @Column(name = "id")
    private Long id;
    
    @Size(max = 20)
    @Column(name = "nombre")
    private String nombre;
    
    @Size(max = 128)
    @Column(name = "descripcion")
    private String descripcion;
    
    @Basic(optional = false)
    @NotNull
    @Column(name = "valor")
    private double valor;
    
    @Column(name = "despreciable")
    private Boolean despreciable;
        
    @Column(name = "requerir_doc")
    private Boolean requerirDoc;
    
    @JsonIgnore
    @JoinColumns({
        @JoinColumn(name = "fk_lista_inspeccion_id", referencedColumnName = "id")
        , @JoinColumn(name = "fk_lista_inspeccion_version", referencedColumnName = "version")})
    @ManyToOne(optional = false)
    private ListaInspeccion listaInspeccion;
    
    @JsonIgnore
    @OneToMany(mappedBy = "opcionCalificacion")
    private List<Calificacion> calificacionList;
    
    @Column(name = "requerir_desc")
    private Boolean requerirDesc;

    public OpcionCalificacion() {
    }

    public OpcionCalificacion(Long id) {
        this.id = id;
    }

    public OpcionCalificacion(Long id, double valor) {
        this.id = id;
        this.valor = valor;
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

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }

    public Boolean getDespreciable() {
        return despreciable;
    }

    public void setDespreciable(Boolean despreciable) {
        this.despreciable = despreciable;
    }

    public ListaInspeccion getListaInspeccion() {
        return listaInspeccion;
    }

    public void setListaInspeccion(ListaInspeccion listaInspeccion) {
        this.listaInspeccion = listaInspeccion;
    }

    @XmlTransient
    public List<Calificacion> getCalificacionList() {
        return calificacionList;
    }

    public void setCalificacionList(List<Calificacion> calificacionList) {
        this.calificacionList = calificacionList;
    }

    public Boolean getRequerirDoc() {
        return requerirDoc;
    }

    public void setRequerirDoc(Boolean requerirDoc) {
        this.requerirDoc = requerirDoc;
    }

    public Boolean getRequerirDesc() {
        return requerirDesc;
    }

    public void setRequerirDesc(Boolean requerirDesc) {
        this.requerirDesc = requerirDesc;
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
        if (!(object instanceof OpcionCalificacion)) {
            return false;
        }
        OpcionCalificacion other = (OpcionCalificacion) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "co.sigess.entities.inp.OpcionCalificacion[ id=" + id + " ]";
    }
    
}
