package com.spring.actor.lib.web.filtering;

public class LinkedCriterion extends Criterion {
    private String nextOperator;

    public LinkedCriterion(
        final String fieldName,
        final String operator,
        final String value,
        final String nextOperator
    ) {
        super(fieldName, operator, value);
        this.nextOperator = nextOperator;
    }

    public String getNextOperator() {
        return nextOperator;
    }

    public void setNextOperator(String nextOperator) {
        this.nextOperator = nextOperator;
    }
}
