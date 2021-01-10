package com.epam.jwd.core_final.decorator;


import com.epam.jwd.core_final.domain.BaseEntity;
import com.epam.jwd.core_final.factory.EntityFactory;

public abstract class AbstractPreProcessFactory<T extends BaseEntity>{

    protected final EntityFactory<T> factory;

    public AbstractPreProcessFactory(EntityFactory<T> factory){
        this.factory = factory;
    }

    protected abstract void preProcess(Object...args);
}
