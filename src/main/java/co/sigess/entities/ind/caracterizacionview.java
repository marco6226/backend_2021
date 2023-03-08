/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.sigess.entities.ind;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Usuario
 */
@Entity
@JsonIgnoreProperties(ignoreUnknown = true)
@Table(name = "vw_caracterizacion", schema = "ind")
@XmlRootElement
public class caracterizacionview implements Serializable{
    
    @Column(name = "reporte_id")
    @Id
    private Integer reporteid;
    
    @Column(name = "analisis_id")
    private Integer analisisid;
    
    @Column(name = "hash_id")
    private String hashid;
    
    @Column(name = "concepto")
    private String concepto;
    
    @Column(name = "hora")
    private String hora;
    
    @Column(name = "severidad")
    private String severidad;
    
    @Column(name = "peligro")
    private String peligro;
    
    @Column(name = "descripcionpeligro")
    private String descripcionpeligro;
    
    @Column(name = "estadoantearl")
    private String estadoantearl;
    
    @Column(name = "diasausencia1")
    private String diasausencia1;
    
    @Column(name = "diasausencia2")
    private String diasausencia2;
    
    @Column(name = "diasausencia3")
    private String diasausencia3;
    
    @Column(name = "factorcausal1")
    private String factorcausal1;
    
    @Column(name = "factorcausal2")
    private String factorcausal2;
    
    @Column(name = "factorcausal3")
    private String factorcausal3;
    
    @Column(name = "factorcausal4")
    private String factorcausal4;
    
    @Column(name = "factorcausal5")
    private String factorcausal5;
    
    @Column(name = "factorcausal6")
    private String factorcausal6;
    
    @Column(name = "razon_social")
    private String razonsocial;
    
    @Column(name = "identificacion_empresa")
    private String identificacionempresa;
    
    @Column(name = "primer_apellido_empleado")
    private String primerapellidoempleado;
    
    @Column(name = "primer_nombre_empleado")
    private String primernombreempleado;
    
    @Column(name = "numero_identificacion_empleado")
    private String numeroidentificacionempleado;
    
    @Column(name = "fecha_ingreso_empleado")
    @Temporal(TemporalType.DATE)
    private Date fechaingresoempleado;
    
    @Column(name = "cargo_empleado")
    private String cargoempleado;
    
    @Column(name = "dias_labor_habitual")
    private Integer diaslaborhabitual;
    
    @Column(name = "meses_labor_habitual")
    private Integer meseslaborhabitual;
    
    @Column(name = "jornada_habitual")
    private String jornadahabitual;
    
    @Column(name = "nombre")
    private String nombre;
    
    @Column(name = "padre_nombre")
    private String padrenombre;
    
    @Column(name = "fecha_accidente")
    @Temporal(TemporalType.DATE)
    private Date fechaaccidente;
    
    @Column(name = "tipo_accidente")
    private String tipoaccidente;
    
    @Column(name = "sitio")
    private String sitio;
    
    @Column(name = "tipo_lesion")
    private String tipolesion;
    
    @Column(name = "mecanismo")
    private String mecanismo;
    
    @Column(name = "agente")
    private String agente;
    
    @Column(name = "parte_cuerpo")
    private String partecuerpo;
    
    @Column(name = "genero_empleado")
    private String generoempleado;
    
    @Column(name = "jornada_accidente")
    private String jornadaaccidente;
    
    @Column(name = "lugar_accidente")
    private String lugaraccidente;

    @Column(name = "emptemporal")
    private String emptemporal;
    
    @Column(name = "hora_accidente")
    @Temporal(TemporalType.DATE)
    private Date horaaccidente;
    
    @Column(name = "fecha_nacimiento_empleado")
    private Date fechanacimientoempleado;

    public Date getFechanacimientoempleado() {
        return fechanacimientoempleado;
    }

    public void setFechanacimientoempleado(Date fechanacimientoempleado) {
        this.fechanacimientoempleado = fechanacimientoempleado;
    }

    public Date getHoraaccidente() {
        return horaaccidente;
    }

    public void setHoraaccidente(Date horaaccidente) {
        this.horaaccidente = horaaccidente;
    }    

    public String getEmptemporal() {
        return emptemporal;
    }

    public void setEmptemporal(String emptemporal) {
        this.emptemporal = emptemporal;
    }
    
    public Integer getReporteid() {
        return reporteid;
    }

    public void setReporteid(Integer reporteid) {
        this.reporteid = reporteid;
    }

    public Integer getAnalisisid() {
        return analisisid;
    }

    public void setAnalisisid(Integer analisisid) {
        this.analisisid = analisisid;
    }

    public String getHashid() {
        return hashid;
    }

    public void setHashid(String hashid) {
        this.hashid = hashid;
    }

    public String getConcepto() {
        return concepto;
    }

    public void setConcepto(String concepto) {
        this.concepto = concepto;
    }

    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }

    public String getSeveridad() {
        return severidad;
    }

    public void setSeveridad(String severidad) {
        this.severidad = severidad;
    }

    public String getPeligro() {
        return peligro;
    }

    public void setPeligro(String peligro) {
        this.peligro = peligro;
    }

    public String getDescripcionpeligro() {
        return descripcionpeligro;
    }

    public void setDescripcionpeligro(String descripcionpeligro) {
        this.descripcionpeligro = descripcionpeligro;
    }

    public String getEstadoantearl() {
        return estadoantearl;
    }

    public void setEstadoantearl(String estadoantearl) {
        this.estadoantearl = estadoantearl;
    }

    public String getDiasausencia1() {
        return diasausencia1;
    }

    public void setDiasausencia1(String diasausencia1) {
        this.diasausencia1 = diasausencia1;
    }

    public String getDiasausencia2() {
        return diasausencia2;
    }

    public void setDiasausencia2(String diasausencia2) {
        this.diasausencia2 = diasausencia2;
    }

    public String getDiasausencia3() {
        return diasausencia3;
    }

    public void setDiasausencia3(String diasausencia3) {
        this.diasausencia3 = diasausencia3;
    }

    public String getFactorcausal1() {
        return factorcausal1;
    }

    public void setFactorcausal1(String factorcausal1) {
        this.factorcausal1 = factorcausal1;
    }

    public String getFactorcausal2() {
        return factorcausal2;
    }

    public void setFactorcausal2(String factorcausal2) {
        this.factorcausal2 = factorcausal2;
    }

    public String getFactorcausal3() {
        return factorcausal3;
    }

    public void setFactorcausal3(String factorcausal3) {
        this.factorcausal3 = factorcausal3;
    }

    public String getFactorcausal4() {
        return factorcausal4;
    }

    public void setFactorcausal4(String factorcausal4) {
        this.factorcausal4 = factorcausal4;
    }

    public String getFactorcausal5() {
        return factorcausal5;
    }

    public void setFactorcausal5(String factorcausal5) {
        this.factorcausal5 = factorcausal5;
    }

    public String getFactorcausal6() {
        return factorcausal6;
    }

    public void setFactorcausal6(String factorcausal6) {
        this.factorcausal6 = factorcausal6;
    }

    public String getRazonsocial() {
        return razonsocial;
    }

    public void setRazonsocial(String razonsocial) {
        this.razonsocial = razonsocial;
    }

    public String getIdentificacionempresa() {
        return identificacionempresa;
    }

    public void setIdentificacionempresa(String identificacionempresa) {
        this.identificacionempresa = identificacionempresa;
    }

    public String getPrimerapellidoempleado() {
        return primerapellidoempleado;
    }

    public void setPrimerapellidoempleado(String primerapellidoempleado) {
        this.primerapellidoempleado = primerapellidoempleado;
    }

    public String getPrimernombreempleado() {
        return primernombreempleado;
    }

    public void setPrimernombreempleado(String primernombreempleado) {
        this.primernombreempleado = primernombreempleado;
    }

    public String getNumeroidentificacionempleado() {
        return numeroidentificacionempleado;
    }

    public void setNumeroidentificacionempleado(String numeroidentificacionempleado) {
        this.numeroidentificacionempleado = numeroidentificacionempleado;
    }

    public Date getFechaingresoempleado() {
        return fechaingresoempleado;
    }

    public void setFechaingresoempleado(Date fechaingresoempleado) {
        this.fechaingresoempleado = fechaingresoempleado;
    }

    public String getCargoempleado() {
        return cargoempleado;
    }

    public void setCargoempleado(String cargoempleado) {
        this.cargoempleado = cargoempleado;
    }

    public Integer getDiaslaborhabitual() {
        return diaslaborhabitual;
    }

    public void setDiaslaborhabitual(Integer diaslaborhabitual) {
        this.diaslaborhabitual = diaslaborhabitual;
    }

    public Integer getMeseslaborhabitual() {
        return meseslaborhabitual;
    }

    public void setMeseslaborhabitual(Integer meseslaborhabitual) {
        this.meseslaborhabitual = meseslaborhabitual;
    }

    public String getJornadahabitual() {
        return jornadahabitual;
    }

    public void setJornadahabitual(String jornadahabitual) {
        this.jornadahabitual = jornadahabitual;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getPadrenombre() {
        return padrenombre;
    }

    public void setPadrenombre(String padrenombre) {
        this.padrenombre = padrenombre;
    }

    public Date getFechaaccidente() {
        return fechaaccidente;
    }

    public void setFechaaccidente(Date fechaaccidente) {
        this.fechaaccidente = fechaaccidente;
    }

    public String getTipoaccidente() {
        return tipoaccidente;
    }

    public void setTipoaccidente(String tipoaccidente) {
        this.tipoaccidente = tipoaccidente;
    }

    public String getSitio() {
        return sitio;
    }

    public void setSitio(String sitio) {
        this.sitio = sitio;
    }

    public String getTipolesion() {
        return tipolesion;
    }

    public void setTipolesion(String tipolesion) {
        this.tipolesion = tipolesion;
    }

    public String getMecanismo() {
        return mecanismo;
    }

    public void setMecanismo(String mecanismo) {
        this.mecanismo = mecanismo;
    }

    public String getAgente() {
        return agente;
    }

    public void setAgente(String agente) {
        this.agente = agente;
    }

    public String getPartecuerpo() {
        return partecuerpo;
    }

    public void setPartecuerpo(String partecuerpo) {
        this.partecuerpo = partecuerpo;
    }

    public String getGeneroempleado() {
        return generoempleado;
    }

    public void setGeneroempleado(String generoempleado) {
        this.generoempleado = generoempleado;
    }

    public String getJornadaaccidente() {
        return jornadaaccidente;
    }

    public void setJornadaaccidente(String jornadaaccidente) {
        this.jornadaaccidente = jornadaaccidente;
    }

    public String getLugaraccidente() {
        return lugaraccidente;
    }

    public void setLugaraccidente(String lugaraccidente) {
        this.lugaraccidente = lugaraccidente;
    }  
}
