/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.sigess.entities.sec;

import co.sigess.entities.CampoDTO;
import co.sigess.entities.ConvertidorDTO;
import co.sigess.entities.ado.Documento;
import co.sigess.entities.emp.Empresa;
import co.sigess.entities.sec.dto.AnalisisDesviacionDTO;
import co.sigess.util.converter.JsonListConverter;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;


/**
 *
 * @author fmoreno
 */
@Entity
@JsonIgnoreProperties(ignoreUnknown = true)
@Table(name = "analisis_desviacion", schema = "sec")
@XmlRootElement
public class AnalisisDesviacion implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @SequenceGenerator(name = "analisis_desviacion_id_seq", schema = "sec", sequenceName = "analisis_desviacion_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "analisis_desviacion_id_seq")
    @Basic(optional = false)
    @Column(name = "id") 
    private Integer id;

    @Size(max = 5120)
    @Column(name = "observacion")
    private String observacion;


    
    @Column(name = "participantes")
    private String participantes;

    @JoinTable(name = "analisis_desviacion_causa_raiz", schema = "sec", joinColumns = {
        @JoinColumn(name = "pk_analisis_desviacion_id", referencedColumnName = "id")}, inverseJoinColumns = {
        @JoinColumn(name = "pk_causa_raiz_id", referencedColumnName = "id")})
    @ManyToMany
    private List<CausaRaiz> causaRaizList;

    @JoinTable(name = "analisis_desviacion_causa_inmediata", schema = "sec", joinColumns = {
        @JoinColumn(name = "pk_analisis_desviacion_id", referencedColumnName = "id")}, inverseJoinColumns = {
        @JoinColumn(name = "pk_causa_inmediata_id", referencedColumnName = "id")})
    @ManyToMany
    private List<CausaInmediata> causaInmediataList;

    
    @Convert(converter = JsonListConverter.class)
    @Column(name = "causas_admin")
    private List causasAdminList;

    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "analisisDesviacionList")
    private List<TareaDesviacion> tareaDesviacionList;

    @JoinTable(name = "desviacion_analisis_desviacion", schema = "sec", joinColumns = {
        @JoinColumn(name = "fk_analisis_desviacion_id", referencedColumnName = "id")}, inverseJoinColumns = {
        @JoinColumn(name = "pk_desviacion_hash_id", referencedColumnName = "hash_id")})    
    @ManyToMany
    private List<Desviacion> desviacionesList;

    @JoinTable(name = "documento_analisis_desviacion", schema = "sec", joinColumns = {
        @JoinColumn(name = "fk_analisis_desviacion_id", referencedColumnName = "id")}, inverseJoinColumns = {
        @JoinColumn(name = "fk_documento_id", referencedColumnName = "id")})   
    @ManyToMany
    private List<Documento> documentosList;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "analisisDesviacion")
    private AnalisisCosto analisisCosto;

    @JoinColumn(name = "fk_empresa_id", referencedColumnName = "id")
    @ManyToOne
    private Empresa empresa;

    
    
    @Column(name = "fecha_elaboracion", updatable = false)
    @Temporal(value = TemporalType.TIMESTAMP)
    private Date fechaElaboracion;

    @Column(name = "fecha_modificacion")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaModificacion;

   
    @Column(name = "fk_usuario_elabora_id", updatable = false)
    private Integer usuarioElaboraId;

    
    @Column(name = "fk_usuario_modifica_id")
    private Integer usuarioModificaId;
    
    @Column(name = "gestor")
    private String gestor;
    
    @Column(name = "seguimiento")
    private String seguimiento;
    
    @Column(name = "observacion_causas")
    private String observacion_causas;
    
    @Column(name = "id_causas_select")
    private String idCausasSelect;
    
    @Column(name = "fk_matriz_peligro_id")
    private Long matrizPeligro;

    public String getIdCausasSelect() {
        return idCausasSelect;
    }

    public void setIdCausasSelect(String idCausasSelect) {
        this.idCausasSelect = idCausasSelect;
    }

    public AnalisisDesviacion() {
    }

    public AnalisisDesviacion(Integer id) {
        this.id = id;
    }

    @CampoDTO(referencia = "id")
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @CampoDTO(referencia = "observacion")
    public String getObservacion() {
        return observacion;
    }

    public void setObservacion(String observacion) {
        this.observacion = observacion;
    }

    @CampoDTO(referencia = "causaRaizList")
    public List<CausaRaiz> getCausaRaizList() {
        return causaRaizList;
    }

    public void setCausaRaizList(List<CausaRaiz> causaRaizList) {
        this.causaRaizList = causaRaizList;
    }

//    @XmlTransient
    public List<TareaDesviacion> getTareaDesviacionList() {
        return tareaDesviacionList;
    }

    public void setTareaDesviacionList(List<TareaDesviacion> tareaDesviacionList) {
        this.tareaDesviacionList = tareaDesviacionList;
    }

    @JsonProperty("tareaAsignada")
    public boolean isTareaAsignada() {
        return this.tareaDesviacionList != null && !this.tareaDesviacionList.isEmpty();
    }

    @CampoDTO(referencia = "desviacionesList")
    public List<Desviacion> getDesviacionesList() {
        return desviacionesList;
    }

    public void setDesviacionesList(List<Desviacion> desviacionesList) {
        this.desviacionesList = desviacionesList;
    }

    public Date getFechaElaboracion() {
        return fechaElaboracion;
    }

    public void setFechaElaboracion(Date fechaElaboracion) {
        this.fechaElaboracion = fechaElaboracion;
    }

    @XmlTransient
    public Empresa getEmpresa() {
        return empresa;
    }

    public void setEmpresa(Empresa empresa) {
        this.empresa = empresa;
    }

    public List getCausasAdminList() {
        return causasAdminList;
    }

    public void setCausasAdminList(List causasAdminList) {
        this.causasAdminList = causasAdminList;
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
        if (!(object instanceof AnalisisDesviacion)) {
            return false;
        }
        AnalisisDesviacion other = (AnalisisDesviacion) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "co.sigess.entities.sec.AnalisisDesviacion[ id=" + id + " ]";
    }

    public AnalisisDesviacionDTO toDTO() throws IllegalAccessException, InstantiationException, NoSuchFieldException, InvocationTargetException {
        return ConvertidorDTO.toDTO(this, AnalisisDesviacionDTO.class);
    }

    public String getParticipantes() {
        return participantes;
    }

    public void setParticipantes(String participantes) {
        this.participantes = participantes;
    }

    public Date getFechaModificacion() {
        return fechaModificacion;
    }

    public void setFechaModificacion(Date fechaModificacion) {
        this.fechaModificacion = fechaModificacion;
    }

    public List<CausaInmediata> getCausaInmediataList() {
        return causaInmediataList;
    }

    public void setCausaInmediataList(List<CausaInmediata> causaInmediataList) {
        this.causaInmediataList = causaInmediataList;
    }

    public AnalisisCosto getAnalisisCosto() {
        return analisisCosto;
    }

    public void setAnalisisCosto(AnalisisCosto analisisCosto) {
        this.analisisCosto = analisisCosto;
    }

    public List<Documento> getDocumentosList() {
        return documentosList;
    }

    public void setDocumentosList(List<Documento> documentosList) {
        this.documentosList = documentosList;
    }

    public Integer getUsuarioElaboraId() {
        return usuarioElaboraId;
    }

    public void setUsuarioElaboraId(Integer usuarioElaboraId) {
        this.usuarioElaboraId = usuarioElaboraId;
    }

    public Integer getUsuarioModificaId() {
        return usuarioModificaId;
    }

    public void setUsuarioModificaId(Integer usuarioModificaId) {
        this.usuarioModificaId = usuarioModificaId;
    }

    @Column(name = "flow_chart")
    private String flow_chart;

    public String getFlow_chart() {
        return flow_chart;
    }

    public void setFlow_chart(String flow_chart) {
        this.flow_chart = flow_chart;
    }

    @Column(name = "factor_causal")
    private String factor_causal;

    public String getFactor_causal() {
        return factor_causal;
    }

    public void setFactor_causal(String factor_causal) {
        this.factor_causal = factor_causal;
    }

    @Column(name = "incapacidades")
    private String incapacidades;

    public String getIncapacidades() {
        return incapacidades;
    }

    public void setIncapacidades(String incapacidades) {
        this.incapacidades = incapacidades;
    }

    @Column (name = "complementaria")
    private String complementaria;

    public String getComplementaria() {
        return complementaria;
    }

    public void setComplementaria(String complementaria) {
        this.complementaria = complementaria;
    }

    @Column (name = "informe")
    private String informe;

    public String getInforme() {
        return informe;
    }

    public void setInforme(String informe) {
        this.informe = informe;
    }

    @Column (name = "plan_accion")
    private String plan_accion;

    public String getPlan_accion() {
        return plan_accion;
    }

    public void setPlan_accion(String plan_accion) {
        this.plan_accion = plan_accion;
    }

    @Column (name = "miembros_equipo")
    private String miembros_equipo;

    public String getMiembros_equipo() {
        return miembros_equipo;
    }

    public void setMiembros_equipo(String miembros_equipo) {
        this.miembros_equipo = miembros_equipo;
    }

    public String getGestor() {
        return gestor;
    }

    public void setGestor(String gestor) {
        this.gestor = gestor;
    }

    public String getSeguimiento() {
        return seguimiento;
    }

    public void setSeguimiento(String seguimiento) {
        this.seguimiento = seguimiento;
    }

    public String getObservacion_causas() {
        return observacion_causas;
    }

    public void setObservacion_causas(String observacion_causas) {
        this.observacion_causas = observacion_causas;
    }

    public Long getMatrizPeligro() {
        return matrizPeligro;
    }

    public void setMatrizPeligro(Long matrizPeligro) {
        this.matrizPeligro = matrizPeligro;
    }
    
    
}
