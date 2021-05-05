/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.sigess.facade.inp;

import co.sigess.entities.com.TipoMensaje;
import co.sigess.entities.conf.Formulario;
import co.sigess.entities.inp.ElementoInspeccion;
import co.sigess.entities.inp.ListaInspeccion;
import co.sigess.entities.inp.ListaInspeccionPK;
import co.sigess.entities.inp.OpcionCalificacion;
import co.sigess.exceptions.UserMessageException;
import co.sigess.facade.com.AbstractFacade;
import co.sigess.facade.conf.FormularioFacade;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author fmoreno
 */
@Stateless
public class ListaInspeccionFacade extends AbstractFacade<ListaInspeccion> {

    @PersistenceContext(unitName = "SIGESS_PU")
    private EntityManager em;

    @EJB
    private ElementoInspeccionFacade elementoInspeccionFacade;

    @EJB
    private OpcionCalificacionFacade opcionCalificacionFacade;

    @EJB
    private FormularioFacade formularioFacade;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public ListaInspeccionFacade() {
        super(ListaInspeccion.class);
    }

    @Override
    public ListaInspeccion create(ListaInspeccion listaInspeccion) throws Exception {
        if (listaInspeccion.getElementoInspeccionList() == null || listaInspeccion.getElementoInspeccionList().isEmpty()) {
            throw new IllegalArgumentException("La lista no contiene elementos a inspeccionar");
        }
        if (listaInspeccion.getOpcionCalificacionList() == null || listaInspeccion.getOpcionCalificacionList().size() < 2) {
            throw new IllegalArgumentException("La lista debe contener al menos dos opciones de calificacion");
        }
        Formulario formulario = formularioFacade.create(listaInspeccion.getFormulario());

        listaInspeccion.setFormulario(formulario);
        if (listaInspeccion.getListaInspeccionPK() == null) {
            listaInspeccion.setListaInspeccionPK(new ListaInspeccionPK());
        }
        if (listaInspeccion.getListaInspeccionPK().getVersion() <= 0) {
            listaInspeccion.getListaInspeccionPK().setVersion(1);
        }

        listaInspeccion = super.create(listaInspeccion);
        Integer numeroPreguntas = elementoInspeccionFacade.createElementosInspeccion(listaInspeccion);
        listaInspeccion.setNumeroPreguntas(numeroPreguntas);
        listaInspeccion = super.edit(listaInspeccion);
        for (OpcionCalificacion opcionCalificacion : listaInspeccion.getOpcionCalificacionList()) {
            opcionCalificacion.setListaInspeccion(listaInspeccion);
            opcionCalificacionFacade.create(opcionCalificacion);
        }

        return listaInspeccion;
    }

    public List<ListaInspeccion> findAllByEmpresa(Integer empresaId) {
        Query query = this.em.createQuery("SELECT new co.sigess.entities.inp.ListaInspeccion(li.listaInspeccionPK, li.nombre, li.codigo, li.descripcion, li.numeroPreguntas) from ListaInspeccion li where  li.empresa.id = :empresaId");
        query.setParameter("empresaId", empresaId);
        List<ListaInspeccion> list = (List<ListaInspeccion>) query.getResultList();
        return list;
    }

    @Override
    public ListaInspeccion edit(ListaInspeccion listInp) throws Exception {
        if (listInp.getListaInspeccionPK() == null) {
            throw new IllegalArgumentException("La lista de inspección a actualizar no cuenta con un id válido");
        }

        ListaInspeccion listInpDB = this.find(listInp.getListaInspeccionPK());
        if (listInpDB.getProgramacionList() != null && !listInpDB.getProgramacionList().isEmpty()) {
            listInpDB.getProgramacionList().stream().filter((programacion) -> (programacion.getInspeccionList() != null && !programacion.getInspeccionList().isEmpty())).forEachOrdered((_item) -> {
                throw new UserMessageException("No es posible modificar la lista de inspección", "Existen inspecciones realizadas con la lista que intenta modificar", TipoMensaje.warn);
            });
        }

        elementoInspeccionFacade.removeElementosInspeccion(listInpDB);
        opcionCalificacionFacade.removeOpcionesCalificacion(listInpDB);
        formularioFacade.edit(listInp.getFormulario());

        Integer numeroPreguntas = elementoInspeccionFacade.createElementosInspeccion(listInp);
        listInp.setNumeroPreguntas(numeroPreguntas);

        for (OpcionCalificacion opcionCalificacion : listInp.getOpcionCalificacionList()) {
            opcionCalificacion.setListaInspeccion(listInp);
            opcionCalificacionFacade.create(opcionCalificacion);
        }

        listInp = super.edit(listInp);

        return listInp;
    }

    public int editProfile(ListaInspeccion listInp) throws Exception {
        if (listInp.getListaInspeccionPK() == null) {
            throw new IllegalArgumentException("La lista de inspección a actualizar no cuenta con un id válido");
        }

             System.out.print(listInp.getFkPerfilId() + " " + listInp.getListaInspeccionPK().getId());
             Query query = this.em.createNativeQuery("UPDATE inp.lista_inspeccion SET  fk_perfil_id = ? where id = ? ;");
             query.setParameter(1, listInp.getFkPerfilId());
             query.setParameter(2, listInp.getListaInspeccionPK().getId());
                
              int res = query.executeUpdate();

        return res;
    }

    
    public ListaInspeccion actualizarVersion(ListaInspeccion listaInspeccion) throws Exception {
        if (listaInspeccion.getListaInspeccionPK() == null) {
            throw new IllegalArgumentException("La lista de inspección a actualizar no cuenta con un id válido");
        }
        listaInspeccion.getListaInspeccionPK().setVersion(listaInspeccion.getListaInspeccionPK().getVersion() + 1);
        listaInspeccion.getFormulario().setId(null);
        listaInspeccion.getFormulario().getCampoList().forEach(campo -> campo.setId(null));
        listaInspeccion.getOpcionCalificacionList().forEach((opc) -> opc.setId(null));
        listaInspeccion.setProgramacionList(null);
        this.reiniciarId(listaInspeccion.getElementoInspeccionList());

        return this.create(listaInspeccion);
    }

    private void reiniciarId(List<ElementoInspeccion> elementos) {
        for (ElementoInspeccion elem : elementos) {
            if (elem.getElementoInspeccionList() != null && !elem.getElementoInspeccionList().isEmpty()) {
                this.reiniciarId(elem.getElementoInspeccionList());
            }
            elem.setId(null);
        }
    }

}
