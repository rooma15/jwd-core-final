package com.epam.jwd.core_final.domain;

import java.util.HashMap;
import java.util.Map;

/**
 * crew {@link java.util.Map<Role, Short>}
 * flightDistance {@link Long} - total available flight distance
 * isReadyForNextMissions {@link Boolean} - true by default. Set to false, after first failed mission
 */
public class Spaceship extends AbstractBaseEntity {

    private final HashMap<Role, Short> crew;

    private Long flightDistance;

    private Boolean isReadyForNextMissions;

    {
        isReadyForNextMissions = true;
    }

    public Spaceship(String name, HashMap<Role, Short> crew, Long flightDistance, Long id) {
        this.id = id;
        this.crew = crew;
        this.name = name;
        this.flightDistance = flightDistance;
    }



    public Map<Role, Short> getCrew() {
        return crew;
    }

    public Long getFlightDistance() {
        return flightDistance;
    }

    public Boolean isReadyForNextMissions() {
        return isReadyForNextMissions;
    }

    public void setReadyForNextMissions(Boolean readyForNextMissions) {
        isReadyForNextMissions = readyForNextMissions;
    }

    public void setFlightDistance(Long flightDistance) {
        this.flightDistance = flightDistance;
    }



        @Override
    public String toString() {
        return "Spaceship{" +
                "id = " + id +
                ", name = '" + name + "; " +
                "distance = " + flightDistance + "; " +
                "crew = " + crew + "; " +
                "isReady = " + isReadyForNextMissions + "; " +
                '}';
    }

    //todo
}
