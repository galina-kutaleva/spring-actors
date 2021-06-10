package com.spring.actor.lib.database_operations.operations;

import com.spring.actor.lib.database_operations.Query;

public interface IDatabaseOperation<T> {

    T execute(final Query query, final QueryParameters parameters);
}