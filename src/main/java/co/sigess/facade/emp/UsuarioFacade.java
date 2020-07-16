/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.sigess.facade.emp;

import co.sigess.entities.com.Mensaje;
import co.sigess.entities.com.TipoMensaje;
import co.sigess.facade.com.AbstractFacade;
import co.sigess.entities.emp.EstadoUsuario;
import co.sigess.entities.emp.Usuario;
import co.sigess.entities.emp.UsuarioEmpresa;
import co.sigess.entities.emp.UsuarioEmpresaPK;
import co.sigess.exceptions.UserMessageException;
import co.sigess.facade.core.EmailFacade;
import co.sigess.facade.core.SMSFacade;
import co.sigess.facade.core.TipoMail;
import co.sigess.restful.security.AuthorizationFacade;
import co.sigess.restful.security.UtilSecurity;
import co.sigess.util.Util;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TemporalType;
import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import javax.ws.rs.core.Context;

/**
 *
 * @author fmoreno
 */
@javax.ejb.Stateless
public class UsuarioFacade extends AbstractFacade<Usuario> {

    @Context
    private HttpServletRequest httpRequest;

    @EJB
    private EmailFacade emailFacade;

    @EJB
    private SMSFacade smsFacade;

    @EJB
    private UsuarioEmpresaFacade usuarioEmpresaFacade;

    @EJB
    private TokenFacade tokenFacade;

    @PersistenceContext(unitName = "SIGESS_PU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public UsuarioFacade() {
        super(Usuario.class);
    }

    public Usuario findByEmail(String email) {
        Query query = em.createQuery("SELECT u from Usuario u where u.email = :email");
        query.setParameter("email", email);
        try {
            Usuario user = (Usuario) query.getSingleResult();
            return user;
        } catch (Exception ejbExc) {
            return null;
        }
    }

    public Usuario authenticate(String email, String passw, String mfaCod) throws Exception {
        Query q = this.em.createNativeQuery("SELECT id, email, estado, codigo, avatar, icon, fecha_acepta_terminos::TIMESTAMP, ip_valida, mfa, codigo_mfa, numero_movil "
                + "FROM emp.verificar_login(?1, ?2, ?3,?4) "
                + "AS (id INTEGER, email TEXT, estado TEXT, password TEXT, expira_password TIMESTAMP, codigo INTEGER, avatar TEXT, icon TEXT, fecha_acepta_terminos TIMESTAMP, ip_valida BOOLEAN, mfa BOOLEAN, codigo_mfa VARCHAR(10), numero_movil VARCHAR(18))");
        q.setParameter(1, email);
        q.setParameter(2, UtilSecurity.createEmailPasswordHash(email, passw));
        q.setParameter(3, httpRequest.getRemoteAddr());
        q.setParameter(4, mfaCod);
        Object[] resp = (Object[]) q.getSingleResult();
        int codigo = (Integer) resp[3];
       // System.out.print(codigo);
        switch (codigo) {
            case Mensaje.COD_IP_NO_PERMITIDA:
                throw new UserMessageException(new Mensaje("ACCESO NO PERMITIDOo", "Su dirección IP no se encuentra autorizada para realizar peticiones. Por favor pongase en contacto con el administrador.", TipoMensaje.warn, Mensaje.COD_IP_NO_PERMITIDA));     
            case Mensaje.COD_USUARIO_NO_VALIDO:
                throw new UserMessageException(new Mensaje("CREDENCIALES INCORRECTAS", "El usuario o contraseña especificada no son correctas", TipoMensaje.warn, Mensaje.COD_USUARIO_NO_VALIDO));
            case Mensaje.COD_PIN_INCORRECTO:
                throw new UserMessageException(new Mensaje("PIN INCORRECTO", "El pin ingresado no es válido", TipoMensaje.warn, Mensaje.COD_PIN_INCORRECTO));
            case Mensaje.COD_USUARIO_LOGIN_PREVIO:
                throw new UserMessageException(new Mensaje("SESIÓN ACTIVA", "Tiene sesiones previas abiertas, por favor cierrelas de manera segura para continuar", TipoMensaje.warn, Mensaje.COD_USUARIO_LOGIN_PREVIO));
            case Mensaje.COD_MAX_INTENTOS_LOGIN:
                throw new UserMessageException(new Mensaje("NÚMERO MÁXIMO ALCANZADO", "Ha alcanzado el número máximo de intentos permitidos", TipoMensaje.warn, Mensaje.COD_MAX_INTENTOS_LOGIN));
            case Mensaje.COD_USR_SIN_NUM_MOVIL:
                throw new UserMessageException(new Mensaje("NÚMERO MÓVIL NO REGISTRADO", "Su cuenta de usuario debe tener un número móvil registrado para recibir el PIN de acceso", TipoMensaje.warn, Mensaje.COD_USR_SIN_NUM_MOVIL));
            default:
                Usuario usr = new Usuario(
                        (Integer) resp[0],
                        resp[1].toString(),
                        resp[2].toString(),
                        resp[4] == null ? null : resp[4].toString(),
                        resp[5] == null ? null : resp[5].toString(),
                        resp[6] == null ? null : Util.SIMPLE_DATE_FORMAT_ISO.parse(resp[6].toString())
                );
                usr.codigo = codigo;
                if (usr.codigo == Mensaje.COD_LOGIN_OK_SIN_MFA) {
                    String pin = resp[9].toString();
                    Mensaje msgError = this.smsFacade.enviarSms(resp[10].toString(), "SIGESS - PIN de ingreso: " + pin);
                    if (msgError != null) {
                        throw new UserMessageException(msgError);
                    }
                }
                return usr;
        }
    }

    public Usuario create(Usuario usuario, Integer empresaId) throws Exception {
        if (usuario.getUsuarioEmpresaList() == null || usuario.getUsuarioEmpresaList().isEmpty()) {
            throw new IllegalArgumentException("Debe establecer un perfil para el usuario a crear");
        }
        Usuario usrDB = this.findByEmail(usuario.getEmail());
        if (usrDB != null) {
            throw new UserMessageException(
                    "USUARIO YA REGISTRADO",
                    "El usuario que intenta registrar con el correo electrónico "
                    + usuario.getEmail()
                    + " ya se encuentra registrado",
                    TipoMensaje.warn
            );
        }
        String passwd = UtilSecurity.generatePassword();
        usuario.setPassword(UtilSecurity.createEmailPasswordHash(usuario.getEmail(), UtilSecurity.toSHA256(passwd)));
        usuario.setEstado(EstadoUsuario.CAMBIO_PASSWD);
        usuario.setFechaCreacion(new Date());
        usuario = super.create(usuario);

//      Crea la relacion usuario-empresa y asigna perfil por defecto
        for (UsuarioEmpresa ue : usuario.getUsuarioEmpresaList()) {
            ue.setUsuarioEmpresaPK(new UsuarioEmpresaPK(usuario.getId(), empresaId, ue.getPerfil().getId()));
            usuarioEmpresaFacade.create(ue);
        }
        Map<String, String> parametros = new HashMap<>();
        parametros.put("P{passwd}", passwd);
        parametros.put("P{email}", usuario.getEmail());
        emailFacade.sendEmail(parametros, TipoMail.CREACION_USUARIO, "Creación de cuenta", usuario.getEmail());
        return usuario;
    }

    public Usuario edit(Usuario usuario, Integer empresaId) throws Exception {
        if (usuario.getEstado() != null) {
            switch (usuario.getEstado()) {
                case BLOQUEADO:
                case ELIMINADO:
                case CAMBIO_PASSWD:
                    throw new UserMessageException("CAMBIO DE ESTADO NO VALIDO", "No puede cambiar a estados diferentes a ACTIVO e INACTIVO", TipoMensaje.warn);
            }
        }
        Usuario usuarioDB = this.find(usuario.getId());
        switch (usuarioDB.getEstado()) {
            case BLOQUEADO:
            case ELIMINADO:
                throw new UserMessageException("MODIFICACION NO PERMITIDA", "No puede modificar usuarios en estado BLOQUEADO o ELIMINADO", TipoMensaje.warn);
        }
        if (usuario.getEstado() != null) {
            usuarioDB.setEstado(usuario.getEstado());
        }
        usuarioDB.setFechaModificacion(new Date());
        usuarioDB.setIpPermitida(usuario.getIpPermitida());
        usuarioDB.setMfa(usuario.getMfa());
        usuarioDB.setNumeroMovil(usuario.getNumeroMovil());
        usuarioDB = super.edit(usuarioDB);

        // Actualiza la relacion usuario-empresa
        for (UsuarioEmpresa ue : usuarioDB.getUsuarioEmpresaList()) {
            if (ue.getEmpresa().getId().equals(empresaId)) {
                usuarioEmpresaFacade.remove(ue);
            }
        }
        this.em.flush();
        for (UsuarioEmpresa ue : usuario.getUsuarioEmpresaList()) {
            ue.setUsuarioEmpresaPK(new UsuarioEmpresaPK(usuarioDB.getId(), empresaId, ue.getPerfil().getId()));
            usuarioEmpresaFacade.create(ue);
        }
        return usuarioDB;
    }

    public void eliminar(Integer entity) throws Exception {
        Usuario usuarioDB = this.find(entity);
        if (usuarioDB.getEstado().equals(EstadoUsuario.BLOQUEADO)) {
            throw new UserMessageException("CAMBIO DE ESTADO NO VALIDO", "El usuario se encuentra BLOQUEADO", TipoMensaje.warn);
        }
        if (usuarioDB.getEstado().equals(EstadoUsuario.ELIMINADO)) {
            throw new UserMessageException("USUARIO ELIMINADO", "El usuario ya se encuentra ELIMINADO", TipoMensaje.info);
        }
        usuarioDB.setEstado(EstadoUsuario.ELIMINADO);
        usuarioDB.setFechaModificacion(new Date());
        super.edit(usuarioDB); //To change body of generated methods, choose Tools | Templates.
    }

    public List<Usuario> findByEmpresa(Integer empresaIdRequestContext) {
        String consulta = "SELECT DISTINCT u FROM Usuario u JOIN u.usuarioEmpresaList ue WHERE ue.empresa.id = ?1";
        Query query = this.em.createQuery(consulta);
        query.setParameter(1, empresaIdRequestContext);
        List<Usuario> list = (List<Usuario>) query.getResultList();
        return list;
    }

    public void filtrarUsuarioEmpresa(Usuario usuario, Integer empresaId) {
        for (Iterator<UsuarioEmpresa> iterator = usuario.getUsuarioEmpresaList().iterator(); iterator.hasNext();) {
            UsuarioEmpresa ue = iterator.next();
            if (!ue.getEmpresa().getId().equals(empresaId)) {
                iterator.remove();
            }
        }
    }

    public Usuario update(Usuario usuario, Integer empresaIdRequestContext) throws Exception {
        Usuario usuarioDB = this.find(usuario.getId());
        usuarioDB.setAvatar(usuario.getAvatar());
        usuarioDB.setIcon(usuario.getIcon());
        usuarioDB = super.edit(usuarioDB);
        return usuario;
    }

    public Usuario recuperarPasswd(String email) throws Exception {
        Usuario user = this.findByEmail(email);
        if (user != null) {
            Query q = this.em.createNativeQuery("SELECT ?1::inet <<= ANY(ip_permitida) AS ip_valida FROM emp.usuario WHERE id = ?2");
            q.setParameter(1, httpRequest.getRemoteAddr());
            q.setParameter(2, user.getId());
            boolean ipValida = (boolean) q.getSingleResult();
            if (!ipValida) {
                throw new UserMessageException(
                        new Mensaje(
                                "ACCESO NO PERMITIDO",
                                "Su dirección IP no se encuentra autorizada para realizar peticiones. "
                                + "Por favor pongase en contacto con el administrador.",
                                TipoMensaje.warn,
                                Mensaje.COD_IP_NO_PERMITIDA
                        )
                );
            }
            switch (user.getEstado()) {
                case BLOQUEADO:
                case ELIMINADO:
                case INACTIVO:
                    throw new UserMessageException("SOLICITUD NO PERMITIDA", "El estado del usuario no permite la operación", TipoMensaje.warn);
            }
            String nuevoPasswd = UtilSecurity.generatePassword();
            String shaPassw = UtilSecurity.createEmailPasswordHash(email, UtilSecurity.toSHA256(nuevoPasswd));
            Calendar expPassed = Calendar.getInstance();
            expPassed.add(Calendar.SECOND, UtilSecurity.CAMBIO_PASSWD_TIMEOUT);

            user.setEstado(EstadoUsuario.CAMBIO_PASSWD);
            user.setPassword(shaPassw);
            user.setExpiraPassword(expPassed.getTime());
            this.edit(user);

            Map<String, String> parametros = new HashMap<>();
            parametros.put(EmailFacade.PARAM_COD_RECUP, nuevoPasswd);
            emailFacade.sendEmail(parametros, TipoMail.RECUPERACION_PASSWD, "Recuperación cuenta", email);
        }
        return user;
    }

    public Usuario cambiarPasswd(Integer idUsuario, String newPasswd, String newPasswdConfirm, String oldPasswd) throws Exception {
        Usuario user = super.find(idUsuario);
        if (user == null) {
            throw new IllegalArgumentException("");
        }
        if (!newPasswd.equals(newPasswdConfirm)) {
            throw new UserMessageException("Las contraseñas no coinciden", "La contraseña nueva debe coincidir con su confirmación", TipoMensaje.warn);
        }
        String hashPasswd = UtilSecurity.createEmailPasswordHash(user.getEmail(), UtilSecurity.toSHA256(oldPasswd));
        if (!hashPasswd.equals(user.getPassword())) {
            throw new UserMessageException("Las contraseña anterior no es válida", "La contraseña anterior no coincide con la especificada", TipoMensaje.warn);
        }
        Mensaje passMsg = UtilSecurity.validarPassword(newPasswd);
        if (passMsg != null) {
            throw new UserMessageException(passMsg);
        }
        user.setEstado(EstadoUsuario.ACTIVO);
        user.setPassword(UtilSecurity.createEmailPasswordHash(user.getEmail(), UtilSecurity.toSHA256(newPasswd)));
        user.setUltimoLogin(new Date());

        Calendar expPassed = Calendar.getInstance();
        expPassed.add(Calendar.MONTH, 2);
        user.setExpiraPassword(expPassed.getTime());
        this.edit(user);

        Map<String, String> parametros = new HashMap<>();
        parametros.put("P{email_usuario}", user.getEmail());
        parametros.put("P{direccion_ip}", httpRequest.getRemoteAddr());
        parametros.put("P{fecha_hora}", Util.SIMPLE_DATE_FORMAT_ISO.format(new Date()));
        parametros.put("P{cliente}", httpRequest.getHeader("user-agent"));
        this.emailFacade.sendEmail(parametros, TipoMail.CAMBIO_PASSWD, "Cambio de contraseña", user.getEmail());

        return user;
    }

    public void invalidarToken(Usuario user, String token) {
        Query query = this.em.createNativeQuery("INSERT INTO emp.token_blacklist (fk_usuario_id, fecha_generacion, token) VALUES (?1, ?2, ?3)");
        query.setParameter(1, user.getId());
        query.setParameter(2, user.getUltimoLogin(), TemporalType.TIMESTAMP);
        query.setParameter(3, token);
        query.executeUpdate();
    }

    public void bloquearUsuario(Usuario user) throws Exception {
        Usuario userDB = this.find(user.getId());
        userDB.setEstado(EstadoUsuario.BLOQUEADO);
        this.edit(userDB);

        tokenFacade.eliminarTokensUsuario(user);

        Mensaje msg = new Mensaje("USUARIO BLOQUEADO", "El usuario " + user.getId() + " ha sido bloqueado", TipoMensaje.error, Mensaje.COD_USUARIO_BLOQUEADO);
        Logger.getLogger(AuthorizationFacade.class.getName()).log(Level.SEVERE, msg.toString());
    }

    public void aceptarTerminos(Usuario usuario, Boolean aceptar) {
        if (aceptar.compareTo(Boolean.TRUE) == 0) {
            Query q = this.em.createQuery("UPDATE Usuario u SET u.fechaAceptaTerminos = ?2 WHERE u.id = ?1");
            q.setParameter(1, usuario.getId());
            q.setParameter(2, new Date());
            q.executeUpdate();
        } else {
            Query q = this.em.createQuery("UPDATE Usuario u SET u.estado = ?2 WHERE u.id = ?1");
            q.setParameter(1, usuario.getId());
            q.setParameter(2, EstadoUsuario.INACTIVO);
            q.executeUpdate();
        }
    }

}
