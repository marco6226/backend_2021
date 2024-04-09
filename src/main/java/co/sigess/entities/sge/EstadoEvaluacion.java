/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.sigess.entities.sge;

/**
 *
 * @author fmoreno
 */
public class EstadoEvaluacion {

    private int evaluacionId;
    private int numeroRespuestas;
    private int numeroPreguntas;

    public EstadoEvaluacion(int numeroRespuestas, int numeroPreguntas) {
        this.numeroRespuestas = numeroRespuestas;
        this.numeroPreguntas = numeroPreguntas;
    }

    public EstadoEvaluacion(int evaluacionId, int numeroRespuestas, int numeroPreguntas) {
        this.evaluacionId = evaluacionId;
        this.numeroRespuestas = numeroRespuestas;
        this.numeroPreguntas = numeroPreguntas;
    }    

    public float getAvance() {
        return numeroPreguntas <= 0 ? 0 : ((float)numeroRespuestas / numeroPreguntas);
    }

    public float getRestantes() {
        return numeroPreguntas - numeroRespuestas;
    }

    public boolean isFinalizado() {
        return (numeroPreguntas > 0 && numeroRespuestas > 0 && numeroPreguntas == numeroRespuestas);
    }

    public int getEvaluacionId() {
        return evaluacionId;
    }

    public void setEvaluacionId(int evaluacionId) {
        this.evaluacionId = evaluacionId;
    }

    public int getNumeroRespuestas() {
        return numeroRespuestas;
    }

    public void setNumeroRespuestas(int numeroRespuestas) {
        this.numeroRespuestas = numeroRespuestas;
    }

    public int getNumeroPreguntas() {
        return numeroPreguntas;
    }

    public void setNumeroPreguntas(int numeroPreguntas) {
        this.numeroPreguntas = numeroPreguntas;
    }

}
