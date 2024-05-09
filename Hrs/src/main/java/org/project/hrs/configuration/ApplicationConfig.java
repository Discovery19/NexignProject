package org.project.hrs.configuration;


import org.antlr.v4.runtime.misc.NotNull;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

@Validated
@ConfigurationProperties(prefix = "app", ignoreUnknownFields = false)
public record ApplicationConfig(
        @NotNull
        String server,
        @NotNull
        String brtListenerTopic,
        @NotNull
        String hrsProducerTopic,
        @NotNull
        String hrsProducerMonthNotifTopic
) {

}