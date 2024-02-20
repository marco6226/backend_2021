/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.sigess.entities.ind;

import java.io.Serializable;
import java.util.List;
import javax.persistence.CascadeType;
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
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author JULIO
 */
@Entity
@Table(name = "meta", schema = "ind")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Meta.findAll", query = "SELECT m FROM Meta m")})
public class Meta implements Serializable {
    
    private static final long serialVersionUID = 1L;
    @Id
    @NotNull
    @SequenceGenerator(name = "meta_id_seq", schema = "ind", sequenceName = "meta_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "meta_id_seq")
    @Column(name = "id")
    private Integer id;
    
    @Column(name = "referencia")
    private Integer referencia;
    
    @Column(name = "anio")
    private Integer anio;
    
    @Column(name = "modulo")
    private String modulo;
    
    @Column(name = "empresa_id")
    private long empresaId;
    
    @Column(name = "pais")
    private String pais;
    
    //@ManyToOne
    //@JoinColumn(name = "id_meta_id")
    //private Meta metaPadre;
    
    @OneToMany//(mappedBy = "metaPadre", cascade = CascadeType.ALL)
    @JoinColumn(name = "id_meta_id", table = "meta")
    private List<Meta> metas;
    
    @Column(name = "meta_anual")
    private Integer metaAnual;
    
    @OneToMany
    @JoinColumn(name = "id_meta", table = "valor_meta")
    private List<ValorMeta> valorMeta;

    public Meta() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getReferencia() {
        return referencia;
    }

    public void setReferencia(Integer referencia) {
        this.referencia = referencia;
    }

    public Integer getAnio() {
        return anio;
    }

    public void setAnio(Integer anio) {
        this.anio = anio;
    }

    public String getModulo() {
        return modulo;
    }

    public void setModulo(String modulo) {
        this.modulo = modulo;
    }

    public long getEmpresaId() {
        return empresaId;
    }

    public void setEmpresaId(long empresaId) {
        this.empresaId = empresaId;
    }

    public String getPais() {
        return pais;
    }

    public void setPais(String pais) {
        this.pais = pais;
    }

    //public Meta getMetaPadre() {
        //return metaPadre;
    //}

    //public void setMetaPadre(Meta metaPadre) {
    //    this.metaPadre = metaPadre;
    //}

    public List<Meta> getMetas() {
        return metas;
    }

    public void setMetas(List<Meta> metas) {
        this.metas = metas;
    }

    public Integer getMetaAnual() {
        return metaAnual;
    }

    public void setMetaAnual(Integer metaAnual) {
        this.metaAnual = metaAnual;
    }

    public List<ValorMeta> getValorMeta() {
        return valorMeta;
    }

    public void setValorMeta(List<ValorMeta> valorMeta) {
        this.valorMeta = valorMeta;
    }
}
