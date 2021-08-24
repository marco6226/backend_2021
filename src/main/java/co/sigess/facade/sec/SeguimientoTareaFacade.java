/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.sigess.facade.sec;

import co.sigess.entities.ado.Documento;
import co.sigess.entities.com.TipoMensaje;
import co.sigess.entities.sec.EvidencesFiles;
import co.sigess.entities.sec.SeguimientoTarea;
import co.sigess.exceptions.UserMessageException;
import co.sigess.facade.ado.DirectorioFacade;
import co.sigess.facade.com.AbstractFacade;
import co.sigess.restful.CriteriaFilter;
import co.sigess.restful.Filter;
import co.sigess.restful.FilterQuery;
import co.sigess.util.FileUtil;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collector;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.stream.Collectors;

/**
 *
 * @author Leo
 */
@Stateless
public class SeguimientoTareaFacade extends AbstractFacade<SeguimientoTarea>{
   
   @EJB
   private EvidencesFilesFacade evidencesFacade;
    
    @PersistenceContext(unitName = "SIGESS_PU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
    
    public SeguimientoTareaFacade(){
        super(SeguimientoTarea.class);
    }
    
    
      public  HashMap<String, List<String>>  findFileV2(Integer documentoId,String type) throws Exception{
        HashMap<String, List<String>> files = new HashMap<String, List<String>>();
          FilterQuery fq = new FilterQuery();
        List<Filter> fl = new ArrayList<>();
        Filter filter = new Filter(type, Integer.toString(documentoId), null, CriteriaFilter.EQUALS);
        fl.add(filter);
        fq.setFilterList(fl);
         List<EvidencesFiles> evidences = evidencesFacade.findWithFilter(fq);
         if (evidences == null) {
            throw new IllegalArgumentException("parámetro id no válido");
        }
        System.out.print(evidences.size());
       List<String> List = null;
       files.put("error", List);
       List<String> file = new ArrayList<String>();
  
          for (int i = 0; i < evidences.size(); i++) {
              System.out.print(evidences.get(i).getRuta());
              try {
                  
                 file.add(FileUtil.getFromVirtualFS2(evidences.get(i).getRuta()));
             
              } catch (Exception ex) {
                  System.out.print("Erro");
             }
          }
           files.put("files", file);
                      return files;
                      
      }
       
 

}
