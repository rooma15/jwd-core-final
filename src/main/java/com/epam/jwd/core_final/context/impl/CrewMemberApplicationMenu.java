package com.epam.jwd.core_final.context.impl;

import com.epam.jwd.core_final.Main;
import com.epam.jwd.core_final.context.Action;
import com.epam.jwd.core_final.context.ApplicationContext;
import com.epam.jwd.core_final.context.ApplicationMenu;
import com.epam.jwd.core_final.domain.CrewMember;
import com.epam.jwd.core_final.exception.EntityObjectDoesNotExistException;
import com.epam.jwd.core_final.service.BaseEntityService;
import com.epam.jwd.core_final.service.impl.CrewServiceImpl;


import java.util.Optional;
import java.util.Scanner;

public class CrewMemberApplicationMenu implements ApplicationMenu {



    @Override
    public ApplicationContext getApplicationContext() {
        return BaseEntityService.context;
    }

    @Override
    public Action printAvailableOptions() {
        System.out.println("Please, choose one of the options below: ");
        System.out.println("1. Get information about Crew Members;");
        System.out.println("2. Update information about Crew Members;");
        System.out.println("3. Assign crew member on mission;");
        System.out.println("4. Exit;");
        System.out.print("Type what option you want to choose: ");
        Scanner in = new Scanner(System.in);
        return CrewMemberAction.getActionById(in.nextInt());
    }


    @Override
    public void handleUserInput(Action action) {
        CrewMemberAction crewMemberAction = (CrewMemberAction)action;
        ApplicationContext context = getApplicationContext();
        Scanner in = new Scanner(System.in);
        switch (crewMemberAction){
            case GET_CREW_MEMBERS -> {
                context.retrieveBaseEntityList(CrewMember.class).forEach(System.out::println);
            }
            case UPDATE_CREW_MEMBERS -> {
                context.retrieveBaseEntityList(CrewMember.class).forEach(System.out::println);
                System.out.println("Enter name of crew member to be updated: ");
                Main.LOGGER.info("Enter name of crew member to be updated");
                String name = in.nextLine();
                Optional<CrewMember> optionalCrewMember = BaseEntityService.context.retrieveBaseEntityList(CrewMember.class)
                        .stream()
                        .filter(member -> member.getName().equals(name))
                        .findFirst();
                if(optionalCrewMember.isPresent()){
                    CrewServiceImpl crewService = CrewServiceImpl.getCrewService();
                    crewService.updateCrewMemberDetails(optionalCrewMember.get());
                }else {

                    Main.LOGGER.error("This crew member does not exist");
                    throw new EntityObjectDoesNotExistException("This crew member does not exist");
                }
            }

            case ASSIGN_CREW_MEMBER_ON_MISSION -> {
                context.retrieveBaseEntityList(CrewMember.class).forEach(System.out::println);
                CrewServiceImpl crewService = CrewServiceImpl.getCrewService();
                System.out.println("Enter name of crew member to be assigned on mission: ");
                Main.LOGGER.info("Enter name of crew member to be assigned on mission: ");
                String name = in.nextLine();
                Optional<CrewMember> optionalCrewMember = BaseEntityService.context
                        .retrieveBaseEntityList(CrewMember.class)
                        .stream()
                        .filter(member -> member.getName().equals(name))
                        .findFirst();
                if(optionalCrewMember.isPresent()){
                    CrewMember crewMember = optionalCrewMember.get();
                    crewService.assignCrewMemberOnMission(crewMember);
                }else {
                    Main.LOGGER.error("This crew member does not exist");
                    throw new EntityObjectDoesNotExistException("This crew member does not exist");
                }
            }

            case EXIT -> {}
        }
    }
}
