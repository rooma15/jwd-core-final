package com.epam.jwd.core_final.domain;

import org.json.JSONObject;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Expected fields:
 * <p>
 * missions name {@link String}
 * start date {@link java.time.LocalDate}
 * end date {@link java.time.LocalDate}
 * distance {@link Long} - missions distance
 * assignedSpaceShift {@link Spaceship} - not defined by default
 * assignedCrew {@link java.util.List<CrewMember>} - list of missions members based on ship capacity - not defined by default
 * missionResult {@link MissionStatus}
 */
public class FlightMission extends AbstractBaseEntity {
    private final String missionName;
    private final LocalDate startDate;
    private final LocalDate endDate;
    private final Long distance;
    private Spaceship assignedSpaceShift;
    private List<CrewMember> assignedCrew;
    private MissionStatus missionStatus;

    public FlightMission(String missionName,
                         LocalDate startDate,
                         LocalDate endDate,
                         Long distance,
                         MissionStatus missionResult,
                         Long id) {
        this.id = id;
        this.missionName = missionName;
        this.startDate = startDate;
        this.endDate = endDate;
        this.distance = distance;
        this.missionStatus = missionResult;
        this.assignedCrew = new ArrayList<>();
    }

    public void setAssignedSpaceShift(Spaceship assignedSpaceShift) {
        this.assignedSpaceShift = assignedSpaceShift;
        if(missionStatus == MissionStatus.COMPLETED){
            assignedSpaceShift.setFlightDistance(assignedSpaceShift.getFlightDistance() + distance);
        }
    }

    public void setMissionStatus(MissionStatus missionStatus) {
        if(missionStatus == MissionStatus.COMPLETED){
            if(assignedSpaceShift != null){
                assignedSpaceShift.setFlightDistance(assignedSpaceShift.getFlightDistance() + distance);
            }
        }
        this.missionStatus = missionStatus;
    }

    public String getMissionName() {
        return missionName;
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

    public MissionStatus getMissionStatus() {
        return missionStatus;
    }

    @Override
    public String toString() {
        return "FlightMission{" +
                "missionName = '" + missionName + "; " +
                ", startDate = " + startDate + "; " +
                ", endDate = " + endDate + "; " +
                ", distance = " + distance + "; " +
                ", assignedSpaceShift = " + assignedSpaceShift + "; " +
                ", assignedCrew = " + assignedCrew + "; " +
                ", missionResult = " + missionStatus + "; " +
                '}';
    }

    public JSONObject toJSON(){
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("id", id);
        jsonObject.put("name", missionName);
        jsonObject.put("start date", startDate.toString());
        jsonObject.put("end date", endDate.toString());
        jsonObject.put("assignedSpaceShift", assignedSpaceShift);
        jsonObject.put("assigned crew", assignedCrew);
        jsonObject.put("mission status", missionStatus);
        return jsonObject;
    }

    // todo
}
