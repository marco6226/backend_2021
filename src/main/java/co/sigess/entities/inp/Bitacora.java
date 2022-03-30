/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package co.sigess.entities.inp;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

/**
 *
 * @author John Rueda
 */
@Entity
@Table(name = "bitacora", schema = "inp")
public class Bitacora implements Serializable{
    
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "id")   
    private Integer id;

    @Basic(optional = false)
    @NotNull
    @Column(name = "fecha")
    private Date fecha;

    @Basic(optional = false)
    @NotNull
    @Column(name = "observacion")
    private String observacion;

    @Basic(optional = false)
    @NotNull
    @Column(name = "pk_numero_economico_id")
    private Integer pk_numero_economico_id;

    @Basic(optional = false)
    @NotNull
    @Column(name = "pk_inspeccion_id")
    private Integer pk_inspeccion_id;

    public Integer getPk_inspeccion_id() {
        return pk_inspeccion_id;
    }

    public void setPk_inspeccion_id(Integer pk_inspeccion_id) {
        this.pk_inspeccion_id = pk_inspeccion_id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public String getObservacion() {
        return observacion;
    }

    public void setObservacion(String observacion) {
        this.observacion = observacion;
    }

    public Integer getPk_numero_economico_id() {
        return pk_numero_economico_id;
    }

    public void setPk_numero_economico_id(Integer pk_numero_economico_id) {
        this.pk_numero_economico_id = pk_numero_economico_id;
    }


}
