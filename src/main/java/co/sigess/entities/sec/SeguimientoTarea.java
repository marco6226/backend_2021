/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.sigess.entities.sec;

import co.sigess.entities.emp.Empleado;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
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

/**
 *
 * @author Leo
 */
@Entity
@Table(name = "seguimiento_tarea",schema = "sec")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "SeguimientoTarea.findAll", query = "SELECT s FROM SeguimientoTarea s"),
    @NamedQuery(name = "SeguimientoTarea.findByFollowDate", query = "SELECT s FROM SeguimientoTarea s WHERE s.followDate = :followDate")})
public class SeguimientoTarea implements Serializable {


    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @SequenceGenerator(name = "seg_tarea_seq", schema = "sec", sequenceName = "seg_tarea_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seg_tarea_seq")
    @NotNull
    @Column(name = "id")
    private Integer id;

    @Column(name = "fk_tarea_id")
    private Integer tareaId;
    @Size(max = 2147483647)
    @Column(name = "description")
    private String description;
    @Column(name = "follow_date")
    @Temporal(TemporalType.DATE)
    private Date followDate;

    
    @OneToMany(cascade = CascadeType.ALL,fetch = FetchType.EAGER,orphanRemoval = true)
    @JoinColumn(name="fk_seg_id")
    private List<EvidencesFiles> evidences  = new ArrayList<EvidencesFiles>();
    
    @JsonIgnoreProperties({"jefeInmediato","businessPartner","cargo","area"})
    @JoinColumn(name = "fk_user_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Empleado pkUser;
    
    public SeguimientoTarea() {
    }

    public SeguimientoTarea(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

  

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getFollowDate() {
        return followDate;
    }

    public void setFollowDate(Date followDate) {
        this.followDate = followDate;
    }

    public Integer getTareaId() {
        return tareaId;
    }

    public void setTareaId(Integer tareaId) {
        this.tareaId = tareaId;
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
        if (!(object instanceof SeguimientoTarea)) {
            return false;
        }
        SeguimientoTarea other = (SeguimientoTarea) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "co.sigess.entities.sec.SeguimientoTarea[ id=" + id + " ]";
    }

    /**
     * @return the pkUser
     */
    public Empleado getPkUser() {
        return pkUser;
    }

    /**
     * @param pkUser the pkUser to set
     */
    public void setPkUser(Empleado pkUser) {
        this.pkUser = pkUser;
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
    
}
