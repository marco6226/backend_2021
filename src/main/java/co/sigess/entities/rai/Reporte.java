/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.sigess.entities.rai;

import co.sigess.entities.com.Ciudad;
import co.sigess.entities.emp.Area;
import co.sigess.entities.emp.Empresa;
import co.sigess.entities.emp.Usuario;
import com.fasterxml.jackson.annotation.JsonIgnore;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
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
import javax.persistence.OneToMany;
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
@Table(name = "reporte", schema = "rai")
@NamedQueries({
    @NamedQuery(name = "Reporte.findAll", query = "SELECT r FROM Reporte r")})
public class Reporte implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @SequenceGenerator(name = "reporte_id_seq", schema = "rai", sequenceName = "reporte_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "reporte_id_seq")
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    
    @Basic(optional = false)
    @NotNull(message = "El campo tipo no puede ser vacío")
    @Size(min = 1, max = 45, message = "El campo tipo debe contener entre 1 y 45 caracteres")
    @Column(name = "tipo")
    private String tipo;
    
    @Size(max = 100, message = "Máximo 100 caracteres para el campo nombreEps")
    @Column(name = "nombre_eps")
    private String nombreEps;
    
    @Size(max = 15, message = "Máximo 15 caracteres para el campo codigoEps")
    @Column(name = "codigo_eps")
    private String codigoEps;
    
    @Size(max = 100, message = "Máximo 100 caracteres para el campo nombre_afp")
    @Column(name = "nombre_afp")
    private String nombreAfp;
    @Size(max = 15, message = "Máximo 15 caracteres para el campo codigo_afp")
    @Column(name = "codigo_afp")
    private String codigoAfp;
    
    @Size(max = 100, message = "Máximo 100 caracteres para el campo nombre_arl")
    @Column(name = "nombre_arl")
    private String nombreArl;
    
    @Size(max = 15, message = "Máximo 15 caracteres para el campo codigo_arl")
    @Column(name = "codigo_arl")
    private String codigoArl;
    
    @Size(max = 100, message = "Máximo 100 caracteres para el campo tipoVinculador")
    @Column(name = "tipo_vinculador")
    private String tipoVinculador;
    
    @Size(max = 255, message = "Máximo 255 caracteres para el campo nombreCiiu")
    @Column(name = "nombre_ciiu")
    private String nombreCiiu;
    
    @Size(max = 15, message = "Máximo 15 caracteres para el campo codigoCiiu")
    @Column(name = "codigo_ciiu")
    private String codigoCiiu;
    
    @Size(max = 45, message = "Máximo 45 caracteres para el campo razon_social")
    @Column(name = "razon_social")
    private String razonSocial;

    @Size(max = 20, message = "Máximo 20 caracteres para el campo tipoIdentificacionEmpresa")
    @Column(name = "tipo_identificacion_empresa")
    private String tipoIdentificacionEmpresa;

    @Size(max = 45, message = "Máximo 45 caracteres para el campo identificacionEmpresa")
    @Column(name = "identificacion_empresa")
    private String identificacionEmpresa;

    @Size(max = 45, message = "Máximo 45 caracteres para el campo direccionEmpresa")
    @Column(name = "direccion_empresa")
    private String direccionEmpresa;
    
    @Size(max = 20, message = "Máximo 20 caracteres para el campo telefonoEmpresa")
    @Column(name = "telefono_empresa")
    private String telefonoEmpresa;
    
    @Size(max = 20, message = "Máximo 20 caracteres para el campo telefono2Empresa")
    @Column(name = "telefono2_empresa")
    private String telefono2Empresa;
    
    @Size(max = 45, message = "Máximo 45 caracteres para el campo emailEmpresa")
    @Column(name = "email_empresa")
    private String emailEmpresa;
    
    @Size(max = 10, message = "Máximo 10 caracteres para el campo zonaEmpresa")
    @Column(name = "zona_empresa")
    private String zonaEmpresa;
    
    @Column(name = "centr_trab_igual_sede_princ")
    private Boolean centrTrabIgualSedePrinc;
    
    @Size(max = 100, message = "Máximo 100 caracteres para el campo nombreEps")
    @Column(name = "nombre_centro_trabajo")
    private String nombreCentroTrabajo;
    
    @Size(max = 15, message = "Máximo 15 caracteres para el campo codigoCentroTrabajo")
    @Column(name = "codigo_centro_trabajo")
    private String codigoCentroTrabajo;
    
    @Size(max = 255, message = "Máximo 255 caracteres para el campo nombreCiiuCentroTrabajo")
    @Column(name = "nombre_ciiu_centro_trabajo")
    private String nombreCiiuCentroTrabajo;
    
    @Size(max = 100, message = "Máximo 100 caracteres para el campo ciiuCentroTrabajo")
    @Column(name = "ciiu_centro_trabajo")
    private String ciiuCentroTrabajo;
    
    @Size(max = 15, message = "Máximo 15 caracteres para el campo codCiiuCentroTrabajo")
    @Column(name = "cod_ciiu_centro_trabajo")
    private String codCiiuCentroTrabajo;
    @Size(max = 45, message = "Máximo 45 caracteres para el campo direccionCentroTrabajo")
    @Column(name = "direccion_centro_trabajo")
    private String direccionCentroTrabajo;
    
    @Size(max = 45, message = "Máximo 45 caracteres para el campo telefonoCentroTrabajo")
    @Column(name = "telefono_centro_trabajo")
    private String telefonoCentroTrabajo;
    
    @Size(max = 20, message = "Máximo 20 caracteres para el campo zonaCentroTrabajo")
    @Column(name = "zona_centro_trabajo")
    private String zonaCentroTrabajo;
    
    @Size(max = 45, message = "Máximo 45 caracteres para el campo tipoVinculacion")
    @Column(name = "tipo_vinculacion")
    private String tipoVinculacion;
    
    @Size(max = 15, message = "Máximo 15 caracteres para el campo codigoTipoVinculacion")
    @Column(name = "codigo_tipo_vinculacion")
    private String codigoTipoVinculacion;
    
    @Size(max = 45, message = "Máximo 45 caracteres para el campo primerApellidoEmpleado")
    @Column(name = "primer_apellido_empleado")
    private String primerApellidoEmpleado;
    
    @Size(max = 45, message = "Máximo 45 caracteres para el campo segundoApellidoEmpleado")
    @Column(name = "segundo_apellido_empleado")
    private String segundoApellidoEmpleado;
    
    @Size(max = 45, message = "Máximo 45 caracteres para el campo primerNombreEmpleado")
    @Column(name = "primer_nombre_empleado")
    private String primerNombreEmpleado;
    
    @Size(max = 45, message = "Máximo 45 caracteres para el campo segundoNombreEmpleado")
    @Column(name = "segundo_nombre_empleado")
    private String segundoNombreEmpleado;
    @Size(max = 25, message = "Máximo 25 caracteres para el campo tipoIdentificacionEmpleado")
    @Column(name = "tipo_identificacion_empleado")
    private String tipoIdentificacionEmpleado;
    
    @Size(max = 20, message = "Máximo 20 caracteres para el campo numeroIdentificacionEmpleado")
    @Column(name = "numero_identificacion_empleado")
    private String numeroIdentificacionEmpleado;
    
    @Column(name = "fecha_nacimiento_empleado")
    @Temporal(TemporalType.DATE)
    private Date fechaNacimientoEmpleado;
    
    @Size(max = 15, message = "Máximo 15 caracteres para el campo generoEmpleado")
    @Column(name = "genero_empleado")
    private String generoEmpleado;
    
    @Size(max = 45, message = "Máximo 45 caracteres para el campo direccionEmpleado")
    @Column(name = "direccion_empleado")
    private String direccionEmpleado;
    
    @Size(max = 20, message = "Máximo 20 caracteres para el campo telefonoEmpleado")
    @Column(name = "telefono_empleado")
    private String telefonoEmpleado;
    
    @Size(max = 20, message = "Máximo 20 caracteres para el campo telefono2Empleado")
    @Column(name = "telefono2_empleado")
    private String telefono2Empleado;
    
    @Column(name = "fecha_ingreso_empleado")
    @Temporal(TemporalType.DATE)
    private Date fechaIngresoEmpleado;
    
    @Size(max = 20, message = "Máximo 20 caracteres para el campo zonaEmpleado")
    @Column(name = "zona_empleado")
    private String zonaEmpleado;
    
    @Size(max = 128, message = "Máximo 128 caracteres para el campo cargoEmpleado")
    @Column(name = "cargo_empleado")
    private String cargoEmpleado;
    
    @Size(max = 100, message = "Máximo 100 caracteres para el campo ocupacionHabitual")
    @Column(name = "ocupacion_habitual")
    private String ocupacionHabitual;
    
    @Size(max = 15, message = "Máximo 15 caracteres para el campo codigoOcupacionHabitual")
    @Column(name = "codigo_ocupacion_habitual")
    private String codigoOcupacionHabitual;
    
    @Column(name = "dias_labor_habitual")
    private Short diasLaborHabitual;
    
    @Column(name = "meses_labor_habitual")
    private Short mesesLaborHabitual;
    
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "salario")
    private Double salario;
    
    @Size(max = 20, message = "Máximo 20 caracteres para el campo jornadaHabitual")
    @Column(name = "jornada_habitual")
    private String jornadaHabitual;
    
    @Column(name = "fecha_accidente")
    @Temporal(TemporalType.DATE)
    private Date fechaAccidente;
    
    @Column(name = "hora_accidente")
    @Temporal(TemporalType.TIME)
    private Date horaAccidente;
    
    @Size(max = 20, message = "Máximo 20 caracteres para el campo jornadaAccidente")
    @Column(name = "jornada_accidente")
    private String jornadaAccidente;
    
    @Column(name = "realizando_labor_habitual")
    private Boolean realizandoLaborHabitual;
    
    @Size(max = 100, message = "Máximo 100 caracteres para el campo nombreLaborHabitual")
    @Column(name = "nombre_labor_habitual")
    private String nombreLaborHabitual;
    
    @Size(max = 20, message = "Máximo 20 caracteres para el campo codigoLaborHabitual")
    @Column(name = "codigo_labor_habitual")
    private String codigoLaborHabitual;
    
    @Column(name = "hora_inicio_labor")
    @Temporal(TemporalType.TIME)
    private Date horaInicioLabor;
    
    @Size(max = 45, message = "Máximo 45 caracteres para el campo tipoAccidente")
    @Column(name = "tipo_accidente")
    private String tipoAccidente;
    
    @Column(name = "causo_muerte")
    private Boolean causoMuerte;
    
    @Size(max = 20, message = "Máximo 20 caracteres para el campo zonaAccidente")
    @Column(name = "zona_accidente")
    private String zonaAccidente;
    
    @Size(max = 45, message = "Máximo 45 caracteres para el campo lugarAccidente")
    @Column(name = "lugar_accidente")
    private String lugarAccidente;
    
    @Column(name = "hubo_testigos")
    private Boolean huboTestigos;
    
    @Column(name = "descripcion")
    private String descripcion;
    
    @Column(name = "numero_furat")
    private String numero_furat;
    
    @Size(max = 100, message = "Máximo 100 caracteres para el campo sitio")
    @Column(name = "sitio")
    private String sitio;
    
    @Size(max = 100, message = "Máximo 100 caracteres para el campo cualSitio")
    @Column(name = "cual_sitio")
    private String cualSitio;
    
    @Size(max = 100, message = "Máximo 100 caracteres para el campo tipoLesion")
    @Column(name = "tipo_lesion")
    private String tipoLesion;
    
    @Size(max = 100, message = "Máximo 100 caracteres para el campo cualTipoLesion")
    @Column(name = "cual_tipo_lesion")
    private String cualTipoLesion;
    
    @Size(max = 100, message = "Máximo 100 caracteres para el campo parteCuerpo")
    @Column(name = "parte_cuerpo")
    private String parteCuerpo;
        
    @Column(name = "agente")
    private String agente;
    
    @Size(max = 100, message = "Máximo 100 caracteres para el campo cualAgente")
    @Column(name = "cual_agente")
    private String cualAgente;
    
    @Size(max = 100, message = "Máximo 100 caracteres para el campo mecanismo")
    @Column(name = "mecanismo")
    private String mecanismo;
    
    @Size(max = 100, message = "Máximo 100 caracteres para el campo cualMecanismo")
    @Column(name = "cual_mecanismo")
    private String cualMecanismo;

    @JoinColumn(name = "fk_ciudad_empresa_id", referencedColumnName = "id")
    @ManyToOne
    private Ciudad ciudadEmpresa;

    @JoinColumn(name = "fk_ciudad_centro_trabajo_id", referencedColumnName = "id")
    @ManyToOne
    private Ciudad ciudadCentroTrabajo;

    @JoinColumn(name = "fk_ciudad_empleado_id", referencedColumnName = "id")
    @ManyToOne
    private Ciudad ciudadEmpleado;

    @JoinColumn(name = "fk_ciudad_accidente_id", referencedColumnName = "id")
    @ManyToOne
    private Ciudad ciudadAccidente;

    @JoinColumn(name = "fk_area_accidente_id", referencedColumnName = "id")
    @ManyToOne
    private Area areaAccidente;

    @NotNull
    @JoinColumn(name = "fk_usuario_reporta_id", referencedColumnName = "id")
    @ManyToOne
    private Usuario usuarioReporta;

    @NotNull
    @JoinColumn(name = "fk_empresa_id", referencedColumnName = "id")
    @ManyToOne
    private Empresa empresa;

    @OneToMany(mappedBy = "reporte")
    private List<TestigoReporte> testigoReporteList;

    @Size(max = 100, message = "Máximo 100 caracteres para el campo nombresResponsable")
    @Column(name = "nombres_responsable")
    private String nombresResponsable;
    
    @Size(max = 100, message = "Máximo 100 caracteres para el campo apellidosResponsable")
    @Column(name = "apellidos_responsable")
    private String apellidosResponsable;
    
    @Size(max = 100, message = "Máximo 100 caracteres para el campo tipoIdentificacionResponsable")
    @Column(name = "tipo_identificacion_responsable")
    private String tipoIdentificacionResponsable;
    
    @Size(max = 20, message = "Máximo 20 caracteres para el campo numeroIdentificacionResponsable")
    @Column(name = "numero_identificacion_responsable")
    private String numeroIdentificacionResponsable;
    
    @Size(max = 100, message = "Máximo 100 caracteres para el campo cargoResponsable")
    @Column(name = "cargo_responsable")
    private String cargoResponsable;
    
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "fecha_reporte")
    private Date fechaReporte;
    
    
    @Column(name = "anio_reporte")
    private Integer anioReporte;
    
    @Column(name = "nombre_area_accidente")
    private String nombreAreaAccidente;
    
    @Column(name = "email_empleado")
    private String emailEmpleado;
    
    @Column(name = "edad_empleado")
    private Integer edadEmpleado;
    
    @Column(name = "rango_edad_empleado")
    private String rangoEdadEmpleado;
    
    @Column(name = "nombre_departamento_accidente")
    private String nombreDepartamentoAccidente;
    
    @Column(name = "nombre_ciudad_accidente")
    private String nombreCiudadAccidente;
    
    @Column(name = "severidad")
    private String severidad;
    
    @Column(name = "mes_accidente")
    private String mesAccidente;
    
    @Column(name = "dia_semana")
    private String diaSemana;
    
    @Column(name = "diagnostico")
    private String diagnostico;
        
    @Column(name = "migrado")
    private boolean migrado;

    public Reporte() {
    }

    public Reporte(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getNombreEps() {
        return nombreEps;
    }

    public void setNombreEps(String nombreEps) {
        this.nombreEps = nombreEps;
    }

    public String getCodigoEps() {
        return codigoEps;
    }

    public void setCodigoEps(String codigoEps) {
        this.codigoEps = codigoEps;
    }

    public String getNombreAfp() {
        return nombreAfp;
    }

    public void setNombreAfp(String nombreAfp) {
        this.nombreAfp = nombreAfp;
    }

    public String getCodigoAfp() {
        return codigoAfp;
    }

    public void setCodigoAfp(String codigoAfp) {
        this.codigoAfp = codigoAfp;
    }

    public String getNombreArl() {
        return nombreArl;
    }

    public void setNombreArl(String nombreArl) {
        this.nombreArl = nombreArl;
    }

    public String getCodigoArl() {
        return codigoArl;
    }

    public void setCodigoArl(String codigoArl) {
        this.codigoArl = codigoArl;
    }

    public String getTipoVinculador() {
        return tipoVinculador;
    }

    public void setTipoVinculador(String tipoVinculador) {
        this.tipoVinculador = tipoVinculador;
    }

    public String getNombreCiiu() {
        return nombreCiiu;
    }

    public void setNombreCiiu(String nombreCiiu) {
        this.nombreCiiu = nombreCiiu;
    }

    public String getCodigoCiiu() {
        return codigoCiiu;
    }

    public void setCodigoCiiu(String codigoCiiu) {
        this.codigoCiiu = codigoCiiu;
    }

    public String getRazonSocial() {
        return razonSocial;
    }

    public void setRazonSocial(String razonSocial) {
        this.razonSocial = razonSocial;
    }

    public String getTipoIdentificacionEmpresa() {
        return tipoIdentificacionEmpresa;
    }

    public void setTipoIdentificacionEmpresa(String tipoIdentificacionEmpresa) {
        this.tipoIdentificacionEmpresa = tipoIdentificacionEmpresa;
    }

    public String getIdentificacionEmpresa() {
        return identificacionEmpresa;
    }

    public void setIdentificacionEmpresa(String identificacionEmpresa) {
        this.identificacionEmpresa = identificacionEmpresa;
    }

    public String getDireccionEmpresa() {
        return direccionEmpresa;
    }

    public void setDireccionEmpresa(String direccionEmpresa) {
        this.direccionEmpresa = direccionEmpresa;
    }

    public String getTelefonoEmpresa() {
        return telefonoEmpresa;
    }

    public void setTelefonoEmpresa(String telefonoEmpresa) {
        this.telefonoEmpresa = telefonoEmpresa;
    }

    public String getTelefono2Empresa() {
        return telefono2Empresa;
    }

    public void setTelefono2Empresa(String telefono2Empresa) {
        this.telefono2Empresa = telefono2Empresa;
    }

    public String getEmailEmpresa() {
        return emailEmpresa;
    }

    public void setEmailEmpresa(String emailEmpresa) {
        this.emailEmpresa = emailEmpresa;
    }

    public String getZonaEmpresa() {
        return zonaEmpresa;
    }

    public void setZonaEmpresa(String zonaEmpresa) {
        this.zonaEmpresa = zonaEmpresa;
    }

    public Boolean getCentrTrabIgualSedePrinc() {
        return centrTrabIgualSedePrinc;
    }

    public void setCentrTrabIgualSedePrinc(Boolean centrTrabIgualSedePrinc) {
        this.centrTrabIgualSedePrinc = centrTrabIgualSedePrinc;
    }

    public String getNombreCentroTrabajo() {
        return nombreCentroTrabajo;
    }

    public void setNombreCentroTrabajo(String nombreCentroTrabajo) {
        this.nombreCentroTrabajo = nombreCentroTrabajo;
    }

    public String getCodigoCentroTrabajo() {
        return codigoCentroTrabajo;
    }

    public void setCodigoCentroTrabajo(String codigoCentroTrabajo) {
        this.codigoCentroTrabajo = codigoCentroTrabajo;
    }

    public String getCiiuCentroTrabajo() {
        return ciiuCentroTrabajo;
    }

    public void setCiiuCentroTrabajo(String ciiuCentroTrabajo) {
        this.ciiuCentroTrabajo = ciiuCentroTrabajo;
    }

    public String getCodCiiuCentroTrabajo() {
        return codCiiuCentroTrabajo;
    }

    public void setCodCiiuCentroTrabajo(String codCiiuCentroTrabajo) {
        this.codCiiuCentroTrabajo = codCiiuCentroTrabajo;
    }

    public String getDireccionCentroTrabajo() {
        return direccionCentroTrabajo;
    }

    public void setDireccionCentroTrabajo(String direccionCentroTrabajo) {
        this.direccionCentroTrabajo = direccionCentroTrabajo;
    }

    public String getTelefonoCentroTrabajo() {
        return telefonoCentroTrabajo;
    }

    public void setTelefonoCentroTrabajo(String telefonoCentroTrabajo) {
        this.telefonoCentroTrabajo = telefonoCentroTrabajo;
    }

    public String getZonaCentroTrabajo() {
        return zonaCentroTrabajo;
    }

    public void setZonaCentroTrabajo(String zonaCentroTrabajo) {
        this.zonaCentroTrabajo = zonaCentroTrabajo;
    }

    public String getTipoVinculacion() {
        return tipoVinculacion;
    }

    public void setTipoVinculacion(String tipoVinculacion) {
        this.tipoVinculacion = tipoVinculacion;
    }

    public String getCodigoTipoVinculacion() {
        return codigoTipoVinculacion;
    }

    public void setCodigoTipoVinculacion(String codigoTipoVinculacion) {
        this.codigoTipoVinculacion = codigoTipoVinculacion;
    }

    public String getPrimerApellidoEmpleado() {
        return primerApellidoEmpleado;
    }

    public void setPrimerApellidoEmpleado(String primerApellidoEmpleado) {
        this.primerApellidoEmpleado = primerApellidoEmpleado;
    }

    public String getSegundoApellidoEmpleado() {
        return segundoApellidoEmpleado;
    }

    public void setSegundoApellidoEmpleado(String segundoApellidoEmpleado) {
        this.segundoApellidoEmpleado = segundoApellidoEmpleado;
    }

    public String getPrimerNombreEmpleado() {
        return primerNombreEmpleado;
    }

    public void setPrimerNombreEmpleado(String primerNombreEmpleado) {
        this.primerNombreEmpleado = primerNombreEmpleado;
    }

    public String getSegundoNombreEmpleado() {
        return segundoNombreEmpleado;
    }

    public void setSegundoNombreEmpleado(String segundoNombreEmpleado) {
        this.segundoNombreEmpleado = segundoNombreEmpleado;
    }

    public String getTipoIdentificacionEmpleado() {
        return tipoIdentificacionEmpleado;
    }

    public void setTipoIdentificacionEmpleado(String tipoIdentificacionEmpleado) {
        this.tipoIdentificacionEmpleado = tipoIdentificacionEmpleado;
    }

    public String getNumeroIdentificacionEmpleado() {
        return numeroIdentificacionEmpleado;
    }

    public void setNumeroIdentificacionEmpleado(String numeroIdentificacionEmpleado) {
        this.numeroIdentificacionEmpleado = numeroIdentificacionEmpleado;
    }

    public Date getFechaNacimientoEmpleado() {
        return fechaNacimientoEmpleado;
    }

    public void setFechaNacimientoEmpleado(Date fechaNacimientoEmpleado) {
        this.fechaNacimientoEmpleado = fechaNacimientoEmpleado;
    }

    public String getGeneroEmpleado() {
        return generoEmpleado;
    }

    public void setGeneroEmpleado(String generoEmpleado) {
        this.generoEmpleado = generoEmpleado;
    }

    public String getDireccionEmpleado() {
        return direccionEmpleado;
    }

    public void setDireccionEmpleado(String direccionEmpleado) {
        this.direccionEmpleado = direccionEmpleado;
    }

    public String getTelefonoEmpleado() {
        return telefonoEmpleado;
    }

    public void setTelefonoEmpleado(String telefonoEmpleado) {
        this.telefonoEmpleado = telefonoEmpleado;
    }

    public String getTelefono2Empleado() {
        return telefono2Empleado;
    }

    public void setTelefono2Empleado(String telefono2Empleado) {
        this.telefono2Empleado = telefono2Empleado;
    }

    public Date getFechaIngresoEmpleado() {
        return fechaIngresoEmpleado;
    }

    public void setFechaIngresoEmpleado(Date fechaIngresoEmpleado) {
        this.fechaIngresoEmpleado = fechaIngresoEmpleado;
    }

    public String getZonaEmpleado() {
        return zonaEmpleado;
    }

    public void setZonaEmpleado(String zonaEmpleado) {
        this.zonaEmpleado = zonaEmpleado;
    }

    public String getCargoEmpleado() {
        return cargoEmpleado;
    }

    public void setCargoEmpleado(String cargoEmpleado) {
        this.cargoEmpleado = cargoEmpleado;
    }

    public String getOcupacionHabitual() {
        return ocupacionHabitual;
    }

    public void setOcupacionHabitual(String ocupacionHabitual) {
        this.ocupacionHabitual = ocupacionHabitual;
    }

    public String getCodigoOcupacionHabitual() {
        return codigoOcupacionHabitual;
    }

    public void setCodigoOcupacionHabitual(String codigoOcupacionHabitual) {
        this.codigoOcupacionHabitual = codigoOcupacionHabitual;
    }

    public Short getDiasLaborHabitual() {
        return diasLaborHabitual;
    }

    public void setDiasLaborHabitual(Short diasLaborHabitual) {
        this.diasLaborHabitual = diasLaborHabitual;
    }

    public Short getMesesLaborHabitual() {
        return mesesLaborHabitual;
    }

    public void setMesesLaborHabitual(Short mesesLaborHabitual) {
        this.mesesLaborHabitual = mesesLaborHabitual;
    }

    public Double getSalario() {
        return salario;
    }

    public void setSalario(Double salario) {
        this.salario = salario;
    }

    public String getJornadaHabitual() {
        return jornadaHabitual;
    }

    public void setJornadaHabitual(String jornadaHabitual) {
        this.jornadaHabitual = jornadaHabitual;
    }

    public Date getFechaAccidente() {
        return fechaAccidente;
    }

    public void setFechaAccidente(Date fechaAccidente) {
        this.fechaAccidente = fechaAccidente;
    }

    public Date getHoraAccidente() {
        return horaAccidente;
    }

    public void setHoraAccidente(Date horaAccidente) {
        this.horaAccidente = horaAccidente;
    }

    public String getJornadaAccidente() {
        return jornadaAccidente;
    }

    public void setJornadaAccidente(String jornadaAccidente) {
        this.jornadaAccidente = jornadaAccidente;
    }

    public Boolean getRealizandoLaborHabitual() {
        return realizandoLaborHabitual;
    }

    public void setRealizandoLaborHabitual(Boolean realizandoLaborHabitual) {
        this.realizandoLaborHabitual = realizandoLaborHabitual;
    }

    public String getNombreLaborHabitual() {
        return nombreLaborHabitual;
    }

    public void setNombreLaborHabitual(String nombreLaborHabitual) {
        this.nombreLaborHabitual = nombreLaborHabitual;
    }

    public String getCodigoLaborHabitual() {
        return codigoLaborHabitual;
    }

    public void setCodigoLaborHabitual(String codigoLaborHabitual) {
        this.codigoLaborHabitual = codigoLaborHabitual;
    }

    public Date getHoraInicioLabor() {
        return horaInicioLabor;
    }

    public void setHoraInicioLabor(Date horaInicioLabor) {
        this.horaInicioLabor = horaInicioLabor;
    }

    public String getTipoAccidente() {
        return tipoAccidente;
    }

    public void setTipoAccidente(String tipoAccidente) {
        this.tipoAccidente = tipoAccidente;
    }

    public Boolean getCausoMuerte() {
        return causoMuerte;
    }

    public void setCausoMuerte(Boolean causoMuerte) {
        this.causoMuerte = causoMuerte;
    }

    public String getZonaAccidente() {
        return zonaAccidente;
    }

    public void setZonaAccidente(String zonaAccidente) {
        this.zonaAccidente = zonaAccidente;
    }

    public String getLugarAccidente() {
        return lugarAccidente;
    }

    public void setLugarAccidente(String lugarAccidente) {
        this.lugarAccidente = lugarAccidente;
    }

    public Boolean getHuboTestigos() {
        return huboTestigos;
    }

    public void setHuboTestigos(Boolean huboTestigos) {
        this.huboTestigos = huboTestigos;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getSitio() {
        return sitio;
    }

    public void setSitio(String sitio) {
        this.sitio = sitio;
    }

    public String getCualSitio() {
        return cualSitio;
    }

    public void setCualSitio(String cualSitio) {
        this.cualSitio = cualSitio;
    }

    public String getTipoLesion() {
        return tipoLesion;
    }

    public void setTipoLesion(String tipoLesion) {
        this.tipoLesion = tipoLesion;
    }

    public String getCualTipoLesion() {
        return cualTipoLesion;
    }

    public void setCualTipoLesion(String cualTipoLesion) {
        this.cualTipoLesion = cualTipoLesion;
    }

    public String getParteCuerpo() {
        return parteCuerpo;
    }

    public void setParteCuerpo(String parteCuerpo) {
        this.parteCuerpo = parteCuerpo;
    }

    public String getAgente() {
        return agente;
    }

    public void setAgente(String agente) {
        this.agente = agente;
    }

    public String getCualAgente() {
        return cualAgente;
    }

    public void setCualAgente(String cualAgente) {
        this.cualAgente = cualAgente;
    }

    public String getMecanismo() {
        return mecanismo;
    }

    public void setMecanismo(String mecanismo) {
        this.mecanismo = mecanismo;
    }

    public String getCualMecanismo() {
        return cualMecanismo;
    }

    public void setCualMecanismo(String cualMecanismo) {
        this.cualMecanismo = cualMecanismo;
    }

    public Ciudad getCiudadEmpresa() {
        return ciudadEmpresa;
    }

    public void setCiudadEmpresa(Ciudad ciudadEmpresa) {
        this.ciudadEmpresa = ciudadEmpresa;
    }

    public Ciudad getCiudadCentroTrabajo() {
        return ciudadCentroTrabajo;
    }

    public void setCiudadCentroTrabajo(Ciudad ciudadCentroTrabajo) {
        this.ciudadCentroTrabajo = ciudadCentroTrabajo;
    }

    public Ciudad getCiudadEmpleado() {
        return ciudadEmpleado;
    }

    public void setCiudadEmpleado(Ciudad ciudadEmpleado) {
        this.ciudadEmpleado = ciudadEmpleado;
    }

    public Ciudad getCiudadAccidente() {
        return ciudadAccidente;
    }

    public void setCiudadAccidente(Ciudad ciudadAccidente) {
        this.ciudadAccidente = ciudadAccidente;
    }

    public Area getAreaAccidente() {
        return areaAccidente;
    }

    public void setAreaAccidente(Area areaAccidente) {
        this.areaAccidente = areaAccidente;
    }

    @JsonIgnore
    public Usuario getUsuarioReporta() {
        return usuarioReporta;
    }

    public void setUsuarioReporta(Usuario usuarioReporta) {
        this.usuarioReporta = usuarioReporta;
    }

    @JsonIgnore
    public Empresa getEmpresa() {
        return empresa;
    }

    public void setEmpresa(Empresa empresa) {
        this.empresa = empresa;
    }

    public List<TestigoReporte> getTestigoReporteList() {
        return testigoReporteList;
    }

    public void setTestigoReporteList(List<TestigoReporte> testigoReporteList) {
        this.testigoReporteList = testigoReporteList;
    }

    public String getNombreCiiuCentroTrabajo() {
        return nombreCiiuCentroTrabajo;
    }

    public void setNombreCiiuCentroTrabajo(String nombreCiiuCentroTrabajo) {
        this.nombreCiiuCentroTrabajo = nombreCiiuCentroTrabajo;
    }

    public String getNombresResponsable() {
        return nombresResponsable;
    }

    public void setNombresResponsable(String nombresResponsable) {
        this.nombresResponsable = nombresResponsable;
    }

    public String getApellidosResponsable() {
        return apellidosResponsable;
    }

    public void setApellidosResponsable(String apellidosResponsable) {
        this.apellidosResponsable = apellidosResponsable;
    }

    public String getTipoIdentificacionResponsable() {
        return tipoIdentificacionResponsable;
    }

    public void setTipoIdentificacionResponsable(String tipoIdentificacionResponsable) {
        this.tipoIdentificacionResponsable = tipoIdentificacionResponsable;
    }

    public String getNumeroIdentificacionResponsable() {
        return numeroIdentificacionResponsable;
    }

    public void setNumeroIdentificacionResponsable(String numeroIdentificacionResponsable) {
        this.numeroIdentificacionResponsable = numeroIdentificacionResponsable;
    }

    public String getCargoResponsable() {
        return cargoResponsable;
    }

    public void setCargoResponsable(String cargoResponsable) {
        this.cargoResponsable = cargoResponsable;
    }

    public Date getFechaReporte() {
        return fechaReporte;
    }

    public void setFechaReporte(Date fechaReporte) {
        this.fechaReporte = fechaReporte;
    }

    public Integer getAnioReporte() {
        return anioReporte;
    }

    public void setAnioReporte(Integer anioReporte) {
        this.anioReporte = anioReporte;
    }

    public String getNombreAreaAccidente() {
        return nombreAreaAccidente;
    }

    public void setNombreAreaAccidente(String nombreAreaAccidente) {
        this.nombreAreaAccidente = nombreAreaAccidente;
    }

    public String getEmailEmpleado() {
        return emailEmpleado;
    }

    public void setEmailEmpleado(String emailEmpleado) {
        this.emailEmpleado = emailEmpleado;
    }

    public Integer getEdadEmpleado() {
        return edadEmpleado;
    }

    public void setEdadEmpleado(Integer edadEmpleado) {
        this.edadEmpleado = edadEmpleado;
    }

    public String getRangoEdadEmpleado() {
        return rangoEdadEmpleado;
    }

    public void setRangoEdadEmpleado(String rangoEdadEmpleado) {
        this.rangoEdadEmpleado = rangoEdadEmpleado;
    }

    public String getNombreDepartamentoAccidente() {
        return nombreDepartamentoAccidente;
    }

    public void setNombreDepartamentoAccidente(String nombreDepartamentoAccidente) {
        this.nombreDepartamentoAccidente = nombreDepartamentoAccidente;
    }

    public String getNombreCiudadAccidente() {
        return nombreCiudadAccidente;
    }

    public void setNombreCiudadAccidente(String nombreCiudadAccidente) {
        this.nombreCiudadAccidente = nombreCiudadAccidente;
    }

    public String getMesAccidente() {
        return mesAccidente;
    }

    public void setMesAccidente(String mesAccidente) {
        this.mesAccidente = mesAccidente;
    }

    public String getDiaSemana() {
        return diaSemana;
    }

    public void setDiaSemana(String diaSemana) {
        this.diaSemana = diaSemana;
    }

    public String getDiagnostico() {
        return diagnostico;
    }

    public void setDiagnostico(String diagnostico) {
        this.diagnostico = diagnostico;
    }

    public String getSeveridad() {
        return severidad;
    }

    public void setSeveridad(String severidad) {
        this.severidad = severidad;
    }

    public boolean isMigrado() {
        return migrado;
    }

    public void setMigrado(boolean migrado) {
        this.migrado = migrado;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (getId() != null ? getId().hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Reporte)) {
            return false;
        }
        Reporte other = (Reporte) object;
        if ((this.getId() == null && other.getId() != null) || (this.getId() != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "co.sigess.entities.rai.Reporte[ id=" + getId() + " ]";
    }

    /**
     * @return the numero_furat
     */
    public String getNumero_furat() {
        return numero_furat;
    }

    /**
     * @param numero_furat the numero_furat to set
     */
    public void setNumero_furat(String numero_furat) {
        this.numero_furat = numero_furat;
    }

}
