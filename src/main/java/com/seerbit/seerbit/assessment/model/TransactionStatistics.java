package com.seerbit.seerbit.assessment.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;

public record TransactionStatistics(
        @JsonProperty("sum") BigDecimal sum,
        @JsonProperty("avg") BigDecimal avg,
        @JsonProperty("max") BigDecimal max,
        @JsonProperty("min") BigDecimal min,
        @JsonProperty("count") long count
) {
}
