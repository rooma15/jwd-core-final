package com.epam.jwd.core_final.context.impl;

import com.epam.jwd.core_final.context.Action;
import com.epam.jwd.core_final.context.ApplicationContext;
import com.epam.jwd.core_final.context.ApplicationMenu;
import com.epam.jwd.core_final.domain.Spaceship;
import com.epam.jwd.core_final.exception.EntityObjectDoesNotExistException;
import com.epam.jwd.core_final.service.BaseEntityService;
import com.epam.jwd.core_final.service.impl.SpaceshipServiceImpl;

import java.util.Optional;
import java.util.Scanner;

public class SpaceshipApplicationMenu implements ApplicationMenu {
    @Override
    public ApplicationContext getApplicationContext() {
        return BaseEntityService.context;
    }

    @Override
    public Action printAvailableOptions() {
        System.out.println("1. Get information about Spaceships;");
        System.out.println("2. Update information about Spaceships;");
        System.out.println("3. Assign spaceship on mission: ");
        System.out.println("4. Exit;");
        System.out.print("Type what option you want to choose: ");
        Scanner in = new Scanner(System.in);
        return SpaceshipAction.getActionById(in.nextInt());
    }

    @Override
    public void handleUserInput(Action action) {
        SpaceshipAction spaceshipAction = (SpaceshipAction) action;
        ApplicationContext context = getApplicationContext();
        Scanner in = new Scanner(System.in);
        switch (spaceshipAction){
            case GET_SPACESHIPS -> {
                context.retrieveBaseEntityList(Spaceship.class).forEach(System.out::println);
            }
            case UPDATE_SPACESHIPS -> {
                context.retrieveBaseEntityList(Spaceship.class).forEach(System.out::println);
                System.out.println("Enter name of spaceship to be updated: ");
                String name = in.nextLine();
                Optional<Spaceship> optionalSpaceship = BaseEntityService.context.retrieveBaseEntityList(Spaceship.class)
                        .stream()
                        .filter(spaceship -> spaceship.getName().equals(name))
                        .findFirst();
                if(optionalSpaceship.isPresent()){
                    SpaceshipServiceImpl spaceshipService = SpaceshipServiceImpl.getService();
                    spaceshipService.updateSpaceshipDetails(optionalSpaceship.get());
                }else {
                    throw new EntityObjectDoesNotExistException("This spaceship does not exist");
                }
            }

            case ASSIGN_SPACESHIP_ON_MISSION -> {
                context.retrieveBaseEntityList(Spaceship.class).forEach(System.out::println);
                SpaceshipServiceImpl spaceshipService = SpaceshipServiceImpl.getService();
                System.out.println("Enter spaceship name to be assigned: ");
                String spaceshipName = in.nextLine();
                Optional<Spaceship> optionalSpaceship = BaseEntityService.context.retrieveBaseEntityList(Spaceship.class)
                        .stream()
                        .filter(sp -> sp.getName().equals(spaceshipName))
                        .findFirst();
                if(optionalSpaceship.isPresent()){
                    Spaceship spaceship = optionalSpaceship.get();
                    spaceshipService.assignSpaceshipOnMission(spaceship);
                }else {
                    throw new EntityObjectDoesNotExistException("This spaceship does not exist");
                }
            }

            case EXIT -> {}
        }
    }
}
