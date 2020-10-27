/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.sigess.entities.scm;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

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
    @Size(max = 128)
    @Column(name = "observaciones")
    private String observaciones;
    @Size(max = 128)
    @Column(name = "origen")
    private String origen;
    @Size(max = 128)
    @Column(name = "codigo_cie10")
    private String codigoCie10;
    @Size(max = 128)
    @Column(name = "region")
    private String region;
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
    @Size(max = 128)
    @Column(name = "descripcion_completa_caso")
    private String descripcionCompletaCaso;
    @Size(max = 128)
    @Column(name = "caso_medico_laboral")
    private String casoMedicoLaboral;
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
    @Size(max = 128)
    @Column(name = "concept_rehabilitacion")
    private String conceptRehabilitacion;
    @Size(max = 128)
    @Column(name = "fecha_concept_rehabilitacion")
    private String fechaConceptRehabilitacion;
    @Size(max = 128)
    @Column(name = "entidad_emite_concepto")
    private String entidadEmiteConcepto;
    @Size(max = 128)
    @Column(name = "requiere_intervencion")
    private String requiereIntervencion;
    @Size(max = 128)
    @Column(name = "sve")
    private String sve;
    @Size(max = 128)
    @Column(name = "professional_area")
    private String professionalArea;
    @Size(max = 128)
    @Column(name = "justification")
    private String justification;
    
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 128)
    @Column(name = "pk_user")
    private String pkUser;
   
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 128)
    @Column(name = "pk_jefe")
    private String pkJefe;
   
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 128)
    @Column(name = "pk_business_partner")
    private String pkBusinessPartner;
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

    public String getDiagnostico() {
        return diagnostico;
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

    public String getRazon() {
        return razon;
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

    public String getFechaConceptRehabilitacion() {
        return fechaConceptRehabilitacion;
    }

    public void setFechaConceptRehabilitacion(String fechaConceptRehabilitacion) {
        this.fechaConceptRehabilitacion = fechaConceptRehabilitacion;
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
    public String getPkUser() {
        return pkUser;
    }

    /**
     * @return the pkJefe
     */
    public String getPkJefe() {
        return pkJefe;
    }

    /**
     * @return the pkBusinessPartner
     */
    public String getPkBusinessPartner() {
        return pkBusinessPartner;
    }

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

    

   
    
}
