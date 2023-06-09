/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.sigess.entities.emp;

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
@Table(name = "firmas", schema = "emp")
public class firma {
    private static final long serialVersionUID = 1L;
    @Id
    @SequenceGenerator(name = "firmas_id_seq", schema = "emp", sequenceName = "firmas_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "anexos_id_seq")
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    
    @Column(name = "fk_empresa_id")
    private Integer idempresa;
    
    @Column(name = "fecha_creacion")
    @Temporal(TemporalType.DATE)
    private Date fechacreacion;
    
    @Column(name = "fk_relacionado_id")
    private Integer idrelacionado;
    
    @Column(name = "email")
    private String email;
    
    @Column(name = "fk_usuaio_id")
    private Integer idusuarion;
    
    @Column(name = "terminos_condiciones")
    private Boolean terminoscondiciones;
    
    @Column(name = "firma")
    private String firma;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getIdempresa() {
        return idempresa;
    }

    public void setIdempresa(Integer idempresa) {
        this.idempresa = idempresa;
    }

    public Date getFechacreacion() {
        return fechacreacion;
    }

    public void setFechacreacion(Date fechacreacion) {
        this.fechacreacion = fechacreacion;
    }

    public Integer getIdrelacionado() {
        return idrelacionado;
    }

    public void setIdrelacionado(Integer idrelacionado) {
        this.idrelacionado = idrelacionado;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getIdusuarion() {
        return idusuarion;
    }

    public void setIdusuarion(Integer idusuarion) {
        this.idusuarion = idusuarion;
    }

    public Boolean getTerminoscondiciones() {
        return terminoscondiciones;
    }

    public void setTerminoscondiciones(Boolean terminoscondiciones) {
        this.terminoscondiciones = terminoscondiciones;
    }

    public String getFirma() {
        return firma;
    }

    public void setFirma(String firma) {
        this.firma = firma;
    }
}
