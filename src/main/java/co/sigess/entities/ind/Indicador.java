/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.sigess.entities.ind;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author fmoreno
 */
@Entity
@Table(name = "indicador", schema = "ind")
@XmlRootElement
public class Indicador implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @Basic(optional = false)
    @Column(name = "id")
    private Long id;
    
    @Size(max = 128, message = "El campo descripcion no puede superar 128 caracteres")
    @Column(name = "nombre")
    private String nombre;
    
    @Size(max = 512, message = "El campo descripcion no puede superar 512 caracteres")
    @Column(name = "descripcion")
    private String descripcion;
    
    @Size(max = 512, message = "El campo proceso no puede superar 512 caracteres")
    @Column(name = "proceso")
    private String proceso;
    
    @Size(max = 256, message = "El campo formulacion no puede superar 256 caracteres")
    @Column(name = "formulacion")
    private String formulacion;
    
    @Column(name = "consulta_sql")
    private String consultaSql;
    
    @Size(max = 45, message = "El campo modulo no puede superar 45 caracteres")
    @Column(name = "modulo")
    private String modulo;

    public Indicador() {
    }

    public Indicador(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getProceso() {
        return proceso;
    }

    public void setProceso(String proceso) {
        this.proceso = proceso;
    }

    public String getFormulacion() {
        return formulacion;
    }

    public void setFormulacion(String formulacion) {
        this.formulacion = formulacion;
    }

    public String getConsultaSql() {
        return consultaSql;
    }

    public void setConsultaSql(String consultaSql) {
        this.consultaSql = consultaSql;
    }

    public String getModulo() {
        return modulo;
    }

    public void setModulo(String modulo) {
        this.modulo = modulo;
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
        if (!(object instanceof Indicador)) {
            return false;
        }
        Indicador other = (Indicador) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "co.sigess.entities.ind.Indicador[ id=" + id + " ]";
    }
    
}
