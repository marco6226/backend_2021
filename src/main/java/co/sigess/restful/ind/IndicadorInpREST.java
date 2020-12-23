/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.sigess.restful.ind;

import co.sigess.entities.ind.ModeloGrafica;
import co.sigess.facade.ind.IndicadorInpFacade;
import co.sigess.restful.ServiceREST;
import co.sigess.restful.sec.TareaDesviacionREST;
import co.sigess.restful.security.Secured;
import co.sigess.util.Util;
import java.util.List;
import javax.ejb.EJB;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 *
 * @author fmoreno
 */

@Path("indicadorInp")
public class IndicadorInpREST extends ServiceREST {

    @EJB
    private IndicadorInpFacade indicadorInpFacade;

    @GET
    @Path("{areaId}/{rango}/{empresaId}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response findAll(@PathParam("areaId") Long areaId, @PathParam("rango") String rango, @PathParam("empresaId") Integer empresaId) {
        try {
            List<ModeloGrafica> list = indicadorInpFacade.find(empresaId <= 0 ? super.getEmpresaIdRequestContext() : empresaId, areaId < 0 ? null : areaId, rango);
            return Response.ok(list).build();
        } catch (Exception ex) {
            return Util.manageException(ex, TareaDesviacionREST.class);
        }
    }
      
    @GET
    @Path("cumplimientoinp/{types}/{desde}/{hasta}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response getInspInd(@PathParam("types")String type,@PathParam("desde") String desde,@PathParam("hasta") String hasta) {
        try {
              String[] arrString = type.split(",");
              int[] areasId = new int[arrString.length];
              for (int i = 0; i < arrString.length; i++) {
                  areasId[i] = Integer.parseInt(arrString[i]);
                
            }
              
              List list = this.indicadorInpFacade.calcularCumplimiento(arrString, desde, hasta);
             
              
           return Response.ok(list).build();
           
        } catch (Exception ex) {
            return Util.manageException(ex, TareaDesviacionREST.class);
        }
    }
    @GET
    @Path("cumplimientoinptotal/{types}/{desde}/{hasta}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response getInspIndtotal(@PathParam("types")String type,@PathParam("desde") String desde,@PathParam("hasta") String hasta) {
        try {
              String[] arrString = type.split(",");
              int[] areasId = new int[arrString.length];
              for (int i = 0; i < arrString.length; i++) {
                  areasId[i] = Integer.parseInt(arrString[i]);
                
            }
              
              List list = this.indicadorInpFacade.calcularCumplimientototal(arrString, desde, hasta);
              
           return Response.ok(list).build();
        } catch (Exception ex) {
            return Util.manageException(ex, TareaDesviacionREST.class);
        }
    }
    @GET
    @Path("cumplimientoattotal/{types}/{desde}/{hasta}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response getInsptotal(@PathParam("types")String type,@PathParam("desde") String desde,@PathParam("hasta") String hasta) {
        try {
              String[] arrString = type.split(",");
              int[] areasId = new int[arrString.length];
              for (int i = 0; i < arrString.length; i++) {
                  areasId[i] = Integer.parseInt(arrString[i]);
                
            }
              
              List list = this.indicadorInpFacade.calcularCumplimientoattotal(arrString, desde, hasta);
              
           return Response.ok(list).build();
        } catch (Exception ex) {
            return Util.manageException(ex, TareaDesviacionREST.class);
        }
    }
    @GET
    @Path("cumplimientoauctotal/{types}/{desde}/{hasta}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response getAuctotal(@PathParam("types")String type,@PathParam("desde") String desde,@PathParam("hasta") String hasta) {
        try {
              String[] arrString = type.split(",");
              int[] areasId = new int[arrString.length];
              for (int i = 0; i < arrString.length; i++) {
                  areasId[i] = Integer.parseInt(arrString[i]);
                
            }
              
              List list = this.indicadorInpFacade.calcularCumplimientoauctotal(arrString, desde, hasta);
              
              
           return Response.ok(list).build();
        } catch (Exception ex) {
            return Util.manageException(ex, TareaDesviacionREST.class);
        }
    }
    @GET
    @Path("cobertura/{types}/{desde}/{hasta}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response getInspIndCobertura(@PathParam("types")String type,@PathParam("desde") String desde,@PathParam("hasta") String hasta) {
        try {
              String[] arrString = type.split(",");
              int[] areasId = new int[arrString.length];
              for (int i = 0; i < arrString.length; i++) {
                  areasId[i] = Integer.parseInt(arrString[i]);
                
            }
              
              
              List list = this.indicadorInpFacade.calcularCobertura(arrString, desde, hasta);
           return Response.ok(list).build();
        } catch (Exception ex) {
            return Util.manageException(ex, TareaDesviacionREST.class);
        }
    }
    @GET
    @Path("tipo/{types}/{desde}/{hasta}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response getTipoAt(@PathParam("types")String type,@PathParam("desde") String desde,@PathParam("hasta") String hasta) {
        try {
              String[] arrString = type.split(",");
              int[] areasId = new int[arrString.length];
              for (int i = 0; i < arrString.length; i++) {
                  areasId[i] = Integer.parseInt(arrString[i]);
                
            }
              
              
              List list = this.indicadorInpFacade.tipoat(arrString, desde, hasta);
           return Response.ok(list).build();
        } catch (Exception ex) {
            return Util.manageException(ex, TareaDesviacionREST.class);
        }
    }
    @GET
    @Path("efectividad/{types}/{desde}/{hasta}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response getInspIndEfectividadInspecciones(@PathParam("types")String type,@PathParam("desde") String desde,@PathParam("hasta") String hasta) {
        try {
              String[] arrString = type.split(",");
              int[] areasId = new int[arrString.length];
              for (int i = 0; i < arrString.length; i++) {
                  areasId[i] = Integer.parseInt(arrString[i]);
                
            }
              
              
              List list = this.indicadorInpFacade.calcularEfectividadInspecciones(arrString, desde, hasta);
           return Response.ok(list).build();
        } catch (Exception ex) {
            return Util.manageException(ex, TareaDesviacionREST.class);
        }
    }
    @GET
    @Path("efectividadat/{types}/{desde}/{hasta}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response getInspIndEfectividadAt(@PathParam("types")String type,@PathParam("desde") String desde,@PathParam("hasta") String hasta) {
        try {
              String[] arrString = type.split(",");
              int[] areasId = new int[arrString.length];
              for (int i = 0; i < arrString.length; i++) {
                  areasId[i] = Integer.parseInt(arrString[i]);
                
            }
              
              
              List list = this.indicadorInpFacade.calcularEfectividadAt(arrString, desde, hasta);
           return Response.ok(list).build();
        } catch (Exception ex) {
            return Util.manageException(ex, TareaDesviacionREST.class);
        }
    }
    @GET
    @Path("coberturaat/{types}/{desde}/{hasta}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response getIndCoberturaAt(@PathParam("types")String type,@PathParam("desde") String desde,@PathParam("hasta") String hasta) {
        try {
              String[] arrString = type.split(",");
              int[] areasId = new int[arrString.length];
              for (int i = 0; i < arrString.length; i++) {
                  areasId[i] = Integer.parseInt(arrString[i]);
                
            }
              
              
              List list = this.indicadorInpFacade.calcularCoberturaAt(arrString, desde, hasta);
           return Response.ok(list).build();
        } catch (Exception ex) {
            return Util.manageException(ex, TareaDesviacionREST.class);
        }
    }
    @GET
    @Path("eficaciaauc/{types}/{desde}/{hasta}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response getInspIndEficaciaAuc(@PathParam("types")String type,@PathParam("desde") String desde,@PathParam("hasta") String hasta) {
        try {
              String[] arrString = type.split(",");
              int[] areasId = new int[arrString.length];
              for (int i = 0; i < arrString.length; i++) {
                  areasId[i] = Integer.parseInt(arrString[i]);
                
            }
              
              
              List list = this.indicadorInpFacade.calcularEficaciaReporte(arrString, desde, hasta);
           return Response.ok(list).build();
        } catch (Exception ex) {
            return Util.manageException(ex, TareaDesviacionREST.class);
        }
    }

}
