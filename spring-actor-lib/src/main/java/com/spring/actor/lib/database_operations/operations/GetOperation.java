package com.spring.actor.lib.database_operations.operations;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.spring.actor.lib.database_operations.Query;
import org.springframework.jdbc.core.JdbcOperations;

import java.util.Map;

public class GetOperation implements IDatabaseOperation<Map<String, Object>> {

    private final JdbcOperations jdbcOperations;
    private final ObjectMapper objectMapper;

    public GetOperation(final JdbcOperations jdbcOperations, final ObjectMapper objectMapper) {
        this.jdbcOperations = jdbcOperations;
        this.objectMapper = objectMapper;
    }

    public Map<String, Object> execute(final Query query, final QueryParameters parameters) {
        System.out.println("Get operation started.");

        return null;
    }
}
