/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.sigess.entities.sec;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.MapsId;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author fmoreno
 */
@Entity
@Table(name = "analisis_costo", schema = "sec")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "AnalisisCosto.findAll", query = "SELECT a FROM AnalisisCosto a")})
public class AnalisisCosto implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Column(name = "pk_analisis_desviacion_id")
    private Integer analisisDesviacionId;
    
    @Column(name = "dias_incapacidad")
    private Integer diasIncapacidad;
    
    @Size(max = 2048)
    @Column(name = "descripcion")
    private String descripcion;
    
    @Column(name = "horas_perdidas_acc")
    private Integer horasPerdidasAcc;
    
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "costo_hora_acc")
    private Double costoHoraAcc;
    
    @Column(name = "dias_mejora")
    private Integer diasMejora;
    
    @Column(name = "imp_diario_mejora")
    private Double impDiarioMejora;
    
    @Column(name = "dias_cotizacion")
    private Integer diasCotizacion;
    
    @Column(name = "imp_diario_cotizacion")
    private Double impDiarioCotizacion;
    
    @Column(name = "horas_repar_intern_edif")
    private Integer horasReparInternEdif;
    
    @Column(name = "costo_hora_repar_intern_edif")
    private Double costoHoraReparInternEdif;
    
    @Column(name = "costo_mater_edif")
    private Double costoMaterEdif;
    
    @Column(name = "costo_repar_extern_edif")
    private Double costoReparExternEdif;
    
    @Column(name = "horas_repar_intern_maq")
    private Integer horasReparInternMaq;
    
    @Column(name = "costo_hora_repar_intern_maq")
    private Double costoHoraReparInternMaq;
    
    @Column(name = "costo_mat_maq")
    private Double costoMatMaq;
    
    @Column(name = "costo_repar_extern_maq")
    private Double costoReparExternMaq;
    
    @Column(name = "num_unid_mater_prim")
    private Integer numUnidMaterPrim;
    
    @Column(name = "costo_unid_mater_prim")
    private Double costoUnidMaterPrim;
    
    @Column(name = "perdida_producc")
    private Double perdidaProducc;
    
    @Column(name = "perdida_parada_maq")
    private Double perdidaParadaMaq;
    
    @Column(name = "costo_horas_extras")
    private Double costoHorasExtras;
    
    @Column(name = "costo_contrat_reemplazo")
    private Double costoContratReemplazo;
    
    @Column(name = "sub_contrat_serv")
    private Double subContratServ;
    
    @Column(name = "otros_costos_producc")
    private Double otrosCostosProducc;
    
    @Column(name = "costos_medidas_prev")
    private Double costosMedidasPrev;
    
    @Column(name = "costo_resp_admin")
    private Double costoRespAdmin;
    
    @Column(name = "costo_resp_seg_social")
    private Double costoRespSegSocial;
    
    @Column(name = "costo_resp_civil")
    private Double costoRespCivil;
    
    @Column(name = "costo_resp_jurid")
    private Double costoRespJurid;
    
    @Column(name = "otros_costos")
    private Double otrosCostos;
    
    @JsonIgnore
    @MapsId
    @JoinColumn(name = "pk_analisis_desviacion_id", referencedColumnName = "id")
    @OneToOne(optional = false)
    private AnalisisDesviacion analisisDesviacion;

    public AnalisisCosto() {
    }

    public AnalisisCosto(Integer analisisDesviacionId) {
        this.analisisDesviacionId = analisisDesviacionId;
    }

    public Integer getAnalisisDesviacionId() {
        return analisisDesviacionId;
    }

    public void setAnalisisDesviacionId(Integer analisisDesviacionId) {
        this.analisisDesviacionId = analisisDesviacionId;
    }


    public Integer getDiasIncapacidad() {
        return diasIncapacidad;
    }

    public void setDiasIncapacidad(Integer diasIncapacidad) {
        this.diasIncapacidad = diasIncapacidad;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Integer getHorasPerdidasAcc() {
        return horasPerdidasAcc;
    }

    public void setHorasPerdidasAcc(Integer horasPerdidasAcc) {
        this.horasPerdidasAcc = horasPerdidasAcc;
    }

    public Double getCostoHoraAcc() {
        return costoHoraAcc;
    }

    public void setCostoHoraAcc(Double costoHoraAcc) {
        this.costoHoraAcc = costoHoraAcc;
    }

    public Integer getDiasMejora() {
        return diasMejora;
    }

    public void setDiasMejora(Integer diasMejora) {
        this.diasMejora = diasMejora;
    }

    public Double getImpDiarioMejora() {
        return impDiarioMejora;
    }

    public void setImpDiarioMejora(Double impDiarioMejora) {
        this.impDiarioMejora = impDiarioMejora;
    }

    public Integer getDiasCotizacion() {
        return diasCotizacion;
    }

    public void setDiasCotizacion(Integer diasCotizacion) {
        this.diasCotizacion = diasCotizacion;
    }

    public Double getImpDiarioCotizacion() {
        return impDiarioCotizacion;
    }

    public void setImpDiarioCotizacion(Double impDiarioCotizacion) {
        this.impDiarioCotizacion = impDiarioCotizacion;
    }

    public Integer getHorasReparInternEdif() {
        return horasReparInternEdif;
    }

    public void setHorasReparInternEdif(Integer horasReparInternEdif) {
        this.horasReparInternEdif = horasReparInternEdif;
    }

    public Double getCostoHoraReparInternEdif() {
        return costoHoraReparInternEdif;
    }

    public void setCostoHoraReparInternEdif(Double costoHoraReparInternEdif) {
        this.costoHoraReparInternEdif = costoHoraReparInternEdif;
    }

    public Double getCostoMaterEdif() {
        return costoMaterEdif;
    }

    public void setCostoMaterEdif(Double costoMaterEdif) {
        this.costoMaterEdif = costoMaterEdif;
    }

    public Double getCostoReparExternEdif() {
        return costoReparExternEdif;
    }

    public void setCostoReparExternEdif(Double costoReparExternEdif) {
        this.costoReparExternEdif = costoReparExternEdif;
    }

    public Integer getHorasReparInternMaq() {
        return horasReparInternMaq;
    }

    public void setHorasReparInternMaq(Integer horasReparInternMaq) {
        this.horasReparInternMaq = horasReparInternMaq;
    }

    public Double getCostoHoraReparInternMaq() {
        return costoHoraReparInternMaq;
    }

    public void setCostoHoraReparInternMaq(Double costoHoraReparInternMaq) {
        this.costoHoraReparInternMaq = costoHoraReparInternMaq;
    }

    public Double getCostoMatMaq() {
        return costoMatMaq;
    }

    public void setCostoMatMaq(Double costoMatMaq) {
        this.costoMatMaq = costoMatMaq;
    }

    public Double getCostoReparExternMaq() {
        return costoReparExternMaq;
    }

    public void setCostoReparExternMaq(Double costoReparExternMaq) {
        this.costoReparExternMaq = costoReparExternMaq;
    }

    public Integer getNumUnidMaterPrim() {
        return numUnidMaterPrim;
    }

    public void setNumUnidMaterPrim(Integer numUnidMaterPrim) {
        this.numUnidMaterPrim = numUnidMaterPrim;
    }

    public Double getCostoUnidMaterPrim() {
        return costoUnidMaterPrim;
    }

    public void setCostoUnidMaterPrim(Double costoUnidMaterPrim) {
        this.costoUnidMaterPrim = costoUnidMaterPrim;
    }

    public Double getPerdidaProducc() {
        return perdidaProducc;
    }

    public void setPerdidaProducc(Double perdidaProducc) {
        this.perdidaProducc = perdidaProducc;
    }

    public Double getPerdidaParadaMaq() {
        return perdidaParadaMaq;
    }

    public void setPerdidaParadaMaq(Double perdidaParadaMaq) {
        this.perdidaParadaMaq = perdidaParadaMaq;
    }

    public Double getCostoHorasExtras() {
        return costoHorasExtras;
    }

    public void setCostoHorasExtras(Double costoHorasExtras) {
        this.costoHorasExtras = costoHorasExtras;
    }

    public Double getCostoContratReemplazo() {
        return costoContratReemplazo;
    }

    public void setCostoContratReemplazo(Double costoContratReemplazo) {
        this.costoContratReemplazo = costoContratReemplazo;
    }

    public Double getSubContratServ() {
        return subContratServ;
    }

    public void setSubContratServ(Double subContratServ) {
        this.subContratServ = subContratServ;
    }

    public Double getOtrosCostosProducc() {
        return otrosCostosProducc;
    }

    public void setOtrosCostosProducc(Double otrosCostosProducc) {
        this.otrosCostosProducc = otrosCostosProducc;
    }

    public Double getCostosMedidasPrev() {
        return costosMedidasPrev;
    }

    public void setCostosMedidasPrev(Double costosMedidasPrev) {
        this.costosMedidasPrev = costosMedidasPrev;
    }

    public Double getCostoRespAdmin() {
        return costoRespAdmin;
    }

    public void setCostoRespAdmin(Double costoRespAdmin) {
        this.costoRespAdmin = costoRespAdmin;
    }

    public Double getCostoRespSegSocial() {
        return costoRespSegSocial;
    }

    public void setCostoRespSegSocial(Double costoRespSegSocial) {
        this.costoRespSegSocial = costoRespSegSocial;
    }

    public Double getCostoRespCivil() {
        return costoRespCivil;
    }

    public void setCostoRespCivil(Double costoRespCivil) {
        this.costoRespCivil = costoRespCivil;
    }

    public Double getCostoRespJurid() {
        return costoRespJurid;
    }

    public void setCostoRespJurid(Double costoRespJurid) {
        this.costoRespJurid = costoRespJurid;
    }

    public Double getOtrosCostos() {
        return otrosCostos;
    }

    public void setOtrosCostos(Double otrosCostos) {
        this.otrosCostos = otrosCostos;
    }

    public AnalisisDesviacion getAnalisisDesviacion() {
        return analisisDesviacion;
    }

    public void setAnalisisDesviacion(AnalisisDesviacion analisisDesviacion) {
        this.analisisDesviacion = analisisDesviacion;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (analisisDesviacionId != null ? analisisDesviacionId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof AnalisisCosto)) {
            return false;
        }
        AnalisisCosto other = (AnalisisCosto) object;
        if ((this.analisisDesviacionId == null && other.analisisDesviacionId != null) || (this.analisisDesviacionId != null && !this.analisisDesviacionId.equals(other.analisisDesviacionId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "co.sigess.entities.sec.AnalisisCosto[ pkAnalisisDesviacionId=" + analisisDesviacionId + " ]";
    }
    
}
