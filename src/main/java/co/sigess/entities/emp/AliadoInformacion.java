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
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author John Rueda
 */
@Entity
@Table(name = "aliado_informacion", schema = "emp")
@XmlRootElement
public class AliadoInformacion implements Serializable{
    
    @Id
    @Basic(optional = false)
    @NotNull
    @SequenceGenerator(name = "aliado_informacion_id_seq", schema = "emp", sequenceName = "aliado_informacion_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "aliado_informacion_id_seq")
    @Column(name = "id")
    private Integer id;
    
    @Column(name = "id_empresa")
    private Integer id_empresa;
    
    @Column(name = "actividad_contratada")
    private String actividad_contratada;
    
    @Column(name = "division")
    private String division;
    
    @Column(name = "localidad")
    private String localidad;
    
    @Column(name = "calificacion")
    private String calificacion;
    
    @Column(name = "colider")
    private String colider;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getId_empresa() {
        return id_empresa;
    }

    public void setId_empresa(Integer id_empresa) {
        this.id_empresa = id_empresa;
    }

    public String getActividad_contratada() {
        return actividad_contratada;
    }

    public void setActividad_contratada(String actividad_contratada) {
        this.actividad_contratada = actividad_contratada;
    }

    public String getDivision() {
        return division;
    }

    public void setDivision(String division) {
        this.division = division;
    }

    public String getLocalidad() {
        return localidad;
    }

    public void setLocalidad(String localidad) {
        this.localidad = localidad;
    }

    public String getCalificacion() {
        return calificacion;
    }

    public void setCalificacion(String calificacion) {
        this.calificacion = calificacion;
    }

    public String getColider() {
        return colider;
    }

    public void setColider(String colider) {
        this.colider = colider;
    }

  
    
}
