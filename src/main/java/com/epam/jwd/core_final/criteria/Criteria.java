package com.epam.jwd.core_final.criteria;

import com.epam.jwd.core_final.domain.BaseEntity;


/**
 * Should be a builder for {@link BaseEntity} fields
 */
public abstract class Criteria<T extends BaseEntity> {

    protected Long id;
    protected String name;

    /*public Optional<Long> getId() {
        return Optional.ofNullable(id);
    }

    public Optional<String> getName() {
        return Optional.ofNullable(name);
    }*/

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }


    static public abstract class CriteriaBuilder<E extends CriteriaBuilder<E>>{

        public abstract E id(Long id);

        public abstract E name(String name);
    }
}
