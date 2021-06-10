package com.spring.actor.lib.pipelines.message_processor;

import com.spring.actor.lib.pipelines.message.IMessage;

public interface IMessageProcessor {

    Object process(final IMessage message);

    Object process(final IMessage message, final int order);
}
