/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.sigess.entities.ado;

/**
 *
 * @author fmoreno
 */
public enum Modulo {
    ADO("Administracion de documentos"),
    AUC("Autocuidado"),
    AUS("Ausentismo"),
    EMP("Empresa"),
    IND("Indicadores"),
    INP("Inspecciones"),
    IPR("Ipecr"),
    RAI("Reporte accidentes/incidentes"),
    SEC("Seguimiento y control"),
    SGE("Sistema de gestión"),
    COP("COPASST"),
    LISTA("Lista Inspeccion"),
    EMPRESA("Empresa");
    
    private String nombre;

    private Modulo(String nombre) {
        this.nombre = nombre;
    }    
    
}
