package com.epam.jwd.core_final.context;

import com.epam.jwd.core_final.Main;
import com.epam.jwd.core_final.context.impl.ApplicationMenuImpl;
import com.epam.jwd.core_final.context.impl.DefaultAction;
import com.epam.jwd.core_final.exception.InvalidStateException;
import com.epam.jwd.core_final.service.BaseEntityService;
import javax.xml.*;
import org.xml.sax.SAXException;
import java.io.IOException;
import java.util.function.Supplier;

public interface Application {

    static ApplicationMenu start() throws InvalidStateException {
        final ApplicationMenuImpl menu = BaseEntityService.menu;
        final ApplicationContext nassaContext = menu.getApplicationContext();

        final Supplier<ApplicationContext> applicationContextSupplier = () -> nassaContext;



        nassaContext.init();
        // todo

        Action action;
            do{
                action = menu.printAvailableOptions();
                try {
                    menu.handleUserInput(action);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }while(action != DefaultAction.EXIT);

        return applicationContextSupplier::get;
    }
}
