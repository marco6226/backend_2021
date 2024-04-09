/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.sigess.restful.security;

import co.sigess.entities.com.Mensaje;
import co.sigess.entities.com.TipoMensaje;
import co.sigess.entities.emp.TokenActivo;
import co.sigess.entities.emp.Usuario;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Header;
import io.jsonwebtoken.Jwt;
import io.jsonwebtoken.Jwts;
import java.security.MessageDigest;

import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.lang.Assert;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.security.SecureRandom;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author fmoreno
 */
public class UtilSecurity {

    private static final Key key = generateKey(SignatureAlgorithm.HS512);

    public static final String APP_VERSION_HEADER_NAME = "app-version";
    public static final String TOKEN_NAME = "Authorization";
    public static String TOKEN_REFRESH_NAME = "refresh";
    public static final String TOKEN_ACCESS_ROLE = "TOKEN_ACCESS_ROLE";
    public static final String TOKEN_REFRESH_ROLE = "TOKEN_REFRESH_ROLE";
    public static final int CAMBIO_PASSWD_TIMEOUT = 600;
    public static final int TOKEN_DURATION = 60 * 60 * 4;
    public static final int TOKEN_REFRESH_DURATION = 60 * 60 * 24 * 15; // Corresponde a la cantidad de segundos en 15 dias
    private static final String AB = "1234567890";
    private static final SecureRandom RND = new SecureRandom();
    private static final int MIN_LENGHT = 6;
    private static final int MAX_LENGHT = 6;

    public static String generatePassword() {
        int size = RND.nextInt((MAX_LENGHT - MIN_LENGHT) + 1) + MIN_LENGHT;
        StringBuilder sb = new StringBuilder(size);
        for (int i = 0; i < size; i++) {
            sb.append(AB.charAt(RND.nextInt(AB.length())));
        }
        return sb.toString();
    }

    public static String toSHA256(String base) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(base.getBytes("UTF-8"));
            StringBuilder hexString = new StringBuilder();

            for (int i = 0; i < hash.length; i++) {
                String hex = Integer.toHexString(0xff & hash[i]);
                if (hex.length() == 1) {
                    hexString.append('0');
                }
                hexString.append(hex);
            }

            return hexString.toString();
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    public static String createEmailPasswordHash(String email, String password) {
        return UtilSecurity.toSHA256("[" + email + "]:[" + password + "]");
    }

    /**
     * Genera un nuevo token con el tiempo de expiración especificado como
     * parámetro
     *
     * @param data
     * @param expiration
     * @param code
     * @param refresher
     * @return
     */
    public static String generateJWT(String data, Date expiration, String code, boolean refresher) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("scope", refresher ? TOKEN_REFRESH_ROLE : TOKEN_ACCESS_ROLE);
        claims.put("exp", expiration.getTime());
        claims.put("cod", code);
        String compactJws = Jwts.builder()
                .setClaims(claims)
                .setSubject(data)
                .signWith(SignatureAlgorithm.HS512, key)
                .setExpiration(expiration)
                .compact();
        return compactJws;
    }

    /**
     * Verifica la validés del token recibido como parametro
     *
     * @param compactJws
     * @param verifyRefresher Flag que indica si se debe verificar el token como
     * TOKEN_REFRESH_ROLE (true) o como TOKEN_ACCES_ROLE (false)
     * @return
     * @throws ExpiredJwtException
     */
    public static TokenActivo verifyJWT(String compactJws, boolean verifyRefresher) throws ExpiredJwtException {
        Claims claims = Jwts.parser().setSigningKey(key).parseClaimsJws(compactJws).getBody();
        if (claims.getExpiration() == null || claims.getExpiration().before(new Date())) {
            throw new ExpiredJwtException(null, null, null);
        }
        String scope = (String) claims.get("scope");
        String tokenType = verifyRefresher ? TOKEN_REFRESH_ROLE : TOKEN_ACCESS_ROLE;
        if (scope == null || !scope.equals(tokenType)) {
            throw new UnsupportedJwtException(null);
        }
        String jsonUser = claims.getSubject();
        Usuario user = Usuario.jsonToUser(jsonUser);

        TokenActivo token = new TokenActivo((String) claims.get("cod"));
        token.setExpira(claims.getExpiration());
        token.setTipo(claims.get("scope", String.class));
        token.setUsuario(user);
        return token;
    }

    public static Claims getTokenClaims(String value) {
        int signIndex = value.lastIndexOf('.');
        Jwt<Header, Claims> untrusted = Jwts.parser().parseClaimsJwt(value.substring(0, signIndex + 1));
        Claims claims = untrusted.getBody();
        return claims;
    }

    public static SecretKey generateKey(SignatureAlgorithm alg) {

        SecureRandom random;
        random = new SecureRandom();
        random.nextBytes(new byte[64]);
        Assert.isTrue(alg.isHmac(), "SignatureAlgorithm argument must represent an HMAC algorithm.");

        byte[] bytes;

        switch (alg) {
            case HS256:
                bytes = new byte[32];
                break;
            case HS384:
                bytes = new byte[48];
                break;
            default:
                bytes = new byte[64];
        }

        random.nextBytes(bytes);

        String stringKey = "{{private secret key}}";
//        return new SecretKeySpec(bytes, alg.getJcaName());
        return new SecretKeySpec(stringKey.getBytes(), alg.getJcaName());
    }

    public static Mensaje validarPassword(String passwd) {
        final String MIN_LENGHT = "8";
//      final String MAX_LENGHT = "20";

        final String MIN_CHAR = ".{" + MIN_LENGHT + ",}";  //.{12,} at least 12 characters
        final String ONE_DIGIT = "(.)*(\\d)(.)*";  //(?=.*[0-9]) a digit must occur at least once
        final String LOWER_CASE = "(.)*([a-z])(.)*";  //(?=.*[a-z]) a lower case letter must occur at least once
        final String UPPER_CASE = "(.)*([A-Z])(.)*";  //(?=.*[A-Z]) an upper case letter must occur at least once
        final String NO_SPACE = "(.)*(\\s)(.)*";  //(?=\\S+$) no whitespace allowed in the entire string
        final String SPECIAL_CHAR = "(.)*([@#$%^&+=_.])(.)*"; //(?=.*[@#$%^&+=]) a special character must occur at least once
//      final String MIN_MAX_CHAR = ".{" + MIN_LENGHT + "," + MAX_LENGHT + "}";  //.{5,10} represents minimum of 5 characters and maximum of 10 characters
        
        if (!passwd.matches(MIN_CHAR)) {
            return new Mensaje("Contraseña no valida", "La contraseña debe tener al menos 8 caractéres", TipoMensaje.warn);
        }
        if (!passwd.matches(ONE_DIGIT)) {
            return new Mensaje("Contraseña incompleta", "La contraseña debe tener al menos un digito numérico", TipoMensaje.warn);
        }
        if (!passwd.matches(UPPER_CASE)) {
            return new Mensaje("Contraseña incompleta", "La contraseña debe tener al menos un carácter en mayúscula", TipoMensaje.warn);
        }
        if (!passwd.matches(LOWER_CASE)) {
            return new Mensaje("Contraseña incompleta", "La contraseña debe tener al menos un carácter en minuscula", TipoMensaje.warn);
        }
        if (passwd.matches(NO_SPACE)) {
            return new Mensaje("Contraseña no valida", "La contraseña no puede contener espacios en blanco", TipoMensaje.warn);
        }
        if (!passwd.matches(SPECIAL_CHAR)) {
            return new Mensaje("Contraseña no valida", "La contraseña debe tener al menos uno de los siguientes caractéres especiales: @ # $ % ^ & + = _ .", TipoMensaje.warn);
        }
        return null;
        //String pattern = "(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{12,}";
        //final String PATTERN = ONE_DIGIT + LOWER_CASE + UPPER_CASE + SPECIAL_CHAR + NO_SPACE + MIN_CHAR;                
        //result = passwd.matches(PATTERN);
    }

}
