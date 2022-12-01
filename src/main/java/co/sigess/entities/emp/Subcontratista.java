/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.sigess.entities.emp;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Julio
 */
@Entity
@Table(name = "subcontratista", schema = "emp")
@XmlRootElement
public class Subcontratista implements Serializable{
    
    @Id
    @Basic(optional = false)
    @SequenceGenerator(name = "subcontratista_id_seq", schema = "emp", sequenceName = "subcontratista_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "subcontratista_id_seq")
    private Integer id;
    
    @Column(name = "nit")
    private String nit;
    
    @Column(name = "nombre")
    private String nombre;
    
    @Column(name = "actividades_riesgo")
    private String actividades_riesgo;
    
    @Column(name = "porcentaje_arl")
    private String porcentaje_arl;
    
    @Column(name = "estado")
    private String estado;
    
    @Column(name = "carta_autorizacion")
    private String carta_autorizacion;
    
    @Column(name = "id_aliado_creador")
    private Integer id_aliado_creador;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNit() {
        return nit;
    }

    public void setNit(String nit) {
        this.nit = nit;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getActividades_riesgo() {
        return actividades_riesgo;
    }

    public void setActividades_riesgo(String actividades_riesgo) {
        this.actividades_riesgo = actividades_riesgo;
    }

    public String getPorcentaje_arl() {
        return porcentaje_arl;
    }

    public void setPorcentaje_arl(String porcentaje_arl) {
        this.porcentaje_arl = porcentaje_arl;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getCarta_autorizacion() {
        return carta_autorizacion;
    }

    public void setCarta_autorizacion(String carta_autorizacion) {
        this.carta_autorizacion = carta_autorizacion;
    }

    public Integer getId_aliado_creador() {
        return id_aliado_creador;
    }

    public void setId_aliado_creador(Integer id_aliado_creador) {
        this.id_aliado_creador = id_aliado_creador;
    }
}
