/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.sigess.entities.ind;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Usuario
 */
@Entity
@JsonIgnoreProperties(ignoreUnknown = true)
@Table(name = "vw_hhtmetas", schema = "ind")
@XmlRootElement
public class viewhhtmetas implements Serializable{

    @Id
    private Integer id;
    
    private String pais;
    private double anio;  

    @Column(name = "fk_planta_id")
    private long plantaId;
    
    @Column(name = "fk_empresa_id")
    private long empresaId;
    
    
    @Column(name = "nombre_planta")
    private String nombrePlanta;
    
    @Column(name = "id_division")
    private String idDivision;
    
    @Column(name = "nombre_division")
    private String nombreDivision;
    private String modulo;
    
    @Column(name = "ili_planta")
    private double iliPlanta;
    
    @Column(name = "ili_division")
    private double iliDivision;
    
    @Column(name = "ili_anual")
    private double iliAnual;
    
    @Column(name = "eve_planta")
    private double evePlanta;
    
    @Column(name = "eve_division")
    private double eveDivision;
    
    @Column(name = "eve_anual")
    private double eveAnual;
    
    @Column(name = "dias_perdidos_planta")
    private double diasPerdidosPlanta;
    
    @Column(name = "dias_perdidos_division")
    private double diasPerdidosDivision;
    
    @Column(name = "dperdidos_anual")
    private double dperdidosAnual;
    
    @Column(name = "tf_planta")
    private double tfPlanta;
    
    @Column(name = "tf_division")
    private double tfDivision;
    
    @Column(name = "tf_anual")
    private double tfAnual;
    
    @Column(name = "ts_planta")
    private double tsPlanta;
    
    @Column(name = "ts_division")
    private double tsDivision;
    
    @Column(name = "ts_anual")
    private double tsAnual;

    public String getNombrePlanta() {
        return nombrePlanta;
    }

    public String getNombreDivision() {
        return nombreDivision;
    }

    
    public Integer getId() {
        return id;
    }

    public String getPais() {
        return pais;
    }

    public double getAnio() {
        return anio;
    }

    public long getPlantaId() {
        return plantaId;
    }

    public long getEmpresaId() {
        return empresaId;
    }

    public String getIdDivision() {
        return idDivision;
    }

    public double getIliAnual() {
        return iliAnual;
    }    

    public double getIliDivision() {
        return iliDivision;
    }

    public double getEveDivision() {
        return eveDivision;
    }

    public double getEveAnual() {
        return eveAnual;
    }

    public double getDiasPerdidosDivision() {
        return diasPerdidosDivision;
    }

    public double getDperdidosAnual() {
        return dperdidosAnual;
    }

    public double getTfDivision() {
        return tfDivision;
    }

    public double getTfAnual() {
        return tfAnual;
    }

    public double getTsDivision() {
        return tsDivision;
    }

    public double getTsAnual() {
        return tsAnual;
    }

    public double getIliPlanta() {
        return iliPlanta;
    }

    public double getEvePlanta() {
        return evePlanta;
    }

    public double getDiasPerdidosPlanta() {
        return diasPerdidosPlanta;
    }

    public double getTfPlanta() {
        return tfPlanta;
    }

    public double getTsPlanta() {
        return tsPlanta;
    }

    public String getModulo() {
        return modulo;
    }
    
}
