package com.spring.actor.lib.pipelines.pipeline;

import java.util.Iterator;
import java.util.LinkedList;

public class PipelineSteps implements IPipelineSteps {

    private final Iterator<IPipelineStep> iterator;

    public PipelineSteps(LinkedList<IPipelineStep> list) {
        this.iterator = list.listIterator();
    }

    @Override
    public IPipelineStep next() {
        if (iterator.hasNext()) {
            return iterator.next();
        } else {
            return null;
        }
    }

    @Override
    public boolean hasNext() {
        return iterator.hasNext();
    }
}
