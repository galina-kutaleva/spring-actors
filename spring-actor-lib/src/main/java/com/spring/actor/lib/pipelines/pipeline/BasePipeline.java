package com.spring.actor.lib.pipelines.pipeline;

import com.spring.actor.lib.pipelines.annotations.Pipeline;
import org.springframework.context.ApplicationContext;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.stream.Collectors;

public abstract class BasePipeline implements IPipeline {

    private final String name;
    private LinkedList<String> steps;

    public BasePipeline()
    {
        this.name = this.getClass().getAnnotation(Pipeline.class).value();
        LinkedList<String> declaredSteps = new LinkedList(
                Arrays.asList(this.getClass().getAnnotation(Pipeline.class).steps())
        );
        this.steps = declaredSteps;
    }

    public BasePipeline(final String name, final LinkedList<String> steps) {
        this.name = name;
        this.steps = steps;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public LinkedList<IPipelineStep> getSteps(final Object object) {

        return this.createStepSequence(object);
    }

    public LinkedList<IPipelineStep> createStepSequence(final Object object) {
            return steps
                    .stream()
                    .map(
                            s -> (IPipelineStep) ((ApplicationContext) object).getBean(s)
                    )
                    .collect(Collectors.toCollection(LinkedList::new));
    }
}
