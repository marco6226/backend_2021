/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.sigess.facade.inp;

import co.sigess.entities.com.Mensaje;
import co.sigess.entities.com.TipoMensaje;
import co.sigess.entities.conf.RespuestaCampo;
import co.sigess.entities.inp.Bitacora;
import co.sigess.entities.inp.Calificacion;
import co.sigess.entities.inp.Inspeccion;
import co.sigess.entities.inp.ListaInspeccion;
import co.sigess.entities.inp.ElementoInspeccion;
import co.sigess.entities.inp.NumeroEconomico;
import co.sigess.entities.inp.Programacion;
import co.sigess.exceptions.UserMessageException;
import co.sigess.facade.com.AbstractFacade;
import co.sigess.facade.conf.RespuestaCampoFacade;
import co.sigess.facade.core.EmailFacade;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.HashMap;
import co.sigess.facade.core.TipoMail;
import java.util.Map;
import java.time.format.DateTimeFormatter;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.Locale;
import co.sigess.exceptions.UserMessageException;
import java.util.ArrayList;
import javax.persistence.TemporalType;

/**
 *
 * @author fmoreno
 */
@Stateless
public class InspeccionFacade extends AbstractFacade<Inspeccion> {

    @EJB
    private ProgramacionFacade programacionFacade;

    @EJB
    private CalificacionFacade calificacionFacade;

    @EJB
    private RespuestaCampoFacade respuestaCampoFacade;
    
    @EJB
    private EmailFacade emailFacade;

    @PersistenceContext(unitName = "SIGESS_PU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public InspeccionFacade() {
        super(Inspeccion.class);
    }

    @Override
    public Inspeccion create(Inspeccion inspeccion) throws Exception {
        if (inspeccion.getCalificacionList() == null || inspeccion.getCalificacionList().isEmpty()) {
            throw new IllegalArgumentException("La inspección especificada no contiene la lista de calificaciones");
        }
        if (inspeccion.getProgramacion() == null) {
            return this.crearInspeccionNoProgramada(inspeccion);
        } else {
            return this.crearInspeccionProgramada(inspeccion);
        }

    }
    
    public Inspeccion createInspeccionAliado(Inspeccion inspeccion) throws Exception {
        if(inspeccion.getCalificacionList() == null || inspeccion.getCalificacionList().isEmpty()) {
            throw new IllegalArgumentException("La inspección especificada no contiene la lista de calificaciones");
        }
        if(inspeccion.getProgramacion() == null) {
            throw new IllegalArgumentException("No está habilitada la opción de crear inspecciones no programadas");
        } else {
            return crearInspeccionProgramadaAliado(inspeccion);
        }
    }

    private Inspeccion crearInspeccionNoProgramada(Inspeccion inspeccion) throws Exception {
        if (inspeccion.getListaInspeccion() == null
                || inspeccion.getListaInspeccion().getListaInspeccionPK() == null
                || inspeccion.getListaInspeccion().getListaInspeccionPK().getId() == 0
                || inspeccion.getListaInspeccion().getListaInspeccionPK().getVersion() == 0) {
            throw new IllegalArgumentException("No se ha establecido la lista de inspección");
        }
        if (inspeccion.getArea() == null || inspeccion.getArea().getId() == null) {
            throw new IllegalArgumentException("No se ha establecido el área de la inspección");
        }

        if (!this.validarCalificaciones(inspeccion)) {
            throw new IllegalArgumentException("Error en la calificación recibida: no contiene un elemento de inspección o una opción seleccionada");
        }
        for (RespuestaCampo rc : inspeccion.getRespuestasCampoList()) {
            respuestaCampoFacade.create(rc);
        }
        inspeccion.setFechaRealizada(new Date());
        super.create(inspeccion);
        for (Calificacion calificacion : inspeccion.getCalificacionList()) {
            calificacion.setInspeccion(inspeccion);
            calificacionFacade.create(calificacion);
        }
        return inspeccion;
    }

    private Inspeccion crearInspeccionProgramada(Inspeccion inspeccion) throws Exception {
        Programacion progDB = programacionFacade.find(inspeccion.getProgramacion().getId());
        if (progDB == null) {
            throw new IllegalArgumentException("No se ha establecido la programación a la que pertenece la inspección");
        }
        if (progDB.getNumeroInspecciones() == progDB.getNumeroRealizadas()) {
            Mensaje msg = new Mensaje(
                    "INSPECCIONES COMPLETADAS",
                    "Ya se han realizado todas las inspecciones de la programación [" + progDB.getId() + "]",
                    TipoMensaje.info
            );
            throw new UserMessageException(msg);
        }
        ListaInspeccion listaInp = progDB.getListaInspeccion();
        
        if(listaInp.getListaInspeccionPK() == null){
            Mensaje msg = new Mensaje(
                    "ERROR",
                    "La inspección no tiene una lista asignada.",
                    TipoMensaje.error
            );
            throw new UserMessageException(msg);
        }

        if (inspeccion.getCalificacionList().size() != listaInp.getNumeroPreguntas()) {
            throw new IllegalArgumentException("El número de preguntas no coincide con el número de respuestas");
        }

        if (!this.validarCalificaciones(inspeccion)) {
            throw new IllegalArgumentException("Error en la calificación recibida: no contiene un elemento de inspección o una opción seleccionada");
        }

        for (RespuestaCampo rc : inspeccion.getRespuestasCampoList()) {
            respuestaCampoFacade.create(rc);
        }

        progDB.setNumeroRealizadas((short) (progDB.getNumeroRealizadas() + 1));
        programacionFacade.edit(progDB);

        inspeccion.setFechaRealizada(new Date());
        super.create(inspeccion); //To change body of generated methods, choose Tools | Templates.

        for (Calificacion calificacion : inspeccion.getCalificacionList()) {
            calificacion.setInspeccion(inspeccion);
            calificacionFacade.create(calificacion);
        }
        return inspeccion;
    }
    
    private Inspeccion crearInspeccionProgramadaAliado(Inspeccion inspeccion) throws Exception {
        
        Programacion progDB = programacionFacade.find(inspeccion.getProgramacion().getId());
        if(progDB == null){
            throw new IllegalArgumentException("No se ha establecido la programación a la que pertenece la inspección");
        }
        
        if(progDB.getNumeroInspecciones() == progDB.getNumeroRealizadas()) {
            Mensaje msg = new Mensaje("Error", "La inspección no tiene una lista asignada", TipoMensaje.error);
            throw new UserMessageException(msg);
        }
        
        if(!this.validarCalificaciones(inspeccion)) {
            throw new IllegalArgumentException("Error en la calificación recibida: no contiene un elemento de inspección o una opción seleccionada");
        }
        
        for(RespuestaCampo rc: inspeccion.getRespuestasCampoList()) {
            respuestaCampoFacade.create(rc);
        }
        
        progDB.setNumeroRealizadas((short) (progDB.getNumeroRealizadas() + 1));
        programacionFacade.edit(progDB);
        
        inspeccion.setFechaRealizada(new Date());
        super.create(inspeccion);
        
        for(Calificacion calificacion : inspeccion.getCalificacionList()){
            calificacion.setInspeccion(inspeccion);
            calificacionFacade.create(calificacion);
        }
        
        return inspeccion;
    }

    private boolean validarCalificaciones(Inspeccion inspeccion) {
        for (Calificacion calificacion : inspeccion.getCalificacionList()) {
            if (calificacion.getElementoInspeccion() == null || calificacion.getOpcionCalificacion() == null) {
                return false;
            }
            if (calificacion.getNivelRiesgo() != null && calificacion.getNivelRiesgo().getId() == null) {
                calificacion.setNivelRiesgo(null);
            }
            if (calificacion.getTipoHallazgo() == null || calificacion.getTipoHallazgo().getId() == null) {
                calificacion.setTipoHallazgo(null);
            }
        }
        return true;
    }

    @Override
    public Inspeccion edit(Inspeccion inspeccion) throws Exception {
        if (inspeccion.getId() == null) {
            throw new IllegalArgumentException("No se ha establecido el id de la inspección a modificar");
        }

        for (Calificacion calificacion : inspeccion.getCalificacionList()) {
            if (calificacion.getId() == null) {
                if(calificacion.getOpcionCalificacion() != null){
                    calificacion.setInspeccion(inspeccion);
                    calificacion = calificacionFacade.create(calificacion);
                }else{
                    throw new IllegalArgumentException("No se ha establecido el id de una de las calificaciones de la inspección");
                }
            }
            if (calificacion.getNivelRiesgo() != null && calificacion.getNivelRiesgo().getId() == null) {
                calificacion.setNivelRiesgo(null);
            }
        }
        Inspeccion inspDB = this.find(inspeccion.getId());
        inspDB.setConceptohse(inspeccion.getConceptohse());
        inspDB.setConceptoing(inspeccion.getConceptoing());
        inspDB.setFechavistohse(inspeccion.getFechavistohse());
        inspDB.setFechavistoing(inspeccion.getFechavistoing());
        inspDB.setEmpleadohse(inspeccion.getEmpleadohse());
        inspDB.setEmpleadoing(inspeccion.getEmpleadoing());
        inspDB.setUsuarioModifica(inspeccion.getUsuarioModifica());
        inspDB.setFechaModificacion(new Date());
        inspDB.setDescripcion(inspeccion.getDescripcion());
        inspDB.setEquipo(inspeccion.getEquipo());
        inspDB.setLugar(inspeccion.getLugar());
        inspDB.setMarca(inspeccion.getMarca());
        inspDB.setModelo(inspeccion.getModelo());
        inspDB.setObservacion(inspeccion.getObservacion());
        inspDB.setSerial(inspeccion.getSerial());
        inspDB.setRespuestasCampoList(inspeccion.getRespuestasCampoList());
        for (RespuestaCampo rc : inspDB.getRespuestasCampoList()) {
            if (rc.getId() == null) {
                respuestaCampoFacade.create(rc);
            } else {
                respuestaCampoFacade.edit(rc);
            }
        }

        for (Calificacion calificacion : inspDB.getCalificacionList()) {
            for (Calificacion calfMod : inspeccion.getCalificacionList()) {
                if (calfMod.getId().equals(calificacion.getId())) {
                    calificacion.setNivelRiesgo(calfMod.getNivelRiesgo());
                    calificacion.setOpcionCalificacion(calfMod.getOpcionCalificacion());
                    calificacion.setRecomendacion(calfMod.getRecomendacion());
                    calificacion.setTipoHallazgo(calfMod.getTipoHallazgo() == null ? null : (calfMod.getTipoHallazgo().getId() == null ? null : calfMod.getTipoHallazgo()));
                    calificacion.setCalcularCumplimiento(calfMod.isCalcularCumplimiento());
                    break;
                }
            }
            this.calificacionFacade.edit(calificacion);
        }
 // List<String> lines =  inspDB.getCalificacionList());
        return super.edit(inspDB); //To change body of generated methods, choose Tools | Templates.
    }
    
    public Inspeccion editInspeccionAliado(Inspeccion inspeccion) throws Exception {
        if (inspeccion.getId() == null) {
            throw new IllegalArgumentException("No se ha establecido el id de la inspección a modificar");
        }
        
        if(!this.validarCalificaciones(inspeccion)) {
            throw new IllegalArgumentException("Error en la calificación recibida: no contiene un elemento de inspección o una opción seleccionada");
        }
        
        for(Calificacion calificacion : inspeccion.getCalificacionList()){
            if(calificacion.getOpcionCalificacion() == null) throw new IllegalArgumentException("No se ha establecido el id de una de las calificaciones de la inspección");
            if(calificacion.getElementoInspeccion() == null) throw new IllegalArgumentException("No se ha establecido el id del elemento de una de las calificaciones de la inspección");
            if(calificacion.getId() == null){
                calificacion.setInspeccion(inspeccion); 
                calificacion = this.calificacionFacade.create(calificacion);
            }else {
                Calificacion calificacionDB = this.calificacionFacade.find(calificacion.getId());
                if(calificacionDB == null) continue;
                calificacionDB.setNivelRiesgo(calificacion.getNivelRiesgo());
                calificacionDB.setOpcionCalificacion(calificacion.getOpcionCalificacion());
                calificacionDB.setRecomendacion(calificacion.getRecomendacion());
                calificacionDB.setTipoHallazgo(calificacion.getTipoHallazgo());
                calificacionDB.setCalcularCumplimiento(calificacion.isCalcularCumplimiento());
                this.calificacionFacade.edit(calificacionDB);
            }
        }
        
        Inspeccion inspDB = this.find(inspeccion.getId());
        inspDB.setConceptohse(inspeccion.getConceptohse());
        inspDB.setConceptoing(inspeccion.getConceptoing());
        inspDB.setFechavistohse(inspeccion.getFechavistohse());
        inspDB.setFechavistoing(inspeccion.getFechavistoing());
        inspDB.setEmpleadohse(inspeccion.getEmpleadohse());
        inspDB.setEmpleadoing(inspeccion.getEmpleadoing());
        inspDB.setUsuarioModifica(inspeccion.getUsuarioModifica());
        inspDB.setFechaModificacion(new Date());
        inspDB.setDescripcion(inspeccion.getDescripcion());
        inspDB.setEquipo(inspeccion.getEquipo());
        inspDB.setLugar(inspeccion.getLugar());
        inspDB.setMarca(inspeccion.getMarca());
        inspDB.setModelo(inspeccion.getModelo());
        inspDB.setObservacion(inspeccion.getObservacion());
        inspDB.setSerial(inspeccion.getSerial());
        inspDB.setRespuestasCampoList(inspeccion.getRespuestasCampoList());
        for (RespuestaCampo rc : inspDB.getRespuestasCampoList()) {
            if (rc.getId() == null) {
                this.respuestaCampoFacade.create(rc);
            } else {
                this.respuestaCampoFacade.edit(rc);
            }
        }
        
        return super.edit(inspDB);
    }

    @Override
    public List<Inspeccion> findAllByEmpresa(Integer empresaId) {
        Query query = this.em.createQuery("SELECT inp FROM Inspeccion inp WHERE inp.empresa.id = ?1");
        query.setParameter(1, empresaId);
        List<Inspeccion> list = (List<Inspeccion>) query.getResultList();
        return list;
    }
    public void enviarCorreoCriticos(Long id, List<ElementoInspeccion> elementosList, String numeroeconomico, String ubicacion) throws Exception {

       // Observacion observacion = this.find(id);
        if (elementosList != null) {
            // switch (user.getEstado()) {
            // case BLOQUEADO:
            // case ELIMINADO:
            // case INACTIVO:
            // throw new UserMessageException("SOLICITUD NO PERMITIDA", "El estado del usuario no permite la operación", TipoMensaje.warn);
            // }
            Inspeccion inspeccion =  this.find(id);


           //  List<Empleado> empleado = findEmpleadoById(idResponsable);
          //  String responsable  = empleado.get(0).getPrimerNombre()+" " + empleado.get(0).getPrimerApellido();
            String fecharealizada =  inspeccion.getFechaRealizada().toString();

        //    System.out.println(responsables);

          //Tue Dec 28 23:03:45 COT 2021
          
          DateTimeFormatter f = DateTimeFormatter.ofPattern("EE MMM dd HH:mm:ss z yyyy", Locale.ENGLISH);
         // DateTimeFormatter f = DateTimeFormatter.ofPattern("EEEE, MMM d, yyyy hh:mm:ss a", Locale.US);

          ZonedDateTime zdt = ZonedDateTime.parse(fecharealizada, f);

          LocalDateTime ld = zdt.toLocalDateTime();
          DateTimeFormatter fLocalDate = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm a");
           String output = ld.format(fLocalDate);
         
         String critico=elementosList.get(0).getCodigo()+ " " + elementosList.get(0).getNombre() + " - Criticidad:" + elementosList.get(0).getCriticidad();            
            for (int i = 1; i < elementosList.size(); i++) {
             critico+= " <br> "+ elementosList.get(i).getCodigo()+ " " + elementosList.get(i).getNombre() + " - Criticidad:" + elementosList.get(i).getCriticidad(); 
            }
         
         

            Map<String, String> parametros = new HashMap<>();
            parametros.put(EmailFacade.PARAM_MENSAJE, "HALLAZGO CRITICO");
            parametros.put(EmailFacade.PARAM_ECONOMICO, numeroeconomico);
            parametros.put(EmailFacade.PARAM_UBICACION, ubicacion);
            parametros.put(EmailFacade.PARAM_ID, "INP-" + inspeccion.id.toString());
            parametros.put(EmailFacade.PARAM_IDS, inspeccion.id.toString());           
            parametros.put(EmailFacade.PARAM_RESPONSABLE, inspeccion.getArea().getContacto());
            parametros.put(EmailFacade.PARAM_FECHA_REALIZADA, output);
            parametros.put(EmailFacade.PARAM_NOMBRE_INSPECCION, inspeccion.getListaInspeccion().getNombre());
            parametros.put(EmailFacade.PARAM_RIESGO_CRITICO, critico);
            //parametros.put(EmailFacade.PARAM_CRITICIDAD, elementosList.get(0).getCriticidad());
            parametros.put(EmailFacade.PARAM_AREA, inspeccion.getArea().getNombre() );
            String contacto = inspeccion.getArea().getContacto();
            System.out.println(contacto);
            
            
            if (contacto != null){
            emailFacade.sendEmail(parametros, TipoMail.RIESGOS_CRITICOS, "Hallazgos criticos", contacto);
            }
            else{
                
                 throw new UserMessageException("El area no tiene un contacto asociado ", "favor comunicarse con soporte técnico", TipoMensaje.warn);
            }
        }
        
        
        
    }

    public ByteArrayOutputStream consultarConsolidado(Integer empresaId, Date desde, Date hasta, Integer listaId, Integer listaVersion) throws IOException {
        Query q = this.em.createNativeQuery("SELECT data FROM  inp.generar_consolidado_inspecciones(?1, ?2, ?3, ?4, ?5)");
        q.setParameter(1, empresaId);
        q.setParameter(2, listaId);
        q.setParameter(3, listaVersion);
        q.setParameter(4, desde);
        q.setParameter(5, hasta);
        List<String> lines = q.getResultList();
        if (lines.isEmpty()) {
            throw new UserMessageException("Datos no encontrados", "No se hallaron inspecciones en el rango de fechas seleccionado", TipoMensaje.info);
        }

        ByteArrayOutputStream bOutput = new ByteArrayOutputStream(2_000 + (lines.size() * 400));

        for (String line : lines) {
            bOutput.write(line.getBytes());
            bOutput.write("\n".getBytes());
        }
        return bOutput;
    }

    public List<NumeroEconomico> getAllNumeroEconomico(){
       
        Query query = this.em.createQuery("SELECT inp FROM NumeroEconomico inp");
        //query.setParameter(1, empresaId);
        List<NumeroEconomico> list = (List<NumeroEconomico>) query.getResultList();
        return list;
    }

   
    public List<NumeroEconomico> getNumeroEconomicoByNumeroEconomico(String numeroEconomico){
       
        Query query = this.em.createQuery("SELECT inp FROM NumeroEconomico inp WHERE inp.numero_economico = ?1");
        query.setParameter(1, numeroEconomico);
        List<NumeroEconomico> list = (List<NumeroEconomico>) query.getResultList();
        return list;
    }

    public List<Bitacora> getBitacora(String numeroEconomicoId, String inspeccionId){
       
        Query query = this.em.createQuery("SELECT inp FROM Bitacora inp WHERE inp.pk_numero_economico_id = ?1 AND inp.pk_inspeccion_id = ?2");
        query.setParameter(1, Long.parseLong(numeroEconomicoId));
        query.setParameter(2, Long.parseLong(inspeccionId));
        List<Bitacora> list = (List<Bitacora>) query.getResultList();
        return list;
    }

    public int getCountBitacora(){
       
        Query query = this.em.createQuery("SELECT inp FROM Bitacora inp");
        List<Bitacora> list = (List<Bitacora>) query.getResultList();
        int count= list.size();
        return count;
    }

    public Bitacora crearBitacora(Bitacora bitacora) throws Exception {
        int idBitacora = getCountBitacora()+1;
        Query query = this.em.createNativeQuery("INSERT INTO inp.bitacora (id, fecha, observacion, pk_numero_economico_id, pk_inspeccion_id) VALUES (?1, ?2, ?3, ?4, ?5)");
        query.setParameter(1, idBitacora);
        query.setParameter(2, bitacora.getFecha());
        query.setParameter(3, bitacora.getObservacion());
        query.setParameter(4, bitacora.getPk_numero_economico_id());
        query.setParameter(5, bitacora.getPk_inspeccion_id());
        query.executeUpdate();
        
        return bitacora;
    }
}



 