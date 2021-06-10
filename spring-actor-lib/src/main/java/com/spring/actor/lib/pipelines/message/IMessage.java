package com.spring.actor.lib.pipelines.message;

public interface IMessage {

    String getId();

    String getPipelineId();

    Object getValue(final String key);

    IMessage setValue(final String key, final Object value);
}
