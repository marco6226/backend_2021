/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.sigess.entities.conf;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
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
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author fmoreno
 */
@Entity
@JsonIgnoreProperties(ignoreUnknown = true)
@Table(name = "consecuencia", schema = "conf")
@XmlRootElement
public class Consecuencia implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @SequenceGenerator(name = "consecuencia_id_seq", schema = "conf", sequenceName = "consecuencia_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "consecuencia_id_seq")
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    
    @Size(max = 45)
    @Column(name = "nombre")
    private String nombre;
    
    @Size(max = 255)
    @Column(name = "descripcion")
    private String descripcion;
    
    @Column(name = "valor")
    private Float valor;
    
    @JoinColumn(name = "fk_sistema_nivel_riesgo", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private SistemaNivelRiesgo sistemaNivelRiesgo;

    public Consecuencia() {
    }

    public Consecuencia(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Float getValor() {
        return valor;
    }

    public void setValor(Float valor) {
        this.valor = valor;
    }

    @JsonIgnore
    public SistemaNivelRiesgo getSistemaNivelRiesgo() {
        return sistemaNivelRiesgo;
    }

    @JsonProperty("sistemaNivelRiesgo")
    public void setSistemaNivelRiesgo(SistemaNivelRiesgo sistemaNivelRiesgo) {
        this.sistemaNivelRiesgo = sistemaNivelRiesgo;
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
        if (!(object instanceof Consecuencia)) {
            return false;
        }
        Consecuencia other = (Consecuencia) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "co.sigess.entities.conf.Consecuencia[ id=" + id + " ]";
    }
    
}
