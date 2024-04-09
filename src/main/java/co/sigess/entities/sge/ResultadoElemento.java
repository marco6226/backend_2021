/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.sigess.entities.sge;

import java.util.List;
import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

/**
 *
 * @author fmoreno
 */
public class ResultadoElemento {

    @Column(name = "promedio")
    private Double promedio;
    
    @Column(name = "ok")
    private Boolean ok;
    
    @Column(name = "maximo_posible")
    private Double maximoPosible;
    
    @Column(name = "calificacion")
    private Double calificacion;
    
    @Column(name = "nombre_calificacion")
    private String nombreCalificacion;
    
    @Column(name = "descripcion_calificacion")
    private String descripcionCalificacion;
    
    @Column(name = "desprecibale")
    private Boolean despreciable;
    
    @Column(name = "elementoId")
    private Integer elementoId;
    
    @Column(name = "elemento_nombre")
    private String elementoNombre;
    
    @JoinColumn(name = "elemento_padre_id", referencedColumnName = "elemento_id")
    @ManyToOne
    private ResultadoElemento elementoPadre;
    
    @OneToMany(mappedBy = "elementoPadre")
    private List<ResultadoElemento> elementoList;

    public Double getPromedio() {
        return promedio;
    }

    public void setPromedio(Double promedio) {
        this.promedio = promedio;
    }

    public Boolean getOk() {
        return ok;
    }

    public void setOk(Boolean ok) {
        this.ok = ok;
    }

    public Double getMaximoPosible() {
        return maximoPosible;
    }

    public void setMaximoPosible(Double maximoPosible) {
        this.maximoPosible = maximoPosible;
    }

    public Double getCalificacion() {
        return calificacion;
    }

    public void setCalificacion(Double calificacion) {
        this.calificacion = calificacion;
    }

    public String getNombreCalificacion() {
        return nombreCalificacion;
    }

    public void setNombreCalificacion(String nombreCalificacion) {
        this.nombreCalificacion = nombreCalificacion;
    }

    public String getDescripcionCalificacion() {
        return descripcionCalificacion;
    }

    public void setDescripcionCalificacion(String descripcionCalificacion) {
        this.descripcionCalificacion = descripcionCalificacion;
    }

    public Boolean getDespreciable() {
        return despreciable;
    }

    public void setDespreciable(Boolean despreciable) {
        this.despreciable = despreciable;
    }

    public Integer getElementoId() {
        return elementoId;
    }

    public void setElementoId(Integer elementoId) {
        this.elementoId = elementoId;
    }

    public String getElementoNombre() {
        return elementoNombre;
    }

    public void setElementoNombre(String elementoNombre) {
        this.elementoNombre = elementoNombre;
    }

    public ResultadoElemento getElementoPadre() {
        return elementoPadre;
    }

    public void setElementoPadre(ResultadoElemento elementoPadre) {
        this.elementoPadre = elementoPadre;
    }

    public List<ResultadoElemento> getElementoList() {
        return elementoList;
    }

    public void setElementoList(List<ResultadoElemento> elementoList) {
        this.elementoList = elementoList;
    }

}
