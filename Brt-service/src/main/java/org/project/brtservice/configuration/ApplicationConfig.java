package org.project.brtservice.configuration;

import org.antlr.v4.runtime.misc.NotNull;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

@Validated
@ConfigurationProperties(prefix = "app", ignoreUnknownFields = false)
public record ApplicationConfig(
        @NotNull
        String bootstrapServers,
        @NotNull
        String cdrListenerTopic,
        @NotNull
        String brtProducerTopic,
        @NotNull
        String hrsListenerTopic,
        @NotNull
        String hrsProducerMonthTopic
) {

}