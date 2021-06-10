package com.spring.actor.lib.pipelines.configurations;

import com.spring.actor.lib.pipelines.message_processor.IMessageProcessor;
import com.spring.actor.lib.pipelines.message_processor.MessageProcessor;
import com.spring.actor.lib.pipelines.pipeline.IPipeline;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class MessageProcessorConfiguration {

    public final ApplicationContext context;

    public MessageProcessorConfiguration(ApplicationContext context) {
        this.context = context;
    }

    @Bean
    public IMessageProcessor messageProcessorConfig(final List<IPipeline> pipelines) {
        return new MessageProcessor(context, 4, pipelines);
    }
}
