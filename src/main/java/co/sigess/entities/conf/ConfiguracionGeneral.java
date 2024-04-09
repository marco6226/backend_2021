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
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.Size;

/**
 *
 * @author fmoreno
 */
@Entity
@Table(name = "configuracion_general", schema = "conf")
public class ConfiguracionGeneral implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @SequenceGenerator(name = "configuracion_general_id_seq", schema = "conf", sequenceName = "configuracion_general_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "configuracion_general_id_seq")
    @Column(name = "id")
    private Long id;
    
    @Size(max = 50)
    @Column(name = "codigo")
    private String codigo;
    
    @Size(max = 128)
    @Column(name = "nombre")
    private String nombre;
    
    @Size(max = 128)
    @Column(name = "valor")
    private String valor;
    
    @Column(name = "fk_empresa_id")
    private Integer empresaId;

    public ConfiguracionGeneral() {
    }

    public ConfiguracionGeneral(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getValor() {
        return valor;
    }

    public void setValor(String valor) {
        this.valor = valor;
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
        if (!(object instanceof ConfiguracionGeneral)) {
            return false;
        }
        ConfiguracionGeneral other = (ConfiguracionGeneral) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "co.sigess.entities.conf.ConfiguracionGeneral[ id=" + id + " ]";
    }
    
}
