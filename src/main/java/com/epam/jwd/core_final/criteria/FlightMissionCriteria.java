package com.epam.jwd.core_final.criteria;

import com.epam.jwd.core_final.domain.CrewMember;
import com.epam.jwd.core_final.domain.FlightMission;
import com.epam.jwd.core_final.domain.MissionStatus;
import com.epam.jwd.core_final.domain.Spaceship;

import java.time.LocalDate;
import java.util.List;

/**
 * Should be a builder for {@link com.epam.jwd.core_final.domain.FlightMission} fields
 */
public class FlightMissionCriteria extends Criteria<FlightMission> {
    private final LocalDate startDate;
    private final LocalDate endDate;
    private final Long distance;
    private final Spaceship assignedSpaceShift;
    private final List<CrewMember> assignedCrew;
    private final MissionStatus missionResult;

    public FlightMissionCriteria(LocalDate startDate,
                                 LocalDate endDate,
                                 Long distance,
                                 Spaceship assignedSpaceShift,
                                 List<CrewMember> assignedCrew,
                                 MissionStatus missionResult,
                                 String name,
                                 Long id
                                 ) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.distance = distance;
        this.assignedSpaceShift = assignedSpaceShift;
        this.assignedCrew = assignedCrew;
        this.missionResult = missionResult;
        this.id = id;
        this.name = name;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public Long getDistance() {
        return distance;
    }

    public Spaceship getAssignedSpaceShift() {
        return assignedSpaceShift;
    }

    public List<CrewMember> getAssignedCrew() {
        return assignedCrew;
    }

    public MissionStatus getMissionResult() {
        return missionResult;
    }

    public static class FlightMissionCriteriaBuilder extends CriteriaBuilder<FlightMissionCriteriaBuilder>{

        private LocalDate startDate;
        private LocalDate endDate;
        private Long distance;
        private Spaceship assignedSpaceShift;
        private List<CrewMember> assignedCrew;
        private MissionStatus missionResult;
        private Long id;
        private String name;


        @Override
        public FlightMissionCriteriaBuilder id(Long id) {
            this.id = id;
            return this;
        }

        @Override
        public FlightMissionCriteriaBuilder name(String name) {
            this.name = name;
            return this;
        }

        public void startDate(LocalDate startDate) {
            this.startDate = startDate;
        }

        public void endDate(LocalDate endDate) {
            this.endDate = endDate;
        }

        public void distance(Long distance) {
            this.distance = distance;
        }

        public void assignedSpaceShift(Spaceship assignedSpaceShift) {
            this.assignedSpaceShift = assignedSpaceShift;
        }

        public void assignedCrew(List<CrewMember> assignedCrew) {
            this.assignedCrew = assignedCrew;
        }

        public void missionResult(MissionStatus missionResult) {
            this.missionResult = missionResult;
        }

        public FlightMissionCriteria build(){
            return new FlightMissionCriteria(startDate,
                    endDate,
                    distance,
                    assignedSpaceShift,
                    assignedCrew,
                    missionResult,
                    name,
                    id);
        }
    }
}
