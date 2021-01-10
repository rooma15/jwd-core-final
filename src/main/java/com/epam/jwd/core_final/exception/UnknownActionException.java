package com.epam.jwd.core_final.exception;

public class UnknownActionException extends RuntimeException{
    public UnknownActionException() {
        super();
    }

    public UnknownActionException(String message) {
        super(message);
    }
}
