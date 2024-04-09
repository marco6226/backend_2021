/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.sigess.entities.com;

import java.util.Map;

/**
 *
 * @author fmoreno
 */
public class Mensaje {
        
    public static final int COD_MSG_SISTEMA = 1_000;
    public static final int COD_TOKEN_EXPIRADO = 1_001;
    public static final int COD_TOKEN_REFRESH_EXPIRADO = 1_002;
    public static final int COD_USUARIO_BLOQUEADO = 1_003;    
    public static final int COD_TOKEN_NO_VALIDO = 1_004;
    public static final int COD_PERMISOS_INSUFICIENTES = 1_005;    
    public static final int COD_TOKEN_ALTERADO = 1_006;
    public static final int COD_TOKEN_FIRMA_INVALIDA = 1_007;
    public static final int COD_USUARIO_NO_VALIDO = 1_008;
    
    public static final int COD_MSG_USUARIO = 2_000;
    public static final int COD_PASSWD_EXPIRADO = 2_001;
    public static final int COD_USUARIO_LOGIN_PREVIO = 2_002;
    public static final int COD_VALIDACION_AREA = 2_003;
    public static final int COD_VERSION_APP_NO_COMPATIBLE = 2_004;
    public static final int COD_VERSION_APP_SUG_UPDATE = 2_005;
    public static final int COD_IP_NO_PERMITIDA= 2_006;
    public static final int COD_LOGIN_OK_SIN_MFA = 2_007;
    public static final int COD_PIN_INCORRECTO = 2_008;
    public static final int COD_MAX_INTENTOS_LOGIN = 2_009;
    public static final int COD_USR_SIN_NUM_MOVIL = 2_010;
    public static final int COD_MOVIL_NO_VALIDO = 2_011;
    public static final int COD_ERROR_ENVIO_SMS = 2_012;
    

    private String mensaje;
    private String detalle;
    private TipoMensaje tipoMensaje;
    private Integer codigo;
    
    private Map<String, String> metaData;

    public Mensaje() {
    }

    public Mensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public Mensaje(String mensaje, String detalle) {
        this.mensaje = mensaje;
        this.detalle = detalle;
    }

    public Mensaje(String mensaje, String detalle, TipoMensaje tipoMensaje) {
        this.mensaje = mensaje;
        this.detalle = detalle;
        this.tipoMensaje = tipoMensaje;
    }

    public Mensaje(String mensaje, String detalle, TipoMensaje tipoMensaje, Integer codigo) {
        this.mensaje = mensaje;
        this.detalle = detalle;
        this.tipoMensaje = tipoMensaje;
        this.codigo = codigo;
    }

    public String getMensaje() {
        return mensaje;
    }
    
    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public String getDetalle() {
        return detalle;
    }

    public void setDetalle(String detalle) {
        this.detalle = detalle;
    }

    public TipoMensaje getTipoMensaje() {
        return tipoMensaje;
    }

    public void setTipoMensaje(TipoMensaje tipoMensaje) {
        this.tipoMensaje = tipoMensaje;
    }

    public Integer getCodigo() {
        return codigo;
    }

    public void setCodigo(Integer codigo) {
        this.codigo = codigo;
    }

    public Map<String, String> getMetaData() {
        return metaData;
    }

    public void setMetaData(Map<String, String> metaData) {
        this.metaData = metaData;
    }

    @Override
    public String toString() {
        return "#SIGESS_ MENSAJE{ codigo=" + codigo + ", mensaje=" + mensaje + ", detalle=" + detalle + ", tipoMensaje=" + tipoMensaje + "} _SIGESS#";
    }

    
}
