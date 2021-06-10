package com.spring.actor.lib.database_operations;

import java.util.Map;

public class Query {
    private String query;
    private Map<String, WhiteParameter> whitelist;

    public Query() {
    }

    public Query(String query, Map<String, WhiteParameter> whiteList) {
        this.query = query;
        this.whitelist = whiteList;
    }

    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }

    public Map<String, WhiteParameter> getWhitelist() {
        return whitelist;
    }

    public void setWhiteList(Map<String, WhiteParameter> whiteList) {
        this.whitelist = whiteList;
    }
}
