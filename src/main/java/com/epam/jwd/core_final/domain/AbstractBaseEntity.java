package com.epam.jwd.core_final.domain;

/**
 * Expected fields:
 * <p>
 * id {@link Long} - entity id
 * name {@link String} - entity name
 */
public abstract class AbstractBaseEntity implements BaseEntity {

    protected Long id;
    protected String name;

    @Override
    public Long getId() {
        // todo
        return id;
    }

    @Override
    public String getName() {
        // todo
        return name;
    }
}
