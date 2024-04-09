/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.sigess.restful.security;

import co.sigess.entities.com.Mensaje;
import co.sigess.entities.com.TipoMensaje;
import co.sigess.entities.emp.EventoLog;
import co.sigess.entities.emp.Usuario;
import co.sigess.facade.emp.EventoLogFacade;
import co.sigess.util.Util;
import java.io.IOException;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.ContainerResponseFilter;
import javax.ws.rs.container.ResourceInfo;
import javax.ws.rs.core.Context;
import javax.ws.rs.ext.Provider;

@Provider
@Auditable
public class ResponseAuditableFilter implements ContainerResponseFilter {

    @Context
    private ResourceInfo resourceInfo;

    @EJB
    private EventoLogFacade eventoLogFacade;

    @Context
    private HttpServletRequest httpRequest;

    @Override
    public void filter(final ContainerRequestContext requestContext, final ContainerResponseContext cres) throws IOException {
        EventoLog evt = null;
        try {
            Usuario user = null;
            if (requestContext.getSecurityContext().getUserPrincipal() != null) {
                user = Util.fromJson(requestContext.getSecurityContext().getUserPrincipal().getName(), Usuario.class);
            }
            Auditable annot = resourceInfo.getResourceMethod().getAnnotation(Auditable.class);
            Object body = httpRequest.getAttribute("body");
           
            evt = new EventoLog(
                    null,
                    resourceInfo.getResourceClass().getSimpleName() + "." + resourceInfo.getResourceMethod().getName(),
                    "[" + httpRequest.getMethod() + "] " + httpRequest.getRequestURI() + (httpRequest.getQueryString() == null ? "" : "?" + httpRequest.getQueryString()),
                    httpRequest.getRemoteAddr(),
                    httpRequest.getHeader("user-agent"),
                    new Date(),
                    cres.getStatus(),
                    body == null || !annot.persistData() ? "" : body.toString(),
                    user == null ? httpRequest.getAttribute("body").toString() : user.getEmail(),
                    httpRequest.getHeader(UtilSecurity.APP_VERSION_HEADER_NAME)
            );
            this.eventoLogFacade.create(evt);
        } catch (Exception ex) {
            Mensaje msg = new Mensaje("Error no esperado", "Se ha producido un error al registrar eventos: " + (evt == null ? "null" : evt.toString()), TipoMensaje.error);
            Logger.getLogger(ResponseAuditableFilter.class.getName()).log(Level.SEVERE, msg.toString(), ex);
        }
    }
}
