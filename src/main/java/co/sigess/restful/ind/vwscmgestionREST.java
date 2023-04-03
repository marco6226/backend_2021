/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.sigess.restful.ind;

import co.sigess.entities.ind.vwscmgestion;
import co.sigess.facade.ind.vwscmgestionFACADE;
import co.sigess.restful.ServiceREST;
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
@Path("indscmge")
public class vwscmgestionREST extends ServiceREST{
    @EJB
    private vwscmgestionFACADE vwscmgestionFACADE;
    
    public vwscmgestionREST(){
        super(vwscmgestionFACADE.class);
    }
    
    @GET
    @Path("empresaId")
    public Response findByEmpresaId(){
        try{
            List<vwscmgestion> list = this.vwscmgestionFACADE.findByEmpresaId(super.getEmpresaIdRequestContext());
            return Response.ok(list).build();
        } catch(Exception ex){
            return Util.manageException(ex, vwscmgestionREST.class);
        }
    }  
    
}
