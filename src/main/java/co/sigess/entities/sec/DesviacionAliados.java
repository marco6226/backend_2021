/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.sigess.entities.sec;

import co.sigess.entities.emp.Area;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author JULIO
 */
@Entity
@JsonIgnoreProperties(ignoreUnknown = true)
@Table(name = "vw_desviacion_aliados", schema = "sec")
@XmlRootElement
public class DesviacionAliados implements Serializable{
    private static final long serialVersionUID = 1L;
    
    @Column(name = "hash_id")
    @Id
    private String hashId;
    
    @Column(name = "id")
    private Integer id;
    
    @Column(name = "razon_social")
    private String razonSocial;
    
    @Column(name = "nit")
    private String nit;
    
    @Column(name = "id_empleado")
    private String idEmpleado;
    
    @Column(name = "fecha_reporte")
    @Temporal(TemporalType.DATE)
    private Date fechaReporte;
    
    @JoinColumn(name = "fk_area_id", referencedColumnName = "id")
    @ManyToOne
    private Area area;
    
    @Column(name = "fk_aliado_id")
    private Integer aliadoId;
    
    @Column(name = "fk_id_empresa")
    private Integer empresaId;
    
    @Column(name = "seguimiento")
    private String seguimiento;
    
    @Column(name = "incapacidades")
    private String incapacidades;
    
    @Column(name = "gestor")
    private String gestor;
    
    @Column(name = "fk_analisis_desviacion")
    private Integer analisisDesviacionId;
    
    @Column(name = "plan_accion")
    private String planAccion;

    public DesviacionAliados() {
    }

    public String getHashId() {
        return hashId;
    }

    public void setHashId(String hashId) {
        this.hashId = hashId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getRazonSocial() {
        return razonSocial;
    }

    public String getNit() {
        return nit;
    }

    public void setNit(String nit) {
        this.nit = nit;
    }

    public void setRazonSocial(String razonSocial) {
        this.razonSocial = razonSocial;
    }

    public String getIdEmpleado() {
        return idEmpleado;
    }

    public void setIdEmpleado(String idEmpleado) {
        this.idEmpleado = idEmpleado;
    }

    public Date getFechaReporte() {
        return fechaReporte;
    }

    public void setFechaReporte(Date fechaReporte) {
        this.fechaReporte = fechaReporte;
    }

    public Area getArea() {
        return area;
    }

    public void setArea(Area area) {
        this.area = area;
    }

    public Integer getAliadoId() {
        return aliadoId;
    }

    public void setAliadoId(Integer aliadoId) {
        this.aliadoId = aliadoId;
    }

    public Integer getEmpresaId() {
        return empresaId;
    }

    public void setEmpresaId(Integer empresaId) {
        this.empresaId = empresaId;
    }

    public String getSeguimiento() {
        return seguimiento;
    }

    public void setSeguimiento(String seguimiento) {
        this.seguimiento = seguimiento;
    }

    public String getIncapacidades() {
        return incapacidades;
    }

    public void setIncapacidades(String incapacidades) {
        this.incapacidades = incapacidades;
    }

    public String getGestor() {
        return gestor;
    }

    public void setGestor(String gestor) {
        this.gestor = gestor;
    }

    public Integer getAnalisisDesviacionId() {
        return analisisDesviacionId;
    }

    public void setAnalisisDesviacionId(Integer analisisDesviacionId) {
        this.analisisDesviacionId = analisisDesviacionId;
    }

    public String getPlanAccion() {
        return this.planAccion;
    }

    public void setPlanAccion(String planAccion) {
        this.planAccion = planAccion;
    }
}
