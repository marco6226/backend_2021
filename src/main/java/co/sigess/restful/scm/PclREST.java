/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.sigess.restful.scm;

import co.sigess.restful.ServiceREST;
import co.sigess.facade.scm.PclFacade;
import co.sigess.entities.scm.Pcl;
import co.sigess.entities.scm.ScmLogs;
import co.sigess.facade.scm.ScmLogsFacade;

import co.sigess.restful.security.Secured;
import co.sigess.util.Util;
import java.util.Calendar;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 *
 * @author leonardo
 */
@Stateless
@Secured
@Path("pcl")
public class PclREST extends ServiceREST {
    
    @EJB
    private PclFacade pclfacade;
        
    @EJB
    private ScmLogsFacade scmLogsFacade;
    
    
    @POST
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response create(Pcl pcl) {
        try {
            
            pcl = this.pclfacade.create(pcl);
            this.logScm("Creacion de pcl", null , pcl.getId().toString() , pcl.getClass().toString());
            
            return Response.ok(pcl.getId()).build();
        } catch (Exception ex) {
            return Util.manageException(ex, PclREST.class);
        }
    }
    
    
     private void logScm(String action, String json, String documento, String entity) {
        try {
            Calendar fechaActual = Calendar.getInstance();
            ScmLogs log = new ScmLogs();
            log.setAction(action);
            log.setPkCase(documento);
            log.setPkUser(super.getUsuarioRequestContext().getEmail());
            log.setFecha_creacion(fechaActual.getTime());
            log.setEntity(entity);
            log.setJson(json);
            scmLogsFacade.create(log);
            
        } catch (Exception e) {
            
        }
        
    }
}
