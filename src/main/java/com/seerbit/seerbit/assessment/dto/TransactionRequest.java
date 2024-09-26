package com.seerbit.seerbit.assessment.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;
public record TransactionRequest(
        @JsonProperty("amount") String amount,
        @JsonProperty("timestamp") String timestamp) {
}
