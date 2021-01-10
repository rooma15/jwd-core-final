package com.epam.jwd.core_final.exception;

public class CrewMemberUnavailableException extends RuntimeException{
    public CrewMemberUnavailableException() {
    }

    public CrewMemberUnavailableException(String message) {
        super(message);
    }
}
