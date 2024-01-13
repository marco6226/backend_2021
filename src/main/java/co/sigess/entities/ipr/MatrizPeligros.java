/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.sigess.entities.ipr;

import co.sigess.entities.ado.Documento;
import co.sigess.entities.emp.Area;
import co.sigess.entities.emp.Empresa;
import co.sigess.entities.emp.Localidades;
import co.sigess.entities.emp.Plantas;
import com.fasterxml.jackson.annotation.JsonIgnore;
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
@Table(name = "matriz_peligro", schema = "ipr")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "MatrizPeligros.findAll", query = "SELECT mp FROM MatrizPeligros mp")})
public class MatrizPeligros implements Serializable{
    private static final long serialVersionUID = 1L;
    @Id
    @SequenceGenerator(name = "matriz_peligros_id_seq", schema = "ipr", sequenceName = "matriz_peligros_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "matriz_peligros_id_seq")
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    
    @Column(name = "generalinf")
    private String generalInf;

    @Column(name = "peligro")
    private String peligro;
            
    @Column(name = "controlesexistentes")
    private String controlesexistentes;
                
    @JoinColumn(name = "fk_area_matriz_id", referencedColumnName = "id")
    private AreaMatriz area;
    
    @JoinColumn(name = "fk_proceso_matriz_id", referencedColumnName = "id")
    private ProcesoMatriz proceso;
        
    @JoinColumn(name = "fk_subproceso_matriz_id", referencedColumnName = "id")
    private SubprocesoMatriz subProceso;
        
    @JoinColumn(name = "fk_planta_id", referencedColumnName = "id")
    private Plantas plantas;
    
    @JoinColumn(name = "fk_localidad_id", referencedColumnName = "id")
    private Localidades localidad;
    
    @JsonIgnore
    @JoinColumn(name = "fk_empresa_id", referencedColumnName = "id")
    private Empresa empresa;
    
    @Column(name = "valoracion_riesgo_inicial")
    private String valoracionRiesgoInicial;
    
    @Column(name = "valoracion_riesgo_residual")
    private String valoracionRiesgoResidual;
        
    @Column(name = "plan_accion")
    private String planAccion;

    @Column(name = "fecha_creacion")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaCreacion;
    
    @Column(name = "fecha_edicion")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaEdicion;
    
    @Column(name = "eliminado")
    private Boolean eliminado;
    
    @Column(name = "id_edicion")
    private Integer idEdicion;
    
    @Column(name = "fk_matriz_peligros")
    private Integer fkmatrizpeligros;
    
    @Column(name = "efectividad_controles")
    private String efectividadControles;
    
    @JoinTable(name = "documento_matriz_peligro", schema = "ipr", joinColumns = {
        @JoinColumn(name = "fk_matriz_peligro_id", referencedColumnName = "id")}, inverseJoinColumns = {
        @JoinColumn(name = "fk_documento_id", referencedColumnName = "id")})   
    @ManyToMany
    private List<Documento> documentosList;

    public List<Documento> getDocumentosList() {
        return documentosList;
    }

    public void setDocumentosList(List<Documento> documentosList) {
        this.documentosList = documentosList;
    }
    
    public Integer getFkmatrizpeligros() {
        return fkmatrizpeligros;
    }

    public void setFkmatrizpeligros(Integer fkmatrizpeligros) {
        this.fkmatrizpeligros = fkmatrizpeligros;
    }
    
    public String getValoracionRiesgoResidual() {
        return valoracionRiesgoResidual;
    }

    public void setValoracionRiesgoResidual(String valoracionRiesgoResidual) {
        this.valoracionRiesgoResidual = valoracionRiesgoResidual;
    }
   
    public Integer getIdEdicion() {
        return idEdicion;
    }

    public void setIdEdicion(Integer idEdicion) {
        this.idEdicion = idEdicion;
    }

    public Date getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(Date fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public Boolean getEliminado() {
        return eliminado;
    }

    public void setEliminado(Boolean eliminado) {
        this.eliminado = eliminado;
    }
    
    public String getValoracionRiesgoInicial() {
        return valoracionRiesgoInicial;
    }

    public void setValoracionRiesgoInicial(String valoracionRiesgoInicial) {
        this.valoracionRiesgoInicial = valoracionRiesgoInicial;
    }

    public String getPlanAccion() {
        return planAccion;
    }

    public void setPlanAccion(String planAccion) {
        this.planAccion = planAccion;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }


    public String getPeligro() {
        return peligro;
    }

    public void setPeligro(String peligro) {
        this.peligro = peligro;
    }

    public String getControlesexistentes() {
        return controlesexistentes;
    }

    public void setControlesexistentes(String controlesexistentes) {
        this.controlesexistentes = controlesexistentes;
    }


    public Plantas getPlantas() {
        return plantas;
    }

    public void setPlantas(Plantas plantas) {
        this.plantas = plantas;
    }

    public Localidades getLocalidad() {
        return localidad;
    }

    public void setLocalidad(Localidades localidad) {
        this.localidad = localidad;
    }
    
    public Empresa getEmpresa() {
        return empresa;
    }

    public void setEmpresa(Empresa empresa) {
        this.empresa = empresa;
    }
           
    public String getGeneralInf() {
        return generalInf;
    }

    public void setGeneralInf(String generalInf) {
        this.generalInf = generalInf;
    }
    
    public AreaMatriz getArea() {
        return area;
    }

    public void setArea(AreaMatriz area) {
        this.area = area;
    }
    
    public ProcesoMatriz getProceso() {
        return proceso;
    }

    public void setProceso(ProcesoMatriz proceso) {
        this.proceso = proceso;
    }

    public SubprocesoMatriz getSubProceso() {
        return subProceso;
    }

    public void setSubProceso(SubprocesoMatriz subProceso) {
        this.subProceso = subProceso;
    }

    public Date getFechaEdicion() {
        return fechaEdicion;
    }

    public void setFechaEdicion(Date fechaEdicion) {
        this.fechaEdicion = fechaEdicion;
    }

    public String getEfectividadControles() {
        return efectividadControles;
    }

    public void setEfectividadControles(String efectividadControles) {
        this.efectividadControles = efectividadControles;
    }
    
    
}
