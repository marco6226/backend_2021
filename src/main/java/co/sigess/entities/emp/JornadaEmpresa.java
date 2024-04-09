/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.sigess.entities.emp;

import co.sigess.util.converter.DurationConverter;
import java.io.Serializable;
import java.time.Duration;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author fmoreno
 */
@Entity
@Table(name = "jornada_empresa", schema = "emp")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "JornadaEmpresa.findAll", query = "SELECT j FROM JornadaEmpresa j")})
public class JornadaEmpresa implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Long id;

    @Column(name = "hora_inicio")
    @Temporal(TemporalType.TIME)
    private Date horaInicio;

    @Column(name = "hora_fin")
    @Temporal(TemporalType.TIME)
    private Date horaFin;

    @Column(name = "receso")
    @Convert(converter = DurationConverter.class)
    private Date receso;

    @Size(max = 15)
    @Column(name = "dia")
    private String dia;

    @Column(name = "labora")
    private Boolean labora;

    public JornadaEmpresa() {
    }

    public JornadaEmpresa(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getHoraInicio() {
        return horaInicio;
    }

    public void setHoraInicio(Date horaInicio) {
        this.horaInicio = horaInicio;
    }

    public Date getHoraFin() {
        return horaFin;
    }

    public void setHoraFin(Date horaFin) {
        this.horaFin = horaFin;
    }

    public Date getReceso() {
        return receso;
    }

    public void setReceso(Date receso) {
        this.receso = receso;
    }

    public String getDia() {
        return dia;
    }

    public void setDia(String dia) {
        this.dia = dia;
    }

    public Boolean getLabora() {
        return labora;
    }

    public void setLabora(Boolean labora) {
        this.labora = labora;
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
        if (!(object instanceof JornadaEmpresa)) {
            return false;
        }
        JornadaEmpresa other = (JornadaEmpresa) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "co.sigess.entities.emp.JornadaEmpresa[ id=" + id + " ]";
    }

}
