/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.sigess.entities.ipr;

import co.sigess.entities.emp.Empresa;
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
@Table(name = "proceso_matriz", schema = "ipr")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ProcesoMatriz.findAll", query = "SELECT pm FROM ProcesoMatriz pm")})
public class ProcesoMatriz implements Serializable{
    private static final long serialVersionUID = 1L;
    @Id
    @SequenceGenerator(name = "proceso_matriz_id_seq", schema = "ipr", sequenceName = "proceso_matriz_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "proceso_matriz_id_seq")
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    
    @Column(name = "nombre")
    private String nombre;
    
    @JoinColumn(name = "fk_empresa_id", referencedColumnName = "id", updatable = false)
    @ManyToOne(optional = false)
    private Empresa empresa;
    
    @JoinColumn(name = "fk_area_matriz_id", referencedColumnName = "id", updatable = false)
    @ManyToOne(optional = false)
    private AreaMatriz areaMatriz;
    
    @Column(name = "estado")
    private String estado;

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public AreaMatriz getAreaMatriz() {
        return areaMatriz;
    }

    public void setAreaMatriz(AreaMatriz areaMatriz) {
        this.areaMatriz = areaMatriz;
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

    public Empresa getEmpresa() {
        return empresa;
    }

    public void setEmpresa(Empresa empresa) {
        this.empresa = empresa;
    }
}
