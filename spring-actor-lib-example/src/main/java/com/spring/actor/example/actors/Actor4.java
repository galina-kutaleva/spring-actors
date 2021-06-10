package com.spring.actor.example.actors;

import com.spring.actor.lib.database_operations.IDatabaseOperationManagement;
import com.spring.actor.lib.pipelines.message.IMessage;
import com.spring.actor.lib.pipelines.pipeline.IPipelineStep;
import com.spring.actor.lib.pipelines.response.Response;
import org.springframework.stereotype.Component;

@Component("actor-4")
public class Actor4 implements IPipelineStep {

    private IDatabaseOperationManagement databaseOperationManagement;

    public Actor4(IDatabaseOperationManagement databaseOperationManagement) {
        this.databaseOperationManagement = databaseOperationManagement;
    }

    @Override
    public IMessage execute(IMessage message) {
        try {
            System.out.println("Current thread - " + Thread.currentThread().getName() + ", pipeline - 1, actor - 4");
        } catch (Exception e) {

        }
        Response response = new Response();
        response.setValue("field1", message.getValue("field1"));
        message.setValue(
                "#response",
                response
        );
        return message;
    }
}
