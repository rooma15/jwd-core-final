package com.epam.jwd.core_final.util;

import com.epam.jwd.core_final.Main;
import com.epam.jwd.core_final.domain.ApplicationProperties;
import com.sun.source.tree.DirectiveTree;


import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public final class PropertyReaderUtil {

    private static final Properties properties = new Properties();



    private PropertyReaderUtil() {
    }



    /**
     * try-with-resource using FileInputStream
     *
     * @see {https://www.netjstech.com/2017/09/how-to-read-properties-file-in-java.html for an example}
     * <p>
     * as a result - you should populate {@link ApplicationProperties} with corresponding
     * values from property file
     */
    private static void loadProperties() throws IOException {
        Main.LOGGER.info("Loading application properties");
        final String propertiesFileName = "./src/main/resources/application.properties";
        try(InputStream input = new FileInputStream(propertiesFileName)){
            properties.load(input);
        }
    }

    public static Properties getProperties() throws IOException{
        loadProperties();
        return properties;
    }


}
