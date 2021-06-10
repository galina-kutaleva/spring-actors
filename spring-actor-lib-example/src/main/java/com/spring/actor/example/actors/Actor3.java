package com.spring.actor.example.actors;

import com.spring.actor.lib.database_operations.IDatabaseOperationManagement;
import com.spring.actor.lib.pipelines.message.IMessage;
import com.spring.actor.lib.pipelines.pipeline.IPipelineStep;
import org.springframework.stereotype.Component;

@Component("actor-3")
public class Actor3 implements IPipelineStep {

    private IDatabaseOperationManagement databaseOperationManagement;

    public Actor3(IDatabaseOperationManagement databaseOperationManagement) {
        this.databaseOperationManagement = databaseOperationManagement;
    }

    @Override
    public IMessage execute(IMessage message) {
        try {
            System.out.println("Current thread - " + Thread.currentThread().getName() + ", pipeline - 2, actor - 3");
        } catch (Exception e) {

        }

        return message;
    }
}
