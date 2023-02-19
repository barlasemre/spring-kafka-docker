package com.barlas.kafkaexample.event;

import com.fasterxml.jackson.annotation.JsonProperty;

public record EmployeeCreated(@JsonProperty("message") String message,
                              @JsonProperty("identifier") String identifier) {
}
