/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.sigess.facade.inp;

import co.sigess.entities.inp.Programacion;
import co.sigess.facade.com.AbstractFacade;
import java.math.BigDecimal;
import java.util.Date;
import java.time.LocalDate;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author fmoreno
 */
@Stateless
public class ProgramacionFacade extends AbstractFacade<Programacion> {

    @PersistenceContext(unitName = "SIGESS_PU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public ProgramacionFacade() {
        super(Programacion.class);
    }

    public List<Programacion> findAllByEmpresa(Integer empresaId) {
        Query query = this.em.createQuery("SELECT p FROM Programacion p WHERE p.listaInspeccion.empresa.id = ?1");
        query.setParameter(1, empresaId);
        List<Programacion> list = (List<Programacion>) query.getResultList();
        return list;
    }

    public Programacion modificar(Programacion prog) throws Exception {
        if (prog.getId() == null) {
            throw new IllegalArgumentException("No se ha establecido el id de la programación a modificar");
        }
        if (prog.getArea() == null) {
            throw new IllegalArgumentException("La programación debe contener un area");
        }
        if (prog.getListaInspeccion() == null) {
            throw new IllegalArgumentException("La programación debe tener una lista de inspección asignada");
        }
        Programacion progDB = this.find(prog.getId());
        if (progDB == null) {
            throw new IllegalArgumentException("id de programación inválido");
        }
        return super.edit(prog); //To change body of generated methods, choose Tools | Templates.
    }
    
    public Programacion modificarAuditoría(Programacion prog) throws Exception{
        if (prog.getId() == null) throw new IllegalArgumentException("No se ha establecido el id de la programación");
        if (prog.getEmpresa() == null) throw new IllegalArgumentException("No se ha establecido la empresa que se va a auditar");
        if (prog.getLocalidad() == null) throw new IllegalArgumentException("No se ha establecido la localidad de la Auditoría");
        if (prog.getListaInspeccion() == null) throw new IllegalArgumentException("La programación debe estar asociada a una auditoría");
        
        Programacion progDB = this.find(prog.getId());
        if (progDB == null) throw new IllegalArgumentException("id de programación inválido");
        return super.edit(prog);
    }

    @Override
    public Programacion create(Programacion prog) throws Exception {
        if (prog.getArea() == null) {
            throw new IllegalArgumentException("La programación debe contener un area");
        }
        if (prog.getListaInspeccion() == null) {
            throw new IllegalArgumentException("La programación debe tener una lista de inspección asignada");
        }
        return super.create(prog); //To change body of generated methods, choose Tools | Templates.
    }
    
    public Programacion createAuditoria(Programacion prog) throws Exception {
        if (prog.getLocalidad() == null) throw new IllegalArgumentException("La programación debe tener una localidad");
        if (prog.getEmpresa() == null) throw new IllegalArgumentException("La programación debe tener una empresa asociada");
        if (prog.getListaInspeccion() == null) throw new IllegalArgumentException("La programación debe tener una lista de inspección asignada");
        return super.create(prog);
    }

    public List<Programacion> findAllByMes(Integer empresaId, LocalDate fechaMes) {
        LocalDate desde = fechaMes.withDayOfMonth(1);
        LocalDate hasta = fechaMes.withDayOfMonth(fechaMes.lengthOfMonth());
        Query query = this.em.createQuery("SELECT p FROM Programacion p WHERE p.listaInspeccion.empresa.id = ?1 AND p.fecha BETWEEN ?2 AND ?3");
        query.setParameter(1, empresaId);
        query.setParameter(2, java.sql.Date.valueOf(desde));
        query.setParameter(3, java.sql.Date.valueOf(hasta));
        List<Programacion> list = (List<Programacion>) query.getResultList();
        return list;
    }

    public List<Programacion> findAllByArea(Integer empresaId, Integer areaId) {
        Query query = this.em.createQuery("SELECT p FROM Programacion p WHERE p.listaInspeccion.empresa.id = ?1 AND p.area.id = ?2");
        query.setParameter(1, empresaId);
        query.setParameter(2, areaId);
        List<Programacion> list = (List<Programacion>) query.getResultList();
        return list;
    }

        public double calcularCumplimiento(Long areaId, Date desde, Date hasta) {
        Query q = this.em.createNativeQuery("SELECT avg(p.numero_realizadas * 1.0 / p.numero_inspecciones)::numeric\n"
                + "from inp.programacion p \n"
                + "inner join emp.area a on a.id = p.fk_area_id\n"
                + "where a.id = ?1 and p.fecha between ?2 AND ?3 GROUP BY a.id");
        q.setParameter(1, areaId);
        q.setParameter(2, desde);
        q.setParameter(3, hasta);
        try {
            return (double) ((BigDecimal) q.getSingleResult()).doubleValue();
        } catch (NoResultException nre) {
            return 0;
        } catch (Exception ed) {
            System.out.println(ed.toString());
            return 0;
        }
    }

    public double calcularCubrimiento(Date desde, Date hasta) {
        String sql = "with calc as (\n"
                + "	select p.fk_area_id, \n"
                + "		sum(case when p.numero_realizadas = 0 then 1 else 0 end) = 0 as cubierta\n"
                + "		from inp.programacion p \n"
                + "		where (p.fecha between ?1 AND ?2)\n"
                + "		group by p.fk_area_id\n"
                + "),\n"
                + "cub as (\n"
                + "	select \n"
                + "		sum(case when cubierta = true then 1 else 0 end) as cubiertas,\n"
                + "		sum(case when cubierta = false then 1 else 0 end) as no_cubiertas\n"
                + "		from calc\n"
                + ")\n"
                + "select cubiertas / (cubiertas + no_cubiertas)::numeric as cubrimiento from cub";
        Query q = this.em.createNativeQuery(sql);
        q.setParameter(1, desde);
        q.setParameter(2, hasta);
        try {
            return (double) ((BigDecimal) q.getSingleResult()).doubleValue();
        } catch (NoResultException | NullPointerException nre) {
            return 0;
        }
    }

}
