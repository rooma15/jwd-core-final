package com.epam.jwd.core_final.domain;

/**
 * Expected fields:
 * <p>
 * role {@link Role} - member role
 * rank {@link Rank} - member rank
 * isReadyForNextMissions {@link Boolean} - true by default. Set to false, after first failed mission
 */
public class CrewMember extends AbstractBaseEntity {
    private final Role role;
    private final Rank rank;
    private Boolean isReadyForNextMissions;

    {
        isReadyForNextMissions = true;
    }

    public CrewMember(String name, Role role, Rank rank, Long id) {
        this.id = id;
        this.name = name;
        this.role = role;
        this.rank = rank;
    }

    public Role getRole() {
        return role;
    }

    public Rank getRank() {
        return rank;
    }

    public Boolean isReadyForNextMissions() {
        return isReadyForNextMissions;
    }

    public void setReadyForNextMissions(Boolean isReadyForNextMissions) {
        this.isReadyForNextMissions = isReadyForNextMissions;
    }

    @Override
    public String toString() {
        return "CrewMember{" +
                ", id = " + id +
                ", name = '" + name + "; " +
                ", Role = '" + role.getName() + "; " +
                ", Rank = '" + rank.getName() + "; " +
                "isReadyForNextMissions = " + isReadyForNextMissions +
                '}';
    }

    // todo
}
