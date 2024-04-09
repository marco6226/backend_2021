/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.sigess.entities.emp;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
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
@Table(name = "contexto_organizacion", schema = "emp")
@NamedQueries({
    @NamedQuery(name = "ContextoOrganizacion.findAll", query = "SELECT c FROM ContextoOrganizacion c")})
public class ContextoOrganizacion implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @SequenceGenerator(name = "contexto_organizacion_id_seq", schema = "emp", sequenceName = "contexto_organizacion_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "contexto_organizacion_id_seq")
    @NotNull
    @Column(name = "id")
    private Integer id;

    @Column(name = "version")
    private Integer version;

    @Column(name = "data")
    private String data;

    @Column(name = "fecha_modificacion")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaModificacion;

    @Column(name = "fecha_elaboracion", updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaElaboracion;

    @JsonIgnore
    @JoinColumn(name = "fk_empresa_id", referencedColumnName = "id", updatable = false)
    @ManyToOne(optional = false)
    private Empresa empresa;

    @Column(name = "fk_usuario_elabora_id", updatable = false)
    private Integer usuarioElaboraId;

    @Column(name = "fk_usuario_modifica_id")
    private Integer usuarioModificaId;

    public ContextoOrganizacion() {
    }

    public ContextoOrganizacion(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public Date getFechaModificacion() {
        return fechaModificacion;
    }

    public void setFechaModificacion(Date fechaModificacion) {
        this.fechaModificacion = fechaModificacion;
    }

    public Date getFechaElaboracion() {
        return fechaElaboracion;
    }

    public void setFechaElaboracion(Date fechaElaboracion) {
        this.fechaElaboracion = fechaElaboracion;
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

    public Empresa getEmpresa() {
        return empresa;
    }

    public void setEmpresa(Empresa empresa) {
        this.empresa = empresa;
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
        if (!(object instanceof ContextoOrganizacion)) {
            return false;
        }
        ContextoOrganizacion other = (ContextoOrganizacion) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "co.sigess.entities.emp.ContextoOrganizacion[ id=" + id + " ]";
    }

}
