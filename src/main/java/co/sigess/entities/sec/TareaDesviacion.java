/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.sigess.entities.sec;

import co.sigess.entities.emp.Area;
import co.sigess.entities.emp.Empleado;
import co.sigess.entities.emp.Empresa;
import co.sigess.entities.emp.Usuario;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
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
@Table(name = "tarea_desviacion", schema = "sec")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TareaDesviacion.findAll", query = "SELECT t FROM TareaDesviacion t")})
public class TareaDesviacion implements Serializable {

    

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id") 
    private Integer id;

    @Size(max = 256)
    @Column(name = "nombre")
    private String nombre;

    @Size(max = 1024)
    @Column(name = "descripcion")
    private String descripcion;

    @Size(max = 1024)
    @Column(name = "descripcion_cierre")
    private String descripcionCierre;

    @Column(name = "enviocorreo")
    private Boolean envioCorreo;
    
    @Size(max = 20)
    @Column(name = "modulo")
    private String modulo;

    @Basic(optional = false)
    @Column(name = "estado")
    @Enumerated(EnumType.STRING)
    private EstadoTarea estado;

    @Size(max = 20)
    @Column(name = "tipo_accion")
    private String tipoAccion;

     @Size(max = 255)
    @Column(name = "jerarquia")
    private String jerarquia;
     
     @Size(max = 255)
    @Column(name = "codigo")
    private String codigo;
     
    @Column(name = "realizada")
    private Boolean realizada;

    @Column(name = "verificada")
    private Boolean verificada;

    @Column(name = "fecha_proyectada")
    @Temporal(TemporalType.DATE)
    private Date fechaProyectada;

    @Column(name = "fecha_verificacion")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaVerificacion;

    @Column(name = "fecha_cierre")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaCierre;
    
    @Column(name = "fecha_realizacion")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaRealizacion;

    @Size(max = 512)
    @Column(name = "observaciones_realizacion")
    private String observacionesRealizacion;

    @Size(max = 512)
    @Column(name = "observaciones_verificacion")
    private String observacionesVerificacion;

    @JoinColumn(name = "fk_usuario_realiza_id", referencedColumnName = "id")
    @ManyToOne
    private Usuario usuarioRealiza;

    
    @JoinColumn(name = "fk_usuario_cierre", referencedColumnName = "id")
    @ManyToOne
    private Empleado usuarioCierre;
    
    
    @OneToMany(cascade = CascadeType.ALL,fetch = FetchType.EAGER,orphanRemoval = true)
    @JoinColumn(name="fk_tarea_cierre")
    private List<EvidencesFiles> evidences  = new ArrayList<EvidencesFiles>();
    
    @OneToMany(cascade = CascadeType.ALL,fetch = FetchType.EAGER,orphanRemoval = true)
    @JoinColumn(name="fk_verify")
    private List<EvidencesFiles> evidencesV  = new ArrayList<EvidencesFiles>();
    
    @JoinColumn(name = "fk_usuario_verifica_id", referencedColumnName = "id")
    @ManyToOne
    private Usuario usuarioVerifica;

    @JoinColumn(name = "fk_area_responsable_id", referencedColumnName = "id")
    @ManyToOne
    private Area areaResponsable;

     @JsonIgnoreProperties({"jefeInmediato","businessPartner","tipoIdentificacion"})
    @JoinColumn(name = "fk_emp_responsable_id", referencedColumnName = "id")
    @ManyToOne(cascade = CascadeType.REFRESH )
    private Empleado empResponsable;
    
    @NotNull
    @JoinTable(name = "analisis_desviacion_tarea_desviacion", schema = "sec", joinColumns = {
        @JoinColumn(name = "pk_tarea_desviacion_id", referencedColumnName = "id")}, inverseJoinColumns = {
        @JoinColumn(name = "pk_analisis_desviacion_id", referencedColumnName = "id")})
    @ManyToMany
    private List<AnalisisDesviacion> analisisDesviacionList;

    @JoinColumn(name = "fk_empresa_id", referencedColumnName = "id")
    @ManyToOne
    private Empresa empresa;
    
    @Column(name = "responsable_aliado")
    private String responsableAliado;

    public TareaDesviacion() {
    }

    public TareaDesviacion(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Boolean getEnvioCorreo() {
        return envioCorreo;
    }

    public void setEnvioCorreo(Boolean envioCorreo) {
        this.envioCorreo = envioCorreo;
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
     public String getjerarquia() {
        return jerarquia;
    }

    public void setjerarquia(String jerarquia) {
        this.jerarquia = jerarquia;
    }

    public String getModulo() {
        return modulo;
    }

    public void setModulo(String modulo) {
        this.modulo = modulo;
    }

    public EstadoTarea getEstado() {
        return estado;
    }

    public void setEstado(EstadoTarea estado) {
        this.estado = estado;
    }

    public String getTipoAccion() {
        return tipoAccion;
    }

    public void setTipoAccion(String tipoAccion) {
        this.tipoAccion = tipoAccion;
    }

    public Boolean getRealizada() {
        return realizada;
    }

    public void setRealizada(Boolean realizada) {
        this.realizada = realizada;
    }

    public Boolean getVerificada() {
        return verificada;
    }

    public void setVerificada(Boolean verificada) {
        this.verificada = verificada;
    }

    public Date getFechaProyectada() {
        return fechaProyectada;
    }

    public void setFechaProyectada(Date fechaProyectada) {
        this.fechaProyectada = fechaProyectada;
    }

    public Date getFechaVerificacion() {
        return fechaVerificacion;
    }

    public void setFechaVerificacion(Date fechaVerificacion) {
        this.fechaVerificacion = fechaVerificacion;
    }

    public Date getFechaRealizacion() {
        return fechaRealizacion;
    }

    public void setFechaRealizacion(Date fechaRealizacion) {
        this.fechaRealizacion = fechaRealizacion;
    }

    public String getObservacionesRealizacion() {
        return observacionesRealizacion;
    }

    public void setObservacionesRealizacion(String observacionesRealizacion) {
        this.observacionesRealizacion = observacionesRealizacion;
    }

    public String getObservacionesVerificacion() {
        return observacionesVerificacion;
    }

    public void setObservacionesVerificacion(String observacionesVerificacion) {
        this.observacionesVerificacion = observacionesVerificacion;
    }

    public Usuario getUsuarioRealiza() {
        return usuarioRealiza;
    }

    public void setUsuarioRealiza(Usuario usuarioRealiza) {
        this.usuarioRealiza = usuarioRealiza;
    }

    public Usuario getUsuarioVerifica() {
        return usuarioVerifica;
    }

    public void setUsuarioVerifica(Usuario usuarioVerifica) {
        this.usuarioVerifica = usuarioVerifica;
    }
    
    /**
     * @return the codigo
     */
    public String getCodigo() {
        return codigo;
    }

    /**
     * @param codigo the codigo to set
     */
    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public Area getAreaResponsable() {
        return areaResponsable;
    }

    public void setAreaResponsable(Area areaResponsable) {
        this.areaResponsable = areaResponsable;
    }

    @XmlTransient
    @JsonIgnore
    public List<AnalisisDesviacion> getAnalisisDesviacionList() {
        return analisisDesviacionList;
    }

    @JsonProperty("analisisDesviacionList")
    public void setAnalisisDesviacionList(List<AnalisisDesviacion> analisisDesviacionList) {
        this.analisisDesviacionList = analisisDesviacionList;
    }

    @XmlTransient
    @JsonIgnore
    public Empresa getEmpresa() {
        return empresa;
    }

    public void setEmpresa(Empresa empresa) {
        this.empresa = empresa;
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
        if (!(object instanceof TareaDesviacion)) {
            return false;
        }
        TareaDesviacion other = (TareaDesviacion) object;
        return this.id != null && other.id != null && Objects.equals(this.id, other.id);
    }

    @Override
    public String toString() {
        return "co.sigess.entities.sec.TareaDesviacion[ id=" + id + " ]";
    }

    /**
     * @return the empResponsable
     */
    public Empleado getEmpResponsable() {
        return empResponsable;
    }

    /**
     * @param empResponsable the empResponsable to set
     */
    public void setEmpResponsable(Empleado empResponsable) {
        this.empResponsable = empResponsable;
    }

    /**
     * @return the fechaCierre
     */
    public Date getFechaCierre() {
        return fechaCierre;
    }

    /**
     * @param fechaCierre the fechaCierre to set
     */
    public void setFechaCierre(Date fechaCierre) {
        this.fechaCierre = fechaCierre;
    }

    /**
     * @return the descripcionCierre
     */
    public String getDescripcionCierre() {
        return descripcionCierre;
    }

    /**
     * @param descripcionCierre the descripcionCierre to set
     */
    public void setDescripcionCierre(String descripcionCierre) {
        this.descripcionCierre = descripcionCierre;
    }

    /**
     * @return the usuarioCierre
     */
    public Empleado getUsuarioCierre() {
        return usuarioCierre;
    }

    /**
     * @param usuarioCierre the usuarioCierre to set
     */
    public void setUsuarioCierre(Empleado usuarioCierre) {
        this.usuarioCierre = usuarioCierre;
    }

    /**
     * @return the evidences
     */
    public List<EvidencesFiles> getEvidences() {
        return evidences;
    }

    /**
     * @param evidences the evidences to set
     */
    public void setEvidences(List<EvidencesFiles> evidences) {
        this.evidences = evidences;
    }

    /**
     * @return the evidencesV
     */
    public List<EvidencesFiles> getEvidencesV() {
        return evidencesV;
    }

    /**
     * @param evidencesV the evidencesV to set
     */
    public void setEvidencesV(List<EvidencesFiles> evidencesV) {
        this.evidencesV = evidencesV;
    }

    public String getResponsableAliado() {
        return responsableAliado;
    }

    public void setResponsableAliado(String responsableAliado) {
        this.responsableAliado = responsableAliado;
    }
}
