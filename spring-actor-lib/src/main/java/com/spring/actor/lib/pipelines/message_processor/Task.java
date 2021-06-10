package com.spring.actor.lib.pipelines.message_processor;

import com.spring.actor.lib.pipelines.message.IMessage;
import com.spring.actor.lib.pipelines.pipeline.IPipelineStep;

import java.util.concurrent.ConcurrentLinkedQueue;

public class Task implements Runnable {

    private final IMessage message;
    private final IPipelineStep pipelineStep;
    private final boolean last;
    private ConcurrentLinkedQueue<IMessage> queue;

    public Task(
            final ConcurrentLinkedQueue<IMessage> queue, final IMessage message, final IPipelineStep pipelineStep, final boolean last
    ) {
        this.message = message;
        this.pipelineStep = pipelineStep;
        this.last = last;
        this.queue = queue;
    }

    @Override
    public void run() {
        try {
            this.pipelineStep.execute(this.message);
        } catch (Exception e) {
            message.setValue(MessageParameters.MESSAGE_ERROR_SECTION, e);
        }
        if (
                   null != message.getValue(MessageParameters.MESSAGE_RESPONSE_SECTION)
                || null != message.getValue(MessageParameters.MESSAGE_ERROR_SECTION)
                || last
        ) {
            synchronized (this.message) {
                this.message.notify();
            }
        }
        if (!last) {
            queue.add(message);
        }
    }
}
