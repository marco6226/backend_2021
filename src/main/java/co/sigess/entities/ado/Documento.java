/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.sigess.entities.ado;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author fmoreno
 */
@Entity
@JsonIgnoreProperties(ignoreUnknown = true)
@Table(name = "documento", schema = "ado")
@NamedQueries({
    @NamedQuery(name = "Documento.findAll", query = "SELECT d FROM Documento d")})
public class Documento implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "id")
    private Long id;
    
    @Column(name = "version")
    private Short version;
    
    @Size(max = 150)
    @Column(name = "nombre")
    private String nombre;
    
    @Size(max = 100)
    @Column(name = "proceso")
    private String proceso;
    
    @Size(max = 25)
    @Column(name = "codigo")
    private String codigo;
    
    @Size(max = 255)
    @Column(name = "descripcion")
    private String descripcion;
    
    @Size(max = 100)
    @Column(name = "nombres_elaborador")
    private String nombresElaborador;
    
    @Size(max = 100)
    @Column(name = "apellidos_elaborador")
    private String apellidosElaborador;
    
    @Size(max = 25)
    @Column(name = "identificacion_elaborador")
    private String identificacionElaborador;
    
    @Size(max = 100)
    @Column(name = "nombres_verificador")
    private String nombresVerificador;
    
    @Size(max = 100)
    @Column(name = "apellidos_verificador")
    private String apellidosVerificador;
    
    @Size(max = 25)
    @Column(name = "identificacion_verificador")
    private String identificacionVerificador;
    
    @Size(max = 100)
    @Column(name = "nombres_aprobador")
    private String nombresAprobador;
    
    @Size(max = 100)
    @Column(name = "apellidos_aprobador")
    private String apellidosAprobador;
    
    @Size(max = 25)
    @Column(name = "identificacion_aprobador")
    private String identificacionAprobador;
        
    @Column(name = "fecha_elaboracion")
    @Temporal(TemporalType.DATE)
    private Date fechaElaboracion;
    
    @Column(name = "fecha_verificacion")
    @Temporal(TemporalType.DATE)
    private Date fechaVerificacion;
    
    @Column(name = "fecha_aprobacion")
    @Temporal(TemporalType.DATE)
    private Date fechaAprobacion;
    
    @Size(max = 45)
    @Column(name = "ubicacion_fisica")
    private String ubicacionFisica;
    
    @Column(name = "tamanio")
    private Long tamanio;
    
    @JsonIgnore
    @Size(max = 255)
    @Column(name = "ruta", updatable = false)
    private String ruta;
    
    @JsonIgnore
    @JoinColumn(name = "id", referencedColumnName = "id", insertable = false, updatable = false)
    @OneToOne(optional = false)
    private Directorio directorio;    
    
    @Enumerated(value = EnumType.STRING)
    @Column(name = "modulo")
    private Modulo modulo;
    
    public Documento() {
    }

    public Documento(Long id) {
        this.id = id;
    }
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Short getVersion() {
        return version;
    }

    public void setVersion(Short version) {
        this.version = version;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getProceso() {
        return proceso;
    }

    public void setProceso(String proceso) {
        this.proceso = proceso;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getNombresElaborador() {
        return nombresElaborador;
    }

    public void setNombresElaborador(String nombresElaborador) {
        this.nombresElaborador = nombresElaborador;
    }

    public String getApellidosElaborador() {
        return apellidosElaborador;
    }

    public void setApellidosElaborador(String apellidosElaborador) {
        this.apellidosElaborador = apellidosElaborador;
    }

    public String getIdentificacionElaborador() {
        return identificacionElaborador;
    }

    public void setIdentificacionElaborador(String identificacionElaborador) {
        this.identificacionElaborador = identificacionElaborador;
    }

    public String getNombresVerificador() {
        return nombresVerificador;
    }

    public void setNombresVerificador(String nombresVerificador) {
        this.nombresVerificador = nombresVerificador;
    }

    public String getApellidosVerificador() {
        return apellidosVerificador;
    }

    public void setApellidosVerificador(String apellidosVerificador) {
        this.apellidosVerificador = apellidosVerificador;
    }

    public String getIdentificacionVerificador() {
        return identificacionVerificador;
    }

    public void setIdentificacionVerificador(String identificacionVerificador) {
        this.identificacionVerificador = identificacionVerificador;
    }

    public String getNombresAprobador() {
        return nombresAprobador;
    }

    public void setNombresAprobador(String nombresAprobador) {
        this.nombresAprobador = nombresAprobador;
    }

    public String getApellidosAprobador() {
        return apellidosAprobador;
    }

    public void setApellidosAprobador(String apellidosAprobador) {
        this.apellidosAprobador = apellidosAprobador;
    }

    public String getIdentificacionAprobador() {
        return identificacionAprobador;
    }

    public void setIdentificacionAprobador(String identificacionAprobador) {
        this.identificacionAprobador = identificacionAprobador;
    }

    public Date getFechaElaboracion() {
        return fechaElaboracion;
    }

    public void setFechaElaboracion(Date fechaElaboracion) {
        this.fechaElaboracion = fechaElaboracion;
    }

    public Date getFechaVerificacion() {
        return fechaVerificacion;
    }

    public void setFechaVerificacion(Date fechaVerificacion) {
        this.fechaVerificacion = fechaVerificacion;
    }

    public Date getFechaAprobacion() {
        return fechaAprobacion;
    }

    public void setFechaAprobacion(Date fechaAprobacion) {
        this.fechaAprobacion = fechaAprobacion;
    }

    public String getUbicacionFisica() {
        return ubicacionFisica;
    }

    public void setUbicacionFisica(String ubicacionFisica) {
        this.ubicacionFisica = ubicacionFisica;
    }

    public String getRuta() {
        return ruta;
    }

    public void setRuta(String ruta) {
        this.ruta = ruta;
    }

    public Directorio getDirectorio() {
        return directorio;
    }

    public void setDirectorio(Directorio directorio) {
        this.directorio = directorio;
    }

    public Long getTamanio() {
        return tamanio;
    }

    public void setTamanio(Long tamanio) {
        this.tamanio = tamanio;
    }

    public Modulo getModulo() {
        return modulo;
    }

    public void setModulo(Modulo modulo) {
        this.modulo = modulo;
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
        if (!(object instanceof Documento)) {
            return false;
        }
        Documento other = (Documento) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "co.sigess.entities.ado.Documento[ id=" + id + " ]";
    }
    
}
