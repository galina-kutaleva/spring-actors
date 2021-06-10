package com.spring.actor.lib.database_operations.operations;

import com.spring.actor.lib.database_operations.filters.IQueryFilter;
import com.spring.actor.lib.web.filtering.Criterion;
import org.springframework.data.domain.Pageable;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class QueryParameters {
    private String queryName;
    private String queryType;
    private final Map<String, Pageable> paginations;
    private final Map<String, IQueryFilter> filteringArguments;
    private final Map<String, List<Criterion>> criteria;

    public QueryParameters() {
        this.paginations = new HashMap<>();
        this.filteringArguments = new HashMap<>();
        this.criteria = new HashMap<>();
    }

    public QueryParameters(
            final Map<String, Pageable> paginations,
            final Map<String, IQueryFilter> filteringArguments,
            final Map<String, List<Criterion>> criteria
    ) {
        this.paginations = paginations;
        this.filteringArguments = filteringArguments;
        this.criteria = criteria;
    }

    public String getQueryName() {
        return queryName;
    }

    public QueryParameters setQueryName(String queryName) {
        this.queryName = queryName;

        return this;
    }

    public String getQueryType() {
        return queryType;
    }

    public QueryParameters setQueryType(String queryType) {
        this.queryType = queryType;

        return this;
    }

    public Map<String, Pageable> getPaginations() {

        return paginations;
    }

    public Map<String, IQueryFilter> getFilteringArguments() {
        return filteringArguments;
    }

    public Map<String, List<Criterion>> getCriteria() {
        return criteria;
    }

    public QueryParameters setFilteringArgument(final String key, final IQueryFilter filteringArgument) {
        this.filteringArguments.put(key, filteringArgument);

        return this;
    }

    public QueryParameters setPagination(final String key, final Pageable pagination) {
        this.paginations.put(key, pagination);

        return this;
    }

    public QueryParameters setCriteria(final String key, final List<Criterion> criteria) {
        this.criteria.put(key, criteria);

        return this;
    }
}
