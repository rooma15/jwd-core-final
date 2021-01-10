package com.epam.jwd.core_final.context.impl;

import com.epam.jwd.core_final.Main;
import com.epam.jwd.core_final.context.ApplicationContext;
import com.epam.jwd.core_final.decorator.impl.CrewMemberPreProcessFactory;
import com.epam.jwd.core_final.decorator.impl.SpaceshipPreProcessFactory;
import com.epam.jwd.core_final.domain.*;
import com.epam.jwd.core_final.exception.DuplicateEntityObjectException;
import com.epam.jwd.core_final.exception.InvalidStateException;
import com.epam.jwd.core_final.exception.UnknownEntityException;
import com.epam.jwd.core_final.factory.impl.CrewMemberFactory;
import com.epam.jwd.core_final.factory.impl.SpaceshipFactory;
import com.epam.jwd.core_final.service.impl.CrewServiceImpl;
import com.epam.jwd.core_final.service.impl.SpaceshipServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

// todo
public class NassaContext implements ApplicationContext {

    NassaContext(){
    }

    // no getters/setters for them
    private final Collection<CrewMember> crewMembers = new ArrayList<>();
    private final Collection<Spaceship> spaceships = new ArrayList<>();
    private final Collection<FlightMission> missions = new ArrayList<>();



    @Override
    public <T extends BaseEntity> Collection<T> retrieveBaseEntityList(Class<T> tClass) {
        if(tClass == Spaceship.class){
            return (Collection<T>) spaceships;
        } else if(tClass == CrewMember.class){
            return (Collection<T>) crewMembers;
        }else if(tClass == FlightMission.class){
            return (Collection<T>) missions;
        }

        else throw new UnknownEntityException(tClass.getSimpleName());
    }

    private void readCrewMembers(BufferedReader reader){
        String[] crewMembers = reader.lines().skip(1).findFirst().get().split(";");
        for(String crewMember : crewMembers) {
            String[] params = crewMember.split(",");
            CrewMemberPreProcessFactory factory = CrewMemberPreProcessFactory
                    .getDecorator(CrewMemberFactory.getFactory());
            CrewMember newCrewMember = factory.create(params[1],
                    Role.resolveRoleById(Long.parseLong(params[0])),
                    Rank.resolveRankById(Long.parseLong(params[2])));
            CrewServiceImpl crewService = CrewServiceImpl.getCrewService();
            crewService.createCrewMember(newCrewMember);
        }
    }

    private void readSpaceships(BufferedReader reader){
        List<String> spaceships = reader.lines().skip(3).collect(Collectors.toList());
        for(String spaceship : spaceships) {
            String[] params = spaceship.split(";");
            String spaceshipName = params[0];

            Long distance = Long.parseLong(params[1]);
            String crewScheme = params[2].substring(1, params[2].length() - 1);

            HashMap<Role, Short> crew = new HashMap<>();

            String[] roles = crewScheme.split(",");

            for(String role : roles) {
                String[] values = role.split(":");
                crew.put(Role.resolveRoleById(Long.parseLong(values[0])), Short.parseShort(values[1]));
            }

            SpaceshipPreProcessFactory factory = SpaceshipPreProcessFactory
                    .getDecorator(SpaceshipFactory.getFactory());
            Spaceship newSpaceship = factory.create(spaceshipName, crew, distance);
            
            SpaceshipServiceImpl spaceshipService = SpaceshipServiceImpl.getService();
            try {
                spaceshipService.createSpaceship(newSpaceship);
            } catch (DuplicateEntityObjectException ob){
                System.out.println("Duplicate spaceship with name " + newSpaceship.getName());

            }
        }
    }


    /**
     * You have to read input files, populate collections
     * @throws InvalidStateException
     */




    @Override
    public void init() throws InvalidStateException {
        ApplicationProperties properties = new ApplicationProperties();
        String rootDir = "./src/main/resources/";
        String crewFile = rootDir + "/"
                + properties.getInputRootDir() + "/"
                + properties.getCrewFileName();

        String spaceshipsFile =rootDir + "/"
                + properties.getInputRootDir() + "/"
                + properties.getSpaceshipsFileName();

        try(BufferedReader reader = new BufferedReader(new FileReader(crewFile))){
            readCrewMembers(reader);
        } catch (IOException e) {
            System.out.println("File crew not found");
            throw new InvalidStateException();
        }

        try(BufferedReader reader = new BufferedReader(new FileReader(spaceshipsFile))){
            readSpaceships(reader);
        }catch (IOException e){
            System.out.println("File not found");
            throw new InvalidStateException();
        }
    }
}
