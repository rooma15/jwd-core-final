package com.epam.jwd.core_final.factory.impl;

import com.epam.jwd.core_final.domain.MissionStatus;
import com.epam.jwd.core_final.domain.FlightMission;
import com.epam.jwd.core_final.factory.EntityFactory;
import java.time.LocalDate;

public class FlightMissionFactory implements EntityFactory<FlightMission> {

    static private Long flightMissionId = 1L;

    static private FlightMissionFactory factory;

    private FlightMissionFactory(){

    }

    public static FlightMissionFactory getFactory(){
        if(factory == null){
            factory = new FlightMissionFactory();
        }
        return factory;
    }

    @Override
    public FlightMission create(Object... args) {

        return new FlightMission(
                (String)args[0],
                (LocalDate)args[1],
                (LocalDate)args[2],
                (Long)args[3],
                (MissionStatus)args[4],
                flightMissionId++);
    }
}
