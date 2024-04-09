/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.sigess.entities.aus;

import co.sigess.entities.emp.Empresa;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.io.Serializable;
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
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author fmoreno
 */
@Entity
@Table(name = "causa_ausentismo", schema = "aus")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "CausaAusentismo.findAll", query = "SELECT c FROM CausaAusentismo c")})
public class CausaAusentismo implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @SequenceGenerator(name = "causa_ausentismo_id_seq", schema = "aus", sequenceName = "causa_ausentismo_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "causa_ausentismo_id_seq")
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    
    @Size(max = 45)
    @Column(name = "tipo")
    private String tipo;
    
    @Size(max = 100)
    @Column(name = "nombre")
    private String nombre;
    
    @Column(name = "requiere_soporte")
    private Boolean requiereSoporte;
    
    @Column(name = "requiere_cie")
    private Boolean requiereCie;
    
    @NotNull
    @ManyToOne
    @JoinColumn(name = "fk_empresa_id", referencedColumnName = "id")
    private Empresa empresa;
    

    public CausaAusentismo() {
    }

    public CausaAusentismo(Integer id) {
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

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Boolean getRequiereSoporte() {
        return requiereSoporte;
    }

    public void setRequiereSoporte(Boolean requiereSoporte) {
        this.requiereSoporte = requiereSoporte;
    }

    public Boolean getRequiereCie() {
        return requiereCie;
    }

    public void setRequiereCie(Boolean requiereCie) {
        this.requiereCie = requiereCie;
    }

    @JsonIgnore
    public Empresa getEmpresa() {
        return empresa;
    }

    @JsonProperty("empresa")
    public void setEmpresa(Empresa empresa) {
        this.empresa = empresa;
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
        if (!(object instanceof CausaAusentismo)) {
            return false;
        }
        CausaAusentismo other = (CausaAusentismo) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "co.sigess.entities.aus.CausaAusentismo[ id=" + id + " ]";
    }
    
}
