/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package co.sigess.restful.security;
import javax.annotation.Priority;
import javax.ws.rs.Priorities;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.ext.Provider;
import java.io.IOException;
/**
 *
 * @author johnr
 */
@Provider
@Priority(Priorities.AUTHENTICATION)
public class TokenInterceptor implements ContainerRequestFilter{
    
    public static final String AUTHORIZATION_HEADER = "Authorization";
    private static final ThreadLocal<String> tokenHolder = new ThreadLocal<>();

    @Override
    public void filter(ContainerRequestContext requestContext) throws IOException {
        String token = requestContext.getHeaderString(AUTHORIZATION_HEADER);
        if (token != null) {
            tokenHolder.set(token);
        }
    }

    public static String getToken() {
        return tokenHolder.get();
    }

    public static void clear() {
        tokenHolder.remove();
    }
}
