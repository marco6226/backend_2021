/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package co.sigess.entities.emp;

import co.sigess.entities.ado.Directorio;
import co.sigess.entities.ado.Documento;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author JULIO
 */
@Entity
@Table(name = "plantas", schema = "emp")
@JsonIgnoreProperties(ignoreUnknown = true)
@XmlRootElement
public class Plantas implements Serializable{

    private static final long serialVersionUID = 1L;
    @Id
    @SequenceGenerator(name = "plantas_id_seq", schema = "emp", sequenceName = "plantas_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "plantas_id_seq")
    @Basic(optional = false)
    @Column(name = "id")
    private long id;

    @Column(name = "nombre")
    private String nombre;

//    @Column(name = "id_division")
//    private String id_division;
    
    @JoinColumn(name = "id_division", referencedColumnName = "id", nullable = true)
    @ManyToOne
    private Area area;
    
//    @JoinColumn(name = "id_division", referencedColumnName = "id")
//    private Area area;

    @Column(name = "pais")
    private String pais;

    @Column(name = "id_empresa")
    private long id_empresa;
    
    @Column(name = "tipo")
    private String tipo;
    
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
    //public long getUsuarioConsolidado() {
     //   return usuarioConsolidado;
    //}

    //public void setUsuarioConsolidado(long usuarioConsolidado) {
    //    this.usuarioConsolidado = usuarioConsolidado;
    //}

    //public long getUsuarioHistorico() {
    //    return usuarioHistorico;
   // }

    //public void setUsuarioHistorico(long usuarioHistorico) {
    //    this.usuarioHistorico = usuarioHistorico;
    //}

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

    
    
    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public Plantas() {
    }

    public Plantas(long id) {
        this.id = id;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

//    public String getId_division() {
//        return id_division;
//    }
//
//    public void setId_division(String id_division) {
//        this.id_division = id_division;
//    }

    public String getPais() {
        return pais;
    }

    public void setPais(String pais) {
        this.pais = pais;
    }

    public long getId_empresa() {
        return id_empresa;
    }

    public void setId_empresa(long id_empresa) {
        this.id_empresa = id_empresa;
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

    public Area getArea() {
        return area;
    }

    public void setArea(Area area) {
        this.area = area;
    }
    
    
    
}
