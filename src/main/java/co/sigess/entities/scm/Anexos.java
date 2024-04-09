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
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author Usuario
 */
@Entity
@Table(name = "anexos", schema = "scm")
public class Anexos implements Serializable{
    
    private static final long serialVersionUID = 1L;
    @Id
    @SequenceGenerator(name = "anexos_id_seq", schema = "scm", sequenceName = "anexos_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "anexos_id_seq")
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    
    @Column(name = "pk_case")
    private Integer pk_case;
        
    @Column(name = "eliminado")
    private Boolean eliminado;
            
    @Column(name = "informacion")
    private String informacion;
    
    @Column(name = "fecha_creacion")
    @Temporal(TemporalType.DATE)
    private Date fecha_creacion;
    
    @Column(name = "tipo")
    private Integer tipo;
    
    @Column(name = "idempresa")
    private Integer idempresa;
    
    @Column(name = "firmas")
    private String firmas;

    public String getFirmas() {
        return firmas;
    }

    public void setFirmas(String firmas) {
        this.firmas = firmas;
    }

    public Integer getIdempresa() {
        return idempresa;
    }

    public void setIdempresa(Integer idempresa) {
        this.idempresa = idempresa;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getPk_case() {
        return pk_case;
    }

    public void setPk_case(Integer pk_case) {
        this.pk_case = pk_case;
    }

    public Boolean getEliminado() {
        return eliminado;
    }

    public void setEliminado(Boolean eliminado) {
        this.eliminado = eliminado;
    }

    public String getInformacion() {
        return informacion;
    }

    public void setInformacion(String informacion) {
        this.informacion = informacion;
    }

    public Date getFecha_creacion() {
        return fecha_creacion;
    }

    public void setFecha_creacion(Date fecha_creacion) {
        this.fecha_creacion = fecha_creacion;
    }

    public Integer getTipo() {
        return tipo;
    }

    public void setTipo(Integer tipo) {
        this.tipo = tipo;
    }
    
}
