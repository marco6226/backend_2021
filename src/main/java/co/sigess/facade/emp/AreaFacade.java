/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.sigess.facade.emp;

import co.sigess.facade.com.AbstractFacade;
import co.sigess.entities.emp.Area;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author fmoreno
 */
@javax.ejb.Stateless
public class AreaFacade extends AbstractFacade<Area> {

    @PersistenceContext(unitName = "SIGESS_PU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public AreaFacade() {
        super(Area.class);
    }

    public List<Area> findByEmpresa(Integer empresaId) {
        Query q = this.em.createQuery("SELECT a from Area a WHERE a.tipoArea.empresa.id = :empresaId AND a.areaPadre = null");
        q.setParameter("empresaId", empresaId);
        List<Area> areaList = (List<Area>) q.getResultList();
        return areaList;
    }

    public List<Area> findByAreaPadre(Long areaPadreId) {
        Query q = this.em.createQuery("SELECT a from Area a where a.areaPadre.id = :areaPadreId");
        q.setParameter("areaPadreId", areaPadreId);
        List<Area> areaList = (List<Area>) q.getResultList();
        return areaList;
    }
    
    public List<Area> findById(int id) {
        Query q = this.em.createQuery("SELECT a from Area a where a.id = :id");
        q.setParameter("id", id);
        List<Area> areaList = (List<Area>) q.getResultList();
        return areaList;
    }
    
     public List<Area> findByIdSL() {
        Query q = this.em.createQuery("SELECT a FROM Area a");
        //q.setParameter("id", id);
        List<Area> areaList = (List<Area>) q.getResultList();
        return areaList;
    }

    @Override
    public Area create(Area area) throws Exception {
        Area a = new Area();
        if (area == null) {
            throw new IllegalArgumentException("Area a crear no contiene datos");
        }
        if (area.getId() != null) {
            throw new IllegalArgumentException("El area a crear no debe contener un id");
        }
        if (area.getNombre() == null || area.getNombre().isEmpty()) {
            throw new IllegalArgumentException("No se ha establecido un nombre para el area a crear");
        }

        if (area.getAreaPadre() != null) {
            // Condición en la que el area se relaciona por con areaPadre
            Area areaPadre = this.find(area.getAreaPadre().getId());
            if (areaPadre == null) {
                throw new IllegalArgumentException("El area establecida como padre no es válido");
            }
            area.setNivel((short) (areaPadre.getNivel() + 1));
        } else {
            area.setNivel((short) 0);
        }
        a=super.create(area);

        Query q1 = this.em.createNativeQuery("SELECT emp.campo_padre_areas()");
        q1.getResultList();
        
        Query q2 = this.em.createNativeQuery("SELECT emp.calcular_area_padre_telefonica()");
        q2.getResultList();
        
        return a;
    }

    @Override
    public Area edit(Area entity) throws Exception {
        Area areaDB = this.find(entity.getId());
        areaDB.setNombre(entity.getNombre());
        areaDB.setDescripcion(entity.getDescripcion());
        areaDB.setTipoArea(entity.getTipoArea());
        return super.edit(areaDB); //To change body of generated methods, choose Tools | Templates.
    }

    public Area eliminar(Long areaId) throws Exception {
        Area areaDB = this.find(areaId);
        Area areaPadre = areaDB.getAreaPadre();
        for (Area child : areaDB.getAreaList()) {
            child.setAreaPadre(areaPadre);
            super.edit(child);
        }
        this.em.flush();
        super.remove(areaDB); //To change body of generated methods, choose Tools | Templates.
        return areaDB;
    }
    
    

}
