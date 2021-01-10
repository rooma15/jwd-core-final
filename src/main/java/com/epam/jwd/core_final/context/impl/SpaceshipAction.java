package com.epam.jwd.core_final.context.impl;

import com.epam.jwd.core_final.context.Action;
import com.epam.jwd.core_final.exception.UnknownActionException;

public enum SpaceshipAction implements Action {
    GET_SPACESHIPS(1),
    UPDATE_SPACESHIPS(2),
    ASSIGN_SPACESHIP_ON_MISSION(3),
    EXIT(4);

    private final int id;

    SpaceshipAction(int id){
        this.id = id;
    }
    public static SpaceshipAction getActionById(int id){
        for(SpaceshipAction action : values()) {
            if(action.id == id){
                return action;
            }
        }
        throw new UnknownActionException();
    }
}
