package com.spring.actor.lib.database_operations.filters;

public class CaseInsensitiveFilter implements IQueryFilter {
    private String expression;
    private Object argument;

    public CaseInsensitiveFilter(String expression, Object argument) {
        this.expression = expression;
        this.argument = argument;
    }

    @Override
    public String build() {
        return this.expression;
    }

    @Override
    public Object[] getArguments() {
        if (null != this.argument) {
            if (this.argument instanceof String) {
                return new Object[]{ ((String) this.argument).toLowerCase()};
            } else {
                return new Object[]{ this.argument};
            }
        }
        return new Object[]{};
    }
}