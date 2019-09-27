package com.buynsell.buynsell;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.convert.threeten.Jsr310JpaConverters;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import javax.annotation.PostConstruct;
import javax.persistence.EntityListeners;
import java.util.TimeZone;

@SpringBootApplication
@EntityScan(basePackageClasses = {
        BuynsellApplication.class,
        Jsr310JpaConverters.class
})
public class BuynsellApplication {
    @PostConstruct
    void init() {
        TimeZone.setDefault(TimeZone.getTimeZone("IST"));
    }

    public static void main(String[] args) {
        SpringApplication.run(BuynsellApplication.class, args);
    }

}
