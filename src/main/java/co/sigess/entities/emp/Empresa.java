/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.sigess.entities.emp;

import co.sigess.entities.com.Arl;
import co.sigess.entities.com.Ciiu;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.Serializable;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
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
import javax.persistence.OneToMany;
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
@Table(name = "empresa", schema = "emp")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Empresa.findAll", query = "SELECT e FROM Empresa e")})
public class Empresa implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Basic(optional = false)
    @NotNull
    @SequenceGenerator(name = "empresa_id_seq", schema = "emp", sequenceName = "empresa_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "empresa_id_seq")
    @Column(name = "id")
    private Integer id;

    @Size(max = 45)
    @Column(name = "nit")
    private String nit;
    
     @Size(max = 45)
    @Column(name = "direccion")
    private String direccion;
     
      @Size(max = 45)
    @Column(name = "telefono")
    private String telefono;
      
       @Size(max = 45)
    @Column(name = "email")
    private String email;
       
    @Size(max = 45)
    @Column(name = "web")
    private String web;
    
    
    @Column(name = "numero_sedes")
    private Integer numeroSedes;

    @Column(name = "activo")
    private Boolean activo;

    @Size(max = 100)
    @Column(name = "nombre_comercial")
    private String nombreComercial;

    @Size(max = 100)
    @Column(name = "razon_social")
    private String razonSocial;
    
    @Column(name = "logo")
    private String logo;

    @Column(name = "tipo_persona")
    private String tipo_persona;

    @Column(name = "actividades_contratadas")
    private String actividades_contratadas;

    @Column(name = "localidad")
    private String localidad;

    @Column(name = "division")
    private String division;    

    @JoinColumn(name = "fk_arl_id", referencedColumnName = "id")
    @ManyToOne
    private Arl arl;

    @JoinColumn(name = "fk_grupo_empresarial_id", referencedColumnName = "id")
    @ManyToOne
    private GrupoEmpresarial grupoEmpresarial;

    @OneToMany(mappedBy = "empresa")
    private List<UsuarioEmpresa> usuarioEmpresaList;

    @JsonIgnore
    @JoinTable(name = "contratista_empresa", schema = "emp", joinColumns = {
        @JoinColumn(name = "fk_empresa_id", referencedColumnName = "id")}, inverseJoinColumns = {
        @JoinColumn(name = "fk_empresa_contratista_id", referencedColumnName = "id")})
    @ManyToMany
    private List<Empresa> empresasContratistasList;

    @JoinColumn(name = "fk_ciiu_id", referencedColumnName = "id")
    @ManyToOne
    private Ciiu ciiu;

    /**
     * @return the direccion
     */
    public String getDireccion() {
        return direccion;
    }

    /**
     * @param direccion the direccion to set
     */
    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    /**
     * @return the telefono
     */
    public String getTelefono() {
        return telefono;
    }

    /**
     * @param telefono the telefono to set
     */
    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    /**
     * @return the email
     */
    public String getEmail() {
        return email;
    }

    /**
     * @param email the email to set
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * @return the web
     */
    public String getWeb() {
        return web;
    }

    /**
     * @param web the web to set
     */
    public void setWeb(String web) {
        this.web = web;
    }

 

    public Empresa() {
    }
    
    @JsonIgnore
    public String getAsJSON() {
        try {
            ObjectMapper mapper = new ObjectMapper();
            return mapper.writeValueAsString(this);
        } catch (JsonProcessingException ex) {
            Logger.getLogger(Usuario.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    public Empresa(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNit() {
        return nit;
    }

    public void setNit(String nit) {
        this.nit = nit;
    }

    public Boolean getActivo() {
        return activo;
    }

    public void setActivo(Boolean activo) {
        this.activo = activo;
    }

    public String getNombreComercial() {
        return nombreComercial;
    }

    public void setNombreComercial(String nombreComercial) {
        this.nombreComercial = nombreComercial;
    }

    public String getRazonSocial() {
        return razonSocial;
    }

    public void setRazonSocial(String razonSocial) {
        this.razonSocial = razonSocial;
    }

    public Arl getArl() {
        return arl;
    }

    public void setArl(Arl arl) {
        this.arl = arl;
    }

    public GrupoEmpresarial getGrupoEmpresarial() {
        return grupoEmpresarial;
    }

    public void setGrupoEmpresarial(GrupoEmpresarial grupoEmpresarial) {
        this.grupoEmpresarial = grupoEmpresarial;
    }

    public List<Empresa> getEmpresasContratistasList() {
        return empresasContratistasList;
    }

    public void setEmpresasContratistasList(List<Empresa> empresasContratistasList) {
        this.empresasContratistasList = empresasContratistasList;
    }

    public Ciiu getCiiu() {
        return ciiu;
    }

    public void setCiiu(Ciiu ciiu) {
        this.ciiu = ciiu;
    }

    public Integer getNumeroSedes() {
        return numeroSedes;
    }

    /**
     * @param numeroSedes the numeroSedes to set
     */
    public void setNumeroSedes(Integer numeroSedes) {
        this.numeroSedes = numeroSedes;
    }
    
    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

public String getTipo_persona() {
        return tipo_persona;
    }

    public void setTipo_persona(String tipo_persona) {
        this.tipo_persona = tipo_persona;
    }

    public String getActividades_contratadas() {
        return actividades_contratadas;
    }

    public void setActividades_contratadas(String actividades_contratadas) {
        this.actividades_contratadas = actividades_contratadas;
    }

    public String getLocalidad() {
        return localidad;
    }

    public void setLocalidad(String localidad) {
        this.localidad = localidad;
    }

    public String getDivision() {
        return division;
    }

    public void setDivision(String division) {
        this.division = division;
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
        if (!(object instanceof Empresa)) {
            return false;
        }
        Empresa other = (Empresa) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "co.sigess.entities.empEmpresa[ id=" + id + " ]";
    }

    @XmlTransient
    public List<UsuarioEmpresa> getUsuarioEmpresaList() {
        return usuarioEmpresaList;
    }

    public void setUsuarioEmpresaList(List<UsuarioEmpresa> usuarioEmpresaList) {
        this.usuarioEmpresaList = usuarioEmpresaList;
    }

//    @XmlTransient
//    public List<Perfil> getPerfilList() {
//        return perfilList;
//    }
//
//    public void setPerfilList(List<Perfil> perfilList) {
//        this.perfilList = perfilList;
//    }


    
}
