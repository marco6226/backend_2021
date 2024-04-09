/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.sigess.entities.rai;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
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
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.Size;

/**
 *
 * @author fmoreno
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@Entity
@Table(name = "testigo_reporte", schema = "rai")
@NamedQueries({
    @NamedQuery(name = "TestigoReporte.findAll", query = "SELECT t FROM TestigoReporte t")})
public class TestigoReporte implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @SequenceGenerator(name = "testigo_reporte_id_seq", schema = "rai", sequenceName = "testigo_reporte_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "testigo_reporte_id_seq")
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    
    @Size(max = 100)
    @Column(name = "nombres")
    private String nombres;
    
    @Size(max = 100)
    @Column(name = "apellidos")
    private String apellidos;
    
    @Size(max = 100)
    @Column(name = "cargo")
    private String cargo;
    
    @Size(max = 25)
    @Column(name = "tipo_identificacion")
    private String tipoIdentificacion;
    @Size(max = 20)
    @Column(name = "numero_identificacion")
    private String numeroIdentificacion;
    
    @JsonIgnore
    @JoinColumn(name = "fk_reporte_id", referencedColumnName = "id")
    @ManyToOne
    private Reporte reporte;

    public TestigoReporte() {
    }

    public TestigoReporte(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNombres() {
        return nombres;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getCargo() {
        return cargo;
    }

    public void setCargo(String cargo) {
        this.cargo = cargo;
    }

    public String getTipoIdentificacion() {
        return tipoIdentificacion;
    }

    public void setTipoIdentificacion(String tipoIdentificacion) {
        this.tipoIdentificacion = tipoIdentificacion;
    }

    public String getNumeroIdentificacion() {
        return numeroIdentificacion;
    }

    public void setNumeroIdentificacion(String numeroIdentificacion) {
        this.numeroIdentificacion = numeroIdentificacion;
    }

    public Reporte getReporte() {
        return reporte;
    }

    public void setReporte(Reporte reporte) {
        this.reporte = reporte;
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
        if (!(object instanceof TestigoReporte)) {
            return false;
        }
        TestigoReporte other = (TestigoReporte) object;
        
        if (this.id == null || other.id == null) {
            return false;
        }
        if (this.id == other.id) {
            return true;
        }
        return false;
    }

    @Override
    public String toString() {
        return "co.sigess.entities.rai.TestigoReporte[ id=" + id + " ]";
    }
    
}
