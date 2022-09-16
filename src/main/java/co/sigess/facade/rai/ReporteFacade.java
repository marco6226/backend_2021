/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.sigess.facade.rai;

import co.sigess.entities.com.TipoMensaje;
import co.sigess.entities.emp.Empleado;
import co.sigess.entities.emp.Empresa;
import co.sigess.entities.emp.Usuario;
import co.sigess.entities.rai.EsquemaCarga;
import co.sigess.entities.rai.Reporte;
import co.sigess.entities.rai.TestigoReporte;
import co.sigess.exceptions.UserMessageException;
import co.sigess.facade.com.AbstractFacade;
import co.sigess.facade.emp.EmpleadoFacade;
import co.sigess.facade.emp.EmpresaFacade;
import co.sigess.facade.emp.UsuarioFacade;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;


/**
 *
 * @author fmoreno
 */
@Stateless
public class ReporteFacade extends AbstractFacade<Reporte> {

    private static int empresaId;
    
    private static int empresaactualid;

    @PersistenceContext(unitName = "SIGESS_PU")
    private EntityManager em;

    @EJB
    private EmpleadoFacade empleadoFacade;

    @EJB
    private EmpresaFacade empresaFacade;

    @EJB
    private UsuarioFacade usuarioFacade;

    @EJB
    private TestigoReporteFacade testigoReporteFacade;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public ReporteFacade() {
        super(Reporte.class);
    }

    public Reporte inicializarReporte(Integer empleadoId, Integer empresaId, Integer usuarioId) {
        Empleado empleado = this.empleadoFacade.find(empleadoId);

        if (empleado == null) {
            throw new IllegalArgumentException("Id de empleado inválido");
        }
        Empresa empresa = empresaFacade.find(empresaId);
        if (empresa == null) {
            throw new IllegalArgumentException("Id de empresa inválido");
        }
        Usuario usuario = usuarioFacade.find(usuarioId);
        if (usuario == null) {
            throw new IllegalArgumentException("Id de usuario inválido");
        }
        Reporte reporte = new Reporte();
        reporte.setCargoEmpleado(empleado.getCargo().getNombre());
        reporte.setCiudadEmpleado(empleado.getCiudad());
        reporte.setCodigoAfp(empleado.getAfp() == null ? null : empleado.getAfp().getCodigo());
        reporte.setCodigoArl(empresa.getArl() == null ? null : empresa.getArl().getCodigo());
        reporte.setCodigoEps(empleado.getEps() == null ? null : empleado.getEps().getCodigo());
        reporte.setNombreAfp(empleado.getAfp() == null ? null : empleado.getAfp().getNombre());
        reporte.setNombreArl(empresa.getArl() == null ? null : empresa.getArl().getNombre());
        reporte.setNombreEps(empleado.getEps() == null ? null : empleado.getEps().getNombre());
        reporte.setTipoVinculacion(empleado.getTipoVinculacion());
        reporte.setZonaEmpleado(empleado.getZonaResidencia());
        reporte.setDireccionEmpleado(empleado.getDireccion());
        reporte.setFechaIngresoEmpleado(empleado.getFechaIngreso());
        reporte.setFechaNacimientoEmpleado(empleado.getFechaNacimiento());
        reporte.setGeneroEmpleado(empleado.getGenero());
        reporte.setTipoIdentificacionEmpleado(empleado.getTipoIdentificacion().name());
        reporte.setNumeroIdentificacionEmpleado(empleado.getNumeroIdentificacion());
        reporte.setPrimerApellidoEmpleado(empleado.getPrimerApellido());
        reporte.setPrimerNombreEmpleado(empleado.getPrimerNombre());
        reporte.setSegundoApellidoEmpleado(empleado.getSegundoApellido());
        reporte.setSegundoNombreEmpleado(empleado.getSegundoNombre());
        reporte.setTelefonoEmpleado(empleado.getTelefono1());
        reporte.setTelefono2Empleado(empleado.getTelefono2());
        reporte.setTipoIdentificacionEmpresa("NI");
        if (empresa.getId() == 22) {
            reporte.setIdentificacionEmpresa(empleado.getNit());
            reporte.setRazonSocial(empleado.getEmpresa());
            }
        else {
            
             reporte.setIdentificacionEmpresa(empresa.getNit());
             reporte.setRazonSocial(empresa.getRazonSocial());
        }
            
        reporte.setFechaReporte(new Date());

        Empleado empleadoReporta = this.empleadoFacade.findByUsuario(usuarioId);
        if (empleadoReporta != null) {
            reporte.setNombresResponsable(empleadoReporta.getPrimerNombre() + " " + ((empleadoReporta.getSegundoNombre()!=null)?empleadoReporta.getSegundoNombre():""));
            reporte.setApellidosResponsable(empleadoReporta.getPrimerApellido() + " " + ((empleadoReporta.getSegundoApellido()!=null)?empleadoReporta.getSegundoApellido():""));
            reporte.setCargoResponsable(empleadoReporta.getCargo().getNombre());
            reporte.setTipoIdentificacionResponsable(empleadoReporta.getTipoIdentificacion().name());
            reporte.setNumeroIdentificacionResponsable(empleadoReporta.getNumeroIdentificacion());
        }
        return reporte;
    }

    @Override
    public Reporte create(Reporte reporte) throws Exception {
        
        if( reporte.getEmpresa().getId() ==3){
        Reporte num_furat = this.findByfurat(reporte.getNumero_furat());
        if (num_furat != null) {
            //System.out.println("INGRESANDO SI SEÑOR");
            throw new UserMessageException(
                    "REPORTE YA REGISTRADO",
                    "el numero de  FURAT "
                    + reporte.getNumero_furat()
                    + " ya se encuentra registrado, corrija los datos e intente cargar nuevamente el archivo",
                    TipoMensaje.warn
            );
        }
        }
        if (reporte.getTipo() == null) {
            throw new IllegalArgumentException("Debe establecer el tipo de reporte");
        }
        if (reporte.getTestigoReporteList() != null) {
            for (TestigoReporte tr : reporte.getTestigoReporteList()) {
                tr.setReporte(reporte);
                this.testigoReporteFacade.create(tr);
            }
        }
        return super.create(reporte);
    }

    @Override
    public Reporte edit(Reporte reporte) throws Exception {
        if (reporte.getId() == null) {
            throw new IllegalArgumentException("Reporte no válido para actualizar: Incorrect param");
        }
        Reporte repDb = this.find(reporte.getId());
        if (repDb == null) {
            throw new IllegalArgumentException("Reporte no válido para actualizar: null");
        }
        if (reporte.getTestigoReporteList() == null || reporte.getTestigoReporteList().isEmpty()) {
            for (TestigoReporte testigo : repDb.getTestigoReporteList()) {
                testigoReporteFacade.remove(testigo);
            }
        } else {
            Collection<TestigoReporte> testigosEliminarList = CollectionUtils.disjunction(reporte.getTestigoReporteList(), repDb.getTestigoReporteList());
            System.out.println(testigosEliminarList);
            for (TestigoReporte tr : reporte.getTestigoReporteList()) {
                tr.setReporte(reporte);
                if (tr.getId() == null) {
                    this.testigoReporteFacade.create(tr);
                    boolean eliminado = testigosEliminarList.remove(tr);
                    System.out.println(eliminado);
                } else if (!testigosEliminarList.contains(tr)) {
                    this.testigoReporteFacade.edit(tr);
                }
            }
            for (TestigoReporte tr : testigosEliminarList) {
                if (tr.getId() != null) {
                    this.testigoReporteFacade.remove(tr);
                }
            }
        }

        return super.edit(reporte);
    }

    public void cargarArchivo(InputStream fileInputStream, String tipoReporte, Integer empresaId, Integer usuarioId)
            throws IOException, NoSuchFieldException, IllegalArgumentException, IllegalAccessException, ParseException, Exception {
        Query q = this.em.createQuery("SELECT esq From EsquemaCarga esq WHERE esq.empresaId = ?1");
        q.setParameter(1, empresaId);
        empresaactualid  = empresaId;
        List<EsquemaCarga> list = q.getResultList();
        if (list == null || list.isEmpty()) {
            throw new UserMessageException("CONFIGURACIÓN NO ENCONTRADA", "No se encontró el esquema de carga masiva para el reporte AT", TipoMensaje.error);
        }

        Date fechaActual = new Date();
            Workbook  workbook = new HSSFWorkbook(fileInputStream);
        Sheet datatypeSheet = workbook.getSheetAt(0);

        for (int i = 1;; i++) {
            Row currentRow = datatypeSheet.getRow(i);
            if (checkIfRowIsEmpty(currentRow)) {
                break;
            }
            Reporte reporte = new Reporte();
            reporte.setMigrado(true);
            reporte.setEmpresa(new Empresa(empresaId));
            reporte.setUsuarioReporta(new Usuario(usuarioId));
            reporte.setTipo(tipoReporte);

            for (EsquemaCarga esquema : list) {
                Cell cell = currentRow.getCell(esquema.getPosicionColumna());
                Object valor = null;

                switch (cell.getCellTypeEnum()) {
                    case STRING:
                        valor = cell.getStringCellValue().trim();
                        break;
                    case BOOLEAN:
                        valor = cell.getBooleanCellValue();
                        break;
                    case NUMERIC:
                        valor = cell.getNumericCellValue();
                        break;
                }
                if (valor == null) {
                    continue;
                }
                Field campo = Reporte.class.getDeclaredField(esquema.getCampoEntidad());
                campo.setAccessible(true);

                switch (esquema.getTipoDatoLeido()) {
                    case "string":
                        if (cell.getCellTypeEnum().equals(CellType.STRING)) {
                            campo.set(reporte, valor);
                        } else {
                            campo.set(reporte, String.format("%.0f", valor));
                        }
                        break;
                    case "date":
                    case "time":
                        if (cell.getCellTypeEnum().equals(CellType.STRING)) {
                            SimpleDateFormat sdf = new SimpleDateFormat(esquema.getFormatoCampoLeido());
                            campo.set(reporte, sdf.parse((String) valor));
                        } else if (cell.getCellTypeEnum().equals(CellType.NUMERIC)) {
                            campo.set(reporte, DateUtil.getJavaDate((double) valor));
                        } else {
                            // Adicionar mensaje de error
                            System.out.println("Error: " + valor);
                        }
                        break;
                    case "integer":
                        if (cell.getCellTypeEnum().equals(CellType.STRING)) {
                            campo.set(reporte, Integer.parseInt((String) valor));
                        } else if (cell.getCellTypeEnum().equals(CellType.NUMERIC)) {
                            campo.set(reporte, ((Double) valor).intValue());
                        } else {
                            Integer val = ((boolean) valor) ? 1 : 0;
                            campo.set(reporte, val);
                        }
                        break;
                }

            }
            if (reporte.getFechaReporte() == null) {
                reporte.setFechaReporte(fechaActual);
            }
            this.create(reporte);
        }
    }

    private boolean checkIfRowIsEmpty(Row row) {
        if (row == null) {
            return true;
        }
        if (row.getLastCellNum() <= 0) {
            return true;
        }
        for (int cellNum = row.getFirstCellNum(); cellNum < row.getLastCellNum(); cellNum++) {
            Cell cell = row.getCell(cellNum);
            if (cell != null && cell.getCellTypeEnum() != CellType.BLANK) {
                return false;
            }
        }
        return true;
    }
    
    public Reporte findByfurat(String furat) {
        Query query = em.createQuery("SELECT u from Reporte u where u.numero_furat = :furat");
        query.setParameter("furat", furat);
        try {
            Reporte reporte = (Reporte) query.getSingleResult();
            return reporte;
        } catch (Exception ejbExc) {
            return null;
        }
    }

}
