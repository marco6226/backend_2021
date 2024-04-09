/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package co.sigess.entities.sec;

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
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author LENOVO
 */
@Entity
@Table(name = "correo_estados", schema = "sec")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "CorreoEstados.findAll", query = "SELECT ce FROM CorreoEstados ce")})
public class CorreoEstados implements Serializable{
     private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @SequenceGenerator(name = "correo_estado_id_seq", schema = "sec", sequenceName = "correo_estado_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "correo_estado_id_seq")
    
    @Column(name = "id")
    private Long id;
    
    @Column(name = "count")
    private Long count;

    @Size(max = 100)
    @Column(name = "email")
    private String email;

    @Column(name = "fk_emp_responsable_id")
    private int fkEmpResponsableId;

    @Size(max = 45)
    @Column(name = "primer_nombre")
    private String primerNombre;

    @Size(max = 45)
    @Column(name = "primer_apellido")
    private String primerApellido;

    @Column(name = "abierto")
    private int abierto;

    @Column(name = "seguimiento")
    private int seguimiento;

    @Column(name = "vencida")
    private int vencida;

    /*public CorreoEstados() {
    }

    public CorreoEstados(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }*/

    public Long getCount() {
        return count;
    }

    public void setCount(Long count) {
        this.count = count;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getFkEmpResponsableId() {
        return fkEmpResponsableId;
    }

    public void setFkEmpResponsableId(int fkEmpResponsableId) {
        this.fkEmpResponsableId = fkEmpResponsableId;
    }

    public String getPrimerNombre() {
        return primerNombre;
    }

    public void setPrimerNombre(String primerNombre) {
        this.primerNombre = primerNombre;
    }

    public String getPrimerApellido() {
        return primerApellido;
    }

    public void setPrimerApellido(String primerApellido) {
        this.primerApellido = primerApellido;
    }

    public int getAbierto() {
        return abierto;
    }

    public void setAbierto(int abierto) {
        this.abierto = abierto;
    }

    public int getSeguimiento() {
        return seguimiento;
    }

    public void setSeguimiento(int seguimiento) {
        this.seguimiento = seguimiento;
    }

    public int getVencida() {
        return vencida;
    }

    public void setVencida(int vencida) {
        this.vencida = vencida;
    }
}
