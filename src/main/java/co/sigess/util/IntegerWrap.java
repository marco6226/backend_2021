/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.sigess.util;

/**
 *
 * @author fmoreno
 */
public class IntegerWrap {

    private int value;

    public void increment(int increment) {
        value += increment;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

}
