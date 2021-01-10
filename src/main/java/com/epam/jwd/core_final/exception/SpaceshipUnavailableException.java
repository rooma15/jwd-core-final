package com.epam.jwd.core_final.exception;

public class SpaceshipUnavailableException extends RuntimeException{
    public SpaceshipUnavailableException() {
    }

    public SpaceshipUnavailableException(String message) {
        super(message);
    }
}
