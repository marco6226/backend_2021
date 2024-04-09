/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.sigess.restful.inp;

import co.sigess.facade.inp.TipoHallazgoFacade;
import co.sigess.restful.ServiceREST;
import co.sigess.restful.security.Secured;
import javax.ws.rs.Path;

/**
 *
 * @author fmoreno
 */
@Secured
@Path("tipoHallazgo")
public class TipoHallazgoREST extends ServiceREST {

    public TipoHallazgoREST() {
        super(TipoHallazgoFacade.class);
    }

}
