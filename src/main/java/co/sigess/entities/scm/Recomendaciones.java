/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.sigess.entities.scm;

import co.sigess.entities.emp.Empleado;
import co.sigess.entities.emp.UsuarioEmpresa;
import co.sigess.entities.inp.ElementoInspeccion;
import co.sigess.entities.sec.TareaDesviacion;
import com.fasterxml.jackson.annotation.JsonIgnore;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.enterprise.inject.Default;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
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
import javax.persistence.OrderBy;
import javax.persistence.PrePersist;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author leonardo
 */
@Entity
@Table(name = "recomendaciones", schema = "scm")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Recomendaciones.findAll", query = "SELECT r FROM Recomendaciones r")})
public class Recomendaciones implements Serializable {

    @Id
    @SequenceGenerator(name = "recomendaciones_id_seq", schema = "scm", sequenceName = "recomendaciones_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "recomendaciones_id_seq")
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;

    private static final long serialVersionUID = 1L;
    @Column(name = "generate_recomendaciones")
    @Size(max = 2147483647)
    private String generateRecomendaciones;

    @Column(name = "actividad")
    @Size(max = 2147483647)
    private String actividad;
    @Column(name = "descripcion_act")
    @Size(max = 2147483647)
    private String descripcion_act;
    @Column(name = "fecha_proyectada")
    @Temporal(TemporalType.DATE)
    private Date fecha_proyectada;

    @Column(name = "action_plan_responsable")
    @Size(max = 2147483647)
    private String actionPlanResponsable;

    @Column(name = "entidad_emit_recomendaciones")
    @Size(max = 2147483647)
    private String entidadEmitRecomendaciones;
    @Column(name = "tipo")
    @Size(max = 2147483647)
    private String tipo;
    @Column(name = "fecha_inicio")
    @Temporal(TemporalType.DATE)
    private Date fechaInicio;
    @Column(name = "fecha_expiracion")
    @Temporal(TemporalType.DATE)
    private Date fechaExpiracion;
    @Column(name = "status")
    @Size(max = 2147483647)
    private String status;
    @Column(name = "responsable_externo")
    @Size(max = 2147483647)
    private String responsableExterno;

    @Column(name = "eliminado", columnDefinition = "boolean default false")
    private boolean eliminado;


    @Column(name = "recomendaciones")
    @Size(max = 2147483647)
    private String recomendaciones;

    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "pk_user")
    private Long pkUser;

    @JoinColumn(name = "responsable_empresa", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Empleado responsableEmpresa;

    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "pk_case")
    private Long pkCase;

    public Recomendaciones() {
    }

    public Recomendaciones(Long pkUser) {
        this.pkUser = pkUser;
    }

    public void setFechaInicio(Date fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public Long getPkUser() {
        return pkUser;
    }

    public void setPkUser(Long pkUser) {
        this.pkUser = pkUser;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (pkUser != null ? pkUser.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Recomendaciones)) {
            return false;
        }
        Recomendaciones other = (Recomendaciones) object;
        if ((this.pkUser == null && other.pkUser != null) || (this.pkUser != null && !this.pkUser.equals(other.pkUser))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "co.sigess.entities.scm.Recomendaciones[ pkUser=" + pkUser + " ]";
    }

    /**
     * @return the id
     */
    public Integer getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * @return the responsableExterno
     */
    public String getResponsableExterno() {
        return responsableExterno;
    }

    /**
     * @param responsableExterno the responsableExterno to set
     */
    public void setResponsableExterno(String responsableExterno) {
        this.responsableExterno = responsableExterno;
    }

    /**
     * @return the actividad
     */
    public String getActividad() {
        return actividad;
    }

    /**
     * @param actividad the actividad to set
     */
    public void setActividad(String actividad) {
        this.actividad = actividad;
    }

    /**
     * @return the descripcion_act
     */
    public String getDescripcion_act() {
        return descripcion_act;
    }

    /**
     * @param descripcion_act the descripcion_act to set
     */
    public void setDescripcion_act(String descripcion_act) {
        this.descripcion_act = descripcion_act;
    }

    /**
     * @return the fecha_proyectada
     */
    public Date getFecha_proyectada() {
        return fecha_proyectada;
    }

    /**
     * @param fecha_proyectada the fecha_proyectada to set
     */
    public void setFecha_proyectada(Date fecha_proyectada) {
        this.fecha_proyectada = fecha_proyectada;
    }

    @PrePersist
    public void onPrePersist() {
        audit("INSERT");
    }

    private void audit(String operation) {
        System.out.print(operation);

    }

    /**
     * @return the generateRecomendaciones
     */
    public String getGenerateRecomendaciones() {
        return generateRecomendaciones;
    }

    /**
     * @param generateRecomendaciones the generateRecomendaciones to set
     */
    public void setGenerateRecomendaciones(String generateRecomendaciones) {
        this.generateRecomendaciones = generateRecomendaciones;
    }

    /**
     * @return the pkCase
     */
    public Long getPkCase() {
        return pkCase;
    }

    /**
     * @return the responsableEmpresa
     */
    public Empleado getResponsableEmpresa() {
        return responsableEmpresa;
    }

    /**
     * @param responsableEmpresa the responsableEmpresa to set
     */
    public void setResponsableEmpresa(Empleado responsableEmpresa) {
        this.responsableEmpresa = responsableEmpresa;
    }

    /**
     * @param pkCase the pkCase to set
     */
    public void setPkCase(Long pkCase) {
        this.pkCase = pkCase;
    }

    /**
     * @return the entidadEmitRecomendaciones
     */
    public String getEntidadEmitRecomendaciones() {
        return entidadEmitRecomendaciones;
    }

    /**
     * @param entidadEmitRecomendaciones the entidadEmitRecomendaciones to set
     */
    public void setEntidadEmitRecomendaciones(String entidadEmitRecomendaciones) {
        this.entidadEmitRecomendaciones = entidadEmitRecomendaciones;
    }

    /**
     * @return the tipo
     */
    public String getTipo() {
        return tipo;
    }

    /**
     * @param tipo the tipo to set
     */
    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    /**
     * @return the fechaInicio
     */
    public Date getFechaInicio() {
        return fechaInicio;
    }

    /**
     * @return the fechaExpiracion
     */
    public Date getFechaExpiracion() {
        return fechaExpiracion;
    }

    /**
     * @param fechaExpiracion the fechaExpiracion to set
     */
    public void setFechaExpiracion(Date fechaExpiracion) {
        this.fechaExpiracion = fechaExpiracion;
    }

    /**
     * @return the status
     */
    public String getStatus() {
        return status;
    }

    /**
     * @param status the status to set
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * @return the recomendaciones
     */
    public String getRecomendaciones() {
        return recomendaciones;
    }

    /**
     * @param recomendaciones the recomendaciones to set
     */
    public void setRecomendaciones(String recomendaciones) {
        this.recomendaciones = recomendaciones;
    }

    /**
     * @return the actionPlanResponsable
     */
    public String getActionPlanResponsable() {
        return actionPlanResponsable;
    }

    /**
     * @param actionPlanResponsable the actionPlanResponsable to set
     */
    public void setActionPlanResponsable(String actionPlanResponsable) {
        this.actionPlanResponsable = actionPlanResponsable;
    }


  

}
