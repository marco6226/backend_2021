/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.sigess.facade.sge;

import co.sigess.entities.ado.Documento;
import co.sigess.entities.sge.Elemento;
import co.sigess.entities.sge.OpcionRespuesta;
import co.sigess.entities.sge.SistemaGestion;
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
public class ElementoFacade extends AbstractFacade<Elemento> {

    @PersistenceContext(unitName = "SIGESS_PU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public ElementoFacade() {
        super(Elemento.class);
    }

    public void removeElementosSGE(SistemaGestion sistemaGestion) {
        for (Elemento elemento : sistemaGestion.getElementoList()) {
            super.remove(elemento);
        }
        this.em.flush();
    }

    //---------------------------------------------------------------------------------
    //---------------------------------------------------------------------------------
    //---------------------------------------------------------------------------------
    public Integer createElementosSGE(SistemaGestion sistemaGestion) {
        IntegerWrap numeroPreguntas = new IntegerWrap();
        this.recursiveCreate(sistemaGestion, sistemaGestion.getElementoList(), numeroPreguntas);
        return numeroPreguntas.getValue();
    }

    private void recursiveCreate(Object padre, List<Elemento> elementosList, IntegerWrap numeroPreguntas) {

        for (Elemento elemento : elementosList) {
            if (padre instanceof SistemaGestion) {
                elemento.setSistemaGestion((SistemaGestion) padre);
            } else if (padre instanceof Elemento) {
                elemento.setElementoPadre((Elemento) padre);
            } else {
                continue;
            }

            if (elemento.getOpcionRespuestaList() != null && !elemento.getOpcionRespuestaList().isEmpty()) {
                for (OpcionRespuesta opResp : elemento.getOpcionRespuestaList()) {
                    opResp.setElemento(elemento);
                }
                numeroPreguntas.increment(1);
            }
            if (elemento.getElementoList() != null && !elemento.getElementoList().isEmpty()) {
                this.recursiveCreate(elemento, elemento.getElementoList(), numeroPreguntas);
            }
            super.create(elemento);
        }
    }

    public Elemento actualizarDocumentos(Elemento elemento) {
        if (elemento.getId() == null) {
            throw new IllegalArgumentException("No se pueden actualizar documentos para elementos que no han sido creados");
        }
        for (Documento doc : elemento.getDocumentosList()) {
            if(doc.getId() == null){
                throw new IllegalArgumentException("Los documentos especificados no son válidos");
            }
        }
        Elemento elemDB = this.find(elemento.getId());
        if (elemDB == null) {
            throw new IllegalArgumentException("El elemento a actualizar no es válido");
        }
        elemDB.setDocumentosList(elemento.getDocumentosList());
        super.edit(elemDB);
        return elemDB;
    }

}
