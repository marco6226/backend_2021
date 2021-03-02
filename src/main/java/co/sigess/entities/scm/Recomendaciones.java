/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.sigess.entities.scm;

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
    
    
    @Column(name = "actionplan")
    @Size(max = 2147483647)
    private String actionPlan;
    
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
    @Column(name = "recomendaciones")
    @Size(max = 2147483647)
    private String recomendaciones;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "pk_user")
    private Long pkUser;

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
     * @return the actionPlan
     */
    public String getActionPlan() {
        return actionPlan;
    }

    /**
     * @param actionPlan the actionPlan to set
     */
    public void setActionPlan(String actionPlan) {
        this.actionPlan = actionPlan;
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
