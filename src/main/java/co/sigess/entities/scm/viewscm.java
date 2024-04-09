/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.sigess.entities.scm;

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
@Table(name = "vw_informescm", schema = "scm")
@XmlRootElement
public class viewscm implements Serializable{
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
    
    @Column(name = "primer_apellido")
    private String primerApellido;
        
    @Column(name = "primer_nombre")
    private String primerNombre;
    
    @Column(name = "documento")
    private String documento;
    
    @Column(name = "estado_del_caso")
    private String estadoCaso;
    
    @Column(name = "diagnostico")
    private String diagnostico;
    
    @Column(name = "prioridad_caso")
    private String prioridadCaso;
    
    @Column(name = "tipo_caso")
    private String tipoCaso;
     
    @Column(name = "tipo_retorno")
    private String tipo_retorno;
    
    @Column(name = "usuario_creador")
    private String usuarioCreador;
    
    @Column(name = "fk_empresa_id")
    private Integer empresaId;
    
    @Column(name = "recomendaciones")
    private Integer recomendaciones;
        
    @Column(name = "plan_accion")
    private Integer planAccion;
    
    private String sve;

    public Integer getRecomendaciones() {
        return recomendaciones;
    }

    public void setRecomendaciones(Integer recomendaciones) {
        this.recomendaciones = recomendaciones;
    }

    public Integer getPlanAccion() {
        return planAccion;
    }

    public void setPlanAccion(Integer planAccion) {
        this.planAccion = planAccion;
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

    public String getPrimerApellido() {
        return primerApellido;
    }

    public void setPrimerApellido(String primerApellido) {
        this.primerApellido = primerApellido;
    }

    public String getPrimerNombre() {
        return primerNombre;
    }

    public void setPrimerNombre(String primerNombre) {
        this.primerNombre = primerNombre;
    }

    public String getDocumento() {
        return documento;
    }

    public void setDocumento(String documento) {
        this.documento = documento;
    }

    public String getEstadoCaso() {
        return estadoCaso;
    }

    public void setEstadoCaso(String estadoCaso) {
        this.estadoCaso = estadoCaso;
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

    public String getTipo_retorno() {
        return tipo_retorno;
    }

    public void setTipo_retorno(String tipo_retorno) {
        this.tipo_retorno = tipo_retorno;
    }

    public String getUsuarioCreador() {
        return usuarioCreador;
    }

    public void setUsuarioCreador(String usuarioCreador) {
        this.usuarioCreador = usuarioCreador;
    }

    public Integer getEmpresaId() {
        return empresaId;
    }

    public void setEmpresaId(Integer empresaId) {
        this.empresaId = empresaId;
    }

    public String getSve() {
        return sve;
    }

    public void setSve(String sve) {
        this.sve = sve;
    }
    
}
