/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test.co.sigess.restful.security;

import co.sigess.entities.emp.EstadoUsuario;
import co.sigess.entities.emp.TokenActivo;
import co.sigess.entities.emp.Usuario;
import co.sigess.restful.security.UtilSecurity;
import co.sigess.util.Util;
import io.jsonwebtoken.ExpiredJwtException;
import java.util.Date;
import org.junit.Assert;
import org.junit.Test;

/**
 *
 * @author fmoreno
 */
public class TestUtilSecurity {

    @Test
    public void generateJWT_TEST() throws Exception {
        Usuario usuario = new Usuario();
        usuario.setId(1);
        usuario.setEmail("usermock@fake.com");
        usuario.setEstado(EstadoUsuario.ACTIVO);

        String tokenId = Util.getFechaId();

        //-------------------------------------------- Test token expirado ----------------------------------------------
        Date fechaExpira = new Date(119, 0, 1, 0, 0, 0);
        try {
            String token = UtilSecurity.generateJWT(Util.objectToJson(usuario), fechaExpira, tokenId, false);
            UtilSecurity.verifyJWT(token, false);
            Assert.assertTrue(false);
        } catch (ExpiredJwtException ex) {
            Assert.assertTrue(true);
        }

        //-------------------------------------------- Test token vigente ----------------------------------------------
        fechaExpira = new Date(1900, 0, 1, 0, 0, 0);
        try {
            String token = UtilSecurity.generateJWT(Util.objectToJson(usuario), fechaExpira, tokenId, false);
            TokenActivo tk = UtilSecurity.verifyJWT(token, false);
            Assert.assertEquals(usuario, tk.getUsuario());
            Assert.assertEquals(fechaExpira, tk.getExpira());
            Assert.assertEquals(tokenId, tk.getId());
            Assert.assertEquals(UtilSecurity.TOKEN_ACCESS_ROLE, tk.getTipo());
        } catch (Exception ex) {
            Assert.assertTrue(false);
        }
    }

}
