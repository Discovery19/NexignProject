package org.project.hrs.requests;

import com.fasterxml.jackson.annotation.JsonProperty;

public record BrtRequest (
       Long id, Short type, Long number,  Long start, Long end, Long tariffId, boolean flagAbonent
){
}

