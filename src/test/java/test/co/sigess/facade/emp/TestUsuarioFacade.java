/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test.co.sigess.facade.emp;

import co.sigess.entities.emp.EstadoUsuario;
import co.sigess.entities.emp.Usuario;
import co.sigess.exceptions.UserMessageException;
import co.sigess.facade.core.EmailFacade;
import co.sigess.facade.emp.UsuarioFacade;
import co.sigess.restful.security.UtilSecurity;
import javax.persistence.EntityManager;
import javax.servlet.http.HttpServletRequest;
import org.junit.Assert;
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
public class TestUsuarioFacade {

    @InjectMocks
    private UsuarioFacade usuarioFacade;

    @Mock
    private EntityManager em;
    
    @Mock
    private HttpServletRequest httpServletRequest;
    
    @Mock    
    private EmailFacade emailFacade;

    @Test
    public void cambiarPasswdTest() throws Exception {
        Usuario user = new Usuario(1, "usermock@fake.com", EstadoUsuario.ACTIVO);
        String oldPass = "old_password";
        String newPass = "new_password";
        String oldPasswdHash = UtilSecurity.createEmailPasswordHash(user.getEmail(), UtilSecurity.toSHA256(oldPass));
        Mockito.when(httpServletRequest.getRemoteAddr()).thenReturn("127.0.0.1");
        Mockito.when(httpServletRequest.getHeader("user-agent")).thenReturn("Mozilla/5.0 (X11; Fedora; Linu…) Gecko/20100101 Firefox/64.0");

        //--------------------------------------- Test id usuario no encontrado  -----------------------------------------
        Mockito.when(em.find(Usuario.class, user.getId())).thenReturn(null);
        try {
            Usuario usuario = usuarioFacade.cambiarPasswd(user.getId(), "", "", "");
            Assert.assertTrue(false);
        } catch (IllegalArgumentException iaex) {
            Assert.assertTrue(true);
        }
        
        
        //--------------------------------------- Test nuevo passwd diferente de passwd confirmacion  -----------------------------------------
        Mockito.when(em.find(Usuario.class, user.getId())).thenReturn(user);
        try {
            Usuario usuario = usuarioFacade.cambiarPasswd(user.getId(), "un_passwd", "otro_passwd", "");
            Assert.assertTrue(false);
        } catch (UserMessageException ume) {
            Assert.assertTrue(true);
        }

        //--------------------------------------- Test confirmacion passwd anterior no coincide  -----------------------------------------
        try {
            Usuario usuario = usuarioFacade.cambiarPasswd(user.getId(), newPass, newPass, "password_anterior_invalido");
            Assert.assertTrue(false);
        } catch (UserMessageException ume) {
            Assert.assertTrue(true);
        }

        //--------------------------------------- Test cambio passwd contraseña sin digito numerico  -----------------------------------------
        user.setPassword(oldPasswdHash);
        try {
            Usuario usuario = usuarioFacade.cambiarPasswd(user.getId(), newPass, newPass, oldPass);
            Assert.assertTrue(false);
        } catch (UserMessageException ume) {
            Assert.assertTrue(true);
        }
        

        //--------------------------------------- Test cambio passwd contraseña menos 12 caracteres  -----------------------------------------
        newPass = "$2019jJ";
        try {
            Usuario usuario = usuarioFacade.cambiarPasswd(user.getId(), newPass, newPass, oldPass);
            Assert.assertTrue(false);
        } catch (UserMessageException ume) {
            Assert.assertTrue(true);
        }

        //--------------------------------------- Test cambio passwd contraseña sin mayuscula  -----------------------------------------
        newPass = "new_password_2019";
        try {
            Usuario usuario = usuarioFacade.cambiarPasswd(user.getId(), newPass, newPass, oldPass);
            Assert.assertTrue(false);
        } catch (UserMessageException ume) {
            Assert.assertTrue(true);
        }

        //--------------------------------------- Test cambio passwd sin minuscula -----------------------------------------
        newPass = "NEW_PASSWORD_2019";
        try {
            Usuario usuario = usuarioFacade.cambiarPasswd(user.getId(), newPass, newPass, oldPass);
            Assert.assertTrue(false);
        } catch (UserMessageException ume) {
            Assert.assertTrue(true);
        }

        //--------------------------------------- Test cambio passwd contraseña con espacio -----------------------------------------
        newPass = "New password_2019";
        try {
            Usuario usuario = usuarioFacade.cambiarPasswd(user.getId(), newPass, newPass, oldPass);
            Assert.assertTrue(false);
        } catch (UserMessageException ume) {
            Assert.assertTrue(true);
        }

        //--------------------------------------- Test cambio passwd contraseña caracter especial -----------------------------------------
        newPass = "NEWpassword2019";
        try {
            Usuario usuario = usuarioFacade.cambiarPasswd(user.getId(), newPass, newPass, oldPass);
            Assert.assertTrue(false);
        } catch (UserMessageException ume) {
            Assert.assertTrue(true);
        }
        //--------------------------------------- Test cambio passwd contraseña valida -----------------------------------------
        newPass = "NEW_pass_2019";
        try {
            Usuario usuario = usuarioFacade.cambiarPasswd(user.getId(), newPass, newPass, oldPass);
            Assert.assertTrue(true);
        } catch (UserMessageException ume) {
            Assert.assertTrue(false);
        }
    }

}
