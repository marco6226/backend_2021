/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.sigess.entities.sge;

import co.sigess.entities.emp.Empresa;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
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
@Table(name = "evaluacion", schema = "sge")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Evaluacion.findAll", query = "SELECT e FROM Evaluacion e")})
public class Evaluacion implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @SequenceGenerator(name = "evaluacion_id_seq", schema = "sge", sequenceName = "evaluacion_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "evaluacion_id_seq")
    @Column(name = "id")
    private Integer id;

    @Size(max = 100)
    @Column(name = "nombre")
    private String nombre;

    @Size(max = 45)
    @Column(name = "codigo")
    private String codigo;

    @Size(max = 1024)
    @Column(name = "descripcion")
    private String descripcion;

    @Column(name = "fecha_inicio", updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Calendar fechaInicio;

    @Column(name = "fecha_finalizacion", updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaFinalizacion;

    @Size(max = 100)
    @Column(name = "nombre_responsable")
    private String nombreResponsable;

    @Size(max = 45)
    @Column(name = "email_responsable")
    private String emailResponsable;

    @Size(max = 255)
    @Column(name = "ciudad")
    private String ciudad;

    @Size(max = 45)
    @Column(name = "direccion")
    private String direccion;

    @Size(max = 20)
    @Column(name = "telefono")
    private String telefono;

    @Size(max = 255)
    @Column(name = "actividad_economica")
    private String actividadEconomica;

    @Column(name = "numero_trabajadores")
    private Integer numeroTrabajadores;

    @Size(max = 255)
    @Column(name = "nombre_evaluador")
    private String nombreEvaluador;

    @Size(max = 45)
    @Column(name = "licencia_evaluador")
    private String licenciaEvaluador;

    @NotNull
    @JoinColumn(name = "fk_empresa_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Empresa empresa;

    @JoinColumns({
        @JoinColumn(name = "fk_sistema_gestion_id", referencedColumnName = "id")
        , @JoinColumn(name = "fk_sistema_gestion_version", referencedColumnName = "version")})
    @ManyToOne(optional = false)
    private SistemaGestion sistemaGestion;

    @OneToMany(mappedBy = "evaluacion")
    private List<Respuesta> respuestaList;

    public Evaluacion() {
    }

    public Evaluacion(Integer id) {
        this.id = id;
    }

    //<editor-fold defaultstate="collapsed" desc="METHODS">
    public int getNumeroPreguntas() {
        return sistemaGestion.getNumeroPreguntas();
    }

    public int getNumeroRespuestas() {
        return this.respuestaList == null ? 0 : this.respuestaList.size();
    }

    public String getNombreSGE() {
        return sistemaGestion.getNombre();
    }
//</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="GETTERS-SETTERS">
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
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

    public Calendar getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(Calendar fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public Date getFechaFinalizacion() {
        return fechaFinalizacion;
    }

    public void setFechaFinalizacion(Date fechaFinalizacion) {
        this.fechaFinalizacion = fechaFinalizacion;
    }

    public Empresa getEmpresa() {
        return empresa;
    }

    public void setEmpresa(Empresa empresa) {
        this.empresa = empresa;
    }

    @JsonIgnore
    public SistemaGestion getSistemaGestion() {
        return sistemaGestion;
    }

    @JsonProperty("sistemaGestion")
    public void setSistemaGestion(SistemaGestion sistemaGestion) {
        this.sistemaGestion = sistemaGestion;
    }

    @XmlTransient
    public List<Respuesta> getRespuestaList() {
        return respuestaList;
    }

    public void setRespuestaList(List<Respuesta> respuestaList) {
        this.respuestaList = respuestaList;
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
        if (!(object instanceof Evaluacion)) {
            return false;
        }
        Evaluacion other = (Evaluacion) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "co.sigess.entities.sgeEvaluacion[ id=" + id + " ]";
    }

    public String getNombreResponsable() {
        return nombreResponsable;
    }

    public void setNombreResponsable(String nombreResponsable) {
        this.nombreResponsable = nombreResponsable;
    }

    public String getEmailResponsable() {
        return emailResponsable;
    }

    public void setEmailResponsable(String emailResponsable) {
        this.emailResponsable = emailResponsable;
    }

    public String getCiudad() {
        return ciudad;
    }

    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getActividadEconomica() {
        return actividadEconomica;
    }

    public void setActividadEconomica(String actividadEconomica) {
        this.actividadEconomica = actividadEconomica;
    }

    public Integer getNumeroTrabajadores() {
        return numeroTrabajadores;
    }

    public void setNumeroTrabajadores(Integer numeroTrabajadores) {
        this.numeroTrabajadores = numeroTrabajadores;
    }

    public String getNombreEvaluador() {
        return nombreEvaluador;
    }

    public void setNombreEvaluador(String nombreEvaluador) {
        this.nombreEvaluador = nombreEvaluador;
    }

    public String getLicenciaEvaluador() {
        return licenciaEvaluador;
    }

    public void setLicenciaEvaluador(String licenciaEvaluador) {
        this.licenciaEvaluador = licenciaEvaluador;
    }

//</editor-fold>
}
