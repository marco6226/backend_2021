/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.sigess.entities.rai;

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
@Table(name = "esquema_carga", schema = "rai")
public class EsquemaCarga implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @SequenceGenerator(name = "esquema_carga_id_seq", schema = "rai", sequenceName = "esquema_carga_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "esquema_carga_id_seq")
    @Basic(optional = false)
    @Column(name = "id")
    private Long id;
    
    @Size(max = 128)
    @Column(name = "campo_entidad")
    private String campoEntidad;
    
    @Size(max = 45)
    @Column(name = "tipo_dato_leido")
    private String tipoDatoLeido;
    
    @Size(max = 45)
    @Column(name = "formato_campo_leido")
    private String formatoCampoLeido;
    
    @Column(name = "posicion_columna")
    private Integer posicionColumna;
    
    @Column(name = "fk_empresa_id")
    private Integer empresaId;

    public EsquemaCarga() {
    }

    public EsquemaCarga(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCampoEntidad() {
        return campoEntidad;
    }

    public void setCampoEntidad(String campoEntidad) {
        this.campoEntidad = campoEntidad;
    }

    public String getTipoDatoLeido() {
        return tipoDatoLeido;
    }

    public void setTipoDatoLeido(String tipoDatoLeido) {
        this.tipoDatoLeido = tipoDatoLeido;
    }

    public String getFormatoCampoLeido() {
        return formatoCampoLeido;
    }

    public void setFormatoCampoLeido(String formatoCampoLeido) {
        this.formatoCampoLeido = formatoCampoLeido;
    }

    public Integer getPosicionColumna() {
        return posicionColumna;
    }

    public void setPosicionColumna(Integer posicionColumna) {
        this.posicionColumna = posicionColumna;
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
        if (!(object instanceof EsquemaCarga)) {
            return false;
        }
        EsquemaCarga other = (EsquemaCarga) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "co.sigess.entities.rai.EsquemaCarga[ id=" + id + " ]";
    }
    
}
