/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.sigess.restful.emp;

import co.sigess.entities.emp.Empresa;
import co.sigess.entities.sge.Elemento;
import co.sigess.entities.sge.OpcionRespuesta;
import co.sigess.entities.sge.SistemaGestion;
import co.sigess.entities.sge.SistemaGestionPK;
import co.sigess.facade.sge.ElementoFacade;
import co.sigess.facade.sge.SistemaGestionFacade;
import co.sigess.restful.security.UtilSecurity;
import java.util.List;
import javax.ejb.EJB;
import javax.ws.rs.Produces;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * REST Web Service
 *
 * @author fernando
 */
@Path("utils")
public class UtilsREST {

    @EJB
    private SistemaGestionFacade sistemaGestionFacade;

    @EJB
    private ElementoFacade elementoFacade;

    /**
     * Creates a new instance of Authentication
     */
    public UtilsREST() {
    }

    /**
     * Devuelve una codificacion SHA256 de la cadena enviada
     *
     * @param cadena
     * @return an instance of co.colaborapp.core.ws.response.Respuesta
     */
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    @Path("toSHA256/{string}")

    public String toSHA256(@PathParam("string") String cadena) {
        return UtilSecurity.toSHA256(cadena);
    }

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    @Path("clonarSistemaGestion")
    public Response clonarSistemaGestion(
            @QueryParam("empresaId") int empresaId,
            @QueryParam("sgeId") int sgeId,
            @QueryParam("sgeVersion") short sgeVersion) throws Exception {

        SistemaGestion sge = this.sistemaGestionFacade.find(new SistemaGestionPK(sgeId, sgeVersion));
        if (sge == null) {
            return Response.status(Response.Status.BAD_REQUEST).entity("No se encontró algún sistema de gestión con el id y version proporcionado").build();
        }

        limpiarElementos(sge.getElementoList());
        sge.setEvaluacionList(null);
        sge.setSistemaGestionPK(new SistemaGestionPK());
        sge.getSistemaGestionPK().setVersion((short) 1);
        sge.setEmpresa(new Empresa(empresaId));

        sge = sistemaGestionFacade.create(sge);
//        Integer numeroPreguntas = elementoFacade.createElementosSGE(sge);
//        sge.setNumeroPreguntas(numeroPreguntas);
//        sge = sistemaGestionFacade.edit(sge);

        return Response.status(Response.Status.OK).entity("Se realizó correctamente la clonación").build();
    }

    private void limpiarElementos(List<Elemento> elementosList) {
        if(elementosList == null){
            return;
        }
        for (Elemento elemento : elementosList) {
            if (elemento.getElementoList() != null) {
                limpiarElementos(elemento.getElementoList());
            }
            elemento.setDocumentosList(null);
            if (elemento.getOpcionRespuestaList() != null) {
                for (OpcionRespuesta opc : elemento.getOpcionRespuestaList()) {
                    opc.setRespuestaList(null);
                    opc.setId(null);
                }
            }
            elemento.setId(null);
        }
    }

}
