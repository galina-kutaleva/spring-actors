package com.spring.actor.lib.database_operations.configurations;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.spring.actor.lib.database_operations.DatabaseOperationManagement;
import com.spring.actor.lib.database_operations.operations.CountOperation;
import com.spring.actor.lib.database_operations.operations.DatabaseOperationParameters;
import com.spring.actor.lib.database_operations.operations.DeleteOperation;
import com.spring.actor.lib.database_operations.operations.GetOperation;
import com.spring.actor.lib.database_operations.operations.GetPageOperation;
import com.spring.actor.lib.database_operations.operations.IDatabaseOperation;
import com.spring.actor.lib.database_operations.operations.InsertOperation;
import com.spring.actor.lib.database_operations.operations.Page;
import com.spring.actor.lib.database_operations.operations.UpdateOperation;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;
import java.io.File;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

@Configuration
public class DatabaseOperationsConfiguration {

    @Value("${spring.database-operations.driver-class-name}")
    private String driverClassName;

    @Value("${spring.database-operations.datasource.url}")
    private String databaseUrl;

    @Value("${spring.database-operations.datasource.username}")
    private String username;

    @Value("${spring.database-operations.datasource.password}")
    private String password;

    @Value("${spring.database-operations.queries.directory}")
    private String directoryName;

    @Bean("database-operations-datasource")
    public DataSource dataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(driverClassName);
        dataSource.setUrl(databaseUrl);
        dataSource.setUsername(username);
        dataSource.setPassword(password);

        return dataSource;
    }

    @Bean("database-operations-jdbc-template")
    public JdbcOperations template(final DataSource dataSource) {
        JdbcTemplate template = new JdbcTemplate(dataSource);
        try {
            template.queryForList("SELECT 1");
        } catch (Throwable e) {
            System.err.println("Database is not ready.");
            e.printStackTrace();
            throw new RuntimeException("Database is not ready.", e);
        }

        return template;
    }

    @Bean("database-operations-count")
    public IDatabaseOperation<Long> countOperation(
            @Qualifier("database-operations-jdbc-template") JdbcOperations jdbcOperations,
            final ObjectMapper objectMapper
    ) {
        return new CountOperation(jdbcOperations, objectMapper);
    }

    @Bean("database-operations-delete")
    public IDatabaseOperation<Boolean> deleteOperation(
            @Qualifier("database-operations-jdbc-template") JdbcOperations jdbcOperations,
            final ObjectMapper objectMapper
    ) {
        return new DeleteOperation(jdbcOperations, objectMapper);
    }

    @Bean("database-operations-get")
    public IDatabaseOperation<Map<String, Object>> getOperation(
            @Qualifier("database-operations-jdbc-template") JdbcOperations jdbcOperations,
            final ObjectMapper objectMapper
    ) {
        return new GetOperation(jdbcOperations, objectMapper);
    }

    @Bean("database-operations-get-page")
    public IDatabaseOperation<Page> getPageOperation(
            @Qualifier("database-operations-jdbc-template") JdbcOperations jdbcOperations,
            final ObjectMapper objectMapper
    ) {
        return new GetPageOperation(jdbcOperations, objectMapper);
    }

    @Bean("database-operations-insert")
    public IDatabaseOperation<Map<String, Object>> insertOperation(
            @Qualifier("database-operations-jdbc-template") JdbcOperations jdbcOperations,
            final ObjectMapper objectMapper
    ) {
        return new InsertOperation(jdbcOperations, objectMapper);
    }

    @Bean("database-operations-update")
    public IDatabaseOperation<Map<String, Object>> updateOperation(
            @Qualifier("database-operations-jdbc-template") JdbcOperations jdbcOperations,
            final ObjectMapper objectMapper
    ) {
        return new UpdateOperation(jdbcOperations, objectMapper);
    }

    @Bean
    public DatabaseOperationManagement databaseOperations(
            @Qualifier("database-operations-count")    final IDatabaseOperation<?> countOperation,
            @Qualifier("database-operations-delete")   final IDatabaseOperation<?> deleteOperation,
            @Qualifier("database-operations-get")      final IDatabaseOperation<?> getOperation,
            @Qualifier("database-operations-get-page") final IDatabaseOperation<?> getPageOperation,
            @Qualifier("database-operations-insert")   final IDatabaseOperation<?> insertOperation,
            @Qualifier("database-operations-update")   final IDatabaseOperation<?> updateOperation,
            final ObjectMapper objectMapper
    ) {
        try {
            URL resource = getClass().getClassLoader().getResource(directoryName);
            assert resource != null;
            File f = new File(resource.toURI());

            return new DatabaseOperationManagement(
                    f,
                    new HashMap<String, IDatabaseOperation<?>>(){{
                        put(DatabaseOperationParameters.COUNT,    countOperation);
                        put(DatabaseOperationParameters.DELETE,   deleteOperation);
                        put(DatabaseOperationParameters.GET,      getOperation);
                        put(DatabaseOperationParameters.GET_PAGE, getPageOperation);
                        put(DatabaseOperationParameters.INSERT,   insertOperation);
                        put(DatabaseOperationParameters.UPDATE,   updateOperation);
                    }},
                    objectMapper
            );
        } catch (Exception e) {
            throw new RuntimeException("Could not initialize DatabaseOperations", e);
        }
    }
}
