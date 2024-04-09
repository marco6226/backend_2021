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
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
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
@Table(name = "evento_log", schema = "emp")
@XmlRootElement
public class EventoLog implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @SequenceGenerator(name = "evento_log_id_seq", schema = "emp", sequenceName = "evento_log_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "evento_log_id_seq")
    @Basic(optional = false)
    @Column(name = "id")
    @JsonIgnore
    private Long id;

    @Size(max = 64)
    @Column(name = "metodo")
    @JsonIgnore
    private String metodo;

    @Column(name = "solicitud")
    @JsonIgnore
    private String solicitud;

    @Size(max = 25)
    @Column(name = "ip")
    private String ip;

    @Size(max = 256)
    @Column(name = "user_agent")
    private String userAgent;

    @Basic(optional = false)
    @NotNull
    @Column(name = "fecha")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fecha;

    @Column(name = "respuesta")
    private Integer respuesta;

    @Size(max = 2147483647)
    @Column(name = "data")
    @JsonIgnore
    private String data;

    @Column(name = "usuario_email")
    @JsonIgnore
    private String usuarioEmail;
    
    @Column(name = "client_version")
    @JsonIgnore
    private String clientVersion;

    public EventoLog() {
    }

    public EventoLog(Long id) {
        this.id = id;
    }

    public EventoLog(Long id, Date fecha) {
        this.id = id;
        this.fecha = fecha;
    }

    public EventoLog(Long id, String metodo, String solicitud, String ip, String userAgent, Date fecha, Integer respuesta, String data, String usuarioEmail, String clientVersion) {
        this.id = id;
        this.solicitud = solicitud;
        this.metodo = metodo;
        this.ip = ip;
        this.userAgent = userAgent;
        this.fecha = fecha;
        this.respuesta = respuesta;
        this.data = data;
        this.usuarioEmail = usuarioEmail;
        this.clientVersion = clientVersion;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMetodo() {
        return metodo;
    }

    public void setMetodo(String metodo) {
        this.metodo = metodo;
    }

    public String getSolicitud() {
        return solicitud;
    }

    public void setSolicitud(String solicitud) {
        this.solicitud = solicitud;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getUserAgent() {
        return userAgent;
    }

    public void setUserAgent(String userAgent) {
        this.userAgent = userAgent;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public Integer getRespuesta() {
        return respuesta;
    }

    public void setRespuesta(Integer respuesta) {
        this.respuesta = respuesta;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getUsuarioEmail() {
        return usuarioEmail;
    }

    public void setUsuarioEmail(String usuarioEmail) {
        this.usuarioEmail = usuarioEmail;
    }

    public String getClientVersion() {
        return clientVersion;
    }

    public void setClientVersion(String clientVersion) {
        this.clientVersion = clientVersion;
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
        if (!(object instanceof EventoLog)) {
            return false;
        }
        EventoLog other = (EventoLog) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "EventoLog{" + "id=" + id
                + ", metodo=" + metodo
                + ", solicitud=" + solicitud
                + ", ip=" + ip
                + ", userAgent=" + userAgent
                + ", fecha=" + fecha
                + ", respuesta=" + respuesta
                + ", data=" + data
                + ", usuarioEmail=" + usuarioEmail
                + ", clientVersion=" + clientVersion
                + '}';
    }

}
