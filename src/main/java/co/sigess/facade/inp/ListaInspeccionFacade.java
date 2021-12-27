/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.sigess.facade.inp;

import co.sigess.entities.ado.Documento;
import co.sigess.entities.com.TipoMensaje;
import co.sigess.entities.conf.Formulario;
import co.sigess.entities.inp.ElementoInspeccion;
import co.sigess.entities.inp.ListaInspeccion;
import co.sigess.entities.inp.ListaInspeccionPK;
import co.sigess.entities.inp.OpcionCalificacion;
import co.sigess.exceptions.UserMessageException;
import co.sigess.facade.com.AbstractFacade;
import co.sigess.facade.conf.FormularioFacade;
import java.util.ArrayList;
import java.util.HashMap;
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
        if(listInp.getEstado().equals("inactivo")){
            if (listInpDB.getProgramacionList() != null && !listInpDB.getProgramacionList().isEmpty()) {
                listInpDB.getProgramacionList().stream().filter((programacion) -> (programacion.getInspeccionList() != null && !programacion.getInspeccionList().isEmpty())).forEachOrdered((_item) -> {
                    throw new UserMessageException("No es posible modificar la lista de inspección", "Existen inspecciones realizadas con la lista que intenta modificar", TipoMensaje.warn);
                });
            }
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
    
    public ListaInspeccion editarForzado(ListaInspeccion listInp) throws Exception {
        if (listInp.getListaInspeccionPK() == null) {
            throw new IllegalArgumentException("La lista de inspección a actualizar no cuenta con un id válido");
        }
        
        ListaInspeccion listInpDB = this.find(listInp.getListaInspeccionPK());
//            if (listInpDB.getProgramacionList() != null && !listInpDB.getProgramacionList().isEmpty()) {
//                listInpDB.getProgramacionList().stream().filter((programacion) -> (programacion.getInspeccionList() != null && !programacion.getInspeccionList().isEmpty())).forEachOrdered((_item) -> {
//                    throw new UserMessageException("No es posible modificar la lista de inspección", "Existen inspecciones realizadas con la lista que intenta modificar", TipoMensaje.warn);
//                });
//        }
        
//        opcionCalificacionFacade.removeOpcionesCalificacion(listInpDB);
//        elementoInspeccionFacade.removeElementosInspeccion(listInpDB);
//        
//        formularioFacade.edit(listInp.getFormulario());
//
//        Integer numeroPreguntas = elementoInspeccionFacade.createElementosInspeccion(listInp);
//        listInp.setNumeroPreguntas(numeroPreguntas);
//
//        for (OpcionCalificacion opcionCalificacion : listInp.getOpcionCalificacionList()) {
//            opcionCalificacion.setListaInspeccion(listInp);
//            opcionCalificacionFacade.create(opcionCalificacion);
//        }

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
    
    public int editEstado(ListaInspeccion listInp) throws Exception {
        if (listInp.getListaInspeccionPK() == null) {
            throw new IllegalArgumentException("La lista de inspección a actualizar no cuenta con un id válido");
        }

            System.out.print(listInp.getFkPerfilId() + " " + listInp.getListaInspeccionPK().getId());
            Query query = this.em.createNativeQuery("UPDATE inp.lista_inspeccion SET  estado = 'inactivo' where id = ? AND version = ?;");
            System.out.println("ESTADO: " + listInp.getEstado());
            query.setParameter(1, listInp.getListaInspeccionPK().getId());
            query.setParameter(2, listInp.getListaInspeccionPK().getVersion());
            int res = query.executeUpdate();

            
            
        return res;
    }

    
    public ListaInspeccion actualizarVersion(ListaInspeccion listaInspeccion) throws Exception {
        if (listaInspeccion.getListaInspeccionPK() == null) {
            throw new IllegalArgumentException("La lista de inspección a actualizar no cuenta con un id válido");
        }
        
        editEstado(listaInspeccion);
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
    
     public HashMap<String, List<String>> getImages(int ListaId, String modulo) {
        ListaInspeccion tareaDB = super.find(ListaId);
        if (tareaDB == null) {
            throw new UserMessageException("No es posible reportar la tarea", "La tarea que intenta reportar no existe", TipoMensaje.warn);
        }
        
                String sql = ""
                + "select  \n"
                + " ado.id as ruta \n"
                + "from sec.tarea_desviacion as td\n"
                + "inner join sec.analisis_desviacion_tarea_desviacion as adtd\n"
                + "on td.id=adtd.pk_tarea_desviacion_id\n"
                + "inner join sec.desviacion_analisis_desviacion as dad\n"
                + "on adtd.pk_analisis_desviacion_id=dad.fk_analisis_desviacion_id	\n"
                + "left join sec.vw_desviacion as vd\n"
                + "on vd.hash_id=dad.pk_desviacion_hash_id\n";
                        
            switch(modulo){
                case "OBS":
                    sql+= "left join auc.documento_observacion as dc\n"
                    + "on dc.fk_observacion_id=vd.id\n"
                    + "left join ado.documento as ado\n"
                    + "on dc.fk_documento_id=ado.id\n"
                    + "where td.id= ?1 and ruta is not null";
                    break;
                case "INP":
                    sql+= "left join inp.calificacion as cali\n"
                    + "on vd.id=cali.fk_inspeccion_id and  vd.elemento=cali.fk_elemento_inspeccion_id\n"
                    + "left join inp.documento_calificacion as dc\n"
                    + "on dc.fk_calificacion_id=cali.id\n"
                    + "left join ado.documento as ado\n"
                    + "on dc.fk_documento_id=ado.id\n"
                    + "where td.id= ?1 and ruta is not null";
                    break;
                default:
                    break;
            }
                              
        System.out.print(sql);
        Query query = this.em.createNativeQuery(sql);
        //query.setParameter(1, tareaId);
        List<String> dtoList = query.setMaxResults(3).getResultList();
        System.out.print(dtoList.size());
        HashMap<String, List<String>> files = new HashMap<String, List<String>>();
        List<String> List = null;
        List<String> file = new ArrayList<String>();
        files.put("error", List);
       for (int i = 0; i <= dtoList.size(); i++) {
             // System.out.print(dtoList.get(i));
              try {
                  file.add(dtoList.get(i));
              } catch (Exception ex) {
                  //System.out.print("Erro" + ex.getMessage());
             }
          }
           files.put("files", dtoList);
            return files;
    }
     
      public HashMap<String, List<String>> getImages(int id_lista, int id_version) {
                
                String sql = ""
                + "select  ado.id as idimagen\n"
                + "from inp.lista_inspeccion as li\n"
                    + "left join inp.documento_lista_inspeccion as dli \n"                   
                    + "on li.id=dli.fk_lista_inspeccion_id and li.version=dli.fk_lista_inspeccion_version\n"
                    + "left join ado.documento as ado  \n"                  
                    + "on dli.fk_documento_id=ado.id\n"
                    + "where dli.fk_lista_inspeccion_id= ?1\n"
                    + "and dli.fk_lista_inspeccion_version = ?2";
                        
            
                              
        System.out.print(sql);
        Query query = this.em.createNativeQuery(sql);
        query.setParameter(1, id_lista);
        query.setParameter(2, id_version);
        
        List<String> dtoList = query.setMaxResults(3).getResultList();
        System.out.print(dtoList.size());
        HashMap<String, List<String>> files = new HashMap<String, List<String>>();
        List<String> List = null;
        List<String> file = new ArrayList<String>();
        files.put("error", List);
       for (int i = 0; i <= dtoList.size(); i++) {
             // System.out.print(dtoList.get(i));
              try {
                  file.add(dtoList.get(i));
              } catch (Exception ex) {
                  //System.out.print("Erro" + ex.getMessage());
             }
          }
           files.put("files", dtoList);
            return files;
    }
     
     public void guardarImagen(Documento id_documento, Long id_lista, Integer id_version) {
        Query query = this.em.createNativeQuery("INSERT INTO inp.documento_lista_inspeccion (fk_documento_id, fk_lista_inspeccion_id, fk_lista_inspeccion_version) VALUES (?1, ?2, ?3)");
        query.setParameter(1, id_documento);
        query.setParameter(2, id_lista);
        query.setParameter(3, id_version);
        query.executeUpdate();
    }
     
     public void guardarImagen(Integer id_documento, Integer id_lista, Integer id_version) {
        Query query = this.em.createNativeQuery("INSERT INTO inp.documento_lista_inspeccion (fk_documento_id, fk_lista_inspeccion_id, fk_lista_inspeccion_version) VALUES (?1, ?2, ?3)");
        query.setParameter(1, id_documento);
        query.setParameter(2, id_lista);
        query.setParameter(3, id_version);
        query.executeUpdate();
    }
     
     public void actualizarImagen(Integer id_documento, Integer id_lista, Integer id_version) {
        Query query = this.em.createNativeQuery("UPDATE inp.documento_lista_inspeccion\n" +
            "	SET fk_documento_id=?1\n" +
            "	WHERE fk_lista_inspeccion_id=?2 AND fk_lista_inspeccion_version=?3;");
        query.setParameter(1, id_documento);
        query.setParameter(2, id_lista);
        query.setParameter(3, id_version);
        query.executeUpdate();
    }
     
}
