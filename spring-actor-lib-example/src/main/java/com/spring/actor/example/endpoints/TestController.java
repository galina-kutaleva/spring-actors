package com.spring.actor.example.endpoints;

import com.spring.actor.lib.pipelines.message.IMessage;
import com.spring.actor.lib.pipelines.message.Message;
import com.spring.actor.lib.pipelines.message_processor.IMessageProcessor;
import com.spring.actor.lib.pipelines.message_processor.MessageParameters;
import com.spring.actor.lib.web.filtering.Criterion;
import com.spring.actor.lib.web.filtering.Filtering;
import com.spring.actor.lib.web.pagination.Pagination;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/test")
public class TestController {

    IMessageProcessor messageProcessor;

    public TestController(
            IMessageProcessor messageProcessor
    ) {
        this.messageProcessor = messageProcessor;
    }

    @RequestMapping(
            value = "/test1",
            produces = { MediaType.APPLICATION_JSON_VALUE },
            consumes = { MediaType.APPLICATION_JSON_VALUE },
            method = RequestMethod.POST
    )
    public Object testEndpoint1(
            final @Pagination Pageable pageable,
            final @Filtering List<Criterion> criteria,
            @RequestBody final Map object
    ) {
        IMessage message = new Message("pipeline-1");
        message
                .setValue(MessageParameters.MESSAGE_BODY_SECTION, object)
                .setValue(MessageParameters.MESSAGE_PAGEABLE_SECTION, pageable)
                .setValue(MessageParameters.MESSAGE_CRITERIA_SECTION, criteria);

        return this.messageProcessor.process(message, 1);
    }

    @RequestMapping(
            value = "/test2",
            produces = { MediaType.APPLICATION_JSON_VALUE },
            consumes = { MediaType.APPLICATION_JSON_VALUE },
            method = RequestMethod.POST
    )
    public Object testEndpoint2(
            final @Pagination Pageable pageable,
            final @Filtering List<Criterion> criteria,
            @RequestBody final Map object
    ) {
        IMessage message = new Message("pipeline-2");
        message
                .setValue(MessageParameters.MESSAGE_BODY_SECTION, object)
                .setValue(MessageParameters.MESSAGE_PAGEABLE_SECTION, pageable)
                .setValue(MessageParameters.MESSAGE_CRITERIA_SECTION, criteria);


        return this.messageProcessor.process(message, 2);
    }

    @RequestMapping(
            value = "/test3",
            produces = { MediaType.APPLICATION_JSON_VALUE },
            consumes = { MediaType.APPLICATION_JSON_VALUE },
            method = RequestMethod.POST
    )
    public Object testEndpoint3(
            final @Pagination Pageable pageable,
            final @Filtering List<Criterion> criteria,
            @RequestBody final Map object
    ) {
        IMessage message = new Message("pipeline-2");
        message
                .setValue(MessageParameters.MESSAGE_BODY_SECTION, object)
                .setValue(MessageParameters.MESSAGE_PAGEABLE_SECTION, pageable)
                .setValue(MessageParameters.MESSAGE_CRITERIA_SECTION, criteria);


        return this.messageProcessor.process(message);
    }

}
