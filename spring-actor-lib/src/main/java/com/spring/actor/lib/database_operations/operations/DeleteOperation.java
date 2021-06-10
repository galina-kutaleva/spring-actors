package com.spring.actor.lib.database_operations.operations;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.spring.actor.lib.database_operations.Query;
import org.springframework.jdbc.core.JdbcOperations;

public class DeleteOperation implements IDatabaseOperation<Boolean> {

    private final JdbcOperations jdbcOperations;
    private final ObjectMapper objectMapper;

    public DeleteOperation(final JdbcOperations jdbcOperations, final ObjectMapper objectMapper) {
        this.jdbcOperations = jdbcOperations;
        this.objectMapper = objectMapper;
    }

    public Boolean execute(final Query query, final QueryParameters parameters) {
        System.out.println("Delete operation started.");
        return false;
    }
}
