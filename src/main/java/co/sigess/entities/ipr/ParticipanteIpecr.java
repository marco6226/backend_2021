/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.sigess.entities.ipr;

import co.sigess.entities.TipoParticipante;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.Size;

/**
 *
 * @author fmoreno
 */
@Entity
@Table(name = "participante_ipecr", schema = "ipr")
@NamedQueries({
    @NamedQuery(name = "ParticipanteIpecr.findAll", query = "SELECT p FROM ParticipanteIpecr p")})
public class ParticipanteIpecr implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @SequenceGenerator(name = "participante_ipecr_id_seq", schema = "ipr", sequenceName = "participante_ipecr_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "participante_ipecr_id_seq")
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;

    @Size(max = 255)
    @Column(name = "nombres")
    private String nombres;

    @Size(max = 255)
    @Column(name = "apellidos")
    private String apellidos;

    @Size(max = 255)
    @Column(name = "cargo")
    private String cargo;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo")
    private TipoParticipante tipo;

    @JoinColumn(name = "fk_ipecr_id", referencedColumnName = "id", updatable = false)
    @ManyToOne
    private Ipecr ipecr;

    public ParticipanteIpecr() {
    }

    public ParticipanteIpecr(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNombres() {
        return nombres;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getCargo() {
        return cargo;
    }

    public void setCargo(String cargo) {
        this.cargo = cargo;
    }

    public TipoParticipante getTipo() {
        return tipo;
    }

    public void setTipo(TipoParticipante tipo) {
        this.tipo = tipo;
    }

    @JsonIgnore
    public Ipecr getIpecr() {
        return ipecr;
    }

    @JsonProperty("ipecr")
    public void setIpecr(Ipecr ipecr) {
        this.ipecr = ipecr;
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
        if (!(object instanceof ParticipanteIpecr)) {
            return false;
        }
        ParticipanteIpecr other = (ParticipanteIpecr) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "co.sigess.entities.ipr.ParticipanteIpecr[ id=" + id + " ]";
    }

}
