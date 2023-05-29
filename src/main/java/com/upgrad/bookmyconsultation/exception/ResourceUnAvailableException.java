package com.upgrad.bookmyconsultation.exception;

public class ResourceUnAvailableException extends RuntimeException {
    public ResourceUnAvailableException() {
        super();
    }

    public ResourceUnAvailableException(String message) {
        super(message);
    }
}
