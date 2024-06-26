/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.sigess.entities.scm;

import co.sigess.entities.emp.Empleado;
import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
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
@Table(name = "seguimiento_caso",schema="scm")

@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "SeguimientoCaso.findAll", query = "SELECT s FROM SeguimientoCaso s WHERE s.eliminado = true" ),})
public class SeguimientoCaso implements Serializable {

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

    private static final long serialVersionUID = 1L;
    
    @Id
    @Basic(optional = false)
    @NotNull
    @SequenceGenerator(name = "seguimiento_id_seq", schema = "scm", sequenceName = "seguimiento_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seguimiento_id_seq")
    @Column(name = "id")
    private Long id;
    
    @Column(name = "fecha_seg")
    @Temporal(TemporalType.DATE)
    private Date fechaSeg;
    @Size(max = 2147483647)
    @Column(name = "seguimiento")
    private String seguimiento;
     @Size(max = 2147483647)
    @Column(name = "pk_case")
    private String pkCase;
    @Size(max = 2147483647)
    @Column(name = "tarea")
    private String tarea;
      @Size(max = 2147483647)
    @Column(name = "responsable_externo")
    private String responsableExterno;
    @JoinColumn(name = "responsable", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Empleado responsable;
  
    @Size(max = 2147483647)
    @Column(name = "resultado")
    private String resultado;

    @Column(name = "eliminado")
    private Boolean eliminado;
    
     @Column(name = "generico")
    private Boolean generico;
     
    @Column(name = "proximafecha_seg")
    @Temporal(TemporalType.DATE)
    private Date proxfechaSeg;

    public Date getProxfechaSeg() {
        return proxfechaSeg;
    }

    public void setProxfechaSeg(Date proxfechaSeg) {
        this.proxfechaSeg = proxfechaSeg;
    }
    
    public SeguimientoCaso() {
    }

    /**
     * @return the pkCase
     */
    public String getPkCase() {
        return pkCase;
    }

    /**
     * @param pkCase the pkCase to set
     */
    public void setPkCase(String pkCase) {
        this.pkCase = pkCase;
    }

    public SeguimientoCaso(Long id) {
        this.id = id;
    }

    public Date getFechaSeg() {
        return fechaSeg;
    }

    public void setFechaSeg(Date fechaSeg) {
        this.fechaSeg = fechaSeg;
    }

    public String getSeguimiento() {
        return seguimiento;
    }

    public void setSeguimiento(String seguimiento) {
        this.seguimiento = seguimiento;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    /**
     * @return the responsable
     */
    public Empleado getResponsable() {
        return responsable;
    }

    /**
     * @param responsable the responsable to set
     */
    public void setResponsable(Empleado responsable) {
        this.responsable = responsable;
    }

    public String getTarea() {
        return tarea;
    }

    /**
     * @return the eliminado
     */
    public Boolean getEliminado() {
        return eliminado;
    }

    /**
     * @param eliminado the eliminado to set
     */
    public void setEliminado(Boolean eliminado) {
        this.eliminado = eliminado;
    }

    public void setTarea(String tarea) {
        this.tarea = tarea;
    }

  

    public String getResultado() {
        return resultado;
    }

    public void setResultado(String resultado) {
        this.resultado = resultado;
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
        if (!(object instanceof SeguimientoCaso)) {
            return false;
        }
        SeguimientoCaso other = (SeguimientoCaso) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "co.sigess.entities.scm.SeguimientoCaso[ id=" + id + " ]";
    }

    /**
     * @return the generico
     */
    public Boolean getGenerico() {
        return generico;
    }

    /**
     * @param generico the generico to set
     */
    public void setGenerico(Boolean generico) {
        this.generico = generico;
    }
    
}
