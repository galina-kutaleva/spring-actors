package com.spring.actor.lib.database_operations.operations;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.spring.actor.lib.database_operations.Query;
import com.spring.actor.lib.database_operations.QueryHelper;
import com.spring.actor.lib.database_operations.filters.IQueryFilter;
import org.springframework.jdbc.core.JdbcOperations;

public class GetPageOperation implements IDatabaseOperation<Page> {

    private final JdbcOperations jdbcOperations;
    private final ObjectMapper objectMapper;

    public GetPageOperation(final JdbcOperations jdbcOperations, final ObjectMapper objectMapper) {
        this.jdbcOperations = jdbcOperations;
        this.objectMapper = objectMapper;
    }

    @Override
    public Page execute(final Query query, final QueryParameters parameters) {
//        IQueryFilter filter = QueryHelper.getFilterFromCriteria(query, parameters.getFilteringArguments());
        System.out.println("Get page operation started.");
        return new Page(null, 0L, 0);
    }
}
