/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.sigess.entities.aus;

import co.sigess.entities.com.Cie;
import co.sigess.entities.com.Ciudad;
import co.sigess.entities.emp.Empleado;
import java.io.Serializable;
import java.util.Date;
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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author fmoreno
 */
@Entity
@Table(name = "reporte_ausentismo", schema = "aus")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ReporteAusentismo.findAll", query = "SELECT r FROM ReporteAusentismo r")})
public class ReporteAusentismo implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @SequenceGenerator(name = "reporte_ausentismo_id_seq", schema = "aus", sequenceName = "reporte_ausentismo_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "reporte_ausentismo_id_seq")
    @Basic(optional = false)
    @Column(name = "id")    
    private Integer id;
    
    @Column(name = "fecha_elaboracion")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaElaboracion;
    
    @NotNull(message = "El campo fechaRadicacion es requerido")
    @Column(name = "fecha_radicacion")
    @Temporal(TemporalType.DATE)
    private Date fechaRadicacion;
    
    @NotNull(message = "El campo fechaDesde es requerido")
    @Column(name = "fecha_desde")
    @Temporal(TemporalType.DATE)
    private Date fechaDesde;
    
    @NotNull(message = "El campo fechaHasta es requerido")
    @Column(name = "fecha_hasta")
    @Temporal(TemporalType.DATE)
    private Date fechaHasta;
    
    @NotNull(message = "El campo dias ausencia es requerido")
    @Column(name = "dias_ausencia")
    private Integer diasAusencia;
    
    @Column(name = "horas_ausencia")
    private Short horasAusencia;
    
    @NotNull(message = "El campo entidadOtorga es requerido")
    @Size(max = 45)
    @Column(name = "entidad_otorga")
    private String entidadOtorga;
        
    @JoinColumn(name = "fk_ciudad_id", referencedColumnName = "id")
    @ManyToOne
    private Ciudad ciudad;
    
    @JoinColumn(name = "fk_cie_id", referencedColumnName = "id")
    @ManyToOne
    private Cie cie;
    
    @NotNull(message = "El campo empleado es requerido")
    @JoinColumn(name = "fk_empleado_id", referencedColumnName = "id")
    @ManyToOne
    private Empleado empleado;
    
    @NotNull(message = "El campo causaAusentismo es requerido")
    @JoinColumn(name = "fk_causa_ausentismo_id", referencedColumnName = "id")
    @ManyToOne
    private CausaAusentismo causaAusentismo;

    public ReporteAusentismo() {
    }

    public ReporteAusentismo(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getFechaElaboracion() {
        return fechaElaboracion;
    }

    public void setFechaElaboracion(Date fechaElaboracion) {
        this.fechaElaboracion = fechaElaboracion;
    }

    public Date getFechaRadicacion() {
        return fechaRadicacion;
    }

    public void setFechaRadicacion(Date fechaRadicacion) {
        this.fechaRadicacion = fechaRadicacion;
    }

    public Date getFechaDesde() {
        return fechaDesde;
    }

    public void setFechaDesde(Date fechaDesde) {
        this.fechaDesde = fechaDesde;
    }

    public Date getFechaHasta() {
        return fechaHasta;
    }

    public void setFechaHasta(Date fechaHasta) {
        this.fechaHasta = fechaHasta;
    }

    public Short getHorasAusencia() {
        return horasAusencia;
    }

    public void setHorasAusencia(Short horasAusencia) {
        this.horasAusencia = horasAusencia;
    }

    public String getEntidadOtorga() {
        return entidadOtorga;
    }

    public void setEntidadOtorga(String entidadOtorga) {
        this.entidadOtorga = entidadOtorga;
    }

    public Cie getCie() {
        return cie;
    }

    public void setCie(Cie cie) {
        this.cie = cie;
    }

    public Empleado getEmpleado() {
        return empleado;
    }

    public void setEmpleado(Empleado empleado) {
        this.empleado = empleado;
    }

    public CausaAusentismo getCausaAusentismo() {
        return causaAusentismo;
    }

    public void setCausaAusentismo(CausaAusentismo causaAusentismo) {
        this.causaAusentismo = causaAusentismo;
    }

    public Integer getDiasAusencia() {
        return diasAusencia;
    }

    public void setDiasAusencia(Integer diasAusencia) {
        this.diasAusencia = diasAusencia;
    }

    public Ciudad getCiudad() {
        return ciudad;
    }

    public void setCiudad(Ciudad ciudad) {
        this.ciudad = ciudad;
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
        if (!(object instanceof ReporteAusentismo)) {
            return false;
        }
        ReporteAusentismo other = (ReporteAusentismo) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "co.sigess.entities.aus.ReporteAusentismo[ id=" + id + " ]";
    }
    
}
