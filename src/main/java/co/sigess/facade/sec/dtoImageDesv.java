/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.sigess.facade.sec;

import java.io.Serializable;

/**
 *
 * @author Leo
 */

public class dtoImageDesv implements Serializable{

    /**
     * @return the ruta
     */
    public String getRuta() {
        return ruta;
    }

    /**
     * @param ruta the ruta to set
     */
    public void setRuta(String ruta) {
        this.ruta = ruta;
    }
	
	
	private String ruta;
	
	
	
	/** GET and SET */
}