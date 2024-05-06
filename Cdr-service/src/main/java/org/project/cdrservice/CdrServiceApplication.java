package org.project.cdrservice;

import org.project.cdrservice.config.ApplicationConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
@EnableConfigurationProperties(ApplicationConfig.class)
public class CdrServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(CdrServiceApplication.class, args);
    }

}
