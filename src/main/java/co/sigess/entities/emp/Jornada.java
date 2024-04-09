/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.sigess.entities.emp;

import co.sigess.util.converter.DurationConverter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
@Table(name = "jornada", schema = "emp")
@XmlRootElement
public class Jornada implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @SequenceGenerator(name = "jornada_id_seq", schema = "emp", sequenceName = "jornada_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "jornada_id_seq")
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
    
    @Enumerated(EnumType.ORDINAL)
    @Column(name = "dia")
    private Dia dia;

    @Column(name = "labora")
    private Boolean labora;

    @JsonIgnore
    @JoinColumn(name = "fk_configuracion_jornada_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private ConfiguracionJornada configuracionJornada;

    public Jornada() {
    }

    public Jornada(Long id) {
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

    public Dia getDia() {
        return dia;
    }

    public void setDia(Dia dia) {
        this.dia = dia;
    }

    public Boolean getLabora() {
        return labora;
    }

    public void setLabora(Boolean labora) {
        this.labora = labora;
    }

    public ConfiguracionJornada getConfiguracionJornada() {
        return configuracionJornada;
    }

    public void setConfiguracionJornada(ConfiguracionJornada configuracionJornada) {
        this.configuracionJornada = configuracionJornada;
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
        if (!(object instanceof Jornada)) {
            return false;
        }
        Jornada other = (Jornada) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "co.sigess.entities.emp.Jornada[ id=" + id + " ]";
    }

}
