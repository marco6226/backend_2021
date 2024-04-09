/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.sigess.restful;

import co.sigess.entities.emp.Usuario;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.SecurityContext;

/**
 *
 * @author fmoreno
 */
public abstract class Request {
    
    @Context
    HttpServletRequest httpRequest;
    
    @Context
    SecurityContext securityContext;
    
    
    public Integer getEmpresaIdRequestContext() {
        return Integer.parseInt(httpRequest.getHeader("Param-Emp"));
    }
    
    public Usuario getUsuarioRequestContext() {
        return Usuario.jsonToUser(securityContext.getUserPrincipal().getName());
    }
    
    public String getToken() {
        return securityContext.getUserPrincipal().getName();
    }
}
