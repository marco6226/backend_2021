/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.sigess.entities.conf;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
import javax.validation.constraints.Size;

/**
 *
 * @author fmoreno
 */
@Entity
@Table(name = "nivel_riesgo", schema = "conf")
@NamedQueries({
    @NamedQuery(name = "NivelRiesgo.findAll", query = "SELECT n FROM NivelRiesgo n")})
public class NivelRiesgo implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @SequenceGenerator(name = "nivel_riesgo_id_seq", schema = "conf", sequenceName = "nivel_riesgo_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "nivel_riesgo_id_seq")
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;

    @Size(max = 45)
    @Column(name = "nombre")
    private String nombre;

    @Size(max = 255)
    @Column(name = "descripcion")
    private String descripcion;

    @Column(name = "valor_minimo")
    private Float valorMinimo;

    @Column(name = "valor_maximo")
    private Float valorMaximo;

    @Column(name = "gp_minimo")
    private Float gpMinimo;

    @Column(name = "gp_maximo")
    private Float gpMaximo;

    @Column(name = "gr_minimo")
    private Float grMinimo;

    @Column(name = "gr_maximo")
    private Float grMaximo;

    @Size(max = 7)
    @Column(name = "color")
    private String color;

    @JsonIgnore
    @JoinColumn(name = "fk_sistema_nivel_riesgo", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private SistemaNivelRiesgo sistemaNivelRiesgo;

    public NivelRiesgo() {
    }

    public NivelRiesgo(Integer id) {
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

    public SistemaNivelRiesgo getSistemaNivelRiesgo() {
        return sistemaNivelRiesgo;
    }

    public void setSistemaNivelRiesgo(SistemaNivelRiesgo sistemaNivelRiesgo) {
        this.sistemaNivelRiesgo = sistemaNivelRiesgo;
    }

    public Float getValorMinimo() {
        return valorMinimo;
    }

    public void setValorMinimo(Float valorMinimo) {
        this.valorMinimo = valorMinimo;
    }

    public Float getValorMaximo() {
        return valorMaximo;
    }

    public void setValorMaximo(Float valorMaximo) {
        this.valorMaximo = valorMaximo;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public Float getGpMinimo() {
        return gpMinimo;
    }

    public void setGpMinimo(Float gpMinimo) {
        this.gpMinimo = gpMinimo;
    }

    public Float getGpMaximo() {
        return gpMaximo;
    }

    public void setGpMaximo(Float gpMaximo) {
        this.gpMaximo = gpMaximo;
    }

    public Float getGrMinimo() {
        return grMinimo;
    }

    public void setGrMinimo(Float grMinimo) {
        this.grMinimo = grMinimo;
    }

    public Float getGrMaximo() {
        return grMaximo;
    }

    public void setGrMaximo(Float grMaximo) {
        this.grMaximo = grMaximo;
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
        if (!(object instanceof NivelRiesgo)) {
            return false;
        }
        NivelRiesgo other = (NivelRiesgo) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "co.sigess.entities.conf.NivelRiesgo[ id=" + id + " ]";
    }

}
