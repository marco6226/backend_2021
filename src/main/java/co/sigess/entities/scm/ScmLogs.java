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
@Table(name = "scm_logs", schema = "scm")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ScmLogs.findAll", query = "SELECT s FROM ScmLogs s")})
public class ScmLogs implements Serializable {

    private static final long serialVersionUID = 1L;
    @Size(max = 2147483647)
    @Column(name = "action")
    private String action;
    @Size(max = 2147483647)
    @Column(name = "pk_user")
    private String pkUser;
    @Column(name = "salud_laboral")
    private boolean saludLaboral;
    @Size(max = 2147483647)
    @Column(name = "pk_case")
    private String pkCase;
    @Column(name = "fecha_creacion")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fecha_creacion;
    @Size(max = 2147483647)
    @Column(name = "json")
    private String json;
    @Size(max = 2147483647)
    @Column(name = "entity")
    private String entity;
    
    @Id
    @SequenceGenerator(name = "scm_logs_id_seq", schema = "scm", sequenceName = "scm_logs_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "scm_logs_id_seq")
    @Basic(optional = false)
    @Column(name = "id")
    

    private long id;

    public boolean isSaludLaboral() {
        return saludLaboral;
    }

    public void setSaludLaboral(boolean saludLaboral) {
        this.saludLaboral = saludLaboral;
    }
    

    public ScmLogs() {
    }

    public ScmLogs(Long id) {
        this.id = id;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public Date getFecha_creacion() {
        return fecha_creacion;
    }

    public void setFecha_creacion(Date fecha_creacion) {
        this.fecha_creacion = fecha_creacion;
    }

    public String getJson() {
        return json;
    }

    public void setJson(String json) {
        this.json = json;
    }

    public String getEntity() {
        return entity;
    }

    public void setEntity(String entity) {
        this.entity = entity;
    }

    public Long getId() {
        return id;
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

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "co.sigess.entities.scm.ScmLogs[ id=" + id + " ]";
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

}
