package org.project.cdrservice.config;

import org.antlr.v4.runtime.misc.NotNull;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.validation.annotation.Validated;

import java.time.Duration;

@Validated
@EnableKafka
@ConfigurationProperties(prefix = "app", ignoreUnknownFields = false)
public record ApplicationConfig(
        @Bean
        Scheduler scheduler,
        @Bean
        Generator generator,
        String bootstrapServer,
        String topic
) {
    public record Scheduler(boolean enable,
                            Duration interval) {
    }

    public record Generator(Integer numberFiles, Integer lastMonth, Integer year) {

    }
}