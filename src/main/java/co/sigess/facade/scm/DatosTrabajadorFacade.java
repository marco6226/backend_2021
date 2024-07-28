/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package co.sigess.facade.scm;

import co.sigess.entities.emp.Empleado;
import co.sigess.entities.scm.DatosTrabajadorEntity;
import co.sigess.facade.com.AbstractFacade;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.Context;

/**
 *
 * @author Luis
 */
@Stateless
public class DatosTrabajadorFacade extends AbstractFacade<DatosTrabajadorEntity> {

    @Context
    private HttpServletRequest httpRequest;

    @PersistenceContext(unitName = "SIGESS_PU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public DatosTrabajadorFacade() {
        super(DatosTrabajadorEntity.class);
    }

    public DatosTrabajadorEntity createDT(DatosTrabajadorEntity data) throws Exception {
        super.create(data);
        return data;
    }

    public List<DatosTrabajadorEntity> buscarSaludLaboral() {

        try {
            Query query = em.createNativeQuery(
                    "SELECT "
                    + "    scm.salud_laboral.iddt, "
                    + "    scm.salud_laboral.usuario_asignado, "
                    + "    scm.salud_laboral.fecha_creacion, "
                    + "    scm.salud_laboral.fecha_edicion, "
                    + "    scm.salud_laboral.cargo_original, "
                    + "    cargo_original.nombre AS cargo_original_nombre, "
                    + "    scm.salud_laboral.cargo_actual, "
                    + "    cargo_actual.nombre AS cargo_actual_nombre, "
                    + "    division_origen_padre.padre_nombre AS division_origen_padre_nombre, "
                    + "    division_actual_padre.padre_nombre AS division_actual_padre_nombre, "
                    + "    localidad_origen.localidad AS localidad_origen_nombre, "
                    + "    localidad_actual.localidad AS localidad_actual_nombre, "
                    + "    scm.salud_laboral.proceso_origen, "
                    + "    scm.salud_laboral.proceso_actual, "
                    + "    scm.salud_laboral.usuario_creador, "
                    + "    scm.salud_laboral.area_origen, "
                    + "    scm.salud_laboral.area_actual "
                    + "FROM "
                    + "    scm.salud_laboral "
                    + "LEFT JOIN "
                    + "    emp.cargo_actual AS cargo_original ON scm.salud_laboral.cargo_original::bigint = cargo_original.id "
                    + "LEFT JOIN "
                    + "    emp.cargo_actual AS cargo_actual ON scm.salud_laboral.cargo_actual::bigint = cargo_actual.id "
                    + "LEFT JOIN "
                    + "    emp.area AS division_origen_padre ON scm.salud_laboral.division_origen::bigint = division_origen_padre.id "
                    + "LEFT JOIN "
                    + "    emp.area AS division_actual_padre ON scm.salud_laboral.division_actual::bigint = division_actual_padre.id "
                    + "LEFT JOIN "
                    + "    emp.localidades AS localidad_origen ON scm.salud_laboral.localidad_origen::bigint = localidad_origen.id "
                    + "LEFT JOIN "
                    + "    emp.localidades AS localidad_actual ON scm.salud_laboral.localidad_actual::bigint = localidad_actual.id",
                    "SaludLaboralDTO"
            );

            List<DatosTrabajadorEntity> result = query.getResultList();
            return result;
        } finally {
            em.close();
        }
    }

    public DatosTrabajadorEntity findById(Integer idSl) {
        Query q = this.em.createNativeQuery("SELECT * FROM scm.salud_laboral WHERE id_sl = ?1", DatosTrabajadorEntity.class);

        q.setParameter(1, idSl);
        DatosTrabajadorEntity saludLaboral = (DatosTrabajadorEntity) q.getSingleResult();
        return saludLaboral;
    }
    

    public DatosTrabajadorEntity update(DatosTrabajadorEntity dt) throws Exception {

        dt = super.edit(dt);
        return dt;
    }
    

    public List<DatosTrabajadorEntity> buscar(String parametro) {
        System.out.println(parametro);

        Query q = this.em.createNativeQuery("SELECT * FROM scm.salud_laboral  WHERE pk_empleado_caso = ?1 order by fecha_creacion desc ", DatosTrabajadorEntity.class);
//esta query hace relacion a busqueda por empleados.

        q.setParameter(1, Integer.parseInt(parametro));
        System.out.println(q);
        List<DatosTrabajadorEntity> list = (List<DatosTrabajadorEntity>) q.getResultList();
        return list;
    }
    
    public void deleteDocumentFromMail(Integer id, String docID) throws Exception {
    try {
        // Suponiendo que el campo documentos es una cadena que contiene los IDs de los documentos separados por comas.
        DatosTrabajadorEntity mailEntity = this.findById(id);
        if (mailEntity != null) {
            String documentos = mailEntity.getDocumentos();
            if (documentos != null && !documentos.isEmpty()) {
                List<String> documentosList = new ArrayList<>(Arrays.asList(documentos.split(",")));
                if (documentosList.contains(docID)) {
                    documentosList.remove(docID);
                    mailEntity.setDocumentos(String.join(",", documentosList));
                    this.update(mailEntity);
                } else {
                    throw new Exception("Documento no encontrado en la lista.");
                }
            } else {
                throw new Exception("No hay documentos para eliminar.");
            }
        } else {
            throw new Exception("MailSaludLaboralEntity no encontrado.");
        }
    } catch (Exception e) {
        // Manejar la excepción y re-lanzarla para que se registre adecuadamente
        e.printStackTrace();
        throw e;
    }
}

}
