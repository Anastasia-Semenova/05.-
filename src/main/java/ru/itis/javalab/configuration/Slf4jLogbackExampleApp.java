package ru.itis.javalab.configuration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Slf4jLogbackExampleApp {

    private static final Logger logger
            = LoggerFactory.getLogger(Slf4jLogbackExampleApp.class);

    public static void main(String[] args) {
        logger.info("Example log from {}", Slf4jLogbackExampleApp.class.getSimpleName());
    }
}