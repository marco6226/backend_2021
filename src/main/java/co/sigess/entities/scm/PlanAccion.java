/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.sigess.entities.scm;

import java.io.Serializable;
import java.math.BigInteger;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author leonardo
 */
@Entity

@Table(name = "plan_accion")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "PlanAccion.findAll", query = "SELECT p FROM PlanAccion p")
})
public class PlanAccion implements Serializable {

    private static final long serialVersionUID = 1L;

 
    @Id
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
    @Column(name = "responsable_empresa")
    private BigInteger responsableEmpresa;
    @Column(name = "responsable_externo")
    private BigInteger responsableExterno;

    
    @OneToOne
    private Recomendaciones test;

    public PlanAccion() {
    }

    public PlanAccion(Long id) {
        this.id = id;
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

    public BigInteger getResponsableEmpresa() {
        return responsableEmpresa;
    }

    public void setResponsableEmpresa(BigInteger responsableEmpresa) {
        this.responsableEmpresa = responsableEmpresa;
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

 

}
