/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.sigess.entities.sec;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.io.Serializable;
import java.util.List;
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
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author fmoreno
 */
@Entity
@Table(name = "causa_inmediata", schema = "sec")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "CausaInmediata.findAll", query = "SELECT c FROM CausaInmediata c")})
public class CausaInmediata implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;

    @Size(max = 255)
    @Column(name = "nombre")
    private String nombre;
    
    @OneToMany(mappedBy = "causaInmediata")
    private List<CausaInmediata> causaInmediataList;
    
    @JoinColumn(name = "fk_causa_inmediata_id", referencedColumnName = "id")
    @ManyToOne
    private CausaInmediata causaInmediata;
    
    @JoinColumn(name = "fk_sistema_causa_inmediata_id", referencedColumnName = "id")
    @ManyToOne
    private SistemaCausaInmediata sistemaCausaInmediata;

    public CausaInmediata() {
    }

    public CausaInmediata(Integer id) {
        this.id = id;
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

    public List<CausaInmediata> getCausaInmediataList() {
        return causaInmediataList;
    }

    public void setCausaInmediataList(List<CausaInmediata> causaInmediataList) {
        this.causaInmediataList = causaInmediataList;
    }

    @JsonIgnore
    public CausaInmediata getCausaInmediata() {
        return causaInmediata;
    }

    @JsonProperty(value = "causaInmediata")
    public void setCausaInmediata(CausaInmediata causaInmediata) {
        this.causaInmediata = causaInmediata;
    }

    @JsonIgnore
    public SistemaCausaInmediata getSistemaCausaInmediata() {
        return sistemaCausaInmediata;
    }

    @JsonProperty(value = "sistemaCausaInmediata")
    public void setSistemaCausaInmediata(SistemaCausaInmediata sistemaCausaInmediata) {
        this.sistemaCausaInmediata = sistemaCausaInmediata;
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
        if (!(object instanceof CausaInmediata)) {
            return false;
        }
        CausaInmediata other = (CausaInmediata) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "co.sigess.entities.sec.CausaInmediata[ id=" + id + " ]";
    }
    
}
