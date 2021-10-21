/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.sigess.facade.auc;

import co.sigess.entities.ado.Documento;
import co.sigess.entities.auc.Observacion;
import co.sigess.entities.auc.Tarjeta;
import co.sigess.entities.emp.Empleado;
import co.sigess.facade.com.AbstractFacade;
import co.sigess.facade.emp.EmpleadoFacade;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

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
        if(observacion.getId() == null){
            throw new IllegalArgumentException("No ha especificado el id de la observación a modificar");
        }
        if(observacion.getUsuarioRevisa() == null){
            throw new IllegalArgumentException("Debe establecer el usuario que acepta la observación");
        }
        if(observacion.getMotivo()== null || observacion.getMotivo().isEmpty()){
            throw new IllegalArgumentException("Debe establecer el motivo de denegación de la observación");
        }
        Observacion observDB = this.find(observacion.getId());
        if(observDB == null){
            throw new IllegalArgumentException("Ha especificado una observación inválida");
        }
        observDB.setUsuarioRevisa(observacion.getUsuarioRevisa());
        observDB.setFechaRespuesta(new Date());
        observDB.setAceptada(Boolean.TRUE);
        observDB.setMotivo(observacion.getMotivo());
        
        return super.edit(observDB); //To change body of generated methods, choose Tools | Templates.
    }
    
    public Observacion denegarObservacion(Observacion observacion) throws Exception {
        if(observacion.getId() == null){
            throw new IllegalArgumentException("No ha especificado el id de la observación a modificar");
        }
        if(observacion.getUsuarioRevisa() == null){
            throw new IllegalArgumentException("Debe establecer el usuario que deniega la observación");
        }
        if(observacion.getMotivo()== null || observacion.getMotivo().isEmpty()){
            throw new IllegalArgumentException("Debe establecer el motivo de denegación de la observación");
        }
        Observacion observDB = this.find(observacion.getId());
        if(observDB == null){
            throw new IllegalArgumentException("Ha especificado una observación inválida");
        }
        observDB.setUsuarioRevisa(observacion.getUsuarioRevisa());
        observDB.setFechaRespuesta(new Date());
        observDB.setAceptada(Boolean.FALSE);
        observDB.setMotivo(observacion.getMotivo());
        return super.edit(observDB); //To change body of generated methods, choose Tools | Templates.
    }
    
//    public List<Observacion> findAllByUsuarioEmpresa(Integer usuarioId, Integer empresaId) {
//        Empleado empleado = empleadoFacade.findByUsuario(usuarioId);
//        if (empleado == null) {
//            return this.findAllByEmpresa(empresaId);
//        } else {
//            return this.findAllByArea(empleado.getArea().getId());
//        }
//    }
    
    @Override
    public List<Observacion> findAllByEmpresa(Integer empresaId){
        Query q = this.em.createQuery("SELECT ob FROM Observacion ob WHERE ob.tarjeta.empresa.id = ?1 ORDER BY ob.fechaObservacion DESC");
        q.setParameter(1, empresaId);
        List<Observacion> list = (List<Observacion> )q.getResultList();
        return list;
    }
    
    public List<Observacion> findAllByArea(Long areaId){
        Query q = this.em.createQuery("SELECT ob FROM Observacion ob WHERE ob.area.id = ?1 ORDER BY ob.fechaObservacion DESC");
        q.setParameter(1, areaId);
        List<Observacion> list = (List<Observacion> )q.getResultList();
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


}
