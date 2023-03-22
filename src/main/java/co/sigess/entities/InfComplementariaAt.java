/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.sigess.entities;

import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;

/**
 * Esta clase es para poder manejar el json de "complementaria" en la tabla ReporteAtView
 * {
 *   "Peligro": {
 *       "id": Number,
 *       "nombre": String
 *   },
 *   "DescripcionPeligro": {
 *       "id": Number,
 *       "nombre": String
 *   },
 *   "EnventoARL": String,
 *   "ReporteControl": Boolean,
 *   "FechaControl": String,
 *   "CopiaTrabajador": Boolean,
 *   "FechaCopia": String
 * }
 * @author JULIO
 * 
 */
public class InfComplementariaAt {
    private JsonElement Peligro;
    private JsonElement DescripcionPeligro;
    private String EventoARL;
    private boolean ReporteControl;
    private String FechaControl;
    private boolean CopiaTrabajador;
    private String FechaCopia;

    public InfComplementariaAt(JsonElement Peligro, JsonElement DescripcionPeligro, String EventoARL, boolean ReporteControl, String FechaControl, boolean CopiaTrabajador, String FechaCopia) {
        this.Peligro = Peligro;
        this.DescripcionPeligro = DescripcionPeligro;
        this.EventoARL = EventoARL;
        this.ReporteControl = ReporteControl;
        this.FechaControl = FechaControl;
        this.CopiaTrabajador = CopiaTrabajador;
        this.FechaCopia = FechaCopia;
    }

    public JsonElement getPeligro() {
        return Peligro;
    }

    public void setPeligro(JsonPrimitive Peligro) {
        this.Peligro = Peligro;
    }

    public JsonElement getDescripcionPeligro() {
        return DescripcionPeligro;
    }

    public void setDescripcionPeligro(JsonPrimitive DescripcionPeligro) {
        this.DescripcionPeligro = DescripcionPeligro;
    }

    public String getEventoARL() {
        return EventoARL;
    }

    public void setEventoARL(String EventoARL) {
        this.EventoARL = EventoARL;
    }

    public boolean isReporteControl() {
        return ReporteControl;
    }

    public void setReporteControl(boolean ReporteControl) {
        this.ReporteControl = ReporteControl;
    }

    public String getFechaControl() {
        return FechaControl;
    }

    public void setFechaControl(String FechaControl) {
        this.FechaControl = FechaControl;
    }

    public boolean isCopiaTrabajador() {
        return CopiaTrabajador;
    }

    public void setCopiaTrabajador(boolean CopiaTrabajador) {
        this.CopiaTrabajador = CopiaTrabajador;
    }

    public String getFechaCopia() {
        return FechaCopia;
    }

    public void setFechaCopia(String FechaCopia) {
        this.FechaCopia = FechaCopia;
    }

    @Override
    public String toString() {
        return "InfComplementariaAt{" + "Peligro=" + Peligro + ", DescripcionPeligro=" + DescripcionPeligro + ", EventoARL=" + EventoARL + ", ReporteControl=" + ReporteControl + ", FechaControl=" + FechaControl + ", CopiaTrabajador=" + CopiaTrabajador + ", FechaCopia=" + FechaCopia + '}';
    }
    
}
