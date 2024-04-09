/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.sigess.facade.inp;

import co.sigess.entities.inp.ElementoInspeccion;
import co.sigess.entities.inp.ListaInspeccion;
import co.sigess.facade.com.AbstractFacade;
import co.sigess.facade.sge.ElementoFacade;
import co.sigess.util.IntegerWrap;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author fmoreno
 */
@Stateless
public class ElementoInspeccionFacade extends AbstractFacade<ElementoInspeccion> {

    @PersistenceContext(unitName = "SIGESS_PU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public ElementoInspeccionFacade() {
        super(ElementoInspeccion.class);
    }

    public void removeElementosInspeccion(ListaInspeccion listaInspeccion) throws Exception {
        this.recursiveDelete(listaInspeccion.getElementoInspeccionList());
        this.em.flush();
    }

    private void recursiveDelete(List<ElementoInspeccion> elemList) throws Exception {
        for (ElementoInspeccion elem : elemList) {
            super.remove(elem);
        }
    }

    //---------------------------------------------------------------------------------
    //---------------------------------------------------------------------------------
    //---------------------------------------------------------------------------------
    public Integer createElementosInspeccion(ListaInspeccion listInp) throws Exception {
        IntegerWrap numeroPreguntas = new IntegerWrap();
        this.recursiveCreate(listInp, listInp.getElementoInspeccionList(), numeroPreguntas);
        return numeroPreguntas.getValue();
    }

    private void recursiveCreate(Object padre, List<ElementoInspeccion> elementosList, IntegerWrap numeroPreguntas) throws Exception {

        for (ElementoInspeccion elemento : elementosList) {
            if (padre instanceof ListaInspeccion) {
                elemento.setListaInspeccion((ListaInspeccion) padre);
            } else if (padre instanceof ElementoInspeccion) {
                elemento.setElementoInspeccionPadre((ElementoInspeccion) padre);
            } else {
                continue;
            }

            if (elemento.getElementoInspeccionList() == null || elemento.getElementoInspeccionList().isEmpty()) {
                elemento.setCalificable(true);
                numeroPreguntas.increment(1);
            } else {
                elemento.setCalificable(false);
            }
            elemento.setId(null);
            elemento = super.create(elemento);

            if (elemento.getElementoInspeccionList() != null && !elemento.getElementoInspeccionList().isEmpty()) {
                this.recursiveCreate(elemento, elemento.getElementoInspeccionList(), numeroPreguntas);
            }
        }
    }

}
