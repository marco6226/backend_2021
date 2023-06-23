/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.sigess.entities.emp;

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
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Usuario
 */
@Entity
@Table(name = "firmas", schema = "emp")
@XmlRootElement
//@NamedQueries({
    //@NamedQuery(name = "firma.findAll", query = "SELECT f FROM firma f"),
    //@NamedQuery(name = "firma.findById", query = "SELECT f FROM firma f WHERE f.id = :id")})
public class firma implements Serializable  {
    private static final long serialVersionUID = 1L;
    @Id
    @SequenceGenerator(name = "firmas_id_seq", schema = "emp", sequenceName = "firmas_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "firmas_id_seq")
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    
    @Column(name = "fk_empresa_id")
    private Integer idempresa;
    
    @Column(name = "fecha_creacion")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechacreacion;
    
    @Column(name = "fk_relacionado_id")
    private Integer idrelacionado;
    
    @Column(name = "email")
    private String email;
    
    @Column(name = "fk_usuario_id")
    private Integer idusuario;
    
    @Column(name = "terminos_condiciones")
    private Boolean terminoscondiciones;
    
    @Column(name = "firma")
    private String firma;

    @Column(name = "firma_terminos_condiciones")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaterminos;
    
    @Column(name = "fecha_renovacion")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fecharenovacion;
    
    @Column(name = "nombre")
    private String nombre;

    public Date getFecharenovacion() {
        return fecharenovacion;
    }

    public void setFecharenovacion(Date fecharenovacion) {
        this.fecharenovacion = fecharenovacion;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Date getFechaterminos() {
        return fechaterminos;
    }

    public void setFechaterminos(Date fechaterminos) {
        this.fechaterminos = fechaterminos;
    }
        
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

    public Integer getIdusuario() {
        return idusuario;
    }

    public void setIdusuario(Integer idusuario) {
        this.idusuario = idusuario;
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
