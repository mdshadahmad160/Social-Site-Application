package com.social.api.exception;

public class EmptyPostException extends RuntimeException{

    public EmptyPostException() {
    }

    public EmptyPostException(String message) {
        super(message);
    }
}
