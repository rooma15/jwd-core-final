package com.epam.jwd.core_final.context.impl;

import com.epam.jwd.core_final.context.Action;
import com.epam.jwd.core_final.exception.UnknownActionException;

public enum DefaultAction implements Action {
    SELECT_CREW_MEMBERS(1),
    SELECT_SPACESHIPS(2),
    SELECT_MISSIONS(3),
    EXIT(4);

    private final int id;

    DefaultAction(int id){
        this.id = id;
    }
    public static DefaultAction getActionById(int id){
        for(DefaultAction action : values()) {
            if(action.id == id){
                return action;
            }
        }
        throw new UnknownActionException();
    }
}
