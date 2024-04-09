/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package co.sigess.restful.emp;
import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletResponse;
/**
 *
 * @author johnr
 */
@WebFilter("/*")
public class ContentSecurityFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
       // Convierte ServletResponse a HttpServletResponse
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        
        // Agrega el encabezado "X-Content-Type-Options" en la respuesta.
        httpResponse.setHeader("X-Content-Type-Options", "nosniff");
        
        // Continúa con la cadena de filtros.
        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {
        
    }
    
}
