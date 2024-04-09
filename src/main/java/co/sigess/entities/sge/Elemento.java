/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.sigess.entities.sge;

import co.sigess.entities.ado.Documento;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author fmoreno
 */
@Entity
@JsonIgnoreProperties
@Table(name = "elemento", schema = "sge")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Elemento.findAll", query = "SELECT e FROM Elemento e")})
public class Elemento implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @SequenceGenerator(name = "elemento_id_seq", schema = "sge", sequenceName = "elemento_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "elemento_id_seq")
    @Column(name = "id")
    private Integer id;

    @Size(max = 255)
    @Column(name = "nombre")
    private String nombre;

    @Size(max = 45)
    @Column(name = "codigo")
    private String codigo;

    @Size(max = 512)
    @Column(name = "marco_legal")
    private String marcoLegal;

    @Size(max = 2048)
    @Column(name = "criterio")
    private String criterio;

    @Size(max = 2048)
    @Column(name = "modo_verificacion")
    private String modoVerificacion;

    @OrderBy(value = "id")
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "elementoPadre")
    private List<Elemento> elementoList;
    
    @JoinColumn(name = "fk_elemento_padre_id", referencedColumnName = "id")
    @ManyToOne
    private Elemento elementoPadre;

    @JoinColumns({
        @JoinColumn(name = "fk_sistema_gestion_id", referencedColumnName = "id")
        , @JoinColumn(name = "fk_sistema_gestion_version", referencedColumnName = "version")})
    @ManyToOne
    private SistemaGestion sistemaGestion;

    @OrderBy(value = "id")
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "elemento")
    private List<OpcionRespuesta> opcionRespuestaList;
    
    @JoinTable(name = "elemento_documento", schema = "sge", joinColumns = {
        @JoinColumn(name = "fk_elemento_id", referencedColumnName = "id")}, inverseJoinColumns = {
        @JoinColumn(name = "fk_documento_id", referencedColumnName = "id")})
    @ManyToMany
    private List<Documento> documentosList;

    public Elemento() {
    }

    public Elemento(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getMarcoLegal() {
        return marcoLegal;
    }

    public void setMarcoLegal(String marcoLegal) {
        this.marcoLegal = marcoLegal;
    }

    public String getCriterio() {
        return criterio;
    }

    public void setCriterio(String criterio) {
        this.criterio = criterio;
    }

    public String getModoVerificacion() {
        return modoVerificacion;
    }

    public void setModoVerificacion(String modoVerificacion) {
        this.modoVerificacion = modoVerificacion;
    }

    public List<Elemento> getElementoList() {
        return elementoList;
    }

    public void setElementoList(List<Elemento> elementoList) {
        this.elementoList = elementoList;
    }

    @XmlTransient
    @JsonIgnore
    public Elemento getElementoPadre() {
        return elementoPadre;
    }

    @JsonProperty("elementoPadre")
    public void setElementoPadre(Elemento elementoPadre) {
        this.elementoPadre = elementoPadre;
    }

    @JsonIgnore
    public SistemaGestion getSistemaGestion() {
        return sistemaGestion;
    }

    public void setSistemaGestion(SistemaGestion sistemaGestion) {
        this.sistemaGestion = sistemaGestion;
    }
    
    public List<OpcionRespuesta> getOpcionRespuestaList() {
        return opcionRespuestaList;
    }

    public void setOpcionRespuestaList(List<OpcionRespuesta> opcionRespuestaList) {
        this.opcionRespuestaList = opcionRespuestaList;
    }

    public List<Documento> getDocumentosList() {
        return documentosList;
    }

    public void setDocumentosList(List<Documento> documentosList) {
        this.documentosList = documentosList;
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
        if (!(object instanceof Elemento)) {
            return false;
        }
        Elemento other = (Elemento) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "co.sigess.entities.sge.Elemento[ id=" + id + " ]";
    }

}
