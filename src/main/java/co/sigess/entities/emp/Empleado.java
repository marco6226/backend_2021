/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.sigess.entities.emp;

import co.sigess.entities.ado.Documento;
import co.sigess.entities.com.Eps;
import co.sigess.entities.com.Afp;
import co.sigess.entities.com.Ccf;
import co.sigess.entities.com.Ciudad;
import co.sigess.entities.com.TipoIdentificacion;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
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
import javax.persistence.OrderBy;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author fmoreno
 */
@Entity
@JsonIgnoreProperties(ignoreUnknown = true)
@Table(name = "empleado", schema = "emp")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Empleado.findAll", query = "SELECT e FROM Empleado e")})
public class Empleado implements Serializable {


    private static final long serialVersionUID = 1L;

    @Id
    @Basic(optional = false) 
    @NotNull
    @SequenceGenerator(name = "empleado_id_seq", schema = "emp", sequenceName = "empleado_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "empleado_id_seq")
    @Column(name = "id")
    private Integer id;

    @Size(max = 10)
    @Column(name = "codigo")
    private String codigo;

    @Size(max = 10)
    @Column(name = "zona_residencia")
    private String zonaResidencia;

    @Size(max = 45)
    @Column(name = "direccion")
    private String direccion;

    @Column(name = "fecha_ingreso")
    @Temporal(TemporalType.DATE)
    private Date fechaIngreso;

    @Column(name = "fecha_nacimiento")
    @Temporal(TemporalType.DATE)
    private Date fechaNacimiento;

    @Size(max = 10)
    @Column(name = "genero")
    private String genero;

    @Column(name = "tipo_identificacion")
    @Enumerated(EnumType.STRING)
    private TipoIdentificacion tipoIdentificacion;

    @Size(max = 45)
    @Column(name = "numero_identificacion")
    private String numeroIdentificacion;

    @Size(max = 45)
    @Column(name = "primer_apellido")
    private String primerApellido;

    @Size(max = 45)
    @Column(name = "primer_nombre")
    private String primerNombre;

    @Size(max = 45)
    @Column(name = "emergency_contact")
    private String phoneEmergencyContact;
    
    @Size(max = 45)
    @Column(name = "corporative_phone")
    private String corporativePhone;
    
    @Size(max = 45)
    @Column(name = "email_emergency_contact")
    private String emailEmergencyContact;

    @Size(max = 45)
    @Column(name = "phone_emergency_contact")
    private String emergencyContact;
    
    @Size(max = 45)
    @Column(name = "segundo_apellido")
    private String segundoApellido;

    @Size(max = 45)
    @Column(name = "segundo_nombre")
    private String segundoNombre;

    @Size(max = 20)
    @Column(name = "telefono1")
    private String telefono1;

    @Size(max = 20)
    @Column(name = "telefono2")
    private String telefono2;

    @Size(max = 255)
    @Column(name = "direccion_gerencia")
    private String direccionGerencia;

    
    @Size(max = 45)
    @Column(name = "regional")
    private String regional;
    
    @Size(max = 20)
    @Column(name = "correo_personal")
    private String correoPersonal;

    @Size(max = 45)
    @Column(name = "ciudad_gerencia")
    private String ciudadGerencia;

    @Size(max = 20)
    @Column(name = "tipo_vinculacion")
    private String tipoVinculacion;
    
    @Column(name = "empresa")
    private String empresa;
    
    @Column(name = "nit")
    private String nit;

    @JoinColumn(name = "fk_eps_id", referencedColumnName = "id")
    @ManyToOne(optional = true)
    private Eps eps;

    @JoinColumn(name = "fk_afp_id", referencedColumnName = "id")
    @ManyToOne(optional = true)
    private Afp afp;

    @JoinColumn(name = "fk_area_id", referencedColumnName = "id")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Area area;

    @JsonIgnoreProperties({"jefeInmediato","businessPartner"})
    @JoinColumn(name = "fk_jefe_inmediato", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Empleado jefeInmediato;

    
    @JsonIgnoreProperties({"jefeInmediato","businessPartner"})
    @JoinColumn(name = "fk_bussiness_partner", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Empleado businessPartner;

    
    @JoinColumn(name = "fk_cargo_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Cargo cargo;

    @JoinColumn(name = "fk_usuario_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Usuario usuario;

    @JoinColumn(name = "fk_ciudad_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Ciudad ciudad;

    @JoinColumn(name = "fk_ccf_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Ccf ccf;

    @OneToMany(mappedBy = "empleado")
    private List<ConfiguracionJornada> configuracionJornadaList;

    @OrderBy(value = "fecha DESC")
    @OneToMany(mappedBy = "empleado")
    private List<HorasExtra> horasExtraList;

    @JoinTable(name = "documento_empleado", schema = "emp", joinColumns = {
        @JoinColumn(name = "fk_empleado_id", referencedColumnName = "id")}, inverseJoinColumns = {
        @JoinColumn(name = "fk_documento_id", referencedColumnName = "id")})
    @ManyToMany
    private List<Documento> documentosList;
    
    
    @Column(name = "estado")
    @Enumerated(EnumType.STRING)
    private EstadoUsuario estado;

    public Empleado() {
    }

    public Empleado(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getZonaResidencia() {
        return zonaResidencia;
    }

    public void setZonaResidencia(String zonaResidencia) {
        this.zonaResidencia = zonaResidencia;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public Date getFechaIngreso() {
        return fechaIngreso;
    }

    public void setFechaIngreso(Date fechaIngreso) {
        this.fechaIngreso = fechaIngreso;
    }

    public Date getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(Date fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    /**
     * @return the regional
     */
    public String getRegional() {
        return regional;
    }

    /**
     * @param regional the regional to set
     */
    public void setRegional(String regional) {
        this.regional = regional;
    }

    /**
     * @return the ciudadGerencia
     */
    public String getCiudadGerencia() {
        return ciudadGerencia;
    }

    /**
     * @param ciudadGerencia the ciudadGerencia to set
     */
    public void setCiudadGerencia(String ciudadGerencia) {
        this.ciudadGerencia = ciudadGerencia;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public TipoIdentificacion getTipoIdentificacion() {
        return tipoIdentificacion;
    }

    /**
     * @return the businessPartner
     */
    public Empleado getBusinessPartner() {
        return businessPartner;
    }

    /**
     * @param businessPartner the businessPartner to set
     */
    public void setBusinessPartner(Empleado businessPartner) {
        this.businessPartner = businessPartner;
    }

    /**
     * @return the direccionGerencia
     */
    public String getDireccionGerencia() {
        return direccionGerencia;
    }

    /**
     * @param direccionGerencia the direccionGerencia to set
     */
    public void setDireccionGerencia(String direccionGerencia) {
        this.direccionGerencia = direccionGerencia;
    }

    /**
     * @return the correoPersonal
     */
    public String getCorreoPersonal() {
        return correoPersonal;
    }

    /**
     * @param correoPersonal the correoPersonal to set
     */
    public void setCorreoPersonal(String correoPersonal) {
        this.correoPersonal = correoPersonal;
    }

    
   

    public void setTipoIdentificacion(TipoIdentificacion tipoIdentificacion) {
        this.tipoIdentificacion = tipoIdentificacion;
    }

    public String getNumeroIdentificacion() {
        return numeroIdentificacion;
    }

    public void setNumeroIdentificacion(String numeroIdentificacion) {
        this.numeroIdentificacion = numeroIdentificacion;
    }

    public String getPrimerApellido() {
        return primerApellido;
    }

    /**
     * @return the jefeInmediato
     */
    public Empleado getJefeInmediato() {
        return jefeInmediato;
    }

    /**
     * @param jefeInmediato the jefeInmediato to set
     */
    public void setJefeInmediato(Empleado jefeInmediato) {
        this.jefeInmediato = jefeInmediato;
    }

    public void setPrimerApellido(String primerApellido) {
        this.primerApellido = primerApellido;
    }

    public String getPrimerNombre() {
        return primerNombre;
    }

    public void setPrimerNombre(String primerNombre) {
        this.primerNombre = primerNombre;
    }

    public String getSegundoApellido() {
        return segundoApellido;
    }

    public void setSegundoApellido(String segundoApellido) {
        this.segundoApellido = segundoApellido;
    }

    public String getSegundoNombre() {
        return segundoNombre;
    }

    public void setSegundoNombre(String segundoNombre) {
        this.segundoNombre = segundoNombre;
    }

    public String getTelefono1() {
        return telefono1;
    }

    public void setTelefono1(String telefono1) {
        this.telefono1 = telefono1;
    }

    public String getTelefono2() {
        return telefono2;
    }

    public void setTelefono2(String telefono2) {
        this.telefono2 = telefono2;
    }

    public String getTipoVinculacion() {
        return tipoVinculacion;
    }

    public void setTipoVinculacion(String tipoVinculacion) {
        this.tipoVinculacion = tipoVinculacion;
    }

    public Eps getEps() {
        return eps;
    }

    public void setEps(Eps eps) {
        this.eps = eps;
    }

    public Afp getAfp() {
        return afp;
    }

    public void setAfp(Afp afp) {
        this.afp = afp;
    }

    public Area getArea() {
        return area;
    }

    public void setArea(Area area) {
        this.area = area;
    }

    public Cargo getCargo() {
        return cargo;
    }

    public void setCargo(Cargo cargo) {
        this.cargo = cargo;
    }

    public Ciudad getCiudad() {
        return ciudad;
    }

    public void setCiudad(Ciudad ciudad) {
        this.ciudad = ciudad;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Ccf getCcf() {
        return ccf;
    }

    public void setCcf(Ccf ccf) {
        this.ccf = ccf;
    }

    public List<ConfiguracionJornada> getConfiguracionJornadaList() {
        return configuracionJornadaList;
    }

    public void setConfiguracionJornadaList(List<ConfiguracionJornada> configuracionJornadaList) {
        this.configuracionJornadaList = configuracionJornadaList;
    }

    public List<HorasExtra> getHorasExtraList() {
        return horasExtraList;
    }

    public void setHorasExtraList(List<HorasExtra> horasExtraList) {
        this.horasExtraList = horasExtraList;
    }

    public List<Documento> getDocumentosList() {
        return documentosList;
    }

    public void setDocumentosList(List<Documento> documentosList) {
        this.documentosList = documentosList;
    }

    public EstadoUsuario getEstado() {
        return estado;
    }

    /**
     * @return the phoneEmergencyContact
     */
    public String getPhoneEmergencyContact() {
        return phoneEmergencyContact;
    }

    /**
     * @param phoneEmergencyContact the phoneEmergencyContact to set
     */
    public void setPhoneEmergencyContact(String phoneEmergencyContact) {
        this.phoneEmergencyContact = phoneEmergencyContact;
    }

    /**
     * @return the emailEmergencyContact
     */
    public String getEmailEmergencyContact() {
        return emailEmergencyContact;
    }

    /**
     * @param emailEmergencyContact the emailEmergencyContact to set
     */
    public void setEmailEmergencyContact(String emailEmergencyContact) {
        this.emailEmergencyContact = emailEmergencyContact;
    }

    /**
     * @return the emergencyContact
     */
    public String getEmergencyContact() {
        return emergencyContact;
    }

    /**
     * @param emergencyContact the emergencyContact to set
     */
    public void setEmergencyContact(String emergencyContact) {
        this.emergencyContact = emergencyContact;
    }
    public void setEstado(EstadoUsuario estado) {
        this.estado = estado;
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
        if (!(object instanceof Empleado)) {
            return false;
        }
        Empleado other = (Empleado) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "co.sigess.entities.empEmpleado[ id=" + id + " ]";
    }

    /**
     * @return the corporativePhone
     */
    public String getCorporativePhone() {
        return corporativePhone;
    }

    /**
     * @param corporativePhone the corporativePhone to set
     */
    public void setCorporativePhone(String corporativePhone) {
        this.corporativePhone = corporativePhone;
    }

    /**
     * @return the empresa
     */
    public String getEmpresa() {
        return empresa;
    }

    /**
     * @return the nit
     */
    public String getNit() {
        return nit;
    }

    /**
     * @param empresa the empresa to set
     */
    public void setEmpresa(String empresa) {
        this.empresa = empresa;
    }

    /**
     * @param nit the nit to set
     */
    public void setNit(String nit) {
        this.nit = nit;
    }

}
