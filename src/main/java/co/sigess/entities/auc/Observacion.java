/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.sigess.entities.auc;

import co.sigess.entities.ado.Documento;
import co.sigess.entities.conf.NivelRiesgo;
import co.sigess.entities.emp.Area;
import co.sigess.entities.emp.Usuario;
import co.sigess.entities.sec.CausaRaiz;
import co.sigess.util.converter.StringListConverter;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Convert;
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
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author fmoreno
 */
@Entity
@JsonIgnoreProperties(ignoreUnknown = true)
@Table(name = "observacion", schema = "auc")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Observacion.findAll", query = "SELECT o FROM Observacion o")})
public class Observacion implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @SequenceGenerator(name = "observacion_id_seq", schema = "auc", sequenceName = "observacion_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "observacion_id_seq")
    @Basic(optional = false)
    @Column(name = "id")
    private Long id;

    @Size(max = 100)
    @Column(name = "tipo_observacion")
    private String tipoObservacion;

    @Convert(converter = StringListConverter.class)
    @Column(name = "afecta")
    private List<String> afecta;

    @Size(max = 512)
    @Column(name = "descripcion")
    private String descripcion;

    @Size(max = 512)
    @Column(name = "recomendacion")
    private String recomendacion;
    
    @Size(max = 512)
    @Column(name = "personas_observadas")
    private String personasobservadas;
    
    @Size(max = 512)
    @Column(name = "personas_abordadas")
    private String personasabordadas;
            
    @Column(name = "fecha_observacion")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaObservacion;
    @Column(name = "aceptada")
    private Boolean aceptada;

    @Size(max = 512)
    @Column(name = "motivo")
    private String motivo;

    @Column(name = "fecha_respuesta")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaRespuesta;

    @JoinColumn(name = "fk_usuario_reporta_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Usuario usuarioReporta;

    @JoinColumn(name = "fk_usuario_revisa_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Usuario usuarioRevisa;

    @JoinColumn(name = "fk_area_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Area area;

    @JoinColumn(name = "fk_nivel_riesgo_id", referencedColumnName = "id")
    @ManyToOne()
    private NivelRiesgo nivelRiesgo;

    @JoinTable(name = "implicacion_observacion", schema = "auc", joinColumns = {
        @JoinColumn(name = "fk_observacion_id", referencedColumnName = "id")}, inverseJoinColumns = {
        @JoinColumn(name = "fk_implicacion_id", referencedColumnName = "id")})
    @ManyToMany
    private List<Implicacion> implicacionList;
    
    @JoinTable(name = "documento_observacion", schema = "auc", joinColumns = {
        @JoinColumn(name = "fk_observacion_id", referencedColumnName = "id")}, inverseJoinColumns = {
        @JoinColumn(name = "fk_documento_id", referencedColumnName = "id")})
    @ManyToMany
    private List<Documento> documentoList;

    @JoinTable(name = "causa_raiz_observacion", schema = "auc", joinColumns = {
        @JoinColumn(name = "fk_observacion_id", referencedColumnName = "id")}, inverseJoinColumns = {
        @JoinColumn(name = "fk_causa_raiz_id", referencedColumnName = "id")})
    @ManyToMany
    private List<CausaRaiz> causaRaizList;

    @JoinTable(name = "causa_raiz_aprobada_observacion", schema = "auc", joinColumns = {
        @JoinColumn(name = "fk_observacion_id", referencedColumnName = "id")}, inverseJoinColumns = {
        @JoinColumn(name = "fk_causa_raiz_id", referencedColumnName = "id")})
    @ManyToMany
    private List<CausaRaiz> causaRaizAprobadaList;

    @JoinColumn(name = "fk_tarjeta_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Tarjeta tarjeta;

    public Observacion() {
    }

    public Observacion(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTipoObservacion() {
        return tipoObservacion;
    }

    public void setTipoObservacion(String tipoObservacion) {
        this.tipoObservacion = tipoObservacion;
    }

    public List<String> getAfecta() {
        return afecta;
    }

    public void setAfecta(List<String> afecta) {
        this.afecta = afecta;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getRecomendacion() {
        return recomendacion;
    }

    public void setRecomendacion(String recomendacion) {
        this.recomendacion = recomendacion;
    }

    public Date getFechaObservacion() {
        return fechaObservacion;
    }

    public void setFechaObservacion(Date fechaObservacion) {
        this.fechaObservacion = fechaObservacion;
    }

    public Boolean getAceptada() {
        return aceptada;
    }

    public void setAceptada(Boolean aceptada) {
        this.aceptada = aceptada;
    }

    public String getMotivo() {
        return motivo;
    }

    public void setMotivo(String motivo) {
        this.motivo = motivo;
    }

    public Date getFechaRespuesta() {
        return fechaRespuesta;
    }

    public void setFechaRespuesta(Date fechaRespuesta) {
        this.fechaRespuesta = fechaRespuesta;
    }

    public List<Implicacion> getImplicacionList() {
        return implicacionList;
    }

    public void setImplicacionList(List<Implicacion> implicacionList) {
        this.implicacionList = implicacionList;
    }

    public Usuario getUsuarioReporta() {
        return usuarioReporta;
    }

    public void setUsuarioReporta(Usuario usuarioReporta) {
        this.usuarioReporta = usuarioReporta;
    }

    public Usuario getUsuarioRevisa() {
        return usuarioRevisa;
    }

    public void setUsuarioRevisa(Usuario usuarioRevisa) {
        this.usuarioRevisa = usuarioRevisa;
    }

    public Area getArea() {
        return area;
    }

    public void setArea(Area area) {
        this.area = area;
    }

    public NivelRiesgo getNivelRiesgo() {
        return nivelRiesgo;
    }

    public void setNivelRiesgo(NivelRiesgo nivelRiesgo) {
        this.nivelRiesgo = nivelRiesgo;
    }

    public Tarjeta getTarjeta() {
        return tarjeta;
    }

    public void setTarjeta(Tarjeta tarjeta) {
        this.tarjeta = tarjeta;
    }

    public List<CausaRaiz> getCausaRaizList() {
        return causaRaizList;
    }

    public void setCausaRaizList(List<CausaRaiz> causaRaizList) {
        this.causaRaizList = causaRaizList;
    }

    public List<CausaRaiz> getCausaRaizAprobadaList() {
        return causaRaizAprobadaList;
    }

    public void setCausaRaizAprobadaList(List<CausaRaiz> causaRaizAprobadaList) {
        this.causaRaizAprobadaList = causaRaizAprobadaList;
    }

    public List<Documento> getDocumentoList() {
        return documentoList;
    }

    public void setDocumentoList(List<Documento> documentoList) {
        this.documentoList = documentoList;
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
        if (!(object instanceof Observacion)) {
            return false;
        }
        Observacion other = (Observacion) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "co.sigess.entities.auc.Observacion[ id=" + id + " ]";
    }

    /**
     * @return the personasabordadas
     */
    public String getPersonasabordadas() {
        return personasabordadas;
    }

    /**
     * @return the personasobservadas
     */
    public String getPersonasobservadas() {
        return personasobservadas;
    }

    /**
     * @param personasabordadas the personasabordadas to set
     */
    public void setPersonasabordadas(String personasabordadas) {
        this.personasabordadas = personasabordadas;
    }

    /**
     * @param personasobservadas the personasobservadas to set
     */
    public void setPersonasobservadas(String personasobservadas) {
        this.personasobservadas = personasobservadas;
    }

}
