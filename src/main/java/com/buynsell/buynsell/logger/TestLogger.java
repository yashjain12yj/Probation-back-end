package com.buynsell.buynsell.logger;

public class TestLogger {
    public static void main(String[] args) {
        Lby4j log = new Lby4j();
        log.debug("Hello I am debug");
        log.error("Hello, I am error");
        log.info("Hello, I am info");
        log.log("Hello, I am log");
        log.success("Hello, I am success");
        log.warn("Hello, I am warning");
    }
}
