package com.spring.actor.lib.pipelines.response;

import java.util.Map;

public interface IResponse {

    IResponse setValue(final String key, final Object value);

    Map<String, Object> getBody();

    Object getValue(final String key);

    Map<String, Object> getErrors();

    IResponse setError(final String key, final Object value);
}
