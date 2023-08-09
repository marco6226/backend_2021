/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.sigess.entities.inp;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author JULIO
 */
@Entity
@JsonIgnoreProperties(ignoreUnknown = false)
@Table(schema = "inp", name = "vw_resumen_aliados")
@XmlRootElement
public class ViewResumenAliado implements Serializable {
    
    @Column(name = "id")
    @Id
    private long id;
    
    @Column(name = "aliado")
    private String aliado;
    
    @Column(name = "programadas")
    private int programadas;
    
    @Column(name = "ejecutadas")
    private int ejecutadas;
    
    @Column(name = "no_programadas")
    private int noProgramadas;
    
    @Column(name = "calificacion_acumulada")
    private Double calificacionAcumulada;
    
    @Column(name = "porcentaje_avance")
    private Double porcentajeAvance;
    
    @Column(name = "id_empresa_aliada")
    private long idEmpresaAliada;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getAliado() {
        return aliado;
    }

    public void setAliado(String aliado) {
        this.aliado = aliado;
    }

    public int getProgramadas() {
        return programadas;
    }

    public void setProgramadas(int programadas) {
        this.programadas = programadas;
    }

    public int getEjecutadas() {
        return ejecutadas;
    }

    public void setEjecutadas(int ejecutadas) {
        this.ejecutadas = ejecutadas;
    }

    public int getNoProgramadas() {
        return noProgramadas;
    }

    public void setNoProgramadas(int noProgramadas) {
        this.noProgramadas = noProgramadas;
    }

    public Double getCalificacionAcumulada() {
        return calificacionAcumulada;
    }

    public void setCalificacionAcumulada(Double calificacionAcumulada) {
        this.calificacionAcumulada = calificacionAcumulada;
    }

    public Double getPorcentajeAvance() {
        return porcentajeAvance;
    }

    public void setPorcentajeAvance(Double porcentajeAvance) {
        this.porcentajeAvance = porcentajeAvance;
    }

    public long getIdEmpresaAliada() {
        return idEmpresaAliada;
    }

    public void setIdEmpresaAliada(long idEmpresaAliada) {
        this.idEmpresaAliada = idEmpresaAliada;
    }
}
