/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.sigess.entities.ind;

import co.sigess.entities.ado.Directorio;
import com.fasterxml.jackson.annotation.JsonIgnore;
import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author fmoreno
 */
@Entity
@Table(name = "tablero", schema = "ind")
@XmlRootElement
public class Tablero implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @SequenceGenerator(name = "tablero_id_seq", schema = "ind", sequenceName = "tablero_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "tablero_id_seq")
    @Basic(optional = false)
    @Column(name = "id")
    private Long id;
    
    @Size(max = 128, message = "El campo nombre no puede exceder 128 caracteres")
    @Column(name = "nombre")
    private String nombre;
    
    @Column(name = "plantilla")
    private String plantilla;
    
    @Column(name = "kpis")
    private String kpis;
    
    @Size(max = 256, message = "El campo descripcion no puede exceder 256 caracteres")
    @Column(name = "descripcion")
    private String descripcion;
    
    @Column(name = "fk_empresa_id")
    private Integer empresaId;
    
    @JsonIgnore
    @OneToMany(mappedBy = "tablero")
    private List<AccesoTablero> accesoTableroList;

    public Tablero() {
    }

    public Tablero(Long id) {
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

    public String getPlantilla() {
        return plantilla;
    }

    public void setPlantilla(String plantilla) {
        this.plantilla = plantilla;
    }

    public String getKpis() {
        return kpis;
    }

    public void setKpis(String kpis) {
        this.kpis = kpis;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Integer getEmpresaId() {
        return empresaId;
    }

    public void setEmpresaId(Integer empresaId) {
        this.empresaId = empresaId;
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
        if (!(object instanceof Tablero)) {
            return false;
        }
        Tablero other = (Tablero) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "co.sigess.entities.ind.Tablero[ id=" + id + " ]";
    }
    
}
