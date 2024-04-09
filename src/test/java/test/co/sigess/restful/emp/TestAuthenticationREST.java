/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test.co.sigess.restful.emp;

import co.sigess.entities.com.ApiVersion;
import co.sigess.entities.emp.EstadoUsuario;
import co.sigess.entities.emp.TokenActivo;
import co.sigess.entities.emp.Usuario;
import co.sigess.facade.core.LoaderFacade;
import co.sigess.facade.emp.TokenFacade;
import co.sigess.facade.emp.UsuarioFacade;
import co.sigess.restful.emp.AuthenticationREST;
import co.sigess.restful.security.UtilSecurity;
import co.sigess.util.Util;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.NewCookie;
import javax.ws.rs.core.Response;
import org.junit.Assert;
import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;


/**
 *
 * @author fmoreno
 */
@RunWith(MockitoJUnitRunner.class)
public class TestAuthenticationREST {

    @InjectMocks
    private AuthenticationREST authenticationREST;

    @Mock
    private HttpServletRequest httpRequest;

    @Mock
    private LoaderFacade loaderFacade;
    
    @Mock
    private UsuarioFacade usuarioFacade;

    @Mock
    private TokenFacade tokenFacade;

    private Usuario usuario;

    @Before
    public void setup() throws Exception {
        usuario = new Usuario();
        usuario.setId(1);
        usuario.setEmail("usermock@fake.com");
        usuario.setPassword("passwddummy");
        Mockito.when(httpRequest.getRemoteAddr()).thenReturn("127.0.0.1");
        Mockito.when(httpRequest.getHeader("app-version")).thenReturn("1.0.2");
        ApiVersion apiVer = new ApiVersion();
        apiVer.setVersionActual("1.0.2");
        apiVer.setSoportado(new HashMap<>());
        
        Map<String, Object> soport = new HashMap<>();
        soport.put("req_update", Boolean.FALSE);
        apiVer.getSoportado().put("1.0.2", soport);
        Mockito.when(loaderFacade.getApiVersion()).thenReturn(apiVer);

        Mockito.when(tokenFacade.find("")).thenReturn(new TokenActivo("1234567890"));
        Mockito.when(usuarioFacade.authenticate(usuario.getEmail(), usuario.getPassword(), null)).thenReturn(usuario);
    }

    @Test
    public void authenticateUser_ESTADO() throws Exception {
        String loginRequest = usuario.getEmail() + ":" + usuario.getPassword();
        //--------------------------------------- Test usuario ACTIVO -----------------------------------------
        usuario.setEstado(EstadoUsuario.ACTIVO);
        usuario.codigo = 0;
        int codResp = authenticationREST.authenticateUser(true, null, loginRequest).getStatusInfo().getStatusCode();
        assertEquals(Response.Status.OK.getStatusCode(), codResp);

        codResp = authenticationREST.authenticateUser(false, null, loginRequest).getStatusInfo().getStatusCode();
        assertEquals(Response.Status.OK.getStatusCode(), codResp);

        //--------------------------------------- Test usuario CAMBIO_PASSWD -----------------------------------------
        usuario.setEstado(EstadoUsuario.CAMBIO_PASSWD);
        codResp = authenticationREST.authenticateUser(true, null, loginRequest).getStatusInfo().getStatusCode();
        assertEquals(Response.Status.OK.getStatusCode(), codResp);

        codResp = authenticationREST.authenticateUser(false, null, loginRequest).getStatusInfo().getStatusCode();
        assertEquals(Response.Status.OK.getStatusCode(), codResp);

        //--------------------------------------- Test usuario BLOQUEADO -----------------------------------------
        usuario.setEstado(EstadoUsuario.BLOQUEADO);
        codResp = authenticationREST.authenticateUser(true, null, loginRequest).getStatusInfo().getStatusCode();
        assertEquals(Response.Status.FORBIDDEN.getStatusCode(), codResp);

        //--------------------------------------- Test usuario ELIMINADO -----------------------------------------
        usuario.setEstado(EstadoUsuario.ELIMINADO);
        codResp = authenticationREST.authenticateUser(true, null, loginRequest).getStatusInfo().getStatusCode();
        assertEquals(Response.Status.FORBIDDEN.getStatusCode(), codResp);

        //--------------------------------------- Test usuario INACTIVO -----------------------------------------
        usuario.setEstado(EstadoUsuario.INACTIVO);
        codResp = authenticationREST.authenticateUser(true, null, loginRequest).getStatusInfo().getStatusCode();
        assertEquals(Response.Status.UNAUTHORIZED.getStatusCode(), codResp);

        //--------------------------------------- Test usuario sin estado -----------------------------------------
        usuario.setEstado(null);
        codResp = authenticationREST.authenticateUser(true, null, loginRequest).getStatusInfo().getStatusCode();
        assertEquals(Response.Status.BAD_REQUEST.getStatusCode(), codResp);

        //--------------------------------------- Test usuario LOGIN FALLIDO -----------------------------------------
        Mockito.when(usuarioFacade.authenticate(usuario.getEmail(), usuario.getPassword(), null)).thenReturn(null);
        codResp = authenticationREST.authenticateUser(true, null, loginRequest).getStatusInfo().getStatusCode();
        assertEquals(Response.Status.FORBIDDEN.getStatusCode(), codResp);
    }

    @Test
    public void refrescarToken() throws Exception {
        //--------------------------------------- Test formato token null -----------------------------------------
        int codResp = authenticationREST.refrescarToken(null).getStatus();
        assertEquals(Response.Status.BAD_REQUEST.getStatusCode(), codResp);

        //--------------------------------------- Test formato token vacio -----------------------------------------
        codResp = authenticationREST.refrescarToken("").getStatus();
        assertEquals(Response.Status.BAD_REQUEST.getStatusCode(), codResp);

        //--------------------------------------- Test formato token no valido -----------------------------------------
        codResp = authenticationREST.refrescarToken("token_sin_estructura_jwt").getStatus();
        assertEquals(Response.Status.BAD_REQUEST.getStatusCode(), codResp);

        //--------------------------------------- Test token expirado -----------------------------------------
        Date fechaExpira = new Date(0, 0, 1, 0, 0, 0);
        String token = UtilSecurity.generateJWT(Util.objectToJson(usuario), fechaExpira, "", true);
        codResp = authenticationREST.refrescarToken(token).getStatus();
        assertEquals(Response.Status.UNAUTHORIZED.getStatusCode(), codResp);

        //--------------------------------------- Test tipo token incorrecto -----------------------------------------  
        fechaExpira = new Date(2000, 0, 1, 0, 0, 0);
        token = UtilSecurity.generateJWT(Util.objectToJson(usuario), fechaExpira, "", false);
        codResp = authenticationREST.refrescarToken(token).getStatus();
        assertEquals(Response.Status.BAD_REQUEST.getStatusCode(), codResp);

        //--------------------------------------- Test token valido -----------------------------------------  
        fechaExpira = new Date(2000, 0, 1, 0, 0, 0);
        token = UtilSecurity.generateJWT(Util.objectToJson(usuario), fechaExpira, "", true);
        codResp = authenticationREST.refrescarToken(token).getStatus();
        assertEquals(Response.Status.OK.getStatusCode(), codResp);

        //--------------------------------------- Test NullPointerException inesperado con token valido -----------------------------------------  
        fechaExpira = new Date(2000, 0, 1, 0, 0, 0);
        token = UtilSecurity.generateJWT(Util.objectToJson(usuario), fechaExpira, "", true);
        Mockito.when(httpRequest.getRemoteAddr()).thenThrow(NullPointerException.class);
        codResp = authenticationREST.refrescarToken(token).getStatus();
        assertEquals(Response.Status.INTERNAL_SERVER_ERROR.getStatusCode(), codResp);
    }

    @Test
    public void logout() throws Exception {
        Date fechaExpira = new Date(2000, 0, 1, 0, 0, 0);
        String tokenAcces = UtilSecurity.generateJWT(Util.objectToJson(usuario), fechaExpira, "", false);
        String tokenRefresh = UtilSecurity.generateJWT(Util.objectToJson(usuario), fechaExpira, "", true);
        Map<String, String> tokenMap = new HashMap();
        tokenMap.put(UtilSecurity.TOKEN_REFRESH_NAME, tokenRefresh);
        tokenMap.put(UtilSecurity.TOKEN_NAME, tokenAcces);

        fechaExpira = new Date(119, 0, 1, 0, 0, 0);
        String tokenAccesExp = UtilSecurity.generateJWT(Util.objectToJson(usuario), fechaExpira, "", false);
        String tokenRefreshExp = UtilSecurity.generateJWT(Util.objectToJson(usuario), fechaExpira, "", true);

        //--------------------------------------- Test logout cuando no se reciben token -----------------------------------------  
        int status = authenticationREST.logout(null).getStatus();
        Assert.assertEquals(Response.Status.BAD_REQUEST.getStatusCode(), status);

        //--------------------------------------- Test logout cuando se reciben tokens validos------------------------------------------------
        status = authenticationREST.logout(tokenMap).getStatus();
        Assert.assertEquals(Response.Status.OK.getStatusCode(), status);
        //--------------------------------------- Test logout cuando se reciben token vacio o null------------------------------------
        status = authenticationREST.logout(null).getStatus();
        Assert.assertEquals(Response.Status.BAD_REQUEST.getStatusCode(), status);

        //--------------------------------------- Test logout cuando se reciben token expirados------------------------------------                
        tokenMap.put(UtilSecurity.TOKEN_REFRESH_NAME, tokenRefreshExp);
        tokenMap.put(UtilSecurity.TOKEN_NAME, tokenAccesExp);
        
        status = authenticationREST.logout(tokenMap).getStatus();
        Assert.assertEquals(Response.Status.OK.getStatusCode(), status);

        //--------------------------------------- Test logout cuando se reciben token mal formados (alterados) ------------------------------------------------
        tokenMap.put(UtilSecurity.TOKEN_NAME, tokenAcces.substring(1, tokenAcces.length()));
        tokenMap.put(UtilSecurity.TOKEN_REFRESH_NAME, tokenRefresh.substring(1, tokenRefresh.length()));
        
        status = authenticationREST.logout(tokenMap).getStatus();
        Assert.assertEquals(Response.Status.BAD_REQUEST.getStatusCode(), status);

        //--------------------------------------- Test logout cuando se reciben token firma alterada ------------------------------------------------
        String tokenAccesAlt = tokenAcces.substring(0, tokenAcces.lastIndexOf('.') + 1) + "SflKxwRJSMeKKF2QT4fwpMeJf36POk6yJV_adQssw5c";
        String tokenRefreshAlt = tokenAcces.substring(0, tokenRefresh.lastIndexOf('.') + 1) + "SflKxwRJSMeKKF2QT4fwpMeJf36POk6yJV_adQssw5c";
        tokenMap.put(UtilSecurity.TOKEN_NAME, tokenAccesAlt);
        tokenMap.put(UtilSecurity.TOKEN_REFRESH_NAME, tokenRefreshAlt);
        
        status = authenticationREST.logout(tokenMap).getStatus();
        Assert.assertEquals(Response.Status.BAD_REQUEST.getStatusCode(), status);
    }

    @Test
    public void recuperarPasswd() throws Exception {
        //--------------------------------------- Test cuando se invoca con un email null ------------------------------------------------
        int codeResp = authenticationREST.recuperarPasswd(null).getStatus();
        Assert.assertEquals(Response.Status.OK.getStatusCode(), codeResp);

        //--------------------------------------- Test cuando se invoca con un email vacio ------------------------------------------------
        codeResp = authenticationREST.recuperarPasswd("").getStatus();
        Assert.assertEquals(Response.Status.OK.getStatusCode(), codeResp);

        //--------------------------------------- Test cuando se invoca con un email valido ------------------------------------------------
        codeResp = authenticationREST.recuperarPasswd("usermock@fake.com").getStatus();
        Assert.assertEquals(Response.Status.OK.getStatusCode(), codeResp);

        //--------------------------------------- Test cuando se genera una excepción inesperada ------------------------------------------------
        Mockito.when(usuarioFacade.recuperarPasswd("usermock@fake.com")).thenThrow(Exception.class);
        codeResp = authenticationREST.recuperarPasswd("usermock@fake.com").getStatus();
        Assert.assertEquals(Response.Status.BAD_REQUEST.getStatusCode(), codeResp);
    }
}
