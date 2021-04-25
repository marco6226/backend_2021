/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.sigess.facade.emp;

import co.sigess.entities.ado.Documento;
import co.sigess.entities.com.Mensaje;
import co.sigess.entities.com.TipoMensaje;
import co.sigess.facade.com.AbstractFacade;
import co.sigess.entities.emp.Empleado;
import co.sigess.entities.emp.EstadoUsuario;
import co.sigess.entities.emp.Usuario;
import co.sigess.exceptions.UserMessageException;
import co.sigess.restful.CriteriaFilter;
import co.sigess.restful.Filter;
import co.sigess.restful.FilterQuery;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.ejb.EJB;
import javax.ejb.EJBTransactionRolledbackException;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author fmoreno
 */
@javax.ejb.Stateless
public class EmpleadoFacade extends AbstractFacade<Empleado> {

    @EJB
    private UsuarioFacade usuarioFacade;

    @PersistenceContext(unitName = "SIGESS_PU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public EmpleadoFacade() {
        super(Empleado.class);
    }

    public List<Empleado> findByEmpresa(Integer empresaId) {
        Query query = this.em.createQuery("SELECT e from Empleado e WHERE e.cargo.empresa.id = :empresaId");
        query.setParameter("empresaId", empresaId);
        List<Empleado> list = (List<Empleado>) query.getResultList();
        return list;
    }

    public Empleado create(Empleado empleado, Integer empresaId) throws Exception {
        if (empleado.getUsuario() == null) {
            throw new IllegalArgumentException("El empleado a crear no contiene un usuario");
        }
        if (empleado.getUsuario().getEmail() == null || empleado.getUsuario().getEmail().isEmpty()) {
            throw new IllegalArgumentException("El campo email es necesario");
        }

        usuarioFacade.create(empleado.getUsuario(), empresaId,true);
        super.create(empleado);
        return empleado;
    }

    @Override
    public Empleado edit(Empleado empleado) throws Exception {
        if (empleado.getId() == null) {
            throw new IllegalArgumentException("No ha establecido un id para el empleado a actualizar");
        }
        Empleado empDB = this.find(empleado.getId());
        empleado.setUsuario(empDB.getUsuario());
        empleado.setEstado(empDB.getEstado());
        super.edit(empleado);
//        empleado = this.find(empleado.getId());
        return empleado;
    }

    public void remove(Integer empleadoId) throws Exception {
        Empleado empleadoDB = this.find(empleadoId);
        if (empleadoDB == null) {
            return;
        }
        empleadoDB.setEstado(EstadoUsuario.ELIMINADO);
        super.edit(empleadoDB);

        Usuario usuario = empleadoDB.getUsuario();
        if (usuario != null) {
            usuario.setEstado(EstadoUsuario.ELIMINADO);
            usuarioFacade.edit(usuario);
        }
    }

    public Empleado findByUsuario(Integer usuarioId) {
        Query q = this.em.createQuery("SELECT emp FROM Empleado emp WHERE emp.usuario.id = ?1 ");
        q.setParameter(1, usuarioId);
        try {
            Empleado empleado = (Empleado) q.getSingleResult();
            return empleado;
        } catch (NoResultException nre) {
            return null;
        }
    }

    public List<Empleado> buscar(String parametro, Integer empresaId) {
        Query q = this.em.createQuery("SELECT e FROM Empleado AS e WHERE e.cargo.empresa.id = ?2 AND (e.usuario.email LIKE ?1 OR e.numeroIdentificacion LIKE ?1 OR e.primerApellido LIKE ?1 OR e.primerNombre LIKE ?1 OR e.segundoApellido LIKE ?1 OR e.segundoNombre LIKE ?1)");
        q.setParameter(1, "%" + parametro + "%");
        q.setParameter(2, empresaId);
        List<Empleado> list = (List<Empleado>) q.getResultList();
        return list;
    }

    public void relacionarDocumento(Documento documento, Integer empleadoId) throws Exception {
        Empleado emp = this.find(empleadoId);
        emp.getDocumentosList().add(documento);
        super.edit(emp);
    }

    public void retirarDocumento(Documento documento) throws Exception {
        Query q = this.em.createQuery("SELECT e FROM Empleado e JOIN e.documentosList doc WHERE doc.id = ?1 ");
        q.setParameter(1, documento.getId());
        Empleado emp = (Empleado) q.getSingleResult();
        emp.getDocumentosList().remove(documento);
        super.edit(emp);
    }

    public List<Mensaje> createList(List<Empleado> list, Integer empresaId) throws Exception,UserMessageException {

        List<Mensaje> listErrors = new ArrayList<>();
        for (Empleado empleado : list) {
            try {
                if (empleado.getUsuario() == null) {
                    throw new IllegalArgumentException("El empleado a crear no contiene un usuario");
                }
                if (empleado.getUsuario().getEmail() == null || empleado.getUsuario().getEmail().isEmpty()) {
                    throw new IllegalArgumentException("El campo email es necesario");
                }
              
               
                usuarioFacade.create(empleado.getUsuario(), empresaId,false);
                super.create(empleado);
               
            } catch (Exception e) {
                System.out.println(e.getMessage()+"Aqui la prueba");
                Mensaje mu = new Mensaje("Error al procesar el empleado: " + empleado.getNumeroIdentificacion(), e.getMessage(), TipoMensaje.error);
                listErrors.add(mu);
            }
        }
        return listErrors;
    }

    public List<Mensaje> loadAll(List<Empleado> list, Integer empresaId) throws Exception {
        StringBuilder sbFilter = new StringBuilder();
        for (Empleado emp : list) {
            sbFilter.append(emp.getNumeroIdentificacion()).append(",");
        }
        String valueFilter = sbFilter.substring(0, sbFilter.length() - 1);
        FilterQuery fq = new FilterQuery();
        List<Filter> fl = new ArrayList<>();
        Filter filter = new Filter("numeroIdentificacion", valueFilter, null, CriteriaFilter.CONTAINS);
        fl.add(filter);
        fq.setFilterList(fl);
        List<Empleado> dbList = this.findWithFilter(fq);
        Map<String, Empleado> empMap = new HashMap<>();
        for (Empleado dbEmp : dbList) {
            empMap.put(dbEmp.getNumeroIdentificacion(), dbEmp);
        }

        List<Mensaje> listErrors = new ArrayList<>();
        for (Empleado emp : list) {
            try {
                Empleado empComun = empMap.get(emp.getNumeroIdentificacion());
                if (empComun == null) {
                    this.create(emp, empresaId);
                } else {
                    emp.setId(empComun.getId());
                    emp.setUsuario(empComun.getUsuario());
                    super.edit(emp);
                }
            } catch (Exception e) {
                Mensaje mu = new Mensaje("Error al procesar el empleado: " + emp.getNumeroIdentificacion(), e.getMessage(), TipoMensaje.error);
                listErrors.add(mu);
            }
        }
        return listErrors;
    }

    public Empleado update(Empleado empleado, Usuario usuario) {
        Empleado empDb = this.find(empleado.getId());
        if (empDb.getUsuario().getId().equals(usuario.getId())) {
            empDb.setPrimerNombre(empleado.getPrimerNombre());
            empDb.setSegundoNombre(empleado.getSegundoNombre());
            empDb.setPrimerApellido(empleado.getPrimerApellido());
            empDb.setSegundoApellido(empleado.getSegundoApellido());
            empDb.setTipoIdentificacion(empleado.getTipoIdentificacion());
            empDb.setNumeroIdentificacion(empleado.getNumeroIdentificacion());
            empDb.setDireccion(empleado.getDireccion());
            empDb.setTelefono1(empleado.getTelefono1());
            empDb.setTelefono2(empleado.getTelefono2());
            empDb.setFechaNacimiento(empleado.getFechaNacimiento());
            empDb.setCiudad(empleado.getCiudad());
            empDb.setGenero(empleado.getGenero());
            return empleado;
        } else {
            throw new UserMessageException("Operación no permitida", "No puede editar datos de empleado diferente al suyo", TipoMensaje.error);
        }
    }

}
