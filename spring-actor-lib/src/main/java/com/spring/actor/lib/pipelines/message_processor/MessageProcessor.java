package com.spring.actor.lib.pipelines.message_processor;

import com.spring.actor.lib.pipelines.message.IMessage;
import com.spring.actor.lib.pipelines.pipeline.IPipeline;
import com.spring.actor.lib.pipelines.pipeline.IPipelineStep;
import com.spring.actor.lib.pipelines.pipeline.IPipelineSteps;
import com.spring.actor.lib.pipelines.pipeline.PipelineSteps;
import com.spring.actor.lib.pipelines.response.IResponse;
import com.spring.actor.lib.pipelines.utils.OrderedLinkedList;
import org.springframework.context.ApplicationContext;

import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

public class MessageProcessor implements IMessageProcessor {

    private final static int DEFAULT_ORDER = 100;


    private final Map<String, IPipeline> pipelines;
    private final OrderedLinkedList<Integer> queueOrder = new OrderedLinkedList<>();
    private final HashMap<Integer, ConcurrentLinkedQueue<IMessage>> queues = new HashMap<>();
    private final ApplicationContext context;
    private final Executor executor;

    private int currentQueue = 0;

    public MessageProcessor(final ApplicationContext context, final int threadNumber, final List<IPipeline> pipelines) {
        this.executor = Executors.newFixedThreadPool(threadNumber);
        this.pipelines = pipelines.stream().collect(Collectors.toMap(IPipeline::getName, p -> p));
        this.context = context;
        this.queueOrder.add(DEFAULT_ORDER);
        this.queues.put(DEFAULT_ORDER, new ConcurrentLinkedQueue<>());
        this.run();
    }

    @Override
    public Object process(final IMessage message) {
        return this.process(message, DEFAULT_ORDER);
    }

    @Override
    public Object process(final IMessage message, final int order) {
        String pipelineId = message.getPipelineId();
        IPipeline pipeline = this.pipelines.get(pipelineId);
        if (null == pipeline) {
            throw new RuntimeException("Pipeline with name " + pipelineId + " not found.");
        }
        LinkedList<IPipelineStep> steps = pipeline.getSteps(this.context);
        message.setValue("#steps", new PipelineSteps(steps));
        ConcurrentLinkedQueue<IMessage> queue = this.queues.get(order);
        synchronized (queueOrder) {
            if (null == queue) {
                Collections.sort(this.queueOrder);
                queue = new ConcurrentLinkedQueue<>();
                queues.put(order, queue);
                queueOrder.orderedAdd(order);
            }
        }
        queue.add(message);
        try {
            synchronized (message) {
                message.wait();
            }
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        IResponse response = (IResponse) message.getValue(MessageParameters.MESSAGE_RESPONSE_SECTION);

        return response;
    }

    private void run() {
        Thread polling = new Thread(
                () -> {
                    try {
                        while (true) {
                            try {
                                int queueKey = this.queueOrder.get(this.currentQueue);
                                ConcurrentLinkedQueue<IMessage> queue = this.queues.get(queueKey);
                                if (null != queue) {
                                    IMessage message = queue.poll();
                                    if (null != message) {
                                        IPipelineSteps stepIterator = (IPipelineSteps) message.getValue(MessageParameters.MESSAGE_STEPS_SECTION);
                                        if (stepIterator.hasNext()) {
                                            IPipelineStep step = stepIterator.next();
                                            this.executor.execute(
                                                    new Task(queue, message, step, !stepIterator.hasNext())
                                            );
                                        }
                                        this.currentQueue = 0;
                                    } else {
                                        this.currentQueue++;
                                        if (this.currentQueue >= this.queueOrder.size()) {
                                            this.currentQueue = 0;
                                        }
                                    }
                                }
                                Thread.sleep(10);
                            } catch (Exception e) {
                                System.err.println(e);
                            }
                        }
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                },
                MessageProcessorParameters.MESSAGE_PROCESSOR_THREAD_NAME
        );
        polling.start();
    }
}
