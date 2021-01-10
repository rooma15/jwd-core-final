package com.epam.jwd.core_final.service.impl;


import com.epam.jwd.core_final.Main;
import com.epam.jwd.core_final.criteria.CrewMemberCriteria;
import com.epam.jwd.core_final.criteria.Criteria;
import com.epam.jwd.core_final.decorator.impl.CrewMemberPreProcessFactory;
import com.epam.jwd.core_final.domain.*;
import com.epam.jwd.core_final.exception.*;
import com.epam.jwd.core_final.factory.impl.CrewMemberFactory;
import com.epam.jwd.core_final.service.BaseEntityService;
import com.epam.jwd.core_final.service.CrewService;

import java.util.*;
import java.util.stream.Collectors;

public class CrewServiceImpl implements CrewService {

    private static CrewServiceImpl crewService;



    private CrewServiceImpl(){

    }

    public static CrewServiceImpl getCrewService(){
        if(crewService == null){
            crewService = new CrewServiceImpl();
        }
        return crewService;
    }

    @Override
    public List<CrewMember> findAllCrewMembers() {
        Main.LOGGER.info("Finding all crew members");
        return new ArrayList<>(BaseEntityService.context.retrieveBaseEntityList(CrewMember.class));
    }

    @Override
    public List<CrewMember> findAllCrewMembersByCriteria(Criteria<? extends CrewMember> criteria) {

        if(criteria == null){
            Main.LOGGER.error("criteria can not be null");
            throw new IllegalArgumentException("Criteria can not be null");
        }
        ArrayList<CrewMember> crewMembers =
                new ArrayList<>(BaseEntityService.context.retrieveBaseEntityList(CrewMember.class));

        CrewMemberCriteria myCriteria = (CrewMemberCriteria)criteria;
        return crewMembers.stream()
                .filter(crewMember -> myCriteria.getRank() == null
                        || crewMember.getRank() == myCriteria.getRank())
                .filter(crewMember -> myCriteria.getRole() == null
                        || crewMember.getRole() == myCriteria.getRole())
                .filter(crewMember -> myCriteria.isReadyForNextMissions() == null
                        || crewMember.isReadyForNextMissions() == myCriteria.isReadyForNextMissions())
                .filter(crewMember -> myCriteria.getName() == null
                        || crewMember.getName().equals(myCriteria.getName()))
                .filter(crewMember -> myCriteria.getId() == null
                        || crewMember.getId().equals(myCriteria.getId()))
                .collect(Collectors.toList());
    }


    @Override
    public Optional<CrewMember> findCrewMemberByCriteria(Criteria<? extends CrewMember> criteria) {
        return findAllCrewMembersByCriteria(criteria).stream().findFirst();
    }

    @Override
    public CrewMember updateCrewMemberDetails(CrewMember crewMember) {
        Main.LOGGER.info("updating crew member details");
        Scanner in = new Scanner(System.in);
        System.out.println("Enter new name: ");
        String name = in.nextLine();
        System.out.println("Enter new Role(mission_specialist, flight_engineer, pilot, commander): ");
        Role role = null;
        try {
            role = Role.valueOf(in.nextLine().toUpperCase());
        } catch (IllegalArgumentException e) {
            Main.LOGGER.error("Role does not exist");
            System.out.println("This role does not exist");
            return crewMember;
        }
        System.out.println("Enter new Rank(trainee, second_officer, first_officer, captain): ");
        Rank rank = null;
        try {
            rank = Rank.valueOf(in.nextLine().toUpperCase());
        } catch (IllegalArgumentException e) {
            System.out.println("This rank does not exist");
            return crewMember;
        }
        System.out.println("Is crew member ready for missions?(yes/no) ");
        Boolean isReady = in.nextLine().equals("yes");
        CrewMember newCrewMember = CrewMemberPreProcessFactory.getDecorator(CrewMemberFactory.getFactory())
                .create(name, role, rank);
        Collection<CrewMember> crewMembers = BaseEntityService.context.retrieveBaseEntityList(CrewMember.class);
        Main.LOGGER.info("updating crew member, id =" + crewMember.getId());
        crewMembers.removeIf(member -> member.getId().equals(crewMember.getId()));
        crewMembers.add(newCrewMember);
        newCrewMember.setReadyForNextMissions(isReady);
        Main.LOGGER.info("finishing crew member details");
        return newCrewMember;
    }

    @Override
    public void assignCrewMemberOnMission(CrewMember crewMember) throws CrewMemberUnavailableException {
        Main.LOGGER.info("assinging crew member, id = {} on mission", crewMember.getId());
        if(!crewMember.isReadyForNextMissions()){
            Main.LOGGER.error("This crew member is not ready for missions");
            throw new CrewMemberUnavailableException("This crew member is not ready for missions");
        }
        Scanner in = new Scanner(System.in);
        System.out.println("Enter mission name: ");
        String name = in.nextLine();
        Optional<FlightMission> flightMission = BaseEntityService.context.retrieveBaseEntityList(FlightMission.class)
                .stream()
                .filter(mission -> mission.getMissionName().equals(name))
                .findFirst();
        if(flightMission.isPresent()){
            FlightMission mission = flightMission.get();
            List<CrewMember> assignedMembers = mission.getAssignedCrew();
            if(assignedMembers.stream().anyMatch(member -> member.getName().equals(crewMember.getName()))){
                Main.LOGGER.error("This crew member is already on mission");
                throw new DuplicateEntityObjectException("This crew member is already on mission");
            }

            Spaceship missionSpaceship = mission.getAssignedSpaceShift();

            if(missionSpaceship == null){
                Main.LOGGER.error("Spaceship has not been assigned yet");
                throw new SpaceshipUnavailableException("Spaceship has not been assigned yet");
            }
            Map<Role, Short> crewScheme = missionSpaceship.getCrew();
            long roleDiffedCrewMembers = assignedMembers.stream()
                    .filter(member -> member.getRole() == crewMember.getRole())
                    .count();

            if(roleDiffedCrewMembers < crewScheme.get(crewMember.getRole())){
                assignedMembers.add(crewMember);
            }else {
                Main.LOGGER.error("overflow of {} on mission", crewMember.getRole());
                throw new UnavailableAssingingException("There is enough " + crewMember.getRole() + " on this mission");
            }
        }else {
            Main.LOGGER.error("mission does not exist");
            throw new EntityObjectDoesNotExistException("This mission does not exist");
        }

    }

    @Override
    public CrewMember createCrewMember(CrewMember newCrewMember) throws DuplicateEntityObjectException {
        Collection<CrewMember> crewMembers = BaseEntityService.context.retrieveBaseEntityList(CrewMember.class);
        if(newCrewMember == null){
            Main.LOGGER.error("Arguments can not be null");
            throw new IllegalArgumentException("Arguments can not be null");
        }
        for(CrewMember crewMember : crewMembers) {
            if(crewMember.getName().equals(newCrewMember.getName())){
                Main.LOGGER.error("This crew member is already in the list");
                throw new DuplicateEntityObjectException("This crew member is already in the list");
            }
        }
        crewMembers.add(newCrewMember);
        return newCrewMember;
    }
}
