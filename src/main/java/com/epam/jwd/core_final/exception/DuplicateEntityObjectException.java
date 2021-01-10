package com.epam.jwd.core_final.exception;

public class DuplicateEntityObjectException extends RuntimeException{
    public DuplicateEntityObjectException() {
    }

    public DuplicateEntityObjectException(String message) {
        super(message);
    }
}
