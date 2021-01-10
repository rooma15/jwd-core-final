package com.epam.jwd.core_final.exception;

public class EntityObjectDoesNotExistException extends RuntimeException{
    public EntityObjectDoesNotExistException() {
    }

    public EntityObjectDoesNotExistException(String message) {
        super(message);
    }
}
