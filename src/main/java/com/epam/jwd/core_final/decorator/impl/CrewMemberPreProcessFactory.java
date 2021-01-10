package com.epam.jwd.core_final.decorator.impl;

import com.epam.jwd.core_final.Main;
import com.epam.jwd.core_final.decorator.AbstractPreProcessFactory;
import com.epam.jwd.core_final.domain.CrewMember;
import com.epam.jwd.core_final.domain.Rank;
import com.epam.jwd.core_final.domain.Role;
import com.epam.jwd.core_final.factory.EntityFactory;

public class CrewMemberPreProcessFactory extends AbstractPreProcessFactory<CrewMember> {

    private static CrewMemberPreProcessFactory decorator;

    private CrewMemberPreProcessFactory(EntityFactory<CrewMember> factory){
        super(factory);
    }

    public static CrewMemberPreProcessFactory getDecorator(EntityFactory<CrewMember> factory){
        if(decorator == null){
            decorator = new CrewMemberPreProcessFactory(factory);
        }
        return decorator;
    }

    @Override
    protected void preProcess(Object... args) {

        if(args[1] == null || args[2] == null){
            throw new IllegalArgumentException("Arguments can not be null");
        }

        if(((String)args[0]).equals("")) {
            throw new IllegalArgumentException("Name can not be empty");
        }
    }

    public CrewMember create(String name, Role role, Rank rank){
        Main.LOGGER.info("creating crew member");
        return factory.create(name, role, rank);
    }
}
