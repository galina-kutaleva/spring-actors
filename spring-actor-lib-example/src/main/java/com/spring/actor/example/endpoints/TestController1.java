package com.spring.actor.example.endpoints;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

//@Controller
public class TestController1 {

    TestService testService;

    public TestController1(
            final TestService testService
    ) {
        this.testService = testService;
    }

    @RequestMapping(
            value = "/test",
            produces = {MediaType.APPLICATION_JSON_VALUE},
            consumes = {MediaType.APPLICATION_JSON_VALUE},
            method = RequestMethod.GET
    )
    public ViewModel testEndpointGet(
            @RequestParam final int id
    ) {

        return this.testService.get(id);
    }

    @RequestMapping(
            value = "/test",
            produces = {MediaType.APPLICATION_JSON_VALUE},
            consumes = {MediaType.APPLICATION_JSON_VALUE},
            method = RequestMethod.POST
    )
    public Object testEndpointPost(
            @RequestBody final FormModel form
    ) {

        return this.testService.create(form);
    }
}
