/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package co.sigess.entities.scm;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Luis
 */
@Entity
@Table(name = "mail_saludlaboral", schema = "scm")
@XmlRootElement
public class MailSaludLaboralEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @Basic(optional = false)
    @NotNull
    @SequenceGenerator(name = "mail_saludlaboral_id_seq", schema = "scm", sequenceName = "mail_saludlaboral_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "mail_saludlaboral_id_seq")
    @Column(name = "id")
    private Long id;
    
    @Column(name = "pk_user")
    private Integer pkUser;
    
    @Column(name = "fecha_envio")
    private Date fechaEnvio;
    
    @Column(name = "fecha_limite")
    private Date fechaLimite;
    
    @Column(name = "fecha_solicitud")
    private Date fechaSolicitud;
    
    @Column(name ="doc_solicitado")
    private String docSolicitado;
    
    @Column(name = "usuario_solicitante")
    private String usuarioSolicitante;
    
    @Column(name = "usuario_solicitado")
    private String usuarioSolicitado;
    
    @Column(name = "estado_correo")
    private Integer estadoCorreo;
    
    @Column(name = "pk_case")
    private Integer pkCase;
    
    @Column(name = "usuario_solicitante_name")
    private String soliictanteNombres;
    
    @Column(name = "usuario_solicitante_cedula")
    private String solicitanteCedula;
    
    @Column(name = "usuario_solicitado_name")
    private String solicitadoNombres;
    
    @Column(name ="usuario_solicitado_cedula")
    private String solicitadoCedula;
    
    @Column(name = "solicitado_nombres_mail")
    private String solicitadoNombresMail;
    
    @Column(name = "razon_rechazo_solicitado")
    private String razonRechazoSolicitado;
    
    @Column(name = "documentos")
    private String documentos;
    
    @Column(name = "razon_rechazo_solicitante")
    private String razonRechazoSolicitante;
    
    @Column(name = "correo_enviado")
    private Boolean correoEnviado;
    
    @Column(name = "fk_empresa_id")
    private Integer empresaId;

    public Integer getEmpresaId() {
        return empresaId;
    }

    public void setEmpresaId(Integer empresaId) {
        this.empresaId = empresaId;
    }
    
    

    public Boolean getCorreoEnviado() {
        return correoEnviado;
    }

    public void setCorreoEnviado(Boolean correoEnviado) {
        this.correoEnviado = correoEnviado;
    }
    
    

    public String getRazonRechazoSolicitante() {
        return razonRechazoSolicitante;
    }

    public void setRazonRechazoSolicitante(String razonRechazoSolicitante) {
        this.razonRechazoSolicitante = razonRechazoSolicitante;
    }
    
    

    public String getDocumentos() {
        return documentos;
    }

    public void setDocumentos(String documentos) {
        this.documentos = documentos;
    }
    
    

    public String getRazonRechazoSolicitado() {
        return razonRechazoSolicitado;
    }

    public void setRazonRechazoSolicitado(String razonRechazoSolicitado) {
        this.razonRechazoSolicitado = razonRechazoSolicitado;
    }
    
    

    public String getSolicitadoNombresMail() {
        return solicitadoNombresMail;
    }

    public void setSolicitadoNombresMail(String solicitadoNombresMail) {
        this.solicitadoNombresMail = solicitadoNombresMail;
    }
    
    

    public String getSolicitadoCedula() {
        return solicitadoCedula;
    }

    public void setSolicitadoCedula(String solicitadoCedula) {
        this.solicitadoCedula = solicitadoCedula;
    }
    
    

    public String getSolicitadoNombres() {
        return solicitadoNombres;
    }

    public void setSolicitadoNombres(String solicitadoNombres) {
        this.solicitadoNombres = solicitadoNombres;
    }
    
    
    
    @Column(name = "asignacion_tarea")
    private String asignacionTarea;

    public String getAsignacionTarea() {
        return asignacionTarea;
    }

    public void setAsignacionTarea(String asignacionTarea) {
        this.asignacionTarea = asignacionTarea;
    }
    
    

    public String getSolicitanteCedula() {
        return solicitanteCedula;
    }

    public void setSolicitanteCedula(String solicitanteCedula) {
        this.solicitanteCedula = solicitanteCedula;
    }
    
    

    public String getSoliictanteNombres() {
        return soliictanteNombres;
    }

    public void setSoliictanteNombres(String soliictanteNombres) {
        this.soliictanteNombres = soliictanteNombres;
    }
    
    

    public Integer getPkCase() {
        return pkCase;
    }

    public void setPkCase(Integer pkCase) {
        this.pkCase = pkCase;
    }
    
    

    public Date getFechaSolicitud() {
        return fechaSolicitud;
    }

    public void setFechaSolicitud(Date fechaSolicitud) {
        this.fechaSolicitud = fechaSolicitud;
    }
    
    

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getPkUser() {
        return pkUser;
    }

    public void setPkUser(Integer pkUser) {
        this.pkUser = pkUser;
    }

    public Date getFechaEnvio() {
        return fechaEnvio;
    }

    public void setFechaEnvio(Date fechaEnvio) {
        this.fechaEnvio = fechaEnvio;
    }

    public Date getFechaLimite() {
        return fechaLimite;
    }

    public void setFechaLimite(Date fechaLimite) {
        this.fechaLimite = fechaLimite;
    }

    public String getDocSolicitado() {
        return docSolicitado;
    }

    public void setDocSolicitado(String docSolicitado) {
        this.docSolicitado = docSolicitado;
    }

    public String getUsuarioSolicitante() {
        return usuarioSolicitante;
    }

    public void setUsuarioSolicitante(String usuarioSolicitante) {
        this.usuarioSolicitante = usuarioSolicitante;
    }

    public String getUsuarioSolicitado() {
        return usuarioSolicitado;
    }

    public void setUsuarioSolicitado(String usuarioSolicitado) {
        this.usuarioSolicitado = usuarioSolicitado;
    }

    public Integer getEstadoCorreo() {
        return estadoCorreo;
    }

    public void setEstadoCorreo(Integer estadoCorreo) {
        this.estadoCorreo = estadoCorreo;
    }
    
    
    
}
