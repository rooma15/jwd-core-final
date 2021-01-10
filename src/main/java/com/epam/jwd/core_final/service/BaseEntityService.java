package com.epam.jwd.core_final.service;

import com.epam.jwd.core_final.context.ApplicationContext;
import com.epam.jwd.core_final.context.impl.ApplicationMenuImpl;

public abstract class BaseEntityService {
    static public final ApplicationContext context;
    static public final ApplicationMenuImpl menu;

    static {
        menu = new ApplicationMenuImpl();
        context = menu.getApplicationContext();
    }
}
