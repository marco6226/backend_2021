/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.sigess.entities.sec;

import co.sigess.entities.emp.Area;
import co.sigess.entities.emp.Localidades;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author fmoreno
 */
@Entity
@JsonIgnoreProperties(ignoreUnknown = true)
@Table(name = "vw_desviacion", schema = "sec")
@XmlRootElement
public class Desviacion implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Column(name = "hash_id")
    @Id
    private String hashId;
    
    @Column(name = "modulo")
    private String modulo;
    
    @Column(name = "concepto")
    private String concepto;
    
    @Column(name = "fecha_reporte")
    @Temporal(TemporalType.DATE)
    private Date fechaReporte;
    
    @Column(name = "aspecto_causante")
    private String aspectoCausante;
    
    @Column(name = "nivel_riesgo")
    private String nivelRiesgo;
        
    @JoinColumn(name = "fk_area_id", referencedColumnName = "id")
    @ManyToOne
    private Area area;
        
    @Column(name = "fk_empresa_id")
    private Integer empresaId;
    
    @Column(name = "analisis_id")
    private Integer analisisId;
    
    @Column(name = "criticidad")
    private String criticidad;
    
    @Column(name = "nombre")
    private String nombre;
    
     @Column(name = "hora")
    private String hora;
     
     @Column(name = "empresa")
    private String empresa;
     
     @Column(name = "nit")
    private String nit;
     
     @Column(name = "severidad")
    private String severidad;
     
       @Column(name = "furat")
    private String furat;
       
    @Column(name = "emptemporal")
    private String emptemporal;
     
    @Column(name = "causo_muerte")
    private boolean causoMuerte;
    
    @Column(name = "email")
    private String email;
    
    @Column(name = "localidad")
    private String localidad;
    
    @Column(name = "division")
    private String division;
    
    @Column(name = "aliado")
    private String aliado;
    
    @JoinColumn(name = "railocalidad", referencedColumnName = "id")
    @ManyToOne
    private Localidades railocalidad;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
        

    public String getCriticidad() {
        return criticidad;
    }

    public void setCriticidad(String criticidad) {
        this.criticidad = criticidad;
    }

    public Desviacion() {
    }

    public String getHashId() {
        return hashId;
    }

    public void setHashId(String hashId) {
        this.hashId = hashId;
    }

    public String getModulo() {
        return modulo;
    }

    public void setModulo(String modulo) {
        this.modulo = modulo;
    }

    public String getConcepto() {
        return concepto;
    }

    public void setConcepto(String concepto) {
        this.concepto = concepto;
    }

    public String getAspectoCausante() {
        return aspectoCausante;
    }

    public void setAspectoCausante(String aspectoCausante) {
        this.aspectoCausante = aspectoCausante;
    }

    public String getNivelRiesgo() {
        return nivelRiesgo;
    }

    public void setNivelRiesgo(String nivelRiesgo) {
        this.nivelRiesgo = nivelRiesgo;
    }

    public Integer getEmpresaId() {
        return empresaId;
    }

    public void setEmpresaId(Integer empresaId) {
        this.empresaId = empresaId;
    }

    public Integer getAnalisisId() {
        return analisisId;
    }

    public void setAnalisisId(Integer analisisId) {
        this.analisisId = analisisId;
    }

    public Area getArea() {
        return area;
    }

    public void setArea(Area area) {
        this.area = area;
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

    /**
     * @return the nombre
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * @param nombre the nombre to set
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * @return the hora
     */
    public String getHora() {
        return hora;
    }

    /**
     * @param hora the hora to set
     */
    public void setHora(String hora) {
        this.hora = hora;
    }

    /**
     * @return the severidad
     */
    public String getSeveridad() {
        return severidad;
    }

    /**
     * @param severidad the severidad to set
     */
    public void setSeveridad(String severidad) {
        this.severidad = severidad;
    }

    /**
     * @return the empresa
     */
    public String getEmpresa() {
        return empresa;
    }

    /**
     * @return the nit
     */
    public String getNit() {
        return nit;
    }

    /**
     * @param empresa the empresa to set
     */
    public void setEmpresa(String empresa) {
        this.empresa = empresa;
    }

    /**
     * @param nit the nit to set
     */
    public void setNit(String nit) {
        this.nit = nit;
    }

    /**
     * @return the furat
     */
    public String getFurat() {
        return furat;
    }

    /**
     * @param furat the furat to set
     */
    public void setFurat(String furat) {
        this.furat = furat;
    }
    
    public String getEmptemporal() {
        return emptemporal;
    }

    public void setEmptemporal(String emptemporal) {
        this.emptemporal = emptemporal;
    }

    public boolean isCausoMuerte() {
        return causoMuerte;
    }

    public void setCausoMuerte(boolean causoMuerte) {
        this.causoMuerte = causoMuerte;
    }

    public String getLocalidad() {
        return localidad;
    }

    public void setLocalidad(String localidad) {
        this.localidad = localidad;
    }

    public String getDivision() {
        return division;
    }

    public void setDivision(String division) {
        this.division = division;
    }

    public String getAliado() {
        return aliado;
    }

    public void setAliado(String aliado) {
        this.aliado = aliado;
    }

    public Localidades getRailocalidad() {
        return railocalidad;
    }

    public void setRailocalidad(Localidades railocalidad) {
        this.railocalidad = railocalidad;
    }
}
