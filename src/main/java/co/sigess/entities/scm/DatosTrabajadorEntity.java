/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package co.sigess.entities.scm;

import co.sigess.entities.emp.CargoActual;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 *
 * @author Luis
 */
@Entity
@Table(name = "salud_laboral", schema = "scm")
@NamedQueries({
    @NamedQuery(name = "DatosTrabajadorEntity.findAll", query = "SELECT c FROM DatosTrabajadorEntity c")})
@JsonIgnoreProperties(ignoreUnknown = true)
public class DatosTrabajadorEntity implements Serializable{
    private static final long serialVersionUID = 1L;
    
    @Id
    @SequenceGenerator(name = "datos_trabajador_id_seq", schema = "scm", sequenceName = "datos_trabajador_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "datos_trabajador_id_seq")
    @Column(name = "id_sl")
    private Long idSl;
    
    
    @Column(name = "pk_empleado_caso")
    private Integer pkUser;
    
     @Column(name = "usuario_creador")
    private String usuarioCreador;
     

    @Column(name = "usuario_asignado")
    private String usuarioAsignado;

    @Column(name = "fecha_creacion")
    private Date fechaCreacion;

    @Column(name = "fecha_edicion")
    private Date fechaEdicion;

    @Column(name = "cargo_original")
    private String cargoOriginal;

    @Column(name = "cargo_actual")
    private String cargoActual;

    @Column(name = "division_origen")
    private String divisionOrigen;

    @Column(name = "division_actual")
    private String divisionActual;

    @Column(name = "localidad_origen")
    private String localidadOrigen;

    @Column(name = "localidad_actual")
    private String localidadActual;

    @Column(name = "area_origen")
    private String areaOrigen;

    @Column(name = "area_actual")
    private String areaActual;

    @Column(name = "proceso_origen")
    private String procesoOrigen;

    @Column(name = "proceso_actual")
    private String procesoActual;
    
    @Column(name = "nombre_completo_usuario")
    private String nombreCompletoSL;

    public String getNombreCompletoSL() {
        return nombreCompletoSL;
    }

    public void setNombreCompletoSL(String nombreCompletoSL) {
        this.nombreCompletoSL = nombreCompletoSL;
    }
    
    

    public Integer getPkUser() {
        return pkUser;
    }

    public void setPkUser(Integer pkUser) {
        this.pkUser = pkUser;
    }

    public Long getIdSl() {
        return idSl;
    }

    public void setIdSl(Long idSl) {
        this.idSl = idSl;
    }
    

 

    
    


    public String getUsuarioCreador() {
        return usuarioCreador;
    }

        public void setUsuarioCreador(String usuarioCreador) {
        this.usuarioCreador = usuarioCreador;
    }

    public String getUsuarioAsignado() {
        return usuarioAsignado;
    }

    public void setUsuarioAsignado(String usuarioAsignado) {
        this.usuarioAsignado = usuarioAsignado;
    }

    public Date getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(Date fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public Date getFechaEdicion() {
        return fechaEdicion;
    }

    public void setFechaEdicion(Date fechaEdicion) {
        this.fechaEdicion = fechaEdicion;
    }

    public String getCargoOriginal() {
        return cargoOriginal;
    }

    public void setCargoOriginal(String cargoOriginal) {
        this.cargoOriginal = cargoOriginal;
    }

    public String getCargoActual() {
        return cargoActual;
    }

    public void setCargoActual(String cargoActual) {
        this.cargoActual = cargoActual;
    }

    public String getDivisionOrigen() {
        return divisionOrigen;
    }

    public void setDivisionOrigen(String divisionOrigen) {
        this.divisionOrigen = divisionOrigen;
    }

    public String getDivisionActual() {
        return divisionActual;
    }

    public void setDivisionActual(String divisionActual) {
        this.divisionActual = divisionActual;
    }

    public String getLocalidadOrigen() {
        return localidadOrigen;
    }

    public void setLocalidadOrigen(String localidadOrigen) {
        this.localidadOrigen = localidadOrigen;
    }

    public String getLocalidadActual() {
        return localidadActual;
    }

    public void setLocalidadActual(String localidadActual) {
        this.localidadActual = localidadActual;
    }

    public String getAreaOrigen() {
        return areaOrigen;
    }

    public void setAreaOrigen(String areaOrigen) {
        this.areaOrigen = areaOrigen;
    }

    public String getAreaActual() {
        return areaActual;
    }

    public void setAreaActual(String areaActual) {
        this.areaActual = areaActual;
    }

    public String getProcesoOrigen() {
        return procesoOrigen;
    }

    public void setProcesoOrigen(String procesoOrigen) {
        this.procesoOrigen = procesoOrigen;
    }

    public String getProcesoActual() {
        return procesoActual;
    }

    public void setProcesoActual(String procesoActual) {
        this.procesoActual = procesoActual;
    }

    
    
}

    

