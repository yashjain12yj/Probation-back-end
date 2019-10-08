package com.buynsell.buynsell.logger;

import org.springframework.beans.factory.annotation.Autowired;

public class TestLogger {
    @Autowired
    public static Lby4j log;
    public static void main(String[] args) {
        log.debug("Hello I am debug");
        log.error("Hello, I am error");
        log.info("Hello, I am info");
        log.log("Hello, I am log");
        log.success("Hello, I am success");
        log.warn("Hello, I am warning");
    }
}
