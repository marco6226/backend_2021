/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.sigess.entities.emp.dto;

import co.sigess.entities.emp.EstadoUsuario;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.List;

/**
 *
 * @author fmoreno
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class UsuarioDTO {

    private Integer id;
    private String email;
    private EstadoUsuario estado;
    private List<Integer> perfilesId;
    private String perfilNombre;

    public UsuarioDTO() {
    }

    public UsuarioDTO(Integer id, String email, EstadoUsuario estado) {
        this.id = id;
        this.email = email;
        this.estado = estado;
    }
    
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public EstadoUsuario getEstado() {
        return estado;
    }

    public void setEstado(EstadoUsuario estado) {
        this.estado = estado;
    }

    public List<Integer> getPerfilesId() {
        return perfilesId;
    }

    public void setPerfilesId(List<Integer> perfilesId) {
        this.perfilesId = perfilesId;
    }

    public String getPerfilNombre() {
        return perfilNombre;
    }

    public void setPerfilNombre(String perfilNombre) {
        this.perfilNombre = perfilNombre;
    }

}
