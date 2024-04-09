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
public enum CriteriaFilter {
    EQUALS("eq"),
    NOT_EQUALS("ne"),
    CONTAINS("ct"),
    NOT_CONTAINS("nc"),
    GREATER_THAN("gt"),
    LOWER_THAN("lt"),
    LESS_THAN_OR_EQUAL("le"),
    GREATER_THAN_OR_EQUAL("ge"),
    BETWEEN("bt"),
    IS_NOT_NULL("isnn"),
    LIKE("lk"),
    IS_NULL("isn");

    private String code;

    private CriteriaFilter(String code) {
        this.code = code;
    }

    public static CriteriaFilter valueFromCode(String code) {
        for (CriteriaFilter enumeration : CriteriaFilter.values()) {
            if (enumeration.code.equalsIgnoreCase(code)) {
                return enumeration;
            }
        }
        throw new IllegalArgumentException("code " + code + " not exist in enumeration " + CriteriaFilter.class.getName());
    }
}
