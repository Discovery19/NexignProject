package org.project.crm.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record InfoAbonent(String msisdn,
                          Integer money,
                          Long tariffId) {
}
