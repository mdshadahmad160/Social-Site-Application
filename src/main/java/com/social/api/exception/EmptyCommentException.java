package com.social.api.exception;

public class EmptyCommentException extends RuntimeException{

    public EmptyCommentException() {
    }

    public EmptyCommentException(String message) {
        super(message);
    }
}
