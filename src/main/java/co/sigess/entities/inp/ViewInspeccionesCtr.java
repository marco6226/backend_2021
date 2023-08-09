/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.sigess.entities.inp;

import co.sigess.entities.emp.Empresa;
import co.sigess.entities.emp.Usuario;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author JULIO
 */
@Entity
@JsonIgnoreProperties(ignoreUnknown = true)
@Table(name = "vw_inspeccion_ctr", schema = "inp")
@XmlRootElement
public class ViewInspeccionesCtr implements Serializable {

    @Column(name = "id")
    @Id
    private String id;
    
    @JoinColumn(name = "fk_programacion_id", referencedColumnName = "id")
    @ManyToOne
    private Programacion programacion;
    
    @Column(name = "fecha_realizada")
    @Temporal(TemporalType.DATE)
    private Date fecha;
    
    @JoinColumns({
        @JoinColumn(name = "fk_lista_inspeccion_id", referencedColumnName = "id")
        , @JoinColumn(name = "fk_lista_inspeccion_version", referencedColumnName = "version")})
    @ManyToOne
    private ListaInspeccion listaInspeccion;
    
    @JoinColumn(name = "fk_usuario_registra_id", referencedColumnName = "id")
    @ManyToOne
    private Usuario usuarioRegistra;
    
    @Column(name = "nombre_usuario_registra")
    private String nombreUsuarioRegistra;
    
    @JoinColumn(name = "fk_empresa_id", referencedColumnName = "id")
    @ManyToOne
    private Empresa empresa;
    
    @JoinColumn(name = "id_empresa_aliada", referencedColumnName = "id")
    @ManyToOne
    private Empresa empresaAliada;
    
    @Column(name = "empresa_aliada")
    private String empresaAliadaConNit;
    
    @Column(name = "tipo_inspeccion")
    private String tipoInspeccion;
    
    @Column(name = "calificacion")
    private double calificacion;

    public Programacion getProgramacion() {
        return programacion;
    }

    public void setProgramacion(Programacion programacion) {
        this.programacion = programacion;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public ListaInspeccion getListaInspeccion() {
        return listaInspeccion;
    }

    public void setListaInspeccion(ListaInspeccion listaInspeccion) {
        this.listaInspeccion = listaInspeccion;
    }

    public Usuario getUsuarioRegistra() {
        return usuarioRegistra;
    }

    public void setUsuarioRegistra(Usuario usuarioRegistra) {
        this.usuarioRegistra = usuarioRegistra;
    }

    public String getNombreUsuarioRegistra() {
        return nombreUsuarioRegistra;
    }

    public void setNombreUsuarioRegistra(String nombreUsuarioRegistra) {
        this.nombreUsuarioRegistra = nombreUsuarioRegistra;
    }

    public Empresa getEmpresa() {
        return empresa;
    }

    public void setEmpresa(Empresa empresa) {
        this.empresa = empresa;
    }

    public Empresa getEmpresaAliada() {
        return empresaAliada;
    }

    public void setEmpresaAliada(Empresa empresaAliada) {
        this.empresaAliada = empresaAliada;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTipoInspeccion() {
        return tipoInspeccion;
    }

    public void setTipoInspeccion(String tipoInspeccion) {
        this.tipoInspeccion = tipoInspeccion;
    }

    public String getEmpresaAliadaConNit() {
        return empresaAliadaConNit;
    }

    public void setEmpresaAliadaConNit(String empresaAliadaConNit) {
        this.empresaAliadaConNit = empresaAliadaConNit;
    }

    public double getCalificacion() {
        return calificacion;
    }

    public void setCalificacion(double calificacion) {
        this.calificacion = calificacion;
    }
}
