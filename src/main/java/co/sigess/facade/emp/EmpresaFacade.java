/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.sigess.facade.emp;

import co.sigess.entities.ado.Documento;
import co.sigess.facade.com.AbstractFacade;
import co.sigess.entities.emp.Empresa;
import co.sigess.entities.emp.Perfil;
import co.sigess.entities.emp.Permiso;
import co.sigess.entities.emp.Recurso;
import co.sigess.entities.emp.Sst;
import co.sigess.entities.emp.UsuarioEmpresa;
import co.sigess.entities.emp.UsuarioEmpresaPK;
import java.util.List;
import javax.ejb.EJB;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author fmoreno
 */
@javax.ejb.Stateless
public class EmpresaFacade extends AbstractFacade<Empresa> {

    @EJB
    private PermisoFacade permisoFacade;

    @EJB
    private PerfilFacade perfilFacade;

    @EJB
    private RecursoFacade recursoFacade;

    @EJB
    private UsuarioEmpresaFacade usuarioEmpresaFacade;

    @PersistenceContext(unitName = "SIGESS_PU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public EmpresaFacade() {
        super(Empresa.class);
    }

    public List<Empresa> findByUsuario(Integer usuarioId) {
        
        // Query query = this.em.createQuery("SELECT DISTINCT emp  FROM Empresa emp  JOIN emp.usuarioEmpresaList ue WHERE ue.usuario.id = :usuarioId ORDER BY emp.razonSocial");
        Query query = this.em.createQuery("SELECT DISTINCT emp FROM Empresa emp JOIN emp.usuarioEmpresaList ue WHERE ue.usuario.id = :usuarioId AND emp.tipoPersona IS NULL ORDER BY emp.razonSocial");
        query.setParameter("usuarioId", usuarioId);
        List<Empresa> emp = (List<Empresa>) query.getResultList();
        if(!emp.isEmpty()) return emp;
        
        query = this.em.createQuery("SELECT DISTINCT emp FROM Empresa emp JOIN emp.usuarioEmpresaList ue WHERE ue.usuario.id = :usuarioId AND emp.tipoPersona IS NOT NULL ORDER BY emp.razonSocial");
        query.setParameter("usuarioId", usuarioId);
        emp = (List<Empresa>) query.getResultList();
        // System.out.println(emp.toString());
        return emp;
    }
    
    public Empresa adicionar(Empresa empresa, Integer usuarioId) throws Exception {

        // Si el id de arl es null, la entidad arl debe cargarse como null
        if (empresa.getArl() != null && empresa.getArl().getId() == null) {
            empresa.setArl(null);
        }
        empresa = super.create(empresa);

        // Crea un perfil por defecto para la nueva empresa
        Perfil perfil = new Perfil();
        perfil.setNombre("Administrador");
        perfil.setDescripcion("Perfil administrador creado automáticamente");
        perfil.setEmpresa(empresa);
        perfil = perfilFacade.create(perfil);

        // Crea la relacion usuario-empresa y asigna perfil por defecto
        UsuarioEmpresa ue = new UsuarioEmpresa();
        ue.setUsuarioEmpresaPK(new UsuarioEmpresaPK(usuarioId, empresa.getId(), perfil.getId()));
        usuarioEmpresaFacade.create(ue);

        // Crea los permisos para el perfil del usuario que crea la empresa
        List<Recurso> recursosList = recursoFacade.findAll();
        for (Recurso recurso : recursosList) {
            Permiso permiso = new Permiso();
            permiso.setValido(true);
            permiso.setEmpresa(empresa);
            permiso.setPerfil(perfil);
            permiso.setRecurso(recurso);
            permisoFacade.create(permiso);
        }

        return empresa;
    }

    @Override
    public Empresa edit(Empresa emp) throws Exception {
        Empresa empresaDB = this.find(emp.getId());
        empresaDB.setRazonSocial(emp.getRazonSocial());
        empresaDB.setDireccion(emp.getDireccion());
        empresaDB.setTelefono(emp.getTelefono());
        empresaDB.setEmail(emp.getEmail());
        empresaDB.setWeb(emp.getWeb());
        empresaDB.setNumeroSedes(emp.getNumeroSedes());
        empresaDB.setNombreComercial(emp.getNombreComercial());
        empresaDB.setLogo(emp.getLogo());
        empresaDB.setTipo_persona(emp.getTipo_persona());
        empresaDB.setActividades_contratadas(emp.getActividades_contratadas());
        empresaDB.setLocalidad(emp.getLocalidad());
        empresaDB.setDivision(emp.getDivision());
        empresaDB.setFechaCreacion(emp.getFechaCreacion());
        empresaDB.setFechaActualizacion(emp.getFechaActualizacion());
        empresaDB.setCalificacion(emp.getCalificacion());
        empresaDB.setEstado(emp.getEstado());
        empresaDB.setVigencia(emp.getVigencia());
        empresaDB.setIdEmpresaAliada(emp.getIdEmpresaAliada());
        empresaDB.setActivo(emp.getActivo());
        empresaDB.setCorreoAliadoCreador(emp.getCorreoAliadoCreador());


        if (emp.getArl() != null && emp.getArl().getId() == null) {
            empresaDB.setArl(null);
        } else {
            empresaDB.setArl(emp.getArl());
        }
        if (emp.getCiiu() != null && emp.getCiiu().getId() == null) {
            empresaDB.setCiiu(null);
        } else {
            empresaDB.setCiiu(emp.getCiiu());
        }
        empresaDB.setNit(emp.getNit());
        return super.edit(empresaDB); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void remove(Empresa empresa) throws Exception {
        super.remove(empresa); //To change body of generated methods, choose Tools | Templates.
    }

    public boolean vincularContratista(Integer empresaId, Empresa contratista) throws Exception {
        //Query
        Empresa empresa = this.find(empresaId);
        boolean vinculado = false;
        if (empresa.getEmpresasContratistasList().contains(contratista)) {
            empresa.getEmpresasContratistasList().remove(contratista);
        } else {
            empresa.getEmpresasContratistasList().add(contratista);
            vinculado = true;
        }
        super.edit(empresa);
        return vinculado;
    }
    
    public void relacionarDocumento(Documento documento, Integer empresaId) throws Exception{
        Empresa emp = this.find(empresaId);
        emp.getDocumentosList().add(documento);
        super.edit(emp);
    }
    
    public boolean eliminarDocumento(Documento documento) throws Exception{
        boolean eliminado = false;
        try{
            Query q = this.em.createQuery("SELECT e FROM Empresa e INNER JOIN e.documentosList d WHERE d.id = ?1");
            q.setParameter(1, documento.getId());
            Empresa emp = (Empresa) q.getSingleResult();
            eliminado = emp.getDocumentosList().remove(documento);
            super.edit(emp);
        } catch(Exception ex){
            throw ex;
        }
        return eliminado;
    }
    
    public Empresa findEmpresaById(Integer empresaId) throws Exception{
        Query q = this.em.createQuery("SELECT e FROM Empresa e WHERE e.id = ?1");
        q.setParameter(1, empresaId);
        Empresa empresa = (Empresa)q.getSingleResult();
        return empresa;
    }
    
    public List<Empresa> findEmpresaByNit(String nit) throws Exception {
        Query q = this.em.createQuery("SELECT e FROM Empresa e WHERE e.nit = ?1");
        q.setParameter(1, nit);
        List<Empresa> empresa = (List<Empresa>) q.getResultList();
        return empresa;
    }

}
