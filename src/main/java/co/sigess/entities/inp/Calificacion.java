/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.sigess.entities.inp;

import co.sigess.entities.ado.Documento;
import co.sigess.entities.conf.NivelRiesgo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
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
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author fmoreno
 */
@Entity
@JsonIgnoreProperties(ignoreUnknown = true)
@Table(name = "calificacion", schema = "inp")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Calificacion.findAll", query = "SELECT c FROM Calificacion c")})
public class Calificacion implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @SequenceGenerator(name = "calificacion_id_seq", schema = "inp", sequenceName = "calificacion_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "calificacion_id_seq")
    @Basic(optional = false)
    @Column(name = "id")
    private Long id;

    @Size(max = 1024)
    @Column(name = "recomendacion")
    private String recomendacion;

    @JoinColumn(name = "fk_elemento_inspeccion_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private ElementoInspeccion elementoInspeccion;

    @JoinColumn(name = "fk_inspeccion_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Inspeccion inspeccion;

    @JoinColumn(name = "fk_opcion_calificacion_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private OpcionCalificacion opcionCalificacion;

    @JoinColumn(name = "fk_nivel_riesgo_id", referencedColumnName = "id")
    @ManyToOne()
    private NivelRiesgo nivelRiesgo;

    @JoinTable(name = "documento_calificacion", schema = "inp", joinColumns = {
        @JoinColumn(name = "fk_calificacion_id", referencedColumnName = "id")}, inverseJoinColumns = {
        @JoinColumn(name = "fk_documento_id", referencedColumnName = "id")})
    @ManyToMany
    private List<Documento> documentosList;
    
    
    @JoinColumn(name = "fk_tipo_hallazgo_id", referencedColumnName = "id")
    @ManyToOne(cascade = CascadeType.PERSIST)
    private TipoHallazgo tipoHallazgo;
    
    @Column(name = "calcular_cumplimiento")
    private boolean calcularCumplimiento;
    
    @Column(name = "accion")
    private Integer accion;
    
    @Size(max = 1024)
    @Column(name = "descripcion_accion")
    private String descripcionAccion;
     @Size(max = 1024)
    @Column(name = "descripcion_miti")
    private String descripcionMiti;
    
    @Column(name = "plan_accion")
    private Integer planAccion;
     
    @Column(name = "responsable")
    private String responsable;
    
    @Size(max = 1024)
    @Column (name="descripcion_acc_tarjeta")
    private String descripcionAccTarjeta;
    
    @Column (name="fecha_proyectada")
    private Date fechaProyectada;
    
    
            
            
    public Calificacion() {
    }

    public Calificacion(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRecomendacion() {
        return recomendacion;
    }

    public void setRecomendacion(String recomendacion) {
        this.recomendacion = recomendacion;
    }

    public ElementoInspeccion getElementoInspeccion() {
        return elementoInspeccion;
    }

    public void setElementoInspeccion(ElementoInspeccion elementoInspeccion) {
        this.elementoInspeccion = elementoInspeccion;
    }

    @JsonIgnore
    public Inspeccion getInspeccion() {
        return inspeccion;
    }

    @JsonProperty("inspeccion")
    public void setInspeccion(Inspeccion inspeccion) {
        this.inspeccion = inspeccion;
    }

    public OpcionCalificacion getOpcionCalificacion() {
        return opcionCalificacion;
    }

    public void setOpcionCalificacion(OpcionCalificacion opcionCalificacion) {
        this.opcionCalificacion = opcionCalificacion;
    }

    public NivelRiesgo getNivelRiesgo() {
        return nivelRiesgo;
    }

    public void setNivelRiesgo(NivelRiesgo nivelRiesgo) {
        this.nivelRiesgo = nivelRiesgo;
    }

    public List<Documento> getDocumentosList() {
        return documentosList;
    }

    public void setDocumentosList(List<Documento> documentosList) {
        this.documentosList = documentosList;
    }

    public TipoHallazgo getTipoHallazgo() {
        return tipoHallazgo;
    }

    public void setTipoHallazgo(TipoHallazgo tipoHallazgo) {
        this.tipoHallazgo = tipoHallazgo;
    }

    public boolean isCalcularCumplimiento() {
        return calcularCumplimiento;
    }

    public void setCalcularCumplimiento(boolean calcularCumplimiento) {
        this.calcularCumplimiento = calcularCumplimiento;
    }
    
     public Integer getAccion() {
        return accion;
    }

    public void setAccion(Integer accion) {
        this.accion = accion;
    }
    
    public String getDescripcionAccion() {
        return descripcionAccion;
    }

    public void setDescripcionAccion(String descripcionAccion) {
        this.descripcionAccion = descripcionAccion;
    }
    
    public String getDescripcionMiti() {
        return descripcionMiti;
    }

    public void setDescripcionMiti(String descripcionMiti) {
        this.descripcionMiti = descripcionMiti;
    }
    
     public Integer getPlanAccion() {
        return planAccion;
    }

    public void setPlanAccion(Integer planAccion) {
        this.planAccion = planAccion;
    }
    
    public String getResponsable() {
        return responsable;
    }

    public void setResponsable(String responsable) {
        this.responsable = responsable;
    }
    
    public String getDescripcionAccTarjeta() {
        return descripcionAccTarjeta;
    }

    public void setDescripcionAccTarjeta(String descripcionAccTarjeta) {
        this.descripcionAccTarjeta = descripcionAccTarjeta;
    }
    
    public Date getFechaProyectada() {
        return fechaProyectada;
    }

    public void setFechaProyectada(Date fechaProyectada) {
        this.fechaProyectada = fechaProyectada;
    }
    

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Calificacion)) {
            return false;
        }
        Calificacion other = (Calificacion) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "co.sigess.entities.inp.Calificacion[ id=" + id + " ]";
    }

}
