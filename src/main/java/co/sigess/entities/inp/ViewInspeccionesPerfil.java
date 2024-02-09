/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.sigess.entities.inp;

import co.sigess.entities.conf.RespuestaCampo;
import co.sigess.entities.emp.Area;
import co.sigess.entities.emp.Empleado;
import co.sigess.entities.emp.Empresa;
import co.sigess.entities.emp.Usuario;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Usuario
 */
@Entity
@JsonIgnoreProperties(ignoreUnknown = true)
@Table(name = "vw_inspeccion_to_perfil", schema = "inp")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ViewInspeccionesPerfil.findAll", query = "SELECT i FROM ViewInspeccionesPerfil i")})
public class ViewInspeccionesPerfil implements Serializable{
    
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "id")
    public Long id;
    
    private String observacion;
    private String lugar;
    private String equipo;
    private String marca;
    private String modelo;
    private String serial;
    private String descripcion;
    
    @JoinColumn(name = "fk_programacion_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Programacion programacion;
    
    @JoinColumn(name = "fk_empresa_id", referencedColumnName = "id")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Empresa empresa;
    
    @JoinColumn(name = "fk_usuario_registra_id", referencedColumnName = "id")
    @ManyToOne
    private Usuario usuarioRegistra;
    
    @JoinColumn(name = "fk_usuario_modifica_id", referencedColumnName = "id")
    @ManyToOne
    private Usuario usuarioModifica;
    
    @Column(name = "fecha_modificacion")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaModificacion;
    
    @JoinColumns({
        @JoinColumn(name = "fk_lista_inspeccion_id", referencedColumnName = "id")
        , @JoinColumn(name = "fk_lista_inspeccion_version", referencedColumnName = "version")})
    @ManyToOne(optional = false)
    private ListaInspeccion listaInspeccion;
    
    @JoinColumn(name = "fk_area_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Area area;
    
    @JsonIgnoreProperties({"tipoIdentificacion"})
    @JoinColumn(name = "fk_empleado_ing", referencedColumnName = "id")
    @ManyToOne
    private Empleado empleadoing;
    
    @JsonIgnoreProperties({"tipoIdentificacion"})
    @JoinColumn(name = "fk_empleado_hse", referencedColumnName = "id")
    @ManyToOne
    private Empleado empleadohse;
    
    @Column(name = "concepto_ing")
    private String conceptoing;

    @Column(name = "concepto_hse")
    private String conceptohse;
    
    @Column(name = "pk_usuario_id")
    private Integer pkUsuarioId;

    
    @Column(name = "fecha_realizada")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaRealizada;
//    
//    @Column(name = "fecha_visto_ing")
//    private Date fechavistoing;
//
//    @Column(name = "fecha_visto_hse")
//    private Date fechavistohse;
//
//    @OneToMany(mappedBy = "inspeccion")
//    private List<Calificacion> calificacionList;
//
//    @JoinTable(name = "inspeccion_respuesta_campo", schema = "inp", joinColumns = {
//        @JoinColumn(name = "fk_inspeccion_id", referencedColumnName = "id")}, inverseJoinColumns = {
//        @JoinColumn(name = "fk_respuesta_campo_id", referencedColumnName = "id")})
//    @ManyToMany
//    private List<RespuestaCampo> respuestasCampoList;


    public Integer getPkUsuarioId() {
        return pkUsuarioId;
    }

    public void setPkUsuarioId(Integer pkUsuarioId) {
        this.pkUsuarioId = pkUsuarioId;
    }

    public ViewInspeccionesPerfil() {
    }

    public ViewInspeccionesPerfil(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getFechaRealizada() {
        return fechaRealizada;
    }

    public void setFechaRealizada(Date fechaRealizada) {
        this.fechaRealizada = fechaRealizada;
    }

    public String getObservacion() {
        return observacion;
    }

    public void setObservacion(String observacion) {
        this.observacion = observacion;
    }

    public String getLugar() {
        return lugar;
    }

    public void setLugar(String lugar) {
        this.lugar = lugar;
    }

    public String getEquipo() {
        return equipo;
    }

    public void setEquipo(String equipo) {
        this.equipo = equipo;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public String getSerial() {
        return serial;
    }

    public void setSerial(String serial) {
        this.serial = serial;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

//    public List<Calificacion> getCalificacionList() {
//        return calificacionList;
//    }
//
//    public void setCalificacionList(List<Calificacion> calificacionList) {
//        this.calificacionList = calificacionList;
//    }

    public Area getArea() {
        return area;
    }

    public void setArea(Area area) {
        this.area = area;
    }

    @JsonIgnore
    public Empresa getEmpresa() {
        return empresa;
    }

    @JsonProperty(value = "empresa")
    public void setEmpresa(Empresa empresa) {
        this.empresa = empresa;
    }

    public Programacion getProgramacion() {
        return programacion;
    }

    public void setProgramacion(Programacion programacion) {
        this.programacion = programacion;
    }

//    public List<RespuestaCampo> getRespuestasCampoList() {
//        return respuestasCampoList;
//    }
//
//    public void setRespuestasCampoList(List<RespuestaCampo> respuestasCampoList) {
//        this.respuestasCampoList = respuestasCampoList;
//    }

    public Date getFechaModificacion() {
        return fechaModificacion;
    }

    public void setFechaModificacion(Date fechaModificacion) {
        this.fechaModificacion = fechaModificacion;
    }

    public Usuario getUsuarioRegistra() {
        return usuarioRegistra;
    }

    public void setUsuarioRegistra(Usuario usuarioRegistra) {
        this.usuarioRegistra = usuarioRegistra;
    }

    public Usuario getUsuarioModifica() {
        return usuarioModifica;
    }

    public void setUsuarioModifica(Usuario usuarioModifica) {
        this.usuarioModifica = usuarioModifica;
    }

    public ListaInspeccion getListaInspeccion() {
        return listaInspeccion;
    }

    public void setListaInspeccion(ListaInspeccion listaInspeccion) {
        this.listaInspeccion = listaInspeccion;
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
        if (!(object instanceof ViewInspeccionesPerfil)) {
            return false;
        }
        ViewInspeccionesPerfil other = (ViewInspeccionesPerfil) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "co.sigess.entities.inp.ViewInspeccionesPerfil[ id=" + id + " ]";
    }

    /**
     * @return the empleadohse
     */
    
    public Empleado getEmpleadohse() {
        return empleadohse;
    }

    /**
     * @return the empleadoing
     */
    
    public Empleado getEmpleadoing() {
        return empleadoing;
    }

//    /**
//     * @return the fechavistohse
//     */
//    public Date getFechavistohse() {
//        return fechavistohse;
//    }
//
//    /**
//     * @return the fechavistoing
//     */
//    public Date getFechavistoing() {
//        return fechavistoing;
//    }

    /**
     * @return the fechavistoing
     */
    public String getConceptohse() {
        return conceptohse;
    }

    /**
     * @return the fechavistoing
     */
    public String getConceptoing() {
        return conceptoing;
    }

    /**
     * @param empleadohse the empleadohse to set
     */    
    public void setEmpleadohse(Empleado empleadohse) {
        this.empleadohse = empleadohse;
    }

    /**
     * @param empleadoing the empleadoing to set
     */
    
    public void setEmpleadoing(Empleado empleadoing) {
        this.empleadoing = empleadoing;
    }

//    /**
//     * @param fechavistohse the fechavistohse to set
//     */
//    public void setFechavistohse(Date fechavistohse) {
//        this.fechavistohse = fechavistohse;
//    }
//
//    /**
//     * @param fechavistoing the fechavistoing to set
//     */
//    public void setFechavistoing(Date fechavistoing) {
//        this.fechavistoing = fechavistoing;
//    }

    /**
     * @param conceptohse the conceptohse to set
     */
    public void setConceptohse(String conceptohse) {
        this.conceptohse = conceptohse;
    }

    /**
     * @param conceptoing the conceptoing to set
     */
    public void setConceptoing(String conceptoing) {
        this.conceptoing = conceptoing;
    }
}
