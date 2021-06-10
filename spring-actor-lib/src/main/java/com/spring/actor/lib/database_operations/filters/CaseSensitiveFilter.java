package com.spring.actor.lib.database_operations.filters;

public class CaseSensitiveFilter implements IQueryFilter {
    private String expression;
    private Object argument;

    public String build() {
        return this.expression;
    }

    public Object[] getArguments() {
        if (null != this.argument) {
            return new Object[]{this.argument};
        } else {
            return new Object[0];
        }
    }
}