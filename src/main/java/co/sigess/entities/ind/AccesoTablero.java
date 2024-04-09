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
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author fmoreno
 */
@Entity
@Table(name = "acceso_tablero", schema = "ind")
@XmlRootElement
public class AccesoTablero implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @SequenceGenerator(name = "acceso_tablero_id_seq", schema = "ind", sequenceName = "acceso_tablero_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "acceso_tablero_id_seq")
    @Basic(optional = false)
    @Column(name = "id")
    private Long id;
    
    @Column(name = "consultar")
    private Boolean consultar;
    
    @Column(name = "modificar")
    private Boolean modificar;
    
    @Column(name = "propietario")
    private Boolean propietario;
    
    @Column(name = "fk_usuario_id")
    private Integer usuarioId;
    
    
    @JoinColumn(name = "fk_tablero_id", referencedColumnName = "id")
    private Tablero tablero;

    public AccesoTablero() {
    }

    public AccesoTablero(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean getConsultar() {
        return consultar;
    }

    public void setConsultar(Boolean consultar) {
        this.consultar = consultar;
    }

    public Boolean getModificar() {
        return modificar;
    }

    public void setModificar(Boolean modificar) {
        this.modificar = modificar;
    }

    public Boolean getPropietario() {
        return propietario;
    }

    public void setPropietario(Boolean propietario) {
        this.propietario = propietario;
    }

    public Integer getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(Integer usuarioId) {
        this.usuarioId = usuarioId;
    }

    public Tablero getTablero() {
        return tablero;
    }

    public void setTablero(Tablero tablero) {
        this.tablero = tablero;
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
        if (!(object instanceof AccesoTablero)) {
            return false;
        }
        AccesoTablero other = (AccesoTablero) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "co.sigess.entities.ind.AccesoTablero[ id=" + id + " ]";
    }
    
}
