package com.epam.jwd.core_final.factory.impl;

import com.epam.jwd.core_final.domain.CrewMember;
import com.epam.jwd.core_final.domain.Rank;
import com.epam.jwd.core_final.domain.Role;
import com.epam.jwd.core_final.factory.EntityFactory;

// do the same for other entities
public class CrewMemberFactory implements EntityFactory<CrewMember> {

    static private Long crewMemberId = 1L;

    static private CrewMemberFactory factory;

    private CrewMemberFactory(){

    }

    public static CrewMemberFactory getFactory(){
        if(factory == null){
            factory = new CrewMemberFactory();
        }
        return factory;
    }

    @Override
    public CrewMember create(Object... args) {

            return new CrewMember((String)args[0], (Role)args[1], (Rank)args[2], crewMemberId++);
    }
}
