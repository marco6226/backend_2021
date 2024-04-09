/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.sigess.entities.inp;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Julio
 */
@Entity
@JsonIgnoreProperties(ignoreUnknown = true)
@Table(schema = "inp", name = "cumplimiento")
@XmlRootElement
@NamedQueries(
        @NamedQuery(name = "Cumplimiento.findAll", query = "SELECT c FROM Cumplimiento c")
)
public class Cumplimiento implements Serializable{
    
    @Id
    @SequenceGenerator(name = "cumplimiento_id_seq", schema = "inp", sequenceName = "cumplimiento_id_seq", allocationSize = 1)
    @GeneratedValue(generator = "cumplimiento_id_seq", strategy = GenerationType.SEQUENCE)
    @Basic(optional = false)
    @Column(name = "id")
    private long id;
    
    @JoinColumn(name = "fk_elemento_inspeccion_id", referencedColumnName = "id")
    @OneToOne
    private ElementoInspeccion elementoInspeccion;
    
    @Column(name = "porcentaje_cumplimiento")
    private double porcentajeCumplimiento;
    
    @Column(name = "aplica")
    private boolean aplica;
    
    @Column(name = "fk_inspeccion_id")
    private long inspeccion;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public ElementoInspeccion getElementoInspeccion() {
        return elementoInspeccion;
    }

    public void setElementoInspeccion(ElementoInspeccion elementoInspeccion) {
        this.elementoInspeccion = elementoInspeccion;
    }

    public double getPorcentajeCumplimiento() {
        return porcentajeCumplimiento;
    }

    public void setPorcentajeCumplimiento(double porcentajeCumplimiento) {
        this.porcentajeCumplimiento = porcentajeCumplimiento;
    }

    public boolean isAplica() {
        return aplica;
    }

    public void setAplica(boolean aplica) {
        this.aplica = aplica;
    }

    public long getInspeccion() {
        return inspeccion;
    }

    public void setInspeccion(long inspeccion) {
        this.inspeccion = inspeccion;
    }
}
