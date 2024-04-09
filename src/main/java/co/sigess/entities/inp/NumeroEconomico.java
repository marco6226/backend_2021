/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package co.sigess.entities.inp;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;
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
@Table(name = "numero_economico", schema = "inp")
public class NumeroEconomico implements Serializable{

private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "id")   
    private Integer id;
    
    @Basic(optional = false)
    @NotNull
    @Column(name = "numero_economico")
    private String numero_economico;

    @Basic(optional = false)
    @NotNull
    @Column(name = "marca")
    private String marca;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    

    public String getNumeroEconomico() {
        return numero_economico;
    }

    public void setNumeroEconomico(String numero_economico) {
        this.numero_economico = numero_economico;
    }   

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }  
}
