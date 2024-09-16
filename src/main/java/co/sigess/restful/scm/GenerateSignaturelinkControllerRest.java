package co.sigess.restful.scm;

import java.util.Collections;
import java.util.Date;
import java.util.Map;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Produces;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import co.sigess.restful.security.Secured;
import co.sigess.restful.security.UtilSecurity;

@Path("medicalFollowUp")
public class GenerateSignaturelinkControllerRest {

    @POST
    @Path("/generateLinkSignature")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response generateLinkSignature(Map<String, Object> data) {
        String user = (String) data.get("user");
        String code = (String) data.get("code");
        Date expiration = new Date(System.currentTimeMillis() + 3600000); // 1 hora en milisegundos
        String jsonWebToken = UtilSecurity.generateJWT(user, expiration, code,
        false);
        // Construir el link con el JWT
        String link = "http://localhost:4200/firma-documento?token=" + jsonWebToken;

        // Devolver el link al frontend
        return Response.ok(Collections.singletonMap("link", link)).build();
    }
}
