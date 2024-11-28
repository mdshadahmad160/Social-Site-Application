package com.social.api.exception;

public class InvalidOperationException extends RuntimeException{

    public InvalidOperationException() {
    }

    public InvalidOperationException(String message) {
        super(message);
    }
}
