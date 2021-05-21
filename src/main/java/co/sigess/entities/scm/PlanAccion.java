/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.sigess.entities.scm;

import co.sigess.entities.emp.Empleado;
import com.fasterxml.jackson.annotation.JsonIgnore;
import java.io.Serializable;
import java.math.BigInteger;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author leonardo
 */
@Entity
@Table(name = "plan_accion", schema = "scm")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "PlanAccion.findAll", query = "SELECT p FROM PlanAccion p")
})
public class PlanAccion implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @SequenceGenerator(name = "plan_accion_id_seq", schema = "scm", sequenceName = "plan_accion_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "plan_accion_id_seq")
    @Basic(optional = false)
    @NotNull
    @Column(name = "id")
    private Long id;
    @Size(max = 2147483647)
    @Column(name = "actividad")
    private String actividad;
    @Size(max = 2147483647)
    @Column(name = "descripcion_act")
    private String descripcionAct;
    @Size(max = 2147483647)
    @Column(name = "fecha_proyectada")
    private String fechaProyectada;
    @Column(name = "responsable_externo")
    private BigInteger responsableExterno;
    
    @JoinColumn(name = "responsable_empresa", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Empleado responsableEmpresa;
    
    @JsonIgnore
     @JoinColumns({
        @JoinColumn(name = "fk_recomendaciones_id", referencedColumnName = "id",insertable=false, updatable=false),
     })
    @ManyToOne(optional = false,fetch = FetchType.EAGER)
    private Recomendaciones listaActionPlan;

    public PlanAccion() {
    }

    public PlanAccion(Long id) {
        this.id = id;
    }

    @JsonIgnore
    public Recomendaciones getListaPlanAccion() {
        return listaActionPlan;
    }

    public void setListaPlanAccion(Recomendaciones listaActionPlan) {
        this.listaActionPlan = listaActionPlan;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public BigInteger getResponsableExterno() {
        return responsableExterno;
    }

    public void setResponsableExterno(BigInteger responsableExterno) {
        this.responsableExterno = responsableExterno;
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
        if (!(object instanceof PlanAccion)) {
            return false;
        }
        PlanAccion other = (PlanAccion) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "co.sigess.entities.scm.PlanAccion[ id=" + id + " ]";
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

    
  

}
