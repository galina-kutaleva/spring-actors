package com.spring.actor.lib.web.filtering;

public class Criterion {
    private String fieldName;
    private String operator;
    private String value;

    public Criterion(String fieldName, String operator, String value) {
        this.fieldName = fieldName;
        this.operator = operator;
        this.value = value;
    }

    public Criterion() {
    }

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
