package com.spring.actor.lib.database_operations.filters;

import java.util.List;

public class MultiArgumentCaseSensitiveFilter implements IQueryFilter {
    private String expression;
    private List<Object> arguments;

    @Override
    public String build() {
        return this.expression;
    }

    @Override
    public Object[] getArguments() {
        if (null != this.arguments && !arguments.isEmpty()) {
           return arguments.toArray();
        }

        return new Object[]{};
    }
}