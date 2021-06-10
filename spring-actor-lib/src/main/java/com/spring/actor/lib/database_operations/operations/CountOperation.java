package com.spring.actor.lib.database_operations.operations;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.spring.actor.lib.database_operations.Query;
import org.springframework.jdbc.core.JdbcOperations;

public class CountOperation implements IDatabaseOperation<Long> {

    private final JdbcOperations jdbcOperations;
    private final ObjectMapper objectMapper;

    public CountOperation(final JdbcOperations jdbcOperations, final ObjectMapper objectMapper) {
        this.jdbcOperations = jdbcOperations;
        this.objectMapper = objectMapper;
    }

    public Long execute(final Query query, final QueryParameters parameters) {
        System.out.println("Count operation started.");
        return 1L;
    }
}