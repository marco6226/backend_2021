/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.sigess.entities.conf;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.Size;

/**
 *
 * @author fmoreno
 */
@Entity
@Table(name = "respuesta_campo", schema = "conf")
@NamedQueries({
    @NamedQuery(name = "RespuestaCampo.findAll", query = "SELECT r FROM RespuestaCampo r")})
public class RespuestaCampo implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @SequenceGenerator(name = "respuesta_campo_id_seq", schema = "conf", sequenceName = "respuesta_campo_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "respuesta_campo_id_seq")
    @Basic(optional = false)
    @Column(name = "id")
    private Long id;
    
    @Size(max = 2147483647)
    @Column(name = "valor")
    private String valor;
    
    
    @Column(name = "fk_campo_id")
    private Long campoId;

    public RespuestaCampo() {
    }

    public RespuestaCampo(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getValor() {
        return valor;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }

    public Long getCampoId() {
        return campoId;
    }

    public void setCampoId(Long campoId) {
        this.campoId = campoId;
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
        if (!(object instanceof RespuestaCampo)) {
            return false;
        }
        RespuestaCampo other = (RespuestaCampo) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "co.sigess.entities.conf.RespuestaCampo[ id=" + id + " ]";
    }
    
}
