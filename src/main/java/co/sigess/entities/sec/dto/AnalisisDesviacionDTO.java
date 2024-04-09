/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.sigess.entities.sec.dto;

import co.sigess.entities.CampoDTO;
import co.sigess.entities.ConvertidorDTO;
import co.sigess.entities.sec.AnalisisDesviacion;
import co.sigess.entities.sec.CausaRaiz;
import co.sigess.entities.sec.Desviacion;
import com.fasterxml.jackson.annotation.JsonIgnore;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author fmoreno
 */
public class AnalisisDesviacionDTO {

    private Integer id;
    private String observacion;
    private List<CausaRaiz> causaRaizList;
    private List<Desviacion> desviacionesList;

    @CampoDTO(referencia = "id")
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @CampoDTO(referencia = "observacion")
    public String getObservacion() {
        return observacion;
    }

    public void setObservacion(String observacion) {
        this.observacion = observacion;
    }

    @CampoDTO(referencia = "causaRaizList")
    public List<CausaRaiz> getCausaRaizList() {
        return causaRaizList;
    }

    public void setCausaRaizList(List<CausaRaiz> causaRaizList) {
        this.causaRaizList = causaRaizList;
    }

    @CampoDTO(referencia = "desviacionesList")
    public List<Desviacion> getDesviacionesList() {
        return desviacionesList;
    }
    
    public void setDesviacionesList(List<Desviacion> desviacionesList) {
        this.desviacionesList = desviacionesList;
    }

    @JsonIgnore
    public AnalisisDesviacion toEntity() throws IllegalAccessException, InvocationTargetException, InstantiationException, NoSuchFieldException {
        AnalisisDesviacion ad = ConvertidorDTO.toEntity(this, AnalisisDesviacion.class);
//        if (this.desviacionList != null && !this.desviacionList.isEmpty()) {
//            List<DesviacionAnalisisREL> darelList = new ArrayList<>();
//            for (Desviacion desviacion : this.desviacionList) {
//                DesviacionAnalisisREL darel = new DesviacionAnalisisREL(desviacion.getHashId());
//                darel.setAnalisisDesviacion(ad);
//                darelList.add(darel);
//            }
////            ad.setDesviacionAnalisisRELList(darelList);
//        }
        return ad;
    }

}
