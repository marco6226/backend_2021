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

    @Column(name = "division_origen")
    private String divisionOrigen;

    @Column(name = "localidad_origen")
    private String localidadOrigen;

    @Column(name = "area_origen")
    private String areaOrigen;

    @Column(name = "proceso_origen")
    private String procesoOrigen;
    
        @Column(name = "division_actual")
    private String divisionActual;

    @Column(name = "localidad_actual")
    private String localidadActual;

    @Column(name = "area_actual")
    private String areaActual;

    @Column(name = "proceso_actual")
    private String procesoActual;
    
    @Column(name = "cargo_original")
    private String cargoOrigen;
    
    @Column(name = "cargo_actual")
    private String cargoActual;
    
    @Column(name = "eps_dictamen")
    private String epsDictamen;
    
    @Column(name = "arl_dictamen")
    private String arlDictamen;
    
    @Column(name = "jr_dictamen")
    private String jrDictamen;
    
    @Column(name = "cedula")
    private String cedulaUsuario;
    
    @Column(name = "nombres_sl")
    private String nombresSl;
    
    @Column(name = "edad")
    @Temporal(TemporalType.DATE)
    private Date edad;
    
    @Column(name = "antiguedad")
    @Temporal(TemporalType.DATE)
    private Date antiguedad;

    @Column(name = "sexo")
    private String genero;
    
    @Column(name = "pk_case_sl")
    private Integer idSl;
    
    @Column(name = "pk_user_sl")
    private Integer pkUserSl;

    public Integer getPkUserSl() {
        return pkUserSl;
    }

    public void setPkUserSl(Integer pkUserSl) {
        this.pkUserSl = pkUserSl;
    }
    
    

    public Integer getIdSl() {
        return idSl;
    }

    public void setIdSl(Integer idSl) {
        this.idSl = idSl;
    }
    
    

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }
    
    
    public Date getAntiguedad() {
        return antiguedad;
    }

    public void setAntiguedad(Date antiguedad) {
        this.antiguedad = antiguedad;
    }
    
    

    public Date getEdad() {
        return edad;
    }

    public void setEdad(Date edad) {
        this.edad = edad;
    }
    
    

    public String getNombresSl() {
        return nombresSl;
    }

    public void setNombresSl(String nombresSl) {
        this.nombresSl = nombresSl;
    }
    
    

    public String getCedulaUsuario() {
        return cedulaUsuario;
    }

    public void setCedulaUsuario(String cedulaUsuario) {
        this.cedulaUsuario = cedulaUsuario;
    }
    
    

    public String getEpsDictamen() {
        return epsDictamen;
    }

    public void setEpsDictamen(String epsDictamen) {
        this.epsDictamen = epsDictamen;
    }

    public String getArlDictamen() {
        return arlDictamen;
    }

    public void setArlDictamen(String arlDictamen) {
        this.arlDictamen = arlDictamen;
    }

    public String getJrDictamen() {
        return jrDictamen;
    }

    public void setJrDictamen(String jrDictamen) {
        this.jrDictamen = jrDictamen;
    }
    
    

    public String getCargoOrigen() {
        return cargoOrigen;
    }

    public void setCargoOrigen(String cargoOrigen) {
        this.cargoOrigen = cargoOrigen;
    }

    public String getCargoActual() {
        return cargoActual;
    }

    public void setCargoActual(String cargoActual) {
        this.cargoActual = cargoActual;
    }
    
    

    public String getDivisionActual() {
        return divisionActual;
    }

    public void setDivisionActual(String divisionActual) {
        this.divisionActual = divisionActual;
    }

    public String getLocalidadActual() {
        return localidadActual;
    }

    public void setLocalidadActual(String localidadActual) {
        this.localidadActual = localidadActual;
    }

    public String getAreaActual() {
        return areaActual;
    }

    public void setAreaActual(String areaActual) {
        this.areaActual = areaActual;
    }

    public String getProcesoActual() {
        return procesoActual;
    }

    public void setProcesoActual(String procesoActual) {
        this.procesoActual = procesoActual;
    }
    
    

    public String getAreaOrigen() {
        return areaOrigen;
    }

    public void setAreaOrigen(String areaOrigen) {
        this.areaOrigen = areaOrigen;
    }

    public String getProcesoOrigen() {
        return procesoOrigen;
    }

    public void setProcesoOrigen(String procesoOrigen) {
        this.procesoOrigen = procesoOrigen;
    }
    
    

    public String getLocalidadOrigen() {
        return localidadOrigen;
    }

    public void setLocalidadOrigen(String localidadOrigen) {
        this.localidadOrigen = localidadOrigen;
    }

    public String getDivisionOrigen() {
        return divisionOrigen;
    }

    public void setDivisionOrigen(String divisionOrigen) {
        this.divisionOrigen = divisionOrigen;
    }

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
