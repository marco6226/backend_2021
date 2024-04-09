/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.sigess.entities.ipr;

import co.sigess.entities.emp.Area;
import co.sigess.entities.emp.Cargo;
import co.sigess.entities.emp.Empresa;
import co.sigess.util.converter.StringListConverter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Convert;
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
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author fmoreno
 */
@Entity
@Table(name = "ipecr", schema = "ipr")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Ipecr.findAll", query = "SELECT i FROM Ipecr i")})
public class Ipecr implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @SequenceGenerator(name = "ipecr_id_seq", schema = "ipr", sequenceName = "ipecr_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ipecr_id_seq")
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    
    @Column(name = "rutinario")
    private Boolean rutinario;
    
    @Size(max = 255)
    @Column(name = "ejecucion")
    private String ejecucion;
    
    @Size(max = 255)
    @Column(name = "actividad")
    private String actividad;
    
    @Size(max = 512)
    @Column(name = "descripcion")
    private String descripcion;
    
    @Size(max = 45)
    @Column(name = "proceso")
    private String proceso;
    
    @Column(name = "num_trabajadores_propios")
    private Integer numTrabajadoresPropios;
    
    @Column(name = "num_trabajadores_externos")
    private Integer numTrabajadoresExternos;
    
    @Convert(converter = StringListConverter.class)
    @Column(name = "grupo_exp_similar")
    private List<String> grupoExpSimilarList;
    
    @Column(name = "fecha_elaboracion", updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaElaboracion;
    
//    @JsonIgnore
//    @OneToMany(mappedBy = "ipecr")
//    private List<PeligroIpecr> peligroIpecrList;    
    
    @JoinColumn(name = "fk_cargo_id", referencedColumnName = "id")
    @ManyToOne()
    private Cargo cargo;
    
    @JoinTable(name = "area_ipecr", schema = "ipr", joinColumns = {
        @JoinColumn(name = "fk_ipecr_id", referencedColumnName = "id")}, inverseJoinColumns = {
        @JoinColumn(name = "fk_area_id", referencedColumnName = "id")})
    @ManyToMany
    private List<Area> areaList;
    
    @Column(name = "fk_usuario_id", updatable = false)
    private Integer usuarioId;
    
    @JsonIgnore
    @JoinColumn(name = "fk_empresa_id", referencedColumnName = "id", updatable = false)
    @ManyToOne()
    private Empresa empresa;

    public Ipecr() {
    }

    public Ipecr(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Boolean getRutinario() {
        return rutinario;
    }

    public void setRutinario(Boolean rutinario) {
        this.rutinario = rutinario;
    }

    public String getEjecucion() {
        return ejecucion;
    }

    public void setEjecucion(String ejecucion) {
        this.ejecucion = ejecucion;
    }

    public String getActividad() {
        return actividad;
    }

    public void setActividad(String actividad) {
        this.actividad = actividad;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getProceso() {
        return proceso;
    }

    public void setProceso(String proceso) {
        this.proceso = proceso;
    }

    public Integer getNumTrabajadoresPropios() {
        return numTrabajadoresPropios;
    }

    public void setNumTrabajadoresPropios(Integer numTrabajadoresPropios) {
        this.numTrabajadoresPropios = numTrabajadoresPropios;
    }

    public Integer getNumTrabajadoresExternos() {
        return numTrabajadoresExternos;
    }

    public void setNumTrabajadoresExternos(Integer numTrabajadoresExternos) {
        this.numTrabajadoresExternos = numTrabajadoresExternos;
    }

    public Date getFechaElaboracion() {
        return fechaElaboracion;
    }

    public void setFechaElaboracion(Date fechaElaboracion) {
        this.fechaElaboracion = fechaElaboracion;
    }

    public Integer getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(Integer usuarioId) {
        this.usuarioId = usuarioId;
    }

    public Empresa getEmpresa() {
        return empresa;
    }

    public void setEmpresa(Empresa empresa) {
        this.empresa = empresa;
    }

    public List<String> getGrupoExpSimilarList() {
        return grupoExpSimilarList;
    }

    public void setGrupoExpSimilarList(List<String> grupoExpSimilarList) {
        this.grupoExpSimilarList = grupoExpSimilarList;
    }

    

//    public List<PeligroIpecr> getPeligroIpecrList() {
//        return peligroIpecrList;
//    }
//
//    public void setPeligroIpecrList(List<PeligroIpecr> peligroIpecrList) {
//        this.peligroIpecrList = peligroIpecrList;
//    }

    public List<Area> getAreaList() {
        return areaList;
    }

    public void setAreaList(List<Area> areaList) {
        this.areaList = areaList;
    }

    public Cargo getCargo() {
        return cargo;
    }

    public void setCargo(Cargo cargo) {
        this.cargo = cargo;
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
        if (!(object instanceof Ipecr)) {
            return false;
        }
        Ipecr other = (Ipecr) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "co.sigess.entities.ipr.Ipecr[ id=" + id + " ]";
    }
    
}
