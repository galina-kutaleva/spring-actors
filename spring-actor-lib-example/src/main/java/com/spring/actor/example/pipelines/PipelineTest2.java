package com.spring.actor.example.pipelines;

import com.spring.actor.lib.pipelines.pipeline.BasePipeline;
import org.springframework.stereotype.Component;

import java.util.LinkedList;

public class PipelineTest2 extends BasePipeline {

    public PipelineTest2(String name, LinkedList<String> steps) {
        super(name, steps);
    }
}
