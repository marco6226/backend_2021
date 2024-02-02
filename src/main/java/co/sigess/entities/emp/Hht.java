/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.sigess.entities.emp;

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
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author fmoreno
 */
@Entity
@Table(name = "hht", schema = "emp")
@JsonIgnoreProperties(ignoreUnknown = true)
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Hht.findAll", query = "SELECT h FROM Hht h")})
public class Hht implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @SequenceGenerator(name = "hht_id_seq", schema = "emp", sequenceName = "hht_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "hht_id_seq")
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    
    @Column(name = "anio")
    private Integer anio;
    
    @Column(name = "mes")
    private String mes;
    
    @Column(name = "valor")
    private String valor;
        
    @JoinColumn(name = "fk_empresa_id", referencedColumnName = "id")
    private Empresa empresa;

    @Column(name = "empresaselect")
    private String empresaSelect;
    
    @JoinColumn(name = "fk_planta_id", referencedColumnName = "id")
    @ManyToOne
    private Plantas planta;
    
    @Column(name = "numero_personas")
    private long numeroPersonas;
    
    @Column(name = "hht")
    private double hht;
        
    public Hht() {
    }

    public Hht(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getAnio() {
        return anio;
    }

    public void setAnio(Integer anio) {
        this.anio = anio;
    }

    public String getMes() {
        return mes;
    }

    public void setMes(String mes) {
        this.mes = mes;
    }

    public String getValor() {
        return valor;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }

    @JsonIgnore
    public Empresa getEmpresa() {
        return empresa;
    }

    @JsonProperty("empresa")
    public void setEmpresa(Empresa empresa) {
        this.empresa = empresa;
    }
    
        
    public String getEmpresaSelect() {
        return empresaSelect;
    }

    public void setEmpresaSelect(String empresaSelect) {
        this.empresaSelect = empresaSelect;
    }

    public Plantas getPlanta() {
        return planta;
    }

    public void setPlanta(Plantas planta) {
        this.planta = planta;
    }

    public long getNumeroPersonas() {
        return numeroPersonas;
    }

    public void setNumeroPersonas(long numeroPersonas) {
        this.numeroPersonas = numeroPersonas;
    }

    public double getHht() {
        return hht;
    }

    public void setHht(double hht) {
        this.hht = hht;
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
        if (!(object instanceof Hht)) {
            return false;
        }
        Hht other = (Hht) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "co.sigess.entities.emp.Hht[ id=" + id + " ]";
    }
    
}
