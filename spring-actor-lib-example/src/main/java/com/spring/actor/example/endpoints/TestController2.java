package com.spring.actor.example.endpoints;

import com.spring.actor.lib.pipelines.annotations.PipelineId;
import com.spring.actor.lib.pipelines.annotations.ToMessage;
import com.spring.actor.lib.pipelines.message.IMessage;
import com.spring.actor.lib.pipelines.message_processor.IMessageProcessor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

//@Controller
public class TestController2 {

    IMessageProcessor messageProcessor;

    public TestController2(
            final IMessageProcessor messageProcessor
    ) {
        this.messageProcessor = messageProcessor;
    }

    @RequestMapping(
            value = "/test",
            produces = {MediaType.APPLICATION_JSON_VALUE},
            consumes = {MediaType.APPLICATION_JSON_VALUE},
            method = RequestMethod.GET
    )
    public Object testEndpointGet(
            @ToMessage("id") @RequestParam final int id,
            IMessage message
    ) {

        return this.messageProcessor.process(message);
    }

    @RequestMapping(
            value = "/test",
            produces = {MediaType.APPLICATION_JSON_VALUE},
            consumes = {MediaType.APPLICATION_JSON_VALUE},
            method = RequestMethod.POST
    )
    @PipelineId("pipeline-1")
    public Object testEndpointPost(
            @ToMessage("body") @RequestBody final Object form,
            IMessage message
    ) {

        return this.messageProcessor.process(message);
    }
}
