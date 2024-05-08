package org.project.brtservice.requests;


public record CdrRequest(
        Long id, Short type, Long number,
        Long start, Long end, Long anotherNumber) {
}
