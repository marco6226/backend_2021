/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.sigess.facade.ado;

import co.sigess.entities.ado.Directorio;
import co.sigess.entities.ado.Documento;
import co.sigess.entities.ado.Modulo;
import co.sigess.entities.com.TipoMensaje;
import co.sigess.entities.inp.Calificacion;
import co.sigess.exceptions.UserMessageException;
import co.sigess.facade.auc.ObservacionFacade;
import co.sigess.facade.com.AbstractFacade;
import co.sigess.facade.cop.ActaFacade;
import co.sigess.facade.emp.EmpleadoFacade;
import co.sigess.facade.emp.EmpresaFacade;
import co.sigess.facade.inp.CalificacionFacade;
import co.sigess.facade.sec.AnalisisDesviacionFacade;
import co.sigess.facade.inp.ListaInspeccionFacade;
import co.sigess.util.FileUtil;
import java.io.File;
import java.io.OutputStream;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
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
public class DirectorioFacade extends AbstractFacade<Directorio> {

    @PersistenceContext(unitName = "SIGESS_PU")
    private EntityManager em;

    @EJB
    private DocumentoFacade documentoFacade;

    @EJB
    private EmpleadoFacade empleadoFacade;

    @EJB
    private CalificacionFacade calificacionFacade;

    @EJB
    private AnalisisDesviacionFacade analisisDesviacionFacade;

    @EJB
    private ObservacionFacade observacionFacade;
    
    @EJB
    private ActaFacade actaFacade;
    
    @EJB
    private ListaInspeccionFacade ListaInspeccionFacade;
    
    @EJB
    private EmpresaFacade empresaFacade;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public DirectorioFacade() {
        super(Directorio.class);
    }

    public List<Directorio> findAllByUsuarioEmpresa(Integer empresaId, Integer usuarioId) {
        Query q = this.em.createQuery("SELECT dir FROM Directorio dir LEFT JOIN dir.permisoDirectorioList pd WHERE dir.eliminado = false AND dir.directorioPadre IS NULL AND dir.empresa.id = ?1 AND dir.usuario.id = ?2 OR (pd.publico = true OR pd.usuario.id = ?2)");
        q.setParameter(1, empresaId);
        q.setParameter(2, usuarioId);
        List<Directorio> list = (List<Directorio>) q.getResultList();
        return list;
    }

    @Override
    public Directorio create(Directorio entity) throws Exception {
        return this.create(entity, null);
    }

    public Directorio create(Directorio directorio, String modParam) throws Exception {
        directorio.setFechaCreacion(new Date());
        Documento documento = directorio.getDocumento();
        if (documento == null) {
            directorio.setEsDocumento(false);
            super.create(directorio);
        } else {
            directorio.setDocumento(null);
            super.create(directorio);
            documento.setId(directorio.getId());
            documento.setDirectorio(directorio);
            documentoFacade.create(documento);
            directorio.setDocumento(documento);

            switch (documento.getModulo()) {
                case EMP:
                    Integer empleadoId = Integer.valueOf(modParam);
                    this.empleadoFacade.relacionarDocumento(documento, empleadoId);
                    break;
                case INP:
                    Long calificacionId = Long.valueOf(modParam);
                    this.calificacionFacade.relacionarDocumento(documento, calificacionId);
                    break;
                case SEC:
                    Integer analisisId = Integer.valueOf(modParam);
                    this.analisisDesviacionFacade.relacionarDocumento(documento, analisisId);
                    break;
                case AUC:
                    Long observacionId = Long.valueOf(modParam);
                    this.observacionFacade.relacionarDocumento(documento, observacionId);
                    break;
                case COP:
                    Long actaId = Long.valueOf(modParam);
                    this.actaFacade.relacionarDocumento(documento, actaId);
                    break;
                default:
                    break;
            }

        }
        directorio.setEliminado(Boolean.FALSE);
        return directorio;
    }
    
    public Directorio create(Directorio directorio, String modParam, String tipoEvidencias) throws Exception {
        directorio.setFechaCreacion(new Date());
        Documento documento = directorio.getDocumento();
        if (documento == null) {
            directorio.setEsDocumento(false);
            super.create(directorio);
        } else {
            directorio.setDocumento(null);
            super.create(directorio);
            documento.setId(directorio.getId());
            documento.setDirectorio(directorio);
            documentoFacade.create(documento);
            directorio.setDocumento(documento);

            switch (documento.getModulo()) {
                case EMP:
                    Integer empleadoId = Integer.valueOf(modParam);
                    this.empleadoFacade.relacionarDocumento(documento, empleadoId);
                    break;
                case INP:
                    Long calificacionId = Long.valueOf(modParam);
                    this.calificacionFacade.relacionarDocumento(documento, calificacionId);
                    break;
                case SEC:
                    Integer analisisId = Integer.valueOf(modParam);                    
                    this.analisisDesviacionFacade.relacionarDocumentoEvidencia(documento, analisisId, tipoEvidencias);
                    break;
                case AUC:
                    Long observacionId = Long.valueOf(modParam);
                    this.observacionFacade.relacionarDocumento(documento, observacionId);
                    break;
                case COP:
                    Long actaId = Long.valueOf(modParam);
                    this.actaFacade.relacionarDocumento(documento, actaId);
                    break;
                case EMPRESA:
                    Integer empresaId = Integer.valueOf(modParam);
                    this.empresaFacade.relacionarDocumento(documento, empresaId);
                    break;
                default:
                    break;
            }

        }
        directorio.setEliminado(Boolean.FALSE);
        return directorio;
    }

     public void create(Integer documentoId, Integer  modParam, Integer version) throws Exception {
        
        this.ListaInspeccionFacade.guardarImagen(documentoId, modParam, version);
             
    }
     
     public void update(Integer documentoId, Integer  modParam, Integer version) throws Exception {
        
        this.ListaInspeccionFacade.actualizarImagen(documentoId, modParam, version);
             
    }
     
     public void create(Integer documentoId, Integer  paramId, String modulo) throws Exception {
        
         switch(modulo){
            case "EMP":
                this.empleadoFacade.guardarImagen(documentoId, paramId);
                break;
            default:
                break;
         }
        
             
    }
     
    public OutputStream findFile(Long documentoId) {
        Documento documento = documentoFacade.find(documentoId);
        if (documento == null) {
            throw new IllegalArgumentException("parámetro id no válido");
        }

        OutputStream file;
        try {
            file = FileUtil.getFromVirtualFS(documento.getRuta());
        } catch (Exception ex) {
            Logger.getLogger(DirectorioFacade.class.getName()).log(Level.SEVERE, null, ex);
            throw new UserMessageException("ERROR AL SOLICITAR DOCUMENTO", "Error de archivo grave: FNTE-" + documento.getId(), TipoMensaje.error);
        }
        return file;
    }

    public OutputStream encontrarDocumentoModulo(Long documentoId, Modulo mod, Integer empresaId) {
        Query q = this.em.createQuery("SELECT doc.ruta FROM Documento doc WHERE doc.id = ?1 AND doc.modulo = ?2 AND doc.directorio.empresa.id = ?3");
        q.setParameter(1, documentoId);
        q.setParameter(2, mod);
        q.setParameter(3, empresaId);
        String ruta = q.getResultList().isEmpty() ? null : (String) q.getResultList().get(0);
        if (ruta == null) {
            throw new UserMessageException("ARCHIVO NO ENCONTRADO", "El archivo que intenta descargar ya no se encuentra disponible", TipoMensaje.warn);
        }

        OutputStream file;
        try {
            file = FileUtil.getFromVirtualFS(ruta);
        } catch (Exception ex) {
            Logger.getLogger(DirectorioFacade.class.getName()).log(Level.SEVERE, null, ex);
            throw new UserMessageException("ERROR AL SOLICITAR DOCUMENTO", "Error de archivo grave: FNTE-" + documentoId, TipoMensaje.error);
        }
        return file;
    }

    @Override
    public Directorio edit(Directorio directorio) throws Exception {
        if (directorio == null || directorio.getId() == null) {
            throw new IllegalArgumentException("directorio a actualizar inválido");
        }
        Directorio dirDB = this.find(directorio.getId());
        dirDB.setNombre(directorio.getNombre());
        dirDB.setDirectorioPadre(directorio.getDirectorioPadre());
        dirDB.setNivelAcceso(directorio.getNivelAcceso());
        dirDB.setPerfilId(directorio.getPerfilId());
        return dirDB;
    }

    public List<Directorio> findByParent(Long directorioId) {
        Query q = this.em.createQuery("SELECT dir FROM Directorio dir WHERE dir.directorioPadre.id = ?1 AND dir.eliminado = false");
        q.setParameter(1, directorioId);
        List<Directorio> list = (List<Directorio>) q.getResultList();
        return list;
    }

    public Directorio eliminar(Long directorioId) throws Exception {
        Directorio dirDB = this.find(directorioId);
        dirDB.setEliminado(Boolean.TRUE);
        super.edit(dirDB);
        return dirDB;
    }

    public List<Directorio> buscarDocumentos(Integer empresaId, Integer usuarioId, String parametro) {
        Query q = this.em.createQuery("SELECT dir FROM Directorio dir LEFT JOIN dir.permisoDirectorioList pd WHERE LOWER(dir.nombre) LIKE ?3 AND dir.esDocumento = true AND dir.eliminado = false AND dir.empresa.id = ?1 AND dir.usuario.id = ?2 OR (pd.publico = true OR pd.usuario.id = ?2)");
        q.setParameter(1, empresaId);
        q.setParameter(2, usuarioId);
        q.setParameter(3, "%" + parametro.toLowerCase() + "%");
        List<Directorio> list = (List<Directorio>) q.getResultList();
        return list;
    }
    
    public List<Directorio> buscarDocumentosById(Integer Id) {
        Query q = this.em.createQuery("SELECT dir FROM Directorio dir WHERE dir.id = ?1");
        q.setParameter(1, Id);
        List<Directorio> list = (List<Directorio>) q.getResultList();
        return list;
    }

    public void eliminarDocumentos(String modulo, String modParam) throws Exception {
        switch (Modulo.valueOf(modulo)) {
            case EMP:

                break;
            case INP:
                Calificacion calificacion = calificacionFacade.find(Long.valueOf(modParam));
                for (int i = 0; i < calificacion.getDocumentosList().size(); i++) {
                    Documento doc = calificacion.getDocumentosList().get(i);
                    documentoFacade.remove(doc);
                    i--;
                }
                calificacion.setDocumentosList(null);
                calificacionFacade.edit(calificacion);
                break;
        }
    }

    /**
     * Realiza la validación de los parámetros recibidos al subir un nuevo
     * documento, y genera excepcion de resultar inválidos los mismos
     *
     * @param modulo
     * @param modParam
     */
    public void validarParametrosUpload(String modulo, String modParam) {
        if (modulo == null) {
            throw new IllegalArgumentException("No se ha especificado el módulo del documento");
        }
        Modulo mod;
        try {
            mod = Modulo.valueOf(modulo);
        } catch (Exception e) {
            throw new IllegalArgumentException("El módulo especificado no es válido");
        }

        switch (mod) {
            case EMP:
            case AUC:
            case SEC:
            case INP:
            case COP:
                if (modParam == null) {
                    throw new IllegalArgumentException("Se ha intentado crear un documento pero no se ha especificado el parametro");
                }
                try {
                    Integer id = Integer.valueOf(modParam);
                } catch (NumberFormatException e) {
                    throw new IllegalArgumentException("Se ha intentado crear un documento con identificador no válido");
                }
                break;
        }

    }
    public void actualizarModuloDir(){
        Query q = this.em.createNativeQuery("SELECT ado.actualizarmodulodirectorio()");
        q.getResultList();
    }
}
