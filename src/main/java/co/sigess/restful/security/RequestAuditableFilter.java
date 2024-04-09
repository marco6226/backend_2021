/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.sigess.restful.security;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.stream.Collectors;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Context;
import javax.ws.rs.ext.Provider;
import javax.ws.rs.ext.ReaderInterceptor;
import javax.ws.rs.ext.ReaderInterceptorContext;

/**
 *
 * @author fmoreno
 */
@Provider
@Auditable
public class RequestAuditableFilter implements ReaderInterceptor {

    @Context
    private HttpServletRequest httpRequest;

    @Override
    public Object aroundReadFrom(ReaderInterceptorContext context) throws IOException, WebApplicationException {
        InputStream is = context.getInputStream();
        String body = new BufferedReader(new InputStreamReader(is)).lines().collect(Collectors.joining("\n"));
        httpRequest.setAttribute("body", body);
        context.setInputStream(new ByteArrayInputStream((body).getBytes()));
        return context.proceed();
    }

}
