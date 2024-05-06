/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package co.sigess.facade.scm;

import co.sigess.entities.scm.Pcl;
import co.sigess.entities.scm.Pcl_Diagnostico;
import co.sigess.facade.com.AbstractFacade;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author Luis
 */
@Stateless
public class pclDiagnosticosFacade extends AbstractFacade<Pcl_Diagnostico> {

    @PersistenceContext(unitName = "SIGESS_PU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public pclDiagnosticosFacade() {
        super(Pcl_Diagnostico.class);
    }

    public Pcl_Diagnostico create(Pcl_Diagnostico pclDiag) throws Exception {

        return super.create(pclDiag);
    }

    public List<Object> findAllByIdPCL(String pcl) {
        Query query = this.em.createNativeQuery("SELECT pcl.*, diag.*\n"
                + "FROM scm.pcl pcl\n"
                + "JOIN scm.pcl_diagnostico pcl_diag ON pcl.id = pcl_diag.id_pcl\n"
                + "JOIN scm.diagnosticos diag ON pcl_diag.id_diagnostico = diag.id\n"
                + "WHERE pcl.id = 131;");
        // query.setParameter(1, pcl);
        List<Object> list = query.getResultList();
        System.out.println(query);
        System.out.println(list + "lsta");
        return list;
    }

}
