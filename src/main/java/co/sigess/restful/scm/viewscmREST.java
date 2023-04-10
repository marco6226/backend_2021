/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.sigess.restful.scm;

import co.sigess.entities.scm.viewscm;
import co.sigess.facade.scm.viewscmFACADE;
import co.sigess.restful.ServiceREST;
import co.sigess.restful.scm.viewscmREST;
import co.sigess.util.Util;
import java.util.List;
import javax.ejb.EJB;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

/**
 *
 * @author Usuario
 */
@Path("viewinfoscm")
public class viewscmREST extends ServiceREST{
    @EJB
    private viewscmFACADE viewscmFACADE;
    
    public viewscmREST(){
        super(viewscmFACADE.class);
    }
    
    @GET
    @Path("empresaId")
    public Response findByEmpresaId(){
        try{
            List<viewscm> list = this.viewscmFACADE.findByEmpresaId(super.getEmpresaIdRequestContext());
            return Response.ok(list).build();
        } catch(Exception ex){
            return Util.manageException(ex, viewscmREST.class);
        }
    }  
}
