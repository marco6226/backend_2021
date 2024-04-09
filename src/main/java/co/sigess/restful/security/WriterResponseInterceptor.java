/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.sigess.restful.security;

import co.sigess.entities.emp.TokenActivo;
import co.sigess.facade.emp.TokenFacade;
import java.io.IOException;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.container.ResourceInfo;
import javax.ws.rs.core.Context;
import javax.ws.rs.ext.Provider;
import javax.ws.rs.ext.WriterInterceptor;
import javax.ws.rs.ext.WriterInterceptorContext;

/**
 *
 * @author fmoreno
 */
@RollBackResponse
@Provider
public class WriterResponseInterceptor implements WriterInterceptor {

    @Context
    private ResourceInfo resourceInfo;

    @EJB
    private TokenFacade tokenFacade;

    @Override
    public void aroundWriteTo(WriterInterceptorContext context) throws IOException, WebApplicationException {
        try {
            context.proceed();
        } catch (IOException | WebApplicationException e) {
            RollBackResponse annot = resourceInfo.getResourceMethod().getAnnotation(RollBackResponse.class);
            if (annot.proceso().equals("login")) {
                this.onLoginConnectionClose((Map<String, Object>) context.getEntity());
            }
        }

    }

    private void onLoginConnectionClose(Map<String, Object> ent) {
        // Elimina el token de acceso creado durante el login
        String accesToken = (String) ent.get(UtilSecurity.TOKEN_NAME);
        String accesTokenId = (String) UtilSecurity.getTokenClaims(accesToken).get("cod");
        try {
            this.tokenFacade.remove(new TokenActivo(accesTokenId));
        } catch (Exception ex) {
            Logger.getLogger(WriterResponseInterceptor.class.getName()).log(Level.SEVERE, null, ex);
        }

        // Elimina el token refresh creado durante el login
        String refresh = (String) ent.get(UtilSecurity.TOKEN_REFRESH_NAME);
        if (refresh != null) {
            String tokenId = (String) UtilSecurity.getTokenClaims(refresh).get("cod");
            try {
                this.tokenFacade.remove(new TokenActivo(tokenId));
            } catch (Exception ex) {
                Logger.getLogger(WriterResponseInterceptor.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
