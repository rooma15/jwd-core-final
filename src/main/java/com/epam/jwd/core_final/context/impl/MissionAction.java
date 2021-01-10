package com.epam.jwd.core_final.context.impl;

import com.epam.jwd.core_final.context.Action;
import com.epam.jwd.core_final.exception.UnknownActionException;

public enum MissionAction implements Action {
    CREATE_MISSION(1),
    UPDATE_MISSION(2),
    CHANGE_MISSION_STATUS(3),
    PRINT_MISSIONS_IN_FILE(4),
    EXIT(5);

    private final int id;

    MissionAction(int id){
        this.id = id;
    }
    public static MissionAction getActionById(int id){
        for(MissionAction action : values()) {
            if(action.id == id){
                return action;
            }
        }
        throw new UnknownActionException();
    }
}
