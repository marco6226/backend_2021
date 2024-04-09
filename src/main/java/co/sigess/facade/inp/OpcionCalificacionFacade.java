/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.sigess.facade.inp;

import co.sigess.entities.inp.ListaInspeccion;
import co.sigess.entities.inp.OpcionCalificacion;
import co.sigess.facade.com.AbstractFacade;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author fmoreno
 */
@Stateless
public class OpcionCalificacionFacade extends AbstractFacade<OpcionCalificacion> {

    @PersistenceContext(unitName = "SIGESS_PU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public OpcionCalificacionFacade() {
        super(OpcionCalificacion.class);
    }

    public void removeOpcionesCalificacion(ListaInspeccion listaInspeccion) throws Exception {
        for (OpcionCalificacion opc : listaInspeccion.getOpcionCalificacionList()) {
            super.remove(opc);
        }
        this.em.flush();
    }

}
