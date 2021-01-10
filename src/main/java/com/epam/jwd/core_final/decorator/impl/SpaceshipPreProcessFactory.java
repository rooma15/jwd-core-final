package com.epam.jwd.core_final.decorator.impl;

import com.epam.jwd.core_final.Main;
import com.epam.jwd.core_final.decorator.AbstractPreProcessFactory;
import com.epam.jwd.core_final.domain.Role;
import com.epam.jwd.core_final.domain.Spaceship;
import com.epam.jwd.core_final.factory.EntityFactory;
import java.util.HashMap;

public class SpaceshipPreProcessFactory extends AbstractPreProcessFactory<Spaceship> {


    private static SpaceshipPreProcessFactory decorator;

    private SpaceshipPreProcessFactory(EntityFactory<Spaceship> factory){
        super(factory);
    }

    public static SpaceshipPreProcessFactory getDecorator(EntityFactory<Spaceship> factory){
        if(decorator == null){
            decorator = new SpaceshipPreProcessFactory(factory);
        }
        return decorator;
    }

    @Override
    protected void preProcess(Object... args){
        if(args[0] == null || args[1] == null || args[2] == null){
            throw new IllegalArgumentException("Arguments can not ne null");
        }

        HashMap<Role, Short> crew = (HashMap<Role, Short>)args[1];
        if(crew.isEmpty()){
            Main.LOGGER.error("Crew can not be empty");
            throw new IllegalArgumentException("Crew can not be empty");
        }

        if(((String) args[0]).equals("")){
            Main.LOGGER.error("Name can not be empty");
            throw new IllegalArgumentException("Name can not empty");
        }

        if((Long)args[2] < 0){
            Main.LOGGER.error("Flight distance can not be less than 0");
            throw new IllegalArgumentException("Flight distance can not be less than 0");
        }
    }

    public Spaceship create(String name, HashMap<Role, Short> crew, Long flightDistance){
        Main.LOGGER.info("start preprocessing spaceship");
        preProcess(name, crew, flightDistance);
        return factory.create(name, crew, flightDistance);
    }

}
