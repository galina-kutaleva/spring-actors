package com.spring.actor.example.pipelines;

import com.spring.actor.lib.pipelines.annotations.Pipeline;
import com.spring.actor.lib.pipelines.pipeline.BasePipeline;
import org.springframework.stereotype.Component;

@Component()
@Pipeline(
        value = "pipeline-1", steps = {
                "actor-1", "actor-4"
        }
)
public class PipelineTest1 extends BasePipeline {
}
