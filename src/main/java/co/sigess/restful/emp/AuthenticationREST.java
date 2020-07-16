/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.sigess.restful.emp;

import co.sigess.entities.com.ApiVersion;
import co.sigess.entities.com.Mensaje;
import co.sigess.entities.com.TipoMensaje;
import co.sigess.entities.emp.TokenActivo;
import co.sigess.entities.emp.Usuario;
import co.sigess.exceptions.UserMessageException;
import co.sigess.facade.core.LoaderFacade;
import co.sigess.facade.emp.TokenFacade;
import co.sigess.facade.emp.UsuarioFacade;
import co.sigess.restful.security.Auditable;
import co.sigess.restful.security.RollBackResponse;
import co.sigess.restful.security.UtilSecurity;
import co.sigess.util.Util;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.Produces;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import jdk.nashorn.internal.runtime.JSONFunctions;

/**
 * REST Web Service
 *
 * @author fernando
 */
@Path("authenticate")
public class AuthenticationREST {

    @Context
    HttpServletRequest httpRequest;

    @EJB
    private UsuarioFacade usuarioFacade;

    @EJB
    private TokenFacade tokenFacade;

    @EJB
    private LoaderFacade loaderFacade;

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Auditable
    @RollBackResponse(proceso = "login")
    public Response authenticateUser(
            @QueryParam("r") boolean recordar,
            @QueryParam("pin") String mfacod,
            String loginRequest) {
        try {
            String[] array = loginRequest.split(":");
            String email = array[0].trim().toLowerCase();
            String passw = array[1];
            // Es necesario cargar como atributo el email, ya que este es un recurso que no pide token
            // por lo que en el contexto no esta cargado el usuario que realiza la petición, sólo es posible
            // capturar el email con el que se está tratando de loguear el usuario
            httpRequest.setAttribute("body", email);

            String appVersion = httpRequest.getHeader(UtilSecurity.APP_VERSION_HEADER_NAME);
            Map<String, Object> map = (Map<String, Object>) loaderFacade.getApiVersion().getSoportado().get(appVersion);
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

            Usuario user = usuarioFacade.authenticate(email, passw, mfacod);
            if (user != null && user.codigo == 0) {
                Map<String, Object> resp = new HashMap<>();
                tokenFacade.eliminarTokensUsuario(user);
                switch (user.getEstado()) {
                    case ACTIVO:
                    case CAMBIO_PASSWD:
                        //user = new Usuario(user.getId(), user.getEmail(), user.getEstado());
                        Calendar fechaActual = Calendar.getInstance();
                        String tokenId = UUID.randomUUID().toString();
                        String refreshId = UUID.randomUUID().toString();

                        // Genera el token de acceso 
                        long expMillis = fechaActual.getTimeInMillis() + (1000 * UtilSecurity.TOKEN_DURATION);
                        Date exp2 = new Date(1000 * (expMillis / 1000));

                        String jsonUser = Util.objectToJson(new Usuario(user.getId(), user.getEmail(), user.getEstado()));
                        String token = UtilSecurity.generateJWT(jsonUser, exp2, tokenId, false);
                        resp.put(UtilSecurity.TOKEN_NAME, token);

                        // Genera el token para solicitar tokens de acceso
                        long expRefMillis = fechaActual.getTimeInMillis() + (1000 * UtilSecurity.TOKEN_REFRESH_DURATION);
                        Date expRefresh = new Date(1000 * (expRefMillis / 1000));
                        String tokenRefresher = UtilSecurity.generateJWT(jsonUser, expRefresh, refreshId, true);

                        TokenActivo ta = new TokenActivo();
                        ta.setId(tokenId);
                        ta.setExpira(exp2);
                        ta.setFechaGeneracion(fechaActual.getTime());
                        ta.setUsuario(user);
                        ta.setIp(httpRequest.getRemoteAddr());
                        ta.setTipo(UtilSecurity.TOKEN_ACCESS_ROLE);
                        tokenFacade.create(ta);

                        if (recordar) {
                            TokenActivo taRefresh = new TokenActivo();
                            taRefresh.setId(refreshId);
                            taRefresh.setExpira(expRefresh);
                            taRefresh.setFechaGeneracion(fechaActual.getTime());
                            taRefresh.setUsuario(user);
                            taRefresh.setIp(httpRequest.getRemoteAddr());
                            taRefresh.setTipo(UtilSecurity.TOKEN_REFRESH_ROLE);
                            tokenFacade.create(taRefresh);
                            resp.put(UtilSecurity.TOKEN_REFRESH_NAME, tokenRefresher);
                        }
                        resp.put("usuario", user);
                        return Response.ok().entity(resp).build();
                    case INACTIVO:
                        Mensaje msg = new Mensaje("USUARIO INACTIVO", "El usuario se encuentra inactivo, por favor comuniquese con el administrador del sistema", TipoMensaje.warn);
                        return Response.status(Response.Status.UNAUTHORIZED).entity(msg).build();
                    case BLOQUEADO:
                    case ELIMINADO:
                    default:
                        return Response.status(Response.Status.FORBIDDEN).entity(new Mensaje()).build();
                }
            } else if (user != null && user.codigo == Mensaje.COD_LOGIN_OK_SIN_MFA) {
                Mensaje msg = new Mensaje("ENVIO DE PIN REALIZADO", "Para continuar, por favor ingrese el pin recibido", TipoMensaje.info, Mensaje.COD_LOGIN_OK_SIN_MFA);
                return Response.status(Response.Status.UNAUTHORIZED).entity(msg).build();
            } else {
                return Response.status(Response.Status.FORBIDDEN).entity(new Mensaje()).build();
            }
        } catch (Exception ex) {
            if (ex.getCause() instanceof UserMessageException) {
                UserMessageException ume = (UserMessageException) ex.getCause();
                if (null == ume.getCodigo()) {
                    return Response.status(Response.Status.FORBIDDEN).entity(new Mensaje()).build();
                } else {
                  //  System.out.print(ume.getClass());
                    switch (ume.getCodigo()) {
                        case Mensaje.COD_USUARIO_LOGIN_PREVIO:
                        case Mensaje.COD_IP_NO_PERMITIDA:
                        case Mensaje.COD_PIN_INCORRECTO:
                        case Mensaje.COD_MAX_INTENTOS_LOGIN:
                        case Mensaje.COD_USR_SIN_NUM_MOVIL:
                        case Mensaje.COD_MOVIL_NO_VALIDO:
                        case Mensaje.COD_ERROR_ENVIO_SMS:
                            return Response.status(Response.Status.UNAUTHORIZED).entity(ume.getObjetoMensaje()).build();
                        default:
                            return Response.status(Response.Status.FORBIDDEN).entity(new Mensaje()).build();
                    }
                }
            } else {
                return Util.manageException(ex, AuthenticationREST.class);
            }
        }
        
    }

    @POST
    @Path("refrescarToken")
    @Produces(MediaType.APPLICATION_JSON)
    public Response refrescarToken(String token) {
        try {
            TokenActivo tk = UtilSecurity.verifyJWT(token, true);
            TokenActivo tkDB = tokenFacade.find(tk.getId());
            if (tkDB == null) {
                Mensaje msg = new Mensaje("Error de token", "Token de sesión expirado", TipoMensaje.error, Mensaje.COD_TOKEN_REFRESH_EXPIRADO);
                return Response.status(Response.Status.UNAUTHORIZED).entity(msg).build();
            }

            Calendar fechaActual = Calendar.getInstance();
            String tokenId = UUID.randomUUID().toString();
            Map<String, Object> resp = new HashMap<>();
            resp.put("usuario", tk.getUsuario());

            long expMillis = fechaActual.getTimeInMillis() + (1000 * UtilSecurity.TOKEN_DURATION);
            Date expira = new Date(1000 * (expMillis / 1000));
            String newToken = UtilSecurity.generateJWT(Util.objectToJson(tk.getUsuario()), expira, tokenId, false);
            resp.put(UtilSecurity.TOKEN_NAME, newToken);

            TokenActivo ta = new TokenActivo();
            ta.setId(tokenId);
            ta.setExpira(expira);
            ta.setFechaGeneracion(fechaActual.getTime());
            ta.setUsuario(tk.getUsuario());
            ta.setIp(httpRequest.getRemoteAddr());
            ta.setTipo(UtilSecurity.TOKEN_ACCESS_ROLE);
            tokenFacade.create(ta);
            return Response.ok().entity(resp).build();
        } catch (ExpiredJwtException ex) {
            Mensaje msg = new Mensaje("Error de token", "Token de sesión expirado", TipoMensaje.error, Mensaje.COD_TOKEN_REFRESH_EXPIRADO);
            return Response.status(Response.Status.UNAUTHORIZED).entity(msg).build();
        } catch (IllegalArgumentException | MalformedJwtException | UnsupportedJwtException ex) {
            return Response.status(Response.Status.BAD_REQUEST).entity(new Mensaje()).build();
        } catch (Exception ex) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(new Mensaje()).build();
        }
    }

    @POST
    @Path("logout")
    @Produces(MediaType.APPLICATION_JSON)
    public Response logout(Map<String, String> tokenMap) {
        try {
            if (tokenMap == null
                    || tokenMap.get(UtilSecurity.TOKEN_NAME) == null
                    || tokenMap.get(UtilSecurity.TOKEN_NAME).isEmpty()
                    || tokenMap.get(UtilSecurity.TOKEN_REFRESH_NAME) == null
                    || tokenMap.get(UtilSecurity.TOKEN_REFRESH_NAME).isEmpty()) {
                Mensaje msg = new Mensaje("ERROR AL CERRAR SESIÓN", "No se recibieron los parametros correctos", TipoMensaje.error);
                return Response.status(Response.Status.BAD_REQUEST).entity(msg).build();
            }

            this.invalidarToken(tokenMap.get(UtilSecurity.TOKEN_NAME), false);
            this.invalidarToken(tokenMap.get(UtilSecurity.TOKEN_REFRESH_NAME), true);
            return Response.ok(new Mensaje("CIERRE SESIÓN REALIZADO", "Se ha cerrado satisfactoriamente la sesión", TipoMensaje.success)).build();
        } catch (Exception ex) {
            return Util.manageException(ex, AuthenticationREST.class);
        }
    }

    private void invalidarToken(String token, boolean refresh) throws Exception {
        TokenActivo tokAct = null;
        try {
            tokAct = UtilSecurity.verifyJWT(token, refresh);
        } catch (ExpiredJwtException ex) {
            String idToken = ex.getClaims().get("cod", String.class);
            tokAct = new TokenActivo(idToken);
        } catch (UnsupportedJwtException uex) {
            String idToken = UtilSecurity.getTokenClaims(token).get("cod", String.class);
            tokAct = new TokenActivo(idToken);
            Logger.getLogger(AuthenticationREST.class.getName()).log(Level.SEVERE, "", uex);
        } catch (SignatureException ex) {
            String jsonUser = UtilSecurity.getTokenClaims(token).getSubject();
            Usuario user = Util.fromJson(jsonUser, Usuario.class);
            this.usuarioFacade.bloquearUsuario(user);
            Mensaje msg = new Mensaje("TOKEN FIRMA INVALIDA", "La firma del token recibido presenta inconsistencias: " + token, TipoMensaje.error, Mensaje.COD_TOKEN_FIRMA_INVALIDA);
            Logger.getLogger(AuthenticationREST.class.getName()).log(Level.SEVERE, msg.toString(), ex);
            throw new IllegalArgumentException();
        } catch (MalformedJwtException ex) {
            // Debido a la imposibilidad de decodificar un token mal formado, solo se puede registrar en log el evento
            // y se debe finalizar el método
            Mensaje msg = new Mensaje("TOKEN MAL FORMADO", "El token recibido presenta inconsistencias: " + token, TipoMensaje.error, Mensaje.COD_TOKEN_ALTERADO);
            Logger.getLogger(AuthenticationREST.class.getName()).log(Level.SEVERE, msg.toString(), ex);
            throw new IllegalArgumentException();
        }
        this.tokenFacade.remove(tokAct);
    }

    @GET
    @Path("recuperarPasswd/{email}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response recuperarPasswd(@PathParam("email") String email) {
        try {
            if (email != null) {
                Usuario usuario = usuarioFacade.recuperarPasswd(email.trim().toLowerCase());
            }
            return Response.ok(new Mensaje("Restauración realizada", "Se ha enviado un correo electrónico con las instrucciones para reestablecer las credenciales de usuario", TipoMensaje.success)).build();
        } catch (Exception ex) {
            return Util.manageException(ex, AuthenticationREST.class);
        }
    }

    @POST
    @Path("activetokens")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getIfExistToken(String loginRequest) throws Exception{
        String[] array = loginRequest.split(":");
            String email = array[0].trim().toLowerCase();
             Map<String, String> da = new HashMap<>();
            String passw = array[1];
            Usuario user = usuarioFacade.authenticate(email, passw, null);
            List tokens = tokenFacade.getTokensByUser(user);
            if (tokens.size() > 0) {
               
            da.put("exit", "true");
                return Response.ok(da).build();
            }
             da.put("exit", "false");
               return Response.ok(da).build();
    }
    
    
    @GET
    @Path("version")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getApiVersion(@QueryParam("r") boolean refresh) {
        try {
            ApiVersion api = null;
            if (refresh) {
                api = this.loaderFacade.refreshApiVersion();
            } else {
                api = this.loaderFacade.getApiVersion();
            }
            return Response.ok(api).build();
        } catch (Exception ex) {
            return Util.manageException(ex, AuthenticationREST.class);
        }
    }

    @GET
    @Path("politicaDatos")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getPolitica() {
        try {
            String politica = "<div style=\"text-align:justify;margin:auto;\">\n"
                    + "<h4>POLÍTICA DE TRATAMIENTO DE DATOS PERSONALES</h4>\n"
                    + "\n"
                    + "\n"
                    + "\n"
                    + "<h6>1. NORMATIVIDAD LEGAL Y ÁMBITO DE APLICACIÓN:</h6>\n"
                    + "\n"
                    + "<p>\n"
                    + "La presente política de Tratamiento de datos personales es elaborada de \n"
                    + "conformidad con lo dispuesto en la Constitución Política, la Ley 1581 de\n"
                    + " 2012, el Decreto Reglamentario 1377 de 2013 y demás disposiciones \n"
                    + "complementarias y será aplicada por LER Prevención respecto de la \n"
                    + "recolección, almacenamiento, uso, circulación, supresión y de todas \n"
                    + "aquellas actividades que constituyan tratamiento de datos personales.\n"
                    + "</p>\n"
                    + "<h6>2. DEFINICIONES:</h6>\n"
                    + "\n"
                    + "<p>\n"
                    + "Para efectos de la ejecución de la presente política y de conformidad \n"
                    + "con la normatividad legal, serán aplicables las siguientes definiciones:\n"
                    + "\n"
                    + "    a) Autorización: Consentimiento previo, expreso e informado del \n"
                    + "Titular para llevar a cabo el Tratamiento de datos personales;\n"
                    + "    b) Aviso de privacidad: Documento físico, electrónico o en cualquier\n"
                    + " otro formato generado  por  el  Responsable  que  se  pone  a  \n"
                    + "disposición  del  Titular  para  el tratamiento de sus datos personales.\n"
                    + " En el Aviso de Privacidad se comunica al Titular la  información \n"
                    + "relativa a  la  existencia de  las  políticas de  tratamiento de \n"
                    + "información que le serán aplicables, la forma de acceder a las mismas y \n"
                    + "la finalidad del tratamiento que se pretende dar a los datos personales;\n"
                    + "    c) Base de Datos: Conjunto organizado de datos personales que sea \n"
                    + "objeto de Tratamiento;\n"
                    + "    d) Dato personal: Cualquier información vinculada o que pueda \n"
                    + "asociarse a una o varias personas naturales determinadas o \n"
                    + "determinables;\n"
                    + "    e) Dato público: Es el dato calificado como tal según los mandatos \n"
                    + "de la ley o de la Constitución Política  y  aquel  que  no  sea  \n"
                    + "semiprivado, privado  o  sensible.  Son públicos,  entre  otros,  los  \n"
                    + "datos  relativos  al  estado  civil  de  las  personas,  a  su profesión\n"
                    + " u oficio, a su calidad de comerciante o de servidor público y aquellos \n"
                    + "que puedan obtenerse sin reserva alguna. Por su naturaleza, los datos \n"
                    + "públicos pueden estar contenidos, entre otros, en registros públicos, \n"
                    + "documentos públicos, gacetas y boletines oficiales;\n"
                    + "    f) Dato privado: Es  el dato que por su naturaleza íntima o  \n"
                    + "reservada sólo es relevante para el titular;\n"
                    + "    g) Datos sensibles: Se entiende por datos sensibles aquellos que \n"
                    + "afectan la intimidad del Titular o cuyo uso indebido puede generar su \n"
                    + "discriminación, tales como aquellos que revelen el origen racial o \n"
                    + "étnico, la orientación política, las convicciones religiosas  o  \n"
                    + "filosóficas,  la  pertenencia  a  sindicatos,  organizaciones sociales, \n"
                    + " de derechos humanos o que promueva intereses de cualquier partido \n"
                    + "político o que garanticen los derechos y garantías de partidos políticos\n"
                    + " de oposición, así como los datos relativos a la salud, a la vida sexual\n"
                    + " y los datos biométricos;\n"
                    + "    h) Encargado del Tratamiento: Persona natural o jurídica, pública o \n"
                    + "privada, que por sí misma o en asocio con otros, realice el Tratamiento \n"
                    + "de datos personales por cuenta del Responsable del Tratamiento;\n"
                    + "    i) Responsable del Tratamiento: Persona natural o jurídica, pública o\n"
                    + " privada, que por sí misma o en asocio con otros, decida sobre la base \n"
                    + "de datos y/o el Tratamiento de los datos;\n"
                    + "    j) Titular: Persona natural cuyos datos personales sean objeto de \n"
                    + "Tratamiento;\n"
                    + "    k) Tratamiento: Cualquier operación o conjunto de operaciones sobre \n"
                    + "datos personales, tales como la recolección, almacenamiento, uso, \n"
                    + "circulación o supresión de los mismos.\n"
                    + "</p>\n"
                    + "<h6>3. TRATAMIENTO AL QUE SERÁN SOMETIDOS LOS DATOS PERSONALES Y FINALIDADES:</h6>\n"
                    + "<p>\n"
                    + "Los datos personales de Clientes, Proveedores, Contratistas, \n"
                    + "Colaboradores, terceros y empleados y/o posibles empleados, que se \n"
                    + "recojan o que se encuentren registrados en las bases de datos, serán \n"
                    + "tratados conforme a  los principios rectores para el Tratamiento de \n"
                    + "Datos Personales y disposiciones contenidas en la Ley 1581 de 2012\n"
                    + "</p>\n"
                    + "<p>\n"
                    + "Únicamente se recolectará los datos personales del Titular, que son \n"
                    + "pertinentes y adecuados para la finalidad para la cual son requeridos y \n"
                    + "no recogerá datos personales sin autorización previa, expresa e \n"
                    + "informada del Titular, a excepción de los casos previstos en la ley en \n"
                    + "los que no es necesaria la obtención de dicha autorización. \n"
                    + "Además, se abstendrá de utilizar medios engañosos y fraudulentos para \n"
                    + "obtener dicha autorización y en general para tratar datos personales de \n"
                    + "Clientes, Proveedores, Contratistas, Colaboradores, terceros y empleados\n"
                    + " y/o posibles empleados.\n"
                    + "</p>\n"
                    + "<h6>4. FINALIDADES DEL TRATAMIENTO DE DATOS PERSONALES</h6>\n"
                    + "<p>\n"
                    + "Los datos personales del Titular –Cliente- se usaran con las siguientes finalidades: \n"
                    + "</p>\n"
                    + "<p>\n"
                    + "    a) Para el desarrollo de actividades comerciales y transaccionales\n"
                    + "    b) Para la expedición de cotizaciones, facturas, notas pedido y \n"
                    + "demás documentos como soporte de una transacción.\n"
                    + "    c) Para perfeccionar o concretar el pago de un producto o servicio \n"
                    + "adquirido por el Titular.\n"
                    + "    d) Para el despacho de pedidos y prestación de servicios contratados\n"
                    + " por el Titular. Así como el proceso de evaluación y posterior \n"
                    + "calificación de la  calidad del servicio prestado.\n"
                    + "    e) Atención del trámite de garantías y devolución de productos.\n"
                    + "    f) Emisión de respuestas a las consultas acerca de productos y \n"
                    + "servicios ofrecidos, peticiones, quejas y reclamos.\n"
                    + "    g) Realización de estudios con fines estadísticos, medición del \n"
                    + "nivel de satisfacción del cliente y de conocimiento del Cliente.\n"
                    + "    h) Recibir información y ofertas sobre novedades, productos, \n"
                    + "servicios, concursos y eventos especiales propios del objeto social que \n"
                    + "puedan resultar atractivos o beneficiosos para sus clientes.\n"
                    + "    i) Para el  desarrollo de actividades relacionadas con  servicios \n"
                    + "computarizados. La prestación de servicios de atención telefónica; \n"
                    + "cobranzas u otros de naturaleza similar.\n"
                    + "    j) La ejecución de procesos y procedimientos contables.\n"
                    + "    k) Además, para cualquier finalidad adicional que sea debidamente \n"
                    + "autorizada por los Clientes.\n"
                    + "</p>\n"
                    + "<p>\n"
                    + "Los datos personales del Titular –Proveedor-contratistas y terceros se usaran con las siguientes finalidades:\n"
                    + "</p>\n"
                    + "<p>\n"
                    + "    a) Para el normal desarrollo de la relación comercial, de servicios y\n"
                    + " contractual. \n"
                    + "    b) Para el cumplimiento de los servicios ofrecidos por la empresa.\n"
                    + "    c) El desarrollo de estudios de seguridad y financieros. Para llevar\n"
                    + " a cabo procesos contables, Tributarios. Labores de pago de facturas, \n"
                    + "mensajería y demás. Servicios de mantenimiento y adecuaciones. Estudios \n"
                    + "de personal y contratación.\n"
                    + "    d) Desarrollar actividades de capacitación.\n"
                    + "    e) Representación judicial de la compañía.\n"
                    + "    f) Lo relacionado con la prestación de servicios de gestión \n"
                    + "ambiental. La prestación de servicios tecnológicos.\n"
                    + "    g) Desarrollo de mercados y publicidad.\n"
                    + "</p>\n"
                    + "<p>\n"
                    + "Los datos personales del Titular –Empleado y/o posibles empleados se usaran con las siguientes finalidades:\n"
                    + "</p>\n"
                    + "<p>\n"
                    + "    a) Para procesos de selección de personal\n"
                    + "    b) Para posibles contrataciones futuras \n"
                    + "    c) Para contratación directa con la empresa\n"
                    + "    d) Para datos posteriores a la finalización de contrato o terminación de relación laboral\n"
                    + "</p>\n"
                    + "\n"
                    + "<h6>5. AUTORIZACIÓN</h6>\n"
                    + "<p>\n"
                    + "El Tratamiento de Datos Personales manejados por la empresa requiere del\n"
                    + " consentimiento libre, previo, expreso e informado del Titular. LER \n"
                    + "Prevención, en su condición de responsable del Tratamiento de Datos \n"
                    + "Personales, ha dispuesto de los mecanismos necesarios para obtener la \n"
                    + "autorización del Titular.\n"
                    + "</p>\n"
                    + "<p>\n"
                    + "La  autorización  podrá  darse  por  medio  de  un  documento  físico,  \n"
                    + "electrónico  o cualquier otro formato que permita garantizar su \n"
                    + "posterior consulta, y que, además, pueda demostrarse, de manera \n"
                    + "inequívoca, que el Titular de los Datos Personales:\n"
                    + "</p>\n"
                    + "<p>\n"
                    + "a)  autorizó el tratamiento,\n"
                    + "b) conoce y acepta que se utilizará la información para los fines que le han sido informados.\n"
                    + "</p>\n"
                    + "<p>\n"
                    + "Por Tanto, la autorización solicitada deberá incluir:\n"
                    + "</p>\n"
                    + "<p>\n"
                    + "a)  El Responsable del Tratamiento y qué datos se recopilan b)  La finalidad del tratamiento de los datos\n"
                    + "c)  Los derechos de acceso, corrección, actualización o supresión de los datos personales suministrados por el titular\n"
                    + "d)  La identificación, dirección física o electrónica y teléfono del Responsable del\n"
                    + "Tratamiento.\n"
                    + "</p>\n"
                    + "<h6>6. MANEJO DE DATOS SENSIBLES</h6>\n"
                    + "<p>\n"
                    + "LER Prevención no recolectará, usará o tratará datos sensibles salvo que\n"
                    + " el Titular de los mismos emita una autorización expresa para dicho \n"
                    + "tratamiento o en los casos que la ley determina que no se requiere dicha\n"
                    + " autorización.\n"
                    + "</p>\n"
                    + "<h6>7. DERECHOS DE LOS TITULARES.</h6>\n"
                    + "<p>\n"
                    + "Los Titulares de los Datos Personales registrados en las Bases de Datos \n"
                    + "de LER Prevención, tienen los siguientes derechos, contenidos en la Ley \n"
                    + "1581 de 2012 y sus Decretos Reglamentarios:\n"
                    + "</p>\n"
                    + "<p>\n"
                    + "    a) Conocer, actualizar, corregir y/o suprimir sus datos personales. \n"
                    + "Estos derechos los podrán ejercer, entre otros, frente a Datos \n"
                    + "Personales parciales, inexactos, incompletos, fraccionados, que induzcan\n"
                    + " a error, o aquellos cuyo Tratamiento esté expresamente prohibido o no \n"
                    + "haya sido autorizado\n"
                    + "    b) Solicitar prueba de la Autorización otorgada a LER Prevención \n"
                    + "salvo cuando se exceptúe expresamente como requisito para el \n"
                    + "Tratamiento, de conformidad con lo previsto en el artículo 10 de la Ley \n"
                    + "1581 de 2012;\n"
                    + "    c) Ser informado respecto del uso que se ha dado a sus Datos \n"
                    + "Personales\n"
                    + "    d) Mediante reclamo presentado conforme al artículo 15 de la Ley \n"
                    + "1581 de 2012, puede solicitar la revocatoria de la autorización y/o \n"
                    + "solicitar la supresión del Dato Personal cuando en el Tratamiento no se \n"
                    + "respeten los principios, derechos y garantías constitucionales y legales\n"
                    + " o en cualquier momento siempre que el Titular no tenga el deber legal o\n"
                    + " contractual de permanecer en las bases de datos de LER Prevención.\n"
                    + "    e) Acceder de manera gratuita a los Datos Personales que son objeto \n"
                    + "de Tratamiento\n"
                    + "</p>\n"
                    + "<h6>8. DEBERES DE LOS TITULARES.</h6>\n"
                    + "<p>\n"
                    + "El Titular de los Datos Personales debe mantener actualizada su \n"
                    + "información y garantizar, en todo momento, la veracidad de la misma. LER\n"
                    + " Prevención no se hará responsable, en ningún caso, por cualquier tipo \n"
                    + "de responsabilidad derivada por la inexactitud de la información \n"
                    + "suministrada por el Titular.\n"
                    + "</p>\n"
                    + "<h6>9. MEDIDAS DE SEGURIDAD:</h6>\n"
                    + "<p>\n"
                    + "En desarrollo del principio de seguridad establecido en la Ley 1581 de \n"
                    + "2012, LER Prevención adoptará las medidas técnicas, humanas y \n"
                    + "administrativas que sean necesarias para otorgar seguridad a los \n"
                    + "registros evitando su adulteración, pérdida, consulta, uso o acceso no \n"
                    + "autorizado o fraudulento. \n"
                    + "El personal que realice el tratamiento de los datos personales ejecutará\n"
                    + " los protocolos establecidos con el fin de garantizar la seguridad de la\n"
                    + " información.\n"
                    + "</p>\n"
                    + "<h6>10. RESPONSABLE DEL TRATAMIENTO</h6>\n"
                    + "<p>\n"
                    + "LER Prevención ha designado para atender todo lo relacionado con \n"
                    + "consultas y reclamos de que trata el Título V de la Ley de protección de\n"
                    + " datos personales a la secretaria de Gerencia, y para dichos efectos, un\n"
                    + " titular de información, puede dirigir su consulta o queja al correo \n"
                    + "electrónico info@mdasociados.com dirección Carrera 7ª No. 123 -34 Ofi. \n"
                    + "302 Bogotá- Colombia\n"
                    + "</p>\n"
                    + "<h6>11. PROCEDIMIENTO DE CONSULTAS Y RECLAMOS.</h6>\n"
                    + "<p>\n"
                    + "LER Prevención, garantiza el derecho de consulta, corrección, \n"
                    + "actualización y/o supresión de los datos personales del Titular, \n"
                    + "suministrando al mismo y cuando actúe en ejercicio de su derecho, toda \n"
                    + "la información contenida en su registro individual o que esté vinculada \n"
                    + "con la identificación del titular.\n"
                    + "</p>\n"
                    + "<p>\n"
                    + "Todas y cada una de las consultas o reclamos (quejas) deberán ser \n"
                    + "puestas en un texto electrónico al correo electrónico  \n"
                    + "info@mdasociados,com o envió de  una comunicación física a la Carrera 7 A\n"
                    + " No, 123 -24 oficina 302 Bogotá- Colombia, es decir, se excluye  la  \n"
                    + "posibilidad de  formular una  consulta o  reclamo  de  manera verbal  o \n"
                    + "telefónica.\n"
                    + "</p>\n"
                    + "<p>\n"
                    + "Una vez recibida por LER Prevención, será radicada en la fecha, día y \n"
                    + "hora de su recepción y se le aplicarán los términos de respuesta y los \n"
                    + "procedimientos consignados por la Ley 1581 de 2012 en el Titulo V \n"
                    + "denominado: Procedimientos.\n"
                    + "</p>\n"
                    + "<p>\n"
                    + "La finalidad de estas alternativas es, mantener una prueba de la \n"
                    + "petición presentada y en segundo lugar, tener certeza de la fecha a \n"
                    + "partir de la cual empiezan a correr los términos previstos en la Ley.\n"
                    + "</p>\n"
                    + "<p>\n"
                    + "La solicitud debe indicar como mínimo\n"
                    + "</p>\n"
                    + "<p>\n"
                    + "    a) El nombre, domicilio del Titular y medio de contacto para recibir\n"
                    + " la respuesta como teléfono, correo electrónico, dirección de \n"
                    + "residencia.\n"
                    + "    b) Los documentos que acrediten la identidad del Titular o la \n"
                    + "representación de su representante.\n"
                    + "    c) La descripción clara y precisa de los datos personales o hechos \n"
                    + "respecto de los cuales el Titular busca ejercer alguno de los derechos y\n"
                    + " que dan lugar a su solicitud.\n"
                    + "\n"
                    + "</p>\n"
                    + "<h6>12. SUPRESIÓN DE DATOS.</h6>\n"
                    + "<p>\n"
                    + "El Titular tiene el derecho, en todo momento, a solicitar a la supresión (eliminación) de sus datos personales cuando:\n"
                    + "</p>\n"
                    + "<p>\n"
                    + "    a) La solicitud de supresión de la información no procederán cuando \n"
                    + "el Titular tenga un deber legal o contractual de permanecer en la base \n"
                    + "de datos.\n"
                    + "    b) La eliminación de datos obstaculice actuaciones judiciales o \n"
                    + "administrativas vinculadas a obligaciones fiscales, la investigación y \n"
                    + "persecución de delitos o la actualización de sanciones administrativas.\n"
                    + "    c) Los datos sean necesarios para proteger los intereses \n"
                    + "jurídicamente tutelados del Titular; para realizar una acción en función\n"
                    + " del interés público, o para cumplir con una obligación legalmente \n"
                    + "adquirida por el Titular.\n"
                    + "</p>\n"
                    + "<p>\n"
                    + "En caso de resultar procedente la cancelación de los datos personales, \n"
                    + "se procederá realizar operativamente la supresión de tal manera que la \n"
                    + "eliminación no permita la recuperación de la información. \n"
                    + "También debe tenerse en cuenta que en algunos casos cierta información \n"
                    + "deberá permanecer en registros históricos por cumplimiento de deberes \n"
                    + "legales de la organización por lo que su supresión versará frente al \n"
                    + "tratamiento activo de los mismos y de acuerdo a la solicitud del \n"
                    + "Titular.\n"
                    + "</p>\n"
                    + "\n"
                    + "<h6>13. IDENTIFICACIÓN DE LAS BASES DE DATOS Y PERIODO DE VIGENCIA.</h6>\n"
                    + "<p>\n"
                    + "Las bases de datos bajo tratamiento por parte de LER Prevención son las \n"
                    + "consagradas en el manual interno de políticas y procedimientos en \n"
                    + "materia de Protección de datos y estarán vigentes durante toda la \n"
                    + "duración del objeto social de la empresa.\n"
                    + "</p>\n"
                    + "<h6>14. VIGENCIA Y ACTUALIZACIÓN DE LAS POLÍTICAS.</h6>\n"
                    + "<p>\n"
                    + "La presente Política de Protección de Datos Personales rige a partir del quince 15 de enero de 2019.\n"
                    + "</p>\n"
                    + "<p>\n"
                    + "Cualquier cambio sustancial en las políticas de Tratamiento, se \n"
                    + "comunicará de forma oportuna a los Titulares de los datos a través de \n"
                    + "los medios habituales de contacto: Correo electrónico enviado a los \n"
                    + "Titulares: Que aplica si tengo autorizada la cuenta de correo \n"
                    + "electrónico del mismo.\n"
                    + "</p>\n"
                    + "<p>\n"
                    + "Para los Titulares que no tengan acceso a medios electrónicos o aquellos\n"
                    + " a los que no sea posible contactar, se comunicará a través de avisos \n"
                    + "abiertos en la sede de la empresa.\n"
                    + "</p>\n"
                    + "<p>\n"
                    + "Las comunicaciones se enviarán como mínimo, diez (10) días antes de \n"
                    + "implementar las nuevas políticas y/o actualización sustancial de la \n"
                    + "misma.\n"
                    + "</p>\n"
                    + "</div>";
            Map<String, String> da = new HashMap<>();
            da.put("data", politica);
            return Response.ok(da).build();
        } catch (Exception ex) {
            return Util.manageException(ex, AuthenticationREST.class);
        }
    }

}
