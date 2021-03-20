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
@Table(name = "diagnosticos",schema="scm")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Diagnosticos.findAll", query = "SELECT d FROM Diagnosticos d"),
    @NamedQuery(name = "Diagnosticos.findById", query = "SELECT d FROM Diagnosticos d WHERE d.id = :id"),
    @NamedQuery(name = "Diagnosticos.findByDiagnostico", query = "SELECT d FROM Diagnosticos d WHERE d.diagnostico = :diagnostico"),
    @NamedQuery(name = "Diagnosticos.findByCodigoCie10", query = "SELECT d FROM Diagnosticos d WHERE d.codigoCie10 = :codigoCie10"),
    @NamedQuery(name = "Diagnosticos.findBySistemaAfectado", query = "SELECT d FROM Diagnosticos d WHERE d.sistemaAfectado = :sistemaAfectado")})
public class Diagnosticos implements Serializable {

    private static final long serialVersionUID = 1L;
     @Id
    @Basic(optional = false)
    @NotNull
    @SequenceGenerator(name = "diagnosticos_id_seq", schema = "scm", sequenceName = "diagnosticos_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "diagnosticos_id_seq")
    @Column(name = "id")
    private Long id;
    @Size(max = 2147483647)
    @Column(name = "diagnostico")
    private String diagnostico;
    @Size(max = 2147483647)
    @Column(name = "pk_case")
    private String pkCase;
      @Column(name = "fecha_diagnostico")
    @Temporal(TemporalType.DATE)
    private Date fechaDiagnostico;
    
    @Size(max = 2147483647)
    @Column(name = "creado_por")
    private String creadoPor;
    @JoinColumn(name = "sistema_afectado", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private SistemaAfectado sistemaAfectado;
        
     @Size(max = 2147483647)
    @Column(name = "pk_user")
    private String pkUser;
    @Size(max = 2147483647)
    @Column(name = "codigo_cie10")
    private String codigoCie10;
   

    public Diagnosticos() {
    }

    public Diagnosticos(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    /**
     * @return the pkUser
     */
    public String getPkUser() {
        return pkUser;
    }

    /**
     * @param pkUser the pkUser to set
     */
    public void setPkUser(String pkUser) {
        this.pkUser = pkUser;
    }

    public String getDiagnostico() {
        return diagnostico;
    }

    public void setDiagnostico(String diagnostico) {
        this.diagnostico = diagnostico;
    }

    public String getCodigoCie10() {
        return codigoCie10;
    }

    public void setCodigoCie10(String codigoCie10) {
        this.codigoCie10 = codigoCie10;
    }

    /**
     * @return the pkCase
     */
    public String getPkCase() {
        return pkCase;
    }

    /**
     * @return the sistemaAfectado
     */
    public SistemaAfectado getSistemaAfectado() {
        return sistemaAfectado;
    }

    /**
     * @return the fechaDiagnostico
     */
    public Date getFechaDiagnostico() {
        return fechaDiagnostico;
    }

    /**
     * @return the creadoPor
     */
    public String getCreadoPor() {
        return creadoPor;
    }

    /**
     * @param creadoPor the creadoPor to set
     */
    public void setCreadoPor(String creadoPor) {
        this.creadoPor = creadoPor;
    }

    /**
     * @param fechaDiagnostico the fechaDiagnostico to set
     */
    public void setFechaDiagnostico(Date fechaDiagnostico) {
        this.fechaDiagnostico = fechaDiagnostico;
    }

    /**
     * @param sistemaAfectado the sistemaAfectado to set
     */
    public void setSistemaAfectado(SistemaAfectado sistemaAfectado) {
        this.sistemaAfectado = sistemaAfectado;
    }

    /**
     * @param pkCase the pkCase to set
     */
    public void setPkCase(String pkCase) {
        this.pkCase = pkCase;
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
        if (!(object instanceof Diagnosticos)) {
            return false;
        }
        Diagnosticos other = (Diagnosticos) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "co.sigess.entities.scm.Diagnosticos[ id=" + id + " ]";
    }
    
}
