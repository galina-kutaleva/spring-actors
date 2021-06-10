package com.spring.actor.lib.database_operations;


import com.spring.actor.lib.database_operations.operations.IDatabaseOperation;
import com.spring.actor.lib.database_operations.operations.QueryParameters;

import java.util.Map;

public interface IDatabaseOperationManagement {

    void addQuery(final String name, final Query query);

    void addQueries(final Map<String, Query> queries);

    void addDatabaseOperation(final String name, final IDatabaseOperation<?> operation);

    void addDatabaseOperations(final Map<String, IDatabaseOperation<?>> operations);

    Query getQuery(final String name);

    IDatabaseOperation<?> getOperation(final String name);

    Object execute(final QueryParameters queryParameters);
}
