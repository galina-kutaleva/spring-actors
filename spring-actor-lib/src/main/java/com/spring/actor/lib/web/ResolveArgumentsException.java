package com.spring.actor.lib.web;

public class ResolveArgumentsException extends Exception {
    public ResolveArgumentsException(String s) {
        super(s);
    }

    public ResolveArgumentsException(String s, Throwable throwable) {
        super(s, throwable);
    }

    public ResolveArgumentsException(Throwable throwable) {
        super(throwable);
    }
}
