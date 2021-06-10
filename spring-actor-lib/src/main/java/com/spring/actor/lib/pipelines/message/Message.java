package com.spring.actor.lib.pipelines.message;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class Message implements IMessage {
    private String id;
    private String pipelineId;
    private Map<String, Object> message = new HashMap<>();

    private Message() {
    }

    public Message(final String pipelineId) {
        this.id = UUID.randomUUID().toString();
        this.pipelineId = pipelineId;
    }

    public Message(final String pipelineId, final Map<String, Object> message) {
        this.id = UUID.randomUUID().toString();
        this.pipelineId = pipelineId;
        this.message = message;
    }

    @Override
    public String getId() {

        return this.id;
    }

    @Override
    public String getPipelineId() {

        return this.pipelineId;
    }

    @Override
    public Object getValue(final String key) {

        return this.message.get(key);
    }

    @Override
    public IMessage setValue(final String key, final Object value) {
        this.message.put(key, value);
        return this;
    }
}
