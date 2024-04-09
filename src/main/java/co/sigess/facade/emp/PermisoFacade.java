/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.sigess.facade.emp;

import co.sigess.facade.com.AbstractFacade;
import co.sigess.entities.emp.Permiso;
import co.sigess.entities.emp.Recurso;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author fmoreno
 */
@javax.ejb.Stateless
public class PermisoFacade extends AbstractFacade<Permiso> {

    @PersistenceContext(unitName = "SIGESS_PU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public PermisoFacade() {
        super(Permiso.class);
    }

    public List<Permiso> findAllByEmpresa(Integer empresaId, Integer usuarioId) {
        Query query = this.em.createNativeQuery("WITH permisos AS (\n"
                + "	SELECT\n"
                + "	    p.fk_recurso_id,\n"
                + "	    array_cat_agg(DISTINCT p.areas::bigint[])::text AS areas\n"
                + "	FROM emp.usuario_empresa ue\n"
                + "	    LEFT JOIN emp.perfil per ON per.id = ue.pk_perfil_id\n"
                + "	    LEFT JOIN emp.permiso p ON p.fk_perfil_id = per.id\n"
                + "	WHERE ue.pk_empresa_id = ?1\n"
                + "	    AND ue.pk_usuario_id = ?2\n"
                + "         AND p.valido = true\n"
                + "	GROUP BY p.fk_recurso_id\n"
                + ")\n"
                + "SELECT r.id, r.modulo, r.tipo, r.nombre, r.codigo, r.validacion_area, p.areas\n"
                + "FROM permisos p\n"
                + "	INNER JOIN emp.recurso r ON r.id = p.fk_recurso_id");
        query.setParameter(1, empresaId);
        query.setParameter(2, usuarioId);
        List<Permiso> permisos = new ArrayList<>();
        List<Object[]> list = query.getResultList();
        list.forEach((object) -> {
            Permiso p = new Permiso();
            Recurso rec = new Recurso((Integer) object[0]);
            rec.setCodigo(object[4].toString());
            rec.setValidacionArea((Boolean)object[5]);
            p.setRecurso(rec);
            p.setValido(true);
            p.setAreas(object[6] == null ? null : object[6].toString());
            permisos.add(p);
        });
        return permisos;
    }

    public List<Permiso> findAllByPerfil(Integer empresaId, Integer perfilId) {
        Query query = this.em.createQuery("SELECT p FROM Permiso p WHERE p.empresa.id = ?1 AND p.perfil.id = ?2");
        query.setParameter(1, empresaId);
        query.setParameter(2, perfilId);
        List<Permiso> list = (List<Permiso>) query.getResultList();
        return list;
    }

    /**
     *
     * @param permiso
     * @return
     * @throws Exception
     */
    @Override
    public Permiso edit(Permiso permiso) throws Exception {
        if (permiso == null) {
            throw new IllegalArgumentException("Objeto permiso no puede ser null");
        }
        if (permiso.getEmpresa() == null) {
            throw new IllegalArgumentException("Permiso debe estar asociado a una empresa");
        }
        if (permiso.getPerfil() == null) {
            throw new IllegalArgumentException("Permiso debe estar asociado a un perfil");
        }
        if (permiso.getRecurso() == null) {
            throw new IllegalArgumentException("Permiso debe estar asociado a un recurso");
        }

        Permiso permisoDB = this.existePermiso(permiso);
        if (permisoDB == null) {
            permiso.setId(null);
            super.create(permiso);
            return permiso;
        } else {
            permisoDB.setValido(permiso.isValido());
            permisoDB.setAreas(permiso.getAreas());
            super.edit(permisoDB);
            return permisoDB;
        }
    }

    public Permiso existePermiso(Permiso permiso) {
        Query query = this.em.createQuery("SELECT p FROM Permiso p WHERE p.empresa.id = ?1 AND p.recurso.id = ?2 AND p.perfil.id = ?3");
        query.setParameter(1, permiso.getEmpresa().getId());
        query.setParameter(2, permiso.getRecurso().getId());
        query.setParameter(3, permiso.getPerfil().getId());

        try {
            Permiso permisoDB = (Permiso) query.getSingleResult();
            return permisoDB;
        } catch (NoResultException nre) {
            return null;
        }
    }
}
