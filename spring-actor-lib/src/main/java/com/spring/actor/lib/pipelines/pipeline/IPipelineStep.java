package com.spring.actor.lib.pipelines.pipeline;


import com.spring.actor.lib.pipelines.message.IMessage;

public interface IPipelineStep {

    IMessage execute(IMessage message);
}
