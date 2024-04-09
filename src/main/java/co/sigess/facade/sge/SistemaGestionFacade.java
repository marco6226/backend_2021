/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.sigess.facade.sge;

import co.sigess.entities.com.TipoMensaje;
import co.sigess.entities.sge.SistemaGestion;
import co.sigess.entities.sge.SistemaGestionPK;
import co.sigess.exceptions.UserMessageException;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import co.sigess.facade.com.AbstractFacade;
import javax.annotation.Resource;
import javax.transaction.UserTransaction;

/**
 *
 * @author fmoreno
 */
@Stateless
public class SistemaGestionFacade extends AbstractFacade<SistemaGestion> {

    @EJB
    private ElementoFacade elementoFacade;

    @PersistenceContext(unitName = "SIGESS_PU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public SistemaGestionFacade() {
        super(SistemaGestion.class);
    }

    @Override
    public SistemaGestion create(SistemaGestion sistemaGestion) throws Exception {
        sistemaGestion.setSistemaGestionPK(new SistemaGestionPK());
        sistemaGestion.getSistemaGestionPK().setVersion((short) 1);
        sistemaGestion = super.create(sistemaGestion);
        Integer numeroPreguntas = elementoFacade.createElementosSGE(sistemaGestion);
        sistemaGestion.setNumeroPreguntas(numeroPreguntas);
        sistemaGestion = super.edit(sistemaGestion);

        return sistemaGestion;
    }

    /**
     * Método que actualiza en base de datos el sistema de gestión recibido como parámetro.
     * @param sistemaGestion
     * @return Devuelve la misma referencia al objeto recibido como parámetro si se ha permitido 
     * una actualización completa que incluye los elementos del mismo. Si la actualización es parcial
     * debido a que existen evaluaciones con el sistema de gestión recibido como parámetro,
     * devuelve otra referencia a un objeto con la actualización aplicada que no incluye los elementos
     * del sistema de gestión.
     * @throws Exception 
     */
    @Override
    public SistemaGestion edit(SistemaGestion sistemaGestion) throws Exception {
        if (sistemaGestion.getSistemaGestionPK() == null) {
            throw new IllegalArgumentException("El sistema a actualizar no cuenta con un id válido");
        }
        if (sistemaGestion.getNombre() == null || sistemaGestion.getNombre().isEmpty()) {
            throw new IllegalArgumentException("El sistema de gestion debe contar con un nombre");
        }
        if (sistemaGestion.getCodigo() == null || sistemaGestion.getCodigo().isEmpty()) {
            throw new IllegalArgumentException("El sistema de gestion debe contar con un código");
        }

        SistemaGestion sgeDB = this.find(sistemaGestion.getSistemaGestionPK());

        sgeDB.setNombre(sistemaGestion.getNombre());
        sgeDB.setDescripcion(sistemaGestion.getDescripcion());
        sgeDB.setCodigo(sistemaGestion.getCodigo());

        if (!sgeDB.getEvaluacionList().isEmpty()) {
            super.edit(sgeDB);
            return sgeDB;            
        }

        elementoFacade.removeElementosSGE(sgeDB);
        Integer numeroPreguntas = elementoFacade.createElementosSGE(sistemaGestion);
        sistemaGestion.setNumeroPreguntas(numeroPreguntas);
        super.edit(sistemaGestion);

        return sistemaGestion;
    }

    public List<SistemaGestion> findAllByEmpresa(Integer empresaId) {
        Query query = this.em.createQuery("SELECT sg from SistemaGestion sg where sg.empresa.id = :empresaId");
        query.setParameter("empresaId", empresaId);
        List<SistemaGestion> list = (List<SistemaGestion>) query.getResultList();
        return list;
    }

    public SistemaGestion findByEvaluacion(Integer evaluacionId) {
        Query query = this.em.createQuery("SELECT sg from SistemaGestion sg JOIN sg.evaluacionList eval where eval.id = :evaluacionId");
        query.setParameter("evaluacionId", evaluacionId);
        SistemaGestion sg = (SistemaGestion) query.getSingleResult();
        return sg;
    }

}
