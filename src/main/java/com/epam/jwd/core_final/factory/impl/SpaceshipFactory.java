package com.epam.jwd.core_final.factory.impl;

import com.epam.jwd.core_final.domain.Spaceship;
import com.epam.jwd.core_final.domain.Role;
import com.epam.jwd.core_final.factory.EntityFactory;

import java.util.HashMap;

public class SpaceshipFactory implements EntityFactory<Spaceship> {

    static private Long spaceshipId = 1L;

    static private SpaceshipFactory factory;

    private SpaceshipFactory(){

    }

    public static SpaceshipFactory getFactory(){
        if(factory == null){
            factory = new SpaceshipFactory();
        }
        return factory;
    }

    @Override
    public Spaceship create(Object... args) {
            return new Spaceship((String)args[0], (HashMap<Role, Short>)args[1], (Long)args[2], spaceshipId++);
    }
}
