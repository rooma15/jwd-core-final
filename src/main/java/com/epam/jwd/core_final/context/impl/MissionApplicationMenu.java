package com.epam.jwd.core_final.context.impl;

import com.epam.jwd.core_final.Main;
import com.epam.jwd.core_final.context.Action;
import com.epam.jwd.core_final.context.ApplicationContext;
import com.epam.jwd.core_final.context.ApplicationMenu;
import com.epam.jwd.core_final.decorator.impl.FlightMissionPreProcessFactory;
import com.epam.jwd.core_final.domain.ApplicationProperties;
import com.epam.jwd.core_final.domain.FlightMission;
import com.epam.jwd.core_final.domain.MissionStatus;
import com.epam.jwd.core_final.exception.EntityObjectDoesNotExistException;
import com.epam.jwd.core_final.factory.impl.FlightMissionFactory;
import com.epam.jwd.core_final.service.BaseEntityService;
import com.epam.jwd.core_final.service.impl.MissionServiceImpl;
import org.json.*;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Collection;
import java.util.Optional;
import java.util.Scanner;

public class MissionApplicationMenu implements ApplicationMenu {

    @Override
    public ApplicationContext getApplicationContext() {
        return BaseEntityService.context;
    }

    @Override
    public Action printAvailableOptions() {
        System.out.println("Please, choose one of the options below: ");
        System.out.println("1. Create Mission;");
        System.out.println("2. Update information about Mission;");
        System.out.println("3. Change mission status;");
        System.out.println("4. Print information about selected mission(s) in output file;");
        System.out.println("5. Exit;");
        System.out.print("Type what option you want to choose: ");
        Scanner in = new Scanner(System.in);
        Main.LOGGER.info("Choosing option in mission");
        return MissionAction.getActionById(in.nextInt());
    }

    @Override
    public void handleUserInput(Action action) throws IOException {
        MissionAction missionAction = (MissionAction) action;
        ApplicationContext context = getApplicationContext();
        Scanner in = new Scanner(System.in);
        switch (missionAction) {
            case CREATE_MISSION -> {
                ApplicationProperties props = new ApplicationProperties();
                System.out.print("Enter mission name: ");
                Main.LOGGER.info("Enter mission name: ");
                String name = in.nextLine();
                if(name.equals("")) {
                    Main.LOGGER.error("Name can not be empty");
                    throw new IllegalArgumentException("Name can not be empty");
                }

                System.out.println("Enter start mission date, pattern - " + props.getDateTimeFormat() + ": ");
                LocalDate startDate = LocalDate.parse(in.nextLine(),
                        DateTimeFormatter.ofPattern(props.getDateTimeFormat()));
                System.out.println("Enter end mission date, pattern - " + props.getDateTimeFormat() + ": ");
                LocalDate endDate = LocalDate.parse(in.nextLine(),
                        DateTimeFormatter.ofPattern(props.getDateTimeFormat()));

                System.out.println("Enter mission flight distance: ");
                Long distance = Long.parseLong(in.nextLine());
                System.out.println("Enter mission status(cancelled, failed, planned, in_progress, completed): ");
                MissionStatus missionStatus = null;
                try {
                    missionStatus = MissionStatus.valueOf(in.nextLine().toUpperCase());
                } catch (IllegalArgumentException e) {
                    throw new IllegalArgumentException("This status does not exist");
                }


                FlightMissionPreProcessFactory factory = FlightMissionPreProcessFactory.
                        getDecorator(FlightMissionFactory.getFactory());
                FlightMission flightMission = factory.create(name, startDate, endDate, distance, missionStatus);

                MissionServiceImpl missionService = MissionServiceImpl.getService();
                missionService.createMission(flightMission);
            }

            case UPDATE_MISSION -> {
                System.out.println("Enter name of mission to be updated: ");
                String name = in.nextLine();
                Optional<FlightMission> optionalFlightMission = BaseEntityService.context
                        .retrieveBaseEntityList(FlightMission.class)
                        .stream()
                        .filter((mission -> mission.getMissionName().equals(name)))
                        .findFirst();
                if(optionalFlightMission.isPresent()) {
                    MissionServiceImpl missionService = MissionServiceImpl.getService();
                    missionService.updateMissionDetails(optionalFlightMission.get());
                } else {
                    Main.LOGGER.error("This mission does not exist");
                    throw new EntityObjectDoesNotExistException("This mission does not exist");
                }
            }

            case CHANGE_MISSION_STATUS -> {
                System.out.println("Enter mission name: ");
                String name = in.nextLine();

                System.out.println("Enter new mission status(cancelled, failed, planned, in_progress, completed): ");
                MissionStatus missionStatus = null;
                try {
                    missionStatus = MissionStatus.valueOf(in.nextLine().toUpperCase());
                } catch (IllegalArgumentException e) {
                    Main.LOGGER.error("This status does not exist");
                    throw new IllegalArgumentException("This status does not exist");
                }

                Optional<FlightMission> optionalFlightMission = BaseEntityService.context
                        .retrieveBaseEntityList(FlightMission.class)
                        .stream()
                        .filter(mission -> mission.getMissionName().equals(name))
                        .findFirst();
                if(optionalFlightMission.isPresent()) {
                    FlightMission flightMission = optionalFlightMission.get();
                    flightMission.setMissionStatus(missionStatus);
                }
            }

            case PRINT_MISSIONS_IN_FILE -> {
                context.retrieveBaseEntityList(FlightMission.class).forEach(System.out::println);
                ApplicationProperties properties = new ApplicationProperties();
                String outputFileSrc = "./src/main/resources/" + properties.getOutputRootDir() + "/" + "missions.json";
                System.out.println("Enter names of missions to be printed into file pattern: name1,name2,...: ");
                String[] missionsNames = in.nextLine().split(",");
                Collection<FlightMission> missions = context.retrieveBaseEntityList(FlightMission.class);
                if(missions.isEmpty()) {
                    throw new EntityObjectDoesNotExistException("There no missions");
                }

                File outputFile = new File(outputFileSrc);
                File outputDir = new File("./src/main/resources/output");
                if(!outputDir.exists()){
                    outputDir.mkdir();
                }
                if(!outputFile.exists()){
                    outputFile.createNewFile();
                }



                FileWriter writer = new FileWriter(outputFileSrc);


                for(FlightMission mission : missions) {
                    for(String missionName : missionsNames) {
                        if(mission.getMissionName().equals(missionName)) {
                            JSONObject jsonMission = new JSONObject();
                            jsonMission = mission.toJSON();
                            writer.write(jsonMission.toString());
                            writer.write(System.lineSeparator());
                        }
                    }
                }
                writer.close();
            }

            case EXIT -> {
            }


        }
    }
}
