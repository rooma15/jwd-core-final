package com.epam.jwd.core_final.context.impl;

import com.epam.jwd.core_final.context.ApplicationContext;
import com.epam.jwd.core_final.context.ApplicationMenu;

public class ApplicationMenuImpl implements ApplicationMenu {

    static private final NassaContext nassaContext;

    static {
        nassaContext = new NassaContext();
    }

    @Override
    public ApplicationContext getApplicationContext() {
        return nassaContext;
    }
}
