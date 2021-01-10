package com.epam.jwd.core_final.service.impl;

import com.epam.jwd.core_final.Main;
import com.epam.jwd.core_final.criteria.Criteria;
import com.epam.jwd.core_final.criteria.SpaceshipCriteria;
import com.epam.jwd.core_final.decorator.impl.SpaceshipPreProcessFactory;
import com.epam.jwd.core_final.domain.FlightMission;
import com.epam.jwd.core_final.domain.Role;
import com.epam.jwd.core_final.domain.Spaceship;
import com.epam.jwd.core_final.exception.DuplicateEntityObjectException;
import com.epam.jwd.core_final.exception.SpaceshipUnavailableException;
import com.epam.jwd.core_final.factory.impl.SpaceshipFactory;
import com.epam.jwd.core_final.service.BaseEntityService;
import com.epam.jwd.core_final.service.SpaceshipService;

import java.util.*;
import java.util.stream.Collectors;

public class SpaceshipServiceImpl implements SpaceshipService {

    private static SpaceshipServiceImpl service;

    private SpaceshipServiceImpl(){

    }

    public static SpaceshipServiceImpl getService(){
        if(service == null){
            service = new SpaceshipServiceImpl();
        }
        return service;
    }

    @Override
    public List<Spaceship> findAllSpaceships() {
        Main.LOGGER.info("finding all sapceships");
        return new ArrayList<>(BaseEntityService.context.retrieveBaseEntityList(Spaceship.class));
    }

    @Override
    public List<Spaceship> findAllSpaceshipsByCriteria(Criteria<? extends Spaceship> criteria) {
        List<Spaceship> spaceships =
                new ArrayList<>(BaseEntityService.context.retrieveBaseEntityList(Spaceship.class));
        SpaceshipCriteria myCriteria = (SpaceshipCriteria)criteria;

        return spaceships.stream()
                .filter(spaceship -> myCriteria.getBottomBorderAmountCrew() == null
                        ||  spaceship.getCrew().size() >= myCriteria.getBottomBorderAmountCrew())
                .filter(spaceship -> myCriteria.getTopBorderAmountCrew() == null
                        || spaceship.getCrew().size() <= myCriteria.getTopBorderAmountCrew())
                .filter(spaceship -> myCriteria.getAmountOfCrewMembers() == null
                        || spaceship.getCrew().size() == myCriteria.getAmountOfCrewMembers())
                .filter(spaceship -> myCriteria.getFlightDistance() == null
                        || spaceship.getFlightDistance().equals(myCriteria.getFlightDistance()))
                .filter(spaceship -> myCriteria.getBottomBorderFlightDistance() == null
                        || spaceship.getFlightDistance() >= myCriteria.getBottomBorderFlightDistance())
                .filter(spaceship -> myCriteria.getTopBorderFlightDistance() == null
                        || spaceship.getFlightDistance() <= myCriteria.getTopBorderFlightDistance())
                .filter(spaceship -> myCriteria.getReadyForNextMissions() == null
                        || spaceship.isReadyForNextMissions() == myCriteria.getReadyForNextMissions())
                .filter(spaceship -> myCriteria.getName() == null
                        || spaceship.getName().equals(myCriteria.getName()))
                .collect(Collectors.toList());
    }

    @Override
    public Optional<Spaceship> findSpaceshipByCriteria(Criteria<? extends Spaceship> criteria) {
        return findAllSpaceshipsByCriteria(criteria).stream().findFirst();
    }

    @Override
    public Spaceship updateSpaceshipDetails(Spaceship spaceship) {
        Main.LOGGER.info("updating spaceship details");
        Scanner in = new Scanner(System.in);
        System.out.println("Enter new spaceship name: ");
        String name = in.nextLine();
        System.out.println("Enter new total flight distance: ");
        Long flightDistance = in.nextLong();
        System.out.println("Enter amount of mission specialists: ");
        Short mission_specialists = in.nextShort();
        System.out.println("Enter amount of flight engineers: ");
        Short flight_engineers = in.nextShort();
        System.out.println("Enter amount of pilots: ");
        Short pilots = in.nextShort();
        System.out.println("Enter amount of mission commanders: ");
        Short commanders = in.nextShort();
        System.out.println("Is this spaceship ready fro next missions ? ");
        Boolean isReady = in.nextLine().equals("yes");
        Main.LOGGER.info("filling CrewScheme");
        HashMap<Role, Short> crewScheme = new HashMap<>();
        crewScheme.put(Role.MISSION_SPECIALIST, mission_specialists);
        crewScheme.put(Role.FLIGHT_ENGINEER, flight_engineers);
        crewScheme.put(Role.PILOT, pilots);
        crewScheme.put(Role.COMMANDER, commanders);
        Spaceship newSpaceship = SpaceshipPreProcessFactory.getDecorator(SpaceshipFactory.getFactory())
                .create(name, crewScheme, flightDistance);
        Collection<Spaceship> spaceships = BaseEntityService.context.retrieveBaseEntityList(Spaceship.class);
        spaceships.removeIf(thisSpaceship -> thisSpaceship.getId().equals(spaceship.getId()));
        newSpaceship.setReadyForNextMissions(isReady);
        spaceships.add(newSpaceship);
        Main.LOGGER.info("finish updating spaceship info");
        return newSpaceship;
    }

    @Override
    public void assignSpaceshipOnMission(Spaceship spaceship) throws SpaceshipUnavailableException {
        Main.LOGGER.info("assinging spaceship with id = {}", spaceship.getId());
        Scanner in = new Scanner(System.in);
        System.out.println("Enter mission name: ");
        String name = in.nextLine();
        Optional<FlightMission> flightMission = BaseEntityService.context.retrieveBaseEntityList(FlightMission.class)
                .stream()
                .filter(mission -> mission.getMissionName().equals(name))
                .findFirst();
        if(flightMission.isPresent()){
            FlightMission mission = flightMission.get();
            if(spaceship.isReadyForNextMissions()){
                mission.setAssignedSpaceShift(spaceship);
            }else {
                Main.LOGGER.error("This spaceship is not ready for missions");
                throw new SpaceshipUnavailableException("This spaceship is not ready for missions");
            }
        }
    }

    @Override
    public Spaceship createSpaceship(Spaceship newSpaceship) throws DuplicateEntityObjectException {
        Main.LOGGER.info("Creating spaceship");
        Collection<Spaceship> spaceships = BaseEntityService.context.retrieveBaseEntityList(Spaceship.class);
        if(newSpaceship == null){
            Main.LOGGER.error("Arguments can not be null");
            throw new IllegalArgumentException("Arguments can not be null");
        }
        for(Spaceship spaceship : spaceships) {
            if(spaceship.getName().equals(newSpaceship.getName())){
                throw new DuplicateEntityObjectException("spaceship with name " + spaceship.getName() + " is already in the list");
            }
        }
        spaceships.add(newSpaceship);
        return newSpaceship;
    }
}
