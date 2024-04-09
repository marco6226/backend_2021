/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.sigess.facade.sec;

import co.sigess.entities.com.TipoMensaje;
import co.sigess.entities.sec.Desviacion;
import co.sigess.exceptions.UserMessageException;
import co.sigess.facade.com.AbstractFacade;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author fmoreno
 */
@Stateless
public class DesviacionFacade extends AbstractFacade<Desviacion> {

    @PersistenceContext(unitName = "SIGESS_PU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public DesviacionFacade() {
        super(Desviacion.class);
    }

    public List<Desviacion> findByEmpresa(Integer empresaIdRequestContext) {
        Query query = this.em.createQuery("SELECT d FROM Desviacion d WHERE d.empresaId = ?1");
        query.setParameter(1, empresaIdRequestContext);
        List<Desviacion> list = (List<Desviacion>) query.getResultList();
        return list;
    }

    public ByteArrayOutputStream consultarConsolidado(Integer empresaId, Date invDesde, Date invHasta) throws IOException {
        Query q = this.em.createNativeQuery("SELECT data FROM sec.generar_consolidado_analisis(?1, ?2, ?3, ?4, ?5)");
        q.setParameter(1, empresaId);
        q.setParameter(2, invDesde);
        q.setParameter(3, invHasta);
        q.setParameter(4, invDesde);
        q.setParameter(5, invDesde);
        List<String> lines = q.getResultList();
        if (lines.isEmpty() || lines.size() <= 1) {
            throw new UserMessageException("Datos no encontrados", "No se hallaron investigaciones en el rango de fechas seleccionado", TipoMensaje.info);
        }

        ByteArrayOutputStream bOutput = new ByteArrayOutputStream(2_000 + (lines.size() * 400));

        for (String line : lines) {
            bOutput.write(line.getBytes());
            bOutput.write("\n".getBytes());
        }
        return bOutput;
    }
    
    public List<Desviacion> findDesviacionAlido(int idDesviacion){
        Query q = this.em.createQuery("SELECT d FROM Desviacion d WHERE d.hashId = ?1");
        q.setParameter(1,"RAI-"+idDesviacion);
        List<Desviacion> list = (List<Desviacion>) q.getResultList();
        return list;
    }

}
