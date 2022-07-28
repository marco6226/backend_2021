/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package co.sigess.entities.scm;

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
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author John Rueda
 */
@Entity
@Table(name = "reintegros", schema = "scm")
@XmlRootElement
//@NamedQueries({
//    @NamedQuery(name = "reintegros.findAll", query = "SELECT s FROM Reintegros s WHERE s.eliminado = true" ),
//})
public class Reintegro implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @SequenceGenerator(name = "reintegro_id_seq", schema = "scm", sequenceName = "reintegro_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "reintegro_id_seq")
    @Column(name = "id")
    private Long id;

    @Column(name = "tipo_retorno")
    private String tipo_retorno;

    @Column(name = "descripcion")
    private String descripcion;

    @Column(name = "permanencia")
    private String permanencia;

    @Column(name = "periodo_seguimiento")
    private String periodo_seguimiento;

    @Column(name = "reintegro_exitoso")
    private String reintegro_exitoso;

    @Column(name = "fecha_cierre")
    private String fecha_cierre;

    @Column(name = "observacion")
    private String observacion;

    @Column(name = "pk_case")
    private String pk_case;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTipo_retorno() {
        return tipo_retorno;
    }

    public void setTipo_retorno(String tipo_retorno) {
        this.tipo_retorno = tipo_retorno;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getPermanencia() {
        return permanencia;
    }

    public void setPermanencia(String permanencia) {
        this.permanencia = permanencia;
    }

    public String getPeriodo_seguimiento() {
        return periodo_seguimiento;
    }

    public void setPeriodo_seguimiento(String periodo_seguimiento) {
        this.periodo_seguimiento = periodo_seguimiento;
    }

    public String getReintegro_exitoso() {
        return reintegro_exitoso;
    }

    public void setReintegro_exitoso(String reintegro_exitoso) {
        this.reintegro_exitoso = reintegro_exitoso;
    }

    public String getFecha_cierre() {
        return fecha_cierre;
    }

    public void setFecha_cierre(String fecha_cierre) {
        this.fecha_cierre = fecha_cierre;
    }

    public String getObservacion() {
        return observacion;
    }

    public void setObservacion(String observacion) {
        this.observacion = observacion;
    }

    public String getPk_case() {
        return pk_case;
    }

    public void setPk_case(String pk_case) {
        this.pk_case = pk_case;
    }

    
}
