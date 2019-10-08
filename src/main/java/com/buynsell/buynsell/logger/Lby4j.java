package com.buynsell.buynsell.logger;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class Lby4j implements Lby4jInterface {


    //to reset
    public static final String ANSI_RESET = "\u001B[0m";
    //Log
    public static final String ANSI_BLACK = "\u001B[30m";
    //Error
    public static final String ANSI_RED = "\u001B[31m";
    //Success
    public static final String ANSI_GREEN = "\u001B[32m";
    //Warning
    public static final String ANSI_YELLOW = "\u001B[33m";
    //info
    public static final String ANSI_CYAN = "\u001B[36m";
    //debug
    public static final String ANSI_PURPLE = "\u001B[35m";


    @Value("${logger.level}")
    private String level;

    @Override
    public void info(String message) {
        System.out.println(ANSI_CYAN + "log.info: " + ANSI_RESET + message );
    }

    @Override
    public void warn(String message) {
        System.out.println(ANSI_YELLOW + "log.warn: "+ ANSI_RESET + message);
    }

    @Override
    public void error(String message) {
        System.out.println(ANSI_RED + "log.error: " + ANSI_RESET + message);
    }

    @Override
    public void debug(String message) {
        System.out.println(ANSI_PURPLE + "log.debug: " + ANSI_RESET + message);
    }

    @Override
    public void success(String message) {
        System.out.println(ANSI_GREEN + "log.success: " + ANSI_RESET + message);
    }

    @Override
    public void log(String message) {
        System.out.println(ANSI_BLACK + "log.log: " + ANSI_RESET + message);
    }
}
