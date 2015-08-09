package it.sirfinpa.roboerp.cli;


import org.apache.log4j.Logger;

public class CliLogger {

    private static final Logger LOGGER = Logger.getLogger(CliLogger.class);

    public static void write(String message){
        LOGGER.info(message);
    }

}
