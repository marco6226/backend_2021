/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.sigess.entities.ado;

import co.sigess.entities.emp.Empresa;
import co.sigess.entities.emp.Usuario;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author fmoreno
 */
@Entity
@JsonIgnoreProperties(ignoreUnknown = true)
@Table(name = "directorio", schema = "ado")
@NamedQueries({
    @NamedQuery(name = "Directorio.findAll", query = "SELECT d FROM Directorio d")})
public class Directorio implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @SequenceGenerator(name = "directorio_id_seq", schema = "ado", sequenceName = "directorio_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "directorio_id_seq")
    @Basic(optional = false)
    @Column(name = "id")
    private Long id;

    @NotNull(message = "El campo nombre es requerido")
    @Size(max = 255, message = "Máximo 255 caracteres para el campo nombre")
    @Column(name = "nombre")
    private String nombre;

    @Basic(optional = false)
    @NotNull
    @Column(name = "es_documento")
    private boolean esDocumento;

    @Column(name = "fecha_creacion")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaCreacion;

    @NotNull(message = "El campo usuario es requerido")
    @JoinColumn(name = "fk_usuario_id", referencedColumnName = "id")
    @ManyToOne
    private Usuario usuario;

    @JsonIgnore
    @NotNull(message = "El campo empresa es requerido")
    @JoinColumn(name = "fk_empresa_id", referencedColumnName = "id")
    @ManyToOne
    private Empresa empresa;
    
    @Column(name = "fk_case_medic_id")
    private Long caseId;
    
    @JsonIgnore
    @OneToMany(mappedBy = "directorioPadre")
    private List<Directorio> directorioList;

    @JoinColumn(name = "fk_directorio_padre_id", referencedColumnName = "id")
    @ManyToOne
    private Directorio directorioPadre;
    
    @Size(max = 255, message = "Máximo 255 caracteres para el campo nivel de  Acceso")
    @Column(name = "nivel_acceso")
    private String nivelAcceso;

    @JsonIgnore
    @OneToMany(mappedBy = "directorio")
    private List<PermisoDirectorio> permisoDirectorioList;

    @OneToOne(mappedBy = "directorio")
    private Documento documento;

    @Column(name = "eliminado")
    private Boolean eliminado;
    
    @Enumerated(value = EnumType.STRING)
    @Column(name = "modulo")
    private Modulo modulo;
    
    @Column(name = "perfilid")
    private String perfilId;

    public String getPerfilId() {
        return perfilId;
    }

    public void setPerfilId(String perfilId) {
        this.perfilId = perfilId;
    }

    public Modulo getModulo() {
        return modulo;
    }

    public void setModulo(Modulo modulo) {
        this.modulo = modulo;
    }

    public Directorio() {
    }

    public Directorio(Long id) {
        this.id = id;
    }

    public long getTamanio() {
        if (this.esDocumento) {
            return documento == null ? 0L : documento.getTamanio();
        } else {
            return this.directorioList == null ? 0L : this.directorioList.size();
        }
    }

    /**
     * @return the caseId
     */
    public Long getCaseId() {
        return caseId;
    }

    /**
     * @param caseId the caseId to set
     */
    public void setCaseId(Long caseId) {
        this.caseId = caseId;
    }
    
    public String getEmailPropietario(){
        return this.usuario == null ? "" : this.usuario.getEmail();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Empresa getEmpresa() {
        return empresa;
    }

    public void setEmpresa(Empresa empresa) {
        this.empresa = empresa;
    }

    @JsonIgnore
    public Directorio getDirectorioPadre() {
        return directorioPadre;
    }

    @JsonProperty(value = "directorioPadre")
    public void setDirectorioPadre(Directorio directorioPadre) {
        this.directorioPadre = directorioPadre;
    }

    public String getNivelAcceso() {
        return nivelAcceso;
    }

    public void setNivelAcceso(String nivelAcceso) {
        this.nivelAcceso = nivelAcceso;
    }

    public boolean getEsDocumento() {
        return esDocumento;
    }

    public void setEsDocumento(boolean esDocumento) {
        this.esDocumento = esDocumento;
    }

    public List<Directorio> getDirectorioList() {
        return directorioList;
    }

    public void setDirectorioList(List<Directorio> directorioList) {
        this.directorioList = directorioList;
    }

    public Documento getDocumento() {
        return documento;
    }

    public void setDocumento(Documento documento) {
        this.documento = documento;
    }

    public List<PermisoDirectorio> getPermisoDirectorioList() {
        return permisoDirectorioList;
    }

    public void setPermisoDirectorioList(List<PermisoDirectorio> permisoDirectorioList) {
        this.permisoDirectorioList = permisoDirectorioList;
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

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Directorio)) {
            return false;
        }
        Directorio other = (Directorio) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "co.sigess.entities.ado.Directorio[ id=" + id + " ]";
    }

}
