package com.spring.actor.example.configurations;

import com.spring.actor.example.pipelines.PipelineTest2;
import com.spring.actor.lib.pipelines.pipeline.IPipeline;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.LinkedList;

@Configuration
public class PipelineConfiguration {

    @Bean
    public IPipeline createPipeline2 () {
        return new PipelineTest2(
                "pipeline-2",
                new LinkedList<String>() {{
                    add("actor-3"); add("actor-2");
                }}
        );
    }
}
