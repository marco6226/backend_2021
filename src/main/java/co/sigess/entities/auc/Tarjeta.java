/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.sigess.entities.auc;

import co.sigess.entities.emp.Empresa;
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
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author fmoreno
 */
@Entity
@Table(name = "tarjeta", schema = "auc")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Tarjeta.findAll", query = "SELECT t FROM Tarjeta t")})
public class Tarjeta implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @SequenceGenerator(name = "tarjeta_id_seq", schema = "auc", sequenceName = "tarjeta_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "tarjeta_id_seq")
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    
    @Size(max = 20)
    @Column(name = "codigo")
    private String codigo;
    
    @Size(max = 45)
    @Column(name = "nombre")
    private String nombre;
    
    @Column(name = "campos")
    private String campos;
    
    @Size(max = 255)
    @Column(name = "descripcion")
    private String descripcion;
    
    @JoinColumn(name = "fk_empresa_id", referencedColumnName = "id")
    @ManyToOne
    private Empresa empresa;
    
    @OneToMany(mappedBy = "tarjeta")
    private List<Implicacion> implicacionList;
        
    @Column(name = "usar_nivel_riesgo")
    private Boolean usarNivelRiesgo;
    
    @Column(name = "usar_causa_raiz")
    private Boolean usarCausaRaiz;
    

    public Tarjeta() {
    }

    public Tarjeta(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
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

    public String getCampos() {
        return campos;
    }

    public void setCampos(String campos) {
        this.campos = campos;
    }

    public Empresa getEmpresa() {
        return empresa;
    }

    public void setEmpresa(Empresa empresa) {
        this.empresa = empresa;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public List<Implicacion> getImplicacionList() {
        return implicacionList;
    }

    public void setImplicacionList(List<Implicacion> implicacionList) {
        this.implicacionList = implicacionList;
    }

    public Boolean getUsarNivelRiesgo() {
        return usarNivelRiesgo;
    }

    public void setUsarNivelRiesgo(Boolean usarNivelRiesgo) {
        this.usarNivelRiesgo = usarNivelRiesgo;
    }

    public Boolean getUsarCausaRaiz() {
        return usarCausaRaiz;
    }

    public void setUsarCausaRaiz(Boolean usarCausaRaiz) {
        this.usarCausaRaiz = usarCausaRaiz;
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
        if (!(object instanceof Tarjeta)) {
            return false;
        }
        Tarjeta other = (Tarjeta) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "co.sigess.entities.auc.Tarjeta[ id=" + id + " ]";
    }
    
}
