package com.epam.jwd.core_final.service.impl;

import com.epam.jwd.core_final.Main;
import com.epam.jwd.core_final.criteria.Criteria;
import com.epam.jwd.core_final.criteria.FlightMissionCriteria;
import com.epam.jwd.core_final.decorator.impl.FlightMissionPreProcessFactory;
import com.epam.jwd.core_final.domain.ApplicationProperties;
import com.epam.jwd.core_final.domain.FlightMission;
import com.epam.jwd.core_final.domain.MissionStatus;
import com.epam.jwd.core_final.exception.DuplicateEntityObjectException;
import com.epam.jwd.core_final.factory.impl.FlightMissionFactory;
import com.epam.jwd.core_final.service.BaseEntityService;
import com.epam.jwd.core_final.service.MissionService;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

public class MissionServiceImpl implements MissionService {

    private static MissionServiceImpl service;

    private MissionServiceImpl(){

    }

    public static MissionServiceImpl getService(){
        if(service == null){
            service = new MissionServiceImpl();
        }
        return service;
    }

    @Override
    public List<FlightMission> findAllMissions() {
        Main.LOGGER.info("getting all missions");
        return new ArrayList<>(BaseEntityService.context.retrieveBaseEntityList(FlightMission.class));
    }

    @Override
    public List<FlightMission> findAllMissionsByCriteria(Criteria<? extends FlightMission> criteria) {
        ArrayList<FlightMission> crewMembers =
                new ArrayList<>(BaseEntityService.context.retrieveBaseEntityList(FlightMission.class));

        FlightMissionCriteria myCriteria = (FlightMissionCriteria)criteria;

        return crewMembers.stream()
                .filter(flightMission -> myCriteria.getAssignedCrew() == null
                        || flightMission.getAssignedCrew().containsAll(myCriteria.getAssignedCrew())
                        && myCriteria.getAssignedCrew().containsAll(flightMission.getAssignedCrew()))
                .filter(flightMission -> myCriteria.getAssignedSpaceShift() == null
                        || flightMission.getAssignedSpaceShift().getName()
                        .equals(myCriteria.getAssignedSpaceShift().getName()))
                .filter(flightMission -> myCriteria.getDistance() == null
                        || flightMission.getDistance().equals(myCriteria.getDistance()))
                .filter(flightMission -> myCriteria.getStartDate() == null
                        || flightMission.getStartDate().isEqual(myCriteria.getStartDate()))
                .filter(flightMission -> myCriteria.getEndDate() == null
                        || flightMission.getEndDate().isEqual(myCriteria.getEndDate()))
                .filter(flightMission -> myCriteria.getMissionResult() == null
                        || flightMission.getMissionStatus() == myCriteria.getMissionResult())
                .filter(flightMission -> myCriteria.getName() == null
                        || flightMission.getMissionName().equals(myCriteria.getName()))
                .collect(Collectors.toList());
    }

    @Override
    public Optional<FlightMission> findMissionByCriteria(Criteria<? extends FlightMission> criteria) {
        return findAllMissionsByCriteria(criteria).stream().findFirst();
    }

    @Override
    public FlightMission updateMissionDetails(FlightMission flightMission) {
        Main.LOGGER.info("updating mission details");
        Scanner in = new Scanner(System.in);
        System.out.println("Enter new mission name: ");
        String name = in.nextLine();
        ApplicationProperties properties = new ApplicationProperties();
        System.out.println("Enter new start date, pattern - " + properties.getDateTimeFormat() + ": ");
        LocalDate startDate = LocalDate.parse(in.nextLine(),
                DateTimeFormatter.ofPattern(properties.getDateTimeFormat()));
        System.out.println("Enter new end date, pattern - " + properties.getDateTimeFormat() + ": ");
        LocalDate endDate = LocalDate.parse(in.nextLine(),
                DateTimeFormatter.ofPattern(properties.getDateTimeFormat()));
        System.out.println("Enter new mission flight distance: ");
        Long distance = Long.parseLong(in.nextLine());
        System.out.println("Enter new mission status(cancelled, failed, planned, in_progress, completed): ");
        MissionStatus missionStatus = null;
        try {
            missionStatus = MissionStatus.valueOf(in.nextLine().toUpperCase());
        } catch (IllegalArgumentException e) {
            Main.LOGGER.error("This status does not exist");
            throw new IllegalArgumentException("This status does not exist");
        }

        Collection<FlightMission> flightMissions = BaseEntityService.context.retrieveBaseEntityList(FlightMission.class);
        flightMissions.removeIf(mission -> mission.getId().equals(flightMission.getId()));
        FlightMission mission = FlightMissionPreProcessFactory.getDecorator(FlightMissionFactory.getFactory())
                .create(name, startDate, endDate, distance, missionStatus);
        flightMissions.add(mission);
        Main.LOGGER.info("finish updating mission info");
        return null;
    }

    @Override
    public FlightMission createMission(FlightMission newFlightMission) {
        Main.LOGGER.info("creating mission");
        Collection<FlightMission> flightMissions = BaseEntityService.context.retrieveBaseEntityList(FlightMission.class);
        if(newFlightMission == null){
            Main.LOGGER.error("Arguments can not be null");
            throw new IllegalArgumentException("Arguments can not be null");
        }
        for(FlightMission flightMission : flightMissions) {
            if(flightMission.getMissionName().equals(newFlightMission.getMissionName())){
                Main.LOGGER.error("This flight mission is already in the list");
                throw new DuplicateEntityObjectException("This flight mission is already in the list");
            }
        }
        flightMissions.add(newFlightMission);
        return newFlightMission;
    }
}
