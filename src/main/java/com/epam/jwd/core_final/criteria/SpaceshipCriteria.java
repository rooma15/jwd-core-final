package com.epam.jwd.core_final.criteria;

import com.epam.jwd.core_final.domain.Spaceship;

/**
 * Should be a builder for {@link Spaceship} fields
 */
public class SpaceshipCriteria extends Criteria<Spaceship> {
    private final Long flightDistance;
    private final Boolean isReadyForNextMissions;
    private final Integer amountOfCrewMembers;
    private final Long bottomBorderFlightDistance;
    private final Long topBorderFlightDistance;
    private final Integer bottomBorderAmountCrew;
    private final Integer topBorderAmountCrew;

    public Integer getBottomBorderAmountCrew() {
        return bottomBorderAmountCrew;
    }

    public Integer getTopBorderAmountCrew() {
        return topBorderAmountCrew;
    }


    public Long getFlightDistance() {
        return flightDistance;
    }

    public Boolean getReadyForNextMissions() {
        return isReadyForNextMissions;
    }

    public Integer getAmountOfCrewMembers() {
        return amountOfCrewMembers;
    }

    public Long getBottomBorderFlightDistance() {
        return bottomBorderFlightDistance;
    }

    public Long getTopBorderFlightDistance() {
        return topBorderFlightDistance;
    }


    public SpaceshipCriteria(Long flightDistance,
                             Boolean isReadyForNextMissions,
                             Integer amountOfCrewMembers,
                             Long bottomBorderFlightDistance,
                             Long topBorderFlightDistance,
                             Integer bottomBorderAmountCrew,
                             Integer topBorderAmountCrew,
                             String name,
                             Long id) {
        this.flightDistance = flightDistance;
        this.isReadyForNextMissions = isReadyForNextMissions;
        this.amountOfCrewMembers = amountOfCrewMembers;
        this.bottomBorderFlightDistance = bottomBorderFlightDistance;
        this.topBorderFlightDistance = topBorderFlightDistance;
        this.bottomBorderAmountCrew = bottomBorderAmountCrew;
        this.topBorderAmountCrew = topBorderAmountCrew;
        this.name = name;
        this.id = id;
    }

    static public class SpaceshipCriteriaBuilder extends CriteriaBuilder<SpaceshipCriteriaBuilder>{

        private Long flightDistance;
        private Boolean isReadyForNextMissions;
        private Integer amountOfCrewMembers;
        private Long bottomBorderFlightDistance;
        private Long topBorderFlightDistance;
        private Integer bottomBorderAmountCrew;
        private Integer topBorderAmountCrew;
        private Long id;
        private String name;

        @Override
        public SpaceshipCriteriaBuilder id(Long id) {
            this.id = id;
            return this;
        }

        @Override
        public SpaceshipCriteriaBuilder name(String name) {
            this.name = name;
            return this;
        }


        public void amountOfCrewMembers(Integer amountOfCrewMembers) {
            this.amountOfCrewMembers = amountOfCrewMembers;
        }

        public void bottomBorderFlightDistance(Long bottomBorderFlightDistance) {
            this.bottomBorderFlightDistance = bottomBorderFlightDistance;
        }

        public void topBorderFlightDistance(Long topBorderFlightDistance) {
            this.topBorderFlightDistance = topBorderFlightDistance;
        }

        public void bottomBorderAmountCrew(Integer bottomBorderAmountCrew) {
            this.bottomBorderAmountCrew = bottomBorderAmountCrew;
        }

        public void topBorderAmountCrew(Integer topBorderAmountCrew) {
            this.topBorderAmountCrew = topBorderAmountCrew;
        }

        public SpaceshipCriteriaBuilder flightDistance(Long distance){
            this.flightDistance = distance;
            return this;
        }

        public SpaceshipCriteriaBuilder isReadyForNextMissions(Boolean isReadyForNextMissions){
            this.isReadyForNextMissions = isReadyForNextMissions;
            return this;
        }

        public SpaceshipCriteria build(){
            return new SpaceshipCriteria(flightDistance,
                    isReadyForNextMissions,
                    amountOfCrewMembers,
                    bottomBorderFlightDistance,
                    topBorderFlightDistance,
                    bottomBorderAmountCrew,
                    topBorderAmountCrew,
                    name,
                    id);
        }
    }
}
