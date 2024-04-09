/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test.co.sigess.util;

import co.sigess.entities.emp.EstadoUsuario;
import co.sigess.entities.emp.Usuario;
import co.sigess.util.Util;
import static org.junit.Assert.assertEquals;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

/**
 *
 * @author fmoreno
 */
@RunWith(MockitoJUnitRunner.class)
public class TestUtil {

    @Test
    public void jsonSerializacionDeserializacionTEST() throws Exception {
        Usuario usuario = new Usuario(1, "usuario@fake.com", EstadoUsuario.ACTIVO.name());
        String userRes = Util.objectToJson(usuario);
        Usuario userEntityResp = Util.fromJson(userRes, Usuario.class);
        assertEquals(userEntityResp, usuario);        
    }

}
