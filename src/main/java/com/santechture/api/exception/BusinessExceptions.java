package com.santechture.api.exception;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class BusinessExceptions extends RuntimeException {
    private final Object[] args;

    public BusinessExceptions(String message, Object... args) {
        super(message);
        this.args = args;
        log.error(message);
    }

    public BusinessExceptions(Exception e) {
        super(e);
        this.args = new Object[]{};
        log.error(e.getMessage(), e);
    }

    public Object[] getArgs() {
        return args;
    }
}