package org.project.cdrservice.kafka;


import com.fasterxml.jackson.annotation.JsonProperty;

public record CdrRequest(
        @JsonProperty("id") Long id, @JsonProperty("type") Short type, @JsonProperty("number") Long number, Long anotherNumber,
        @JsonProperty("start") Long start, @JsonProperty("end") Long end) {
}
