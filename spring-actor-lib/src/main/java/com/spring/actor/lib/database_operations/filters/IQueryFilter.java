package com.spring.actor.lib.database_operations.filters;

public interface IQueryFilter {

    String build();

    Object[] getArguments();
}
