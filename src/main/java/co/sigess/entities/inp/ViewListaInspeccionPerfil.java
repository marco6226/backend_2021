/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.sigess.entities.inp;

import co.sigess.entities.conf.Formulario;
import co.sigess.entities.emp.Empresa;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Usuario
 */
@Entity
@JsonIgnoreProperties(ignoreUnknown = true)
@Table(name = "vw_lista_inspecciones_to_perfil", schema = "inp")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ViewListaInspeccionPerfil.findAll", query = "SELECT l FROM ViewListaInspeccionPerfil l")})
public class ViewListaInspeccionPerfil implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected ListaInspeccionPK listaInspeccionPK;

    private String nombre;
    private String codigo;
    private String descripcion;

    @Column(name = "numero_preguntas")
    private Integer numeroPreguntas;
    
    @JoinColumn(name = "fk_empresa_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Empresa empresa;
    
    @JoinColumn(name = "fk_formulario_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Formulario formulario;
    
    @Column(name = "tipo_lista")
    private String tipoLista;
    
    
    @Column(name = "usar_nivel_riesgo")
    private Boolean usarNivelRiesgo;
    
    @Column(name = "usar_tipo_hallazgo")
    private Boolean usarTipoHallazgo;
    
    @Column(name = "fk_perfil_id")
    private String fkPerfilId;
    private String estado;
    
    @Column(name = "pk_usuario_id")
    private Integer pkUsuarioId;
    
    @Column(name = "division_sv")
    private Integer divisionSv;
       
    @Column(name = "localidad_sv")
    private Integer localidadSv;
       
  
    public ViewListaInspeccionPerfil() {
    }

    public ViewListaInspeccionPerfil(ListaInspeccionPK listaInspeccionPK, String nombre, String codigo, String descripcion, Integer numeroPreguntas, Integer divisionSv, Integer localidadSv) {
        this.listaInspeccionPK = listaInspeccionPK;
        this.nombre = nombre;
        this.codigo = codigo;
        this.descripcion = descripcion;
        this.numeroPreguntas = numeroPreguntas;
        this.divisionSv = divisionSv;
        this.localidadSv = localidadSv;
    }

    public ViewListaInspeccionPerfil(ListaInspeccionPK listaInspeccionPK) {
        this.listaInspeccionPK = listaInspeccionPK;
    }

    public ViewListaInspeccionPerfil(int id, int version) {
        this.listaInspeccionPK = new ListaInspeccionPK(id, version);
    }

    public ListaInspeccionPK getListaInspeccionPK() {
        return listaInspeccionPK;
    }

    public void setListaInspeccionPK(ListaInspeccionPK listaInspeccionPK) {
        this.listaInspeccionPK = listaInspeccionPK;
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

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Integer getNumeroPreguntas() {
        return numeroPreguntas;
    }

    public void setNumeroPreguntas(Integer numeroPreguntas) {
        this.numeroPreguntas = numeroPreguntas;
    }

    public Empresa getEmpresa() {
        return empresa;
    }

    public void setEmpresa(Empresa empresa) {
        this.empresa = empresa;
    }

    public Formulario getFormulario() {
        return formulario;
    }

    public void setFormulario(Formulario formulario) {
        this.formulario = formulario;
    }

    public String getTipoLista() {
        return tipoLista;
    }

    public void setTipoLista(String tipoLista) {
        this.tipoLista = tipoLista;
    }

    public Boolean getUsarNivelRiesgo() {
        return usarNivelRiesgo;
    }

    public void setUsarNivelRiesgo(Boolean usarNivelRiesgo) {
        this.usarNivelRiesgo = usarNivelRiesgo;
    }

    public Boolean getUsarTipoHallazgo() {
        return usarTipoHallazgo;
    }

    public void setUsarTipoHallazgo(Boolean usarTipoHallazgo) {
        this.usarTipoHallazgo = usarTipoHallazgo;
    }

    public String getFkPerfilId() {
        return fkPerfilId;
    }

    public void setFkPerfilId(String fkPerfilId) {
        this.fkPerfilId = fkPerfilId;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public Integer getPkUsuarioId() {
        return pkUsuarioId;
    }

    public void setPkUsuarioId(Integer pkUsuarioId) {
        this.pkUsuarioId = pkUsuarioId;
    } 
    
      public Integer getDivisionSv() {
        return divisionSv;
    }

    public void setDivisionSv(Integer divisionSv) {
        this.divisionSv = divisionSv;
    }

    public Integer getLocalidadSv() {
        return localidadSv;
    }

    public void setLocalidadSv(Integer localidadSv) {
        this.localidadSv = localidadSv;
    }

    
        @Override
    public int hashCode() {
        int hash = 0;
        hash += (listaInspeccionPK != null ? listaInspeccionPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ListaInspeccion)) {
            return false;
        }
        ListaInspeccion other = (ListaInspeccion) object;
        if ((this.listaInspeccionPK == null && other.listaInspeccionPK != null) || (this.listaInspeccionPK != null && !this.listaInspeccionPK.equals(other.listaInspeccionPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "co.sigess.entities.inp.ListaInspeccion[ listaInspeccionPK=" + listaInspeccionPK + " ]";
    }
}
