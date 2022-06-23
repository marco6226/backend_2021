/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.sigess.entities.scm;

import co.sigess.entities.emp.Empleado;
import co.sigess.entities.emp.Empresa;
import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.ManyToOne;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import org.jvnet.hk2.annotations.Optional;

/**
 *
 * @author leonardo
 */
@Entity
@Table(name = "casos_medicos", schema="scm")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "CasosMedicos.findAll", query = "SELECT c FROM CasosMedicos c")})
   
public class CasosMedicos implements Serializable {

  
    private static final long serialVersionUID = 1L;
    @Id
    @SequenceGenerator(name = "casos_medicos_id_seq", schema = "scm", sequenceName = "casos_medicos_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "casos_medicos_id_seq")
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Size(max = 128)
    @Column(name = "status_caso")
    private String statusCaso;
    @Size(max = 128)
    @Column(name = "fecha_final")
    private String fechaFinal;
    @Size(max = 1024)
    @Column(name = "descripcion_cargo")
    private String descripcionCargo;
    @Size(max = 2048)
    @Column(name = "observaciones")
    private String observaciones;
    @Size(max = 2048) 
    @Column(name = "origen")
    private String origen;
    @Size(max = 128)
    @Column(name = "codigo_cie10")
    private String codigoCie10;
    @Size(max = 128)
    @Column(name = "region")
    private String region;
    @Column(name = "fecha_creacion")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaCreacion;
    @Size(max = 128)
    @Column(name = "ciudad")
    private String ciudad;
    @Size(max = 128)
    @Column(name = "names")
    private String names;
    @Size(max = 128)
    @Column(name = "cargo")
    private String cargo;
    @Size(max = 128)
    @Column(name = "salud_status")
    private String saludStatus;
    @Size(max = 128)
    @Column(name = "sistema_afectado")
    private String sistemaAfectado;
    @Size(max = 128)
    @Column(name = "diagnostico")
    private String diagnostico;
    @Size(max = 1024)
    @Column(name = "descripcion_completa_caso")
    private String descripcionCompletaCaso;
    @Size(max = 1024)
    @Column(name = "caso_medico_laboral")
    private String casoMedicoLaboral;
    @Column(name = "prioridad_caso")
    private String prioridadCaso;
    @Column(name = "tipo_caso")
    private String tipoCaso;
    @Size(max = 128)
    @Column(name = "razon")
    private String razon;
    @Size(max = 128)
    @Column(name = "pcl")
    private String pcl;
    @Size(max = 128)
    @Column(name = "porcentaje_pcl")
    private String porcentajePcl;
    @Size(max = 128)
    @Column(name = "emision_pcl_fecha")
    private String emisionPclFecha;
    @Size(max = 128)
    @Column(name = "pcl_emit_entidad")
    private String pclEmitEntidad;
    @Size(max = 128)
    @Column(name = "status_de_calificacion")
    private String statusDeCalificacion;
    @Size(max = 128)
    @Column(name = "fecha_calificacion")
    private String fechaCalificacion;
    @Size(max = 128)
    @Column(name = "entidad_emite_calificacion")
    private String entidadEmiteCalificacion;
    @Optional
    @Column(name = "entidad_emitida")
    private Integer entidadEmitida;
    @Optional
    @Column(name = "entidad_emitidatwo")
    private Integer entidadEmitidaTwo;
    @Size(max = 128)
    @Column(name = "concept_rehabilitacion")
    private String conceptRehabilitacion;
     @Size(max = 128)
    @Column(name = "concept_rehabilitaciontwo")
    private String conceptRehabilitacionTwo;
    @Column(name = "fecha_concept_rehabilitacion")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaConceptRehabilitacion;
    @Column(name = "fecha_concept_rehabilitaciontwo")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaConceptRehabilitacionTwo;
    
    @JoinColumn(name = "fk_empresa_id", referencedColumnName = "id")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Empresa empresa;
    @Size(max = 128)
    @Column(name = "entidad_emite_concepto")
    private String entidadEmiteConcepto;
    @Size(max = 128)
    @Column(name = "entidad_emite_conceptotwo")
    private String entidadEmiteConceptoTwo;
    @Size(max = 128)
    @Column(name = "requiere_intervencion")
    private String requiereIntervencion;
    @Size(max = 128)
    @Column(name = "sve")
    private String sve;
    @Size(max = 128)
    @Column(name = "documento")
    private String documento;
    @Size(max = 128)
    @Column(name = "professional_area")
    private String professionalArea;
    @Size(max = 128)
    @Column(name = "justification")
    private String justification;
    
    @Column(name = "eliminado")
    private Boolean eliminado;
    

    @JoinColumn(name = "pk_user", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Empleado pkUser;

    public CasosMedicos() {
    }

    

 
    public String getStatusCaso() {
        return statusCaso;
    }

    public void setStatusCaso(String statusCaso) {
        this.statusCaso = statusCaso;
    }

    public String getFechaFinal() {
        return fechaFinal;
    }

    public void setFechaFinal(String fechaFinal) {
        this.fechaFinal = fechaFinal;
    }

    /**
     * @return the fechaConceptRehabilitacion
     */
    public Date getFechaConceptRehabilitacion() {
        return fechaConceptRehabilitacion;
    }

    /**
     * @param fechaConceptRehabilitacion the fechaConceptRehabilitacion to set
     */
    public void setFechaConceptRehabilitacion(Date fechaConceptRehabilitacion) {
        this.fechaConceptRehabilitacion = fechaConceptRehabilitacion;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public String getOrigen() {
        return origen;
    }

    public void setOrigen(String origen) {
        this.origen = origen;
    }

    public String getCodigoCie10() {
        return codigoCie10;
    }

    public void setCodigoCie10(String codigoCie10) {
        this.codigoCie10 = codigoCie10;
    }

    public String getSistemaAfectado() {
        return sistemaAfectado;
    }

    public void setSistemaAfectado(String sistemaAfectado) {
        this.sistemaAfectado = sistemaAfectado;
    }

    /**
     * @return the empresa
     */
    public Empresa getEmpresa() {
        return empresa;
    }

    /**
     * @param empresa the empresa to set
     */
    public void setEmpresa(Empresa empresa) {
        this.empresa = empresa;
    }

    public String getDiagnostico() {
        return diagnostico;
    }

    /**
     * @return the descripcionCargo
     */
    public String getDescripcionCargo() {
        return descripcionCargo;
    }

    /**
     * @param descripcionCargo the descripcionCargo to set
     */
    public void setDescripcionCargo(String descripcionCargo) {
        this.descripcionCargo = descripcionCargo;
    }

    public void setDiagnostico(String diagnostico) {
        this.diagnostico = diagnostico;
    }

    public String getDescripcionCompletaCaso() {
        return descripcionCompletaCaso;
    }

    public void setDescripcionCompletaCaso(String descripcionCompletaCaso) {
        this.descripcionCompletaCaso = descripcionCompletaCaso;
    }

    public String getCasoMedicoLaboral() {
        return casoMedicoLaboral;
    }

    public void setCasoMedicoLaboral(String casoMedicoLaboral) {
        this.casoMedicoLaboral = casoMedicoLaboral;
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

    public String getRazon() {
        return razon;
    }

      /**
     * @return the fechaCreacion
     */
    public Date getFechaCreacion() {
        return fechaCreacion;
    }

    /**
     * @param fechaCreacion the fechaCreacion to set
     */
    public void setFechaCreacion(Date fechaCreacion) {
         this.fechaCreacion = fechaCreacion; 
    }
 
    /**
     * @return the saludStatus
     */
    public String getSaludStatus() {
        return saludStatus;
    }

    /**
     * @param saludStatus the saludStatus to set
     */
    public void setSaludStatus(String saludStatus) {
        this.saludStatus = saludStatus;
    }

    /**
     * @return the names
     */
    public String getNames() {
        return names;
    }

    /**
     * @param names the names to set
     */
    public void setNames(String names) {
        this.names = names;
    }

    
    /**
     * @return the region
     */
    public String getRegion() {
        return region;
    }

    /**
     * @return the pkUser
     */
    public Empleado getPkUser() {
        return pkUser;
    }

    /**
     * @param pkUser the pkUser to set
     */
    public void setPkUser(Empleado pkUser) {
        this.pkUser = pkUser;
    }

    /**
     * @param region the region to set
     */
    public void setRegion(String region) {
        this.region = region;
    }

    /**
     * @return the ciudad
     */
    public String getCiudad() {
        return ciudad;
    }

    /**
     * @param ciudad the ciudad to set
     */
    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }
    
    public void setRazon(String razon) {
        this.razon = razon;
    }

    public String getPcl() {
        return pcl;
    }

    public void setPcl(String pcl) {
        this.pcl = pcl;
    }

    public String getPorcentajePcl() {
        return porcentajePcl;
    }

    public void setPorcentajePcl(String porcentajePcl) {
        this.porcentajePcl = porcentajePcl;
    }

    public String getEmisionPclFecha() {
        return emisionPclFecha;
    }

    public void setEmisionPclFecha(String emisionPclFecha) {
        this.emisionPclFecha = emisionPclFecha;
    }

    public String getPclEmitEntidad() {
        return pclEmitEntidad;
    }

    public void setPclEmitEntidad(String pclEmitEntidad) {
        this.pclEmitEntidad = pclEmitEntidad;
    }

    public String getStatusDeCalificacion() {
        return statusDeCalificacion;
    }

    public void setStatusDeCalificacion(String statusDeCalificacion) {
        this.statusDeCalificacion = statusDeCalificacion;
    }

    public String getFechaCalificacion() {
        return fechaCalificacion;
    }

    public void setFechaCalificacion(String fechaCalificacion) {
        this.fechaCalificacion = fechaCalificacion;
    }

    public String getEntidadEmiteCalificacion() {
        return entidadEmiteCalificacion;
    }

    public void setEntidadEmiteCalificacion(String entidadEmiteCalificacion) {
        this.entidadEmiteCalificacion = entidadEmiteCalificacion;
    }

    public String getConceptRehabilitacion() {
        return conceptRehabilitacion;
    }

    public void setConceptRehabilitacion(String conceptRehabilitacion) {
        this.conceptRehabilitacion = conceptRehabilitacion;
    }

   
    public String getEntidadEmiteConcepto() {
        return entidadEmiteConcepto;
    }

    public void setEntidadEmiteConcepto(String entidadEmiteConcepto) {
        this.entidadEmiteConcepto = entidadEmiteConcepto;
    }

    public String getRequiereIntervencion() {
        return requiereIntervencion;
    }

    public void setRequiereIntervencion(String requiereIntervencion) {
        this.requiereIntervencion = requiereIntervencion;
    }

    public String getSve() {
        return sve;
    }

    public void setSve(String sve) {
        this.sve = sve;
    }

    public String getProfessionalArea() {
        return professionalArea;
    }

    public void setProfessionalArea(String professionalArea) {
        this.professionalArea = professionalArea;
    }

    public String getJustification() {
        return justification;
    }

    public void setJustification(String justification) {
        this.justification = justification;
    }

    /**
     * @return the pkUser
     */
  

    /**
     * @return the pkJefe
     */
  
    /**
     * @return the id
     */
    public Integer getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * @return the cargo
     */
    public String getCargo() {
        return cargo;
    }

    /**
     * @param cargo the cargo to set
     */
    public void setCargo(String cargo) {
        this.cargo = cargo;
    }

    /**
     * @return the documento
     */
    public String getDocumento() {
        return documento;
    }

    /**
     * @param documento the documento to set
     */
    public void setDocumento(String documento) {
        this.documento = documento;
    }

    /**
     * @return the entidadEmitida
     */
    public Integer getEntidadEmitida() {
        return entidadEmitida;
    }

    /**
     * @param entidadEmitida the entidadEmitida to set
     */
    public void setEntidadEmitida(Integer entidadEmitida) {
        this.entidadEmitida = entidadEmitida;
    }

    /**
     * @return the entidadEmitidaTwo
     */
    public Integer getEntidadEmitidaTwo() {
        return entidadEmitidaTwo;
    }

    /**
     * @param entidadEmitidaTwo the entidadEmitidaTwo to set
     */
    public void setEntidadEmitidaTwo(Integer entidadEmitidaTwo) {
        this.entidadEmitidaTwo = entidadEmitidaTwo;
    }

    /**
     * @return the conceptRehabilitacionTwo
     */
    public String getConceptRehabilitacionTwo() {
        return conceptRehabilitacionTwo;
    }

    /**
     * @param conceptRehabilitacionTwo the conceptRehabilitacionTwo to set
     */
    public void setConceptRehabilitacionTwo(String conceptRehabilitacionTwo) {
        this.conceptRehabilitacionTwo = conceptRehabilitacionTwo;
    }

    /**
     * @return the fechaConceptRehabilitacionTwo
     */
    public Date getFechaConceptRehabilitacionTwo() {
        return fechaConceptRehabilitacionTwo;
    }

    /**
     * @param fechaConceptRehabilitacionTwo the fechaConceptRehabilitacionTwo to set
     */
    public void setFechaConceptRehabilitacionTwo(Date fechaConceptRehabilitacionTwo) {
        this.fechaConceptRehabilitacionTwo = fechaConceptRehabilitacionTwo;
    }

    /**
     * @return the entidadEmiteConceptoTwo
     */
    public String getEntidadEmiteConceptoTwo() {
        return entidadEmiteConceptoTwo;
    }

    /**
     * @param entidadEmiteConceptoTwo the entidadEmiteConceptoTwo to set
     */
    public void setEntidadEmiteConceptoTwo(String entidadEmiteConceptoTwo) {
        this.entidadEmiteConceptoTwo = entidadEmiteConceptoTwo;
    }

    /**
     * @return the eliminado
     */
    public Boolean getEliminado() {
        return eliminado;
    }

    /**
     * @param eliminado the eliminado to set
     */
    public void setEliminado(Boolean eliminado) {
        this.eliminado = eliminado;
    }

    
    
}
