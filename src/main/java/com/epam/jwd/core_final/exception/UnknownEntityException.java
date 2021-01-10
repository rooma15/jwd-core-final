package com.epam.jwd.core_final.exception;

import java.util.Arrays;
import java.util.stream.Collectors;

public class UnknownEntityException extends RuntimeException {

    private final String entityName;
    private final String[] args;

    public UnknownEntityException(String entityName) {
        super();
        this.entityName = entityName;
        this.args = null;
    }

    public UnknownEntityException(String entityName, String[] args) {
        super();
        this.entityName = entityName;
        this.args = args;
    }

    @Override
    public String getMessage() {
        // todo
        // you should use entityName, args (if necessary)
        String result;
        if(args == null){
            result = "exception connected with " + entityName;
        } else{
            String argsStr = String.join(", ", args);
            result = "exception in " + entityName + " with args: " + argsStr;
        }
        return result;
    }
}
