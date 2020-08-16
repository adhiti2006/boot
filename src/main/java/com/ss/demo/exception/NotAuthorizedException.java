package com.ss.demo.exception;

public class NotAuthorizedException extends RuntimeException{
    public NotAuthorizedException(String msg) {
        super(msg);
    }
}
