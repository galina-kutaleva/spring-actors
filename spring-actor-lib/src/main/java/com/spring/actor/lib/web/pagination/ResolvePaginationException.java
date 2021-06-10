package com.spring.actor.lib.web.pagination;

import com.spring.actor.lib.web.ResolveArgumentsException;

public class ResolvePaginationException extends ResolveArgumentsException {
    public ResolvePaginationException(String s) {
        super(s);
    }

    public ResolvePaginationException(String s, Throwable throwable) {
        super(s, throwable);
    }

    public ResolvePaginationException(Throwable throwable) {
        super(throwable);
    }
}
