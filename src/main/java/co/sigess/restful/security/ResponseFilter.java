/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.sigess.restful.security;

import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.ContainerResponseFilter;
import javax.ws.rs.core.Context;
import javax.ws.rs.ext.Provider;

@Provider
public class ResponseFilter implements ContainerResponseFilter {

    @Context
    HttpServletRequest httpRequest;

    @Override
    public void filter(final ContainerRequestContext requestContext, final ContainerResponseContext cres) throws IOException {
        cres.getHeaders().add("Access-Control-Allow-Origin", httpRequest.getHeader("Origin"));
        cres.getHeaders().add("Access-Control-Allow-Headers", "Authorization");
        cres.getHeaders().add("Access-Control-Allow-Headers", "content-type");
        cres.getHeaders().add("Access-Control-Allow-Headers", "Param-Emp");
        cres.getHeaders().add("Access-Control-Allow-Headers", "app-version");
        cres.getHeaders().add("Access-Control-Max-Age", "600");
        cres.getHeaders().add("Access-Control-Allow-Credentials", "true");
        cres.getHeaders().add("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, HEAD, OPTIONS");
    }
}
