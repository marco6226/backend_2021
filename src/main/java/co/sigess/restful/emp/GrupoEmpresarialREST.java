/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.sigess.restful.emp;

import co.sigess.entities.emp.GrupoEmpresarial;
import co.sigess.facade.emp.GrupoEmpresarialFacade;
import java.util.List;
import javax.ejb.EJB;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 *
 * @author fmoreno
 */
@Path("grupoempresarial")
public class GrupoEmpresarialREST {

    @EJB
    private GrupoEmpresarialFacade grupoEmpresarialFacade;

    public GrupoEmpresarialREST() {

    }

    @GET
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<GrupoEmpresarial> findAll() {
        return grupoEmpresarialFacade.findAll();
    }

}
