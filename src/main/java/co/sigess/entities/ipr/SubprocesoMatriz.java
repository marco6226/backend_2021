/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.sigess.entities.ipr;

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
 * @author Usuario
 */

@Entity
@Table(name = "subproceso_matriz", schema = "ipr")
@XmlRootElement
@NamedQueries({
@NamedQuery(name = "SubprocesoMatriz.findAll", query = "SELECT sm FROM SubprocesoMatriz sm")})
public class SubprocesoMatriz implements Serializable{
    
    private static final long serialVersionUID = 1L;
    @Id
    @SequenceGenerator(name = "subproceso_matriz_id_seq", schema = "ipr", sequenceName = "subproceso_matriz_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "subproceso_matriz_id_seq")
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;  
    
    @Column(name = "nombre")
    private String nombre;
    
    @JoinColumn(name = "fk_proceso_matriz_id", referencedColumnName = "id", updatable = false)
    @ManyToOne(optional = false)
    private ProcesoMatriz procesoMatriz;
    
    @Column(name = "estado")
    private String estado;
    
    @Column(name = "eliminado")
    private Boolean eliminado;

    public Boolean getEliminado() {
        return eliminado;
    }

    public void setEliminado(Boolean eliminado) {
        this.eliminado = eliminado;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
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

    public ProcesoMatriz getProcesoMatriz() {
        return procesoMatriz;
    }

    public void setProcesoMatriz(ProcesoMatriz procesoMatriz) {
        this.procesoMatriz = procesoMatriz;
    }
}
