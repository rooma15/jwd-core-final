package com.epam.jwd.core_final.context.impl;

import com.epam.jwd.core_final.context.Action;
import com.epam.jwd.core_final.exception.UnknownActionException;

public enum CrewMemberAction implements Action {
    GET_CREW_MEMBERS(1),
    UPDATE_CREW_MEMBERS(2),
    ASSIGN_CREW_MEMBER_ON_MISSION(3),
    EXIT(4);

    private final int id;

    CrewMemberAction(int id){
        this.id = id;
    }
    public static CrewMemberAction getActionById(int id){
        for(CrewMemberAction action : values()) {
            if(action.id == id){
                return action;
            }
        }
        throw new UnknownActionException();
    }
}
