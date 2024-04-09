/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package co.sigess.facade.emp;

import co.sigess.entities.emp.Plantas;
import co.sigess.facade.com.AbstractFacade;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author JULIO
 */
@Stateless
public class PlantasFacade extends AbstractFacade<Plantas>{
    
    @PersistenceContext(unitName = "SIGESS_PU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager(){
        return em;
    }

    public PlantasFacade() {
        super(Plantas.class);
    }

    public List<Plantas> findPlantasByEmpresa(long idEmpresa){
        Query q = this.em.createQuery("SELECT P FROM Plantas p WHERE p.id_empresa=:idEmpresa ORDER BY p.pais, p.nombre");
        q.setParameter("idEmpresa", idEmpresa);
        List<Plantas> plantas = (List<Plantas>)  q.getResultList();
        return plantas;
    }
}
