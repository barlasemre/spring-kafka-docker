package com.barlas.kafkaexample.event;

import com.fasterxml.jackson.annotation.JsonProperty;

public record EmployeeDeleted(@JsonProperty("message") String message,
                       @JsonProperty("identifier") long identifier) {
}
