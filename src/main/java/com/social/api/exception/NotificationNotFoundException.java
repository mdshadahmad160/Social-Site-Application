package com.social.api.exception;

public class NotificationNotFoundException extends RuntimeException{

    public NotificationNotFoundException() {
    }

    public NotificationNotFoundException(String message) {
        super(message);
    }
}
