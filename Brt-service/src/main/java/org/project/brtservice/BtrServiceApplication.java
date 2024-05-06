package org.project.brtservice;

import org.project.brtservice.configuration.ApplicationConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties(ApplicationConfig.class)
public class BtrServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(BtrServiceApplication.class, args);
    }

}
