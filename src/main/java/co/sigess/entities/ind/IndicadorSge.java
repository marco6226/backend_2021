/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.sigess.entities.ind;

import co.sigess.util.converter.DoubleListConverter;
import co.sigess.util.converter.StringListConverter;
import java.util.Date;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author fmoreno
 */
@Entity
@Table(name = "vw_indicador_sge", schema = "sge")
@XmlRootElement
public class IndicadorSge {

    @Id
    @Column(name = "id")
    private Integer id;

    @Temporal(javax.persistence.TemporalType.DATE)
    @Column(name = "fecha_evaluacion")
    private Date fechaEvaluacion;

    @Convert(converter = StringListConverter.class)
    @Column(name = "elementos")
    private List<String> elementos;
 
    @Convert(converter = DoubleListConverter.class)
    @Column(name = "valores")
    private List<Double> valores;

    @Column(name = "promedio")
    private Double promedio;

    @Column(name = "fk_empresa_id")
    private Integer empresaId;

    @Column(name = "fk_sistema_gestion_id")
    private Integer sgeId;

    @Column(name = "fk_sistema_gestion_version")
    private Integer sgeVersion;

    public IndicadorSge() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getFechaEvaluacion() {
        return fechaEvaluacion;
    }

    public void setFechaEvaluacion(Date fechaEvaluacion) {
        this.fechaEvaluacion = fechaEvaluacion;
    }

    public List<String> getElementos() {
        return elementos;
    }

    public void setElementos(List<String> elementos) {
        this.elementos = elementos;
    }
//

    public List<Double> getValores() {
        return valores;
    }

    public void setValores(List<Double> valores) {
        this.valores = valores;
    }

    public Double getPromedio() {
        return promedio;
    }

    public void setPromedio(Double promedio) {
        this.promedio = promedio;
    }

    public Integer getEmpresaId() {
        return empresaId;
    }

    public void setEmpresaId(Integer empresaId) {
        this.empresaId = empresaId;
    }

    public Integer getSgeId() {
        return sgeId;
    }

    public void setSgeId(Integer sgeId) {
        this.sgeId = sgeId;
    }

    public Integer getSgeVersion() {
        return sgeVersion;
    }

    public void setSgeVersion(Integer sgeVersion) {
        this.sgeVersion = sgeVersion;
    }

}
