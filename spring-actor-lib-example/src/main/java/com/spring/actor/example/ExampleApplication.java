package com.spring.actor.example;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.spring.actor.lib.web.filtering.FilteringResolver;
import com.spring.actor.lib.web.pagination.PaginationResolver;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@SpringBootApplication(
        exclude = {
                DataSourceAutoConfiguration.class,
                DataSourceTransactionManagerAutoConfiguration.class,
                HibernateJpaAutoConfiguration.class},
        scanBasePackages = {"com.spring.actor"}
)
public class ExampleApplication implements WebMvcConfigurer {

    private ObjectMapper mapper;

    public ExampleApplication(final ObjectMapper objectMapper) {
        this.mapper = objectMapper;
    }

    public static void main(final String[] args) {
        SpringApplication.run(ExampleApplication.class, args);
    }

    @Override
    public void addArgumentResolvers(final List<HandlerMethodArgumentResolver> argumentResolvers) {
        argumentResolvers.add(new PaginationResolver());
        argumentResolvers.add(new FilteringResolver());
    }
}
