package com.spring.actor.lib.database_operations;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.spring.actor.lib.database_operations.operations.IDatabaseOperation;
import com.spring.actor.lib.database_operations.operations.QueryParameters;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Stream;

public class DatabaseOperationManagement implements IDatabaseOperationManagement {
    private final Map<String, Query> queries = new ConcurrentHashMap<>();
    private final Map<String, IDatabaseOperation<?>> operations = new ConcurrentHashMap<>();
    private final ObjectMapper objectMapper;

    public DatabaseOperationManagement(
            final File directoryName,
            final Map<String, IDatabaseOperation<?>> operations,
            final ObjectMapper objectMapper
    ) {
        this.objectMapper = objectMapper;
        this.operations.putAll(operations);
        initQueries(directoryName);
    }

    public void addQuery(final String name, final Query query) {
        this.queries.put(name, query);
    }

    public void addQueries(final Map<String, Query> queries) {
        this.queries.putAll(queries);
    }

    public void addDatabaseOperation(final String name, final IDatabaseOperation<?> operation) {
        this.operations.put(name, operation);
    }

    public void addDatabaseOperations(final Map<String, IDatabaseOperation<?>> operations) {
        this.operations.putAll(operations);
    }

    public Query getQuery(final String name) {

        return this.queries.get(name);
    }

    public IDatabaseOperation<?> getOperation(final String name) {

        return this.operations.get(name);
    }

    public Object execute(final QueryParameters queryParameters) {
        IDatabaseOperation<?> operation = this.operations.get(queryParameters.getQueryType());
        Query query = this.queries.get(queryParameters.getQueryName());
        if (null == operation) {
            throw new RuntimeException("Database operation with type " + queryParameters.getQueryType() + "not found.");
        }
        if (null == query) {
            throw new RuntimeException("Query to database with name " + queryParameters.getQueryName() + "not found.");
        }
        return operation.execute(query, queryParameters);
    }

    private void initQueries(final File directoryName) {
        if (null != directoryName && directoryName.isDirectory()) {
            try (Stream<Path> paths = Files.walk(Paths.get(directoryName.toURI()))) {
                paths
                        .filter(Files::isRegularFile)
                        .forEach(
                                f -> {
                                    try {
                                        String query = new String(
                                                Files.readAllBytes(f.toAbsolutePath()), StandardCharsets.UTF_8
                                        );

                                        this.queries.put(
                                                f.toString().substring(
                                                        f.toAbsolutePath().toString().lastIndexOf("\\") + 1
                                                ).replaceFirst("[.][^.]+$", ""),
                                                this.objectMapper.readValue(query, Query.class)
                                        );
                                    } catch (IOException e) {
                                        throw new RuntimeException(
                                                "Could not read file with name - " + f.getFileName()
                                        );
                                    }
                                }
                        );
            } catch (IOException e) {
                throw new RuntimeException("Could not read file from directory - " + directoryName.getName());
            }
        } else {
            throw new RuntimeException("Wrong queries location.");
        }
    }
}
