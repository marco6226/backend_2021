/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.sigess.entities.ipr;

import co.sigess.entities.conf.Consecuencia;
import co.sigess.entities.conf.NivelRiesgo;
import co.sigess.entities.conf.Probabilidad;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
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
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author fmoreno
 */
@Entity
@JsonIgnoreProperties(ignoreUnknown = true)
@Table(name = "peligro_ipecr", schema = "ipr")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "PeligroIpecr.findAll", query = "SELECT p FROM PeligroIpecr p")})
public class PeligroIpecr implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @SequenceGenerator(name = "peligro_ipecr_id_seq", schema = "ipr", sequenceName = "peligro_ipecr_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "peligro_ipecr_id_seq")
    @Basic(optional = false)
    @Column(name = "id")
    private Long id;
    
    @Column(name = "proceso", length = 255)
    private String proceso;
    
    @Column(name = "zona_lugar", length = 255)
    private String zonaLugar;
    
    @Column(name = "tarea", length = 255)
    private String tarea;    
    
    @Column(name = "actividad", length = 255)
    private String actividad;
    
    @Column(name = "peor_consecuencia", length = 255)
    private String peorConsecuencia;
    
    @Column(name = "rutinario")
    private Boolean rutinario;
    
    @Column(name = "nivel_deficiencia")
    private Short nivelDeficiencia;
    
    @Column(name = "nivel_exposicion")
    private Short nivelExposicion;
    
    @Column(name = "numero_expuestos")
    private Short numeroExpuestos;
    
    @Column(name = "valor_probabilidad")
    private Short valorProbabilidad;
    
    @Column(name = "valor_riesgo")
    private Short valorRiesgo;
    
    @JoinColumn(name = "fk_probabilidad_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Probabilidad probabilidad;
    
    @JoinColumn(name = "fk_consecuencia_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Consecuencia consecuencia;
    
    @JoinTable(name = "fuente_peligro_ipecr", schema = "ipr", joinColumns = {
        @JoinColumn(name = "fk_peligro_ipecr_id", referencedColumnName = "id")}, inverseJoinColumns = {
        @JoinColumn(name = "fk_fuente_id", referencedColumnName = "id")})
    @ManyToMany
    private List<Fuente> fuenteList;
    
    @JoinTable(name = "efecto_peligro_ipecr", schema = "ipr", joinColumns = {
        @JoinColumn(name = "fk_peligro_ipecr_id", referencedColumnName = "id")}, inverseJoinColumns = {
        @JoinColumn(name = "fk_efecto_id", referencedColumnName = "id")})
    @ManyToMany
    private List<Efecto> efectoList;
    
    @JoinTable(name = "control_peligro_ipecr", schema = "ipr", joinColumns = {
        @JoinColumn(name = "fk_peligro_ipecr_id", referencedColumnName = "id")}, inverseJoinColumns = {
        @JoinColumn(name = "fk_control_id", referencedColumnName = "id")})
    @ManyToMany
    private List<Control> controlList;
    
    @JoinTable(name = "necesidad_control_peligro_ipecr", schema = "ipr", joinColumns = {
        @JoinColumn(name = "fk_peligro_ipecr_id", referencedColumnName = "id")}, inverseJoinColumns = {
        @JoinColumn(name = "fk_control_id", referencedColumnName = "id")})
    @ManyToMany
    private List<Control> necesidadControlList;
            
    @JoinColumn(name = "fk_nivel_riesgo_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private NivelRiesgo nivelRiesgo;
        
    @JoinColumn(name = "fk_peligro_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Peligro peligro;

    @JoinColumn(name = "fk_ipecr_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Ipecr ipecr;
    
    public PeligroIpecr() {
    }

    public PeligroIpecr(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Short getNumeroExpuestos() {
        return numeroExpuestos;
    }

    public void setNumeroExpuestos(Short numeroExpuestos) {
        this.numeroExpuestos = numeroExpuestos;
    }

    public NivelRiesgo getNivelRiesgo() {
        return nivelRiesgo;
    }

    public void setNivelRiesgo(NivelRiesgo nivelRiesgo) {
        this.nivelRiesgo = nivelRiesgo;
    }

    public Probabilidad getProbabilidad() {
        return probabilidad;
    }

    public void setProbabilidad(Probabilidad probabilidad) {
        this.probabilidad = probabilidad;
    }

    public Consecuencia getConsecuencia() {
        return consecuencia;
    }

    public void setConsecuencia(Consecuencia consecuencia) {
        this.consecuencia = consecuencia;
    }

    public String getProceso() {
        return proceso;
    }

    public void setProceso(String proceso) {
        this.proceso = proceso;
    }

    public String getZonaLugar() {
        return zonaLugar;
    }

    public void setZonaLugar(String zonaLugar) {
        this.zonaLugar = zonaLugar;
    }

    public String getTarea() {
        return tarea;
    }

    public void setTarea(String tarea) {
        this.tarea = tarea;
    }

    public String getActividad() {
        return actividad;
    }

    public void setActividad(String actividad) {
        this.actividad = actividad;
    }

    public Boolean getRutinario() {
        return rutinario;
    }

    public void setRutinario(Boolean rutinario) {
        this.rutinario = rutinario;
    }

    public Short getNivelDeficiencia() {
        return nivelDeficiencia;
    }

    public void setNivelDeficiencia(Short nivelDeficiencia) {
        this.nivelDeficiencia = nivelDeficiencia;
    }

    public Short getNivelExposicion() {
        return nivelExposicion;
    }

    public void setNivelExposicion(Short nivelExposicion) {
        this.nivelExposicion = nivelExposicion;
    }

    public Short getValorProbabilidad() {
        return valorProbabilidad;
    }

    public void setValorProbabilidad(Short valorProbabilidad) {
        this.valorProbabilidad = valorProbabilidad;
    }

    public Short getValorRiesgo() {
        return valorRiesgo;
    }

    public void setValorRiesgo(Short valorRiesgo) {
        this.valorRiesgo = valorRiesgo;
    }

    @JsonIgnore
    public Ipecr getIpecr() {
        return ipecr;
    }

    @JsonProperty("ipecr")
    public void setIpecr(Ipecr ipecr) {
        this.ipecr = ipecr;
    }

    public List<Fuente> getFuenteList() {
        return fuenteList;
    }

    public void setFuenteList(List<Fuente> fuenteList) {
        this.fuenteList = fuenteList;
    }

    public List<Efecto> getEfectoList() {
        return efectoList;
    }

    public void setEfectoList(List<Efecto> efectoList) {
        this.efectoList = efectoList;
    }

    public List<Control> getControlList() {
        return controlList;
    }

    public void setControlList(List<Control> controlList) {
        this.controlList = controlList;
    }

    public List<Control> getNecesidadControlList() {
        return necesidadControlList;
    }

    public void setNecesidadControlList(List<Control> necesidadControlList) {
        this.necesidadControlList = necesidadControlList;
    }

    public String getPeorConsecuencia() {
        return peorConsecuencia;
    }

    public void setPeorConsecuencia(String peorConsecuencia) {
        this.peorConsecuencia = peorConsecuencia;
    }

    public Peligro getPeligro() {
        return peligro;
    }

    public void setPeligro(Peligro peligro) {
        this.peligro = peligro;
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
        if (!(object instanceof PeligroIpecr)) {
            return false;
        }
        PeligroIpecr other = (PeligroIpecr) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "co.sigess.entities.ipr.PeligroIpecr[ id=" + id + " ]";
    }
    
}
