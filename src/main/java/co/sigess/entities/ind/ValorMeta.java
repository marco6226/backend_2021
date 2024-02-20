/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.sigess.entities.ind;

import java.io.Serializable;
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
 * @author LENOVO
 */
@Entity
@Table(name = "valor_meta", schema = "ind")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ValorMeta.findAll", query = "SELECT vm FROM ValorMeta vm")})
public class ValorMeta implements Serializable{
    
    private static final long serialVersionUID = 1L;
    
    @Id
    @NotNull
    @SequenceGenerator(name = "valor_meta_id_seq", schema = "ind", sequenceName = "valor_meta_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "valor_meta_id_seq")
    @Column(name = "id")
    private Integer id;
    
    @Column(name = "referencia")
    private String referencia;
    
    @Column(name = "value")
    private double value;
    
    @Column(name = "id_meta")
    private Integer idMeta;

    public ValorMeta() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getReferencia() {
        return referencia;
    }

    public void setReferencia(String referencia) {
        this.referencia = referencia;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public Integer getIdMeta() {
        return idMeta;
    }

    public void setIdMeta(Integer idMeta) {
        this.idMeta = idMeta;
    }
}
