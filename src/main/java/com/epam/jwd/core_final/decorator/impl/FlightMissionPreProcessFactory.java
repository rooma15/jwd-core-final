package com.epam.jwd.core_final.decorator.impl;

import com.epam.jwd.core_final.Main;
import com.epam.jwd.core_final.decorator.AbstractPreProcessFactory;
import com.epam.jwd.core_final.domain.FlightMission;
import com.epam.jwd.core_final.domain.MissionStatus;
import com.epam.jwd.core_final.factory.EntityFactory;

import java.time.LocalDate;

public class FlightMissionPreProcessFactory extends AbstractPreProcessFactory<FlightMission> {


    private static FlightMissionPreProcessFactory decorator;

    private FlightMissionPreProcessFactory(EntityFactory<FlightMission> factory){
        super(factory);
    }

    public static FlightMissionPreProcessFactory getDecorator(EntityFactory<FlightMission> factory){
        if(decorator == null){
            decorator = new FlightMissionPreProcessFactory(factory);
        }
        return decorator;
    }

    @Override
    protected void preProcess(Object... args){
        for(Object arg : args) {
            if(arg == null){
                throw new IllegalArgumentException("arguments can not be null");
            }
        }

        if(((String) args[0]).equals("")){
            throw new IllegalArgumentException("Name can not be empty");
        }

        if((Long)args[3] <= 0){
            throw new IllegalArgumentException("Distance can not be less than 0");
        }

        LocalDate startDate = (LocalDate)args[1];


        LocalDate endDate = (LocalDate)args[2];
        if(endDate.compareTo(startDate) < 0){
            throw new IllegalArgumentException("incorrect endDate");
        }
    }

    public FlightMission create(String missionName,
                                LocalDate startDate,
                                LocalDate endDate,
                                Long distance,
                                MissionStatus missionStatus){
        Main.LOGGER.info("preprocessing before creating mission");
        preProcess(missionName, startDate, endDate, distance, missionStatus);
        Main.LOGGER.info("creating mission");
        return factory.create(missionName, startDate, endDate, distance, missionStatus);
    }
}
