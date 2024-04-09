/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.sigess.entities.ind;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.Date;
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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Usuario
 */
@Entity
@JsonIgnoreProperties(ignoreUnknown = true)
@Table(name = "vw_numeroat", schema ="ind")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ReporteATView.findAll", query = "SELECT rat FROM ReporteATView rat")})
public class ReporteATView implements Serializable{
    private static final long serialVersionUID = 1L;
    
    //@SequenceGenerator(name = "ReporteATView_id_seq", schema = "ind", sequenceName = "ReporteATView_id_seq", allocationSize = 1)
    //@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "hht_id_seq")
    
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "id")
    private Integer id;
    
    @Column(name = "padre_nombre")
    private String padreNombre;
    
    @Column(name = "incapacidades")
    private String incapacidades;
        
    @Column(name = "fecha_reporte")
    @Temporal(TemporalType.DATE)
    private Date fechaReporte;
    
    @Column(name = "fk_empresa_id")
    private Integer empresaId;
    
    @Column(name = "empresa")
    private String empresa;
    
    @Column(name = "temporal")
    private String temporal;
    
    @Column(name = "causo_muerte")
    private boolean causoMuerte;
    
    @Column(name = "complementaria")
    private String complementaria;
    
    @Column(name = "id_planta")
    private Integer idPlanta;
    
    @Column(name = "pais")
    private String pais;
    
    @Column(name = "nombre_planta")
    private String nombrePlanta;
    
    @Column(name = "id_localidad")
    private Integer idLocalidad;
    
    @Column(name = "nombre_localidad")
    private String nombreLocalidad;
    
    public ReporteATView() {
    }
    
        public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
    
    public String getPadreNombre() {
        return padreNombre;
    }

    public void setPadreNombre(String padreNombre) {
        this.padreNombre = padreNombre;
    }
    
        public String getIncapacidades() {
        return incapacidades;
    }

    public void setIncapacidades(String incapacidades) {
        this.incapacidades = incapacidades;
    }
    
    /**
     * @return the fechaReporte
     */
    public Date getFechaReporte() {
        return fechaReporte;
    }

    /**
     * @param fechaReporte the fechareporte to set
     */
    public void setFechaReporte(Date fechaReporte) {
        this.fechaReporte = fechaReporte;
    }
    
    public Integer getEmpresaId() {
        return empresaId;
    }

    public void setEmpresaId(Integer empresaId) {
        this.empresaId = empresaId;
    }
        /**
     * @return the empresa
     */
    public String getEmpresa() {
        return empresa;
    }

    /**
     * @param empresa the empresa to set
     */
    public void setEmpresa(String empresa) {
        this.empresa = empresa;
    }

    public String getTemporal() {
        return temporal;
    }

    public void setTemporal(String temporal) {
        this.temporal = temporal;
    }

    public boolean isCausoMuerte() {
        return causoMuerte;
    }

    public void setCausoMuerte(boolean causoMuerte) {
        this.causoMuerte = causoMuerte;
    }

    public String getComplementaria() {
        return complementaria;
    }

    public void setComplementaria(String complementaria) {
        this.complementaria = complementaria;
    }

    public Integer getIdPlanta() {
        return idPlanta;
    }

    public void setIdPlanta(Integer idPlanta) {
        this.idPlanta = idPlanta;
    }

    public String getPais() {
        return pais;
    }

    public void setPais(String pais) {
        this.pais = pais;
    }

    public String getNombrePlanta() {
        return nombrePlanta;
    }

    public void setNombrePlanta(String nombrePlanta) {
        this.nombrePlanta = nombrePlanta;
    }

    public Integer getIdLocalidad() {
        return idLocalidad;
    }

    public void setIdLocalidad(Integer idLocalidad) {
        this.idLocalidad = idLocalidad;
    }

    public String getNombreLocalidad() {
        return nombreLocalidad;
    }

    public void setNombreLocalidad(String nombreLocalidad) {
        this.nombreLocalidad = nombreLocalidad;
    }
    
    
}
