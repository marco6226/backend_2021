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
    
    @Column(name = "documentos")
    private String documentos;
    
    @Column(name = "representante_legal")
    private String representante_legal;
    
    @Column(name = "numero_trabajadores")
    private Integer numero_trabajadores;
    
    @Column(name = "numero_trabajadores_asignados")
    private Integer numero_trabajadores_asignados;
    
    @Column(name = "fecha_vencimiento_arl")
    private Date fecha_vencimiento_arl;
    
    @Column(name = "fecha_vencimiento_sst")
    private Date fecha_vencimiento_sst;

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

    public String getDocumentos() {
        return documentos;
    }

    public void setDocumentos(String documentos) {
        this.documentos = documentos;
    }

    public String getRepresentante_legal() {
        return representante_legal;
    }

    public void setRepresentante_legal(String representante_legal) {
        this.representante_legal = representante_legal;
    }

    public Integer getNumero_trabajadores() {
        return numero_trabajadores;
    }

    public void setNumero_trabajadores(Integer numero_trabajadores) {
        this.numero_trabajadores = numero_trabajadores;
    }

    public Integer getNumero_trabajadores_asignados() {
        return numero_trabajadores_asignados;
    }

    public void setNumero_trabajadores_asignados(Integer numero_trabajadores_asignados) {
        this.numero_trabajadores_asignados = numero_trabajadores_asignados;
    }

    public Date getFecha_vencimiento_arl() {
        return fecha_vencimiento_arl;
    }

    public void setFecha_vencimiento_arl(Date fecha_vencimiento_arl) {
        this.fecha_vencimiento_arl = fecha_vencimiento_arl;
    }

    public Date getFecha_vencimiento_sst() {
        return fecha_vencimiento_sst;
    }

    public void setFecha_vencimiento_sst(Date fecha_vencimiento_sst) {
        this.fecha_vencimiento_sst = fecha_vencimiento_sst;
    }
}
