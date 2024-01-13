/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author John Rueda
 */
@Entity
@Table(name = "localidades", schema = "emp")
@XmlRootElement
public class Localidades implements Serializable{
    
    @Id
    @Basic(optional = false)
    @NotNull
    @SequenceGenerator(name = "localidades_id_seq", schema = "emp", sequenceName = "localidades_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "localidades_id_seq")
    @Column(name = "id")
    private Integer id;
    
    @Column(name = "empresa_id")
    private Integer empresa_id;
    
    @Column(name = "localidad")
    private String localidad;
    
    @JoinColumn(name = "fk_plantas_id", referencedColumnName = "id", updatable = false)
    @ManyToOne(optional = false)
    private Plantas plantas;
    
    @Column(name = "id_doc_consolidado")
    private String idDocConsolidado;
        
    @Column(name = "id_doc_historico")
    private String idDocHistorico;

    @Column(name = "fecha_consolidado")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaConsolidado;
        
    @Column(name = "fecha_historico")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaHistorico;
    
    @Column(name = "fecha_consolidado_start")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaConsolidadoStart;
        
    @Column(name = "fecha_historico_start")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaHistoricoStart;
    
    //@Column(name = "usuario_consolidado")
    //private long usuarioConsolidado;
        
    //@Column(name = "usuario_historico")
    //private long usuarioHistorico;
    
    @JoinColumn(name = "usuario_consolidado", referencedColumnName = "id")
    @ManyToOne
    private Usuario usuarioConsolidado;
    
    @JoinColumn(name = "usuario_historico", referencedColumnName = "id")
    @ManyToOne
    private Usuario usuarioHistorico;
            
    @Column(name = "descarga_consolidado")
    private Boolean descargaConsolidado;
        
    @Column(name = "descarga_historico")
    private Boolean descargaHistorico;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getEmpresa_id() {
        return empresa_id;
    }

    public void setEmpresa_id(Integer empresa_id) {
        this.empresa_id = empresa_id;
    }

    public String getLocalidad() {
        return localidad;
    }

    public void setLocalidad(String localidad) {
        this.localidad = localidad;
    }

    public Plantas getPlantas() {
        return plantas;
    }

    public void setPlantas(Plantas plantas) {
        this.plantas = plantas;
    }

    public String getIdDocConsolidado() {
        return idDocConsolidado;
    }

    public void setIdDocConsolidado(String idDocConsolidado) {
        this.idDocConsolidado = idDocConsolidado;
    }

    public String getIdDocHistorico() {
        return idDocHistorico;
    }

    public void setIdDocHistorico(String idDocHistorico) {
        this.idDocHistorico = idDocHistorico;
    }

    public Date getFechaConsolidado() {
        return fechaConsolidado;
    }

    public void setFechaConsolidado(Date fechaConsolidado) {
        this.fechaConsolidado = fechaConsolidado;
    }

    public Date getFechaHistorico() {
        return fechaHistorico;
    }

    public void setFechaHistorico(Date fechaHistorico) {
        this.fechaHistorico = fechaHistorico;
    }

    public Date getFechaConsolidadoStart() {
        return fechaConsolidadoStart;
    }

    public void setFechaConsolidadoStart(Date fechaConsolidadoStart) {
        this.fechaConsolidadoStart = fechaConsolidadoStart;
    }

    public Date getFechaHistoricoStart() {
        return fechaHistoricoStart;
    }

    public void setFechaHistoricoStart(Date fechaHistoricoStart) {
        this.fechaHistoricoStart = fechaHistoricoStart;
    }

    public Usuario getUsuarioConsolidado() {
        return usuarioConsolidado;
    }

    public void setUsuarioConsolidado(Usuario usuarioConsolidado) {
        this.usuarioConsolidado = usuarioConsolidado;
    }

    public Usuario getUsuarioHistorico() {
        return usuarioHistorico;
    }

    public void setUsuarioHistorico(Usuario usuarioHistorico) {
        this.usuarioHistorico = usuarioHistorico;
    }

    public Boolean getDescargaConsolidado() {
        return descargaConsolidado;
    }

    public void setDescargaConsolidado(Boolean descargaConsolidado) {
        this.descargaConsolidado = descargaConsolidado;
    }

    public Boolean getDescargaHistorico() {
        return descargaHistorico;
    }

    public void setDescargaHistorico(Boolean descargaHistorico) {
        this.descargaHistorico = descargaHistorico;
    }
    
    
}
