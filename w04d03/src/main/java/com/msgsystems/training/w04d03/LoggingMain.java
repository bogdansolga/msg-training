package com.msgsystems.training.w04d03;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class LoggingMain {

    private final static Logger LOGGER = LogManager.getLogger();

    public static void main(String[] args) {
        LOGGER.info("Hello, Log4J 2!");
        //LOGGER.warn("message", new Exception());
    }
}
