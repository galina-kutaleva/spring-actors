package com.spring.actor.example.actors;

import com.spring.actor.lib.database_operations.IDatabaseOperationManagement;
import com.spring.actor.lib.database_operations.QueryHelper;
import com.spring.actor.lib.database_operations.filters.IQueryFilter;
import com.spring.actor.lib.database_operations.operations.DatabaseOperationParameters;
import com.spring.actor.lib.database_operations.operations.Page;
import com.spring.actor.lib.database_operations.operations.QueryParameters;
import com.spring.actor.lib.pipelines.message.IMessage;
import com.spring.actor.lib.pipelines.message_processor.MessageParameters;
import com.spring.actor.lib.pipelines.pipeline.IPipelineStep;
import com.spring.actor.lib.web.filtering.Criterion;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.List;

@Component("actor-1")
public class Actor1 implements IPipelineStep {

    private IDatabaseOperationManagement databaseOperationManagement;

    public Actor1(IDatabaseOperationManagement databaseOperationManagement) {
        this.databaseOperationManagement = databaseOperationManagement;
    }

    @Override
    public IMessage execute(IMessage message) {
        try {
            System.out.println("Current thread - " + Thread.currentThread().getName() + ", pipeline - 1, actor - 1");
            message.getValue(MessageParameters.MESSAGE_CRITERIA_SECTION);
            Page page = (Page) this.databaseOperationManagement.execute(
                    new QueryParameters()
                            .setQueryName("query2")
                            .setQueryType(DatabaseOperationParameters.GET_PAGE)
                            .setCriteria(
                                    "FILTER_1", (List<Criterion>) message.getValue(MessageParameters.MESSAGE_CRITERIA_SECTION)
                            )
                            .setPagination("%PAGE%", (Pageable) message.getValue(MessageParameters.MESSAGE_PAGEABLE_SECTION))
            );
            message.setValue("test_page", page);
            return message;
        } catch (Exception e) {
            throw new RuntimeException("Could not execute actor 1", e);
        }
    }
}
