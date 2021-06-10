package com.spring.actor.lib.database_operations.filters;

import java.util.List;

public class MultiArgumentCaseInsensitiveFilter implements IQueryFilter {
    private String expression;
    private List<Object> arguments;

    @Override
    public String build() {
        return this.expression;
    }

    @Override
    public Object[] getArguments() {
        if (null != this.arguments && !arguments.isEmpty()) {
           return arguments
                   .stream()
                   .map(
                           a -> a instanceof String ? ((String) a).toLowerCase() : a)
                   .toArray();
        }

        return new Object[]{};
    }
}