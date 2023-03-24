/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.sigess.entities.ind;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Usuario
 */
@Entity
@JsonIgnoreProperties(ignoreUnknown = true)
@Table(name = "vw_scm_corporativo", schema = "ind")
@XmlRootElement
public class vwscmcorporativo implements Serializable {
    
    @Column(name = "caso_medico")
    @Id
    private Integer id;
    
    @Column(name = "fecha_creacion")
    @Temporal(TemporalType.DATE)
    private Date fechaCreacion;

    @Column(name = "divisionunidad")
    private String divisionUnidad;    
    
    @Column(name = "ubicacion")
    private String ubicacion;
       
    @Column(name = "idarea")
    private Integer idArea;
           
    @Column(name = "idpadrearea")
    private Integer idPadreArea;
               
    @Column(name = "idtipoarea")
    private Integer idTipoArea;
    
    @Column(name = "estado_del_caso")
    private String estadoDelCaso;
    
    @Column(name = "diagnostico")
    private String diagnostico;
    
    @Column(name = "prioridad_caso")
    private String prioridadCaso;
    
    @Column(name = "tipo_caso")
    private String tipoCaso;
    
    @Column(name = "tipo_retorno")
    private String tipoRetorno;
    
    @Column(name = "fk_empresa_id")
    private Integer empresaId;
    
    @Column(name = "caso_medico_laboral")
    private String casoMedicoLaboral;

    public String getCasoMedicoLaboral() {
        return casoMedicoLaboral;
    }

    public void setCasoMedicoLaboral(String casoMedicoLaboral) {
        this.casoMedicoLaboral = casoMedicoLaboral;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(Date fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public String getDivisionUnidad() {
        return divisionUnidad;
    }

    public void setDivisionUnidad(String divisionUnidad) {
        this.divisionUnidad = divisionUnidad;
    }

    public String getUbicacion() {
        return ubicacion;
    }

    public void setUbicacion(String ubicacion) {
        this.ubicacion = ubicacion;
    }

    public Integer getIdArea() {
        return idArea;
    }

    public void setIdArea(Integer idArea) {
        this.idArea = idArea;
    }

    public Integer getIdPadreArea() {
        return idPadreArea;
    }

    public void setIdPadreArea(Integer idPadreArea) {
        this.idPadreArea = idPadreArea;
    }

    public Integer getIdTipoArea() {
        return idTipoArea;
    }

    public void setIdTipoArea(Integer idTipoArea) {
        this.idTipoArea = idTipoArea;
    }

    public String getEstadoDelCaso() {
        return estadoDelCaso;
    }

    public void setEstadoDelCaso(String estadoDelCaso) {
        this.estadoDelCaso = estadoDelCaso;
    }

    public String getDiagnostico() {
        return diagnostico;
    }

    public void setDiagnostico(String diagnostico) {
        this.diagnostico = diagnostico;
    }

    public String getPrioridadCaso() {
        return prioridadCaso;
    }

    public void setPrioridadCaso(String prioridadCaso) {
        this.prioridadCaso = prioridadCaso;
    }

    public String getTipoCaso() {
        return tipoCaso;
    }

    public void setTipoCaso(String tipoCaso) {
        this.tipoCaso = tipoCaso;
    }

    public String getTipoRetorno() {
        return tipoRetorno;
    }

    public void setTipoRetorno(String tipoRetorno) {
        this.tipoRetorno = tipoRetorno;
    }

    public Integer getEmpresaId() {
        return empresaId;
    }

    public void setEmpresaId(Integer empresaId) {
        this.empresaId = empresaId;
    }
    
}
