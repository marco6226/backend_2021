/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.sigess.entities.sec;

import co.sigess.entities.emp.Area;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
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
    private String fechaReporte;
    
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
    public String getFechaReporte() {
        return fechaReporte;
    }

    /**
     * @param fechareporte the fechareporte to set
     */
    public void setFechaReporte(String fechaReporte) {
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
    
}
