package org.project.brtservice.requests;

public record BrtRequest(
        Long id, Short type, Long number, Long start, Long end, Long tariffId
) {
}
