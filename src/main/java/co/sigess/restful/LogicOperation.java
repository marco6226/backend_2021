/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.sigess.restful;

/**
 *
 * @author fmoreno
 */
public enum LogicOperation {
    AND("and"),
    OR("or");

    private String code;

    private LogicOperation(String code) {
        this.code = code;
    }

    public static LogicOperation valueFromCode(String code) {
        for (LogicOperation enumeration : LogicOperation.values()) {
            if (enumeration.code.equalsIgnoreCase(code)) {
                return enumeration;
            }
        }
        throw new IllegalArgumentException("code " + code + " not exist in enumeration " + LogicOperation.class.getName());
    }
}
