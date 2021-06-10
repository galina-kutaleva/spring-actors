package com.spring.actor.example.endpoints;

import org.springframework.stereotype.Service;

@Service
public class TestService {

    public TestService() {
    }

    public ViewModel get(final int id) {
        return new ViewModel();
    }

    public BusinessModel create(final FormModel form) {
        return new BusinessModel();
    }
}
