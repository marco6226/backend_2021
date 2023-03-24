/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.sigess.restful.ind;

import co.sigess.entities.ind.caracterizacionview;
import co.sigess.entities.ind.vwscmcorporativo;
import co.sigess.facade.ind.vwscmcorporativoFACADE;
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
@Path("indscmco")
public class vwscmcorporativoREST extends ServiceREST{
    @EJB
    private vwscmcorporativoFACADE vwscmcorporativoFACADE;
    
    public vwscmcorporativoREST(){
        super(vwscmcorporativoFACADE.class);
    }
    
    @GET
    @Path("empresaId")
    public Response findByEmpresaId(){
        try{
            List<vwscmcorporativo> list = this.vwscmcorporativoFACADE.findByEmpresaId(super.getEmpresaIdRequestContext());
            return Response.ok(list).build();
        } catch(Exception ex){
            return Util.manageException(ex, vwscmcorporativoREST.class);
        }
    }  
}
