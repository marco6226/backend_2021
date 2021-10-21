/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.sigess.restful.security;

import co.sigess.entities.com.ApiVersion;
import co.sigess.entities.com.Mensaje;
import co.sigess.entities.com.TipoMensaje;
import co.sigess.entities.emp.EstadoUsuario;
import co.sigess.entities.emp.TokenActivo;
import co.sigess.entities.emp.Usuario;
import co.sigess.exceptions.UserMessageException;
import co.sigess.facade.core.LoaderFacade;
import co.sigess.facade.emp.TokenFacade;
import co.sigess.facade.emp.UsuarioFacade;
import co.sigess.util.Util;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;
import java.lang.reflect.Method;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ResourceInfo;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Cookie;
import javax.ws.rs.core.Response;
import org.glassfish.jersey.server.ExtendedUriInfo;
import org.glassfish.jersey.uri.UriTemplate;

/**
 *
 * @author fmoreno
 */
@Stateless
public class AuthorizationFacade {

    @Context
    private ResourceInfo resourceInfo;

    @PersistenceContext(unitName = "SIGESS_PU")
    private EntityManager em;

    @EJB
    private UsuarioFacade usuarioFacade;

    @EJB
    private TokenFacade tokenFacade;

    @EJB
    private LoaderFacade loaderFacade;

    @Context
    private HttpServletRequest httpRequest;

    protected EntityManager getEntityManager() {
        return em;
    }

    public Response validate(ContainerRequestContext requestContext) throws Exception {
        String appVersion = requestContext.getHeaderString(UtilSecurity.APP_VERSION_HEADER_NAME);
        Map<String, Object> map = (Map<String, Object>) this.loaderFacade.getApiVersion().getSoportado().get(appVersion);
        if (map == null || (Boolean) map.get("req_update")) {
            Mensaje msg = new Mensaje(
                    "La versión de la app no es compatible",
                    "Por favor asegurese de actualizar a la versión mas reciente",
                    TipoMensaje.warn, Mensaje.COD_VERSION_APP_NO_COMPATIBLE
            );
            Map<String, String> msgData = new HashMap<>();
            msgData.put("playStoreUrl", appVersion);
            msgData.put("appStoreUrl", appVersion);
            msg.setMetaData(msgData);
            return Response.status(Response.Status.FORBIDDEN).entity(msg).build();
        }

        String tParam = requestContext.getHeaderString(UtilSecurity.TOKEN_NAME);
        if (tParam == null) {
            Mensaje msg = new Mensaje("ACCESO DENEGADO", "Token proporcionado no es válido", TipoMensaje.error, Mensaje.COD_TOKEN_NO_VALIDO);
            return Response.status(Response.Status.FORBIDDEN).entity(msg).build();
        }
        String[] paramArr = tParam.split(" ");
        if (paramArr.length != 2) {
            Mensaje msg = new Mensaje("ACCESO DENEGADO", "Token proporcionado no es válido", TipoMensaje.error, Mensaje.COD_TOKEN_NO_VALIDO);
            return Response.status(Response.Status.FORBIDDEN).entity(msg).build();
        }

        String token = paramArr[1];
        if (token == null || token.isEmpty()) {
            Mensaje msg = new Mensaje("ACCESO DENEGADO", "Token proporcionado no es válido", TipoMensaje.error, Mensaje.COD_TOKEN_NO_VALIDO);
            return Response.status(Response.Status.FORBIDDEN).entity(msg).build();
        }

        String method = requestContext.getMethod();
        List<UriTemplate> listTemp = ((ExtendedUriInfo) requestContext.getUriInfo()).getMatchedTemplates();
        String ruta = "";
        for (int i = listTemp.size() - 1; i >= 0; i--) {
            ruta += listTemp.get(i).getTemplate();
        }
        String empresa_id = requestContext.getHeaderString("param-emp");

        TokenActivo tokenAct = null;
        try {
            tokenAct = UtilSecurity.verifyJWT(token, false);
        } catch (ExpiredJwtException ex) {
            Mensaje msg = new Mensaje("TOKEN EXPIRADO", "Token de sesión expirado", TipoMensaje.warn, Mensaje.COD_TOKEN_EXPIRADO);
            Logger.getLogger(AuthorizationFacade.class.getName()).log(Level.INFO, msg.toString(), ex);
            return Response.status(Response.Status.UNAUTHORIZED).entity(msg).build();
        } catch (SignatureException sex) {
            String jsonUser = UtilSecurity.getTokenClaims(token).getSubject();
            Usuario user = Util.fromJson(jsonUser, Usuario.class);
            this.usuarioFacade.bloquearUsuario(user);

            Mensaje msg = new Mensaje("TOKEN FIRMA INVALIDA", "La firma del token recibido presenta inconsistencias: " + token, TipoMensaje.error, Mensaje.COD_TOKEN_FIRMA_INVALIDA);
            Logger.getLogger(AuthorizationFacade.class.getName()).log(Level.SEVERE, msg.toString(), sex);
            return Response.status(Response.Status.FORBIDDEN).entity(new Mensaje()).build();
        } catch (UnsupportedJwtException ex) {
            Logger.getLogger(AuthorizationFacade.class.getName()).log(Level.SEVERE, "", ex);
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
        if (tokenAct != null) {
            boolean tienePermiso = false;
            boolean permisoArea = false;
            String estado = "";
            boolean passwdVigente = false;
            String codigoRecurso = "";
            String nombreRecurso = "";
            String idToken = null;
            Date expToken = null;
            boolean ipValida = false;

            Method resourceMethod = resourceInfo.getResourceMethod();
            Secured methodAnnot = resourceMethod.getAnnotation(Secured.class);
            ruta = ruta.endsWith("/count") ? ruta.replace("/count", "") : ruta;

            if (methodAnnot != null && !methodAnnot.validarPermiso()) {
                return Response.status(Response.Status.OK).entity(tokenAct.getUsuario().getAsJSON()).build();
            }

            if (methodAnnot == null || methodAnnot.requiereEmpresaId()) {
                Query query = this.em.createNativeQuery("WITH validacion AS (\n"
                        + "SELECT\n"
                        + "    u.estado,\n"
                        + "    u.expira_password > NOW() AS passwd_vigente,\n"
                        + "    u.ip_permitida,\n"
                        + "    COALESCE(pe.valido,false) AS tiene_permiso,\n"
                        + "    pe.areas::bigint[],\n"
                        + "    rec.nombre,\n"
                        + "    rec.codigo,\n"
                        + "    rec.validacion_area,\n"
                        + "    tb.id,\n"
                        + "    tb.expira\n"
                        + "FROM emp.usuario u\n"
                        + "    LEFT JOIN emp.recurso rec ON rec.tipo = ?1 AND rec.ruta = ?2\n"
                        + "    LEFT JOIN emp.usuario_empresa ue on ue.pk_usuario_id = u.id AND ue.pk_empresa_id = ?3\n"
                        + "    LEFT JOIN emp.perfil p on p.id = ue.pk_perfil_id\n"
                        + "    LEFT JOIN emp.permiso pe ON pe.fk_perfil_id = p.id AND pe.fk_recurso_id = rec.id AND pe.valido = true\n"
                        + "    LEFT JOIN emp.token_activo tb ON tb.fk_usuario_id = u.id AND tb.id = ?4\n"
                        + "WHERE u.id = ?5\n"
                        + ")\n"
                        + "SELECT v.estado, \n"
                        + "	v.passwd_vigente, \n"
                        + "	bool_or(v.tiene_permiso) AS tiene_permiso, \n"
                        + "	CASE WHEN v.validacion_area = true THEN array_cat_agg(v.areas) @> ?6::bigint[] ELSE true END AS permiso_area, \n"
                        + "	v.nombre, \n"
                        + "	v.codigo, \n"
                        + "	v.id, \n"
                        + "	v.expira,\n"
                        + "	CASE WHEN ?7::inet <<= ANY(v.ip_permitida) THEN true ELSE false END AS ip_valida  \n"
                        + "FROM validacion v\n"
                        + "GROUP BY v.estado, v.passwd_vigente, v.nombre, v.codigo, v.id, v.expira, v.validacion_area, v.ip_permitida");
                ;
                ValidadorArea areaVal = resourceMethod.getAnnotation(ValidadorArea.class);
                String param = "{}";
                if (areaVal != null) {
                    if (requestContext.getUriInfo().getQueryParameters().get("filterList") == null) {
                        Mensaje msg = new Mensaje("ACCESO NO PERMITIDO", "No ha establecido el area a consultar.", TipoMensaje.warn);
                        return Response.status(Response.Status.FORBIDDEN).entity(msg).build();
                    }
                    String filterList = requestContext.getUriInfo().getQueryParameters().get("filterList").get(0);
                    List<LinkedHashMap> filtro = Util.fromJson(filterList, List.class);
                    for (LinkedHashMap filt : filtro) {
                        if (((String) filt.get("field")).equals(areaVal.value())) {
                            param = ((String) filt.get("value1"));
                        }
                    }
                    if (param == null || param.equals("{}")) {
                        Mensaje msg = new Mensaje("ACCESO NO PERMITIDO", "No ha establecido el area a consultar", TipoMensaje.warn);
                        return Response.status(Response.Status.FORBIDDEN).entity(msg).build();
                    }
                }

                query.setParameter(1, method);
                query.setParameter(2, ruta);
                query.setParameter(3, Integer.parseInt(empresa_id));
                query.setParameter(4, tokenAct.getId());
                query.setParameter(5, tokenAct.getUsuario().getId());
                query.setParameter(6, param);
                query.setParameter(7, httpRequest.getRemoteAddr());

                Object[] tupla = (Object[]) query.getSingleResult();
                estado = (String) tupla[0];
                passwdVigente = tupla[1] == null ? false : (Boolean) tupla[1];
                tienePermiso = tupla[2] == null ? false : (Boolean) tupla[2];
                permisoArea = tupla[3] == null ? false : (Boolean) tupla[3];
                nombreRecurso = (String) tupla[4];
                codigoRecurso = (String) tupla[5];
                idToken = (String) tupla[6];
                expToken = (Date) tupla[7];
                ipValida = (Boolean) tupla[8];

               /* if (!ipValida) {
                    Mensaje msg = new Mensaje(
                            "ACCESO NO PERMITIDO",
                            "Su dirección IP no se encuentra autorizada para realizar peticiones. "
                            + "Por favor pongase en contacto con el administrador.",
                            TipoMensaje.warn
                    );
                    return Response.status(Response.Status.FORBIDDEN).entity(msg).build();
                }*/

                // TODO: Se debe determinar que tratamiento dar cuando no es posible recibir el id de empresa de la sesión
                if (idToken != null) {
                    // En este punto, si las fechas de expiración del token en DB es diferente a la recibida en el token, el token pudo ser manipulado
                    // y la firma del mismo fue vulnerada, supondría una situación de alto riesgo
                    if (expToken.compareTo(tokenAct.getExpira()) != 0) {
                        tokenFacade.remove(new TokenActivo(idToken));
                        Mensaje msg = new Mensaje("TOKEN ALTERADO", "Fechas de expiración no coinciden, posible alteración de token: " + token, TipoMensaje.error, Mensaje.COD_TOKEN_ALTERADO);
                        Logger.getLogger(AuthorizationFacade.class.getName()).log(Level.SEVERE, msg.toString());
                        this.usuarioFacade.bloquearUsuario(tokenAct.getUsuario());
                        return Response.status(Response.Status.FORBIDDEN).entity(new Mensaje()).build();
                    }
                    if (expToken.before(new Date())) {
                        tokenFacade.remove(new TokenActivo(idToken));
                        Mensaje msg = new Mensaje("TOKEN EXPIRADO", "Token de sesión expirado", TipoMensaje.warn, Mensaje.COD_TOKEN_EXPIRADO);
                        return Response.status(Response.Status.UNAUTHORIZED).entity(msg).build();
                    }
                } else {
                    Mensaje msg = new Mensaje("TOKEN EXPIRADO", "Token de sesión expirado", TipoMensaje.warn, Mensaje.COD_TOKEN_EXPIRADO);
                    return Response.status(Response.Status.UNAUTHORIZED).entity(msg).build();
                }

                // si idToken != null indica que se recibió un token que no se encuentra en la lista blanca
                if (EstadoUsuario.CAMBIO_PASSWD.name().equals(estado)) {
                    Mensaje msg = new Mensaje("CONTRASEÑA EXPIRADA", "Por favor actualice su contraseña para continuar", TipoMensaje.warn, Mensaje.COD_PASSWD_EXPIRADO);
                    return Response.status(Response.Status.UNAUTHORIZED).entity(msg).build();
                }

                if (!EstadoUsuario.ACTIVO.name().equals(estado)) {
                    Mensaje msg = new Mensaje("USUARIO NO VALIDO", "El usuario no se encuentra activo actualmente", TipoMensaje.error);
                    return Response.status(Response.Status.FORBIDDEN).entity(msg).build();
                }

                if (!passwdVigente) {
                    // Establece el estado del usuario para cambio de contraseña
                    Usuario usrDB = usuarioFacade.find(tokenAct.getUsuario().getId());
                    usrDB.setEstado(EstadoUsuario.CAMBIO_PASSWD);
                    usuarioFacade.edit(usrDB);
                    Mensaje msg = new Mensaje("CONTRASEÑA EXPIRADA", "Por favor actualice su contraseña para continuar", TipoMensaje.warn, Mensaje.COD_PASSWD_EXPIRADO);
                    return Response.status(Response.Status.UNAUTHORIZED).entity(msg).build();
                }
                if (!tienePermiso) {
                    Mensaje msg = new Mensaje("ACCESO DENEGADO", "No tiene asignado el permiso \"" + nombreRecurso + "\" [" + codigoRecurso + "]", TipoMensaje.warn, Mensaje.COD_PERMISOS_INSUFICIENTES);
                    return Response.status(Response.Status.FORBIDDEN).entity(msg).build();
                }

                if (!permisoArea) {
                    Mensaje msg = new Mensaje("ACCESO DENEGADO", "No tiene permisos para acceder a los datos de area", TipoMensaje.warn, Mensaje.COD_VALIDACION_AREA);
                    return Response.status(Response.Status.UNAUTHORIZED).entity(msg).build();
                }
            } else {
                tienePermiso = true;
            }

            if (tienePermiso) {
                return Response.status(Response.Status.OK).entity(tokenAct.getUsuario().getAsJSON()).build();
            }
            Mensaje msg = new Mensaje("ACCESO DENEGADO", "No tiene asignado el permiso \"" + nombreRecurso + "\" [" + codigoRecurso + "]", TipoMensaje.warn, Mensaje.COD_PERMISOS_INSUFICIENTES);
            return Response.status(Response.Status.FORBIDDEN).entity(msg).build();
        } else {
            Mensaje msg = new Mensaje("ACCESO DENEGADO", "Token proporcionado no es válido", TipoMensaje.error, Mensaje.COD_TOKEN_NO_VALIDO);
            return Response.status(Response.Status.FORBIDDEN).entity(msg).build();
        }
    }
}
