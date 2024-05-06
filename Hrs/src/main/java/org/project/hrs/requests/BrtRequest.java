package org.project.hrs.requests;

import com.fasterxml.jackson.annotation.JsonProperty;

public record BrtRequest (
        @JsonProperty("id") Long id, @JsonProperty("type") Short type, @JsonProperty("number") Long number,  @JsonProperty("start") Long start, @JsonProperty("end") Long end, @JsonProperty("tariff_id") Long tariffId
){
}

