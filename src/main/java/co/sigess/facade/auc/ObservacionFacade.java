/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.sigess.facade.auc;

import co.sigess.entities.ado.Documento;
import co.sigess.entities.auc.Observacion;
import co.sigess.entities.auc.Tarjeta;
import co.sigess.entities.com.TipoMensaje;
import co.sigess.entities.emp.Empleado;
import co.sigess.entities.emp.Usuario;
import co.sigess.exceptions.UserMessageException;
import co.sigess.facade.com.AbstractFacade;
import co.sigess.facade.core.EmailFacade;
import co.sigess.facade.core.TipoMail;
import co.sigess.facade.emp.EmpleadoFacade;
import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import org.apache.commons.lang3.StringEscapeUtils;

/**
 *
 * @author fmoreno
 */
@Stateless
public class ObservacionFacade extends AbstractFacade<Observacion> {

    @PersistenceContext(unitName = "SIGESS_PU")
    private EntityManager em;

    @EJB
    private TarjetaFacade tarjetaFacade;

    @EJB
    private EmpleadoFacade empleadoFacade;

    @EJB
    private EmailFacade emailFacade;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public ObservacionFacade() {
        super(Observacion.class);
    }

    public Observacion create(Observacion observacion, int empresaId) throws Exception {
        if (observacion.getTipoObservacion() == null) {
            throw new IllegalArgumentException("Debe especificar el tipo de observación");
        }
        if (observacion.getDescripcion() == null) {
            throw new IllegalArgumentException("Debe especificar la descripción de la observación");
        }
        if (observacion.getImplicacionList() == null) {
            throw new IllegalArgumentException("Debe especificar al menos una implicación");
        }
        if (observacion.getArea() == null) {
            throw new IllegalArgumentException("Debe especificar el área de la obsevación");
        }
        if (observacion.getTarjeta() == null) {
            throw new IllegalArgumentException("Debe establecer una tarjeta para la observación a reportar");
        }

        Tarjeta tarjeta = tarjetaFacade.find(observacion.getTarjeta().getId());
        if (tarjeta == null || !tarjeta.getEmpresa().getId().equals(empresaId)) {
            throw new IllegalArgumentException("Se ha establecido una tarjeta no válida para la obsevación");
        }

        observacion.setFechaObservacion(new Date());
        return super.create(observacion);
    }

    public Observacion aceptarObservacion(Observacion observacion) throws Exception {
        if (observacion.getId() == null) {
            throw new IllegalArgumentException("No ha especificado el id de la observación a modificar");
        }
        if (observacion.getUsuarioRevisa() == null) {
            throw new IllegalArgumentException("Debe establecer el usuario que acepta la observación");
        }
        if (observacion.getMotivo() == null || observacion.getMotivo().isEmpty()) {
            throw new IllegalArgumentException("Debe establecer el motivo de denegación de la observación");
        }
        Observacion observDB = this.find(observacion.getId());
        if (observDB == null) {
            throw new IllegalArgumentException("Ha especificado una observación inválida");
        }
        observDB.setUsuarioRevisa(observacion.getUsuarioRevisa());
        observDB.setFechaRespuesta(new Date());
        observDB.setAceptada(Boolean.TRUE);
        observDB.setMotivo(observacion.getMotivo());
        //
        observDB.setPersonasabordadas(observacion.getPersonasabordadas());
        observDB.setPersonasobservadas(observacion.getPersonasobservadas());

        return super.edit(observDB); // To change body of generated methods, choose Tools | Templates.
    }

    public Observacion denegarObservacion(Observacion observacion) throws Exception {
        if (observacion.getId() == null) {
            throw new IllegalArgumentException("No ha especificado el id de la observación a modificar");
        }
        if (observacion.getUsuarioRevisa() == null) {
            throw new IllegalArgumentException("Debe establecer el usuario que deniega la observación");
        }
        if (observacion.getMotivo() == null || observacion.getMotivo().isEmpty()) {
            throw new IllegalArgumentException("Debe establecer el motivo de denegación de la observación");
        }
        Observacion observDB = this.find(observacion.getId());
        if (observDB == null) {
            throw new IllegalArgumentException("Ha especificado una observación inválida");
        }
        observDB.setUsuarioRevisa(observacion.getUsuarioRevisa());
        observDB.setFechaRespuesta(new Date());
        observDB.setAceptada(Boolean.FALSE);
        observDB.setMotivo(observacion.getMotivo());
        return super.edit(observDB); // To change body of generated methods, choose Tools | Templates.
    }

    public Observacion enviarCorreo(String email, Integer idResponsable, String nombre, Long id, Date fechaproyectada) throws Exception {

        Observacion observacion = this.find(id);
        if (observacion != null) {
            // switch (user.getEstado()) {
            // case BLOQUEADO:
            // case ELIMINADO:
            // case INACTIVO:
            // throw new UserMessageException("SOLICITUD NO PERMITIDA", "El estado del usuario no permite la operación", TipoMensaje.warn);
            // }

             List<Empleado> empleado = findEmpleadoById(idResponsable);
             String responsable;
              System.out.println(empleado);
             if (empleado.size() > 0 ){
             responsable  = empleado.get(0).getPrimerNombre()+" " + empleado.get(0).getPrimerApellido();  
            }
             else 
             {
                  responsable = email;
             }
            String fechaproyectadas = fechaproyectada.toString();

        //    System.out.println(responsables);

            DateTimeFormatter f = DateTimeFormatter.ofPattern("E MMM dd HH:mm:ss z yyyy", Locale.ENGLISH);

            ZonedDateTime zdt = ZonedDateTime.parse(fechaproyectadas, f);

            LocalDate ld = zdt.toLocalDate();
            DateTimeFormatter fLocalDate = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            String output = StringEscapeUtils.escapeHtml4(ld.format(fLocalDate));
            String motivo = StringEscapeUtils.escapeHtml4(observacion.getMotivo());

            Map<String, String> parametros = new HashMap<>();
            parametros.put(EmailFacade.PARAM_MENSAJE, "OBSERVACION DENEGADA");
            parametros.put(EmailFacade.PARAM_ACTIVIDAD, nombre);
            parametros.put(EmailFacade.PARAM_RESPONSABLE, responsable);
            parametros.put(EmailFacade.PARAM_MOTIVO, motivo);
            if (id != null) {
                parametros.put(EmailFacade.PARAM_ID, id.toString());
            }
            parametros.put(EmailFacade.PARAM_FECHA_PROY, output);
            emailFacade.sendEmail(parametros, TipoMail.OBSERVACION_DENEGADA, "Observacion", email);
        }
        return observacion;
    }


    
    public List<Empleado> findEmpleadoById(Integer idUser) {

        Query q = this.em.createNativeQuery("SELECT e.* FROM emp.empleado e WHERE fk_usuario_id = ?1",Empleado.class);   
        q.setParameter(1, idUser);
        List<Empleado> list = (List<Empleado>) q.getResultList();   
        return list;     
    }

    @Override
    public List<Observacion> findAllByEmpresa(Integer empresaId) {
        Query q = this.em.createQuery("SELECT ob FROM Observacion ob WHERE ob.tarjeta.empresa.id = ?1 ORDER BY ob.fechaObservacion DESC");
        q.setParameter(1, empresaId);
        List<Observacion> list = (List<Observacion>) q.getResultList();
        return list;
    }

    public List<Observacion> findAllByArea(Long areaId) {
        Query q = this.em.createQuery("SELECT ob FROM Observacion ob WHERE ob.area.id = ?1 ORDER BY ob.fechaObservacion DESC");
        q.setParameter(1, areaId);
        List<Observacion> list = (List<Observacion>) q.getResultList();
        return list;
    }

    public void relacionarDocumento(Documento documento, Long observacionId) throws Exception {
        Observacion ad = this.find(observacionId);
        List<Documento> list = ad.getDocumentoList();
        if (list == null) {
            list = new ArrayList<>();
        }
        list.add(documento);
        super.edit(ad);
    }

    @Override
    public Observacion edit(Observacion observacion)throws Exception{
        Observacion obsDB = this.find(observacion.getId());

        if (observacion.getId() == null) {
            throw new IllegalArgumentException("La observacion no tiene ID");
        }
        
        //if(observacion!=null && observacion.getId()!=null){
            
            obsDB.setAfecta(observacion.getAfecta());
            obsDB.setCausaRaizList(observacion.getCausaRaizList());
            obsDB.setDescripcion(observacion.getDescripcion());
            obsDB.setImplicacionList(observacion.getImplicacionList());
            obsDB.setNivelRiesgo(observacion.getNivelRiesgo());
            obsDB.setPersonasabordadas(observacion.getPersonasabordadas());
            obsDB.setPersonasobservadas(observacion.getPersonasobservadas());
            obsDB.setRecomendacion(observacion.getRecomendacion());
            obsDB.setTipoObservacion(observacion.getTipoObservacion());
            obsDB.setArea(observacion.getArea());
            obsDB.setAfecta(observacion.getAfecta());
            return super.edit(obsDB);
        //}
        //else{
        //    return obsDB;
        //}

        
    }
}
