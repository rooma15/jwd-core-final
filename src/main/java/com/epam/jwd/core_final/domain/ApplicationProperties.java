package com.epam.jwd.core_final.domain;

import com.epam.jwd.core_final.util.PropertyReaderUtil;

import java.io.IOException;
import java.util.Properties;

/**
 * This class should be IMMUTABLE!
 * <p>
 * Expected fields:
 * <p>
 * inputRootDir {@link String} - base dir for all input files
 * outputRootDir {@link String} - base dir for all output files
 * crewFileName {@link String}
 * missionsFileName {@link String}
 * spaceshipsFileName {@link String}
 * <p>
 * fileRefreshRate {@link Integer}
 * dateTimeFormat {@link String} - date/time format for {@link java.time.format.DateTimeFormatter} pattern
 */
public final class ApplicationProperties {


    private String inputRootDir;
    private String outputRootDir;
    private String crewFileName;
    private String missionsFileName;
    private String spaceshipsFileName;
    private Integer fileRefreshRate;
    private String dateTimeFormat;

    public ApplicationProperties() {
        try {
            Properties properties = PropertyReaderUtil.getProperties();
            inputRootDir = properties.getProperty("inputRootDir");
            outputRootDir = properties.getProperty("outputRootDir");
            crewFileName = properties.getProperty("crewFileName");
            missionsFileName = properties.getProperty("missionsFileName");
            spaceshipsFileName = properties.getProperty("spaceshipsFileName");
            fileRefreshRate = Integer.parseInt(properties.getProperty("fileRefreshRate"));
            dateTimeFormat = properties.getProperty("dateTimeFormat");
        } catch (IOException e) {
            System.out.println("Property file not found");
        }

    }

    public String getInputRootDir() {
        return inputRootDir;
    }

    public String getOutputRootDir() {
        return outputRootDir;
    }

    public String getCrewFileName() {
        return crewFileName;
    }

    public String getMissionsFileName() {
        return missionsFileName;
    }

    public String getSpaceshipsFileName() {
        return spaceshipsFileName;
    }

    public Integer getFileRefreshRate() {
        return fileRefreshRate;
    }

    public String getDateTimeFormat() {
        return dateTimeFormat;
    }

    //todo
}
