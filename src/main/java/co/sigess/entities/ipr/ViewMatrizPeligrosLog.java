/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.sigess.entities.ipr;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Usuario
 */
@Entity
@Table(name = "view_matriz_peligros_log", schema = "ipr")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ViewMatrizPeligrosLog.findAll", query = "SELECT vmp FROM ViewMatrizPeligrosLog vmp")})
public class ViewMatrizPeligrosLog implements Serializable {
    private static final long serialVersionUID = 1L;

    @Column(name = "id")
    @Id
    private Integer id;
    
    @Column(name = "id_riesgo")    
    private Long idRiesgo;
    
    @Column(name = "fk_planta_id")    
    private Long idplantas;
    
    private String division;
    private String planta;
    private String area;
    private String proceso; 
    private String subproceso; 
    private String actividades; 
    private String rutinaria; 
    private String propios; 
    private String temporales; 
    private String contratistas; 
    private Integer total; 
    private String peligro; 
    
    @Column(name = "descripcion_peligro")
    private String descripcionPeligro; 
    
    @Column(name = "fuente_generadora")
    private String fuenteGeneradora; 
    
    private String efectos; 
    private String ingenieria; 
    private String administrativos; 
    private String elementospro; 
    
    @Column(name = "nd_inicial")
    private String ndInicial; 
    
    @Column(name = "ne_inicial")
    private String neInicial; 
    
    @Column(name = "np_inicial")
    private String npInicial; 
    
    @Column(name = "np_interpretacion_inicial")
    private String npInterpretacionInicial; 
    
    @Column(name = "nc_inicial")
    private String ncInicial; 
    
    @Column(name = "nr_inicial")
    private String nrInicial; 
    
    @Column(name = "cuantitativo_inicial")
    private String cuantitativoInicial; 
    
    @Column(name = "cualitativo_inicial")
    private String cualitativoInicial; 
    
    @Column(name = "descripcion_inicial")
    private String descripcionInicial;
    
    private Long matrizrexistente; 
    private String accmayor; 
    private String realizovaloracion; 
    
    @Column(name = "plan_accion_existente")
    private String planAccionExistente; 

    private Long matrizeriesgo; 
    
    @Column(name = "plan_accion")
    private String planAccion; 
    
    @Column(name = "nd_residual")
    private String ndResidual; 
    
    @Column(name = "ne_residual")
    private String neResidual; 
    
    @Column(name = "np_residual")
    private String npResidual; 
    
    @Column(name = "np_interpretacion_residual")
    private String npInterpretacionResidual;
    
    @Column(name = "nc_residual")
    private String ncResidual; 
    
    @Column(name = "nr_residual")
    private String nrResidual; 
    
    @Column(name = "cuantitativo_residual")
    private String cuantitativoResidual;
    
    @Column(name = "cualitativo_residual")
    private String cualitativoResidual;
    
    @Column(name = "descripcion_residual")
    private String descripcionResidual;
    
    private Long matrizrresidual; 
    
    @Column(name = "controles_ejecutados")
    private String controlesEjecutados;
        
    @Column(name = "controles_propuestos")
    private String controlesPropuestos;
            
    private String cumplimiento;
                
    @Column(name = "at_asociados")
    private String atAsociados;
                    
    @Column(name = "el_asociados")
    private String elAsociados;
                        
    private String estado;
    private Long icr;
    
    @Column(name = "fecha_edicion")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaEdicion;
    
    private String nombre_mes;
    private Long anio;

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public Integer getId() {
        return id;
    }

    public Long getIdRiesgo() {
        return idRiesgo;
    }

    public Long getIdplantas() {
        return idplantas;
    }

    public String getDivision() {
        return division;
    }

    public String getPlanta() {
        return planta;
    }

    public String getArea() {
        return area;
    }

    public String getProceso() {
        return proceso;
    }

    public String getSubproceso() {
        return subproceso;
    }

    public String getActividades() {
        return actividades;
    }

    public String getRutinaria() {
        return rutinaria;
    }

    public String getPropios() {
        return propios;
    }

    public String getTemporales() {
        return temporales;
    }

    public String getContratistas() {
        return contratistas;
    }

    public Integer getTotal() {
        return total;
    }

    public String getPeligro() {
        return peligro;
    }

    public String getDescripcionPeligro() {
        return descripcionPeligro;
    }

    public String getFuenteGeneradora() {
        return fuenteGeneradora;
    }

    public String getEfectos() {
        return efectos;
    }

    public String getIngenieria() {
        return ingenieria;
    }

    public String getAdministrativos() {
        return administrativos;
    }

    public String getElementospro() {
        return elementospro;
    }

    public String getNdInicial() {
        return ndInicial;
    }

    public String getNeInicial() {
        return neInicial;
    }

    public String getNpInicial() {
        return npInicial;
    }

    public String getNpInterpretacionInicial() {
        return npInterpretacionInicial;
    }

    public String getNcInicial() {
        return ncInicial;
    }

    public String getNrInicial() {
        return nrInicial;
    }

    public String getCuantitativoInicial() {
        return cuantitativoInicial;
    }

    public String getCualitativoInicial() {
        return cualitativoInicial;
    }

    public String getDescripcionInicial() {
        return descripcionInicial;
    }
    
    public Long getMatrizrexistente() {
        return matrizrexistente;
    }

    public String getAccmayor() {
        return accmayor;
    }

    public String getRealizovaloracion() {
        return realizovaloracion;
    }

    public String getPlanAccionExistente() {
        return planAccionExistente;
    }

    public Long getMatrizeriesgo() {
        return matrizeriesgo;
    }

    public String getPlanAccion() {
        return planAccion;
    }

    public String getNdResidual() {
        return ndResidual;
    }

    public String getNeResidual() {
        return neResidual;
    }

    public String getNpResidual() {
        return npResidual;
    }

    public String getNpInterpretacionResidual() {
        return npInterpretacionResidual;
    }

    public String getNcResidual() {
        return ncResidual;
    }

    public String getNrResidual() {
        return nrResidual;
    }

    public String getCuantitativoResidual() {
        return cuantitativoResidual;
    }

    public String getCualitativoResidual() {
        return cualitativoResidual;
    }

    public String getDescripcionResidual() {
        return descripcionResidual;
    }

    public Long getMatrizrresidual() {
        return matrizrresidual;
    }
    
    public String getControlesEjecutados() {
        return controlesEjecutados;
    }

    public String getControlesPropuestos() {
        return controlesPropuestos;
    }

    public String getCumplimiento() {
        return cumplimiento;
    }

    public String getAtAsociados() {
        return atAsociados;
    }

    public String getElAsociados() {
        return elAsociados;
    }

    public String getEstado() {
        return estado;
    }

    public Long getIcr() {
        return icr;
    }

    public Date getFechaEdicion() {
        return fechaEdicion;
    }

    public String getNombre_mes() {
        return nombre_mes;
    }

    public Long getAnio() {
        return anio;
    }
    
    
}
