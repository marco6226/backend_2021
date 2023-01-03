/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.sigess.entities.emp;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Julio
 */
@Entity
@JsonIgnoreProperties
@Table(name = "aliados_division", schema = "emp")
@XmlRootElement
public class AliadosDivisiones {
    
    private static final long serialVersionUID = 1L;
    
    @Column(name = "id")
    @Id
    private long id;
    
    @Column(name = "division")
    private String division;
    
    @Column(name = "id_empresa_aliada")
    private long id_empresa_aliada;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getDivision() {
        return division;
    }

    public void setDivision(String division) {
        this.division = division;
    }

    public long getId_empresa_aliada() {
        return id_empresa_aliada;
    }

    public void setId_empresa_aliada(long id_empresa_aliada) {
        this.id_empresa_aliada = id_empresa_aliada;
    }
    
}
