/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.sigess.facade.ind;

import co.sigess.entities.emp.Area;
import co.sigess.entities.ind.Dataset;
import co.sigess.entities.ind.FichaTecnicaIndicador;
import co.sigess.entities.ind.ModeloGrafica;
import co.sigess.facade.emp.AreaFacade;
import co.sigess.facade.inp.ProgramacionFacade;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author fmoreno
 */
@Stateless
public class IndicadorInpFacade {

    @EJB
    private ProgramacionFacade programacionFacade;

    @EJB
    private AreaFacade areaFacade;

    @PersistenceContext(unitName = "SIGESS_PU")
    private EntityManager em;

    public List<ModeloGrafica> find(Integer empresaId, Long areaId, String rangos) throws Exception {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        List<Area> areaList = null;
        if (areaId == null) {
            areaList = this.areaFacade.findByEmpresa(empresaId);
        } else {
            areaList = this.areaFacade.findByAreaPadre(areaId);
        }

        ModeloGrafica mgCumpProg = new ModeloGrafica();
        mgCumpProg.setTitle("CUMPLIMIENTO PROGRAMACION");
        mgCumpProg.setLabels(new ArrayList<>());
        mgCumpProg.setDatasets(new ArrayList<>());

        ModeloGrafica mgCubr = new ModeloGrafica();
        mgCubr.setTitle("COBERTURA");
        mgCubr.setLabels(new ArrayList<>());
        mgCubr.setDatasets(new ArrayList<>());

        for (Area area : areaList) {
            mgCumpProg.getLabels().add(area.getNombre());
        }
        mgCumpProg.getLabels().add("TOTAL");
        mgCubr.getLabels().add("Cubrimiento");

        String[] rangosList = rangos.split("\",");
        for (String rango : rangosList) {
            Dataset dsCumpProg = new Dataset();
            dsCumpProg.setData(new ArrayList<>());

            Dataset dsCubr = new Dataset();
            dsCubr.setData(new ArrayList<>());

            String[] rangoSplit = rango.split(",");
            String valor1 = rangoSplit[0].replace("[", "").replace("\"", "").replace("{", "");
            String valor2 = rangoSplit[1].replace(")", "").replace("]", "");
            Date desde = sdf.parse(valor1);
            Date hasta = sdf.parse(valor2);
            dsCumpProg.setLabel(sdf.format(desde) + "," + sdf.format(hasta));
            dsCubr.setLabel(sdf.format(desde) + "," + sdf.format(hasta));

            double totalCumpProg = 0.0;
            for (Area area : areaList) {
                double acumulado = calcularCumplimiento(area, desde, hasta);
                acumulado += this.programacionFacade.calcularCumplimiento(area.getId(), desde, hasta);
                dsCumpProg.getData().add(acumulado * 100);
                totalCumpProg += acumulado;
            }
            dsCumpProg.getData().add(totalCumpProg / areaList.size() * 100);
            mgCumpProg.getDatasets().add(dsCumpProg);

            double cubrimiento = this.programacionFacade.calcularCubrimiento(desde, hasta);
            dsCubr.getData().add(cubrimiento * 100);
            
            mgCubr.getDatasets().add(dsCubr);
        }
        List<ModeloGrafica> list = new ArrayList<>();
        
        FichaTecnicaIndicador ftCumpProg = new FichaTecnicaIndicador(
                "Resultado",
                "Cumplimiento",
                "Se verifica el cumplimiento del proceso de inspecciones ejecutadas versus las planeadas.",
                "(N° inspecciones ejecutadas / N° inspecciones planeadas) * 100",
                "Trimestral",
                "100%"
        );
        FichaTecnicaIndicador ftCubr = new FichaTecnicaIndicador(
                "Resultado",
                "Cobertura",
                "Se verifica las sedes intervenidas con respecto al numero total de sedes a intervenir.",
                "(N° sedes inspeccionadas / N° sedes a intervernir) * 100",
                "Trimestral",
                "100%"
        );
        mgCumpProg.setFichaTecnicaIndicador(ftCumpProg);
        mgCubr.setFichaTecnicaIndicador(ftCubr);
        
        list.add(mgCumpProg);
        list.add(mgCubr);
        return list;
    }

    private double calcularCumplimiento(Area area, Date desde, Date hasta) throws Exception {
        double acumulador = 0;
        if (area.getAreaList() == null || area.getAreaList().isEmpty()) {
            acumulador += this.programacionFacade.calcularCumplimiento(area.getId(), desde, hasta);
        } else {
            for (Area areaChild : area.getAreaList()) {
                acumulador += this.calcularCumplimiento(areaChild, desde, hasta);
            }
            acumulador += this.programacionFacade.calcularCumplimiento(area.getId(), desde, hasta);
        }

        return acumulador;
    }
    
     public List calcularCumplimiento(String[] areasId, Date desde, Date hasta) {
   
        Query q = this.em.createNamedQuery("SELECT b.n_realiz, b.total , p.nombre\n "
                + "from inp.tesdeleonardo(?1) b\n "
                + "INNER JOIN emp.area p on p.id = b.areas"
               );
        
        q.setParameter(1, areasId);
       
        try {
          List list;
            list = q.getResultList();
            
          return list;  
        } catch (NoResultException nre) {
            return null;
        } catch (Exception ed) {
            System.out.println(ed.toString());
            return null;
        }
    }
}
