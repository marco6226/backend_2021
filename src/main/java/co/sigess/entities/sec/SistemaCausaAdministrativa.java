/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.sigess.entities.sec;

import co.sigess.util.converter.JsonListConverter;
import java.io.Serializable;
import java.util.List;
import javax.json.JsonObject;
import javax.persistence.Column;
import javax.persistence.Convert;
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
@Table(name = "sistema_causa_admin", schema = "sec")
@NamedQueries({
    @NamedQuery(name = "SistemaCausaAdministrativa.findAll", query = "SELECT s FROM SistemaCausaAdministrativa s")})
public class SistemaCausaAdministrativa implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @SequenceGenerator(name = "sistema_causa_admin_id_seq", schema = "emp", sequenceName = "sistema_causa_admin_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sistema_causa_admin_id_seq")
    @Column(name = "id")
    private Integer id;

    @Size(max = 50)
    @Column(name = "nombre")
    private String nombre;

    @Convert(converter = JsonListConverter.class)
    @Column(name = "causa_admin")
    private List<JsonObject> causaAdminList;

    @Column(name = "selected")
    private Boolean selected;
    
    @Column(name = "fk_empresa_id")
    private Integer empresaId;

    public SistemaCausaAdministrativa() {
    }

    public SistemaCausaAdministrativa(Integer id) {
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

    public List<JsonObject> getCausaAdminList() {
        return causaAdminList;
    }

    public void setCausaAdminList(List<JsonObject> causaAdminList) {
        this.causaAdminList = causaAdminList;
    }

    public Integer getEmpresaId() {
        return empresaId;
    }

    public void setEmpresaId(Integer empresaId) {
        this.empresaId = empresaId;
    }

    public Boolean getSelected() {
        return selected;
    }

    public void setSelected(Boolean selected) {
        this.selected = selected;
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
        if (!(object instanceof SistemaCausaAdministrativa)) {
            return false;
        }
        SistemaCausaAdministrativa other = (SistemaCausaAdministrativa) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "co.sigess.entities.sec.SistemaCausaAdministrativa[ id=" + id + " ]";
    }

}
