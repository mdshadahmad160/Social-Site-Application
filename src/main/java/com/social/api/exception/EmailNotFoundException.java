package com.social.api.exception;

public class EmailNotFoundException extends RuntimeException{
    public EmailNotFoundException() {
    }

    public EmailNotFoundException(String message) {
        super(message);
    }
}
