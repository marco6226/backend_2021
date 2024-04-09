/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.sigess.restful;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 *
 * @author fmoreno
 */
public class Filter {

    private String field;
    private String value1;
    private String value2;
    private String criteria;
    private CriteriaFilter criteriaEnum;
    private boolean isExpression;

    public Filter() {
    }

    public Filter(String field, String value1, String value2, CriteriaFilter criteriaEnum) {
        this.field = field;
        this.value1 = value1;
        this.value2 = value2;
        this.criteriaEnum = criteriaEnum;
    }

    public Filter(String field, String value1, String value2, String criteria) {
        this.field = field;
        this.value1 = value1;
        this.value2 = value2;
        this.criteria = criteria;
    }

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public String getValue1() {
        return value1;
    }

    @JsonIgnore
    public String[] getValueArray() {
//        if (this.value1.contains(",")) {
//            return this.value1.split(",");
//        } else {
//            throw new IllegalArgumentException("Parametro value1 no corresponde a una lista o array");
//        }
        return this.value1.replace("{", "").replace("}", "").replace(" ", "").split(",");
    }

    public void setValue1(String value1) {
        this.value1 = value1;
    }

    public String getValue2() {
        return value2;
    }

    public void setValue2(String value2) {
        this.value2 = value2;
    }

    public String getCriteria() {
        return criteria;
    }

    public void setCriteria(String criteria) {
        this.criteria = criteria;
    }

    public CriteriaFilter getCriteriaEnum() {
        if (this.criteriaEnum == null) {
            this.criteriaEnum = this.criteria == null ? null : CriteriaFilter.valueFromCode(this.criteria);
        }
        return criteriaEnum;
    }

    public void setCriteriaEnum(CriteriaFilter criteriaEnum) {
        this.criteriaEnum = criteriaEnum;
    }

    public boolean isExpression() {
        return isExpression;
    }

    public void setIsExpression(boolean isExpression) {
        this.isExpression = isExpression;
    }

}
