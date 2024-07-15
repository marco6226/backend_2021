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
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Luis
 */
@Entity
@Table(name = "partes_diagnostico", schema = "scm")
@XmlRootElement
public class DiagnosticoPartesEntity implements Serializable{
    private static final long serialVersionUID = 1L;
    
    @Id
    @Basic(optional = false)
    @SequenceGenerator(name = "diagnosticos_partes_id_seq", schema = "scm", sequenceName = "diagnosticos_partes_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "diagnosticos_partes_id_seq")
    @NotNull
    @Column(name = "id")
    private Long id;
    
    @Column(name = "id_diagnostico")
    private Long idDiagnostico;
    
    @Column(name = "id_partes")
     private Long idPartes;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getIdDiagnostico() {
        return idDiagnostico;
    }

    public void setIdDiagnostico(Long idDiagnostico) {
        this.idDiagnostico = idDiagnostico;
    }

    public Long getIdPartes() {
        return idPartes;
    }

    public void setIdPartes(Long idPartes) {
        this.idPartes = idPartes;
    }
    
    
    
    
}
