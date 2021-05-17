/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.sigess.entities.scm;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
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

/**
 *
 * @author leonardo
 */
@Entity
@Table(name = "recomendaciones", schema = "scm")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Recomendaciones.findAll", query = "SELECT r FROM Recomendaciones r"),
})
public class Recomendaciones implements Serializable {

    private static final long serialVersionUID = 1L;
 
    @Id
    @SequenceGenerator(name = "recomendaciones_id_seq", schema = "scm", sequenceName = "recomendaciones_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "recomendaciones_id_seq")
    @Basic(optional = false)
    @NotNull
    @Column(name = "id")
    private Integer id;
    @Size(max = 2147483647)
    @Column(name = "generate_recomendaciones")
    private String generateRecomendaciones;
    @Size(max = 2147483647)
    @Column(name = "entidad_emit_recomendaciones")
    private String entidadEmitRecomendaciones;
    @Size(max = 2147483647)
    @Column(name = "tipo")
    private String tipo;
    @Column(name = "fecha_inicio")
    @Temporal(TemporalType.DATE)
    private Date fechaInicio;
    @Column(name = "fecha_expiracion")
    @Temporal(TemporalType.DATE)
    private Date fechaExpiracion;
    @Size(max = 2147483647)
    @Column(name = "status")
    private String status;
    @Size(max = 2147483647)
    @Column(name = "recomendaciones")
    private String recomendaciones;
    @Basic(optional = false)
    @NotNull
    @Column(name = "pk_user")
    private long pkUser;
  
    @Size(max = 2147483647)
    @Column(name = "actionplan")
    private String actionplan;
    @Size(max = 2147483647)
    @Column(name = "action_plan_responsable")
    private String actionPlanResponsable;
    @Column(name = "pk_case")
    private BigInteger pkCase;
    @Column(name = "responsable_empresa")
    private BigInteger responsableEmpresa;
    @Size(max = 2147483647)
    @Column(name = "responsable_externo")
    private String responsableExterno;
    @Column(name = "eliminado",columnDefinition = "boolean default false")
    private Boolean eliminado;
    @Size(max = 2147483647)
    @Column(name = "codigo")
    private String codigo;
    @Size(max = 2147483647)
    @Column(name = "actividad")
    private String actividad;
    @Size(max = 2147483647)
    @Column(name = "descripcion_act")
    private String descripcionAct;
    @Size(max = 2147483647)
    @Column(name = "fecha_proyectada")
    private String fechaProyectada;

    
    @OneToMany(cascade = CascadeType.ALL,fetch = FetchType.EAGER,orphanRemoval = true)
    @JoinColumn(name="fk_recomendaciones_id")
    private List<PlanAccion> actionPlanList  = new ArrayList<PlanAccion>();
    
    public Recomendaciones() {
    }

    public Recomendaciones(Integer id) {
        this.id = id;
    }

    public Recomendaciones(Integer id, long pkUser) {
        this.id = id;
        this.pkUser = pkUser;
    }

    public String getGenerateRecomendaciones() {
        return generateRecomendaciones;
    }

    public void setGenerateRecomendaciones(String generateRecomendaciones) {
        this.generateRecomendaciones = generateRecomendaciones;
    }

    public String getEntidadEmitRecomendaciones() {
        return entidadEmitRecomendaciones;
    }

    public void setEntidadEmitRecomendaciones(String entidadEmitRecomendaciones) {
        this.entidadEmitRecomendaciones = entidadEmitRecomendaciones;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public Date getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(Date fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public Date getFechaExpiracion() {
        return fechaExpiracion;
    }

    public void setFechaExpiracion(Date fechaExpiracion) {
        this.fechaExpiracion = fechaExpiracion;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getRecomendaciones() {
        return recomendaciones;
    }

    public void setRecomendaciones(String recomendaciones) {
        this.recomendaciones = recomendaciones;
    }

    public long getPkUser() {
        return pkUser;
    }

    public void setPkUser(long pkUser) {
        this.pkUser = pkUser;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getActionplan() {
        return actionplan;
    }

    public void setActionplan(String actionplan) {
        this.actionplan = actionplan;
    }

    public String getActionPlanResponsable() {
        return actionPlanResponsable;
    }

    public void setActionPlanResponsable(String actionPlanResponsable) {
        this.actionPlanResponsable = actionPlanResponsable;
    }

    public BigInteger getPkCase() {
        return pkCase;
    }

    public void setPkCase(BigInteger pkCase) {
        this.pkCase = pkCase;
    }

    public BigInteger getResponsableEmpresa() {
        return responsableEmpresa;
    }

    public void setResponsableEmpresa(BigInteger responsableEmpresa) {
        this.responsableEmpresa = responsableEmpresa;
    }

    public String getResponsableExterno() {
        return responsableExterno;
    }

    public void setResponsableExterno(String responsableExterno) {
        this.responsableExterno = responsableExterno;
    }

    public Boolean getEliminado() {
        return eliminado;
    }

    public void setEliminado(Boolean eliminado) {
        this.eliminado = eliminado;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getActividad() {
        return actividad;
    }

    public void setActividad(String actividad) {
        this.actividad = actividad;
    }

    public String getDescripcionAct() {
        return descripcionAct;
    }

    public void setDescripcionAct(String descripcionAct) {
        this.descripcionAct = descripcionAct;
    }

    public String getFechaProyectada() {
        return fechaProyectada;
    }

    public void setFechaProyectada(String fechaProyectada) {
        this.fechaProyectada = fechaProyectada;
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
        if (!(object instanceof Recomendaciones)) {
            return false;
        }
        Recomendaciones other = (Recomendaciones) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "co.sigess.entities.scm.Recomendaciones[ id=" + id + " ]";
    }

    /**
     * @return the actionPlanList
     */
    public List<PlanAccion> getActionPlanList() {
        return actionPlanList;
    }

    /**
     * @param actionPlanList the actionPlanList to set
     */
    public void setActionPlanList(List<PlanAccion> actionPlanList) {
        this.actionPlanList = actionPlanList;
    }
    
}
