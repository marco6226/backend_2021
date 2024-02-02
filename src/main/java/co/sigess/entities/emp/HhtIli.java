/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.sigess.entities.emp;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
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
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Julio
 */
@Entity
@Table(name = "hht_ili", schema = "emp")
@JsonIgnoreProperties(ignoreUnknown = true)
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "HhtIli.findAll", query = "SELECT hi FROM HhtIli hi")})
public class HhtIli implements Serializable{
    
    private static final long serialVersionUID = 1L;
    
    @Id
    @SequenceGenerator(name = "hht_ili_id_seq", schema = "emp", sequenceName = "hht_ili_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "hht_ili_id_seq")
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    
    @Column(name = "id_division")
    private long idDivision;
    
    @Column(name = "anio")
    private int anio;
    
    @Column(name = "ili_division")
    private double iliDivision;
    
    @Column(name = "ili_empresa")
    private double iliEmpresa;
    
    @Column(name = "id_empresa")
    private long idEmpresa;
    
    @Column(name = "pais")
    private String pais;
    
    @Column(name = "empresa_selected")
    private long empresaSelected;
    
    public HhtIli() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public long getIdDivision() {
        return idDivision;
    }

    public void setIdDivision(long idDivision) {
        this.idDivision = idDivision;
    }

    public long getIdEmpresa() {
        return idEmpresa;
    }

    public void setIdEmpresa(long idEmpresa) {
        this.idEmpresa = idEmpresa;
    }

    public int getAnio() {
        return anio;
    }

    public void setAnio(int anio) {
        this.anio = anio;
    }

    public double getIliDivision() {
        return iliDivision;
    }

    public void setIliDivision(double iliDivision) {
        this.iliDivision = iliDivision;
    }

    public double getIliEmpresa() {
        return iliEmpresa;
    }

    public void setIliEmpresa(double iliEmpresa) {
        this.iliEmpresa = iliEmpresa;
    }

    public String getPais() {
        return pais;
    }

    public void setPais(String pais) {
        this.pais = pais;
    }

    public long getEmpresaSelected() {
        return empresaSelected;
    }

    public void setEmpresaSelected(long empresa) {
        this.empresaSelected = empresa;
    }
    
}
