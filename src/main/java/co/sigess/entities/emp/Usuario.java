/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.sigess.entities.emp;

import co.sigess.util.Util;
import co.sigess.util.converter.StringArrayListConverter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.ws.rs.NotAuthorizedException;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author fmoreno
 */
@Entity
@JsonIgnoreProperties(ignoreUnknown = true)
@Table(name = "usuario", schema = "emp")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Usuario.findAll", query = "SELECT u FROM Usuario u")})
public class Usuario implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Basic(optional = false)
    @NotNull
    @SequenceGenerator(name = "usuario_id_seq", schema = "emp", sequenceName = "usuario_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "usuario_id_seq")
    @Column(name = "id")
    private Integer id;

    // @Pattern(regexp="[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?", message="Invalid email")//if the field contains email address consider using this annotation to enforce field validation
    @Basic(optional = false)
    @NotNull(message = "El campo email es requerido")
    @Size(min = 1, max = 100)
    @Column(name = "email")
    private String email;

    @JsonIgnore
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "password")
    private String password;

    @JsonIgnore
    @Basic(optional = false)
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "expira_password")
    private Date expiraPassword;

    @JsonIgnore
    @Basic(optional = false)
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "ultimo_login")
    private Date ultimoLogin;
    
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "fecha_creacion")
    private Date fechaCreacion;
    
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "fecha_modificacion")
    private Date fechaModificacion;

    @Basic(optional = false)
    @NotNull(message = "El campo estado es requerido")
    @Column(name = "estado")
    @Enumerated(EnumType.STRING)
    private EstadoUsuario estado;
    
    @Column(name = "avatar")
    private String avatar;
    
    @Column(name = "icon")
    private String icon;

    @OneToMany(mappedBy = "usuario")
    private List<UsuarioEmpresa> usuarioEmpresaList;
    
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "fecha_acepta_terminos")
    private Date fechaAceptaTerminos;

    @Convert(converter = StringArrayListConverter.class)
    //@JsonIgnore
    @Column(name = "ip_permitida")
    private List<String> ipPermitida;
        
    @Column(name = "mfa")
    private Boolean mfa;
    
    @Column(name = "numero_movil")
    private String numeroMovil;
    
    @Transient
    public Integer codigo;

    public Usuario() {
    }

    public Usuario(Integer id) {
        this.id = id;
    }

    public Usuario(Integer id, String email, String estado) {
        this.id = id;
        this.email = email;
        this.estado = EstadoUsuario.valueOf(estado);
    }

    public Usuario(Integer id, String email, EstadoUsuario estado) {
        this.id = id;
        this.email = email;
        this.estado = estado;
    }
    
    public Usuario(Integer id, String email, String estado, String avatar, String icon, Date fechaAceptaTerminos) {
        this.id = id;
        this.email = email;
        this.estado = EstadoUsuario.valueOf(estado);
        this.avatar = avatar;
        this.icon = icon;
        this.fechaAceptaTerminos = fechaAceptaTerminos;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Date getExpiraPassword() {
        return expiraPassword;
    }

    public void setExpiraPassword(Date expiraPassword) {
        this.expiraPassword = expiraPassword;
    }

    public Date getUltimoLogin() {
        return ultimoLogin;
    }

    public void setUltimoLogin(Date ultimoLogin) {
        this.ultimoLogin = ultimoLogin;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public Date getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(Date fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public Date getFechaModificacion() {
        return fechaModificacion;
    }

    public void setFechaModificacion(Date fechaModificacion) {
        this.fechaModificacion = fechaModificacion;
    }

    public Date getFechaAceptaTerminos() {
        return fechaAceptaTerminos;
    }

    public void setFechaAceptaTerminos(Date fechaAceptaTerminos) {
        this.fechaAceptaTerminos = fechaAceptaTerminos;
    }

    public List<String> getIpPermitida() {
        return ipPermitida;
    }

    public void setIpPermitida(List<String> ipPermitida) {
        this.ipPermitida = ipPermitida;
    }

    public Boolean getMfa() {
        return mfa;
    }

    public void setMfa(Boolean mfa) {
        this.mfa = mfa;
    }

    public String getNumeroMovil() {
        return numeroMovil;
    }

    public void setNumeroMovil(String numeroMovil) {
        this.numeroMovil = numeroMovil;
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
        if (!(object instanceof Usuario)) {
            return false;
        }
        Usuario other = (Usuario) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "co.sigess.entities.empUsuario[ id=" + id + " ]";
    }

    @Deprecated
    @JsonIgnore
    public String getAsJSON() {
        try {
            ObjectMapper mapper = new ObjectMapper();
            String avatar = this.avatar;
            String icon = this.icon;
            this.avatar = null;
            this.icon = null;
            String json = mapper.writeValueAsString(this);
            this.avatar = avatar;
            this.icon = icon;
            return json;
        } catch (JsonProcessingException ex) {
            Logger.getLogger(Usuario.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
    @Deprecated
    @JsonIgnore
    public static Usuario jsonToUser(String data) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            mapper.enable(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT);
            return mapper.readValue(data, new TypeReference<Usuario>() {
            });
        } catch (JsonProcessingException ex) {
            Logger.getLogger(Usuario.class.getName()).log(Level.SEVERE, null, ex);
            throw new NotAuthorizedException("No Authorization");
        } catch (IOException ex) {
            Logger.getLogger(Usuario.class.getName()).log(Level.SEVERE, null, ex);
            throw new NotAuthorizedException("No Authorization");
        }
    }

    public EstadoUsuario getEstado() {
        return estado;
    }

    public void setEstado(EstadoUsuario estado) {
        this.estado = estado;
    }

    @JsonIgnore
    public List<UsuarioEmpresa> getUsuarioEmpresaList() {
        return usuarioEmpresaList;
    }

    @JsonProperty(value = "usuarioEmpresaList")
    public void setUsuarioEmpresaList(List<UsuarioEmpresa> usuarioEmpresaList) {
        this.usuarioEmpresaList = usuarioEmpresaList;
    }

}
