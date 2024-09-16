/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.sigess.restful;

import java.util.Set;
import javax.ws.rs.core.Application;
import org.glassfish.jersey.media.multipart.MultiPartFeature;

/**
 *
 * @author milan
 */
@javax.ws.rs.ApplicationPath("api")
public class ApplicationConfig extends Application {

    @Override
    public Set<Class<?>> getClasses() {
        Set<Class<?>> resources = new java.util.HashSet<>();
        resources.add(org.glassfish.jersey.jackson.JacksonFeature.class);
        resources.add(MultiPartFeature.class);

        addRestResourceClasses(resources);
        return resources;
    }

    /**
     * Do not modify addRestResourceClasses() method.
     * It is automatically populated with
     * all resources defined in the project.
     * If required, comment out calling this method in getClasses().
     */
    private void addRestResourceClasses(Set<Class<?>> resources) {
        resources.add(co.sigess.restful.ContactoREST.class);
        resources.add(co.sigess.restful.ado.DirectorioREST.class);
        resources.add(co.sigess.restful.auc.ObservacionREST.class);
        resources.add(co.sigess.restful.auc.TarjetaREST.class);
        resources.add(co.sigess.restful.aus.CausaAusentismoREST.class);
        resources.add(co.sigess.restful.aus.ReporteAusentismoREST.class);
        resources.add(co.sigess.restful.com.AfpREST.class);
        resources.add(co.sigess.restful.com.ArlREST.class);
        resources.add(co.sigess.restful.com.CcfREST.class);
        resources.add(co.sigess.restful.com.CieREST.class);
        resources.add(co.sigess.restful.com.CiiuREST.class);
        resources.add(co.sigess.restful.com.CiudadREST.class);
        resources.add(co.sigess.restful.com.DepartamentoREST.class);
        resources.add(co.sigess.restful.com.EnumeracionesREST.class);
        resources.add(co.sigess.restful.com.EpsREST.class);
        resources.add(co.sigess.restful.com.JuntaRegionalREST.class);
        resources.add(co.sigess.restful.com.PaisREST.class);
        resources.add(co.sigess.restful.com.PrepagadasREST.class);
        resources.add(co.sigess.restful.com.ProvedorSaludREST.class);
        resources.add(co.sigess.restful.conf.ConfiguracionGeneralREST.class);
        resources.add(co.sigess.restful.conf.ManualREST.class);
        resources.add(co.sigess.restful.conf.OfflineServiceREST.class);
        resources.add(co.sigess.restful.conf.SistemaNivelRiesgoREST.class);
        resources.add(co.sigess.restful.cop.ActaREST.class);
        resources.add(co.sigess.restful.emp.AreaREST.class);
        resources.add(co.sigess.restful.emp.AuthenticationREST.class);
        resources.add(co.sigess.restful.emp.CargoActualREST.class);
        resources.add(co.sigess.restful.emp.CargoREST.class);
        resources.add(co.sigess.restful.emp.CompetenciaREST.class);
        resources.add(co.sigess.restful.emp.ConfiguracionJornadaREST.class);
        resources.add(co.sigess.restful.emp.ContextoOrganizacionREST.class);
        resources.add(co.sigess.restful.emp.EmpleadoREST.class);
        resources.add(co.sigess.restful.emp.EmpresaREST.class);
        resources.add(co.sigess.restful.emp.EvaluacionDesempenoREST.class);
        resources.add(co.sigess.restful.emp.GrupoEmpresarialREST.class);
        resources.add(co.sigess.restful.emp.HhtIliREST.class);
        resources.add(co.sigess.restful.emp.HhtREST.class);
        resources.add(co.sigess.restful.emp.HorasExtraREST.class);
        resources.add(co.sigess.restful.emp.PerfilREST.class);
        resources.add(co.sigess.restful.emp.PermisoREST.class);
        resources.add(co.sigess.restful.emp.PlantasREST.class);
        resources.add(co.sigess.restful.emp.RecursoREST.class);
        resources.add(co.sigess.restful.emp.TipoAreaREST.class);
        resources.add(co.sigess.restful.emp.UsuarioREST.class);
        resources.add(co.sigess.restful.emp.UtilsREST.class);
        resources.add(co.sigess.restful.emp.firmaREST.class);
        resources.add(co.sigess.restful.emp.sfirmaREST.class);
        resources.add(co.sigess.restful.exceptions.InternalServerErrorMapper.class);
        resources.add(co.sigess.restful.exceptions.NotFoundExceptionMapper.class);
        resources.add(co.sigess.restful.ind.IndicadorAusentismoREST.class);
        resources.add(co.sigess.restful.ind.IndicadorEmpresaREST.class);
        resources.add(co.sigess.restful.ind.IndicadorInpREST.class);
        resources.add(co.sigess.restful.ind.IndicadorREST.class);
        resources.add(co.sigess.restful.ind.IndicadorRaiREST.class);
        resources.add(co.sigess.restful.ind.IndicadorSgeREST.class);
        resources.add(co.sigess.restful.ind.LogIndicadoresRest.class);
        resources.add(co.sigess.restful.ind.MetaREST.class);
        resources.add(co.sigess.restful.ind.ReporteATViewREST.class);
        resources.add(co.sigess.restful.ind.TableroREST.class);
        resources.add(co.sigess.restful.ind.caracterizacionviewREST.class);
        resources.add(co.sigess.restful.ind.viewhhtmetasREST.class);
        resources.add(co.sigess.restful.ind.vwscmcorporativoREST.class);
        resources.add(co.sigess.restful.ind.vwscmgestionREST.class);
        resources.add(co.sigess.restful.inp.CumplimientoREST.class);
        resources.add(co.sigess.restful.inp.InspeccionREST.class);
        resources.add(co.sigess.restful.inp.ListaInspeccionREST.class);
        resources.add(co.sigess.restful.inp.ProgramacionREST.class);
        resources.add(co.sigess.restful.inp.TipoHallazgoREST.class);
        resources.add(co.sigess.restful.inp.ViewInspeccionCtrREST.class);
        resources.add(co.sigess.restful.inp.ViewInspeccionPerfilREST.class);
        resources.add(co.sigess.restful.inp.ViewListaInspeccionPerfilREST.class);
        resources.add(co.sigess.restful.inp.ViewResumenAliadosREST.class);
        resources.add(co.sigess.restful.ipr.AreaMatrizREST.class);
        resources.add(co.sigess.restful.ipr.ControlREST.class);
        resources.add(co.sigess.restful.ipr.EfectoREST.class);
        resources.add(co.sigess.restful.ipr.FuenteREST.class);
        resources.add(co.sigess.restful.ipr.IpecrREST.class);
        resources.add(co.sigess.restful.ipr.MatrizPeligrosLogREST.class);
        resources.add(co.sigess.restful.ipr.MatrizPeligrosREST.class);
        resources.add(co.sigess.restful.ipr.ParticipanteIpecrREST.class);
        resources.add(co.sigess.restful.ipr.PeligroIpecrREST.class);
        resources.add(co.sigess.restful.ipr.PeligroREST.class);
        resources.add(co.sigess.restful.ipr.ProcesoMatrizREST.class);
        resources.add(co.sigess.restful.ipr.SubprocesoMatrizREST.class);
        resources.add(co.sigess.restful.ipr.TipoControlREST.class);
        resources.add(co.sigess.restful.ipr.TipoPeligroREST.class);
        resources.add(co.sigess.restful.ipr.ViewMatrizPeligrosLogREST.class);
        resources.add(co.sigess.restful.ipr.ViewMatrizPeligrosREST.class);
        resources.add(co.sigess.restful.rai.ReporteREST.class);
        resources.add(co.sigess.restful.scm.AnexosREST.class);
        resources.add(co.sigess.restful.scm.CasoMedicoREST.class);
        resources.add(co.sigess.restful.scm.viewscmREST.class);
        resources.add(co.sigess.restful.scm.GenerateSignaturelinkControllerRest.class);
        resources.add(co.sigess.restful.sec.AnalisisDesviacionREST.class);
        resources.add(co.sigess.restful.sec.CorreoEstadosREST.class);
        resources.add(co.sigess.restful.sec.DesviacionAliadosREST.class);
        resources.add(co.sigess.restful.sec.DesviacionREST.class);
        resources.add(co.sigess.restful.sec.SeguimientoTareaREST.class);
        resources.add(co.sigess.restful.sec.SistemaCausaAdministrativaREST.class);
        resources.add(co.sigess.restful.sec.SistemaCausaInmediataREST.class);
        resources.add(co.sigess.restful.sec.SistemaCausaRaizREST.class);
        resources.add(co.sigess.restful.sec.TareaDesviacionREST.class);
        resources.add(co.sigess.restful.security.AuthorizationFilter.class);
        resources.add(co.sigess.restful.security.GZIPWriterInterceptor.class);
        resources.add(co.sigess.restful.security.RequestAuditableFilter.class);
        resources.add(co.sigess.restful.security.ResponseAuditableFilter.class);
        resources.add(co.sigess.restful.security.ResponseFilter.class);
        resources.add(co.sigess.restful.security.TokenInterceptor.class);
        resources.add(co.sigess.restful.security.WriterResponseInterceptor.class);
        resources.add(co.sigess.restful.sge.ElementoREST.class);
        resources.add(co.sigess.restful.sge.EvaluacionREST.class);
        resources.add(co.sigess.restful.sge.ReportesSGEREST.class);
        resources.add(co.sigess.restful.sge.RespuestaREST.class);
        resources.add(co.sigess.restful.sge.SistemaGestionREST.class);
        
    }

}
