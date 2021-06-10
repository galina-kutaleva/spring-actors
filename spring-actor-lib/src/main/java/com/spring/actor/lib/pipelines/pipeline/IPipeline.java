package com.spring.actor.lib.pipelines.pipeline;

import java.util.LinkedList;

public interface IPipeline {

    String getName();

    LinkedList<IPipelineStep> getSteps(final Object object);
}
