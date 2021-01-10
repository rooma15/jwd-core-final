package com.epam.jwd.core_final.criteria;

import com.epam.jwd.core_final.domain.CrewMember;
import com.epam.jwd.core_final.domain.Rank;
import com.epam.jwd.core_final.domain.Role;

import java.util.Optional;

/**
 * Should be a builder for {@link com.epam.jwd.core_final.domain.CrewMember} fields
 */

public class CrewMemberCriteria extends Criteria<CrewMember> {
    private final Role role;
    private final Rank rank;
    private final Boolean isReadyForNextMissions;

    public CrewMemberCriteria(Role role, Rank rank, Boolean isReadyForNextMissions, Long id, String name) {
        this.id = id;
        this.name = name;
        this.role = role;
        this.rank = rank;
        this.isReadyForNextMissions = isReadyForNextMissions;
    }

    /*public Optional<Role> getRole() {
        return Optional.ofNullable(role);
    }

    public Optional<Rank> getRank() {
        return Optional.ofNullable(rank);
    }

    public Optional<Boolean> isReadyForNextMissions() {
        return Optional.ofNullable(isReadyForNextMissions);
    }*/

    public Role getRole() {
        return role;
    }

    public Rank getRank() {
        return rank;
    }

    public Boolean isReadyForNextMissions() {
        return isReadyForNextMissions;
    }

    public static class CrewMemberCriteriaBuilder extends Criteria.CriteriaBuilder<CrewMemberCriteriaBuilder>{
        private Long id;
        private String name;
        private Role role;
        private Rank rank;
        private Boolean isReadyForNextMissions;

        public CrewMemberCriteriaBuilder role(Role criteriaRole){
            role = criteriaRole;
            return this;
        }

        public CrewMemberCriteriaBuilder rank(Rank criteriaRank){
            rank = criteriaRank;
            return this;
        }

        public CrewMemberCriteriaBuilder isReadyForNextMissions(Boolean readyState){
            isReadyForNextMissions = readyState;
            return this;
        }

        @Override
        public CrewMemberCriteriaBuilder id(Long id) {
            this.id = id;
            return this;
        }

        @Override
        public CrewMemberCriteriaBuilder name(String name) {
            this.name = name;
            return this;
        }

        public CrewMemberCriteria build(){
            return new CrewMemberCriteria(role, rank, isReadyForNextMissions, id, name);
        }

    }
}
