package com.social.api.exception;

public class ShareNotFoundException extends RuntimeException{
    public ShareNotFoundException() {
    }

    public ShareNotFoundException(String message) {
        super(message);
    }
}
