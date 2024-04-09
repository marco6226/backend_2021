/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.sigess.entities.sge;

import co.sigess.entities.emp.Empresa;
import com.fasterxml.jackson.annotation.JsonIgnore;
import java.io.Serializable;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author fmoreno
 */
@Entity
@Table(name = "sistema_gestion", schema = "sge")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "SistemaGestion.findAll", query = "SELECT s FROM SistemaGestion s")})
public class SistemaGestion implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected SistemaGestionPK sistemaGestionPK;
    
    @Size(max = 100)
    @NotNull(message = "El campo nombre es requerido")
    @Column(name = "nombre")
    private String nombre;
    
    @Size(max = 45)
    @NotNull(message = "El campo nombre es requerido")
    @Column(name = "codigo")
    private String codigo;
    
    @Size(max = 1024)
    @Column(name = "descripcion")
    private String descripcion;
    
    @Column(name = "numero_preguntas")
    private Integer numeroPreguntas;
    
    @NotNull
    @JoinColumn(name = "fk_empresa_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Empresa empresa;
        
    @OrderBy(value = "id")
    @OneToMany(mappedBy = "sistemaGestion")
    private List<Elemento> elementoList;
    
    @JsonIgnore
    @OrderBy("fechaInicio")
    @OneToMany(mappedBy = "sistemaGestion")
    private List<Evaluacion> evaluacionList;

    public SistemaGestion() {
    }

    public SistemaGestion(SistemaGestionPK sistemaGestionPK) {
        this.sistemaGestionPK = sistemaGestionPK;
    }

    public SistemaGestion(int id, short version) {
        this.sistemaGestionPK = new SistemaGestionPK(id, version);
    }

    public SistemaGestionPK getSistemaGestionPK() {
        return sistemaGestionPK;
    }

    public void setSistemaGestionPK(SistemaGestionPK sistemaGestionPK) {
        this.sistemaGestionPK = sistemaGestionPK;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Empresa getEmpresa() {
        return empresa;
    }

    public void setEmpresa(Empresa empresa) {
        this.empresa = empresa;
    }

//    @XmlTransient
//    @JsonIgnore
    public List<Elemento> getElementoList() {
        return elementoList;
    }

    public void setElementoList(List<Elemento> elementoList) {
        this.elementoList = elementoList;
    }

//    @XmlTransient
    @JsonIgnore
    public List<Evaluacion> getEvaluacionList() {
        return evaluacionList;
    }

    public void setEvaluacionList(List<Evaluacion> evaluacionList) {
        this.evaluacionList = evaluacionList;
    }

    public Integer getNumeroPreguntas() {
        return numeroPreguntas;
    }

    public void setNumeroPreguntas(Integer numeroPreguntas) {
        this.numeroPreguntas = numeroPreguntas;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (sistemaGestionPK != null ? sistemaGestionPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SistemaGestion)) {
            return false;
        }
        SistemaGestion other = (SistemaGestion) object;
        if ((this.sistemaGestionPK == null && other.sistemaGestionPK != null) || (this.sistemaGestionPK != null && !this.sistemaGestionPK.equals(other.sistemaGestionPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "co.sigess.entities.sgeSistemaGestion[ sistemaGestionPK=" + sistemaGestionPK + " ]";
    }
    
}
