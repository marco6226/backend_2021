/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.sigess.facade.ado;

import co.sigess.entities.ado.Directorio;
import co.sigess.entities.ado.Documento;
import co.sigess.entities.com.TipoMensaje;
import co.sigess.exceptions.UserMessageException;
import co.sigess.facade.com.AbstractFacade;
import co.sigess.facade.emp.EmpleadoFacade;
import co.sigess.facade.emp.EmpresaFacade;
import co.sigess.facade.inp.CalificacionFacade;
import co.sigess.facade.sec.AnalisisDesviacionFacade;
import co.sigess.util.FileUtil;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author fmoreno
 */
@Stateless
public class DocumentoFacade extends AbstractFacade<Documento> {

    @PersistenceContext(unitName = "SIGESS_PU")
    private EntityManager em;

    @EJB
    private EmpleadoFacade empleadoFacade;

    @EJB
    private CalificacionFacade calificacionFacade;

    @EJB
    private DirectorioFacade directorioFacade;
    
    @EJB
    private AnalisisDesviacionFacade anDesvFacade;
    
    @EJB
    private EmpresaFacade empresaFacade;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public DocumentoFacade() {
        super(Documento.class);
    }

    @Override
    public Documento edit(Documento documento) throws Exception {
        if (documento == null || documento.getId() == null) {
            throw new IllegalArgumentException("Documento a actualizar inválido");
        }
        Documento docDB = this.find(documento.getId());
        if (docDB == null) {
            throw new IllegalArgumentException("No se ha podido finalizar el proceso debido a una inconsistencia: INC-1-" + documento.getId());
        }
        if (documento.getId() == null) {
            throw new IllegalArgumentException("No se ha podido finalizar el proceso debido a inconsistencias en el documento: INC-2-" + documento.getId());
        }
        if (!documento.getId().equals(docDB.getId())) {
            throw new IllegalArgumentException("No se ha podido finalizar el proceso debido a inconsistencias en el documento: INC-3-" + documento.getId());
        }
        docDB.setNombre(documento.getNombre());
        docDB.setApellidosAprobador(documento.getApellidosAprobador());
        docDB.setApellidosElaborador(documento.getApellidosElaborador());
        docDB.setApellidosVerificador(documento.getApellidosVerificador());
        docDB.setCodigo(documento.getCodigo());
        docDB.setDescripcion(documento.getDescripcion());
        docDB.setFechaAprobacion(documento.getFechaAprobacion());
        docDB.setFechaElaboracion(documento.getFechaElaboracion());
        docDB.setFechaVerificacion(documento.getFechaVerificacion());
        docDB.setIdentificacionAprobador(documento.getIdentificacionAprobador());
        docDB.setIdentificacionElaborador(documento.getIdentificacionElaborador());
        docDB.setIdentificacionVerificador(documento.getIdentificacionVerificador());
        docDB.setNombre(documento.getNombre());
        docDB.setNombresAprobador(documento.getNombresAprobador());
        docDB.setNombresElaborador(documento.getNombresElaborador());
        docDB.setNombresVerificador(documento.getNombresVerificador());
        docDB.setProceso(documento.getProceso());
        docDB.setUbicacionFisica(documento.getUbicacionFisica());
        docDB.setVersion(documento.getVersion());

        super.edit(docDB);

        return docDB;
    }

    @Override
    public void remove(Documento documento) throws Exception {
        switch (documento.getModulo()) {
            case EMP:
                try {
                    this.empleadoFacade.retirarDocumento(documento);
                } catch (Exception ex) {
                    throw new UserMessageException("Operación no realizada", "No fue posible desligar el documento del empleado", TipoMensaje.error);
                }
                break;
            case INP:
                try {
                    this.calificacionFacade.retirarDocumento(documento);
                } catch (Exception ex) {
                    throw new UserMessageException("Operación no realizada", "No fue posible desligar el documento de la calificación de la inspección", TipoMensaje.error);
                }
                break;
            case SEC:
                try {
                    this.anDesvFacade.retirarDocumento(documento);
                } catch (Exception ex) {
                    throw new UserMessageException("Operación no realizada", "No fue posible desligar el documento de la calificación de la inspección", TipoMensaje.error);
                }
                break;
            case EMPRESA:
                try{
                    this.empresaFacade.eliminarDocumento(documento);
                } catch (Exception ex) {
                    throw new UserMessageException("Operación no realizada", "No fue posible eliminar el soporte", TipoMensaje.error);
                }
                break;
            default:
                break;
        }
        Directorio dir = documento.getDirectorio();
        documento.setDirectorio(null);
        dir.setDocumento(null);
        super.remove(documento);
        this.directorioFacade.remove(dir);
        FileUtil.removeFromVirtualFS(documento.getRuta());
    }

}
