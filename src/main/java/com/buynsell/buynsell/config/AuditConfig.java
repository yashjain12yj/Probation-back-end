package com.buynsell.buynsell.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import javax.annotation.PostConstruct;
import java.util.TimeZone;

@Configuration
@EnableJpaAuditing
public class AuditConfig {
    @PostConstruct
    void init() {
        TimeZone.setDefault(TimeZone.getTimeZone("IST"));
    }
}
