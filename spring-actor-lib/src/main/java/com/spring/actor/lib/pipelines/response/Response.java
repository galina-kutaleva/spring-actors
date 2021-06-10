package com.spring.actor.lib.pipelines.response;

import java.util.HashMap;
import java.util.Map;

public class Response implements IResponse {
    private Map<String, Object> body = new HashMap<>();
    private Map<String, Object> errors = new HashMap<>();

    public Response() {
        this.body = new HashMap<>();
        this.errors = new HashMap<>();
    }

    public Response(Map<String, Object> body) {
        this.body = body;
        this.errors = new HashMap<>();
    }

    @Override
    public IResponse setValue(final String key, final Object value) {
        this.body.put(key, value);

        return this;
    }

    @Override
    public Map<String, Object> getBody() {

        return this.body;
    }

    @Override
    public Object getValue(String key) {

        return this.body.get(key);
    }

    @Override
    public Map<String, Object> getErrors() {
        return this.errors;
    }

    @Override
    public IResponse setError(String key, Object value) {
        this.errors.put(key, value);

        return this;
    }
}
