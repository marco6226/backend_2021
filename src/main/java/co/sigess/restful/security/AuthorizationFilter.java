/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.sigess.restful.security;

import co.sigess.entities.com.Mensaje;
import co.sigess.entities.com.TipoMensaje;
import co.sigess.util.Util;
import java.io.IOException;
import java.security.Principal;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Priority;
import javax.ejb.EJB;
import javax.ws.rs.Priorities;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import javax.ws.rs.ext.Provider;

/**
 *
 * @author fmoreno
 */
@Secured
@Provider
@Priority(Priorities.AUTHORIZATION)
public class AuthorizationFilter implements ContainerRequestFilter {

    @EJB
    private AuthorizationFacade authorizationFacade;

//    @Context
//    private HttpServletRequest httpRequest;
    @Override
    public void filter(ContainerRequestContext requestContext) throws IOException {
        try {
            // Validate the request
            Response resp = authorizationFacade.validate(requestContext);
            if (resp.getStatus() == Response.Status.OK.getStatusCode()) {
                String jsonUser = resp.getEntity().toString();
                setupSecurityContext(requestContext, jsonUser);
            } else {
                requestContext.abortWith(resp);
            }
        } catch (Exception e) {
            Mensaje msg = new Mensaje();
            msg.setMensaje("ERROR ID: " + Util.getFechaId());
            msg.setDetalle("La solicitud presenta inconsistencias y no puede ser resuelta");
            msg.setTipoMensaje(TipoMensaje.error);
            Logger.getLogger(AuthorizationFilter.class.getName()).log(Level.SEVERE, msg.toString(), e);
            requestContext.abortWith(Response.status(Response.Status.BAD_REQUEST).entity(msg).build());
        }
        }

    private void setupSecurityContext(ContainerRequestContext requestContext, final String jsonUser) {
        requestContext.setSecurityContext(new SecurityContext() {

            @Override
            public Principal getUserPrincipal() {
                return new Principal() {
                    @Override
                    public String getName() {
                        return jsonUser;
                    }
                };
            }

            @Override
            public boolean isUserInRole(String role) {
                return true;
            }

            @Override
            public boolean isSecure() {
                return false;
            }

            @Override
            public String getAuthenticationScheme() {
                return null;
            }
        });
    }

}
