package com.epam.jwd.core_final.context;


import com.epam.jwd.core_final.context.impl.CrewMemberApplicationMenu;
import com.epam.jwd.core_final.context.impl.DefaultAction;
import com.epam.jwd.core_final.context.impl.MissionApplicationMenu;
import com.epam.jwd.core_final.context.impl.SpaceshipApplicationMenu;
import com.epam.jwd.core_final.service.BaseEntityService;

import java.io.IOException;
import java.util.Scanner;

// todo replace Object with your own types
@FunctionalInterface
public interface ApplicationMenu {

    ApplicationContext getApplicationContext();

    default Action printAvailableOptions() {
        System.out.println();
        System.out.println("------------------------------");
        System.out.println();
        System.out.println("Welcome to airport simulator!");
        System.out.println("Please, choose one of the options below: ");
        System.out.println("1. Interaction with Crew Members;");
        System.out.println("2. Interaction with Spaceships;");
        System.out.println("3. Interaction with Missions;");
        System.out.println("4. Exit;");
        System.out.print("Type what option you want to choose: ");
        Scanner in = new Scanner(System.in);
        return DefaultAction.getActionById(in.nextInt());
    }

    default void handleUserInput(Action action) throws IOException {
        DefaultAction defaultAction = (DefaultAction) action;
        ApplicationContext context = BaseEntityService.context;
        Scanner in = new Scanner(System.in);
        switch (defaultAction){
            case SELECT_CREW_MEMBERS -> {
                ApplicationMenu menu = new CrewMemberApplicationMenu();
                Action choice = menu.printAvailableOptions();
                try {
                    menu.handleUserInput(choice);
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
            }

            case SELECT_SPACESHIPS -> {
                ApplicationMenu menu = new SpaceshipApplicationMenu();
                Action choice = menu.printAvailableOptions();
                try {
                    menu.handleUserInput(choice);
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
            }


            case SELECT_MISSIONS -> {
                ApplicationMenu menu = new MissionApplicationMenu();
                Action choice = menu.printAvailableOptions();
                try {
                    menu.handleUserInput(choice);
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
            }

            case EXIT -> {}
        }
    }
}
